#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Cisco Cyber Vision Classic API (Swagger 2.0) -> Spring Cloud OpenFeign 클라이언트/DTO 생성기.

  python tools/gen.py

입력
  src/main/resources/openapi/cyber-vision-3.0.json   원본 스펙
  tools/ko.json                                      한글 번역 사전 (없으면 영문만 생성)

출력
  src/main/java/com/cvGateway/cvGateway/cybervision/model/*.java   DTO
  src/main/java/com/cvGateway/cvGateway/cybervision/api/*.java     태그별 Feign 클라이언트
"""

import json
import os
import re
import shutil
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SPEC = os.path.join(ROOT, "src", "main", "resources", "openapi", "cyber-vision-3.0.json")
KO = os.path.join(ROOT, "tools", "ko.json")

BASE_PKG = "com.cvGateway.cvGateway.cybervision"
MODEL_PKG = BASE_PKG + ".model"
API_PKG = BASE_PKG + ".api"
MODEL_DIR = os.path.join(ROOT, "src", "main", "java", *BASE_PKG.split("."), "model")
API_DIR = os.path.join(ROOT, "src", "main", "java", *BASE_PKG.split("."), "api")

HTTP_METHODS = ("get", "post", "put", "patch", "delete")

# Feign 인터페이스에 노출하지 않고 설정/인터셉터에서 전역 처리하는 헤더.
GLOBAL_HEADERS = {"Accept-Language"}

# 스펙에 인라인으로만 존재해 이름이 없는 스키마에 부여할 DTO 이름.
# key = "<operationId>:<body|response>"
INLINE_NAMES = {
    "moveSensorsToFolder:body": "SensorExplorerMoveTarget",
    "putGlobalCredentials:body": "GlobalCredentialsRequest",
    "postGlobalCredentials:body": "GlobalCredentialsRequest",
    "updateCustomNetworks:body": "CustomNetworkUpdate",
    "createCustomNetworks:body": "CustomNetworkCreate",
    "validateCustomNetworks:body": "CustomNetworkCreate",
    "putRiskScorePeriod:body": "RiskScorePeriod",
    "updateSensorCaptureModeFilter:body": "SensorCaptureModeFilter",
    "getGlobalCredentials:response": "GlobalCredentials",
    "putGlobalCredentials:response": "GlobalCredentials",
    "postGlobalCredentials:response": "GlobalCredentials",
    "getRiskScorePeriod:response": "RiskScorePeriod",
}

JAVA_KEYWORDS = {
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
    "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
    "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
    "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
    "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
    "volatile", "while", "record", "var",
}


# --------------------------------------------------------------------------- 유틸

def load_json(path, default=None):
    if not os.path.exists(path):
        if default is None:
            sys.exit("파일을 찾을 수 없습니다: " + path)
        return default
    with open(path, encoding="utf-8") as fp:
        return json.load(fp)


def pascal(text):
    parts = re.split(r"[^0-9A-Za-z]+", text)
    out = []
    for p in parts:
        if not p:
            continue
        out.append(p[0].upper() + p[1:])
    return "".join(out) or "Value"


def camel(text):
    p = pascal(text)
    name = p[0].lower() + p[1:] if p else "value"
    if name in JAVA_KEYWORDS or name[0].isdigit():
        name = name + "Value"
    return name


def esc(text):
    """Javadoc 본문에 넣을 수 있도록 이스케이프."""
    if text is None:
        return ""
    return (text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("*/", "*&#47;"))


def wrap(text, width=100):
    """Javadoc 한 줄이 너무 길어지지 않도록 단어 단위로 접는다."""
    words = str(text).split()
    lines, cur = [], ""
    for w in words:
        if cur and len(cur) + 1 + len(w) > width:
            lines.append(cur)
            cur = w
        else:
            cur = (cur + " " + w) if cur else w
    if cur:
        lines.append(cur)
    return lines or [""]


# --------------------------------------------------------------------------- 타입 매핑

class Types:
    """스펙의 스키마를 자바 타입 문자열로 변환하고, 필요한 import 를 모은다."""

    def __init__(self, spec):
        self.defs = spec["definitions"]
        self.alias = {}       # 정의 이름 -> 클래스를 만들지 않고 대체할 자바 타입
        self.classes = set()  # 실제로 클래스를 생성할 정의 이름
        for name, schema in self.defs.items():
            kind = schema.get("type")
            if kind == "object" or "properties" in schema:
                self.classes.add(name)
            elif kind == "string":
                self.alias[name] = "String"
            elif kind == "array":
                self.alias[name] = None  # 아래에서 2차 해석
            else:
                self.alias[name] = "Object"
        # 배열 별칭은 원소 타입이 먼저 정해져야 하므로 뒤늦게 해석한다.
        for name, schema in self.defs.items():
            if schema.get("type") == "array" and self.alias.get(name, "x") is None:
                self.alias[name] = "List<%s>" % self.of(schema.get("items", {}), boxed=True)

    def ref_name(self, schema):
        ref = schema.get("$ref")
        return ref.rsplit("/", 1)[-1] if ref else None

    def of(self, schema, imports=None, boxed=False):
        """스키마 하나를 자바 타입 문자열로."""
        if not schema:
            return "Object"

        ref = self.ref_name(schema)
        if ref:
            if ref in self.classes:
                return ref
            resolved = self.alias.get(ref, "Object")
            if resolved and resolved.startswith("List<") and imports is not None:
                imports.add("java.util.List")
            return resolved or "Object"

        kind = schema.get("type")
        fmt = schema.get("format")

        if kind == "array":
            if imports is not None:
                imports.add("java.util.List")
            return "List<%s>" % self.of(schema.get("items", {}), imports, boxed=True)

        if kind == "string":
            if fmt == "date-time":
                if imports is not None:
                    imports.add("java.time.OffsetDateTime")
                return "OffsetDateTime"
            return "String"

        if kind == "integer":
            return "Long" if fmt in ("int64", "uint32", "uint64") else "Integer"

        if kind == "number":
            return "Double"

        if kind == "boolean":
            return "Boolean"

        if kind == "file":
            return "byte[]"

        if kind == "object" or "properties" in schema or "additionalProperties" in schema:
            if imports is not None:
                imports.add("java.util.Map")
            return "Map<String, Object>"

        return "Object"


# --------------------------------------------------------------------------- 번역 사전

class Ko:
    def __init__(self, data):
        self.ops = data.get("operations", {})
        self.params = data.get("params", {})
        self.fields = data.get("fields", {})
        self.tags = data.get("tags", {})
        self.models = data.get("models", {})
        self.missing = {"operations": set(), "params": set(), "tags": set(), "models": set()}

    def op(self, operation_id, key):
        entry = self.ops.get(operation_id)
        if not entry:
            self.missing["operations"].add(operation_id)
            return None
        return entry.get(key)

    def param(self, description):
        value = self.params.get(description)
        if not value:
            self.missing["params"].add(description)
        return value

    def field(self, definition, field):
        return self.fields.get("%s.%s" % (definition, field))

    def tag(self, name, key):
        entry = self.tags.get(name)
        if not entry:
            self.missing["tags"].add(name)
            return None
        return entry.get(key)

    def model(self, name):
        value = self.models.get(name)
        if not value:
            self.missing["models"].add(name)
        return value


# --------------------------------------------------------------------------- DTO 생성

def render_model(name, schema, types, ko):
    imports = set()
    props = schema.get("properties") or {}
    required = set(schema.get("required") or [])

    fields = []
    for raw_name, prop in props.items():
        java_type = types.of(prop, imports)
        java_name = camel(raw_name)
        fields.append({
            "raw": raw_name,
            "name": java_name,
            "type": java_type,
            "desc": prop.get("description"),
            "ko": ko.field(name, raw_name),
            "example": prop.get("example"),
            "required": raw_name in required,
            "annotate": java_name != raw_name,
        })
    if any(f["annotate"] for f in fields):
        imports.add("com.fasterxml.jackson.annotation.JsonProperty")

    out = ["package %s;" % MODEL_PKG, ""]
    for imp in sorted(imports):
        out.append("import %s;" % imp)
    if imports:
        out.append("")
    out.append("import lombok.Data;")
    out.append("")

    # 클래스 Javadoc
    out.append("/**")
    out.append(" * {@code %s}." % name)
    for text in (schema.get("description"), ko.model(name)):
        if not text:
            continue
        out.append(" *")
        for lidx, line in enumerate(wrap(esc(text))):
            out.append(" * %s%s" % ("<p>" if lidx == 0 else "", line))
    out.append(" */")
    out.append("@Data")
    out.append("public class %s {" % name)

    for idx, f in enumerate(fields):
        if idx:
            out.append("")

        # 담을 내용이 있는 단락만 모은다. 하나뿐이면 한 줄 주석으로 줄여 잡음을 없앤다.
        paragraphs = []
        if f["desc"]:
            paragraphs.append(wrap(esc(f["desc"])))
        if f["ko"]:
            paragraphs.append(wrap(esc(f["ko"])))
        if f["example"] is not None:
            example = json.dumps(f["example"], ensure_ascii=False)
            if len(example) <= 160:
                paragraphs.append(["예: {@code %s}" % esc(example)])

        if not paragraphs:
            out.append("    /** {@code %s} */" % esc(f["raw"]))
        elif len(paragraphs) == 1 and len(paragraphs[0]) == 1:
            out.append("    /** %s */" % paragraphs[0][0])
        else:
            out.append("    /**")
            for pidx, para in enumerate(paragraphs):
                if pidx:
                    out.append("     *")
                for lidx, line in enumerate(para):
                    prefix = "<p>" if pidx and lidx == 0 else ""
                    out.append("     * %s%s" % (prefix, line))
            out.append("     */")

        if f["annotate"]:
            out.append('    @JsonProperty("%s")' % f["raw"])
        out.append("    private %s %s;" % (f["type"], f["name"]))

    out.append("}")
    return "\n".join(out) + "\n"


# --------------------------------------------------------------------------- Feign 클라이언트 생성

SPRING_ANNOTATION = {
    "get": "GetMapping",
    "post": "PostMapping",
    "put": "PutMapping",
    "patch": "PatchMapping",
    "delete": "DeleteMapping",
}


def method_name(operation_id):
    return camel(operation_id)


def render_client(tag, operations, types, ko):
    imports = set()
    imports.add("org.springframework.cloud.openfeign.FeignClient")

    body = []
    for op in operations:
        body.extend(render_operation(op, types, ko, imports))

    class_name = pascal(tag) + "Client"

    out = ["package %s;" % API_PKG, ""]
    java_imports = sorted(i for i in imports if i.startswith("java."))
    other_imports = sorted(i for i in imports if not i.startswith("java."))
    for imp in java_imports:
        out.append("import %s;" % imp)
    if java_imports:
        out.append("")
    for imp in other_imports:
        out.append("import %s;" % imp)
    out.append("")
    out.append("import %s.config.CyberVisionFeignConfig;" % BASE_PKG)
    out.append("")

    out.append("/**")
    out.append(" * Cisco Cyber Vision Classic API - {@code %s}." % tag)
    ko_title = ko.tag(tag, "title")
    ko_desc = ko.tag(tag, "desc")
    if ko_title or ko_desc:
        out.append(" *")
        if ko_title:
            out.append(" * <p><b>%s</b>" % esc(ko_title))
        if ko_desc:
            for line in wrap(esc(ko_desc)):
                out.append(" * %s" % line)
    out.append(" *")
    out.append(" * <p>오퍼레이션 %d개." % len(operations))
    out.append(" *")
    out.append(" * <p>{@code url} 은 자리표시자다. Center 주소는 DB 에서 오는 런타임 값이라")
    out.append(" * 요청 직전에 {@code DynamicUrlClient} 가 실제 주소로 갈아끼운다.")
    out.append(" */")
    out.append('@FeignClient(')
    out.append('        name = "cyberVision%s",' % pascal(tag))
    out.append('        url = "http://cyber-vision.invalid",')
    out.append('        path = "${cyber-vision.base-path:/api/3.0}",')
    out.append('        configuration = CyberVisionFeignConfig.class)')
    out.append("public interface %s {" % class_name)
    out.extend(body)
    out.append("}")
    return class_name, "\n".join(out) + "\n"


def render_operation(op, types, ko, imports):
    spec_op = op["op"]
    http = op["method"]
    path = op["path"]
    operation_id = spec_op["operationId"]

    params = [p for p in spec_op.get("parameters", [])
              if not (p["in"] == "header" and p["name"] in GLOBAL_HEADERS)]

    # ---- 반환 타입
    success = spec_op["responses"].get("200") or spec_op["responses"].get("201") or {}
    schema = success.get("schema")
    inline = INLINE_NAMES.get(operation_id + ":response")
    if schema is None:
        ret = "void"
    elif inline:
        ret = inline
        imports.add("%s.%s" % (MODEL_PKG, inline))
    else:
        ret = types.of(schema, imports)
        _import_model(ret, imports)

    # ---- 파라미터
    sig = []
    doc_params = []
    for p in params:
        where = p["in"]
        desc = p.get("description")
        ko_desc = ko.param(desc) if desc else None

        if where == "body":
            inline_body = INLINE_NAMES.get(operation_id + ":body")
            if inline_body:
                ptype = "List<%s>" % inline_body if p.get("schema", {}).get("type") == "array" else inline_body
                if ptype.startswith("List<"):
                    imports.add("java.util.List")
                imports.add("%s.%s" % (MODEL_PKG, inline_body))
            else:
                ptype = types.of(p.get("schema", {}), imports)
                _import_model(ptype, imports)
            pname = camel(p.get("name") or "body")
            imports.add("org.springframework.web.bind.annotation.RequestBody")
            sig.append(("@RequestBody %s %s" % (ptype, pname), p.get("required", False)))

        elif where == "path":
            ptype = types.of(p, imports)
            pname = camel(p["name"])
            imports.add("org.springframework.web.bind.annotation.PathVariable")
            sig.append(('@PathVariable("%s") %s %s' % (p["name"], ptype, pname), True))

        elif where == "query":
            ptype = types.of(p, imports)
            pname = camel(p["name"])
            imports.add("org.springframework.web.bind.annotation.RequestParam")
            required = "true" if p.get("required") else "false"
            sig.append(('@RequestParam(name = "%s", required = %s) %s %s'
                        % (p["name"], required, ptype, pname), p.get("required", False)))

        elif where == "header":
            ptype = types.of(p, imports)
            pname = camel(p["name"])
            imports.add("org.springframework.web.bind.annotation.RequestHeader")
            sig.append(('@RequestHeader("%s") %s %s' % (p["name"], ptype, pname), p.get("required", False)))

        elif where == "formData":
            imports.add("org.springframework.web.multipart.MultipartFile")
            pname = camel(p["name"])
            imports.add("org.springframework.web.bind.annotation.RequestPart")
            sig.append(('@RequestPart("%s") MultipartFile %s' % (p["name"], pname), True))
            ptype = "MultipartFile"
        else:
            continue

        doc_params.append((camel(p.get("name") or "body"), desc, ko_desc))

    annotation = SPRING_ANNOTATION[http]
    imports.add("org.springframework.web.bind.annotation." + annotation)

    # ---- Javadoc
    lines = [""]
    lines.append("    /**")
    for line in wrap(esc(spec_op["summary"])):
        lines.append("     * %s" % line)

    # 요약 문장 뒤에 오는 단락들. 첫 줄에 <p> 를 붙여야 Javadoc 에서 문단이 나뉜다.
    description = spec_op.get("description")
    if description and description.strip().rstrip(".").lower() == spec_op["summary"].strip().rstrip(".").lower():
        description = None
    for text in (description, ko.op(operation_id, "summary"), ko.op(operation_id, "note")):
        if not text:
            continue
        lines.append("     *")
        for lidx, line in enumerate(wrap(esc(text))):
            lines.append("     * %s%s" % ("<p>" if lidx == 0 else "", line))

    lines.append("     *")
    lines.append("     * <p>{@code %s %s}" % (http.upper(), path))

    # 응답 코드 표
    codes = sorted(k for k in spec_op["responses"] if k.isdigit() and k not in ("200", "201"))
    if codes:
        lines.append("     *")
        for code in codes:
            lines.append("     * <p>{@code %s} - %s" % (code, esc(spec_op["responses"][code].get("description", ""))))

    if doc_params or ret != "void":
        lines.append("     *")

    for pname, desc, ko_desc in doc_params:
        text = esc(desc or "")
        if ko_desc:
            text = "%s (%s)" % (text, esc(ko_desc)) if text else esc(ko_desc)
        lines.append("     * @param %s %s" % (pname, text))

    if ret != "void":
        ko_returns = ko.op(operation_id, "returns")
        returns_text = esc(success.get("description") or "success")
        if ko_returns:
            returns_text = esc(ko_returns)
        lines.append("     * @return %s" % returns_text)
    lines.append("     */")

    # ---- 시그니처
    mapping_path = path if path.startswith("/") else "/" + path
    # 스펙 최상위 consumes 는 application/json 이다. 오퍼레이션이 이를 덮어쓴 경우에만 명시한다.
    consumes = spec_op.get("consumes")
    if consumes:
        lines.append('    @%s(value = "%s", consumes = "%s")' % (annotation, mapping_path, consumes[0]))
    else:
        lines.append('    @%s("%s")' % (annotation, mapping_path))

    name = method_name(operation_id)
    if not sig:
        lines.append("    %s %s();" % (ret, name))
    elif len(sig) == 1:
        lines.append("    %s %s(%s);" % (ret, name, sig[0][0]))
    else:
        lines.append("    %s %s(" % (ret, name))
        for i, (text, _) in enumerate(sig):
            tail = "," if i < len(sig) - 1 else ");"
            lines.append("            %s%s" % (text, tail))
    return lines


def _import_model(java_type, imports):
    """DTO 클래스명이 들어간 타입이면 model 패키지 import 를 추가한다."""
    for token in re.findall(r"[A-Za-z_][A-Za-z0-9_]*", java_type):
        if token in ("List", "Map", "String", "Object", "Long", "Integer", "Double", "Boolean",
                     "OffsetDateTime", "byte", "void"):
            continue
        imports.add("%s.%s" % (MODEL_PKG, token))


# --------------------------------------------------------------------------- 인라인 DTO

def inline_schemas(spec):
    """INLINE_NAMES 에 등록된 인라인 스키마를 이름 있는 정의처럼 끌어올린다."""
    found = {}
    for path, item in spec["paths"].items():
        for method, op in item.items():
            if method.lower() not in HTTP_METHODS:
                continue
            oid = op["operationId"]

            name = INLINE_NAMES.get(oid + ":body")
            if name and name not in found:
                for p in op.get("parameters", []):
                    if p["in"] == "body":
                        schema = p.get("schema", {})
                        if schema.get("type") == "array":
                            schema = schema.get("items", {})
                        if "properties" in schema:
                            found[name] = dict(schema, type="object")

            name = INLINE_NAMES.get(oid + ":response")
            if name and name not in found:
                schema = (op["responses"].get("200") or {}).get("schema") or {}
                if "properties" in schema and not schema.get("additionalProperties"):
                    found[name] = dict(schema, type="object")
    return found


# --------------------------------------------------------------------------- main

def write_package_info(model_count, by_tag, total_ops):
    """생성 디렉터리마다 package-info.java 를 남긴다. 디렉터리가 통째로 재생성되므로 여기서 함께 만든다."""
    with open(os.path.join(MODEL_DIR, "package-info.java"), "w", encoding="utf-8") as fp:
        fp.write("""/**
 * Cisco Cyber Vision Classic API 의 요청/응답 DTO %d개.
 *
 * <p>{@code src/main/resources/openapi/cyber-vision-3.0.json} 의 {@code definitions} 에서
 * {@code tools/gen.py} 가 생성한다. <b>직접 수정하지 말 것</b> — 재생성 시 사라진다.
 *
 * <p>필드 이름이 JSON 키와 다른 경우에만 {@code @JsonProperty} 가 붙는다.
 * 스펙에 이름만 있고 설명이 없는 필드가 많아, 필드 주석은 원문에 설명이 있을 때만 내용을 담는다.
 */
package %s;
""" % (model_count, MODEL_PKG))

    tag_lines = "\n".join(
        " * <li>{@link %s.%sClient} - %d개</li>" % (API_PKG, pascal(tag), len(ops))
        for tag, ops in sorted(by_tag.items())
    )
    with open(os.path.join(API_DIR, "package-info.java"), "w", encoding="utf-8") as fp:
        fp.write("""/**
 * Cisco Cyber Vision Classic API 를 호출하는 Feign 클라이언트 %d개 (오퍼레이션 %d개).
 *
 * <p>스펙의 태그 하나가 클라이언트 인터페이스 하나에 대응한다.
 *
 * <ul>
%s
 * </ul>
 *
 * <p>{@code tools/gen.py} 가 생성한다. <b>직접 수정하지 말 것</b> — 재생성 시 사라진다.
 *
 * <p>인증 토큰({@code x-token-id})과 {@code Accept-Language} 헤더는 각 메서드가 아니라
 * {@link %s.config.CyberVisionFeignConfig} 의 인터셉터가 일괄로 붙인다.
 */
package %s;
""" % (len(by_tag), total_ops, tag_lines, BASE_PKG, API_PKG))


def main():
    spec = load_json(SPEC)
    ko = Ko(load_json(KO, default={}))

    # 인라인 스키마를 정의 목록에 합류시킨 뒤 타입 해석기를 만든다.
    extra = inline_schemas(spec)
    spec["definitions"].update(extra)
    types = Types(spec)

    for directory in (MODEL_DIR, API_DIR):
        if os.path.isdir(directory):
            shutil.rmtree(directory)
        os.makedirs(directory)

    # ---- DTO
    for name in sorted(types.classes):
        source = render_model(name, spec["definitions"][name], types, ko)
        with open(os.path.join(MODEL_DIR, name + ".java"), "w", encoding="utf-8") as fp:
            fp.write(source)

    # ---- 태그별 클라이언트
    by_tag = {}
    for path, item in spec["paths"].items():
        for method, op in item.items():
            if method.lower() not in HTTP_METHODS:
                continue
            for tag in op.get("tags", ["Misc"]):
                by_tag.setdefault(tag, []).append({"path": path, "method": method.lower(), "op": op})

    for tag in by_tag:
        by_tag[tag].sort(key=lambda o: (o["path"], HTTP_METHODS.index(o["method"])))

    for tag, operations in sorted(by_tag.items()):
        class_name, source = render_client(tag, operations, types, ko)
        with open(os.path.join(API_DIR, class_name + ".java"), "w", encoding="utf-8") as fp:
            fp.write(source)

    total_ops = sum(len(v) for v in by_tag.values())
    write_package_info(len(types.classes), by_tag, total_ops)
    print("DTO        : %d" % len(types.classes))
    print("클라이언트 : %d (오퍼레이션 %d)" % (len(by_tag), total_ops))
    for key in ("tags", "operations", "params", "models"):
        missing = ko.missing[key]
        if missing:
            print("번역 누락 %-10s: %d" % (key, len(missing)))


if __name__ == "__main__":
    main()

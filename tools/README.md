# Cyber Vision API 클라이언트 생성

`src/main/java/com/cvGateway/cvGateway/cybervision/{api,model}` 아래 코드는 **전부 자동 생성물**이다.
직접 고치지 말고 생성기와 번역 사전을 고친 뒤 다시 생성한다.

## 재생성

```bash
python tools/gen.py
./gradlew test --tests '*CyberVisionClientContractTest*'
```

생성기는 `api`/`model` 디렉터리를 통째로 지우고 다시 만든다. 그 안에 손으로 쓴 파일을 두면 사라진다.

## 구성

| 파일 | 역할 |
|---|---|
| `src/main/resources/openapi/cyber-vision-3.0.json` | Cisco 배포 원본 스펙 (Swagger 2.0) |
| `tools/gen.py` | 스펙 → Java 생성기 |
| `tools/ko.json` | 한글 번역 사전 |

출력물:

- `cybervision/api/*Client.java` — 스펙의 태그 1개당 Feign 인터페이스 1개 (26개, 오퍼레이션 182개)
- `cybervision/model/*.java` — DTO 211개
- `cybervision/config/*` — **자동 생성 아님.** 인증·에러 처리 설정이라 손으로 관리한다.

## 스펙 원본

```
https://pubhub.devnetcloud.com/media/cisco-cyber-vision-5-5-classic-api/docs/reference/openapi.json
```

Cyber Vision 버전이 올라가면 이 파일을 새로 받아 덮어쓰고 재생성한다.
`gen.py` 가 번역이 없는 항목을 개수로 알려주므로, 그만큼 `ko.json` 을 채우면 된다.

## 번역 사전 (`ko.json`)

| 키 | 대상 | 매칭 기준 |
|---|---|---|
| `tags` | 클라이언트 인터페이스 주석 | 스펙 태그 이름 |
| `operations` | 메서드 주석 | `operationId` |
| `params` | `@param` 주석 | 파라미터 설명 **원문 전체** |
| `models` | DTO 클래스 주석 | 정의 이름 |
| `fields` | DTO 필드 주석 | `정의이름.필드명` |

`params` 는 원문 문자열을 그대로 키로 쓴다. 스펙에서 문구가 한 글자라도 바뀌면 매칭이 끊기고
누락으로 잡히니, 재생성 후 출력되는 누락 개수를 확인한다.

`operations` 항목은 `summary`(무엇을 하는가), `note`(주의사항, 생략 가능),
`returns`(`@return` 문구) 세 가지를 받는다.

## 스펙 해석에서 손댄 부분

생성기가 스펙을 그대로 옮기지 않고 판단을 넣은 지점이다. 재생성해도 유지된다.

- **`Accept-Language` 헤더** — 스펙은 27개 오퍼레이션에서 파라미터로 받지만, 메서드 시그니처에
  넣지 않고 `CyberVisionFeignConfig` 인터셉터가 `cyber-vision.accept-language` 값으로 일괄 적용한다.
- **타입 별칭** — `type: string` 정의 10개(값 목록이 비어 있음)는 `String` 으로, `type: array`
  정의 25개는 `List<원소>` 로 인라인 처리한다. 이 35개는 클래스를 만들지 않는다.
- **이름 없는 인라인 스키마** — `gen.py` 의 `INLINE_NAMES` 에서 이름을 부여해 DTO 로 끌어올린다.
  `additionalProperties` 가 열려 있는 스키마(파라미터, 배포 JWT)는 `Map<String, Object>` 로 둔다.
- **바이너리 응답** — `downloadReports`, `getSensorPackaging` 은 `byte[]` 를 반환한다.
  대용량이면 메모리에 전부 올라온다.

## 의존성 주의

`build.gradle` 의 `io.github.openfeign:feign-hc5` 를 빼면 안 된다.
Feign 기본 클라이언트는 `HttpURLConnection` 기반이라 **PATCH 를 지원하지 않는데**,
이 API 에는 PATCH 오퍼레이션이 6개 있다.

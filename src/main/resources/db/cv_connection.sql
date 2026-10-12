-- Cyber Vision 접속 정보 보관 테이블 (MS SQL Server)
--
-- 이 프로젝트는 spring.jpa.hibernate.ddl-auto=validate 로 동작한다.
-- 즉 애플리케이션이 테이블을 만들지 않으므로, 기동 전에 이 스크립트를 먼저 실행해야 한다.
-- 테이블이 없으면 기동 단계에서 스키마 검증에 실패한다.
--
--   sqlcmd -S <host> -d dsaWatcher -U dw -P dw -i cv_connection.sql
--
-- token_value 는 암호화 없이 저장된다. 이 테이블의 조회 권한이 곧 Cyber Vision 접근 권한이므로
-- 필요한 계정에만 권한을 주는 것이 좋다.

IF NOT EXISTS (SELECT 1 FROM sys.tables WHERE name = 'cv_connection')
BEGIN
    CREATE TABLE dbo.cv_connection
    (
        -- 외부 시스템이 부여하는 식별자. API 요청 본문의 id 가 그대로 들어온다.
        -- 숫자를 보내도 문자열로 받는다. 식별자 형식이 바뀌어도 스키마를 건드리지 않기 위함이다.
        id          VARCHAR(100)  NOT NULL,

        -- Center 베이스 URL. 스킴 포함, 뒤 슬래시 없음. 예: https://192.168.0.10
        center_url  VARCHAR(255)  NOT NULL,

        -- 대상 스위치 이름.
        switch_name VARCHAR(255)  NULL,

        -- 버전 문자열.
        version     VARCHAR(50)   NULL,

        -- Cyber Vision 관리 UI 에서 발급한 토큰 원문.
        -- 실제 값은 50자 안팎이지만 형식 변경에 여유를 두고 512 로 잡았다.
        token_value VARCHAR(512)  NOT NULL,

        -- 마지막으로 주입된 시각 (UTC).
        -- 행이 여러 개일 때 기동 시 어느 것을 쓸지 정하는 기준이 된다.
        updated_at  DATETIME2(6)  NOT NULL,

        CONSTRAINT pk_cv_connection PRIMARY KEY (id)
    );

    -- 기동 시 가장 최근 행을 찾는 조회(findFirstByOrderByUpdatedAtDesc)를 위한 인덱스.
    CREATE INDEX ix_cv_connection_updated_at ON dbo.cv_connection (updated_at DESC);
END
GO

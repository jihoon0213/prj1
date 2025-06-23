CREATE DATABASE prj1;
USE prj1;

# 게시물 테이블
CREATE TABLE board
(
    id         INT AUTO_INCREMENT NOT NULL,
    title      VARCHAR(500)       NOT NULL,
    content    VARCHAR(10000)     NOT NULL,
    writer     VARCHAR(120)       NOT NULL,
    created_at datetime           NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_board PRIMARY KEY (id)
);

# 페이징 용 글 복사
INSERT INTO board
    (title, content, writer)
SELECT title, content, writer
FROM board;
SELECT COUNT(*)
FROM board;


# 회원 테이블
CREATE TABLE member
(
    id         VARCHAR(100)        NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    nick_name  VARCHAR(100) UNIQUE NOT NULL,
    info       VARCHAR(10000)      NULL,
    created_at datetime            NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_member PRIMARY KEY (id)
);
DROP TABLE member;

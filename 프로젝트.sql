ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;

CREATE USER PROJECT_HYE IDENTIFIED BY kh1234;

GRANT CONNECT, RESOURCE, CREATE VIEW TO PROJECT_HYE;

ALTER USER PROJECT_HYE DEFAULT
TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;

---------------------------------------------------------
CREATE TABLE "GRADE"(
	GRADE_NO NUMBER PRIMARY KEY,
	GRADE_NM VARCHAR2(30) NOT NULL
);

COMMENT ON COLUMN "GRADE".GRADE_NO IS '등급 번호';
COMMENT ON COLUMN "GRADE".GRADE_NM IS '등급명';

INSERT INTO "GRADE" VALUES(10,'씨앗');
INSERT INTO "GRADE" VALUES(20,'새싹');
INSERT INTO "GRADE" VALUES(30,'잎새');
INSERT INTO "GRADE" VALUES(40,'가지');
INSERT INTO "GRADE" VALUES(50,'열매');
INSERT INTO "GRADE" VALUES(60,'나무');

CREATE TABLE "MEMBER"(
	MEMBER_NO NUMBER PRIMARY KEY,
	MEMBER_ID VARCHAR2(30) NOT NULL,
	MEMBER_PW VARCHAR2(30) NOT NULL,
	MEMBER_NM VARCHAR2(30) NOT NULL,
	MEMBER_GENDER CHAR(1) CHECK(MEMBER_GENDER IN ('M','F')),
	ENROLL_DATE DATE DEFAULT SYSDATE,
	SECESSION_FL CHAR(1) DEFAULT 'N' CHECK(SECESSION_FL IN ('Y','N')),
	GRADE_NO NUMBER DEFAULT 10 CONSTRAINT MEMBER_GRADE_FK REFERENCES "GRADE" 
);



COMMENT ON COLUMN "MEMBER".MEMBER_NO IS '회원 번호';
COMMENT ON COLUMN "MEMBER".MEMBER_ID IS '회원 아이디';
COMMENT ON COLUMN "MEMBER".MEMBER_PW IS '회원 비밀 번호';
COMMENT ON COLUMN "MEMBER".MEMBER_NM IS '회원 이름';
COMMENT ON COLUMN "MEMBER".MEMBER_GENDER IS '회원 성별';
COMMENT ON COLUMN "MEMBER".ENROLL_DATE IS '회원 가입일';
COMMENT ON COLUMN "MEMBER".SECESSION_FL IS '회원 탈퇴 여부';
COMMENT ON COLUMN "MEMBER".GRADE_NO IS '회원 등급 번호';

COMMIT;

ALTER TABLE "MEMBER" ADD (POST_CNT NUMBER DEFAULT 0);

SELECT * FROM MEMBER;

COMMENT ON COLUMN "MEMBER".POST_CNT IS '게시글 수';

CREATE TABLE BOARD(
	BOARD_NO NUMBER PRIMARY KEY,
	BOARD_NM VARCHAR2(100) NOT NULL,
	GRADE_NO NUMBER CONSTRAINT BOARD_GRADE_FK REFERENCES "GRADE" 
);

COMMENT ON COLUMN BOARD.BOARD_NO IS '게시판 번호';
COMMENT ON COLUMN BOARD.BOARD_NM IS '게시판 이름';
COMMENT ON COLUMN BOARD.GRADE_NO IS '게시판 등급 번호';

CREATE TABLE POST(
	POST_NO NUMBER PRIMARY KEY,
	POST_TITLE VARCHAR2(500) NOT NULL,
	POST_CONTENT VARCHAR2(4000) NOT NULL,
	CREATE_DT DATE DEFAULT SYSDATE,
	READ_COUNT NUMBER DEFAULT 0,
	DELETE_FL CHAR(1) DEFAULT 'N' CHECK(DELETE_FL IN ('N','Y')),
	MEMBER_NO NUMBER CONSTRAINT POST_WRITER_FK REFERENCES "MEMBER",
	BOARD_NO NUMBER CONSTRAINT BOARD_NO_FK REFERENCES BOARD
);

COMMENT ON COLUMN POST.POST_NO IS '게시글 번호';
COMMENT ON COLUMN POST.POST_TITLE IS '게시글 제목';
COMMENT ON COLUMN POST.POST_CONTENT IS '게시글 내용';
COMMENT ON COLUMN POST.CREATE_DT IS '게시글 작성일';
COMMENT ON COLUMN POST.READ_COUNT IS '게시글 조회수';
COMMENT ON COLUMN POST.DELETE_FL IS '게시글 삭제 여부';
COMMENT ON COLUMN POST.MEMBER_NO IS '게시글 작성 회원 번호';
COMMENT ON COLUMN POST.BOARD_NO IS '게시판 번호';

COMMIT;

CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE;
CREATE SEQUENCE SEQ_POST_NO NOCACHE;

INSERT INTO "MEMBER" 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user01','pass01','유저일','M', DEFAULT, DEFAULT, DEFAULT, DEFAULT);
INSERT INTO "MEMBER" 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user02','pass02','유저이','M', DEFAULT, DEFAULT, DEFAULT, DEFAULT);
INSERT INTO "MEMBER" 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user03','pass03','유저삼','F', DEFAULT, DEFAULT, DEFAULT, DEFAULT);
INSERT INTO "MEMBER" 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user04','pass04','유저사','M', DEFAULT, DEFAULT, DEFAULT, DEFAULT);
INSERT INTO "MEMBER" 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user05','pass05','유저오','F', DEFAULT, DEFAULT, DEFAULT, DEFAULT);
INSERT INTO "MEMBER" 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user06','pass06','유저육','F', DEFAULT, DEFAULT, DEFAULT, DEFAULT);

SELECT * FROM "MEMBER";

COMMIT;
	
SELECT MEMBER_NO, MEMBER_ID, MEMBER_NM, MEMBER_GENDER, ENROLL_DATE, GRADE_NM
FROM MEMBER
JOIN GRADE USING(GRADE_NO)
WHERE MEMBER_NO=?
AND SECESSION_FL='N';

UPDATE MEMBER SET 
SECESSION_FL='N'
WHERE MEMBER_NO=1;

COMMIT;

-- SYS 관리자 계정
ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;

-- 사용자 계정 생성
CREATE USER member_hye IDENTIFIED BY member1234;

-- 생성한 사용자 계정에 권한 부여
GRANT CONNECT, RESOURCE, CREATE VIEW TO member_hye;

-- 테이블 스페이스 할당
ALTER USER member_hye DEFAULT
TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;

--------------------------------------------------------

-- member_hye 계정

-- 회원 테이블
-- 회원번호, 아이디, 비밀번호, 이름, 성별, 가입일, 탈퇴여부
CREATE TABLE MEMBER(
	MEMBER_NO NUMBER PRIMARY KEY,
	MEMBER_ID VARCHAR2(30) NOT NULL,
	MEMBER_PW VARCHAR2(30) NOT NULL,
	MEMBER_NM VARCHAR2(30) NOT NULL,
	MEMBER_GENDER CHAR(1) CHECK(MEMBER_GENDER IN ('M','F')),
	ENROLL_DATE DATE DEFAULT SYSDATE,
	SECESSION_FL CHAR(1) DEFAULT 'N' CHECK(SECESSION_FL IN ('Y','N'))
);

COMMENT ON COLUMN "MEMBER".MEMBER_NO IS '회원 번호';
COMMENT ON COLUMN "MEMBER".MEMBER_ID IS '회원 아이디';
COMMENT ON COLUMN "MEMBER".MEMBER_PW IS '회원 비밀번호';
COMMENT ON COLUMN "MEMBER".MEMBER_NM IS '회원 이름';
COMMENT ON COLUMN "MEMBER".MEMBER_GENDER IS '회원 성별';
COMMENT ON COLUMN "MEMBER".ENROLL_DATE IS '회원 가입입';
COMMENT ON COLUMN "MEMBER".SECESSION_FL IS '탈퇴여부(Y/N)';

-- 회원 번호 시퀀스 생성
CREATE SEQUENCE SEQ_MEMBER_NO
START WITH 1
INCREMENT BY 1
NOCYCLE 
NOCACHE;

-- 회원 가입 INSERT 
INSERT INTO MEMBER 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user01','pass01','유저일','M', DEFAULT, DEFAULT);
INSERT INTO MEMBER 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user02','pass02','유저이','F', DEFAULT, DEFAULT);
INSERT INTO MEMBER 
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'user03','pass03','유저삼','F', DEFAULT, DEFAULT);

SELECT * FROM MEMBER;

COMMIT;

-- 아이디 중복 확인
-- (중복되는 아이디가 입력되어도 탈퇴한 계정이면 중복 X)
SELECT COUNT(*) FROM MEMBER
WHERE MEMBER_ID='user04'
AND SECESSION_FL='N';
--> 결과가 0이면 중복 X, 결과가 1이면 중복 O

SELECT * FROM MEMBER;

SELECT MEMBER_NO, MEMBER_ID, MEMBER_NM, MEMBER_GENDER,
	TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') ENROLL_DATE
FROM MEMBER
WHERE MEMBER_ID='user02'
AND MEMBER_PW='pass02'
AND SECESSION_FL='N';

-- 회원 목록 조회(아이디, 이름, 성별)
-- 탈퇴 회원 미포함
-- 가입일 내림차순
   --> MEMBER_NO 내림차순 (나중에 가입한 회원의 번호가 더 큼)
SELECT MEMBER_ID, MEMBER_NM, MEMBER_GENDER
FROM MEMBER
WHERE SECESSION_FL ='N'
ORDER BY MEMBER_NO DESC;

-- 회원 정보 수정(이름, 성별)
UPDATE MEMBER SET
MEMBER_NM='바꾼이름', --입력
MEMBER_GENDER='바꾼성별' --입력
WHERE MEMBER_NO=? ; --loginMember.getMemberNo();


-- 비밀번호 변경
UPDATE MEMBER SET
MEMBER_PW = '바꾼비번' --입력
WHERE MEMBER_NO = ? --loginMember.getMemberNo()
AND MEMBER_PW = '현재비밀번호' ;

-- 회원 탈퇴 (SECESSION_FL 컬럼의 값을 'Y'로 변경)
UPDATE MEMBER SET
SECESSION_FL ='Y'
WHERE MEMBER_NO= ?
AND MEMBER_PW = '현재비밀번호'

UPDATE MEMBER SET
SECESSION_FL ='N'
WHERE MEMBER_NO= 1;

SELECT * FROM MEMBER;

COMMIT;

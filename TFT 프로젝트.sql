USE lolchess;

CREATE TABLE IF NOT EXISTS MEMBER(
    M_ID VARCHAR(20) BINARY NOT NULL PRIMARY KEY,
    M_PWD VARCHAR(100) NOT NULL,
    M_NAME VARCHAR(10) NOT NULL,
    M_BIRTH DATE NOT NULL,
    M_ADDR VARCHAR(50) NOT NULL,
    M_PHONE VARCHAR(13) NOT NULL
);

INSERT INTO MEMBER(M_ID,M_PWD,M_NAME,M_BIRTH,M_ADDR,M_PHONE)
VALUES('검은깨우유','1111','sj','20021015','부천','010-1111-1111');
INSERT INTO MEMBER(M_ID,M_PWD,M_NAME,M_BIRTH,M_ADDR,M_PHONE)
VALUES('들깨우유','1111','dh','20020408','인천','010-2222-2222');
INSERT INTO MEMBER(M_ID,M_PWD,M_NAME,M_BIRTH,M_ADDR,M_PHONE)
VALUES('롤체지지','1111','gg','20231231','서울','010-0000-0000');
INSERT INTO MEMBER(M_ID,M_PWD,M_NAME,M_BIRTH,M_ADDR,M_PHONE)
VALUES('개발자_코그모','1111','dc','20230101','서울','010-0000-0000');
COMMIT;

CREATE TABLE IF NOT EXISTS BOARD(
	B_NUM INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    B_TITLE VARCHAR(50) BINARY NOT NULL,
    B_CONTENTS TEXT BINARY NOT NULL,
    B_ID VARCHAR(20) BINARY NOT NULL,
    B_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
    B_VIEWS INT DEFAULT 0 NOT NULL,
    CONSTRAINT FK_B_ID FOREIGN KEY(B_ID) REFERENCES MEMBER(M_ID)
);

INSERT INTO BOARD
VALUES(NULL,'13.22 전략적 팀 전투 패치노트','13.22 전략적 팀 전투 패치 노트','롤체지지',DEFAULT,DEFAULT);
INSERT INTO BOARD
VALUES(NULL,'13.21 전략적 팀 전투 패치노트','13.21 전략적 팀 전투 패치 노트','롤체지지',DEFAULT,DEFAULT);
INSERT INTO BOARD
VALUES(NULL,'13.20 전략적 팀 전투 패치노트','13.20 전략적 팀 전투 패치 노트','롤체지지',DEFAULT,DEFAULT);
COMMIT;

DROP TABLE reply;
CREATE TABLE IF NOT EXISTS REPLY(
    R_NUM INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    R_BNUM INT NOT NULL,
    R_CONTENTS VARCHAR(200) NOT NULL,
    R_ID VARCHAR(20) BINARY NOT NULL,
    R_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_R_BNUM FOREIGN KEY(R_BNUM) REFERENCES BOARD(B_NUM),
    CONSTRAINT FK_R_ID FOREIGN KEY(R_ID) REFERENCES MEMBER(M_ID)
);

INSERT INTO REPLY VALUES(NULL,1,'댓글1','들깨우유',DEFAULT);
INSERT INTO REPLY VALUES(NULL,2,'댓글2','검은깨우유',DEFAULT);
INSERT INTO REPLY VALUES(NULL,3,'댓글3','들깨우유',DEFAULT); 
COMMIT;

CREATE OR REPLACE VIEW MINFO AS
SELECT M.M_ID,M.M_NAME
FROM MEMBER M;

CREATE OR REPLACE VIEW RLIST AS
SELECT R_BNUM, R_NUM, R_CONTENTS, R_DATE, R_ID
FROM REPLY
ORDER BY R_DATE DESC;

CREATE OR REPLACE VIEW BLIST AS
SELECT B.B_NUM,
       B.B_TITLE,
       B.B_CONTENTS,
       B.B_ID,
       M.M_NAME,
       B.B_DATE,
       B.B_VIEWS 
FROM BOARD B INNER JOIN MEMBER M
ON B.B_ID=M.M_ID
ORDER BY B.B_DATE DESC;
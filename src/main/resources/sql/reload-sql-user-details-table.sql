-- in sql don't reset pk
-- it's reset by TRUNCATE TABLE <table>
TRUNCATE TABLE TTKNP.A_APP.USERS_DETAIL;

INSERT INTO TTKNP.A_APP.USERS_DETAIL (ID, FIRSTNAME, LASTNAME, AGE, EMAIL, PHONE) VALUES (1, N'Alex', N'Ryder', 52, N'Alex@hotmail.com', N'0898989898');
INSERT INTO TTKNP.A_APP.USERS_DETAIL (ID, FIRSTNAME, LASTNAME, AGE, EMAIL, PHONE) VALUES (2, N'Jack', N'Ryder', 43, N'Jack@hotmail.co', N'0989898584');



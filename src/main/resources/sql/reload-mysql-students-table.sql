SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE table TTKNP.students;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO TTKNP.students (fullname, age) VALUES
                                               ('Alice Smith', 20),
                                               ('Bob Johnson', 22),
                                               ('Charlie Brown', 19),
                                               ('David Lee', 21),
                                               ('Eve Wilson', 23);
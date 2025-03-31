SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE table TTKNP.students;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO TTKNP.students (fullname, age,code) VALUES
                                               ('Alice Smith', 20,'A0001'),
                                               ('Bob Johnson', 22,'A0002'),
                                               ('Charlie Brown', 19,'A0003'),
                                               ('David Lee', 21,'A0004'),
                                               ('Eve Wilson', 23,'A0005');
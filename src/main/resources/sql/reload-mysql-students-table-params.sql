INSERT INTO `students` (ID,FULLNAME, AGE,CODE) VALUES (1,'Alice Smith', 20,'A0001') ,
                                                      (2,'Bob Johnson', 22,'A0002') ,
                                                      ({ID},{FULLNAME}, {AGE}, {CODE});
-- Remember JDBC template or the Jdbc driver doesn't allow truncate operations
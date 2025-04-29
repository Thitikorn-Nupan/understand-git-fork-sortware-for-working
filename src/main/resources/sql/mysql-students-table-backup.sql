INSERT INTO TTKNP.students_bak (ID,FULLNAME, AGE,CODE)
 SELECT ID,FULLNAME, AGE,[CODE]
  FROM TTKNP.students
   WHERE CODE = {CODE};
-- [] is optional parameter(s) & {} is required parameter(s)
-- [] can be null as ...,AGE,null
package com.ttknp.testspringbootapp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
// @Table() in JDBC works kinda same JPA

@Table(name = "users_detail",schema = "A_APP")
public class UserDetail {
    @Id
    public Integer id;
    public String firstname;
    public String lastname;
    public Integer age;
    public String email;
    public String phone;
}

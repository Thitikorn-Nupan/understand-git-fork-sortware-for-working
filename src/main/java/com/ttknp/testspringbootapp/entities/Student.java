package com.ttknp.testspringbootapp.entities;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
// multiple database it's not work
// log tell you are working @Primary
// @Table(name = "students")
public class Student {
    // @Id
    public Integer id;
    // @Column("fullname")
    public String fullName;
    public Short age;
}

package com.ttknp.testspringbootapp.repositories;

import com.ttknp.testspringbootapp.entities.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// @Repository // not working
public interface StudentRepo extends CrudRepository<Student,Integer> {
}

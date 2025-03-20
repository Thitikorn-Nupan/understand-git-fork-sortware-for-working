package com.ttknp.testspringbootapp.repositories;

import com.ttknp.testspringbootapp.entities.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends CrudRepository<Student,Integer> {
}

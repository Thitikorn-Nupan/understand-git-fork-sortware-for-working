package com.ttknp.testspringbootapp.repositories;

import com.ttknp.testspringbootapp.entities.UserDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserDetailRepo extends CrudRepository<UserDetail,Integer> {

}

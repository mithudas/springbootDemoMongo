package com.mithu.springbootDemo.repository;

import com.mithu.springbootDemo.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mithu on 25/6/17.
 */
public interface UserMongoRepository extends CrudRepository<User, String> {}

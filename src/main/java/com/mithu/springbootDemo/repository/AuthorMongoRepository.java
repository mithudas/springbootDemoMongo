package com.mithu.springbootDemo.repository;

import com.mithu.springbootDemo.model.Author;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mithu on 25/6/17.
 */
public interface AuthorMongoRepository extends CrudRepository<Author, String> {}

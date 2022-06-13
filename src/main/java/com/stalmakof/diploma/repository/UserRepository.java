package com.stalmakof.diploma.repository;

import org.springframework.data.repository.CrudRepository;

import com.stalmakof.diploma.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}

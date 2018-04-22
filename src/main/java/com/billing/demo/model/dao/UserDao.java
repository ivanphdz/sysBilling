package com.billing.demo.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.billing.demo.model.entity.User;

public interface UserDao extends CrudRepository<User, Long> {

	public User findByUsername(String username);
}

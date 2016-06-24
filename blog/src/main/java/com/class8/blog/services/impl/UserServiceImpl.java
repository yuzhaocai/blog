package com.class8.blog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.class8.blog.models.User;
import com.class8.blog.repositorys.UserRepository;
import com.class8.blog.services.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void createUser(User user) {
		System.out.println("create user method invoke()!");
	}
	
}

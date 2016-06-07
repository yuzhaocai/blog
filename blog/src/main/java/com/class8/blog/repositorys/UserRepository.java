package com.class8.blog.repositorys;

import org.springframework.data.repository.CrudRepository;
import com.class8.blog.models.User;

public interface UserRepository extends CrudRepository<User,Integer>{
	
}

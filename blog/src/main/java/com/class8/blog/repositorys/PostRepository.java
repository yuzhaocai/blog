package com.class8.blog.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import com.class8.blog.models.Post;
/**
 * 
 * 
 *
 */
public interface PostRepository extends JpaRepository<Post, Long>{

}

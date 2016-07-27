package com.class8.blog.controllers.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.class8.entity.User;
import com.class8.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/{id:\\d+}",method={GET,POST})
	public String showUser(Long id,Model model){
		User user = userService.getById(id);
		model.addAttribute("user", user);
		return "user/show";
	}
	
}

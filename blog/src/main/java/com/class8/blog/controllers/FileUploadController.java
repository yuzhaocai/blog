package com.class8.blog.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.class8.blog.exception.BusinessException;
import com.class8.blog.models.User;

/**
 * Handles requests for the application file upload requests
 *
 */
@Controller
public class FileUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFileHandler(@RequestParam("name") String name,@RequestParam("file") MultipartFile file){
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}
	
	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());
				System.out.println("测试System.out会不会出现在文件里！");
				message = message + "You successfully uploaded file=" + name + "";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
	
	/**
	 * 测试MessageConverter选择的问题，即如何选择对应的MessageConverter
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/user/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable Long userId){
		User user = new User();
		user.setId(userId);
		user.setUsername("yuzhaocai");
		user.setPassword("870646595");
		user.setEmail("870646595@qq.com");
		user.setNickName("情非得已");
		user.setBirthday("1990-12-20");
		return user;
	}
	
	/**
	 * 测试StringHttpMessageConverter返回中文的问题，设置编码为中文
	 * @return
	 */
	@RequestMapping(value="/getUsername",method=RequestMethod.GET)
	@ResponseBody
	public String getUsername(){
		return "余昭财";
	}
	
	/**
	 * 测试Spring MVC全局异常处理和日志记录
	 * @return
	 */
	@RequestMapping(value="/user/list",method=RequestMethod.GET)
	public String listUser(){
		throw new NullPointerException();
	}
	
	/**
	 * 测试ajax请求抛出异常是否会被记录
	 * @param userId
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/getUserById",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String getUserById(@RequestParam Long id) throws BusinessException{
		throw new BusinessException("参数不正确.");
	}
	
	
}

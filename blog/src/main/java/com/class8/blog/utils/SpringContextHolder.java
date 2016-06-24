package com.class8.blog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * ApplicationContext的持有者，想在Servelt/Filter/Listerner中获取spring容器中的对象，可以使用这种方式
 * @author Administrator
 *
 */
public class SpringContextHolder implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	/** 
	* 取得存储在静态变量中的ApplicationContext. 
	*/  
	public static ApplicationContext getApplicationContext() {  
		checkApplicationContext();  
		return applicationContext;  
	}  
	  
	/** 
	* 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 
	*/  
	@SuppressWarnings("unchecked")  
	public static <T> T getBean(String name) {  
		checkApplicationContext();  
		return (T) applicationContext.getBean(name);  
	}  
	  
	/** 
	* 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 
	*/  
	@SuppressWarnings("unchecked")  
	public static <T> T getBean(Class<T> clazz) {  
		checkApplicationContext();  
		return (T) applicationContext.getBeansOfType(clazz);  
	}  
	  
	/** 
	* 清除applicationContext静态变量. 
	*/  
	public static void cleanApplicationContext() {  
		applicationContext = null;  
	}  
	  
	private static void checkApplicationContext() {  
		if (applicationContext == null) {  
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");  
		}  
	}  
	
	
	
	

}

package com.class8.blog.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class UserServiceAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceAspect.class);
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.class8.blog.services.impl..*(..))")
	public void pointcut(){
		
	}
	
	//前置通知，可以接受JoinPoint切入点对象，可以没有该参数
	@Before("pointcut()")
	public void before(JoinPoint joinPoint){
		logger.info("before " + joinPoint);
	}
	
	//后置通知，可以接受JoinPoint切入点对象，可以没有该参数
	@After("pointcut()")
	public void after(JoinPoint joinPoint){
		logger.info("after " + joinPoint);
	}
	
	//配置环绕通知,使用在方法aspect()上注册的切入点
	@Around("pointcut()")
	public void around(JoinPoint joinPoint){
		long start = System.currentTimeMillis();
		try {
			((ProceedingJoinPoint) joinPoint).proceed();
			long end = System.currentTimeMillis();
			logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
		}
	}
	
	//配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning("pointcut()")
	public void afterReturn(JoinPoint joinPoint){
		logger.info("afterReturn " + joinPoint);
	}
	
	//配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut="pointcut()", throwing="ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex){
		logger.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
	}
	
}

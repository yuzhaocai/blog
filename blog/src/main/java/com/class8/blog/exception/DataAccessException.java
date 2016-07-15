package com.class8.blog.exception;
/**
 * 对数据库访问层的抛出的进行封装，需要记录原始的异常，web层需要捕捉改异常，并进行log日志记录
 * @author Administrator
 *
 */
public class DataAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7682903436189528601L;

}

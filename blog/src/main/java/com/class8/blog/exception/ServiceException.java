package com.class8.blog.exception;
/**
 * 对业务逻辑层抛出的非业务异常进行包装，需要记录原始的异常，web层需要捕捉改异常，并进行log日志记录
 * @author Administrator
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5353121308931155083L;

}

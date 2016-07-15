package com.class8.blog.exception;
/**
 * 动态数据源异常
 * @author Administrator
 *
 */
public class DynamicDataSourceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6887935542872283434L;
	
	public DynamicDataSourceException(String message, Throwable cause) {
		super(message, cause);
    }

}

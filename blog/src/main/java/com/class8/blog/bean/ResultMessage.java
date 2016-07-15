package com.class8.blog.bean;
/**
 * AJAX请求响应结果对象
 *
 */
public class ResultMessage {
	
	public static final int INTERNAL_SERVER_ERROR_RTCODE = -100;
	
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "系统错误，请稍后重试.";
	
	private int code;
	
	private String message;
	
	private Object data;
	
	public ResultMessage(){
		
	}
	
	public ResultMessage(int code,String message){
		this(code,message,null);
	}
	
	public ResultMessage(String message,Object data){
		this(0,message,data);
	}
	
	public ResultMessage(int code,String message,Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}

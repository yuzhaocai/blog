package com.class8.blog.bean;
/**
 * AJAX请求响应结果对象
 *
 */
public class ResultMessage {
	
	public static final int INTERNAL_SERVER_ERROR_RTCODE = -100;
	
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "系统错误，请稍后重试.";
	
	/**
	 * 响应码
	 */
	private int rtcode;
	
	/**
	 * 结果描述
	 */
	private String message;
	
	/**
	 * 响应的数据
	 */
	private Object result;
	
	public ResultMessage(){
		
	}
	
	public ResultMessage(int rtcode,String message){
		this(rtcode,message,null);
	}
	
	public ResultMessage(String message,Object result){
		this(0,message,result);
	}
	
	public ResultMessage(int rtcode,String message,Object result){
		this.rtcode = rtcode;
		this.message = message;
		this.result = result;
	}

	public int getRtcode() {
		return rtcode;
	}

	public void setRtcode(int rtcode) {
		this.rtcode = rtcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}

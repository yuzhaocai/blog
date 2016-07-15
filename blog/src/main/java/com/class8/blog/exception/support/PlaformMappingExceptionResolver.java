package com.class8.blog.exception.support;


import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.class8.blog.bean.ResultMessage;
import com.class8.blog.exception.BusinessException;
import com.class8.blog.utils.JsonUtil;

public class PlaformMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(PlaformMappingExceptionResolver.class);
	
	/**
	 * logger记录所有的异常(SimpleMappingExceptionResolver默认异常记录只会记录简单的信息)
	 */
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		String message = ex == null ? "null" : ex.getMessage();
		logger.error(message == null? "null" : message, ex);
	}
	
	/**
	 * 分别处理ajax请求和非ajax请求的错误的处理，ajax以json数据格式返回,默认logException不会记录ajax处理方式的异常信息，所以在这里记录异常
	 * 并且如果为业务异常(BusinessException),则消息为业务异常的消息
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);  
        if (viewName != null) {  
	        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request  
	                .getHeader("X-Requested-With") != null && request  
	                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {  
	            Integer statusCode = determineStatusCode(request, viewName);  
	            if (statusCode != null) {  
	                applyStatusCodeIfPossible(request, response, statusCode);  
	            }  
	            return getModelAndView(viewName, ex, request);  
	        } else {  
	            try {
	            	String message = ex == null ? "null" : ex.getMessage();
	            	logger.error(message == null? "null" : message,ex);
	            	response.setContentType("application/json;charset=UTF-8");
	                ResultMessage resultMessage = new ResultMessage();
	                resultMessage.setRtcode(ResultMessage.INTERNAL_SERVER_ERROR_RTCODE);
	                if(ex != null && ex instanceof BusinessException){
	                	resultMessage.setMessage(ex.getMessage());
	                } else {
	                	resultMessage.setMessage(ResultMessage.INTERNAL_SERVER_ERROR_MESSAGE);
	                }
	                PrintWriter writer = response.getWriter();  
                    writer.write(JsonUtil.toJson(resultMessage));  
	                writer.flush(); 
	            } catch ( Exception e ) {  
	                e.printStackTrace();  
	            }  
	             return null;  
	        }  
        } else {  
            return null;  
        }  
	}
	
	 public static String getTrace(Throwable t) {  
        StringWriter stringWriter= new StringWriter();  
        PrintWriter writer= new PrintWriter(stringWriter);  
        t.printStackTrace(writer);  
        StringBuffer buffer= stringWriter.getBuffer();  
        return buffer.toString();  
    }  
	
}

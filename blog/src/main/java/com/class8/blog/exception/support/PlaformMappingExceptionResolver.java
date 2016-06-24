package com.class8.blog.exception.support;


import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class PlaformMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(PlaformMappingExceptionResolver.class);
	
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		String message = ex == null ? "null" : ex.getMessage();
		logger.error(message, ex);
	}
	
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
	            	PrintWriter writer = response.getWriter();  
                    writer.write(ex.getMessage());  
                    response.setStatus(404, ex.getMessage());  
	                writer.flush();  
	            } catch ( Exception e ) {  
	                  logger.error("weizhi cuowu", e);
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

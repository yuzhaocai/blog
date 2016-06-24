package com.class8.blog.exception.support;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import com.class8.blog.exception.DataAccessException;
import com.class8.blog.exception.ServiceException;

//@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	public static final String DEFAULT_ERROR_VIEW = "error/error";
	
	@ExceptionHandler(value={DataAccessException.class})
	public ModelAndView handleDateAccessException(Exception exception){
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	
	@ExceptionHandler(value={ServiceException.class})
	public ModelAndView handleServiceException(Exception exception){
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	
	@ExceptionHandler(value={Exception.class})
	public ModelAndView defaultExceptionHandler(Exception exception) throws Exception{
		if(AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null){
			throw exception;
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	
}

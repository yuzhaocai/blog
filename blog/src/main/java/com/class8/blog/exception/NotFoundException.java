package com.class8.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 资源不存在,抛出该异常，系统返回404页面，而非抛出异常
 *
 */
@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

}

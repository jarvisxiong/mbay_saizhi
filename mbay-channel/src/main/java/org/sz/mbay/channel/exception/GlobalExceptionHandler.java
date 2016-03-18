package org.sz.mbay.channel.exception;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.sz.mbay.base.exception.ServiceException;
import org.sz.mbay.channel.costant.error.GloblError;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	protected Logger logger;
	public static final String ERROR_PAGE = "error_page";
	public static final String CODE = "code";
	
	public GlobalExceptionHandler() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	/**
	 * Convert a predefined exception to an HTTP Status code
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		logger.error("Request raised a DataIntegrityViolationException");
		// Nothing to do
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException() {
		logger.error("IOException handler executed");
		// returning 404 error code
	}
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "IOException occured")
	@ExceptionHandler(Exception.class)
	public void handleInternalException(Exception e) {
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			System.out.println(element.getClassName() + element.getMethodName() + element.getLineNumber());
			
		}
		String fullClassName = Thread.currentThread().getStackTrace()[1]
				.getClassName();
		String className = fullClassName.substring(fullClassName
				.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[1]
				.getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[1]
				.getLineNumber();
		logger.error(className + "." + methodName + "():" + lineNumber);
		logger.error("IOException handler executed");
		logger.error("IN LIN:" + lineNumber, e.fillInStackTrace());
		// returning 404 error code
	}
	
	@ExceptionHandler(ServiceException.class)
	public String handleServiceException(ServiceException exception) {
		return "redirect:/exception/error.mbay?" + CODE + "="
				+ exception.getErrorInfo().getCode();
	}
	@ExceptionHandler(WebInterfaceException.class)
	public String handleWebInterfaceException(WebInterfaceException exception) {
		return "redirect:/exception/web_inteface_error.mbay?" + CODE + "="
				+ exception.getErrorInfo().getCode();
	}
	
	@ExceptionHandler(SQLException.class)
	public String handleSQLException(SQLException exception) {
		return "redirect:/error.mbay?" + CODE + "="
				+ GloblError.SQL_EXCEPTION.getCode();
	}
	
}

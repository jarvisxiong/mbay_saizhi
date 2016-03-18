package org.sz.mbay.routeros.framework.exception;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理
 * 
 * @author jerry
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	protected Logger logger;
	
	public GlobalExceptionHandler() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	@ExceptionHandler(DataIntegrityViolationException.class)
	public void conflict() {
		logger.error("Request raised a DataIntegrityViolationException");
	}
	
	/**
	 * 处理io异常
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException(IOException e) {
		logger.error("IOException handler executed", e.fillInStackTrace());
	}
	
	/**
	 * 处理数据库执行异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SQLException.class)
	public void handleSQLException(SQLException e) {
		logger.error("SQLException handler executed", e.fillInStackTrace());
	}
	
	/**
	 * 处理未知异常
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Exception occured")
	@ExceptionHandler(Exception.class)
	public void handleInternalException(Exception e, HttpServletResponse response) {
		logger.error("Exception handler executed", e.fillInStackTrace());
	}
}

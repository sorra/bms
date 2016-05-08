package com.iostate.bms.web.support;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iostate.bms.commons.BadArgumentException;
import com.iostate.bms.commons.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ControllerExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(TypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView typeMismatch(TypeMismatchException e, HttpServletRequest request) {
    log.error("URI: {} Exception: {}", request.getRequestURI(), e.toString());
    return errorPage(HttpStatus.BAD_REQUEST, "Parameter type mismatch 参数类型不对");
  }

  @ExceptionHandler(BadArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ModelAndView badArgument(BadArgumentException e,
                                  HttpServletRequest request, HttpServletResponse response) {
    log.error("URI: {} Exception: {}", request.getRequestURI(), e.toString());
    return errorPage(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ModelAndView httpMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                             HttpServletRequest request, HttpServletResponse response) {
    log.error("URI: {} Exception: {}", request.getRequestURI(), e.toString());
    return errorPage(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
  }

  @ExceptionHandler(DomainException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView domainException(DomainException e) {
    StringBuilder msgBuilder = new StringBuilder(e.toString());
    StackTraceElement[] stacks = e.getStackTrace();
    if (stacks.length > 0) msgBuilder.append("\n\tat ").append(stacks[0]);

    if (e.getCause() != null) {
      StringWriter stringWriter = new StringWriter();
      e.getCause().printStackTrace(new PrintWriter(stringWriter));
      msgBuilder.append("\n\tCaused by: ").append(stringWriter);
    }

    log.error(msgBuilder.toString());
    return errorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView any(Throwable e, HttpServletRequest request) {
    log.error("URI: " + request.getRequestURI() + "\nController error: ", e);
    return errorPage(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
  }

  private ModelAndView errorPage(HttpStatus status, String reason) {
    return new ModelAndView("error")
        .addObject("errorCode", status.value()).addObject("reason", reason);
  }
}

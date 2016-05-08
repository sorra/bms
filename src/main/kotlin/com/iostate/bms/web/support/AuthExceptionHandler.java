package com.iostate.bms.web.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(0)
public class AuthExceptionHandler {
  @ExceptionHandler(RequireLoginException.class)
  @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
  public String redirectToLogin(HttpServletRequest request) {
    String uri = request.getRequestURI();
    if (request.getQueryString() != null) {
      uri += ("?" + request.getQueryString());
    }
    return "redirect:/login?" + Auth.INSTANCE.getRedirectGoto(uri);
  }
}

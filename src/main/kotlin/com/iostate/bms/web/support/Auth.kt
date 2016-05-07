package com.iostate.bms.web.support

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION
import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpServletRequest


object Auth {
  private val log: Logger = LoggerFactory.getLogger(Auth.javaClass)

  fun checkUserId() = userId() ?: throw RequireLoginException()

  fun userId() =
      RequestContextHolder.currentRequestAttributes().getAttribute("userId", SCOPE_SESSION) as Long?

  fun login(request: HttpServletRequest, userId: Long) {
    logout(request) // Clear old session data
    request.session.setAttribute("userId", userId)
    log.info("User {} login successfully.", userId)
  }

  fun logout(request: HttpServletRequest) {
    request.getSession(false)?.invalidate()
  }
}
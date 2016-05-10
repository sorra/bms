package com.iostate.bms.web.support

import org.jasypt.util.password.StrongPasswordEncryptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.util.UriUtils
import javax.servlet.http.HttpServletRequest


object Auth {
  private val log: Logger = LoggerFactory.getLogger(Auth.javaClass)

  fun checkUserId() = userId() ?: throw RequireLoginException()

  fun userId() =
      RequestContextHolder.currentRequestAttributes().getAttribute("userId", SCOPE_SESSION) as Long?

  fun login(request: HttpServletRequest, userId: Long) {
    logout(request) // Clear old session data
    request.session.setAttribute("userId", userId)
    log.info("User[{}] login successfully.", userId)
  }

  fun logout(request: HttpServletRequest) {
    log.info("User[{}] logout.", userId())
    request.getSession(false)?.invalidate()
  }

  fun encryptPassword(password: String) = StrongPasswordEncryptor().encryptPassword(password)

  fun checkPassword(plainPassword: String, encryptedPassword: String) =
      StrongPasswordEncryptor().checkPassword(plainPassword, encryptedPassword)

  fun getRedirectGoto(requestLink: String): String {
    return "goto=" + encodeLink(requestLink)
  }

  fun encodeLink(link: String) = UriUtils.encodeQueryParam(link, "ISO-8859-1")

  fun decodeLink(link: String) = UriUtils.decode(link, "ISO-8859-1")
}
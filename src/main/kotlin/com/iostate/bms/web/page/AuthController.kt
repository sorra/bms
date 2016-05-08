package com.iostate.bms.web.page

import com.iostate.bms.commons.DomainException
import com.iostate.bms.entity.User
import com.iostate.bms.web.support.Auth
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/auth")
open class AuthController {
  val log = LoggerFactory.getLogger(AuthController::class.java)

  @RequestMapping("/login", method = arrayOf(POST))
  open fun login(request: HttpServletRequest,
                 @RequestParam email: String, @RequestParam password: String): String {
    val user = User.where().eq("email", email).findUnique()
    if (user != null && Auth.checkPassword(password, user.password)) {
      Auth.login(request, user.id)
      return "redirect:/"
    } else {
      throw DomainException("Wrong email or password!")
    }
  }

  @RequestMapping("/register", method = arrayOf(POST))
  open fun register(@RequestParam email: String, @RequestParam password: String,
                    @RequestParam(required = false) name: String?): String {
    if (User.where().eq("email", email).findUnique() != null) {
      throw DomainException("Email %s is already used!", email)
    }
    val user = User(email = email, password = Auth.encryptPassword(password), name = name.orEmpty())
    user.save()
    log.info("User[{}] registered.", user.id)
    if (name.isNullOrEmpty()) {
      user.name = "用户" + user.id
      user.save()
    }
    return "forward:/auth/login"
  }

  @RequestMapping("/change-password", method = arrayOf(POST))
  open fun changePassword(@RequestParam oldPassword: String,
                          @RequestParam newPassword: String): String {
    val userId = Auth.checkUserId()
    val user = User.byId(userId)
    if (user != null && Auth.checkPassword(oldPassword, user.password)) {
      user.password = Auth.encryptPassword(newPassword)
      user.save()
      log.info("User[{}] changed password.", user.id)
      return "redirect:/"
    } else {
      throw DomainException("Wrong email or password")
    }
  }
}
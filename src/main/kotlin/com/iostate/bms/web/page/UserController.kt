package com.iostate.bms.web.page

import com.iostate.bms.commons.DomainException
import com.iostate.bms.entity.User
import com.iostate.bms.web.support.Auth
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/users")
open class UserController {
  @RequestMapping("/{id}/edit")
  open fun edit(@PathVariable id: Long): ModelAndView {
    val userId = Auth.checkUserId()
    if (id == userId) {
      return ModelAndView("edit-user").addObject("user", User.byId(id))
    } else {
      throw DomainException("You are not allowed to edit User[%s]", id)
    }
  }
}

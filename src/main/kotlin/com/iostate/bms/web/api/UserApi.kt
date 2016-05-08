package com.iostate.bms.web.api

import com.iostate.bms.commons.DomainException
import com.iostate.bms.entity.User
import com.iostate.bms.web.support.Auth
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping("/api/users")
open class UserApi {
  // Cannot create user -- must via register

  @RequestMapping("/all")
  open fun all(@RequestParam(defaultValue = "0") pageIndex: Int,
               @RequestParam(defaultValue = "20") pageSize: Int,
               @RequestParam(required = false) orderBy: String?) =
      User.apply{ if(orderBy != null) orderBy(orderBy) }.findPagedList(pageIndex, pageSize).list

  @RequestMapping("/{id}")
  open fun get(@PathVariable id: Long) = User.byId(id)

  @RequestMapping("/{id}/update", method = arrayOf(POST))
  open fun update(@PathVariable id: Long,
                  @RequestParam name: String, @RequestParam introduction: String): User {
    val userId = Auth.checkUserId()
    if (id == userId) {
      return User.byId(userId)?.apply {
        this.name = name
        this.introduction = introduction
        update()
      } ?: throw DomainException("User[%s] does not exist", id)
    } else{
      throw DomainException("You are not allowed to update User[%s]", id)
    }
  }

  @RequestMapping("/{id}/delete", method = arrayOf(POST))
  open fun delete(@PathVariable id: Long) {
    val userId = Auth.checkUserId()
    if (id == userId) User.deleteById(id)
    else throw DomainException("You are not allowed to delete User[%s]", id)
  }
}
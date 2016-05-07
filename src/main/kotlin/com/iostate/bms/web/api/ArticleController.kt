package com.iostate.bms.web.api

import com.iostate.bms.commons.DomainException
import com.iostate.bms.entity.Article
import com.iostate.bms.entity.User
import com.iostate.bms.web.support.Auth
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping("/api/articles/")
open class ArticleController {

  @RequestMapping("/new", method = arrayOf(POST))
  open fun create(@RequestBody article: Article) {
    val userId = Auth.checkUserId()
    article.author = User.ref(userId)
    article.save()
  }

  @RequestMapping("/{id}")
  open fun get(@PathVariable id: Long) = Article.byId(id)

  @RequestMapping("/{id}/update")
  open fun update(@PathVariable id: Long, @RequestBody article: Article): Article {
    val userId = Auth.checkUserId()
    if (userId == Article.byId(id)?.author?.id) {
      article.id = id
      article.save()
      return article
    } else {
      throw DomainException("You are not the author of article[%s]", id)
    }
  }

  @RequestMapping("/{id}/delete")
  open fun delete(@PathVariable id: Long) {
    val userId = Auth.checkUserId()
    if (userId == Article.byId(id)?.author?.id) Article.deleteById(id)
    else throw DomainException("You are not the author of article[%s]", id)
  }
}
package com.iostate.bms.web.api

import com.iostate.bms.commons.DomainException
import com.iostate.bms.entity.Article
import com.iostate.bms.entity.User
import com.iostate.bms.web.support.Auth
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping("/api/articles")
open class ArticleApi {

  @RequestMapping("/new", method = arrayOf(POST))
  open fun create(@RequestParam title: String, @RequestParam content: String): Article {
    val userId = Auth.checkUserId()
    return Article(title = title, content = content, author = User.ref(userId)).apply{
      save()
    }
  }

  @RequestMapping("/all")
  open fun all(@RequestParam(defaultValue = "0") pageIndex: Int,
               @RequestParam(defaultValue = "20") pageSize: Int,
               @RequestParam(required = false) orderBy: String?) =
      Article.apply{ if(orderBy != null) orderBy(orderBy) }.findPagedList(pageIndex, pageSize).list

  @RequestMapping("/{id}")
  open fun get(@PathVariable id: Long) = Article.byId(id)

  @RequestMapping("/{id}/update", method = arrayOf(POST))
  open fun update(@PathVariable id: Long,
                  @RequestParam title: String, @RequestParam content: String): Article {
    val userId = Auth.checkUserId()
    val article = Article.byId(id) ?: throw DomainException("Article[%s] does not exist", id)
    if (userId == article.author?.id) {
      article.title = title
      article.content = content
      return article
    } else {
      throw DomainException("You are not the author of article[%s]", id)
    }
  }

  @RequestMapping("/{id}/delete", method = arrayOf(POST))
  open fun delete(@PathVariable id: Long) {
    val userId = Auth.checkUserId()
    if (userId == Article.byId(id)?.author?.id) Article.deleteById(id)
    else throw DomainException("You are not the author of article[%s]", id)
  }
}
package com.iostate.bms.web.page

import com.iostate.bms.commons.DomainException
import com.iostate.bms.entity.Article
import com.iostate.bms.web.support.Auth
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/articles")
open class ArticleController {
  @RequestMapping("/write")
  open fun write(): ModelAndView {
    Auth.checkUserId()
    return ModelAndView("write-article").addObject("subTarget", "new")
  }

  @RequestMapping
  open fun all(): ModelAndView {
    Auth.checkUserId()
    return ModelAndView("article-list").addObject("ars", Article.all())
  }

  @RequestMapping("/{id}")
  open fun byId(@PathVariable id: Long): ModelAndView {
    val userId = Auth.checkUserId()
    val article = Article.byId(id)
    return ModelAndView("article").addObject("ar", article).addObject("canEdit", article?.authorId == userId)
  }

  @RequestMapping("/{id}/edit")
  open fun edit(@PathVariable id: Long): ModelAndView {
    Auth.checkUserId()
    val article = Article.byId(id) ?: throw DomainException("Article[%s] does not exist", id)
    return ModelAndView("write-article").addObject("subTarget", "$id/update").addObject("ar", article)
  }
}

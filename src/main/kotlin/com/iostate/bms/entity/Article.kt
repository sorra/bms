package com.iostate.bms.entity

import com.avaje.ebean.annotation.SoftDelete
import javax.persistence.Entity

@Entity
class Article (
    var title: String = "",
    var content: String = "",
    var authorId: Long = 0,
    @SoftDelete
    var deleted: Boolean = false
) : BaseModel() {

  @delegate:javax.persistence.Transient
  val author: User? by lazy { load(User::class.java, authorId) }

  companion object find : Find<Long, Article>()
}
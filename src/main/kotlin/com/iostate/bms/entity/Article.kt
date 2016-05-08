package com.iostate.bms.entity

import com.avaje.ebean.annotation.SoftDelete
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Article (
    var title: String = "",
    var content: String = "",
    @ManyToOne
    var author: User? = null,
    @SoftDelete
    var deleted: Boolean = false
) : BaseModel() {
  companion object find : Find<Long, Article>()
}
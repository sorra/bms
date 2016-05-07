package com.iostate.bms.entity

import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Article (
    var title: String = "",
    var content: String = "",
    @ManyToOne
    var author: User
) : BaseModel() {
  companion object find : Find<Long, Article>()
}
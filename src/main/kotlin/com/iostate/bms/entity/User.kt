package com.iostate.bms.entity

import javax.persistence.Entity

@Entity
class User (
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var introduction: String = ""
) : BaseModel() {
  companion object find : Find<Long, User>()
}
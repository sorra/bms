package com.iostate.bms.entity

import com.avaje.ebean.annotation.SoftDelete
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity

@Entity
class User (
    var email: String = "",
    @JsonIgnore
    var password: String = "",
    var name: String = "",
    var introduction: String = "",
    @SoftDelete
    var deleted: Boolean = false
) : BaseModel() {
  companion object find : Find<Long, User>()
}
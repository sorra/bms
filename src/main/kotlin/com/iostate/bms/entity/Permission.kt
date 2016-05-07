package com.iostate.bms.entity

import javax.persistence.Entity

@Entity
class Permission (
    var name: String = "",
    var remark: String = ""
) : BaseModel() {
  companion object find : Find<Long, Permission>()
}
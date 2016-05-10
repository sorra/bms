package com.iostate.bms.entity

import com.avaje.ebean.Model
import com.avaje.ebean.annotation.WhenCreated
import com.avaje.ebean.annotation.WhenModified
import java.sql.Timestamp
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
abstract class BaseModel : Model() {

  @Id @GeneratedValue
  var id: Long = 0

  @Version
  var version: Long = 0

  @WhenCreated
  var whenCreated: Timestamp? = null

  @WhenModified
  var whenModified: Timestamp? = null

}

fun <T> load(beanType: Class<T>, id: Long): T? =
    if (id > 0) Model.db().find(beanType, id) else null
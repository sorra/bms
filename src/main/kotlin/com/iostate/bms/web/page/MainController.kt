package com.iostate.bms.web.page

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
open class MainController {

  @RequestMapping("/", "/index")
  open fun index() = "index"
}
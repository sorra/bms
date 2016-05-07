package com.iostate.bms

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping
open class MainController {
  @RequestMapping("/", "/index")
  open fun index() = "index"
}
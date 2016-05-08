package com.iostate.bms

import com.iostate.bms.BmsApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf(BmsApplication::class))
@WebAppConfiguration
class BmsApplicationTests {

  @Test
  fun contextLoads() {
  }

}

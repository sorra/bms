package com.iostate.bms

import com.avaje.ebean.EbeanServerFactory
import com.avaje.ebean.config.ServerConfig
import httl.web.springmvc.HttlViewResolver
import org.avaje.agentloader.AgentLoader
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

@SpringBootApplication
open class BmsApplication : WebMvcConfigurationSupport() {
  @Bean(autowire = Autowire.BY_TYPE)
  @Scope(SCOPE_SINGLETON)
  open fun getEbeanServer() = {
    val config = ServerConfig()
    config.name = "mysql"
    config.loadFromProperties()
    config.isDefaultServer = true

    EbeanServerFactory.create(config)
  }

  override fun configureViewResolvers(registry: ViewResolverRegistry) {
    registry.viewResolver(HttlViewResolver().apply { setContentType("text/html; charset=UTF-8") })
  }

  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
    registry.addResourceHandler("/static/**")?.addResourceLocations("/static/")
  }

  @Bean
  @Scope(SCOPE_SINGLETON)
  open fun json() = MappingJackson2JsonView()
}

fun main(args: Array<String>) {
  if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", "debug=1;packages=com.iostate.**")) {
    System.err.println("avaje-ebeanorm-agent not found in classpath - not dynamically loaded")
  }
  SpringApplication.run(BmsApplication::class.java, *args)
}

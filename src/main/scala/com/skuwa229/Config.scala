package com.skuwa229

import com.typesafe.config.ConfigFactory

/**
  * Created by shota.kuwahara on 2016/07/30.
  */
trait Config {
  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")

  val httpInterface = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")
}
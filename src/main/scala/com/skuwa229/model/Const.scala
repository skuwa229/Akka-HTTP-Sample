package com.skuwa229.model

import com.typesafe.config.ConfigFactory

/**
  * Created by shota.kuwahara on 2016/07/31.
  */
trait Const {
  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")

  val httpInterface = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")
}

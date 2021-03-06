package com.skuwa229

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import scalikejdbc._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.skuwa229.model.orm.{Tuser, TuserJsonProtocol}
import spray.json._

import scala.io.StdIn
import model._
import TuserJsonProtocol._

/**
  * Created by shota.kuwahara on 2016/07/30.
  */
object Server extends Const with SprayJsonSupport {
  implicit lazy val s = AutoSession

  def main(args: Array[String]) {
    implicit val system = ActorSystem("webserver")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    skinny.DBSettings.initialize()

    val log: LoggingAdapter = Logging(system, getClass)

    val routes =
      path("")(getFromResource("public/index.html")) ~
        path("hello") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http!</h1>"))
          }
        } ~
        path("userlist") {
          get {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,Tuser.findAll().map(r=>r.toJson).mkString("<br>")))
          }
        } ~
        path("user" / ".+".r) { id =>
          Tuser.findBy(sqls.eq(Tuser.defaultAlias.column("userid"),id)) match {
            case Some(u) => complete(u.toJson)
            case None => complete(404, "Not found!")
          }
        }

    val bindingFuture = Http().bindAndHandle(routes, httpInterface, httpPort)
    bindingFuture.onFailure {
      case ex: Exception => log.error(ex, "Failed to bind to {}:{}!", httpInterface, httpPort)
    }
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}
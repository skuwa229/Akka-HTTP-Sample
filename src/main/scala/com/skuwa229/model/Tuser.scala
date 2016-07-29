package com.skuwa229.model

import scalikejdbc._
import skinny.orm._
import spray.json.DefaultJsonProtocol

case class Tuser(
  userid: Option[String] = None,
  name: Option[String] = None,
  age: Option[Long] = None
)

object Tuser extends SkinnyNoIdCRUDMapper[Tuser] {
  override lazy val tableName = "t_user"
  override lazy val defaultAlias = createAlias("t")

  override def extract(rs: WrappedResultSet, rn: ResultName[Tuser]): Tuser = {
    autoConstruct(rs, rn)
  }
}

object TuserJsonProtocol extends DefaultJsonProtocol {
  implicit val format = jsonFormat3(Tuser.apply)
}
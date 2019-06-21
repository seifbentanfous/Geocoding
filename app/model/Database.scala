package model

import com.datastax.oss.driver.api.core.{CqlIdentifier, CqlSession}
import play.api.libs.json.{JsValue, Json}

import scala.collection.JavaConverters._

object Database {

  private val session = CqlSession
    .builder()
    .withKeyspace(CqlIdentifier.fromCql("immobilier"))
    .build()

  private val preparedStatement = session.prepare("select * from synthese where commune = ?")

  def getValueFromCassandraTable(city: String): JsValue = {
    val boundStatement = preparedStatement.bind(city)
    val rows = session.execute(boundStatement)
    val prices = rows.asScala.map(_.getFloat("prix").toString)
    Json.toJson(prices)
  }
}
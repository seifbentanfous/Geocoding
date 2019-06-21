package model.geocoding

import com.datastax.oss.driver.api.core.{CqlIdentifier, CqlSession}
import play.api.libs.json.{JsValue, Json}

import scala.collection.JavaConverters._

object test extends App {

  private val session = CqlSession
    .builder()
    .withKeyspace(CqlIdentifier.fromCql("immobilier"))
    .build()


  private val preparedStatement = session
    .prepare("select * from valeursfoncieres where latitude <= ? and latitude >= ? and longitude <= ? and longitude >= ? ALLOW FILTERING")


  def getAddress(address: String)= {

    val data = scala.io.Source.fromURL("https://nominatim.openstreetmap.org/search/6%20rue%20de%20rome%20%2075008?format=json").mkString
    val json = Json.parse(data)
    val lat = json \\ "lat"
    val lon = json \\ "lon"
    val latt = lat(0).toString()
    val long = lon(0).toString()
    val latitude = latt.slice(1, latt.length - 1).toDouble
    val longitude = long.slice(1, long.length - 1).toDouble

    println(latitude)
    println(longitude)
    val boundStatement = preparedStatement.bind()
      .setDouble(0, latitude)
      .setDouble(1, latitude)
      .setDouble(2, longitude)
      .setDouble(3, longitude)

    val rows = session.execute(boundStatement)
    val addr = rows
      .asScala.
      map(row
      => (row.getInt("adresse_numero")
          , row.getString("adresse_code_voie")
          , row.getInt("code_postal")
          , row.getString("nom_commune")
          , row.getDouble("valeur_fonciere")))
    println(Json.toJson(addr))


  }
  getAddress("dede")
}

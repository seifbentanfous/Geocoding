package model.geocoding

import org.apache.spark.sql.SparkSession
import play.api.libs.json.{JsValue, Json}


object teste extends App {

  case class ValeursFoncieres(id_mutation: String, date_mutation: String, numero_disposition: String, nature_mutation: String, valeur_fonciere: Double, adresse_numero: Int,
                              adresse_suffixe: String, adresse_code_voie: String, adresse_nom_voie: String, code_postal: Int, code_commune: String, nom_commune: String,
                              ancien_code_commune: String,
                              ancien_nom_commune: String, code_departement: String, id_parcelle: String, ancien_id_parcelle: String, numero_volume: String,
                              lot1_numero: String, lot1_surface_carrez: String, lot2_numero: String,
                              lot2_surface_carrez: String, lot3_numero: String, lot3_surface_carrez: String, lot4_numero: String, lot4_surface_carrez: String,
                              lot5_numero: String, lot5_surface_carrez: String,
                              nombre_lots: String, code_type_local: String, type_local: String, surface_reelle_bati: String, nombre_pieces_principales: String, code_nature_culture: String,
                              nature_culture: String, code_nature_culture_speciale: String,
                              nature_culture_speciale: String, surface_terrain: Int, longitude: Double, latitude: Double
                             )

  val spark = SparkSession
    .builder()
    .appName("SparkSessionZipsExample")
    .master("local[*]")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()

  import spark.implicits._

  val df = spark
    .sqlContext
    .read
    .format("jdbc")
    .option("driver", "org.postgresql.Driver")
    .option("url", "jdbc:postgresql://localhost/immobilier")
    .option("user", "postgres")
    .option("password", "21544314")
    .option("dbtable", "valeursfonciere")
    .load().as[ValeursFoncieres]


  def getAddress(address: String) = {

    val data: String = scala.io.Source.fromURL("https://nominatim.openstreetmap.org/search/22%20RUE%20LAURENCE%20LEBOUCHER%2072190?format=json").mkString
    val json = Json.parse(data)
    val lat = json \\ "lat"
    val lon = json \\ "lon"
    val latt = lat.head.toString()
    val long = lon.head.toString()
    val latitude = latt.slice(1, latt.length - 1).toDouble
    val longitude = long.slice(1, long.length - 1).toDouble

    val rows = df
      .select("adresse_numero", "adresse_code_voie", "code_postal", "nom_commune", "valeur_fonciere")
      .where(s"latitude <= $latitude + 0.45 and latitude >= $latitude - 0.45 " +
        s" and longitude <= $longitude + 0.083 and longitude >= $longitude - 0.083  and  adresse_numero >0")

    val addr = rows.map(row => (row.getInt(0), row.getString(1), row.getInt(2), row.getString(3), row.getDouble(4)))

  }

  getAddress("dede")
}

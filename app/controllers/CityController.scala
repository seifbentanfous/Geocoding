package controllers

import javax.inject.Inject
import model.Database
import model.geocoding.{ConvertingAddresses}
import play.api.mvc.{AbstractController, ControllerComponents}


class CityController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getCityInfo(city: String) = Action {
    val data = Database.getValueFromCassandraTable(city).toString()
    println(data)
    Ok(data)
  }

  def getCityAddress(address: String) = Action {
    val data = ConvertingAddresses.getAddress(address)
    Ok(data)
  }
}

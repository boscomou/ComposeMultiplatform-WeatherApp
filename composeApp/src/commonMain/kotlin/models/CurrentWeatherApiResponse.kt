package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherApiResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("data")
    val data: List<Data2>
)
@Serializable
data class Data2(
    @SerialName("app_temp")
    val appTemp: Double,
    @SerialName("aqi")
    val aqi: Int,
    @SerialName("city_name")
    val cityName: String,
    @SerialName("clouds")
    val clouds: Int,
    @SerialName("country_code")
    val countryCode: String,
    @SerialName("datetime")
    val datetime: String,
    @SerialName("dewpt")
    val dewpt: Double,
    @SerialName("dhi")
    val dhi: Double,
    @SerialName("dni")
    val dni: Double,
    @SerialName("elev_angle")
    val elevAngle: Double,
    @SerialName("ghi")
    val ghi: Double,
    @SerialName("gust")
    val gust: Double,
    @SerialName("h_angle")
    val hAngle: Double,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("ob_time")
    val obTime: String,
    @SerialName("pod")
    val pod: String,
    @SerialName("precip")
    val precip: Int,
    @SerialName("pres")
    val pres: Double,
    @SerialName("rh")
    val rh: Int,
    @SerialName("slp")
    val slp: Double,
    @SerialName("snow")
    val snow: Int,
    @SerialName("solar_rad")
    val solarRad: Double,
    @SerialName("sources")
    val sources: List<String>,
    @SerialName("state_code")
    val stateCode: String,
    @SerialName("station")
    val station: String,
    @SerialName("sunrise")
    val sunrise: String,
    @SerialName("sunset")
    val sunset: String,
    @SerialName("temp")
    val temp: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("ts")
    val ts: Int,
    @SerialName("uv")
    val uv: Double,
    @SerialName("vis")
    val vis: Int,
    @SerialName("weather")
    val weather: Weather2,
    @SerialName("wind_cdir")
    val windCdir: String,
    @SerialName("wind_cdir_full")
    val windCdirFull: String,
    @SerialName("wind_dir")
    val windDir: Int,
    @SerialName("wind_spd")
    val windSpd: Double
)

@Serializable
data class Weather2(
    @SerialName("code")
    val code: Int,
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)
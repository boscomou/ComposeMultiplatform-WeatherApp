package models
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse(
//

    @SerialName( "city_name")
    val cityName: String,
    @SerialName( "country_code")
    val countryCode: String,
    @SerialName( "data")
    val `data`: List<Data>,
    @SerialName( "lat")
    val lat: Double,
    @SerialName( "lon")
    val lon: Double,
    @SerialName( "state_code")
    val stateCode: String,
    @SerialName( "timezone")
    val timezone: String



)
@Serializable
data class Data(
    @SerialName( "app_max_temp")
    val appMaxTemp: Double,
    @SerialName( "app_min_temp")
    val appMinTemp: Double,
    @SerialName( "clouds")
    val clouds: Int,
    @SerialName( "clouds_hi")
    val cloudsHi: Int,
    @SerialName( "clouds_low")
    val cloudsLow: Int,
    @SerialName( "clouds_mid")
    val cloudsMid: Int,
    @SerialName( "datetime")
    val datetime: String,
    @SerialName( "dewpt")
    val dewpt: Double,
    @SerialName( "high_temp")
    val highTemp: Double,
    @SerialName( "low_temp")
    val lowTemp: Double,
    @SerialName( "max_dhi")
    @Contextual
    val maxDhi: Any,
    @SerialName( "max_temp")
    val maxTemp: Double,
    @SerialName( "min_temp")
    val minTemp: Double,
    @SerialName( "moon_phase")
    val moonPhase: Double,
    @SerialName( "moon_phase_lunation")
    val moonPhaseLunation: Double,
    @SerialName( "moonrise_ts")
    val moonriseTs: Int,
    @SerialName( "moonset_ts")
    val moonsetTs: Int,
    @SerialName( "ozone")
    val ozone: Double,
    @SerialName( "pop")
    val pop: Int,
    @SerialName( "precip")
    val precip: Double,
    @SerialName( "pres")
    val pres: Double,
    @SerialName( "rh")
    val rh: Int,
    @SerialName( "slp")
    val slp: Double,
    @SerialName( "snow")
    val snow: Int,
    @SerialName( "snow_depth")
    val snowDepth: Int,
    @SerialName( "sunrise_ts")
    val sunriseTs: Int,
    @SerialName( "sunset_ts")
    val sunsetTs: Int,
    @SerialName( "temp")
    val temp: Double,
    @SerialName( "ts")
    val ts: Int,
    @SerialName( "uv")
    val uv: Double,
    @SerialName( "valid_date")
    val validDate: String,
    @SerialName( "vis")
    val vis: Double,
    @SerialName( "weather")
    val weather: Weather,
    @SerialName( "wind_cdir")
    val windCdir: String,
    @SerialName( "wind_cdir_full")
    val windCdirFull: String,
    @SerialName( "wind_dir")
    val windDir: Int,
    @SerialName( "wind_gust_spd")
    val windGustSpd: Double,
    @SerialName( "wind_spd")
    val windSpd: Double
)
@Serializable
data class Weather(
    @SerialName( "code")
    val code: Int,
    @SerialName( "description")
    val description: String,
    @SerialName( "icon")
    val icon: String
    
)
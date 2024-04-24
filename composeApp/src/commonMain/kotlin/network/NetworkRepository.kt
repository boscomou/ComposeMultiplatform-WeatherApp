package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import models.CurrentWeatherApiResponse
import models.WeatherForecastsApiResponse

const val API_KEY = "f9bc9f6f041b460ab0e7c15f6fb4e0f5"

class NetworkRepository(private val httpClient: HttpClient) {

    suspend fun getWeatherForecastsList(location:String): Flow<NetWorkResult<WeatherForecastsApiResponse?>> {
        return toResultFlow {
            val response =
                httpClient.get("https://api.weatherbit.io/v2.0/forecast/daily?&city=$location&key=$API_KEY"){
                    contentType(ContentType.Application.Json)
                }.body<WeatherForecastsApiResponse>()

            NetWorkResult.Success(response)
        }


    }

    suspend fun getCurrentWeatherList(location:String): Flow<NetWorkResult<CurrentWeatherApiResponse?>>{
        return toResultFlow {
            val response =
                httpClient.get("https://api.weatherbit.io/v2.0/current?city=$location&key=$API_KEY&include=minutely"){
                    contentType(ContentType.Application.Json)
                }.body<CurrentWeatherApiResponse>()

            NetWorkResult.Success(response)
        }
    }

}





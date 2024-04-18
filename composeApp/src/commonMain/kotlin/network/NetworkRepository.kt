package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import models.CurrentWeatherApiResponse
import models.WeatherForecastsApiResponse

class NetworkRepository(private val httpClient: HttpClient) {

    suspend fun getWeatherForecastsList(): Flow<NetWorkResult<WeatherForecastsApiResponse?>> {
        return toResultFlow {
            val response =
                httpClient.get("https://api.weatherbit.io/v2.0/forecast/daily?city=HongKong&country=HK&key=6f1dc247ee644a61bdbf9d97bd761557"){
                    contentType(ContentType.Application.Json)
                }
                    .body<WeatherForecastsApiResponse>()

            NetWorkResult.Success(response)
        }


    }

    suspend fun getCurrentWeatherList(): Flow<NetWorkResult<CurrentWeatherApiResponse?>>{
        return toResultFlow {
            val response =
                httpClient.get("https://api.weatherbit.io/v2.0/current?city=HongKong&country=HK&key=6f1dc247ee644a61bdbf9d97bd761557&include=minutely"){
                    contentType(ContentType.Application.Json)
                }
                    .body<CurrentWeatherApiResponse>()

            NetWorkResult.Success(response)
        }
    }

}





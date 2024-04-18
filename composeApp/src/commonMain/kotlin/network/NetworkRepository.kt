package network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import io.kamel.core.Resource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.ApiResponse
import models.ApiResponse2

class NetworkRepository(private val httpClient: HttpClient) {

    suspend fun getProductList(): Flow<NetWorkResult<ApiResponse?>> {
        return toResultFlow {
            val response =
                httpClient.get("https://api.weatherbit.io/v2.0/forecast/daily?city=HongKong&country=HK&key=6f1dc247ee644a61bdbf9d97bd761557"){
                    contentType(ContentType.Application.Json)
                }
                    .body<ApiResponse>()

            NetWorkResult.Success(response)
        }


    }
}





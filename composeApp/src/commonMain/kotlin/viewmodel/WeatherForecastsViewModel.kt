package viewmodel;

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.WeatherForecastsApiResponse
import network.ApiStatus
import network.NetworkRepository


class WeatherForecastsViewModel(private val networkRepository: NetworkRepository) {

    private val _weatherForecastsState = MutableStateFlow(WeatherForecastsState())
    private val _weatherForecastsViewState: MutableStateFlow<WeatherForecastsScreenState> = MutableStateFlow(WeatherForecastsScreenState.Loading)
    val weatherForecastsViewState = _weatherForecastsViewState.asStateFlow()

    suspend fun getWeatherForecasts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                networkRepository.getWeatherForecastsList().collect { response ->
                    try {
                        when (response.status) {
                            ApiStatus.LOADING -> {
                                _weatherForecastsState.update { it.copy(isLoading = true) }

                            }

                            ApiStatus.SUCCESS -> {
                                _weatherForecastsState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "",
                                        response.data
                                    )
                                }
                            }

                            ApiStatus.ERROR -> {
                                Logger.e("mytag4") { "err" }
                                _weatherForecastsState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = response.message
                                    )
                                }
                            }
                        }
                    }catch (e: Exception){
                        Logger.e("mytag100"){"Dfs"}
                    }

                    _weatherForecastsViewState.value = _weatherForecastsState.value.toUiState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.e("mytag2"){e.message.toString()}

                _weatherForecastsState.update { it.copy(isLoading = false, errorMessage = "Failed to fetch data") }
            }
        }
    }


    sealed class WeatherForecastsScreenState {
        data object Loading: WeatherForecastsScreenState()
        data class Error(val errorMessage: String):WeatherForecastsScreenState()
        data class Success(val responseData: WeatherForecastsApiResponse):WeatherForecastsScreenState()
    }
    private data class WeatherForecastsState(
        val isLoading:Boolean = false,
        val errorMessage: String?=null,
        val responseData: WeatherForecastsApiResponse? =null
    ) {
        fun toUiState(): WeatherForecastsScreenState {
            return if (isLoading) {
                WeatherForecastsScreenState.Loading
            } else if(errorMessage?.isNotEmpty()==true) {
                WeatherForecastsScreenState.Error(errorMessage)
            } else {
                WeatherForecastsScreenState.Success(responseData!!)
            }
        }
    }
}
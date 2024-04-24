package viewmodel;

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.CurrentWeatherApiResponse
import network.ApiStatus
import network.NetworkRepository


class CurrentWeatherViewModel(private val networkRepository: NetworkRepository) {

    private val _currentWeatherState = MutableStateFlow(CurrentWeatherState())
    private val _currentWeatherViewState: MutableStateFlow<CurrentWeatherScreenState> = MutableStateFlow(CurrentWeatherScreenState.Loading)
    val currentWeatherViewState = _currentWeatherViewState.asStateFlow()

    suspend fun getCurrentWeather(location:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                networkRepository.getCurrentWeatherList(location).collect { response ->

                        when (response.status) {
                            ApiStatus.LOADING -> {
                                _currentWeatherState.update { it.copy(isLoading = true) }

                            }

                            ApiStatus.SUCCESS -> {
                                _currentWeatherState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "",
                                        response.data
                                    )
                                }
                            }

                            ApiStatus.ERROR -> {
                                Logger.e("mytag4") { "err" }
                                _currentWeatherState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = response.message
                                    )
                                }
                            }
                        }


                    _currentWeatherViewState.value = _currentWeatherState.value.toUiState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.e("mytagcurrent"){e.message.toString()}

                _currentWeatherState.update { it.copy(isLoading = false, errorMessage = "Failed to fetch data") }
            }
        }
    }


    sealed class CurrentWeatherScreenState {
        data object Loading: CurrentWeatherScreenState()
        data class Error(val errorMessage: String):CurrentWeatherScreenState()
        data class Success(val responseData: CurrentWeatherApiResponse):CurrentWeatherScreenState()
    }
    private data class CurrentWeatherState(
        val isLoading:Boolean = false,
        val errorMessage: String?=null,
        val responseData: CurrentWeatherApiResponse? =null
    ) {
        fun toUiState(): CurrentWeatherScreenState {
            return if (isLoading) {
                CurrentWeatherScreenState.Loading
            } else if(errorMessage?.isNotEmpty()==true) {
                CurrentWeatherScreenState.Error(errorMessage)
            } else {
                CurrentWeatherScreenState.Success(responseData!!)
            }
        }
    }
}
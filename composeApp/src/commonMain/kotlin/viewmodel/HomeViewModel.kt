package viewmodel;

import co.touchlab.kermit.Logger
import io.github.aakira.napier.Napier
import io.kamel.core.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import models.ApiResponse
import network.ApiStatus
import network.NetworkRepository


class HomeViewModel(private val networkRepository: NetworkRepository) {

    private val _homeState = MutableStateFlow(HomeState())
    private val _homeViewState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Loading)
    val homeViewState = _homeViewState.asStateFlow()

    suspend fun getProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                networkRepository.getProductList().collect { response ->
                    try {
                        when (response.status) {
                            ApiStatus.LOADING -> {
                                _homeState.update { it.copy(isLoading = true) }

                            }

                            ApiStatus.SUCCESS -> {
                                _homeState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "",
                                        response.data
                                    )
                                }
                            }

                            ApiStatus.ERROR -> {
                                Logger.e("mytag4") { "err" }
                                _homeState.update {
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

                    _homeViewState.value = _homeState.value.toUiState()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.e("mytag2"){e.message.toString()}

                _homeState.update { it.copy(isLoading = false, errorMessage = "Failed to fetch data") }
            }
        }
    }
    sealed class HomeScreenState {
        data object Loading: HomeScreenState()
        data class Error(val errorMessage: String):HomeScreenState()
        data class Success(val responseData: ApiResponse):HomeScreenState()
    }
    private data class HomeState(
        val isLoading:Boolean = false,
        val errorMessage: String?=null,
        val responseData: ApiResponse? =null
    ) {
        fun toUiState(): HomeScreenState {
            return if (isLoading) {
                HomeScreenState.Loading
            } else if(errorMessage?.isNotEmpty()==true) {
                HomeScreenState.Error(errorMessage)
            } else {
                HomeScreenState.Success(responseData!!)
            }
        }
    }
}
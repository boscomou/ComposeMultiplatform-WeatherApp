package screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import models.Data
import models.Data2

import org.koin.compose.getKoin
import viewmodel.CurrentWeatherViewModel
import viewmodel.WeatherForecastsViewModel


@Composable
fun HomeScreen(){
    val weatherForecastsViewModel: WeatherForecastsViewModel = getKoin().get()
    val weatherForecastsScreenState by weatherForecastsViewModel.weatherForecastsViewState.collectAsState()
    var weatherForecasts by remember { mutableStateOf<List<Data>?>(null) }

    val currentWeatherViewModel: CurrentWeatherViewModel = getKoin().get()
    val currentWeatherScreenState by currentWeatherViewModel.currentWeatherViewState.collectAsState()
    var currentWeather by remember { mutableStateOf<List<Data2>?>(null) }

    LaunchedEffect(Unit) {
        weatherForecastsViewModel.getWeatherForecasts()
    }
    when (weatherForecastsScreenState) {
        is WeatherForecastsViewModel.WeatherForecastsScreenState.Loading -> {
            Logger.i("mytag") {"load" }
        }
        is WeatherForecastsViewModel.WeatherForecastsScreenState.Success -> {
            weatherForecasts = (weatherForecastsScreenState as WeatherForecastsViewModel.WeatherForecastsScreenState.Success).responseData.data
            Logger.i("mytag") { "success" }

        }
        is WeatherForecastsViewModel.WeatherForecastsScreenState.Error -> {

        }
    }

    LaunchedEffect(Unit) {
        currentWeatherViewModel.getCurrentWeather()
    }
    when (currentWeatherScreenState) {
        is CurrentWeatherViewModel.CurrentWeatherScreenState.Loading -> {
            Logger.i("mytag") {"load" }
        }
        is CurrentWeatherViewModel.CurrentWeatherScreenState.Success -> {
            currentWeather = (currentWeatherScreenState as CurrentWeatherViewModel.CurrentWeatherScreenState.Success).responseData.data
            Logger.i("mytag") { "success" }

        }
        is CurrentWeatherViewModel.CurrentWeatherScreenState.Error -> {

        }
    }


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Hong Kong")
        Text(currentWeather?.get(0)?.temp.toString())
        Text("Description")
        Text("Highest Temperature: ,Lowest Temperature: ")
        Card(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            elevation = 10.dp
        ) {
            Row {
                Text("now")
                Text("1 hour later")
            }

        }

        Column {

            weatherForecasts?.forEach { weatherData ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(15.dp),

                    elevation = 10.dp
                ) {
                    Row {
                        Text(weatherData.datetime)
                        Text(weatherData.maxTemp.toString())
                        Text(weatherData.minTemp.toString())
                    }

                }
            }
        }
    }

}
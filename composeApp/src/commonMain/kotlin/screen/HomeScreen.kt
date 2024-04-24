package screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.touchlab.kermit.Logger
import component.iconSelector
import composemultiplatformweatherapp.composeapp.generated.resources.Res
import composemultiplatformweatherapp.composeapp.generated.resources.sky
import models.Data
import models.Data2
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

import org.koin.compose.getKoin
import viewmodel.CurrentWeatherViewModel
import viewmodel.WeatherForecastsViewModel


@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(){
    val weatherForecastsViewModel: WeatherForecastsViewModel = getKoin().get()
    val weatherForecastsScreenState by weatherForecastsViewModel.weatherForecastsViewState.collectAsState()
    var weatherForecasts by remember { mutableStateOf<List<Data>?>(null) }
    var weatherForecastsFinishLoading by remember { mutableStateOf(false)}

    val currentWeatherViewModel: CurrentWeatherViewModel = getKoin().get()
    val currentWeatherScreenState by currentWeatherViewModel.currentWeatherViewState.collectAsState()
    var currentWeather by remember { mutableStateOf<List<Data2>?>(null) }
    var currentWeatherFinishLoading by remember { mutableStateOf(false) }

    var showPopUp by remember { mutableStateOf(false) }
    val places by remember { mutableStateOf(mutableListOf("Hong Kong", "New York")) }
    var newPlace by remember { mutableStateOf("") }
    var selectedPlaceIndex by remember { mutableStateOf(0) }


    LaunchedEffect(selectedPlaceIndex) {
        weatherForecastsViewModel.getWeatherForecasts(places[selectedPlaceIndex])
    }
    when (weatherForecastsScreenState) {
        is WeatherForecastsViewModel.WeatherForecastsScreenState.Loading -> {
            Logger.i("mytag") {"load" }
            weatherForecastsFinishLoading = false
        }
        is WeatherForecastsViewModel.WeatherForecastsScreenState.Success -> {
            weatherForecasts = (weatherForecastsScreenState as WeatherForecastsViewModel.WeatherForecastsScreenState.Success).responseData.data
            Logger.i("mytag") { "success" }
            weatherForecastsFinishLoading = true

        }
        is WeatherForecastsViewModel.WeatherForecastsScreenState.Error -> {

        }
    }

    LaunchedEffect(selectedPlaceIndex) {
        currentWeatherViewModel.getCurrentWeather(places[selectedPlaceIndex])
    }
    when (currentWeatherScreenState) {
        is CurrentWeatherViewModel.CurrentWeatherScreenState.Loading -> {
            Logger.i("mytagcurrent") {"load" }
            currentWeatherFinishLoading = false
        }
        is CurrentWeatherViewModel.CurrentWeatherScreenState.Success -> {
            currentWeather = (currentWeatherScreenState as CurrentWeatherViewModel.CurrentWeatherScreenState.Success).responseData.data
            Logger.i("mytagcurrent") { "success" }
            currentWeatherFinishLoading = true

        }
        is CurrentWeatherViewModel.CurrentWeatherScreenState.Error -> {
            Logger.i("mytagcurrent") { "error" }
        }
    }

    if(showPopUp){
        Dialog(
            onDismissRequest = { showPopUp = false },
            content = {
                Column(modifier = Modifier.background(Color.White)) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = newPlace,
                        onValueChange = { newPlace = it },
                        label = { Text(text = "New Location") },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done // Set the keyboard action to Done
                        ),
                    )


                    Button(onClick = { showPopUp = false
                        places.add(newPlace)
                        selectedPlaceIndex = places.lastIndex
                        newPlace=""}) {
                        Text("Ok")

                    }
                }
            }

        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
//        if (weatherForecastsFinishLoading == true && currentWeatherFinishLoading == true) {
            Image(
                painter = painterResource(Res.drawable.sky),
                null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds,

                )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TabRow(
                        modifier = Modifier.width(350.dp),
                        selectedTabIndex = selectedPlaceIndex,
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White,
                        indicator = { tabPositions ->
                            tabPositions.getOrNull(selectedPlaceIndex)
                                ?.let { Modifier.tabIndicatorOffset(it) }?.let {
                                    TabRowDefaults.Indicator(
                                        //                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedPlaceIndex]),
                                        modifier = it,
                                        color = Color.White
                                    )
                                }
                        }
                    ) {
                        places.forEachIndexed { index, place ->
                            Tab(
                                selected = selectedPlaceIndex == index,
                                onClick = {
                                    selectedPlaceIndex = index
                                    Logger.e("yo2") { selectedPlaceIndex.toString() }
                                },
                                text = { Text(place) }
                            )
                        }
                    }

                    Button(
                        onClick = {
                            showPopUp = true


                        }
                    ) {
//                    Icon(Icons.Default.Add, contentDescription = "Add Place")
                        Text("+")
                    }
                }

                Text(places[selectedPlaceIndex])
                Text(currentWeather?.get(0)?.temp.toString())
                Text("humidity: " + currentWeather?.get(0)?.rh.toString() + " %")
                Text(currentWeather?.get(0)?.weather?.description.toString())
                currentWeather?.get(0)?.weather?.let { iconSelector(it.code) }
                    ?.let { painterResource(it) }?.let {
                        Image(
                            painter = it,
                            null,
                            modifier = Modifier.size(50.dp).padding(end = 16.dp)


                        )
                    }
                Text("Highest Temperature: "+ (weatherForecasts?.get(0)?.maxTemp ?: null) +" Lowest Temperature: " + (weatherForecasts?.get(0)?.lowTemp
                    ?:null ))


                Column {
                    Card(
                        modifier = Modifier.padding(15.dp),
                        elevation = 0.dp,
                        backgroundColor = Color.Black.copy(alpha = 0.1f),

                        ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(15.dp).verticalScroll(
                                rememberScrollState()
                            ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            weatherForecasts?.forEach { weatherData ->
                                Card(
                                    modifier = Modifier.fillMaxWidth().height(90.dp).padding(5.dp)
                                        .clip(
                                            RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)
                                        ),
                                    elevation = 0.dp,
                                    backgroundColor = Color.Blue.copy(alpha = 0.1f),

                                    ) {

                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = weatherData.datetime,
                                            color = Color.White,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Image(
                                            painter = painterResource(iconSelector(weatherData.weather.code)),
                                            null,
                                            modifier = Modifier.size(50.dp).padding(end = 16.dp)


                                        )
                                        Text(
                                            text = weatherData.maxTemp.toString(),
                                            color = Color.White,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        Text(
                                            text = weatherData.minTemp.toString(),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


        }

    }

}
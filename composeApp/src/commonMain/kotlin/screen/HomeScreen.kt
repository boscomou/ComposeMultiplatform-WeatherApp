package screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import models.Products

import org.koin.compose.getKoin
import viewmodel.HomeViewModel


@Composable
fun HomeScreen(){
    val viewModel: HomeViewModel = getKoin().get()
    val homeScreenState by viewModel.homeViewState.collectAsState()
    var homeList by remember { mutableStateOf<List<Data>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }
    when (homeScreenState) {
        is HomeViewModel.HomeScreenState.Loading -> {
            Logger.i("mytag") {"load" }
        }
        is HomeViewModel.HomeScreenState.Success -> {
            homeList = (homeScreenState as HomeViewModel.HomeScreenState.Success).responseData.data
            Logger.i("mytag") { "success" }

        }
        is HomeViewModel.HomeScreenState.Error -> {



        }
    }


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Column (){

            homeList?.forEach {weatherForecast->

                Text(weatherForecast.maxTemp)
                println(weatherForecast.weather)


            }
        }
        Text("My Location")
        Text("Temperature")
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
        Card(
            modifier = Modifier.fillMaxWidth().padding(15.dp),

            elevation = 10.dp
        ) {
            Column {
                Text("today")
                Text("Tommorow")
            }

        }
    }

}
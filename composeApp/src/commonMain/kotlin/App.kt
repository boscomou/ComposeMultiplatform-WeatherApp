import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

import composemultiplatformweatherapp.composeapp.generated.resources.Res
import composemultiplatformweatherapp.composeapp.generated.resources.compose_multiplatform
import di.appModule
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import screen.HomeScreen
import screen.PreviewScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                HomeScreen()
            }
        }
    }

}
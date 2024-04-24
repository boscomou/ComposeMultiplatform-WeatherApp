import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TwoSidedSlider() {
    val minValue = 0f
    val maxValue = 100f

    val leftSliderValue = remember { mutableStateOf(minValue) }
    val rightSliderValue = remember { mutableStateOf(maxValue) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Left Value: ${leftSliderValue.value}")
        Slider(
            value = leftSliderValue.value,
            onValueChange = { newValue ->
                if (newValue < rightSliderValue.value) {
                    leftSliderValue.value = newValue
                }
            },
            valueRange = minValue..rightSliderValue.value
        )

        Text("Right Value: ${rightSliderValue.value}")
        Slider(
            value = rightSliderValue.value,
            onValueChange = { newValue ->
                if (newValue > leftSliderValue.value) {
                    rightSliderValue.value = newValue
                }
            },
            valueRange = leftSliderValue.value..maxValue
        )
    }
}
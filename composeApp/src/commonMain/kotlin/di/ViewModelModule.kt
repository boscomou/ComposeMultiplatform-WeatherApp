package di


import org.koin.dsl.module
import viewmodel.CurrentWeatherViewModel
import viewmodel.WeatherForecastsViewModel

val provideviewModelModule = module {
    single {
        WeatherForecastsViewModel(get())
    }
    single { CurrentWeatherViewModel(get()) }
}
package component

import composemultiplatformweatherapp.composeapp.generated.resources.ClearSky
import composemultiplatformweatherapp.composeapp.generated.resources.Cloud
import composemultiplatformweatherapp.composeapp.generated.resources.Drizzle
import composemultiplatformweatherapp.composeapp.generated.resources.FewCloud
import composemultiplatformweatherapp.composeapp.generated.resources.Rain
import composemultiplatformweatherapp.composeapp.generated.resources.Res
import composemultiplatformweatherapp.composeapp.generated.resources.Sleet
import composemultiplatformweatherapp.composeapp.generated.resources.Snow
import composemultiplatformweatherapp.composeapp.generated.resources.ThunderstormWithRain
import composemultiplatformweatherapp.composeapp.generated.resources.Unknown
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
fun iconSelector(code: Int): DrawableResource {

    if(code == 200 || code == 201 || code == 202 ){

        return Res.drawable.ThunderstormWithRain
    }

    else if(code == 230 || code == 231 ||code ==232 || code == 233){

        return Res.drawable.ThunderstormWithRain
    }

    else if(code == 300 || code==301 || code == 302 ) {
        return Res.drawable.Drizzle
    }
    else if(code == 500 || code == 501 || code == 502 || code == 511 || code == 520 || code == 521 || code == 522){
        return Res.drawable.Rain
    }

    else if (code==600 || code == 601 || code == 602 || code==610 || code == 621 || code==622 || code == 623){
        return Res.drawable.Snow
    }

    else if ( code==611 || code==612 ||code == 700 || code==711 || code== 721 || code==731 || code ==741 || code == 751 ){
        return  Res.drawable.Sleet
    }
    else if( code==800){
        return Res.drawable.ClearSky
    }
    else if( code==801 || code == 802){
        return Res.drawable.FewCloud
    }
    else if (code == 803 || code == 804){
        return  Res.drawable.Cloud
    }
    else{
        return Res.drawable.Unknown
    }

}
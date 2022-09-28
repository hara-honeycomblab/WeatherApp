package com.example.weatherapp

data class WeatherData(
    val precipAverage: PrecipAverage,
    val publishingOffice: String,
    val reportDatetime: String,
    val tempAverage: TempAverage,
    val timeSeries: List<TimeSery>
)

data class PrecipAverage(
    val areas: List<Area>
)

data class TempAverage(
    val areas: List<Area>
)

data class TimeSery(
    val areas: List<AreaXXXX>,
    val timeDefines: List<String>
)

data class AreaXXXX(
    val area: AreaX,
    val pops: List<String>,
    val reliabilities: List<String>,
    val temps: List<String>,
    val tempsMax: List<String>,
    val tempsMaxLower: List<String>,
    val tempsMaxUpper: List<String>,
    val tempsMin: List<String>,
    val tempsMinLower: List<String>,
    val tempsMinUpper: List<String>,
    val waves: List<String>,
    val weatherCodes: List<String>,
    val weathers: List<String>,
    val winds: List<String>
)

data class AreaX(
    val code: String,
    val name: String
)


data class Area(
    val area: AreaX,
    val max: String,
    val min: String
)
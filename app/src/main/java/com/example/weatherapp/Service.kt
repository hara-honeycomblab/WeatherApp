package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList
import io.reactivex.rxjava3.core.Observable

interface Service {
    @GET("data/forecast/130000.json")
    fun getWeather() : Observable<ArrayList<WeatherData>>
}
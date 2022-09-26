package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.*

interface Service {
    @GET("data/forecast/130000.json")
    fun getWeather(): Call<WeatherData>
}
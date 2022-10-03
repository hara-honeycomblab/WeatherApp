package com.example.weatherapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    enum class timeState(val number: Int, val string: String) {
        Today(0, "今日"),
        Tomorrow(1, "明日"),
        DATomorrow(2, "明後日")
    }

    enum class areaName(val number: Int) {
        Tokyo(0),
        NorthernIzu(1),
        SouthernIzu(2),
        Ogasawara(3)
    }

    val httpBuilder: OkHttpClient.Builder get() {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .build()

            var response = chain.proceed(request)

            return@Interceptor response
        })
        return httpClient
    }

    var client = httpBuilder.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.jma.go.jp/bosai/forecast/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestWeatherData()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, HomeFragment.newInstance())
            .commit()
    }

    fun requestWeatherData(){
        val service = retrofit.create(IApiService::class.java)
        service.getWeather()
            .subscribeOn(Schedulers.io())
            .subscribe({
                val gson = Gson()
                val sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("data", gson.toJson(it))
                editor.apply()
            }, {
                Log.e(TAG, "")
            })
    }
}
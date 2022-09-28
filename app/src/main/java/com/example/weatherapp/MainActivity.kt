package com.example.weatherapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.notifyAll
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    enum class timeState(val time: Int) {
        Today(0),
        Tomorrow(1),
        DATomorrow(2)
    }
    lateinit var text: TextView
    var weatherData: ArrayList<WeatherData> = ArrayList()

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
        var btn: Button = findViewById(R.id.button)
        text = findViewById(R.id.weatherView)
        requestWeatherData()

        btn.setOnClickListener {
            selectWeatherData(timeState.Today.time)
        }
    }

   fun selectWeatherData(number: Int) {
       text.text = weatherData.get(0).timeSeries[0].areas[0].weathers[number]
    }

    private fun requestWeatherData(){
        val service = retrofit.create(Service::class.java)
        service.getWeather()
            .subscribeOn(Schedulers.io())
            .subscribe({
                weatherData = it
            }, {
                Log.e(TAG, "")
            })
    }
}
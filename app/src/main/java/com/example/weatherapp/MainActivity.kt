package com.example.weatherapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.subscriptions.CompositeSubscription
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    enum class timeState(val time: Int) {
        Today(0),
        Tomorrow(1),
        DATomorrow(2)
    }
    lateinit var text: TextView
//    private val compositeSubscription = CompositeSubscription()

    private val client = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.jma.go.jp/bosai/forecast/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
//        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn: Button = findViewById(R.id.button)
        text = findViewById(R.id.weatherView)

        btn.setOnClickListener {
            requestWeatherData()
        }
    }

    fun requestWeatherData() {
        thread {
            try {
                timeWeatherData(timeState.Today.time)
            } catch (e: Exception) {
                Log.e("response-weather", "debug $e")
            }
        }
    }
    private fun timeWeatherData(number: Int){
        val service = retrofit.create(Service::class.java)
        val weatherApiResponse = service.getWeather().execute().body()

        Handler(Looper.getMainLooper()).post {
            //get(0)で東京地方
            var word = weatherApiResponse?.get(0)!!.timeSeries[0].areas[0].weathers[number]
            text.text = word
            Log.e("response-weather", word)
        }
    }
}
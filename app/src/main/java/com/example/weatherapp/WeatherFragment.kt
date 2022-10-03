package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

class WeatherFragment : Fragment() {
    lateinit var iconImage: ImageView
    lateinit var areaText: TextView
    lateinit var weatherText: TextView
    lateinit var windText: TextView
    lateinit var waveText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var headerView: TextView = view.findViewById(R.id.headerText)
        areaText = view.findViewById(R.id.areaText)
        iconImage = view.findViewById(R.id.iconImage)
        weatherText = view.findViewById(R.id.weatherText)
        windText = view.findViewById(R.id.windText)
        waveText = view.findViewById(R.id.waveText)
        var data = getArguments()?.getString("data")
        Log.e("data", data.toString())
        if(MainActivity.timeState.Today.string == data){
            headerView.text = "${data}の天気"
            assignmentData(MainActivity.timeState.Today.number,
                            MainActivity.areaName.Tokyo.number)
        }
        else if(MainActivity.timeState.Tomorrow.string == data){
            headerView.text = "${data}の天気"
            assignmentData(MainActivity.timeState.Tomorrow.number,
                            MainActivity.areaName.Tokyo.number)
        }
        else {
            headerView.text = "${data}の天気"
            assignmentData(MainActivity.timeState.DATomorrow.number,
                            MainActivity.areaName.Tokyo.number)
        }
    }

    fun assignmentData(time: Int, area: Int) {
        var data = getWeatherData(time, area)
        var url = "https://www.jma.go.jp/bosai/forecast/img/${data.weatherCodes}.svg"
        Picasso.get()
            .load(url)
            .into(iconImage)
        areaText.text = data.name
        weatherText.text = data.weathers
        windText.text = data.winds
        waveText.text = data.waves
    }

    fun getWeatherData(time: Int, area: Int): Model{
        val gson = Gson()
        val sharedPreferences = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        var weatherData = gson.fromJson<ArrayList<WeatherData>>(
            sharedPreferences?.getString("data", null),
            object : TypeToken<ArrayList<WeatherData?>?>() {}.type
        )
        return selectModel(weatherData, time, area)
    }

    fun selectModel(weatherData: ArrayList<WeatherData>, time: Int, area: Int) : Model{
        var source = weatherData.get(0).timeSeries[0].areas[area]
        return Model(
            source.area.name,//名前
            source.weatherCodes[time],//アイコン
            source.weathers[time],//天気
            source.winds[time],//風
            source.waves[time],//波
        )
    }

    fun newInstance(string: String?): WeatherFragment {
        val fragment = WeatherFragment()
        val args = Bundle()
        args.putString("data", string)
        fragment.setArguments(args)
        return fragment
    }
}
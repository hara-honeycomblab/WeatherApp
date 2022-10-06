package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WeatherFragment : Fragment() {
    private lateinit var spinner: Spinner
    private lateinit var iconImage: ImageView
    private lateinit var areaText: TextView
    private lateinit var weatherText: TextView
    private lateinit var windText: TextView
    private lateinit var waveText: TextView
    private var timeState: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner = view.findViewById(R.id.areaSpinner)
        var headerView: TextView = view.findViewById(R.id.headerText)
        areaText = view.findViewById(R.id.areaText)
        iconImage = view.findViewById(R.id.iconImage)
        weatherText = view.findViewById(R.id.weatherText)
        windText = view.findViewById(R.id.windText)
        waveText = view.findViewById(R.id.waveText)
        var data = getArguments()?.getString("data")
        var title = "${data}の天気"
        if(MainActivity.timeState.Today.string == data){
            headerView.text = title
            timeState = MainActivity.timeState.Today.number
            assignmentData(timeState, MainActivity.areaName.Tokyo.number)
        }
        else if(MainActivity.timeState.Tomorrow.string == data){
            headerView.text = title
            timeState = MainActivity.timeState.Tomorrow.number
            assignmentData(timeState, MainActivity.areaName.Tokyo.number)
        }
        else {
            headerView.text = title
            timeState = MainActivity.timeState.DATomorrow.number
            assignmentData(timeState, MainActivity.areaName.Tokyo.number)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                assignmentData(timeState, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }

    fun assignmentData(time: Int, area: Int) {
        var data = getWeatherData(time, area)
        var url = "https://www.jma.go.jp/bosai/forecast/img/${data.weatherCodes}.svg"
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
package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DATomorrowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_d_a_tomorrow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var todayText : TextView = view.findViewById(R.id.daTomorrowText)
        todayText.text = getWeatherData().toString()
    }

    fun getWeatherData() : ArrayList<WeatherData>{
        val gson = Gson()
        val sharedPreferences = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        var weatherData = gson.fromJson<ArrayList<WeatherData>>(
            sharedPreferences?.getString("data", null),
            object : TypeToken<ArrayList<WeatherData?>?>() {}.type
        )
        return weatherData
    }

    companion object {
        @JvmStatic
        fun newInstance() = DATomorrowFragment()
    }
}
package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.weatherapp.HomeFragment.Companion.newInstance

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var todayBtn : Button = view.findViewById(R.id.todayBtn)
        var tomorrowBtn : Button = view.findViewById(R.id.tomorrowBtn)
        var daTomorrowBtn : Button = view.findViewById(R.id.daTomorrowBtn)

        todayBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.main, WeatherFragment().newInstance(MainActivity.timeState.Today.string))
                .commit()
        }

        tomorrowBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.main, WeatherFragment().newInstance(MainActivity.timeState.Tomorrow.string))
                .commit()
        }

        daTomorrowBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.main, WeatherFragment().newInstance(MainActivity.timeState.DATomorrow.string))
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
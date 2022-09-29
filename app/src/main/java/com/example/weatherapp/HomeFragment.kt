package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
                .replace(R.id.main, TodayFragment.newInstance())
                .commit()
        }

        tomorrowBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.main, TomorrowFragment.newInstance())
                .commit()
        }

        daTomorrowBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.main, DATomorrowFragment.newInstance())
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
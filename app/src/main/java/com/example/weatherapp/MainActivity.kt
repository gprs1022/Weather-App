package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import com.example.weatherapp.databinding.ActivityMainBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
   private  val  binding : ActivityMainBinding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        fetchWeatherData("Satna")
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response = retrofit.getWeatherData("jaipur",  "206b452c3aa396deaefcb84c8d771c67", "metric")

        response.enqueue (object: Callback<WeatherApp> {
            override fun onResponse (call: Call<WeatherApp>, response: Response<WeatherApp>) {
            val responseBody = response.body()
            if (response.isSuccessful && responseBody !=null) {

                val temperature = responseBody.main.temp.toString()
                val humidity = responseBody.main.humidity
                val windSpeed = responseBody.wind.speed
                val sunRise = responseBody.sys.sunrise
                val sunset= responseBody.sys.sunset
                val seaLevel = responseBody.main.pressure
                val condition = responseBody.weather.firstOrNull()?.main?: "unknown"
                val maxTemp = responseBody.main.temp_max
                val minTemp = responseBody.main.temp_min
                binding.temp.text= "$temperature °C"
                binding.weather.text = condition
                binding.maxTmp.text = "Max Temp: $maxTemp °C"
                binding.minTemp.text = "Min Temp: $minTemp °C"
                binding.humidity.text = "$humidity %"
                binding.windSpeed. text = "$windSpeed m/s"
                binding.sunRise.text= "$sunRise"
                binding.sunSet.text= "$sunset"
                binding.sea.text = "$seaLevel hPa"
                binding.conditions.text = condition
                binding.day.text =dayName(System.currentTimeMillis())
                binding.date.text =date()
                binding.cityName.text=" $cityName"

             //Log.d("TAG", "onResponse: $temperature")

            //Log.d("TAG", "on Response: $temperature")
        }
        }
            override fun onFailure (call: Call<WeatherApp>, t: Throwable) {

        }
        })


    }

     private fun date(): String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }

    fun dayName(timestamp : Long): String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }
}
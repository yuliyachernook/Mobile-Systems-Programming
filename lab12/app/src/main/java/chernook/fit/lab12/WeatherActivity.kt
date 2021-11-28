package chernook.fit.lab12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import chernook.fit.lab12.weather.*
import chernook.fit.lab12.weather.service.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity() {
    private var textViewCity: TextView?=null
    private var textViewWeather: TextView?=null
    private var textViewTemperature: TextView?=null
    private var textViewPressure: TextView?=null
    private var textViewError: TextView?=null
    private var buttonGetWeather: Button?=null
    private var countrySpinner: Spinner?=null

    private fun initializeWidgets(){
        textViewCity = findViewById(R.id.textViewCity)
        textViewWeather = findViewById(R.id.textViewWeather)
        textViewTemperature = findViewById(R.id.textViewTemperature)
        textViewPressure = findViewById(R.id.textViewPressure)
        textViewError = findViewById(R.id.textViewError)
        buttonGetWeather = findViewById(R.id.buttonGetWeather)
        countrySpinner = findViewById<Spinner>(R.id.spinner)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initializeWidgets()
        buttonGetWeather!!.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                getWeatherData()
            }
        }
    }

    private fun getWeatherData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherService::class.java)
        val call = service.getCurrentWeatherData(countrySpinner!!.selectedItem.toString(), appId)

        call.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()!!
                    textViewCity!!.text = weatherResponse.name
                    textViewWeather!!.text = weatherResponse.weather[0].main
                    textViewTemperature!!.text = weatherResponse.main!!.temp.toString()
                    textViewPressure!!.text = weatherResponse.main!!.pressure.toString()
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                textViewError!!.text = t.message
            }
        })
    }

    companion object {
        var baseUrl = "https://api.openweathermap.org/"
        var appId = "1c996d70931c20b73f7f12e8563185bd"
    }
}
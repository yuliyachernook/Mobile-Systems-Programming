package chernook.fit.lab12.weather.service

import chernook.fit.lab12.weather.WeatherResponse
import retrofit2.Call;
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(
        @Query("q") q: String,
        @Query("appid") app_id: String)
    : Call<WeatherResponse>
}
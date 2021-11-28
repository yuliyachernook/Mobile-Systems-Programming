package chernook.fit.lab12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var buttonVk: Button?=null
    private var buttonWeather: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonVk = findViewById(R.id.buttonVkActivity)
        buttonWeather = findViewById(R.id.buttonWeatherActivity)

        buttonVk!!.setOnClickListener {
            val intent = Intent(this, VKActivity::class.java)
            startActivity(intent)
        }
        buttonWeather!!.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }
    }
}
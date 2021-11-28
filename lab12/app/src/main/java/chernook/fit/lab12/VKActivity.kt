package chernook.fit.lab12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import chernook.fit.lab12.adapter.CustomAdapter
import chernook.fit.lab12.vk.*
import chernook.fit.lab12.vk.service.VkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_vk)
        val recyclerView: RecyclerView? = findViewById(R.id.list)
        val errorTextView: TextView? = findViewById(R.id.error)
        val amountTextView: TextView? = findViewById(R.id.friendsNumber)
        val friends = ArrayList<Item>()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(VkService::class.java)
        val call = service.getVkFriendsData(fields, accessToken, version)

        call.enqueue(object : Callback<VKResponse> {
            override fun onResponse(call: Call<VKResponse>, response: Response<VKResponse>) {
                if (response.isSuccessful) {
                    val vkResponse = response.body()!!
                    for (item in vkResponse.response!!.items) {
                        friends.add(item)
                    }
                    val adapter = CustomAdapter(baseContext, friends)
                    recyclerView!!.adapter = adapter
                    amountTextView!!.text = "Total number of friends: " + vkResponse.response!!.count
                }
            }

            override fun onFailure(call: Call<VKResponse>, t: Throwable) {
                errorTextView!!.text = t.message
            }
        })

    }

    companion object {
        var baseUrl = "https://api.vk.com/"
        var accessToken = "0550ec9bb3174081918c7543f40cbe53b754941fca47d2dc55b748e0f8708baf2ff76979815e9d82d1291"
        var fields = "online"
        var version = "5.81"
    }
}
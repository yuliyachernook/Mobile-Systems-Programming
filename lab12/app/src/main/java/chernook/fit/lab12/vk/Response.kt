package chernook.fit.lab12.vk

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class Response{
    @SerializedName("count")
    var count: Int? = null
    @SerializedName("items")
    var items = ArrayList<Item>()
}
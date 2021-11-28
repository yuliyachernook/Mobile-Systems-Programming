package chernook.fit.lab12.vk

import com.google.gson.annotations.SerializedName

class Item{
    @SerializedName("first_name")
    var firstName: String? = null
    @SerializedName("last_name")
    var lastName: String? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("online")
    var online: Int? = null
    @SerializedName("track_code")
    var trackCode: String? = null
}
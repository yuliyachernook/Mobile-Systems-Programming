package chernook.fit.lab12.vk.service
import chernook.fit.lab12.vk.VKResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VkService {
    @GET("method/friends.get?")
    fun getVkFriendsData(@Query("fields") fields: String,
                         @Query("access_token") accessToken: String,
                         @Query("v") v: String ): Call<VKResponse>
}
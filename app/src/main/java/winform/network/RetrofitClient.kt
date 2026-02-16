package winform.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var BASE_URL = "http://default-url/" // default, can be empty

    private var retrofit: Retrofit? = null

    fun setBaseUrl(url: String) {
        BASE_URL = if (url.endsWith("/")) url else "$url/"
        retrofit = null // reset Retrofit instance
    }

    val api: ApiService
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(ApiService::class.java)
        }
}
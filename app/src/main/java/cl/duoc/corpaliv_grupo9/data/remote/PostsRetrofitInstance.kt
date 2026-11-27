package cl.duoc.corpaliv_grupo9.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostsRetrofitInstance {
    val api: PostsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostsApiService::class.java)
    }
}
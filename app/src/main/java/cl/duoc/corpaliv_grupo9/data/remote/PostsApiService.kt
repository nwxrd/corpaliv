package cl.duoc.corpaliv_grupo9.data.remote

import cl.duoc.corpaliv_grupo9.data.model.Post
import retrofit2.http.GET

interface PostsApiService {
    @GET("posts")
    suspend fun listarPosts(): List<Post>
}
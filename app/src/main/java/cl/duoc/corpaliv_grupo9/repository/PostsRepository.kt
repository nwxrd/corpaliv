package cl.duoc.corpaliv_grupo9.repository

import cl.duoc.corpaliv_grupo9.data.model.Post
import cl.duoc.corpaliv_grupo9.data.remote.PostsApiService
import cl.duoc.corpaliv_grupo9.data.remote.PostsRetrofitInstance

class PostsRepository(private val api: PostsApiService = PostsRetrofitInstance.api) {
    suspend fun listar(): List<Post> = api.listarPosts()
}
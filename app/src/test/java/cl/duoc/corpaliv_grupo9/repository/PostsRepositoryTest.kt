package cl.duoc.corpaliv_grupo9.repository

import cl.duoc.corpaliv_grupo9.data.model.Post
import cl.duoc.corpaliv_grupo9.data.remote.PostsApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PostsRepositoryTest {
    private val api = mockk<PostsApiService>()
    private val repository = PostsRepository(api)

    @Test
    fun listarDevuelvePostsDelServicio(): Unit = runBlocking {
        val posts = listOf(Post(userId = 1, id = 10, title = "Título", body = "Contenido"))
        coEvery { api.listarPosts() } returns posts

        val result = repository.listar()
        assertEquals(posts, result)
        assertEquals(1, result.single().userId)
        assertEquals(10, result.single().id)
        assertEquals("Título", result.single().title)
        assertEquals("Contenido", result.single().body)
    }

    @Test
    fun errorDelServicioSePropaga(): Unit = runBlocking {
        coEvery { api.listarPosts() } throws IllegalStateException("fallo")

        assertThrows(IllegalStateException::class.java) {
            runBlocking { repository.listar() }
        }
    }
}

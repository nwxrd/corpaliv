package cl.duoc.corpaliv_grupo9.viewmodel

import cl.duoc.corpaliv_grupo9.MainDispatcherRule
import cl.duoc.corpaliv_grupo9.data.model.Post
import cl.duoc.corpaliv_grupo9.repository.PostsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun cargarActualizaPostsYFinalizaIndicador() = runTest {
        val repository = mockk<PostsRepository>()
        val posts = listOf(Post(userId = 1, id = 1, title = "Título", body = "Contenido"))
        coEvery { repository.listar() } returns posts
        val viewModel = PostsViewModel(repository)

        viewModel.cargar()
        advanceUntilIdle()

        assertEquals(posts, viewModel.posts.value)
        assertFalse(viewModel.cargando.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun cargarAnteFallaExponeErrorYFinalizaIndicador() = runTest {
        val repository = mockk<PostsRepository>()
        coEvery { repository.listar() } throws IllegalStateException("offline")
        val viewModel = PostsViewModel(repository)

        viewModel.cargar()
        advanceUntilIdle()

        assertFalse(viewModel.cargando.value)
        assertEquals("No se pudieron cargar las publicaciones externas.", viewModel.error.value)
    }
}

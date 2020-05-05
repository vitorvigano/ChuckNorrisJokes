package me.vitorvigano.chucknorrisjokes.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import me.vitorvigano.chucknorrisjokes.domain.Joke
import me.vitorvigano.chucknorrisjokes.domain.Result

class JokesRepository(private val dao: JokeDao, private val api: ChuckNorrisApi) {

    @ExperimentalCoroutinesApi
    fun getJokes(): Flow<Result<List<Joke>>> {
        return flow {
            emit(Result.Loading(dao.getJokes().first()))
            try {
                dao.addJoke(api.get())
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "Unknown error"))
            }
            emitAll(dao.getJokes().map { Result.Success(it) })
        }
    }

    suspend fun getJoke(): Result<Long> {
        return try {
            Result.Success(dao.addJoke(api.get()))
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    fun getLastJokeFromCache() = dao.getLastJoke()
}
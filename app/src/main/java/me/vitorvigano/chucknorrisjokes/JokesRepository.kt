package me.vitorvigano.chucknorrisjokes

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

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
}
package me.vitorvigano.chucknorrisjokes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.vitorvigano.chucknorrisjokes.domain.Joke

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJoke(joke: Joke): Long

    @Query("SELECT * FROM jokes ORDER BY jokeId DESC")
    fun getJokes(): Flow<List<Joke>>

    @Query("SELECT * FROM jokes ORDER BY jokeId DESC LIMIT 1")
    fun getLastJoke(): Flow<Joke>
}
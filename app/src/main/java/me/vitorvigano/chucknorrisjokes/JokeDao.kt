package me.vitorvigano.chucknorrisjokes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJoke(joke: Joke): Long

    @Query("SELECT * FROM jokes ORDER BY jokeId DESC")
    fun getJokes(): Flow<List<Joke>>
}
package me.vitorvigano.chucknorrisjokes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import me.vitorvigano.chucknorrisjokes.data.JokeDao
import me.vitorvigano.chucknorrisjokes.domain.Joke

@Database(entities = [Joke::class], version = 1)
abstract class ChuckNorrisDatabase : RoomDatabase() {

    abstract fun jokeDao(): JokeDao
}
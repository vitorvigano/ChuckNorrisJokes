package me.vitorvigano.chucknorrisjokes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Joke::class], version = 1)
abstract class ChuckNorrisDatabase : RoomDatabase() {

    abstract fun jokeDao(): JokeDao
}
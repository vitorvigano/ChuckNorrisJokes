package me.vitorvigano.chucknorrisjokes

import retrofit2.http.GET

interface ChuckNorrisApi {

    // https://api.chucknorris.io

    @GET("jokes/random")
    suspend fun get(): Joke
}
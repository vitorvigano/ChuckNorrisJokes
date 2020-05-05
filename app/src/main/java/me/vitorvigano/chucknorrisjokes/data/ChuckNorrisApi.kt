package me.vitorvigano.chucknorrisjokes.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import me.vitorvigano.chucknorrisjokes.domain.Joke
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ChuckNorrisApi {

    companion object {

        private const val BASE_URL = "https://api.chucknorris.io/"
        private const val TIMEOUT: Long = 5

        private fun getHttpClient(): OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)

        fun build(): Retrofit {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(
                    getHttpClient()
                        .build())
                .build()
        }
    }

    // https://api.chucknorris.io
    @GET("jokes/random")
    suspend fun get(): Joke
}
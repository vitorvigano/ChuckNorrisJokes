package me.vitorvigano.chucknorrisjokes.di

import androidx.room.Room
import me.vitorvigano.chucknorrisjokes.data.ChuckNorrisApi
import me.vitorvigano.chucknorrisjokes.data.ChuckNorrisDatabase
import me.vitorvigano.chucknorrisjokes.data.JokesRepository
import me.vitorvigano.chucknorrisjokes.ui.JokesListViewModel
import me.vitorvigano.chucknorrisjokes.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val allModules = module {
    single {
        Room.databaseBuilder(androidContext(), ChuckNorrisDatabase::class.java, "norris-db").build()
    }
    single { get<ChuckNorrisDatabase>().jokeDao() }
    single<ChuckNorrisApi> { getService()
        .create(ChuckNorrisApi::class.java) }
    single { JokesRepository(get(), get()) }

    viewModel { MainViewModel(get()) }
    viewModel { JokesListViewModel(get()) }
}

private fun getService() =
    ChuckNorrisApi.build()

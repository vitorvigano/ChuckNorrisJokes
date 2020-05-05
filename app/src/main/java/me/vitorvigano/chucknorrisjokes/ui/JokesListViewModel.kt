package me.vitorvigano.chucknorrisjokes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.vitorvigano.chucknorrisjokes.data.JokesRepository
import me.vitorvigano.chucknorrisjokes.domain.Joke
import me.vitorvigano.chucknorrisjokes.domain.Result


class JokesListViewModel(repository: JokesRepository) : ViewModel() {

    @ExperimentalCoroutinesApi
    val jokes: LiveData<Result<List<Joke>>> = repository
        .getJokes().asLiveData()
}
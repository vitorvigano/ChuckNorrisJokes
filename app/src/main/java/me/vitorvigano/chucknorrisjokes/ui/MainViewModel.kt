package me.vitorvigano.chucknorrisjokes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.vitorvigano.chucknorrisjokes.data.JokesRepository
import me.vitorvigano.chucknorrisjokes.domain.Joke

class MainViewModel(private val repository: JokesRepository) : ViewModel() {

    val lastJoke: LiveData<Joke> = repository.getLastJokeFromCache().asLiveData()

    fun onLoadMoreClick() {
        viewModelScope.launch {
            repository.getJoke()
        }
    }
}
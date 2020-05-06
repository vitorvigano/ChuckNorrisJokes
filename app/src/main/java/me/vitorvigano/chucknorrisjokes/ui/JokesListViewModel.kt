package me.vitorvigano.chucknorrisjokes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.vitorvigano.chucknorrisjokes.data.JokesRepository
import me.vitorvigano.chucknorrisjokes.domain.Joke
import me.vitorvigano.chucknorrisjokes.domain.Result


class JokesListViewModel(private val repository: JokesRepository) : ViewModel() {

    private val _jokes: MutableLiveData<Result<List<Joke>>> = MutableLiveData()
    val jokes: LiveData<Result<List<Joke>>> get() = _jokes

    @ExperimentalCoroutinesApi
    fun getJokes(refresh: Boolean) {
        viewModelScope.launch {
            repository.getJokes(refresh).collect {
                _jokes.value = it
            }
        }
    }
}
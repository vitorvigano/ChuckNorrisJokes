package me.vitorvigano.chucknorrisjokes.ui

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.vitorvigano.chucknorrisjokes.data.JokesRepository
import me.vitorvigano.chucknorrisjokes.domain.Joke
import me.vitorvigano.chucknorrisjokes.domain.Result

class MainViewModel(private val repository: JokesRepository) : ViewModel() {

    private val _newJoke: MutableLiveData<Result<Long>> = MutableLiveData()
    val newJoke: LiveData<Result<Long>> get() = _newJoke
    
    val lastJoke: LiveData<Joke> = repository.getLastJokeFromCache().asLiveData()

    fun getNewJoke() {
        viewModelScope.launch {
            _newJoke.value =  repository.getNewJoke()
        }
    }
}
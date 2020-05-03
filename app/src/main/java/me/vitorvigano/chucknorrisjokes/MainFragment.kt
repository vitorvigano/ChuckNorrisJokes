package me.vitorvigano.chucknorrisjokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private lateinit var repository: JokesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = JokesRepository(getDao(), getChuckNorrisApi())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe.setOnRefreshListener {
            getJokes()
        }
        getJokes() // First time.
    }

    private fun getChuckNorrisApi(): ChuckNorrisApi {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ChuckNorrisApi::class.java)
    }

    private fun getDao(): JokeDao {
        val database = Room.databaseBuilder(
            requireContext(),
            ChuckNorrisDatabase::class.java, "chuck-norris-db"
        ).build()

        return database.jokeDao()
    }

    private fun getJokes() {
        GlobalScope.launch {
            repository.getJokes().collect { result ->
                withContext(Dispatchers.Main) {
                    handleResult(result)
                }
            }
        }
    }

    private fun handleResult(result: Result<List<Joke>>) {
        when (result) {
            is Result.Success -> {
                swipe.isRefreshing = false
                showJokes(result.data)
            }
            is Result.Loading -> {
                swipe.isRefreshing = true
                result.data?.let {
                    showJokes(it)
                }
            }
            is Result.Error -> {
                Toast.makeText(
                    requireContext(),
                    "Chuck Norris doesn't like errors",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showJokes(jokes: List<Joke>) {
        val adapter = JokesAdapter(jokes)
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = adapter
    }
}
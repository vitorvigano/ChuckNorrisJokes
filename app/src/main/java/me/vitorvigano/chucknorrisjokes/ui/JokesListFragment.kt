package me.vitorvigano.chucknorrisjokes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_jokes_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.vitorvigano.chucknorrisjokes.R
import me.vitorvigano.chucknorrisjokes.domain.Joke
import me.vitorvigano.chucknorrisjokes.domain.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class JokesListFragment : Fragment() {

    private val vm: JokesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jokes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getJokes()
    }

    private fun getJokes() {
        vm.jokes.observe(viewLifecycleOwner, Observer { result ->
            handleResult(result)
        })
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
package me.vitorvigano.chucknorrisjokes.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*
import me.vitorvigano.chucknorrisjokes.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random


class MainFragment : Fragment() {

    private val vm: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActions()
        getLastJoke()
    }

    private fun setupActions() {
        load_more.setOnClickListener {
            load_more.isEnabled = false
            vm.onLoadMoreClick()
        }
        whatsapp_share.setOnClickListener {
            launchWhatApp()
        }
    }

    private fun launchWhatApp() {
        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "text/plain"
        whatsappIntent.setPackage("com.whatsapp")
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, joke_value.text.toString())
        try {
            startActivity(whatsappIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(), "There is no whatsapp installed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getLastJoke() {
        vm.lastJoke.observe(viewLifecycleOwner, Observer { joke ->
            joke?.let {
                load_more.isEnabled = true
                val labels = resources.getStringArray(R.array.load_more_labels_array)
                load_more.text = labels[Random.nextInt(0, labels.size)]
                joke_value.text = joke.value
            }
        })
    }
}
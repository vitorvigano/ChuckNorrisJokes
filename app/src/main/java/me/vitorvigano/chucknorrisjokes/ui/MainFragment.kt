package me.vitorvigano.chucknorrisjokes.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.vitorvigano.chucknorrisjokes.R
import me.vitorvigano.chucknorrisjokes.domain.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private val vm: MainViewModel by viewModel()
    private lateinit var filter: ColorMatrixColorFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        filter = ColorMatrixColorFilter(matrix)
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

        setupActions()
        subscribeObservers()
    }

    private fun setupActions() {
        im_chuck_norris.setOnClickListener {
            im_chuck_norris.isEnabled = false
            isChuckNorrisColored(false)
            vm.getNewJoke()
        }
        whatsapp_share.setOnClickListener {
            share("com.whatsapp")
        }
        insta_share.setOnClickListener {
            share("com.instagram.android")
        }
    }

    private fun share(appPackage: String) {
        val appIntent = Intent(Intent.ACTION_SEND)
        appIntent.type = "text/plain"
        appIntent.setPackage(appPackage)
        appIntent.putExtra(Intent.EXTRA_TEXT, joke_value.text.toString())
        try {
            startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(), "Chuck Norris cannot find this app",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun subscribeObservers() {
        vm.lastJoke.observe(viewLifecycleOwner, Observer { joke ->
            val value = joke?.value ?: getString(R.string.empty_message)
            im_chuck_norris.isEnabled = true
            isChuckNorrisColored(true)
            joke_value.text = value
        })
        vm.newJoke.observe(viewLifecycleOwner, Observer { result ->
            handleResult(result)
        })
    }

    private fun handleResult(result: Result<Long>) {
        when (result) {
            is Result.Error -> {
                im_chuck_norris.isEnabled = true
                isChuckNorrisColored(true)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isChuckNorrisColored(colored: Boolean) {
        if (colored)
            im_chuck_norris.colorFilter = null
        else
            im_chuck_norris.colorFilter = filter
    }
}
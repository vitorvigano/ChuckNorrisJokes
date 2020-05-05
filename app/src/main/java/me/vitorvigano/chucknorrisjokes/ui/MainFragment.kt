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
import me.vitorvigano.chucknorrisjokes.R
import org.koin.androidx.viewmodel.ext.android.viewModel


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
        getLastJoke()
    }

    private fun setupActions() {
        im_chuck_norris.setOnClickListener {
            im_chuck_norris.isEnabled = false
            isChuckNorrisColored(false)
            vm.onLoadMoreClick()
        }
        whatsapp_share.setOnClickListener {
            share("com.whatsapp")
        }
        insta_share.setOnClickListener {
            share("com.instagram.android")
        }
    }

    private fun share(appPackage:String) {
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

    private fun getLastJoke() {
        vm.lastJoke.observe(viewLifecycleOwner, Observer { joke ->
            joke?.let {
                im_chuck_norris.isEnabled = true
                isChuckNorrisColored(true)
                joke_value.text = joke.value
            }
        })
    }

    private fun isChuckNorrisColored(colored: Boolean) {
        if (colored)
            im_chuck_norris.colorFilter = null
        else
            im_chuck_norris.colorFilter = filter
    }
}
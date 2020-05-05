package me.vitorvigano.chucknorrisjokes

import android.app.Application
import me.vitorvigano.chucknorrisjokes.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChuckNorrisApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChuckNorrisApp)
            modules(allModules)
        }
    }
}
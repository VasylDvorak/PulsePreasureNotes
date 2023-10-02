package com.pulsepreassurenotes.application

import android.app.Application
import com.pulsepreassurenotes.di.ConnectKoinModules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    appModule
                )
            )
        }
    }
}


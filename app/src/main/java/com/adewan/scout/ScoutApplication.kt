package com.adewan.scout

import android.app.Application
import com.adewan.scout.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ScoutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ScoutApplication)
            modules(appModule)
        }
    }
}
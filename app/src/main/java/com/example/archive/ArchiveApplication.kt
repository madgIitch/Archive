package com.example.archive

import android.app.Application
import com.example.archive.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ArchiveApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ArchiveApplication)
            modules(appModule)
        }
    }
}
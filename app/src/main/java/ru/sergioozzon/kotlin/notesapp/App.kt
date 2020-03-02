package ru.sergioozzon.kotlin.notesapp

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.sergioozzon.kotlin.notesapp.di.appModule
import ru.sergioozzon.kotlin.notesapp.di.mainModule
import ru.sergioozzon.kotlin.notesapp.di.noteModule
import ru.sergioozzon.kotlin.notesapp.di.splashModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}
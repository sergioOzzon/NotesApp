package ru.sergioozzon.kotlin.notesapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.sergioozzon.kotlin.notesapp.data.NotesRepository
import ru.sergioozzon.kotlin.notesapp.data.provider.FireStoreProvider
import ru.sergioozzon.kotlin.notesapp.data.provider.RemoteDataProvider
import ru.sergioozzon.kotlin.notesapp.ui.main.MainViewModel
import ru.sergioozzon.kotlin.notesapp.ui.note.NoteViewModel
import ru.sergioozzon.kotlin.notesapp.ui.splash.SplashViewModel

val appModule = module{
    single {FirebaseAuth.getInstance()}
    single {FirebaseFirestore.getInstance()}
    single <RemoteDataProvider> { FireStoreProvider(get(), get())} //bind RemoteDataProvider::class
    single { NotesRepository(get())}
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}


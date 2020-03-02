package ru.sergioozzon.kotlin.notesapp.ui.splash

import ru.sergioozzon.kotlin.notesapp.data.NotesRepository
import ru.sergioozzon.kotlin.notesapp.data.errors.NoAuthException
import ru.sergioozzon.kotlin.notesapp.ui.base.BaseViewModel

class SplashViewModel(private val notesRepository: NotesRepository) : BaseViewModel<Boolean?, SplashViewState>() {
    fun requestUser(){
        notesRepository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it!=null){
                SplashViewState(authenticad = true)
            } else{
                SplashViewState(error = NoAuthException())
            }
        }
    }
}
package ru.sergioozzon.kotlin.notesapp.ui.splash

import ru.sergioozzon.kotlin.notesapp.data.NotesRepository
import ru.sergioozzon.kotlin.notesapp.ui.base.BaseViewModel
import java.io.NotActiveException

class SplashViewModel() : BaseViewModel<Boolean?, SplashViewState>() {
    fun requestUser(){
        NotesRepository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it!=null){
                SplashViewState(authenticad = true)
            } else{
                SplashViewState(error = NotActiveException())
            }
        }
    }
}
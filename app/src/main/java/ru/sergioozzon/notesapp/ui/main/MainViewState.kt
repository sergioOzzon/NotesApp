package ru.sergioozzon.notesapp.ui.main

import ru.sergioozzon.notesapp.data.entity.Note
import ru.sergioozzon.notesapp.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)
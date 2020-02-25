package ru.sergioozzon.notesapp.ui.note

import ru.sergioozzon.notesapp.data.entity.Note
import ru.sergioozzon.notesapp.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error)
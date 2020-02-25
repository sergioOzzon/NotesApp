package ru.sergioozzon.notesapp.data.provider

import androidx.lifecycle.LiveData
import ru.sergioozzon.notesapp.data.entity.Note
import ru.sergioozzon.notesapp.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String) : LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}
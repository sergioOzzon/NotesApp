package ru.sergioozzon.kotlin.notesapp.data.provider

import androidx.lifecycle.LiveData
import ru.sergioozzon.kotlin.notesapp.data.entity.Note
import ru.sergioozzon.kotlin.notesapp.data.entity.User
import ru.sergioozzon.kotlin.notesapp.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
    fun deleteNote(noteId: String): LiveData<NoteResult>
}
package ru.sergioozzon.kotlin.notesapp.data

import ru.sergioozzon.kotlin.notesapp.data.entity.Note
import ru.sergioozzon.kotlin.notesapp.data.provider.RemoteDataProvider

class NotesRepository(private val remoteProvider: RemoteDataProvider) {
    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteNote(id: String) = remoteProvider.deleteNote(id)
}
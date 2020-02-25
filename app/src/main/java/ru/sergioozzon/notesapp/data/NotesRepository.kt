package ru.sergioozzon.notesapp.data

import ru.sergioozzon.notesapp.data.entity.Note
import ru.sergioozzon.notesapp.data.provider.FireStoreProvider
import ru.sergioozzon.notesapp.data.provider.RemoteDataProvider

object NotesRepository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

}
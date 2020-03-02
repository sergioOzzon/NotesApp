package ru.sergioozzon.kotlin.notesapp.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import ru.sergioozzon.kotlin.notesapp.data.entity.Note
import ru.sergioozzon.kotlin.notesapp.data.entity.User
import ru.sergioozzon.kotlin.notesapp.data.errors.NoAuthException
import ru.sergioozzon.kotlin.notesapp.data.model.NoteResult

class FireStoreProvider(private val firebaseAuth: FirebaseAuth, private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"
    }

    private val currentUser
        get() = firebaseAuth.currentUser

    private val userNotesCollection: CollectionReference
        get() = currentUser?.let {
            store.collection(USER_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
            } ?: throw NoAuthException()

    override fun getCurrentUser() = MutableLiveData<User?>().apply {
        value = currentUser?.let { firebaseUser ->
            User(firebaseUser.displayName ?: "", firebaseUser.email ?: "")
        }
    }

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        try{
            userNotesCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                firebaseFirestoreException?.let {
                    throw it
                } ?: let {
                    querySnapshot?.let { snapshot ->
                        /*val notes = mutableListOf<Note>()
                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        value = NoteResult.Success(notes)*/
                        value = NoteResult.Success(snapshot.map { it.toObject(Note::class.java) })
                    }

                }
            }
        } catch (e: Throwable){
            value = NoteResult.Error(e)
        }
    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {

        try{
            userNotesCollection.document(id).get()
                .addOnSuccessListener { snapshot ->
                    value = NoteResult.Success(snapshot.toObject(Note::class.java))
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (e: Throwable){
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {

        try{
            userNotesCollection.document(note.id).set(note)
                .addOnSuccessListener {
                    value = NoteResult.Success(note)
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (e: Throwable){
            value = NoteResult.Error(e)
        }
    }

    override fun deleteNote(noteId: String) : LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try{
            userNotesCollection.document(noteId).delete()
                .addOnSuccessListener {
                    value = NoteResult.Success(null)
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (e: Throwable){
            value = NoteResult.Error(e)
        }
    }
}
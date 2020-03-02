package ru.sergioozzon.kotlin.notesapp.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import ru.sergioozzon.kotlin.notesapp.R
import ru.sergioozzon.kotlin.notesapp.common.getColorInt
import ru.sergioozzon.kotlin.notesapp.data.entity.Note
import ru.sergioozzon.kotlin.notesapp.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<NoteViewState.Data, NoteViewState>() {
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            context.startActivity(intent)
        }
    }

    override val model: NoteViewModel by viewModel()
    override val layoutRes = R.layout.activity_note
    private var note: Note? = null
    var color = Note.Color.WHITE

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let {
            model.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
            initView()
        }

    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) finish()
        this.note = data.note
        initView()
    }

    private fun initView() {
        note?.let { note ->
            removeEditListener()
            if (et_title.text.toString() != note.title){
                et_title.setText(note.title)
            }
            if (et_body.text.toString() != note.body){
                et_body.setText(note.body)
            }
            toolbar.setBackgroundColor(note.color.getColorInt(this))
            supportActionBar?.title = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note.lastChanged)
        } ?: let{
            supportActionBar?.title = getString(R.string.new_note_title)
        }

        setEditListener()

        colorPicker.onColorClickListener = {
            toolbar.setBackgroundColor(color.getColorInt(this))
            color = it
            saveNote()
        }
    }

    private fun removeEditListener(){
        et_title.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)
    }

    private fun setEditListener(){
        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            body = et_body.text.toString(),
            lastChanged = Date(),
            color = color
        ) ?: Note(
            UUID.randomUUID().toString(),
            et_title.text.toString(),
            et_body.text.toString(),
            color
        )

        note?.let {
            model.save(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.note, menu).let {
        true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.colorPallete -> togglePallete().let {true}
        R.id.deleteNote -> deleteNote().let {true}
        else -> super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.note_delete_message
            negativeButton(R.string.no) {dialog -> dialog.dismiss()}
            positiveButton(R.string.yes) { model.deleteNote() }
        }.show()
    }

    private fun togglePallete(){
        if(colorPicker.isOpen){
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }
}
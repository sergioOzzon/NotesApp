package ru.sergioozzon.kotlin.notesapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import ru.sergioozzon.kotlin.notesapp.R
import ru.sergioozzon.kotlin.notesapp.data.entity.Note
import ru.sergioozzon.kotlin.notesapp.ui.base.BaseActivity
import ru.sergioozzon.kotlin.notesapp.ui.note.NoteActivity
import ru.sergioozzon.kotlin.notesapp.ui.splash.SplashActivity

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object{
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val model: MainViewModel by viewModel()
    override val layoutRes = R.layout.activity_main
    private lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = LinearLayoutManager(this)
        adapter = NotesRVAdapter { note ->
            NoteActivity.start(this, note.id)
        }
        rv_notes.adapter = adapter

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.logout -> showLogoutDialog().let { true}
        else -> false
    }

    private fun showLogoutDialog() {
        alert {
            titleResource = R.string.log_out_dialog
            messageResource = R.string.logout_gialog_message
            positiveButton(R.string.yes) { onLogout()}
            negativeButton(R.string.no) { dialog -> dialog.dismiss()}
        }.show()
    }

    private fun onLogout(){
        AuthUI.getInstance().signOut(this).addOnCompleteListener{
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }


    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }


}

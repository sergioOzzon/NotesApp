package ru.sergioozzon.kotlin.notesapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import ru.sergioozzon.kotlin.notesapp.R
import ru.sergioozzon.kotlin.notesapp.data.entity.Note
import ru.sergioozzon.kotlin.notesapp.ui.base.BaseActivity
import ru.sergioozzon.kotlin.notesapp.ui.note.NoteActivity
import ru.sergioozzon.kotlin.notesapp.ui.splash.SplashActivity

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), LogoutDialog.LogoutListener {

    companion object{
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override val layoutRes = R.layout.activity_main
    lateinit var adapter: NotesRVAdapter

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
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG)?:
                LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onLogout(){
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

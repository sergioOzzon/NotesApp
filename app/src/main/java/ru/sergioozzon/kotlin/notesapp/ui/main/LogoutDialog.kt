package ru.sergioozzon.kotlin.notesapp.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.sergioozzon.kotlin.notesapp.R

class LogoutDialog: DialogFragment() {
    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance() = LogoutDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(context)
            .setTitle(R.string.log_out)
            .setMessage(getString(R.string.a_u_sure))
            .setPositiveButton(getString(R.string.yes)) { dialog, which -> (activity as LogoutListener).onLogout()}
            .setNegativeButton(getString(R.string.no)) { dialog, which -> dismiss()}
            .create()

    interface LogoutListener {
        fun onLogout()
    }
}
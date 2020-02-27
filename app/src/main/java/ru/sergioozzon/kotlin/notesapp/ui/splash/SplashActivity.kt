package ru.sergioozzon.kotlin.notesapp.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import ru.sergioozzon.kotlin.notesapp.ui.base.BaseActivity
import ru.sergioozzon.kotlin.notesapp.ui.main.MainActivity

class SplashActivity: BaseActivity<Boolean?, SplashViewState>() {
    override val viewModel by lazy{
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({viewModel.requestUser()}, 1000)

    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }
}
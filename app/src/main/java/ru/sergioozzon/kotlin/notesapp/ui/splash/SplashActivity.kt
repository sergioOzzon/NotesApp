package ru.sergioozzon.kotlin.notesapp.ui.splash

import android.os.Handler
import org.koin.android.viewmodel.ext.android.viewModel
import ru.sergioozzon.kotlin.notesapp.ui.base.BaseActivity
import ru.sergioozzon.kotlin.notesapp.ui.main.MainActivity

class SplashActivity: BaseActivity<Boolean?, SplashViewState>() {
    override val model: SplashViewModel by viewModel()

    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({model.requestUser()}, 1000)

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
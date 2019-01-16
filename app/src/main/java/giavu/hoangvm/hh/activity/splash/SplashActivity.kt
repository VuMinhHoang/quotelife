package giavu.hoangvm.hh.activity.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.hh.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //actionBar?.hide()
        animation_view.playAnimation()
    }
}

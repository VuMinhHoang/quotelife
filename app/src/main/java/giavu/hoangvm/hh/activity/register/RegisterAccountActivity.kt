package giavu.hoangvm.hh.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.hh.R
import org.koin.android.ext.android.inject

class RegisterAccountActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent{
            return Intent(context,RegisterAccountActivity::class.java)
        }
    }

    private val viewModel: RegisterAccountViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)
        initializeViewModel()

    }

    private fun initializeViewModel(){
        viewModel.initialize(
                navigator = navigator
        )
    }

    private val navigator = object: RegisterAccountNavigator {
        override fun showProgress() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun hideProgress() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun register() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}

package giavu.hoangvm.hh.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.databinding.ActivityRegisterAccountBinding
import org.koin.android.ext.android.inject

class RegisterAccountActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent{
            return Intent(context,RegisterAccountActivity::class.java)
        }
    }

    private val viewModel: RegisterAccountViewModel by inject()
    private lateinit var dataBinding: ActivityRegisterAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register_account)
        initializeDataBinding()
        initializeViewModel()

    }

    private fun initializeDataBinding() {
        dataBinding = DataBindingUtil.setContentView<ActivityRegisterAccountBinding>(
                this, R.layout.activity_register_account)
                .apply {
                    viewModel = this@RegisterAccountActivity.viewModel
                    setLifecycleOwner(this@RegisterAccountActivity)
                }

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

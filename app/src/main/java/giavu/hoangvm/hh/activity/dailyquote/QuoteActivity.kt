package giavu.hoangvm.hh.activity.dailyquote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.dialog.hideProgress
import giavu.hoangvm.hh.dialog.showProgress
import timber.log.Timber

class QuoteActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, QuoteActivity::class.java)
        }
    }
    private lateinit var mDrawerLayout: DrawerLayout
    val viewModel: QuoteViewModel by lazy {
        ViewModelProviders.of(this@QuoteActivity).get(QuoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        initViewModel()
        initializeActionBar()
    }


    private fun initializeActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("Quote")
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                Timber.d("Menu is click")
                mDrawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initViewModel() {
        viewModel.initialize(navigator = navigator)
    }

    private val navigator = object : QuoteNavigator {
        override fun showProgress() {
            this@QuoteActivity.showProgress()
        }

        override fun hideProgress() {
            this@QuoteActivity.hideProgress()
        }

        override fun toLogout(message: String) {
            Timber.d(message)
        }

        override fun toError(e: Throwable) {
            Timber.d(e)
        }
    }
}

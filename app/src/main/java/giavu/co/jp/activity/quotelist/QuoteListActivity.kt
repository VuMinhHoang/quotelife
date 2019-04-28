package giavu.co.jp.activity.quotelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import giavu.co.jp.R
import giavu.co.jp.utils.State
import kotlinx.android.synthetic.main.activity_quote_list.*

class QuoteListActivity : AppCompatActivity() {

    private lateinit var viewModel: QuoteListViewModel
    private lateinit var quoteListAdapter: QuoteListAdapter

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, QuoteListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_list)
        viewModel = ViewModelProviders.of(this)
            .get(QuoteListViewModel::class.java)
        initializeActionBar()
        initAdapter()
        initState()
    }

    private fun initializeActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            actionBar.title = "QUOTE LIST"
            setHomeAsUpIndicator(R.drawable.ic_stat_keyboard_backspace)
        }
    }

    private fun initAdapter() {
        quoteListAdapter = QuoteListAdapter { viewModel.retry() }
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.adapter = quoteListAdapter
        viewModel.quoteList.observe(
            this, Observer {
                quoteListAdapter.submitList(it)
            }
        )
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                quoteListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
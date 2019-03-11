package giavu.hoangvm.hh.activity.quotelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import giavu.hoangvm.hh.R
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
        initAdapter()
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
}

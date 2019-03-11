package giavu.hoangvm.hh.activity.quotelist

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import giavu.hoangvm.hh.model.Quote

/**
 * @Author: Hoang Vu
 * @Date:   2019/03/11
 */
class QuoteListAdapter(private val retry: () -> Unit): PagedListAdapter<Quote, RecyclerView.ViewHolder>(quoteDiffCallBack)  {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    companion object {
        val quoteDiffCallBack = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.body == newItem.body
            }

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE){
            QuoteViewHolder.create(parent)
        } else {
            QuoteViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as QuoteViewHolder).bind(getItem(position))
        else (holder as QuoteViewHolder).bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if(position< super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}
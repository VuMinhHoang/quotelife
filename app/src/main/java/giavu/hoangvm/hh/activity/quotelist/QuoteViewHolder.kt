package giavu.hoangvm.hh.activity.quotelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.model.Quote
import kotlinx.android.synthetic.main.item_quote.view.*

/**
 * @Author: Hoang Vu
 * @Date:   2019/03/11
 */
class QuoteViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(quote: Quote?) {
        if(quote != null) {
            itemView.quote_body.text = quote.body
            itemView.author.text = quote.author
            itemView.up_vote.text = quote.upvotes_count.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): QuoteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote, parent, false)
            return QuoteViewHolder(view)
        }
    }
}
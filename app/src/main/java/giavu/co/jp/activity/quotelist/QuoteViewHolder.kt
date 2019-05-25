package giavu.co.jp.activity.quotelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import giavu.co.jp.R
import giavu.co.jp.model.Quote
import kotlinx.android.synthetic.main.item_quote.view.*

/**
 * @Author: Hoang Vu
 * @Date:   2019/03/11
 */
class QuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(quote: Quote?) {
        if (quote != null) {
            itemView.quote_body.text = quote.body
            itemView.author.text = quote.author
            itemView.favorite_vote.text = quote.favorites_count.toString()
            itemView.up_vote.text = quote.upvotes_count.toString()
            itemView.down_vote.text = quote.downvotes_count.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup, navigator: ItemListNavigator): QuoteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_quote, parent, false)
            view.setOnClickListener {
                navigator.onItemClick()
            }
            return QuoteViewHolder(view)
        }
    }
}
package com.example.newsapp.ui.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.BookmarksListItemBinding
import com.example.newsapp.models.Article
import com.example.newsapp.utils.Utils
import timber.log.Timber

class BookmarksAdapter(private val listener: BookmarksItemListener) :
    ListAdapter<Article, BookmarksAdapter.ViewHolder>(Article.DiffCallback())  {

    interface BookmarksItemListener {
        fun onNewsItemClicked(url: String)
        fun onUnsetBookmarkClicked(article: Article)
    }

    inner class ViewHolder(val binding: BookmarksListItemBinding, private val listener: BookmarksItemListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) = with(binding) {
            sourceTextView.text = article.source.name
            descriptionTextView.text = article.description
            dateTextView.text = article.publishedAt
            Glide.with(root)
                .load(article.urlToImage)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(articleLittleImageView)

            root.setOnClickListener {
                Timber.d(it.context.getString(R.string.list_item_clicked_message))
                listener.onNewsItemClicked(article.url)
            }
            unsetBookmarkImageView.setOnClickListener {
                Timber.d(it.context.getString(R.string.unset_bookmark_clicked_message))
                listener.onUnsetBookmarkClicked(article)
                notifyItemRemoved(adapterPosition)
            }
            moreActionsImageView.setOnClickListener {
                Timber.d(it.context.getString(R.string.more_button_clicked_message))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = BookmarksListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        listener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }
}
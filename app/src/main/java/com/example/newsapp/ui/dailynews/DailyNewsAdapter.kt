package com.example.newsapp.ui.dailynews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsListItemBinding
import com.example.newsapp.models.Article
import timber.log.Timber


class DailyNewsAdapter(private val listener: NewsItemListener) :
    ListAdapter<Article, DailyNewsAdapter.ViewHolder>(Article.DiffCallback()) {

    interface NewsItemListener {
        fun onNewsItemClicked(url: String)
        fun onSetBookmarkClicked(article: Article)
    }

    inner class ViewHolder(val binding: NewsListItemBinding, private val listener: NewsItemListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) = with(binding) {
            articleSourceTextView.text = article.source.name
            articleTitleTextView.text = article.title
            Glide.with(root)
                .load(article.urlToImage)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(articleImageView)

            root.setOnClickListener {
                Timber.d(it.context.getString(R.string.list_item_clicked_message))
                listener.onNewsItemClicked(article.url)
            }
            setBookmarkImageView.setOnClickListener {
                Timber.d(it.context.getString(R.string.set_bookmark_clicked_message))
                listener.onSetBookmarkClicked(article)

                fun paintOverBookmarkIcon() {
                    binding.setBookmarkImageView.setImageResource(R.drawable.ic_bookmark_default)
                }
            }
            moreImageView.setOnClickListener {
                Timber.d(it.context.getString(R.string.more_button_clicked_message))
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        binding = NewsListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false),
        listener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }
}

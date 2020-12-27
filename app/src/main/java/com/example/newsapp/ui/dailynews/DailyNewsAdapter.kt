package com.example.newsapp.ui.dailynews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsListItemBinding
import com.example.newsapp.models.Article
import timber.log.Timber


class DailyNewsAdapter(private val listener: NewsItemListener) :
    RecyclerView.Adapter<DailyNewsAdapter.ViewHolder>() {

    interface NewsItemListener {
        fun onNewsItemClicked(url: String)
        fun onSetBookmarkClicked(article: Article)
    }

    private val dataSet: ArrayList<Article> = arrayListOf()

    fun setItems(items: ArrayList<Article>) {
        this.dataSet.clear()
        this.dataSet.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: NewsListItemBinding, private val listener: NewsItemListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Define click listener for the ViewHolder's View.
            binding.root.setOnClickListener {
                Timber.d("RecyclerView item clicked!")
                listener.onNewsItemClicked(dataSet[adapterPosition].url)
            }

            binding.setBookmarkImageView.setOnClickListener {
                Timber.d("Bookmark clicked!")
                listener.onSetBookmarkClicked(dataSet[adapterPosition])

                fun paintOverBookmarkIcon() {
                    binding.setBookmarkImageView.setImageResource(R.drawable.ic_bookmark_default)
                }
            }

            binding.moreImageView.setOnClickListener {
                Timber.d("More button clicked")
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        binding = NewsListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false),
        listener
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataSet at this position and replace the
        // contents of the view with that element
        with(viewHolder) {
            binding.articleSourceTextView.text = dataSet[position].source.name
            binding.articleTitleTextView.text = dataSet[position].title
            Glide.with(binding.root)
                .load(dataSet[position].urlToImage)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.articleImageView)
        }
    }

    override fun getItemCount() = dataSet.size
}

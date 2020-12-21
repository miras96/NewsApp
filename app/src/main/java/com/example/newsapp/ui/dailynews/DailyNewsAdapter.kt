package com.example.newsapp.ui.dailynews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.NewsListItemBinding
import com.example.newsapp.models.Article
import timber.log.Timber


class DailyNewsAdapter(private val listener: NewsItemListener) :
    RecyclerView.Adapter<DailyNewsAdapter.ViewHolder>() {

    interface NewsItemListener {
        fun onNewsItemClicked(url: String)
    }

    private val dataSet: ArrayList<Article> = arrayListOf()

    fun setItems(items: ArrayList<Article>) {
        this.dataSet.clear()
        this.dataSet.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: NewsListItemBinding, private val listener: NewsItemListener) :
        RecyclerView.ViewHolder(binding.root) {
        val articleImageView: ImageView = binding.articleImageView
        val articleSourceTextView: TextView = binding.articleSourceTextView
        val articleTitleTextView: TextView = binding.articleTitleTextView
        var articleUrl: String = ""

        init {
            // Define click listener for the ViewHolder's View.
            binding.root.setOnClickListener {
                Timber.d("RecyclerView item clicked! $articleUrl")
                listener.onNewsItemClicked(articleUrl)
            }

            binding.setBookmarkImageView.setOnClickListener {
                Timber.d("Bookmark clicked!")
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
            articleSourceTextView.text = dataSet[position].source.name
            articleTitleTextView.text = dataSet[position].title
            articleUrl = dataSet[position].url
            Glide.with(binding.root)
                .load(dataSet[position].urlToImage)
                .fitCenter()
                .into(articleImageView)
        }
    }

    override fun getItemCount() = dataSet.size
}

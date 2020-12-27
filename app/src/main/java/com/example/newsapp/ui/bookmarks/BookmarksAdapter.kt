package com.example.newsapp.ui.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.databinding.BookmarksListItemBinding
import com.example.newsapp.models.Article
import com.example.newsapp.utils.Utils
import timber.log.Timber
import kotlin.collections.ArrayList

class BookmarksAdapter(private val listener: BookmarksItemListener) :
    RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    interface BookmarksItemListener {
        fun onNewsItemClicked(url: String)
        fun onUnsetBookmarkClicked(article: Article)
    }

    private val dataSet: ArrayList<Article> = arrayListOf()

    fun setItems(items: ArrayList<Article>) {
        this.dataSet.clear()
        this.dataSet.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: BookmarksListItemBinding, private val listener: BookmarksItemListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Define click listener for the ViewHolder's View.
            binding.root.setOnClickListener {
                Timber.d("RecyclerView item clicked!")
                listener.onNewsItemClicked(dataSet[adapterPosition].url)
            }

            binding.unsetBookmarkImageView.setOnClickListener {
                Timber.d("Unset bookmark clicked!")
                listener.onUnsetBookmarkClicked(dataSet[adapterPosition])
            }

            binding.moreActionsImageView.setOnClickListener {
                Timber.d("More button clicked")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = BookmarksListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        listener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataSet at this position and replace the
        // contents of the view with that element
        with(holder) {
            binding.sourceTextView.text = dataSet[position].source.name
            binding.descriptionTextView.text = dataSet[position].description
            binding.dateTextView.text = Utils.parseDate(dataSet[position].publishedAt)
            Glide.with(binding.root)
                .load(dataSet[position].urlToImage)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.articleLittleImageView)
        }
    }

    override fun getItemCount() = dataSet.size
}
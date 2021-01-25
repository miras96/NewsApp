package com.example.newsapp.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentBookmarksBinding
import com.example.newsapp.models.Article
import com.example.newsapp.ui.article.WebViewFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*


@AndroidEntryPoint
class BookmarksFragment : Fragment(), BookmarksAdapter.BookmarksItemListener {
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BookmarksViewModel>()
    private lateinit var adapter: BookmarksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        adapter = BookmarksAdapter(this)
        with(binding.recyclerViewBookmarks) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext()).apply {
                addItemDecoration(DividerItemDecoration(context, orientation))
            }
            adapter = this@BookmarksFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.getSavedBookmarks().observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.setItems(it.toMutableList().toCollection(arrayListOf()))
            }
        })
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it)
                Snackbar.make(binding.root, getString(R.string.removed_from_bookmarks), Snackbar.LENGTH_SHORT)
                    .show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNewsItemClicked(url: String) {
        findNavController().navigate(
            R.id.action_bookmarksFragment_to_webViewFragment,
            bundleOf(WebViewFragment.URL_KEY to url)
        )
    }

    override fun onUnsetBookmarkClicked(article: Article) {
        viewModel.removeFromBookmarks(article)
    }
}
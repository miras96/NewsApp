package com.example.newsapp.ui.dailynews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDailyNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class DailyNewsFragment : Fragment(), DailyNewsAdapter.NewsItemListener {
    private var _binding: FragmentDailyNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DailyNewsViewModel>()
    private lateinit var adapter: DailyNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDailyNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        adapter = DailyNewsAdapter(this)
        with(binding.recyclerViewNews) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DailyNewsFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.getLatestNews().observe(viewLifecycleOwner, Observer {
            it?.let { response ->
                when(response.status) {
                    "ok" -> {
                        if (!response.articles.isNullOrEmpty()) adapter.setItems(response.articles)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNewsItemClicked(url: String) {
        findNavController().navigate(
            R.id.action_dailyNewsFragment_to_webViewFragment,
            bundleOf("url" to url)
        )
    }
}
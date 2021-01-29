package com.example.newsapp.ui.bottomsheet

import android.app.Activity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import com.example.newsapp.databinding.FragmentBottomSheetBinding
import com.example.newsapp.utils.Utils
import timber.log.Timber


class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupUI()
    }

    private fun setupUI() {
        var url: String? = null
        arguments?.let {
            url = it.getString(Utils.URL_KEY)
        }
        binding.constraintLayoutShare.setOnClickListener {
            Timber.d("on share clicked")
            val shareIntent = ShareCompat.IntentBuilder
                .from(activity as Activity)
                .setType("text/plain")
                .setText(url)
                .intent
            if (shareIntent.resolveActivity(binding.root.context.packageManager) != null)
                startActivity(shareIntent)
        }
        binding.constraintLayoutGoToSource.setOnClickListener {
            Timber.d("on go to source clicked")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
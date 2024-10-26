package com.example.androidassignment.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassignment.Injection
import com.example.androidassignment.MainActivity
import com.example.androidassignment.databinding.FragmentHomeBinding
import com.example.androidassignment.presentation.viewmodel.ArticleViewModel
import com.example.androidassignment.presentation.adapter.ArticleAdapter
import com.example.androidassignment.presentation.adapter.BeerPagingAdapter
import com.example.androidassignment.presentation.viewmodel.BeerViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val homeViewModel by activityViewModels<HomeViewModel>()

        // Get the view model
        val articleViewModel by activityViewModels<ArticleViewModel>(
            factoryProducer = { Injection.provideViewModelFactory(owner = this) }
        )

        val beerViewModel by activityViewModels<BeerViewModel>(
            factoryProducer = { Injection.provideBeerViewModelFactory(owner = this) }
        )


        val items = articleViewModel.items
        val articleAdapter = ArticleAdapter()
        val beerPagingAdapter = BeerPagingAdapter()

        binding.bindAdapter(articleAdapter = articleAdapter)

        beerViewModel.getBeers().observe(requireActivity(), Observer {
            beerPagingAdapter.submitData(lifecycle, it)
        })

        binding.tvActivityStatus.text = homeViewModel.statusText

        // Collect from the PagingData Flow in the ViewModel, and submit it to the
        // PagingDataAdapter.
        lifecycleScope.launch {
            // We repeat on the STARTED lifecycle because an Activity may be PAUSED
            // but still visible on the screen, for example in a multi window app
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    articleAdapter.submitData(it)
                }
            }
        }

        // Use the CombinedLoadStates provided by the loadStateFlow on the ArticleAdapter to
        // show progress bars when more data is being fetched
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                articleAdapter.loadStateFlow.collect {
                    binding.prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }

        binding.tvActivityStatus.setOnClickListener {
            homeViewModel.statusText = "Resume" // Update ViewModel's state
            binding.tvActivityStatus.text = homeViewModel.statusText
            MainActivity.INSTANCE?.openFavourites()
        }

        return binding.root
    }

}

/**
 * Sets up the [RecyclerView] and binds [ArticleAdapter] to it
 */
private fun FragmentHomeBinding.bindAdapter(articleAdapter: ArticleAdapter) {
    rvArticles.adapter = articleAdapter
    rvArticles.layoutManager = LinearLayoutManager(rvArticles.context)
    val decoration = DividerItemDecoration(rvArticles.context, DividerItemDecoration.VERTICAL)
    rvArticles.addItemDecoration(decoration)
}

private fun FragmentHomeBinding.bindAdapter(beerPagingAdapter: BeerPagingAdapter) {
    rvArticles.adapter = beerPagingAdapter
    rvArticles.layoutManager = LinearLayoutManager(rvArticles.context)
    val decoration = DividerItemDecoration(rvArticles.context, DividerItemDecoration.VERTICAL)
    rvArticles.addItemDecoration(decoration)
}

class HomeViewModel : ViewModel() {
    var statusText: String = "Start" // Set initial default text
}

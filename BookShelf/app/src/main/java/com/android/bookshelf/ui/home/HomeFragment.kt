package com.android.bookshelf.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bookshelf.R
import com.android.bookshelf.business.domain.model.bookModel.BookInfo
import com.android.bookshelf.business.domain.utils.collectLatestFlow
import com.android.bookshelf.business.domain.utils.exceptionView.ExceptionItems
import com.android.bookshelf.business.domain.utils.exceptionView.createExceptionView
import com.android.bookshelf.business.domain.utils.exhaustive
import com.android.bookshelf.business.domain.utils.hideKeyboard
import com.android.bookshelf.business.domain.utils.toast
import com.android.bookshelf.databinding.FragmentHomeBinding
import com.android.bookshelf.ui.home.adapter.BookPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) , BookPagingAdapter.OnItemClickListener{

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var topupPagingAdapter: BookPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        (activity as AppCompatActivity).supportActionBar?.title =
            "${resources.getString(R.string.menu_launchpad)}"
        topupPagingAdapter = BookPagingAdapter(this,requireActivity())
        binding.apply {
            displayProgressBar(false)

            emptyView.visibility = View.GONE
            createExceptionView(
                ExceptionItems.EMPTY.label,
                layoutInflater,
                emptyView,
                ""
            )

            inputSearchQuery.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_search,
                0,
                0,
                0
            )

            inputSearchQuery.addTextChangedListener(watcher)

            inputSearchQuery.setOnTouchListener(object : View.OnTouchListener {
                val DRAWABLE_RIGHT = 2
                override fun onTouch(v: View?, event: MotionEvent): Boolean {
                    if (event.action === MotionEvent.ACTION_UP) {
                        if (inputSearchQuery.compoundDrawables[DRAWABLE_RIGHT] != null &&
                            event.rawX >= inputSearchQuery.right - inputSearchQuery.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                        ) {
                            inputSearchQuery.setText("")
                            hideKeyboard()
                            inputSearchQuery.clearFocus()
                            return true
                        }
                    }
                    return false
                }
            })

            recyclerViewListing.apply {

                layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

        }
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getMyList("search")
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun subscribeObservers() {
        displayProgressBar(true)
        homeViewModel.topupDataStateApp.observe(viewLifecycleOwner){
            setTopUpAdapter(it)
        }

        collectLatestFlow(topupPagingAdapter.loadStateFlow){ loadState ->

            when (val currentState = loadState.refresh) {
                is LoadState.Loading -> {
                    Log.e("TOPUP::", "progress : true")
                    displayProgressBar(true)
                }
                is LoadState.Error -> {
                    displayProgressBar(false)
                    activity?.toast(currentState.error.message)
                }
                is LoadState.NotLoading -> displayProgressBar(false)
            }.exhaustive

            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && topupPagingAdapter.itemCount < 1) {
                Log.e("TOPUP::", "empty view")
            } else {
                Log.e("TOPUP::", "not empty ")
            }
        }
    }

    private fun setTopUpAdapter(it: PagingData<BookInfo>) {
        displayProgressBar(false)
        topupPagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        binding.recyclerViewListing.apply {
            adapter = topupPagingAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
         Log.e("TOPUP::", "UI : ${topupPagingAdapter.snapshot().items}")
    }

    private var watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            homeViewModel.getMyList(s.toString())
            if (s != null && s.isNotEmpty()) {
                binding.inputSearchQuery.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_close,
                    0
                )
            } else {
                binding.inputSearchQuery.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_search,
                    0,
                    0,
                    0
                )
            }
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.INVISIBLE
    }

    override fun onItemClick(data: BookInfo) {
        val intent = Intent(context, DescriptionActivity::class.java)
        intent.putExtra("book_details", data)
        context?.startActivity(intent)

    }
}

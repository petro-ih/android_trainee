package com.petro.scope104.presentation.list

import android.app.ActivityOptions
import android.content.res.Configuration
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.petro.scope104.R
import com.petro.scope104.databinding.FragmentWorkersBinding
import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.util.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerListFragment : Fragment() {
    private val isLastPage = false
    private lateinit var adapter: WorkerListAdapter
    private lateinit var binding: FragmentWorkersBinding
    private lateinit var viewModel: WorkerListViewModel
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(
            KEY_DATA, ArrayList(
                adapter.currentList
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WorkerListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listType = requireArguments().getSerializable(KEY_TYPE) as ListType
        val rv = binding.listOfWorkers
        val pullToRefresh = binding.pullToRefresh
        pullToRefresh.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple_500
            )
        )
        pullToRefresh.setOnRefreshListener {
            loadMore(true)
            pullToRefresh.isRefreshing = false
        }
        var spanCount = 3
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 6
        }
        when (listType) {
            ListType.LINEAR -> {
                adapter = WorkerListAdapter(WorkerListAdapter.VIEW_TYPE_NORMAL)
                rv.layoutManager = LinearLayoutManager(context)
            }
            ListType.GRID -> {
                adapter = WorkerListAdapter(WorkerListAdapter.VIEW_TYPE_SQUARE)
                rv.layoutManager = GridLayoutManager(context, spanCount)
            }
            ListType.STAGGERED -> {
                adapter = WorkerListAdapter(WorkerListAdapter.VIEW_TYPE_SQUARE)
                rv.layoutManager =
                    StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        rv.adapter = adapter
        adapter.setOnClickListener(object : WorkerListAdapter.OnClickListener{
            override fun onClick(clickedItem: WorkerEntity, transitionView: List<View>) {
                val p1 = Pair.create(
                    transitionView[0], transitionView[0].transitionName
                )
                val p2 = Pair.create(transitionView[1], transitionView[1].transitionName)
                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, p1, p2)
                (requireActivity() as WorkerListInteractions).onItemClick(clickedItem, activityOptions)            }
        } )

        if (savedInstanceState == null) {
            loadMore(false)
        }
        if (rv.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = rv.layoutManager as LinearLayoutManager?
            rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override fun loadMoreItems() {
                    loadMore(false)
                }

                override val isLastPage: Boolean get() = this@WorkerListFragment.isLastPage
                override val isLoading: Boolean
                    get() = java.lang.Boolean.TRUE == viewModel.isLoadingLiveData.value
            })
        }
        viewModel.isLoadingLiveData
            .observe(viewLifecycleOwner) { isLoading: Boolean -> setLoading(isLoading) }
        viewModel.users
            .observe(viewLifecycleOwner) { list: List<WorkerEntity?> -> adapter.submitList(list) }
    }

    fun refresh() {
        loadMore(true)
    }

    private fun loadMore(refresh: Boolean) {
        if (activity is ListFilter) {
            val listFilter = activity as ListFilter?
            viewModel.loadMore(refresh, listFilter!!.currentSelectedGender, listFilter.countries)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    interface ListFilter {
        val currentSelectedGender: Gender
        val countries: Set<String>
    }

    internal interface WorkerListInteractions {
        fun onItemClick(workerEntity: WorkerEntity?, activityOptions: ActivityOptions)
    }

    companion object {
        private const val KEY_TYPE = "KEY_TYPE"
        private const val KEY_DATA = "KEY_DATA"
        fun newInstance(type: ListType?): Fragment {
            val args = Bundle()
            args.putSerializable(KEY_TYPE, type)
            val fragment = WorkerListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
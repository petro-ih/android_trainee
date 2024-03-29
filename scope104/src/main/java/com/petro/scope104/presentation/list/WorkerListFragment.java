package com.petro.scope104.presentation.list;

import android.app.ActivityOptions;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.petro.scope104.R;
import com.petro.scope104.databinding.FragmentWorkersBinding;
import com.petro.scope104.domain.entity.WorkerEntity;
import com.petro.scope104.util.PaginationScrollListener;

import java.util.ArrayList;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkerListFragment extends Fragment {
    private static final String KEY_TYPE = "KEY_TYPE";
    private static final String KEY_DATA = "KEY_DATA";
    private final boolean isLastPage = false;
    private WorkerListAdapter adapter;
    private FragmentWorkersBinding binding;
    private WorkerListViewModel viewModel;

    public static Fragment newInstance(ListType type) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_TYPE, type);
        WorkerListFragment fragment = new WorkerListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_DATA, new ArrayList<>(adapter.getCurrentList()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkerListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWorkersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        ListType listType = ((ListType) getArguments().getSerializable(KEY_TYPE));
        RecyclerView rv = binding.listOfWorkers;
        final SwipeRefreshLayout pullToRefresh = binding.pullToRefresh;
        pullToRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.purple_500));
        pullToRefresh.setOnRefreshListener(() -> {
            loadMore(true);
            pullToRefresh.setRefreshing(false);
        });
        int spanCount = 3;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 6;
        }
        switch (listType) {
            case LINEAR:
                adapter = new WorkerListAdapter(WorkerListAdapter.VIEW_TYPE_NORMAL);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case GRID:
                adapter = new WorkerListAdapter(WorkerListAdapter.VIEW_TYPE_SQUARE);
                rv.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
                break;
            case STAGGERED:
                adapter = new WorkerListAdapter(WorkerListAdapter.VIEW_TYPE_SQUARE);
                rv.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        rv.setAdapter(adapter);
        adapter.setOnClickListener((clickedItem, transitionView) -> {
            Pair<View, String> p1 = Pair.create(transitionView.get(0), transitionView.get(0).getTransitionName());
            Pair<View, String> p2 = Pair.create(transitionView.get(1), transitionView.get(1).getTransitionName());
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p2);
            ((WorkerListInteractions) requireActivity()).onItemClick(clickedItem, activityOptions);
        });
        if (savedInstanceState == null) {
            loadMore(false);
        }

        if (rv.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) rv.getLayoutManager());
            rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    loadMore(false);
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return Boolean.TRUE.equals(viewModel.getIsLoadingLiveData().getValue());
                }
            });
        }

        viewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), this::setLoading);
        viewModel.getUsers().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    public void refresh() {
        loadMore(true);
    }

    private void loadMore(boolean refresh) {
        if (getActivity() instanceof ListFilter) {
            ListFilter listFilter = (ListFilter) getActivity();
            viewModel.loadMore(refresh, listFilter.getCurrentSelectedGender(), listFilter.getCountries());
        }
    }

    private void setLoading(boolean isLoading) {
        binding.progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    public interface ListFilter {
        Gender getCurrentSelectedGender();

        Set<String> getCountries();
    }

    interface WorkerListInteractions {
        void onItemClick(WorkerEntity workerEntity, ActivityOptions activityOptions);
    }
}

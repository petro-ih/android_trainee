package com.petro.scope104.ui.list;

import android.app.ActivityOptions;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.petro.scope104.util.PaginationScrollListener;
import com.petro.scope104.R;
import com.petro.scope104.databinding.FragmentWorkersBinding;
import com.petro.scope104.network.RetrofitInstance;
import com.petro.scope104.network.response.UserListResponse;
import com.petro.scope104.network.response.UserResponse;
import com.petro.scope104.ui.WorkerUi;
import com.petro.scope104.util.WorkerUIMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerListFragment extends Fragment {
    private static final String KEY_TYPE = "KEY_TYPE";
    private static final String KEY_DATA = "KEY_DATA";
    private final WorkerListAdapter adapter = new WorkerListAdapter();
    private FragmentWorkersBinding binding;
    private int i = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;

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
        outState.putSerializable(KEY_DATA, adapter.getData());
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
        ListType listType = ((ListType) getArguments().getSerializable(KEY_TYPE));
        RecyclerView rv = binding.listOfWorkers;
        final SwipeRefreshLayout pullToRefresh = binding.pullToRefresh;
        pullToRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.purple_500));
        pullToRefresh.setOnRefreshListener(() -> {
            loadMore(true);
            pullToRefresh.setRefreshing(false);
        });
        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }
        switch (listType) {
            case LINEAR:
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case GRID:
                rv.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
                break;
            case STAGGERED:
                rv.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        rv.setAdapter(adapter);
        adapter.setOnClickListener((clickedItem, transitionView) -> {
            Pair<View, String> p1 = Pair.create(transitionView.get(0), transitionView.get(0).getTransitionName());
            Pair<View, String> p2 = Pair.create(transitionView.get(1), transitionView.get(1).getTransitionName());
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), p1, p2);
            ((WorkerListInteractions) getActivity()).onItemClick(clickedItem, activityOptions);
        });
        if (savedInstanceState != null) {
            adapter.setData((List<WorkerUi>) savedInstanceState.getSerializable(KEY_DATA));
        } else loadMore(false);

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
                    return isLoading;
                }
            });
        }
    }

    private void loadMore(boolean refresh) {
        Log.d("userlist", "refreshData: ");
        setLoading(true);
        if (refresh) {
            i = 0;
        }
        RetrofitInstance.INSTANCE.service.listRepos("ua", i, 10, "seed" + i++).enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                setLoading(false);
                List<WorkerUi> list = response.body() != null ? response.body().results.stream().map(WorkerUIMapper::map).collect(Collectors.toList()) : new ArrayList<>();
                isLastPage = list.size() < 10;
                if (refresh) {
                    adapter.setData(list);
                } else adapter.addData(list);
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                setLoading(false);
                if (refresh) {
                    adapter.setData(new ArrayList<>());
                }
                t.printStackTrace();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        binding.progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    interface WorkerListInteractions {
        void onItemClick(WorkerUi workerUi, ActivityOptions activityOptions);
    }

}

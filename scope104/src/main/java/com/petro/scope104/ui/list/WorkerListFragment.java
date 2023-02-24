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

import com.petro.scope104.R;
import com.petro.scope104.databinding.FragmentWorkersBinding;
import com.petro.scope104.db.DataBase;
import com.petro.scope104.db.entity.CountryEntity;
import com.petro.scope104.db.entity.UserEntity;
import com.petro.scope104.network.RetrofitInstance;
import com.petro.scope104.network.response.UserListResponse;
import com.petro.scope104.ui.WorkerUi;
import com.petro.scope104.util.PaginationScrollListener;
import com.petro.scope104.util.WorkerUIMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerListFragment extends Fragment {
    private static final int PAGE_SIZE = 20;
    private static final String KEY_TYPE = "KEY_TYPE";
    private static final String KEY_DATA = "KEY_DATA";
    private WorkerListAdapter adapter;
    private FragmentWorkersBinding binding;
    private int currentPageNumber = 0;
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
        outState.putSerializable(KEY_DATA, new ArrayList<>(adapter.getCurrentList()));
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
        if (savedInstanceState != null) {
            //noinspection unchecked
            adapter.submitList((List<WorkerUi>) savedInstanceState.getSerializable(KEY_DATA));
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

    public void refresh() {
        loadMore(true);
    }

    private void loadMore(boolean refresh) {
        Log.d("userlist", "refreshData: ");
        setLoading(true);
        if (refresh) {
            currentPageNumber = 0;
        }
        String gender = null;
        String countries = null;
        if (getActivity() instanceof ListFilter) {
            ListFilter listFilter = (ListFilter) getActivity();
            gender = listFilter.getCurrentSelectedGender().serverName;
            countries = String.join(",", listFilter.getCountries());
        }
        RetrofitInstance.INSTANCE.service.listRepos(countries, currentPageNumber++, PAGE_SIZE, gender).enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserListResponse> call, @NonNull Response<UserListResponse> response) {
                setLoading(false);
                List<WorkerUi> list = response.body() != null ? response.body().results.stream().map(WorkerUIMapper::mapNetworkToUi).collect(Collectors.toList()) : new ArrayList<>();
                List<UserEntity> listDB = list.stream().map(WorkerUIMapper::mapUiToDatabase).collect(Collectors.toList());
                List<CountryEntity> countryList = list.stream().map(WorkerUi::getNat).map(WorkerUIMapper::mapUiToCountryEntity).collect(Collectors.toList());
                DataBase.getDataBase(getContext()).userDao().insert(listDB);
                DataBase.getDataBase(getContext()).countryDao().insert(countryList);
                isLastPage = list.size() < 10;
                if (refresh) {
                    adapter.submitList(list);
                } else {
                    ArrayList<WorkerUi> newList = new ArrayList<>(adapter.getCurrentList());
                    newList.addAll(list);
                    adapter.submitList(newList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserListResponse> call, @NonNull Throwable t) {
                setLoading(false);
                if (refresh) {
                    adapter.submitList(new ArrayList<>());
                }
                t.printStackTrace();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (binding == null) {
            return;
        }
        binding.progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    public interface ListFilter {
        Gender getCurrentSelectedGender();

        Set<String> getCountries();
    }

    interface WorkerListInteractions {
        void onItemClick(WorkerUi workerUi, ActivityOptions activityOptions);
    }

}

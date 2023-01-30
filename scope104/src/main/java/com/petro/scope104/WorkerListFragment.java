package com.petro.scope104;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.petro.scope104.network.RetrofitInstance;
import com.petro.scope104.network.response.UserListResponse;
import com.petro.scope104.network.response.UserResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerListFragment extends Fragment {
    private static final String KEY_TYPE = "KEY_TYPE";
    private ListType listType;
    public static Fragment newInstance(ListType type) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_TYPE, type);
        WorkerListFragment fragment = new WorkerListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listType = ((ListType) getArguments().getSerializable(KEY_TYPE));
        RecyclerView rv = view.findViewById(R.id.listOfWorkers);
        switch (listType) {
            case LINEAR:
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case GRID:
                rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
            case STAGGERED:
                rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        WorkerListAdapter adapter = new WorkerListAdapter();
        rv.setAdapter(adapter);
        RetrofitInstance.INSTANCE.service.listRepos("ua", 0, 100, "seed").enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                List<WorkerUi> list = new ArrayList<>();
                UserListResponse r = response.body();
                for (UserResponse user : r.results) {
                    list.add(new WorkerUi(
                            user.picture.medium,
                            user.name.first +" "+ user.name.last,
                            user.dob.date,
                            user.city + user.country
                    ));
                }
                adapter.setData(list);


            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

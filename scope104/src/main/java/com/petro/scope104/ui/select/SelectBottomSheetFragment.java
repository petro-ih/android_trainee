package com.petro.scope104.ui.select;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.petro.scope104.databinding.FragmentSelectBinding;

import java.util.ArrayList;
import java.util.List;


public class SelectBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String KEY_TITLE = "KEY_TITLE";
    private static final String KEY_ITEMS = "KEY_ITEMS";
    private static final String KEY_REQUEST_CODE = "KEY_REQUEST_CODE";

    public interface OnItemSelected{
        void onItemSelected(int requestCode, List<SelectItem> items);
    }

    public static SelectBottomSheetFragment newInstance(int requestCode, String title, List<SelectItem> items) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putInt(KEY_REQUEST_CODE, requestCode);
        args.putSerializable(KEY_ITEMS, new ArrayList<>(items));
        SelectBottomSheetFragment fragment = new SelectBottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentSelectBinding binding;
    private final SelectRVAdapter adapter = new SelectRVAdapter();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.title.setText(requireArguments().getString(KEY_TITLE));
        assert getArguments() != null;
        @SuppressWarnings("unchecked")
        final ArrayList<SelectItem> stringArrayList = (ArrayList<SelectItem>) getArguments().getSerializable(KEY_ITEMS);
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(stringArrayList);
        binding.cta.setOnClickListener(v -> {
            if(getActivity() instanceof OnItemSelected){
                ((OnItemSelected) getActivity()).onItemSelected(getArguments().getInt(KEY_REQUEST_CODE), adapter.getCurrentList());
                dismiss();
            }
        });
    }
}

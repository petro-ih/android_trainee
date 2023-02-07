package com.petro.scope104.ui.list;

import android.annotation.SuppressLint;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petro.scope104.R;
import com.petro.scope104.databinding.ItemWorkersBinding;
import com.petro.scope104.ui.WorkerUi;
import com.petro.scope104.util.DateFormatHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WorkerListAdapter extends ListAdapter<WorkerUi, WorkerListAdapter.WorkerViewHolder> {
//    private final ArrayList<WorkerUi> data = new ArrayList<>();

    interface OnClickListener {
        void onClick(WorkerUi clickedItem, List<View> transitionView);
    }

    private OnClickListener callback;

    public WorkerListAdapter(){
        super(new UserDiffCallBack());
    }

    public void setOnClickListener(OnClickListener callback) {
        this.callback = callback;
    }

//    @SuppressLint("NotifyDataSetChanged")
//    void setData(List<WorkerUi> newData) {
//        data.clear();
//        data.addAll(newData);
//        notifyDataSetChanged();
//    }
//    void addData(List<WorkerUi> newData) {
//        int previousSize = data.size();
//        data.addAll(newData);
//        notifyItemRangeInserted(previousSize, newData.size());
//    }

//    public ArrayList<WorkerUi> getData() {
//        return data;
//    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new WorkerViewHolder(ItemWorkersBinding.inflate(layoutInflater, parent, false), callback);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

//    @Override
//    public int getItemCount() {
//        return data.size();
//    }

    class WorkerViewHolder extends RecyclerView.ViewHolder {
        private final OnClickListener onClickListener;
        private final ItemWorkersBinding binding;
        public WorkerViewHolder(@NonNull ItemWorkersBinding binding, OnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void bind(WorkerUi item) {
            Glide.with(binding.ivAVATAR).load(item.getAvatarUrl()).centerCrop().into(binding.ivAVATAR);
            binding.name.setText(item.getName());
            binding.date.setText(DateFormatHelper.formatDob(item.getDob()));
            binding.city.setText(item.getCity());
            binding.ivAVATAR.setTransitionName(binding.ivAVATAR.getContext().getString(R.string.avatarTransition, item.getUsername()));
            binding.name.setTransitionName(binding.name.getContext().getString(R.string.nameTransition, item.getUsername()));
            itemView.setOnClickListener(v -> {
                if (callback != null)
                    onClickListener.onClick(getItem(getAdapterPosition()), Arrays.asList(binding.ivAVATAR, binding.name));
            });
        }
    }
    private static class UserDiffCallBack extends DiffUtil.ItemCallback<WorkerUi> {

        @Override
        public boolean areItemsTheSame(@NonNull WorkerUi oldItem, @NonNull WorkerUi newItem) {
            return oldItem.getUsername().equals(newItem.getUsername());
        }

        @Override
        public boolean areContentsTheSame(@NonNull WorkerUi oldItem, @NonNull WorkerUi newItem) {
            return oldItem.equals(newItem);
        }
    }

}

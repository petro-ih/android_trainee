package com.petro.scope104.presentation.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.petro.scope104.R;
import com.petro.scope104.databinding.ItemWorkerBinding;
import com.petro.scope104.databinding.ItemWorkerSquareBinding;
import com.petro.scope104.presentation.WorkerUi;
import com.petro.scope104.util.DateFormatHelper;

import java.util.Arrays;
import java.util.List;

public class WorkerListAdapter extends ListAdapter<WorkerUi, WorkerListAdapter.AbstractViewHolder> {
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_SQUARE = 2;

    private OnClickListener callback;
    private final int currentViewType;
    public WorkerListAdapter(int currentViewType) {
        super(new UserDiffCallBack());
        this.currentViewType = currentViewType;
    }

    @Override
    public int getItemViewType(int position) {
        return currentViewType;
    }

    public void setOnClickListener(OnClickListener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_NORMAL:
                return new WorkerViewHolder(parent, callback);
            case VIEW_TYPE_SQUARE:
                return new WorkerGridViewHolder(parent, callback);
        }
        throw new IllegalStateException();
    }


    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    interface OnClickListener {
        void onClick(WorkerUi clickedItem, List<View> transitionView);
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

    abstract static class AbstractViewHolder extends RecyclerView.ViewHolder{

        public AbstractViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public abstract void bind(WorkerUi item);
    }

    static class WorkerViewHolder extends AbstractViewHolder {
        private final OnClickListener onClickListener;
        private final ItemWorkerBinding binding;

        public WorkerViewHolder(@NonNull ItemWorkerBinding binding, OnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public WorkerViewHolder(@NonNull ViewGroup parent, OnClickListener onClickListener) {
            this(ItemWorkerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onClickListener);
        }

        public void bind(WorkerUi item) {
            Glide.with(binding.ivAVATAR).load(item.getAvatarUrl()).centerCrop().into(binding.ivAVATAR);
            binding.name.setText(item.getName());
            binding.date.setText(DateFormatHelper.formatDob(item.getDob()));
            binding.city.setText(item.getCity());
            binding.ivAVATAR.setTransitionName(binding.ivAVATAR.getContext().getString(R.string.avatarTransition, item.getUsername()));
            binding.name.setTransitionName(binding.name.getContext().getString(R.string.nameTransition, item.getUsername()));
            binding.root.setOnClickListener(v -> {
                if (onClickListener != null)
                    onClickListener.onClick(item, Arrays.asList(binding.ivAVATAR, binding.name));
            });
        }
    }

    static class WorkerGridViewHolder extends AbstractViewHolder {
        private final OnClickListener onClickListener;
        private final ItemWorkerSquareBinding binding;

        public WorkerGridViewHolder(@NonNull ItemWorkerSquareBinding binding, OnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public WorkerGridViewHolder(@NonNull ViewGroup parent, OnClickListener onClickListener) {
            this(ItemWorkerSquareBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onClickListener);
        }

        public void bind(WorkerUi item) {
            Glide.with(binding.ivAVATAR).load(item.getAvatarUrlXXL()).centerCrop().into(binding.ivAVATAR);
            binding.name.setText(item.getName());
            binding.ivAVATAR.setTransitionName(binding.ivAVATAR.getContext().getString(R.string.avatarTransition, item.getUsername()));
            binding.name.setTransitionName(binding.name.getContext().getString(R.string.nameTransition, item.getUsername()));
            binding.root.setOnClickListener(v -> {
                if (onClickListener != null)
                    onClickListener.onClick(item, Arrays.asList(binding.ivAVATAR, binding.name));
            });
        }
    }

}

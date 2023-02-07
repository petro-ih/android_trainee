package com.petro.scope104.ui.select;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.petro.scope104.databinding.ItemSelectBinding;

public class SelectRVAdapter extends ListAdapter<SelectItem, SelectRVAdapter.SelectViewHolder> {

    protected SelectRVAdapter() {
        super(new DiffUtil.ItemCallback<SelectItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull SelectItem oldItem, @NonNull SelectItem newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }

            @Override
            public boolean areContentsTheSame(@NonNull SelectItem oldItem, @NonNull SelectItem newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) && (oldItem.getValue() == newItem.getValue());
            }
        });
    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectViewHolder(ItemSelectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class SelectViewHolder extends RecyclerView.ViewHolder {
        private final ItemSelectBinding binding;

        public SelectViewHolder(ItemSelectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        boolean isChangingCheckedState = false;
        public void bind(SelectItem selectItem) {
            binding.getRoot().setText(selectItem.getTitle());

            isChangingCheckedState = true;
            // Do not notify the CheckedChangeListener here
            binding.getRoot().setChecked(selectItem.getValue());
            isChangingCheckedState = false;

            binding.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChangingCheckedState) return;
                selectItem.setValue(isChecked);
                itemView.post(() -> notifyItemChanged(getAdapterPosition()));
            });
        }
    }
}

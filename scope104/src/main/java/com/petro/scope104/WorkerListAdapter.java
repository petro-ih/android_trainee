package com.petro.scope104;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerViewHolder> {
    private final List<WorkerUi> data = new ArrayList<>();

    void setData(List<WorkerUi> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        WorkerViewHolder viewHolder = new WorkerViewHolder(layoutInflater.inflate(R.layout.item_workers, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class WorkerViewHolder extends RecyclerView.ViewHolder {

        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(WorkerUi item) {
            ImageView imageView = itemView.findViewById(R.id.ivAVATAR);
            Glide.with(imageView).load(item.getAvatarUrl()).centerCrop().into(imageView);
            TextView tvName = itemView.findViewById(R.id.name);
            TextView tvDate = itemView.findViewById(R.id.date);
            TextView tvCity = itemView.findViewById(R.id.city);
            tvName.setText(item.getName());
            tvDate.setText(item.getDob().toString());
            tvCity.setText(item.getCity());
        }
    }
}

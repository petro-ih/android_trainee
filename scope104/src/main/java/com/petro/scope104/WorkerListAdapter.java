package com.petro.scope104;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerViewHolder> {
    private final List<WorkerUi> data = new ArrayList<>();

    interface OnClickListener {
        void onClick(WorkerUi clickedItem);
    }

    private OnClickListener callback;

    public void setOnClickListener(OnClickListener callback) {
        this.callback = callback;
    }

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onClick(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class WorkerViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
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
            String dateText = String.format(Locale.getDefault(),"%s, %d years", format.format(item.getDob()), item.getAge());
            tvDate.setText(dateText);
            tvCity.setText(item.getCity());
        }
    }
}

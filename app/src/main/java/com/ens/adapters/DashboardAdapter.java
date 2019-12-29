package com.ens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.model.dashboard.DashboardItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<DashboardItem> dashboardItems;
    private Context context;
    private final OnItemClickListener listener;

    public DashboardAdapter(List<DashboardItem> dashboardItems, Context context, OnItemClickListener listener) {
        this.dashboardItems = dashboardItems;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dashboardItems.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return dashboardItems == null ? 0 : dashboardItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDescribe;
        public ImageView imageView;

        public ViewHolder(View v) {

            super(v);

            txtDescribe = v.findViewById(R.id.txtDescribe);
            imageView = v.findViewById(R.id.imageView);

        }

        public void bind(final DashboardItem dashboardItem, final OnItemClickListener onItemClickListener) {

            Glide.with(itemView.getContext()).load(dashboardItem.getImageUrl()).into(imageView);
            txtDescribe.setText(dashboardItem.getDescription());
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(dashboardItem));

        }

    }

    public interface OnItemClickListener {
        void onItemClick(DashboardItem item);
    }

}

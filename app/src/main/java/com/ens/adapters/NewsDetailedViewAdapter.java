package com.ens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.model.news.NewsItem;
import com.ens.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsDetailedViewAdapter extends RecyclerView.Adapter<NewsDetailedViewAdapter.ItemViewHolder> {

    private List<NewsItem> newsItems;
    private OnItemClickListener mItemClickListener;
    private Context mContext;

    public NewsDetailedViewAdapter(List<NewsItem> newsItems, Context mContext) {
        this.newsItems = newsItems;
        this.mContext = mContext;
    }

    public NewsDetailedViewAdapter(Context mContext) {
        this.newsItems = new ArrayList<>();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_view_news_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        NewsItem newsItem = newsItems.get(position);

        Glide.with(mContext).load(newsItem.getImageUrl()).into(holder.imgDetailedView);
        holder.txtNewsCardHeadline.setText(newsItem.getHeadLine());
        holder.txtNewsCardViewsCount.setText(String.valueOf(newsItem.getViews()));
        holder.txtNewsCardCreatedOn.setText(DateUtils.asPrettyDateTime(newsItem.getCreatedOn()));

    }

    @Override
    public int getItemCount() {
        return newsItems == null ? 0 : newsItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgDetailedView;
        private TextView txtNewsCardHeadline;
        private TextView txtNewsCardCreatedOn;
        private TextView txtNewsCardViewsCount;

        public ItemViewHolder(View view) {
            super(view);

            imgDetailedView = view.findViewById(R.id.imgDetailedView);
            txtNewsCardHeadline = view.findViewById(R.id.txtNewsCardHeadline);
            txtNewsCardCreatedOn = view.findViewById(R.id.txtNewsCardCreatedOn);
            txtNewsCardViewsCount = view.findViewById(R.id.txtNewsCardViewsCount);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, newsItems.get(getAdapterPosition()).getNewsItemId());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, long newsItemId);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    public void addItems(List<NewsItem> items) {
        newsItems.addAll(items);
        notifyDataSetChanged();
    }
}

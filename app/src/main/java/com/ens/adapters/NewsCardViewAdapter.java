package com.ens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.model.NewsCardViewItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsCardViewAdapter extends RecyclerView.Adapter<NewsCardViewAdapter.NewsCardViewHolder> {

    private Context context;
    private List<NewsCardViewItem> newsCardViewItems;

    public NewsCardViewAdapter(Context context, List<NewsCardViewItem> newsCardViewItems) {
        this.context = context;
        this.newsCardViewItems = newsCardViewItems;
    }

    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_page_news_card, viewGroup, false);
        return new NewsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder holder, int position) {
        holder.bind(newsCardViewItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsCardViewItems == null ? 0 : newsCardViewItems.size();
    }


    public class NewsCardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgNewsCard;
        private TextView txtNewsCardHeadline;
        private TextView txtNewsCardDescription;
        private TextView txtNewsCardCreatedOn;

        private TextView txtNewsCardViewsCount;
        private TextView txtNewsCardLikeCount;
        private TextView txtNewsCardUnLikeCount;
        private TextView txtNewsCardCommentsCount;
        private TextView txtNewsCardWhatsAppShareCount;
        private TextView txtNewsCardFacebookShareCount;
        private TextView txtNewsCardHelloAppShareCount;
        private TextView txtNewsCardInstagramShareCount;

        private LinearLayout layoutLike;
        private LinearLayout layoutUnLike;
        private LinearLayout layoutComments;
        private LinearLayout layoutWhatsappShare;
        private LinearLayout layoutFacebookShare;
        private LinearLayout layoutInstagramShare;
        private LinearLayout layoutHelloAppShare;

        public NewsCardViewHolder(@NonNull View view) {
            super(view);

            imgNewsCard = view.findViewById(R.id.imgNewsCard);
            txtNewsCardHeadline = view.findViewById(R.id.txtNewsCardHeadline);
            txtNewsCardDescription = view.findViewById(R.id.txtNewsCardDescription);
            txtNewsCardCreatedOn = view.findViewById(R.id.txtNewsCardCreatedOn);

            txtNewsCardViewsCount = view.findViewById(R.id.txtNewsCardViewsCount);
            txtNewsCardLikeCount = view.findViewById(R.id.txtNewsCardLikeCount);
            txtNewsCardUnLikeCount = view.findViewById(R.id.txtNewsCardUnLikeCount);
            txtNewsCardCommentsCount = view.findViewById(R.id.txtNewsCardCommentsCount);
            txtNewsCardWhatsAppShareCount = view.findViewById(R.id.txtNewsCardWhatsAppShareCount);
            txtNewsCardFacebookShareCount = view.findViewById(R.id.txtNewsCardFacebookShareCount);
            txtNewsCardInstagramShareCount = view.findViewById(R.id.txtNewsCardInstagramShareCount);
            txtNewsCardHelloAppShareCount = view.findViewById(R.id.txtNewsCardHelloAppShareCount);

            layoutLike = view.findViewById(R.id.layoutLike);
            layoutUnLike = view.findViewById(R.id.layoutUnLike);
            layoutComments = view.findViewById(R.id.layoutComments);
            layoutWhatsappShare = view.findViewById(R.id.layoutWhatsappShare);
            layoutFacebookShare = view.findViewById(R.id.layoutFacebookShare);
            layoutHelloAppShare = view.findViewById(R.id.layoutHelloAppShare);
            layoutInstagramShare = view.findViewById(R.id.layoutInstagramShare);

        }

        public void bind(final NewsCardViewItem newsCardViewItem) {

            Glide.with(context).load(newsCardViewItem.getImageUrl()).into(imgNewsCard);
            txtNewsCardHeadline.setText(newsCardViewItem.getHeadLine());
            txtNewsCardDescription.setText(newsCardViewItem.getDescription());
            txtNewsCardCreatedOn.setText(newsCardViewItem.getStrCreatedOn());

            txtNewsCardViewsCount.setText(String.valueOf(newsCardViewItem.getViews()));
            txtNewsCardLikeCount.setText(String.valueOf(newsCardViewItem.getLikes()));
            txtNewsCardUnLikeCount.setText(String.valueOf(newsCardViewItem.getUnLikes()));
            txtNewsCardCommentsCount.setText(String.valueOf(newsCardViewItem.getComments()));
            txtNewsCardWhatsAppShareCount.setText(String.valueOf(newsCardViewItem.getWhatsAppShares()));
            txtNewsCardFacebookShareCount.setText(String.valueOf(newsCardViewItem.getFacebookShares()));
            txtNewsCardInstagramShareCount.setText(String.valueOf(newsCardViewItem.getInstagramShares()));
            txtNewsCardHelloAppShareCount.setText(String.valueOf(newsCardViewItem.getHelloAppShares()));

            layoutLike.setOnClickListener(v -> Toast.makeText(context, String.valueOf(newsCardViewItem.getLikes()), Toast.LENGTH_LONG).show());

        }

    }

}

package com.ens.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.activities.ExoPlayerActivity;
import com.ens.model.news.NewsItem;
import com.ens.utils.DateUtils;
import com.github.abdularis.civ.CircleImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.VideoViewHolder> {

    private Context context;
    private List<NewsItem> newsItems;

    public VideoViewAdapter(Context context, List<NewsItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_page_ens_video_card, viewGroup, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems != null ? newsItems.size() : 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVideoThumbnail;
        private ImageView imgVideoDownload;
        private ImageView imgVideoPlay;
        private TextView txtVideoHeadline;
        private TextView txtVideoDescription;
        private TextView txtNewsCardCreatedOn;
        private TextView txtNewsCardPostedBy;
        private CircleImageView createdByCircleImageView;

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

        public VideoViewHolder(@NonNull View view) {
            super(view);

            imgVideoThumbnail = view.findViewById(R.id.imgVideoThumbnail);
            imgVideoDownload = view.findViewById(R.id.imgVideoDownload);
            imgVideoPlay = view.findViewById(R.id.imgVideoPlay);
            txtVideoHeadline = view.findViewById(R.id.txtVideoHeadline);
            txtVideoDescription = view.findViewById(R.id.txtVideoDescription);
            txtNewsCardCreatedOn = view.findViewById(R.id.txtNewsCardCreatedOn);
            txtNewsCardPostedBy = view.findViewById(R.id.txtNewsCardPostedBy);
            createdByCircleImageView = view.findViewById(R.id.createdByCircleImageView);

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

        public void bind(final NewsItem newsItem) {

            Glide.with(context).load(newsItem.getThumbnailImageUrl()).into(imgVideoThumbnail);
            txtVideoHeadline.setText(newsItem.getHeadLine());
            txtVideoDescription.setText(newsItem.getDescription());
            txtNewsCardCreatedOn.setText(DateUtils.asPrettyDateTime(newsItem.getCreatedOn()));
            txtNewsCardPostedBy.setText(newsItem.getCreatedBy());
            Glide.with(context).load(newsItem.getCreatedByProfileImageUrl()).into(createdByCircleImageView);

            txtNewsCardViewsCount.setText(String.valueOf(newsItem.getViews()));
            txtNewsCardLikeCount.setText(String.valueOf(newsItem.getLikes()));
            txtNewsCardUnLikeCount.setText(String.valueOf(newsItem.getUnLikes()));
            txtNewsCardCommentsCount.setText(String.valueOf(newsItem.getComments()));
            txtNewsCardWhatsAppShareCount.setText(String.valueOf(newsItem.getWhatsAppShares()));
            txtNewsCardFacebookShareCount.setText(String.valueOf(newsItem.getFacebookShares()));
            txtNewsCardInstagramShareCount.setText(String.valueOf(newsItem.getInstagramShares()));
            txtNewsCardHelloAppShareCount.setText(String.valueOf(newsItem.getHelloAppShares()));

            layoutLike.setOnClickListener(v -> Toast.makeText(context, String.valueOf(newsItem.getLikes()), Toast.LENGTH_LONG).show());

            View.OnClickListener onClickListener = v -> playVideo(newsItem.getVideoUrl());

            imgVideoThumbnail.setOnClickListener(onClickListener);
            imgVideoPlay.setOnClickListener(onClickListener);
        }
    }

    private void playVideo(String url) {

        Intent mIntent = ExoPlayerActivity.getStartIntent(context, url);
        context.startActivity(mIntent);

    }

}

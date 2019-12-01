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
import com.ens.model.NewsCardViewItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.VideoViewHolder> {

    private Context context;
    private List<NewsCardViewItem> newsCardViewItems;

    public VideoViewAdapter(Context context, List<NewsCardViewItem> newsCardViewItems) {
        this.context = context;
        this.newsCardViewItems = newsCardViewItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_page_ens_video_card, viewGroup, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(newsCardViewItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsCardViewItems != null ? newsCardViewItems.size() : 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVideoThumbnail;
        private ImageView imgVideoDownload;
        private ImageView imgVideoPlay;
        private TextView txtVideoHeadline;
        private TextView txtVideoDescription;
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

        public VideoViewHolder(@NonNull View view) {
            super(view);

            imgVideoThumbnail = view.findViewById(R.id.imgVideoThumbnail);
            imgVideoDownload = view.findViewById(R.id.imgVideoDownload);
            imgVideoPlay = view.findViewById(R.id.imgVideoPlay);
            txtVideoHeadline = view.findViewById(R.id.txtVideoHeadline);
            txtVideoDescription = view.findViewById(R.id.txtVideoDescription);
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

            Glide.with(context).load(newsCardViewItem.getVideoThumbnailUrl()).into(imgVideoThumbnail);
            txtVideoHeadline.setText(newsCardViewItem.getHeadLine());
            txtVideoDescription.setText(newsCardViewItem.getDescription());
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

            View.OnClickListener onClickListener = v -> playVideo(newsCardViewItem.getVideoUrl());

            imgVideoThumbnail.setOnClickListener(onClickListener);
            imgVideoPlay.setOnClickListener(onClickListener);
        }
    }

    private void playVideo(String url) {

        Intent mIntent = ExoPlayerActivity.getStartIntent(context, url);
        context.startActivity(mIntent);

    }

}

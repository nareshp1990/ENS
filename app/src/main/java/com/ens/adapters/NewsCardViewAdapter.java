package com.ens.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.activities.CommentActivity;
import com.ens.activities.NewsCardDetailedActivity;
import com.ens.bus.NewsActionEvent;
import com.ens.config.ENSApplication;
import com.ens.model.news.ActionType;
import com.ens.model.news.NewsItem;
import com.ens.service.NewsService;
import com.ens.utils.AppUtils;
import com.ens.utils.DateUtils;
import com.github.abdularis.civ.CircleImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;

public class NewsCardViewAdapter extends RecyclerView.Adapter<NewsCardViewAdapter.NewsCardViewHolder> {

    private Context context;
    private List<NewsItem> newsItems;
    private NewsService newsService;
    private EventBus eventBus = EventBus.getDefault();

    public NewsCardViewAdapter(Context context, List<NewsItem> newsItems, NewsService newsService) {
        this.context = context;
        this.newsItems = newsItems;
        this.newsService = newsService;
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_page_news_card, viewGroup, false);
        return new NewsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems == null ? 0 : newsItems.size();
    }


    public class NewsCardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgNewsCard;
        private TextView txtNewsCardHeadline;
        private TextView txtNewsCardDescription;
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
        private LinearLayout touchViewLayout;


        public NewsCardViewHolder(@NonNull View view) {
            super(view);

            imgNewsCard = view.findViewById(R.id.imgNewsCard);
            txtNewsCardHeadline = view.findViewById(R.id.txtNewsCardHeadline);
            txtNewsCardDescription = view.findViewById(R.id.txtNewsCardDescription);
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
            touchViewLayout = view.findViewById(R.id.touchViewLayout);

        }

        public void bind(final NewsItem newsItem) {

            Glide.with(context).load(newsItem.getImageUrl()).into(imgNewsCard);
            txtNewsCardHeadline.setText(newsItem.getHeadLine());
            txtNewsCardDescription.setText(newsItem.getDescription());
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

            layoutLike.setOnClickListener(v -> postNewsItemAction(newsItem.getNewsItemId(), ActionType.LIKE, this));
            layoutUnLike.setOnClickListener(v -> postNewsItemAction(newsItem.getNewsItemId(), ActionType.UNLIKE, this));
            layoutComments.setOnClickListener(v -> {

                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("newsItemId",newsItem.getNewsItemId());
                context.startActivity(intent);


            });
            layoutWhatsappShare.setOnClickListener(v -> {

                AppUtils.launchShareIntent(context, touchViewLayout, newsItem);

                postNewsItemAction(newsItem.getNewsItemId(), ActionType.WHATSAPP, this);

            });
            layoutFacebookShare.setOnClickListener(v -> {

                AppUtils.launchShareIntent(context, touchViewLayout, newsItem);

                postNewsItemAction(newsItem.getNewsItemId(), ActionType.FACEBOOK, this);

            });
            layoutHelloAppShare.setOnClickListener(v -> {

                AppUtils.launchShareIntent(context, touchViewLayout, newsItem);

                postNewsItemAction(newsItem.getNewsItemId(), ActionType.HELLO_APP, this);

            });
            layoutInstagramShare.setOnClickListener(v -> {

                AppUtils.launchShareIntent(context, touchViewLayout, newsItem);

                postNewsItemAction(newsItem.getNewsItemId(), ActionType.INSTAGRAM, this);

            });


            touchViewLayout.setOnClickListener(v -> {
                postNewsItemAction(newsItem.getNewsItemId(), ActionType.VIEW, this);
                openDetailedNewsActivity(newsItem);
            });

        }

    }

    private void openDetailedNewsActivity(final NewsItem newsItem) {

        Intent intent = new Intent(context, NewsCardDetailedActivity.class);
        intent.putExtra("newsItemId", newsItem.getNewsItemId());
        context.startActivity(intent);

    }

    public void postNewsItemAction(Long newsItemId, ActionType actionType, NewsCardViewHolder holder) {
        newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, actionType, holder);
    }

    public void onEvent(NewsActionEvent newsActionEvent) {

        newsActionEvent.getNewsCardViewHolder().txtNewsCardViewsCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getViews()));
        newsActionEvent.getNewsCardViewHolder().txtNewsCardLikeCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getLikes()));
        newsActionEvent.getNewsCardViewHolder().txtNewsCardUnLikeCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getUnLikes()));
        newsActionEvent.getNewsCardViewHolder().txtNewsCardCommentsCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getComments()));
        newsActionEvent.getNewsCardViewHolder().txtNewsCardWhatsAppShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getWhatsAppShares()));
        newsActionEvent.getNewsCardViewHolder().txtNewsCardFacebookShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getFacebookShares()));
        newsActionEvent.getNewsCardViewHolder().txtNewsCardInstagramShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getInstagramShares()));
        newsActionEvent.getNewsCardViewHolder().txtNewsCardHelloAppShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getHelloAppShares()));
    }

}

package com.ens.adapters;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.model.news.comment.Comment;
import com.ens.utils.DateUtils;
import com.github.abdularis.civ.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewAdapter.ViewHolder> {

    private List<Comment> comments;
    private Context context;

    public CommentViewAdapter(Context context) {
        this.comments = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item, viewGroup, false);
        return new CommentViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView createdByCircleImageView;
        private TextView txtUserComment;
        private TextView txtCreatedOn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            createdByCircleImageView = itemView.findViewById(R.id.createdByCircleImageView);
            txtUserComment = itemView.findViewById(R.id.txtUserComment);
            txtCreatedOn = itemView.findViewById(R.id.txtCreatedOn);

        }

        public void bind(final Comment comment) {

            Glide.with(itemView.getContext()).load(comment.getCommentedByProfileImageUrl()).into(createdByCircleImageView);
            txtCreatedOn.setText(DateUtils.asPrettyDateTime(comment.getCreatedOn()));
            setTextWithSpan(txtUserComment, comment.getCommentedByName(), comment.getComment(), new StyleSpan(android.graphics.Typeface.BOLD));

        }

        private void setTextWithSpan(TextView textView, String text, String spanText, StyleSpan style) {
            SpannableStringBuilder sb = new SpannableStringBuilder(text);
            int start = text.indexOf(spanText);
            int end = start + spanText.length();
            sb.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textView.setText(sb);
        }

    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addItems(List<Comment> list) {
        comments.addAll(list);
        notifyDataSetChanged();
    }

}

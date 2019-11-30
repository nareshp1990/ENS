package com.ens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ens.R;
import com.ens.model.PollCardItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PollCardViewAdapter extends RecyclerView.Adapter<PollCardViewAdapter.PollCardViewHolder> {

    private Context context;
    private List<PollCardItem> pollCardItems;

    public PollCardViewAdapter(Context context, List<PollCardItem> pollCardItems) {
        this.context = context;
        this.pollCardItems = pollCardItems;
    }

    @NonNull
    @Override
    public PollCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_page_poll_card, viewGroup, false);
        return new PollCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollCardViewHolder holder, int position) {
        holder.bind(pollCardItems.get(position));
    }

    @Override
    public int getItemCount() {
        return pollCardItems != null ? pollCardItems.size() : 0;
    }


    public class PollCardViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPollQuestion;
        private RadioGroup radioGroupPoll;
        private RadioButton rdbOption1;
        private RadioButton rdbOption2;
        private RadioButton rdbOption3;
        private RadioButton rdbOption4;
        private TextView txtPollQuestionCreatedOn;
        private TextView txtTotalVotes;

        public PollCardViewHolder(@NonNull View view) {
            super(view);

            txtPollQuestion = view.findViewById(R.id.txtPollQuestion);

            radioGroupPoll = view.findViewById(R.id.radioGroupPoll);
            rdbOption1 = view.findViewById(R.id.rdbOption1);
            rdbOption2 = view.findViewById(R.id.rdbOption2);
            rdbOption3 = view.findViewById(R.id.rdbOption3);
            rdbOption4 = view.findViewById(R.id.rdbOption4);

            txtPollQuestionCreatedOn = view.findViewById(R.id.txtPollQuestionCreatedOn);
            txtTotalVotes = view.findViewById(R.id.txtTotalVotes);

        }

        public void bind(final PollCardItem pollCardItem) {

            txtPollQuestion.setText(pollCardItem.getQuestion());

            rdbOption1.setText(pollCardItem.getOption1() + "  ( " + pollCardItem.getOption1Votes() + " votes )");
            rdbOption2.setText(pollCardItem.getOption2() + "  ( " + pollCardItem.getOption2Votes() + " votes )");
            rdbOption3.setText(pollCardItem.getOption3() + "  ( " + pollCardItem.getOption3Votes() + " votes )");
            rdbOption4.setText(pollCardItem.getOption4() + "  ( " + pollCardItem.getOption4Votes() + " votes )");

            txtPollQuestionCreatedOn.setText(pollCardItem.getStrPollCreatedOn());
            txtTotalVotes.setText(String.valueOf(pollCardItem.getTotalVotes()) + " votes");

        }

    }

}

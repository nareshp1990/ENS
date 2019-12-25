package com.ens.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ens.R;
import com.ens.bus.VoteCastedEvent;
import com.ens.config.ENSApplication;
import com.ens.model.poll.Choice;
import com.ens.model.poll.Poll;
import com.ens.model.poll.VoteRequest;
import com.ens.service.PollService;
import com.ens.utils.DateUtils;
import com.skydoves.progressview.ProgressView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;

import static android.view.View.TEXT_ALIGNMENT_VIEW_START;
import static android.view.View.TEXT_DIRECTION_LTR;

public class PollCardViewAdapter extends RecyclerView.Adapter<PollCardViewAdapter.PollCardViewHolder> {

    public static final String TAG = PollCardViewAdapter.class.getCanonicalName();
    private static DecimalFormat df = new DecimalFormat("0");

    private Context context;
    private List<Poll> polls;
    private PollService pollService;
    private EventBus eventBus = EventBus.getDefault();

    public PollCardViewAdapter(Context context, List<Poll> polls, PollService pollService) {
        this.context = context;
        this.polls = polls;
        this.pollService = pollService;
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @NonNull
    @Override
    public PollCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_page_poll_card, viewGroup, false);
        return new PollCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollCardViewHolder holder, int position) {
        holder.bind(polls.get(position));
    }

    @Override
    public int getItemCount() {
        return polls != null ? polls.size() : 0;
    }


    public class PollCardViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPollQuestion;
        private RadioGroup radioGroupPoll;
        private TextView txtPollQuestionCreatedOn;
        private TextView txtTotalVotes;
        private LinearLayout progressViewLayout;

        public PollCardViewHolder(@NonNull View view) {
            super(view);

            txtPollQuestion = view.findViewById(R.id.txtPollQuestion);
            radioGroupPoll = view.findViewById(R.id.radioGroupPoll);
            txtPollQuestionCreatedOn = view.findViewById(R.id.txtPollQuestionCreatedOn);
            txtTotalVotes = view.findViewById(R.id.txtTotalVotes);
            progressViewLayout = view.findViewById(R.id.progressViewLayout);

        }

        public void bind(final Poll poll) {

            txtPollQuestion.setText(poll.getQuestion());
            txtPollQuestionCreatedOn.setText(DateUtils.asPrettyDateTime(poll.getCreationDateTime()));
            txtTotalVotes.setText(poll.getTotalVotes() + " vote(s)");

            if (poll.getChoices() != null) {

                if (poll.getSelectedChoice()!=null && poll.getSelectedChoice()>0){

                    radioGroupPoll.setVisibility(View.GONE);

                    poll.getChoices().stream().forEach(choice -> {

                        int progressBarColor = poll.getSelectedChoice() == choice.getId() ? ContextCompat.getColor(context, R.color.red) : ContextCompat.getColor(context, R.color.blue);

                        ProgressView progressView = getProgressView(choice,progressBarColor);

                        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(layoutParams);
                        params.bottomMargin = 10;

                        progressViewLayout.addView(progressView,params);

                    });

                }else {

                    progressViewLayout.setVisibility(View.GONE);

                    poll.getChoices().stream().forEach(choice -> {

                        RadioButton rb = new RadioButton(context);
                        rb.setText(choice.getText());
                        rb.setSelected(poll.getSelectedChoice() == choice.getId());
                        rb.setChecked(poll.getSelectedChoice() == choice.getId());

                        radioGroupPoll.addView(rb);

                    });

                }

            }

            radioGroupPoll.setOnCheckedChangeListener((group, checkedId) -> {

                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);

                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();

                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {

                    //Get Selected Choice Id
                    String selectedStr = checkedRadioButton.getText().toString();

                    Optional<Choice> selectedChoiceOptional = poll.getChoices().stream().filter(choice -> choice.getText().equals(selectedStr)).findFirst();

                    if (selectedChoiceOptional.isPresent()) {

                        Long selectedChoice = selectedChoiceOptional.get().getId();
                        castVote(poll.getId(),selectedChoice,this);

                    }

                }

            });

        }

    }

    private void castVote(Long pollId, Long selectedChoiceId, PollCardViewHolder pollCardViewHolder) {

        pollService.castVote(ENSApplication.getLoggedInUserId(),pollId,new VoteRequest(selectedChoiceId),pollCardViewHolder);

    }

    public void onEvent(VoteCastedEvent voteCastedEvent){

        Log.d(TAG, "Vote Casted Successfully");

        Toast.makeText(context, "Vote Casted Successfully", Toast.LENGTH_SHORT).show();

        if (voteCastedEvent != null && voteCastedEvent.getPoll() != null && voteCastedEvent.getPollCardViewHolder() != null ) {

            Poll poll = voteCastedEvent.getPoll();

            voteCastedEvent.getPollCardViewHolder().txtTotalVotes.setText(poll.getTotalVotes() + " vote(s)");

        }

    }

    private ProgressView getProgressView(Choice choice, int selectedProgressColor){

        /*TextForm textForm = TextForm.Builder(context)
                .setText("This is a TextForm")
                .setTextColor(R.color.colorPrimary)
                .setTextSize(14f)
                .setTextTypeFace(Typeface.BOLD)
                .build();*/

        ProgressView progressView = new ProgressView.Builder(context)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, 100)
                .setProgress((float) choice.getPollPercentage())
                .setMax(100f)
                .setMin(15f)
                .setRadius(20f)
                .setDuration(1200L)
                .setAutoAnimate(true)
                .setColorBackground(ContextCompat.getColor(context, R.color.grey))
//                .setProgressbarColor(ContextCompat.getColor(context, R.color.blue))
                .setProgressbarColor(selectedProgressColor)
                .setLabelColorInner(ContextCompat.getColor(context, R.color.white))
                .setLabelColorOuter(ContextCompat.getColor(context, R.color.red))
                .setLabelText(choice.getText() + "  " + df.format(choice.getPollPercentage()) +"%")
//                .setLabelSize(13f)
//                .setLabelSpace(10f)
                .setLabelTypeface(Typeface.NORMAL)
                .setProgressbarPadding(10f)
                .setProgressbarRadius(20f)
                .build();

        progressView.setTextDirection(TEXT_DIRECTION_LTR);
        progressView.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);

        return progressView;
    }


}

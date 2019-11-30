package com.ens.model;

public class PollCardItem {

    private String pollId;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int option1Votes;
    private int option2Votes;
    private int option3Votes;
    private int option4Votes;
    private String strPollCreatedOn;
    private int totalVotes;

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getOption1Votes() {
        return option1Votes;
    }

    public void setOption1Votes(int option1Votes) {
        this.option1Votes = option1Votes;
    }

    public int getOption2Votes() {
        return option2Votes;
    }

    public void setOption2Votes(int option2Votes) {
        this.option2Votes = option2Votes;
    }

    public int getOption3Votes() {
        return option3Votes;
    }

    public void setOption3Votes(int option3Votes) {
        this.option3Votes = option3Votes;
    }

    public int getOption4Votes() {
        return option4Votes;
    }

    public void setOption4Votes(int option4Votes) {
        this.option4Votes = option4Votes;
    }

    public String getStrPollCreatedOn() {
        return strPollCreatedOn;
    }

    public void setStrPollCreatedOn(String strPollCreatedOn) {
        this.strPollCreatedOn = strPollCreatedOn;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}

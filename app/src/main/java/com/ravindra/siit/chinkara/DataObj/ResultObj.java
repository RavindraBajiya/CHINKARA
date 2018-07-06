package com.ravindra.siit.chinkara.DataObj;

public class ResultObj {
    int maximumQuestions;
    int attemptQuestions;
    int rightQuestions;
    int wrongQuestions;
    float positiveMarking;
    float negativeMarking;
    float totalMarks;
    String overAllResultInPercentage;

    public ResultObj(int maximumQuestions, int attemptQuestions, int rightQuestions, int wrongQuestions, float positiveMarking, float negativeMarking, float totalMarks, String overAllResultInPercentage) {
        this.maximumQuestions = maximumQuestions;
        this.attemptQuestions = attemptQuestions;
        this.rightQuestions = rightQuestions;
        this.wrongQuestions = wrongQuestions;
        this.positiveMarking = positiveMarking;
        this.negativeMarking = negativeMarking;
        this.totalMarks = totalMarks;
        this.overAllResultInPercentage = overAllResultInPercentage;
    }

    public ResultObj() {
    }

    public int getMaximumQuestions() {
        return maximumQuestions;
    }

    public void setMaximumQuestions(int maximumQuestions) {
        this.maximumQuestions = maximumQuestions;
    }

    public int getAttemptQuestions() {
        return attemptQuestions;
    }

    public void setAttemptQuestions(int attemptQuestions) {
        this.attemptQuestions = attemptQuestions;
    }

    public int getRightQuestions() {
        return rightQuestions;
    }

    public void setRightQuestions(int rightQuestions) {
        this.rightQuestions = rightQuestions;
    }

    public int getWrongQuestions() {
        return wrongQuestions;
    }

    public void setWrongQuestions(int wrongQuestions) {
        this.wrongQuestions = wrongQuestions;
    }

    public float getPositiveMarking() {
        return positiveMarking;
    }

    public void setPositiveMarking(float positiveMarking) {
        this.positiveMarking = positiveMarking;
    }

    public float getNegativeMarking() {
        return negativeMarking;
    }

    public void setNegativeMarking(float negativeMarking) {
        this.negativeMarking = negativeMarking;
    }

    public float getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(float totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getOverAllResultInPercentage() {
        return overAllResultInPercentage;
    }

    public void setOverAllResultInPercentage(String overAllResultInPercentage) {
        this.overAllResultInPercentage = overAllResultInPercentage;
    }
}

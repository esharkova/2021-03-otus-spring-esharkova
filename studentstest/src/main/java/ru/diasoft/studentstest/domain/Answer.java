package ru.diasoft.studentstest.domain;

public class Answer {

    private int answernumber;
    private String answertext;

    public Answer(int answernumber, String answertext){
        this.answernumber = answernumber;
        this.answertext = answertext;
    }

    public String getAnswertext() {
        return answertext;
    }

    public int getAnswernumber() {
        return answernumber;
    }
}

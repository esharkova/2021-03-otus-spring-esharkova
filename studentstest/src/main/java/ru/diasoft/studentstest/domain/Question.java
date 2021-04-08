package ru.diasoft.studentstest.domain;

public class Question {
    private int questionnumber;
    private String questiontext;
    private String right_answer;


    public Question(int questionnumber, String questiontext, String right_answer)
    {
        this.questionnumber = questionnumber;
        this.questiontext = questiontext;
        this.right_answer = right_answer;
    }

    public String getQuestiontext()
    {
        return questiontext;
    }

    public int getQuestionnumber()
    {
        return questionnumber;
    }

    public String getRight_answer() {
        return right_answer;
    }
}

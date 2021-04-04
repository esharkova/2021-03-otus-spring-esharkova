package ru.diasoft.studentstest.domain;

public class Question {
    private int questionnumber;
    private String questiontext;


    public Question(int questionnumber, String questiontext)
    {
        this.questionnumber = questionnumber;
        this.questiontext = questiontext;
    }

    public String getQuestiontext()
    {
        return questiontext;
    }

    public int getQuestionnumber()
    {
        return questionnumber;
    }
}

package ru.diasoft.StudentsTest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer {

    private int answerNumber;
    private String answerText;

    public Answer(String answertext){
        this.answerText = answertext;
    }

}

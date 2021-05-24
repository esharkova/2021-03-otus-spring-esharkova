package ru.diasoft.StudentsTest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private int questionNumber;
    private String questionText;
    private String rightAnswer;


}

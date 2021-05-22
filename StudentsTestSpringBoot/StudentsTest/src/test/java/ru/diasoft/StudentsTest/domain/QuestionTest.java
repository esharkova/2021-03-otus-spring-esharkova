package ru.diasoft.StudentsTest.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Класс Question")
class QuestionTest {
    @DisplayName("корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor(){
        Question question = new Question(1, "Question", "RightAnswer");

        assertEquals(question.getQuestionNumber(),1);
        assertEquals(question.getQuestionText(),"Question");
        assertEquals(question.getRightAnswer(),"RightAnswer");
    }

}
package ru.diasoft.studentstest.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Question")
class QuestionTest {
    @DisplayName("корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor(){
        Question question = new Question(1, "Question", "RightAnswer");

        assertEquals(question.getQuestionnumber(),1);
        assertEquals(question.getQuestiontext(),"Question");
        assertEquals(question.getRightAnswer(),"RightAnswer");
    }

}
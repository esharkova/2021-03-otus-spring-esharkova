package ru.diasoft.studentstest.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Answer")
class AnswerTest {
    @DisplayName("корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor(){
        Answer answer = new Answer(1, "Answer");

        assertEquals(answer.getAnswernumber(), 1);
        assertEquals(answer.getAnswertext(), "Answer");
    }

}
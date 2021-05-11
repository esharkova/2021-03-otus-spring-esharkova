package ru.diasoft.studentstest.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.diasoft.studentstest.domain.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс AnswerDaoSimpleTest")
class AnswerDaoSimpleTest {



    @DisplayName("должен записывать ответ")
    @Test
    void saveAnswer() {
        Answer answer = new Answer(1, "Answer1");
        AnswerDaoSimple dao = new AnswerDaoSimple();

        dao.saveAnswer(answer);

        assertEquals(dao.answerList.size(), 1);

    }

    @DisplayName("должен возвращать список ответов")
    @Test
    void getAnswersList() {

        Answer answer1 = new Answer(1, "Answer1");
        Answer answer2 = new Answer(2, "Answer2");

        AnswerDaoSimple dao = new AnswerDaoSimple();

        dao.saveAnswer(answer1);
        dao.saveAnswer(answer2);

        assertNotNull(dao.getAnswersList());
        assertThat(dao.getAnswersList()).hasSize(2);
    }
}
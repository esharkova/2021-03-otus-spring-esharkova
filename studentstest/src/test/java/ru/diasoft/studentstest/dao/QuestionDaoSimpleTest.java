package ru.diasoft.studentstest.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import ru.diasoft.studentstest.Config.AppConfig;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Класс QuestionDao")
@PropertySource("classpath:app.properties")
class QuestionDaoSimpleTest {

    @Mock
    @Value("${template}")
    private Resource resourceFile;

    @DisplayName("должен возвращать список вопросов")
    @Test
    void getList_question() throws IOException {

        QuestionDaoSimple dao = new QuestionDaoSimple(resourceFile);

        assertNotNull(dao.getList_question());

    }

}
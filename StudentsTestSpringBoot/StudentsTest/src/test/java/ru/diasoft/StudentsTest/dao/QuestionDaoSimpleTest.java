package ru.diasoft.StudentsTest.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;

import org.springframework.test.context.TestPropertySource;

import ru.diasoft.StudentsTest.Config.AppConfig;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс QuestionDao")
@TestPropertySource("classpath:application.yml")
@SpringBootTest
class QuestionDaoSimpleTest {


    @Value("${testing.filename}")
    private Resource resourceFile;

    @Mock
    private MessageSource messageSource;
    @Mock
    private AppConfig appConfig;

    @DisplayName("должен возвращать список вопросов")
    @Test
    void getListQuestionTest() throws IOException {


        QuestionDaoSimple dao = new QuestionDaoSimple(appConfig, messageSource);
        given(appConfig.getFileName()).willReturn(resourceFile);

        assertNotNull(dao.getListQuestion());

    }

}
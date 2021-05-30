package ru.diasoft.StudentsTest.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.StudentsTest.Config.AppConfig;
import ru.diasoft.StudentsTest.dao.QuestionDao;
import ru.diasoft.StudentsTest.domain.Answer;
import ru.diasoft.StudentsTest.domain.Question;
import ru.diasoft.StudentsTest.domain.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class TestingServiceImplTest {

    private static final String GREETING_PATTERN = "Welcome: %s";
    private static final String CUSTOM_LOGIN = "Sharkova Elena";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";
    private static final String COMMAND_START = "start";
    private static final String COMMAND_START_SHORT = "s";
    private static final String COMMAND_START_RESULT = "Testing is over!";

    private TestingServiceImpl testingService;
    private Student student = new Student("Sharkova", "Elena", false);


    @Value("${testing.count}")
    private int count;


    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private ReaderService readerService;

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private AppConfig appConfig;

    @BeforeEach
    void setUp(){
        testingService = new TestingServiceImpl(readerService, questionDao, appConfig, messageSource);
    }

    @Autowired
    private Shell shell;


    private ArrayList<Answer> FillAnswer(){
        ArrayList<Answer> list = new ArrayList<Answer>();
        for (int i = 0; i < 5; i++ ){
            String stringAnswer = "Answer" + (i+1);
            list.add(new Answer(i+1, stringAnswer));
        }
        return list;
    }

    private ArrayList<Question> FillQuestions(){
        ArrayList<Question> list = new ArrayList<Question>();
        for (int i = 0; i < 5; i++ ){
            String stringQuestion = "Question" + (i+1);
            String stringAnswer = "Answer" + (i+1);
            list.add(new Question(i+1, stringQuestion, stringAnswer));
        }
        return list;
    }

    @DisplayName(" должен возвращать приветствие для всех форм команды логина")
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
        given(readerService.saveStudent("Sharkova", "Elena")).willReturn(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
        String res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @DisplayName("должен получать список вопросов и ответов студента")
    @Test
    void testingTest() throws IOException {

        given(questionDao.getListQuestion()).willReturn(FillQuestions());
        given(readerService.getStudent()).willReturn(student);
        given(readerService.getAnswerList()).willReturn(FillAnswer());

        assertAll("student",
                ()-> assertEquals("Elena", readerService.getStudent().getName()),
                ()-> assertEquals("Sharkova", readerService.getStudent().getSurname()),
                ()->assertFalse(readerService.getStudent().getResult())
        );

        assertNotNull(readerService.getAnswerList());
        assertNotNull(questionDao.getListQuestion());
        assertEquals(readerService.getAnswerList().size(),questionDao.getListQuestion().size());
        assertThat(readerService.getAnswerList()).extracting(Answer::getAnswerText).contains("Answer1", "Answer2", "Answer3", "Answer4", "Answer5");
        assertThat(questionDao.getListQuestion()).extracting(Question::getRightAnswer).contains("Answer1", "Answer2", "Answer3", "Answer4", "Answer5");
        assertThat(questionDao.getListQuestion()).extracting(Question::getQuestionText).contains("Question1", "Question2", "Question3", "Question4", "Question5");
        assertThat(testingService.checkAnswers(questionDao.getListQuestion(), readerService.getAnswerList())).isBetween(0,5);

        readerService.getStudent().setResult(true);
        assertTrue(readerService.getStudent().getResult());

    }

    @DisplayName("должен сравнивать список правильных ответов и ответов студента и выдавать результат")
    @Test
    void checkAnswersTest() throws IOException {


        given(questionDao.getListQuestion()).willReturn(FillQuestions());
        given(readerService.getAnswerList()).willReturn(FillAnswer());

        assertNotNull(readerService.getAnswerList());
        assertNotNull(questionDao.getListQuestion());
        assertThat(questionDao.getListQuestion().size()).isEqualTo(5);
        assertEquals(questionDao.getListQuestion().size(),readerService.getAnswerList().size());


        for (int i= 0; i < 5; i++){
            assertEquals(questionDao.getListQuestion().get(i).getQuestionNumber(), readerService.getAnswerList().get(i).getAnswerNumber());
            assertEquals(questionDao.getListQuestion().get(i).getRightAnswer(), readerService.getAnswerList().get(i).getAnswerText());
        }

        assertThat(testingService.checkAnswers(questionDao.getListQuestion(), readerService.getAnswerList())).isEqualTo(5);

    }
}
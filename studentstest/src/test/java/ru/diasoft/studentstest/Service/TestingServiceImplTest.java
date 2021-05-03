package ru.diasoft.studentstest.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.dao.StudentDao;
import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Question;
import ru.diasoft.studentstest.domain.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("Класс TestingServiceImplTest")
@ExtendWith(MockitoExtension.class)
class TestingServiceImplTest {

    private List<Question> listQuestion;
    private TestingServiceImpl testingServiceImpl;
    private Student student;

    @Value("${count}")
    private String count;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private  ReaderService readerService;

    @InjectMocks
    private TestingServiceImpl TestingServiceImpl;

    @BeforeEach
    void setUp() {

    }



    @DisplayName("должен получать список вопросов и ответов студента")
    @Test
    void testingTest() throws IOException {

        Student student = new Student("Sharkova", "Elena");
        List<Question> listQuestion = new ArrayList<Question>();

        for (int i = 0; i < 5; i++ ){
            String stringQuestion = "Question" + (i+1);
            String stringAnswer = "Answer" + (i+1);
            listQuestion.add(new Question(i+1, stringQuestion, stringAnswer));
        }

        List<Answer> listAnswer = new ArrayList<Answer>(); //массив ответов
        for (int i= 0; i < 5; i++){
            String string = "Answer" + (i+1);
            Answer answer = new Answer(i+1, string);
            listAnswer.add(answer);
        }

        /*testingTest();*/
        student.setResult(true);

        assertAll("student",
                ()-> assertEquals("Elena", student.getName()),
                ()-> assertEquals("Sharkova", student.getSurname()),
                ()->assertTrue(student.getResult())
        );

        assertNotNull(listAnswer);
        assertEquals(listAnswer.size(),listQuestion.size());
        assertThat(listAnswer).extracting(Answer::getAnswertext).contains("Answer1", "Answer2", "Answer3", "Answer4", "Answer5");
        assertThat(listQuestion).extracting(Question::getRight_answer).contains("Answer1", "Answer2", "Answer3", "Answer4", "Answer5");
        assertThat(student.getResultText())
                .startsWith(student.getSurname())
                .endsWith("пройден!");

    }

}
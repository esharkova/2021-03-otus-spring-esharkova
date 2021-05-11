package ru.diasoft.studentstest.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Question;
import ru.diasoft.studentstest.domain.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@DisplayName("Класс TestingServiceImplTest")
@ExtendWith(MockitoExtension.class)
class TestingServiceImplTest {

    private TestingServiceImpl testingServiceImpl;
    private Student student = new Student("Sharkova", "Elena");


    @Value("${count}")
    private String count;

    @Mock
    private QuestionDao questionDao;
    @Mock
    private ReaderService readerService;

    @InjectMocks
    private TestingServiceImpl testingService;


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
        assertThat(readerService.getAnswerList()).extracting(Answer::getAnswertext).contains("Answer1", "Answer2", "Answer3", "Answer4", "Answer5");
        assertThat(questionDao.getListQuestion()).extracting(Question::getRightAnswer).contains("Answer1", "Answer2", "Answer3", "Answer4", "Answer5");
        assertThat(questionDao.getListQuestion()).extracting(Question::getQuestiontext).contains("Question1", "Question2", "Question3", "Question4", "Question5");
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
            assertEquals(questionDao.getListQuestion().get(i).getQuestionnumber(), readerService.getAnswerList().get(i).getAnswernumber());
            assertEquals(questionDao.getListQuestion().get(i).getRightAnswer(), readerService.getAnswerList().get(i).getAnswertext());
        }

        assertThat(testingService.checkAnswers(questionDao.getListQuestion(), readerService.getAnswerList())).isEqualTo(5);

    }
}
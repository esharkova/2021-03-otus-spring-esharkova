package ru.diasoft.StudentsTest.Service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.diasoft.StudentsTest.Config.AppConfig;
import ru.diasoft.StudentsTest.dao.QuestionDao;
import ru.diasoft.StudentsTest.domain.Answer;
import ru.diasoft.StudentsTest.domain.Question;
import ru.diasoft.StudentsTest.domain.Student;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Service
public class TestingServiceImpl implements TestingService{
    private final QuestionDao questionDao;
    private final int count;
    private final ReaderService readerService;
    private final MessageSource messageSource;
    private final Locale locale;


    public TestingServiceImpl(ReaderService readerService, QuestionDao questionDao, AppConfig config, MessageSource messageSource) {
        this.readerService = readerService;
        this.questionDao = questionDao;
        this.count = config.getCount();
        this.messageSource = messageSource;
        this.locale = config.getLocale();
    }

    @Override
    public void testing() throws IOException {

        Student student = readerService.getStudent();

        for (int i = 0; i < questionDao.getListQuestion().size(); i++) {

            System.out.println(questionDao.getListQuestion().get(i).getQuestionNumber() + ". " + questionDao.getListQuestion().get(i).getQuestionText());
            readerService.saveAnswer();
        }

        if (checkAnswers(questionDao.getListQuestion(), readerService.getAnswerList())>= count) {
            student.setResult(true);
        } else {
            student.setResult(false);
        }

        System.out.println(student.getSurname() + " " + student.getName() + " "
                + (student.getResult() ? messageSource.getMessage("strings.success", null, locale) : messageSource.getMessage("strings.failure", null, locale)));

    }

    @Override
    public int checkAnswers(List<Question> questionList, List<Answer> answerList) {

        int countRightAnswer = 0;

        //сравнение ответа на вопрос и правильного ответа
        for (int j = 0; j < questionList.size(); j++) {
            System.out.println(questionList.get(j).getQuestionText()
                    + messageSource.getMessage("strings.correctAnswer", null, locale)
                    + questionList.get(j).getRightAnswer());
            System.out.println(messageSource.getMessage("strings.answered", null, locale)
                    + answerList.get(j).getAnswerText());
            if (answerList.get(j).getAnswerText().equals(questionList.get(j).getRightAnswer())) {
                countRightAnswer++;
                System.out.println(messageSource.getMessage("strings.right", null, locale));
            } else {
                System.out.println(messageSource.getMessage("strings.wrong", null, locale));
            }
        }
        return countRightAnswer;
    }
}

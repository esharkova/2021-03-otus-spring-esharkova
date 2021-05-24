package ru.diasoft.StudentsTest.dao;

import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.diasoft.StudentsTest.Config.AppConfig;
import ru.diasoft.StudentsTest.domain.Question;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class QuestionDaoSimple implements QuestionDao{
    private final Resource resourceFile;

    private final MessageSource messageSource;
    private final Locale locale;

    public QuestionDaoSimple(AppConfig config, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = config.getLocale();
        this.resourceFile = config.getFileName();
    }

    /*public Question findByNumber(int number, String question, String rightAnswer) {
        return new Question(number, question, rightAnswer);
    }*/

    public Question addQuestion(int number, String question, String rightAnswer) {
        return new Question(number, question, rightAnswer);
    }

    public List<Question> getListQuestion() throws IOException {

        List<Question> listQuestion = new ArrayList<Question>(); //массив вопросов

        String line; //для работы со строкой из файла
        int i = 1, charindex1, charindex2;
        String questionStr, rightAnswerStr;

        try (InputStream is = resourceFile.getInputStream()) {
            try (InputStreamReader streamReader =  new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {

                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);
                    charindex1 = line.indexOf("(");
                    charindex2 = line.indexOf(")");

                    questionStr = line.substring(0, charindex1);
                    rightAnswerStr = line.substring(charindex1 + 1, charindex2);

                    //вопрос
                    //Question question = questionDao.findByNumber(i, question_str, right_answer_str);
                    Question question = addQuestion(i, questionStr, rightAnswerStr);
                    //System.out.println(question.getQuestionnumber() +". "+ question.getQuestiontext());
                    listQuestion.add(question);
                    i++;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listQuestion;
    }

}

package ru.diasoft.studentstest.dao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.diasoft.studentstest.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoSimple implements QuestionDao {

    private final Resource resourceFile;

    public QuestionDaoSimple(@Value("${template}") Resource resourceFile) {
        this.resourceFile = resourceFile;
    }

    public Question findByNumber(int number, String question, String right_answer) {
        return new Question(number, question, right_answer);
    }

    public Question addQuestion(int number, String question, String right_answer) {
        return new Question(number, question, right_answer);
    }

    public List<Question> getListQuestion() throws IOException {

        List<Question> list_question = new ArrayList<Question>(); //массив вопросов

        String line; //для работы со строкой из файла
        int i = 1, charindex1, charindex2;
        String question_str, right_answer_str;

        try (InputStream is = resourceFile.getInputStream()) {
            try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {

                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);
                    charindex1 = line.indexOf("(");
                    charindex2 = line.indexOf(")");

                    question_str = line.substring(0, charindex1);
                    right_answer_str = line.substring(charindex1 + 1, charindex2);

                    //вопрос
                    //Question question = questionDao.findByNumber(i, question_str, right_answer_str);
                    Question question = addQuestion(i, question_str, right_answer_str);
                    //System.out.println(question.getQuestionnumber() +". "+ question.getQuestiontext());
                    list_question.add(question);
                    i++;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list_question;
    }

}

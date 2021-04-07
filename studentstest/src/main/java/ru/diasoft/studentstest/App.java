package ru.diasoft.studentstest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.dao.QuestionResource;
import ru.diasoft.studentstest.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionDao questionDao = context.getBean(QuestionDao.class);
        QuestionResource questionResource = context.getBean(QuestionResource.class);
        Resource resource = questionResource.getTemplate();

        System.out.println(resource.getFilename());
        InputStream is = resource.getInputStream();

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            int i = 1;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                Question question = questionDao.findByNumber(i, line);
                System.out.println(question.getQuestionnumber() +". "+ question.getQuestiontext());
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

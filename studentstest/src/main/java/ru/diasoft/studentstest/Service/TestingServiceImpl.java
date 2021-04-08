package ru.diasoft.studentstest.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.AnswerDao;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.dao.StudentDao;
import ru.diasoft.studentstest.domain.Answer;
import ru.diasoft.studentstest.domain.Question;
import ru.diasoft.studentstest.domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class TestingServiceImpl implements TestingService {
    private final StudentDao studentDao;
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;
    private final Resource resourceFile;
    private final String count;


    public TestingServiceImpl(StudentDao studentDao, QuestionDao questionDao, AnswerDao answerDao, @Value("${template}") Resource resourceFile, @Value("${count}") String count){
        this.studentDao = studentDao;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.resourceFile = resourceFile;
        this.count = count;
    }

    public void testing () throws IOException {

//        System.out.println(resourceFile.getFilename());
//        System.out.println(count);

        BufferedReader readeranswer = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter your surname");
        String surname = readeranswer.readLine();
        System.out.println("Enter your name");
        String name = readeranswer.readLine();

        Student student =  studentDao.addStudent(surname,name);

        String line; //для работы со строкой из файла
        ArrayList<Question> list_question = new ArrayList<Question>(); //массив вопросов
        ArrayList<Answer> list_answer = new ArrayList<Answer>(); //массив ответов

        int i = 1, count_right_answer =0;
        int charindex1, charindex2;
        String question_str, right_answer_str, student_answer;

        InputStream is = resourceFile.getInputStream();
        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                charindex1 = line.indexOf("(");
                charindex2 = line.indexOf(")");

                question_str = line.substring(0,charindex1);
                right_answer_str = line.substring(charindex1+1,charindex2);

                //вопрос
                //Question question = questionDao.findByNumber(i, question_str, right_answer_str);
                Question question = questionDao.addQuestion(i, question_str, right_answer_str);
                System.out.println(question.getQuestionnumber() +". "+ question.getQuestiontext());
                list_question.add(question);

                //ответ
                student_answer = readeranswer.readLine();
                Answer answer = answerDao.addAnswer(i,student_answer);
                //Answer answer = questionDao.CreateAnswerByNumberi,student_answer;
                list_answer.add(answer);

                i++;
            }

            //сравнение ответа на вопрос и правильного ответа
            for (int j = 0; j < list_question.size(); j++){
                System.out.println(list_question.get(j).getQuestiontext() + "Правильный ответ: " + list_question.get(j).getRight_answer());
                System.out.println("Дан ответ: " + list_answer.get(j).getAnswertext());
                if (list_answer.get(j).getAnswertext().equals(list_question.get(j).getRight_answer())){
                    count_right_answer++;
                    System.out.println("Правильно!");
                }
                else {System.out.println("НЕ правильно!");}
            }

            System.out.println("Итого правильных ответов " + count_right_answer);
            if (count_right_answer >= Integer.parseInt(count)){
                student.setResult(true);
            }
            else {
                student.setResult(false);
            }
            System.out.println(student.getSurname() + " " + student.getName() + " " + (student.getResult()?"Тест успешно пройден!":"Тест не пройден!"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

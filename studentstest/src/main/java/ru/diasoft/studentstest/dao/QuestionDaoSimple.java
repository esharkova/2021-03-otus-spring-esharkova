package ru.diasoft.studentstest.dao;
import org.springframework.stereotype.Component;
import ru.diasoft.studentstest.domain.Question;

@Component
public class QuestionDaoSimple implements QuestionDao {

    public Question findByNumber(int number, String question, String right_answer){
        return new Question(number, question, right_answer );
    }

    public Question addQuestion(int number, String question, String right_answer){
        return new Question(number, question, right_answer );
    }
}

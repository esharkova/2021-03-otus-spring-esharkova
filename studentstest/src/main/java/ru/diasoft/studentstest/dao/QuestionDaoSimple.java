package ru.diasoft.studentstest.dao;
import ru.diasoft.studentstest.domain.Question;

public class QuestionDaoSimple implements QuestionDao {

    public Question findByNumber(int Number, String Question){
        return new Question(Number, Question);
    }
}

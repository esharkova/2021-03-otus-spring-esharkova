package ru.diasoft.studentstest.Service;

import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.QuestionDao;
import ru.diasoft.studentstest.domain.Question;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public Question getByNumber(int number ,String question, String right_answer) {
        return dao.findByNumber(number, question, right_answer);
    }

    public Question createQuestion(int number ,String question, String right_answer) {
        return dao.addQuestion(number, question, right_answer);
    }

}

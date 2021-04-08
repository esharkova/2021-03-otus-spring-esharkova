package ru.diasoft.studentstest.Service;

import org.springframework.stereotype.Service;
import ru.diasoft.studentstest.dao.AnswerDao;
import ru.diasoft.studentstest.domain.Answer;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerDao dao;

    public AnswerServiceImpl(AnswerDao dao){
        this.dao = dao;
    }

    public Answer createAnswer(int number , String answer) {
        return dao.addAnswer(number, answer);
    }
}

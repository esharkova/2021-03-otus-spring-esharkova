package ru.diasoft.studentstest.dao;

import org.springframework.core.io.Resource;

public class QuestionResource {
    private Resource template;

    public void setTemplate(Resource template) {
        this.template = template;
    }

    public Resource getTemplate(){
        return template;
    }
}

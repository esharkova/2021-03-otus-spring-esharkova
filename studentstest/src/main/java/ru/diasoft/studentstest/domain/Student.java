package ru.diasoft.studentstest.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Student {
    private String surname;
    private String name;
    private Boolean result;

    public Student(String surname, String name){
        this.surname = surname;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}

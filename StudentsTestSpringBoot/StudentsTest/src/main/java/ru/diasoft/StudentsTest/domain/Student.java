package ru.diasoft.StudentsTest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    private String surname;
    private String name;
    private Boolean result;

    public void getResultText(){
        System.out.println(this.surname + " " + this.name + " " + (this.result ? "Тест успешно пройден!" : "Тест не пройден!"));
    }
}

package ru.diasoft.studentstest.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс Student")
class StudentTest {

    @DisplayName("корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor(){

        Student student = student();

        assertThat(student)
                .hasFieldOrPropertyWithValue("result", false)
                .matches(p -> p.getName().equals("Elena"))
                .matches(p -> p.getSurname().equals("Sharkova"));

    }

    @DisplayName("должен записывать результат - зачет")
    @Test
    void shouldSetResultTrue() {

        Student student = student();

        student.setResult(true);
        assertTrue(student.getResult());
    }

    private Student student(){
        return new Student("Sharkova", "Elena");
    }
}
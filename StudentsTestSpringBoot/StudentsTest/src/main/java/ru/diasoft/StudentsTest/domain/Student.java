package ru.diasoft.StudentsTest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import ru.diasoft.StudentsTest.Config.AppConfig;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    private String surname;
    private String name;
    private Boolean result;

}

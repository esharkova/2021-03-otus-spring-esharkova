package ru.diasoft.studentstest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.diasoft.studentstest.Service.TestingService;

import java.io.IOException;

@ComponentScan(basePackages = "ru.diasoft")
public class App
{
    public static void main( String[] args ) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        TestingService service = context.getBean(TestingService.class);

        try {
            service.testing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

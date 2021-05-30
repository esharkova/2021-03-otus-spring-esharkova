package ru.diasoft.StudentsTest.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.StudentsTest.Config.AppConfig;

import java.io.IOException;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private String studentFullName;
    private final ReaderService readerService;
    private final TestingService testingService;
    private final MessageSource messageSource;
    private final AppConfig appConfig;

    @ShellMethod(value = "Login command",  key = {"l", "login"})
    public String login(String surname, String name) {
        studentFullName = readerService.saveStudent(surname, name);
        return studentFullName;

    }

    @ShellMethod(value = "Start testing command",  key = {"s", "start"})
    @ShellMethodAvailability(value = "isStartTestingCommandAvailable")
    public String testing() throws IOException {
        testingService.testing();
        return String.format(messageSource.getMessage("strings.over", null, appConfig.getLocale()));
    }

    private Availability isStartTestingCommandAvailable() {
        return studentFullName == null? Availability.unavailable(messageSource.getMessage("strings.available", null, appConfig.getLocale())): Availability.available();
    }
}


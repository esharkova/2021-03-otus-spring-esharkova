package ru.diasoft.StudentsTest.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "testing")
@Component
public class AppConfig {
    private Resource fileName;
    private int count;
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Resource getFileName() {
        return fileName;
    }

    public void setFileName(Resource fileName) {
        this.fileName = fileName;
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}

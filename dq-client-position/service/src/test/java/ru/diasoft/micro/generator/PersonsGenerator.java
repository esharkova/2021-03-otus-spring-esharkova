package ru.diasoft.micro.generator;

import ru.diasoft.micro.model.PersonDeleteDto;
import ru.diasoft.micro.model.PersonInsertDto;
import ru.diasoft.micro.model.PersonUpdateDto;

public class PersonsGenerator {
    public static final long PERSON_ID = 1;
    public static final String EXTERNAL_NUMBER = "externalNumber";
    public static final String NAME = "NAME";
    public static final String PERSON_BRIEF = "personBrief";
    public static final String SURNAME = "SURNAME";
    public static final String PATRONYMIC_NAME = "PATRONYMICNAME";

    public static PersonInsertDto getPersonInsertDto() {
        return PersonInsertDto.builder()
                .personID(PERSON_ID)
                .externalNumber(EXTERNAL_NUMBER)
                .surName(SURNAME)
                .name(NAME)
                .patronymicName(PATRONYMIC_NAME)
                .personBrief(PERSON_BRIEF)
                .build();
    }

    public static PersonUpdateDto getPersonUpdateDto() {
        return PersonUpdateDto.builder()
                .personID(PERSON_ID)
                .externalNumber(EXTERNAL_NUMBER)
                .surName(SURNAME)
                .name(NAME)
                .patronymicName(PATRONYMIC_NAME)
                .personBrief(PERSON_BRIEF)
                .build();
    }

    public static PersonDeleteDto getPersonDeleteDto() {
        return PersonDeleteDto.builder()
                .personID(PERSON_ID)
                .build();
    }
}

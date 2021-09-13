package ru.diasoft.micro.generator;

import ru.diasoft.micro.model.LegalDeleteDto;
import ru.diasoft.micro.model.LegalInsertDto;
import ru.diasoft.micro.model.LegalUpdateDto;

public class LegalsGenerator {
    public static final long LEGAL_ID = 1;
    public static final String EXTERNAL_NUMBER = "externalNumber";
    public static final String NAME = "name";

    public static LegalInsertDto getLegalInsertDto() {
        return LegalInsertDto.builder()
                .legalID(LEGAL_ID)
                .externalNumber(EXTERNAL_NUMBER)
                .name(NAME)
                .build();
    }

    public static LegalUpdateDto getLegalUpdateDto() {
        return LegalUpdateDto.builder()
                .legalID(LEGAL_ID)
                .externalNumber(EXTERNAL_NUMBER)
                .name(NAME)
                .build();
    }

    public static LegalDeleteDto getLegalDeleteDto() {
        return LegalDeleteDto.builder()
                .legalID(LEGAL_ID)
                .build();
    }
}

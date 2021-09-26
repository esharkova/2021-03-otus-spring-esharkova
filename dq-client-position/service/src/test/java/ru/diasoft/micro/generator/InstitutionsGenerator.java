package ru.diasoft.micro.generator;

import ru.diasoft.micro.model.InstitutionDeleteDto;
import ru.diasoft.micro.model.InstitutionInsertDto;
import ru.diasoft.micro.model.InstitutionUpdateDto;

public class InstitutionsGenerator {
    public static final long INTSTITUTION_ID = 1;
    public static final int  INTSTITUTION_TYPE = 1;
    public static final String INTSTITUTION_NAME = "institutionName";


    public static InstitutionInsertDto getInstitutionInsertDto() {
        return InstitutionInsertDto.builder()
                .institutionID(INTSTITUTION_ID)
                .institutionType(INTSTITUTION_TYPE)
                .institutionName(INTSTITUTION_NAME)
                .build();
    }

    public static InstitutionUpdateDto getInstitutionUpdateDto() {
        return InstitutionUpdateDto.builder()
                .institutionID(INTSTITUTION_ID)
                .institutionType(INTSTITUTION_TYPE)
                .institutionName(INTSTITUTION_NAME)
                .build();
    }

    public static InstitutionDeleteDto getInstitutionDeleteDto() {
       return InstitutionDeleteDto.builder()
               .institutionID(INTSTITUTION_ID)
               .build();
    }
}

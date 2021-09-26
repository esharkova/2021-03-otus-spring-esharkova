package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;

public class ClientGenerator {

    public static final long CLIENT_ID = 1;
    public static final String CLIENT_NAME = "clientName";
    public static final int CLIENT_TYPE = 1;
    public static final String EXTERNAL_NUMBER = "externalNumber";

    public static final String EMPTY_STRING = "";
    public static final int LEGAL_TYPE = 2;
    public static final int PERSON_TYPE = 1;

    public static Client getClient(){
        return Client.builder()
                .clientID(CLIENT_ID)
                .clientType(CLIENT_TYPE)
                .externalNumber(EXTERNAL_NUMBER)
                .name(CLIENT_NAME)
                .build();
    }

    public static Client getClientInstitution(){
        return Client.builder()
                .clientID(InstitutionsGenerator.INTSTITUTION_ID)
                .clientType(InstitutionsGenerator.INTSTITUTION_TYPE)
                .externalNumber(EMPTY_STRING)
                .name(InstitutionsGenerator.INTSTITUTION_NAME)
                .build();
    }

    public static Client getClientLegal(){
        return Client.builder()
                .clientID(LegalsGenerator.LEGAL_ID)
                .clientType(LEGAL_TYPE)
                .externalNumber(LegalsGenerator.EXTERNAL_NUMBER)
                .name(LegalsGenerator.NAME)
                .build();
    }

    public static Client getClientPerson(){
        return Client.builder()
                .clientID(PersonsGenerator.PERSON_ID)
                .clientType(PERSON_TYPE)
                .externalNumber(PersonsGenerator.EXTERNAL_NUMBER)
                .name(PersonsGenerator.SURNAME + " " + PersonsGenerator.NAME + " " + PersonsGenerator.PATRONYMIC_NAME)
                .build();
    }
}

package ru.diasoft.micro.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.model.*;

@Mapper
@DecoratedWith(ClientMapperDecorator.class)
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    String CLIENT_TYPE_EMPTY = "";
    String CLIENT_TYPE_1 = "1";
    String CLIENT_TYPE_2 = "2";


    @Mappings({
            @Mapping(source = "institutionID", target = "clientID"),
            @Mapping(source = "institutionType", target = "clientType"),
            @Mapping(target = "externalNumber" , constant = CLIENT_TYPE_EMPTY),
            @Mapping(source = "institutionName", target = "name")})
    Client InstitutionInsertDtoToClient(InstitutionInsertDto institutionInsertDto);

    @Mappings({
            @Mapping(source = "institutionID", target = "clientID"),
            @Mapping(source = "institutionType", target = "clientType"),
            @Mapping(target = "externalNumber" , constant = CLIENT_TYPE_EMPTY),
            @Mapping(source = "institutionName", target = "name")})
    Client InstitutionUpdateDtoToClient(InstitutionUpdateDto institutionUpdateDto);

    @Mapping(source = "institutionID", target = "clientID")
    Client InstitutionDeleteDtoToClient(InstitutionDeleteDto institutionDeleteDto);

    @Mappings({
            @Mapping(source = "legalID", target = "clientID"),
            @Mapping(target = "clientType", constant = CLIENT_TYPE_2),
            @Mapping(source = "externalNumber", target = "externalNumber"),
            @Mapping(source = "name", target = "name")})
    Client LegalInsertDtoToClient(LegalInsertDto legalInsertDto);

    @Mappings({
            @Mapping(source = "legalID", target = "clientID"),
            @Mapping(target = "clientType", constant = CLIENT_TYPE_2),
            @Mapping(source = "externalNumber", target = "externalNumber"),
            @Mapping(source = "name", target = "name")})
    Client LegalUpdateDtoToClient(LegalUpdateDto legalUpdateDto);

    @Mapping(source = "legalID", target = "clientID")
    Client LegalDeleteDtoToClient(LegalDeleteDto legalDeleteDto);

    @Mappings({
            @Mapping(source = "personID", target = "clientID"),
            @Mapping(target = "clientType", constant = CLIENT_TYPE_1),
            @Mapping(source = "externalNumber", target = "externalNumber")})
    Client personInsertDtoToClient(PersonInsertDto personInsertDto);

    @Mappings({
            @Mapping(source = "personID", target = "clientID"),
            @Mapping(target = "clientType" , constant = CLIENT_TYPE_1),
            @Mapping(source = "externalNumber", target = "externalNumber")})
    Client personUpdateDtoToClient(PersonUpdateDto personUpdateDto);

    @Mapping(source = "personID", target = "clientID")
    Client PersonDeleteDtoToClient(PersonDeleteDto personDeleteDto);

}
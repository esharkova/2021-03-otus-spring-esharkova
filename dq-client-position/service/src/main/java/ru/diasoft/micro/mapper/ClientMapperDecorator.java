package ru.diasoft.micro.mapper;

import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.model.PersonInsertDto;
import ru.diasoft.micro.model.PersonUpdateDto;

public abstract class ClientMapperDecorator implements ClientMapper{
    private final ClientMapper delegate;

    protected ClientMapperDecorator(ClientMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public Client personUpdateDtoToClient(PersonUpdateDto personUpdateDto){
        Client client = delegate.personUpdateDtoToClient(personUpdateDto);
        client.setName(personUpdateDto.getSurName() + " " + personUpdateDto.getName() + " " + personUpdateDto.getPatronymicName());
        return client;
    }

    @Override
    public Client personInsertDtoToClient(PersonInsertDto personInsertDto){
        Client client = delegate.personInsertDtoToClient(personInsertDto);
        client.setName(personInsertDto.getSurName() + " " + personInsertDto.getName() + " " + personInsertDto.getPatronymicName());
        return client;
    }
}

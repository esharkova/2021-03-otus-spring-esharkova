package ru.diasoft.micro.service;


import ru.diasoft.micro.model.SendLimitDto;

import java.text.ParseException;
import java.util.List;

public interface LimitsTransformService {
    List<SendLimitDto> transform(Object msg) throws ParseException;
}

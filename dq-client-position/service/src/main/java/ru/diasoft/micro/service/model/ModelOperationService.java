package ru.diasoft.micro.service.model;

import ru.diasoft.micro.domain.ModelOperation;

import java.util.List;

public interface ModelOperationService {
    List<ModelOperation> saveModelOperationList(List<ModelOperation> modelOperationList);

    ModelOperation saveModelOperation(ModelOperation modelOperation);
}

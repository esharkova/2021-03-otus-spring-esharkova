package ru.diasoft.micro.service.model;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.ModelOperation;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.repository.ModelOperationRepository;
import ru.diasoft.micro.service.model.ModelOperationService;

import java.util.List;

@RequiredArgsConstructor
@Primary
@Loggable
@Service
public class ModelOperationServiceImpl implements ModelOperationService {

    private final ModelOperationRepository modelOperationsRepository;

    @Override
    public List<ModelOperation> saveModelOperationList(List<ModelOperation> modelOperationList) {
        return modelOperationsRepository.saveAll(modelOperationList);
    }

    @Override
    public ModelOperation saveModelOperation(ModelOperation modelOperation) {
        return modelOperationsRepository.save(modelOperation);
    }
}

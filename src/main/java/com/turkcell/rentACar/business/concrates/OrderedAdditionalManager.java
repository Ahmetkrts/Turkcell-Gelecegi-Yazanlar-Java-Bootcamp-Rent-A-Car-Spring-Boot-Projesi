package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.AdditionalService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACar.business.abstracts.RentCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.orderedAdditional.OrderedAdditionalListDto;
import com.turkcell.rentACar.business.request.orderedAdditional.CreateOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.orderedAdditional.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.orderedAdditional.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalDao;
import com.turkcell.rentACar.entities.concrates.OrderedAdditional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalManager implements OrderedAdditionalService {
    private OrderedAdditionalDao orderedAdditionalDao;
    private ModelMapperService modelMapperService;
    private AdditionalService additionalService;
    private RentCarService rentCarService;

    @Autowired
    public OrderedAdditionalManager(OrderedAdditionalDao orderedAdditionalDao, ModelMapperService modelMapperService,
                                    @Lazy AdditionalService additionalService,
                                    @Lazy RentCarService rentCarService) {
        this.orderedAdditionalDao = orderedAdditionalDao;
        this.modelMapperService = modelMapperService;
        this.additionalService = additionalService;
        this.rentCarService = rentCarService;

    }

    @Override
    public Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException {
        this.rentCarService.checkIfRentCarExists(createOrderedAdditionalRequest.getRentCarId());
        this.additionalService.checkIfAdditionIdExists(createOrderedAdditionalRequest.getAdditionalId());
        OrderedAdditional response = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest, OrderedAdditional.class);
        response.setOrderedAdditionalId(0);
        this.orderedAdditionalDao.save(response);
        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {
        checkIfOrderedAdditionalIdExists(updateOrderedAdditionalRequest.getOrderedAdditionalId());
        this.rentCarService.checkIfRentCarExists(updateOrderedAdditionalRequest.getRentCarId());
        this.additionalService.checkIfAdditionIdExists(updateOrderedAdditionalRequest.getAdditionalId());
        OrderedAdditional response = this.modelMapperService.forRequest().map(updateOrderedAdditionalRequest, OrderedAdditional.class);
        this.orderedAdditionalDao.save(response);
        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException {
        checkIfOrderedAdditionalIdExists(deleteOrderedAdditionalRequest.getOrderedAdditionalId());

        OrderedAdditional response = this.modelMapperService.forRequest().map(deleteOrderedAdditionalRequest, OrderedAdditional.class);
        this.orderedAdditionalDao.deleteById(response.getOrderedAdditionalId());
        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<OrderedAdditionalListDto>> getAll() {
        List<OrderedAdditional> response = this.orderedAdditionalDao.findAll();
        List<OrderedAdditionalListDto> result = response
                .stream()
                .map(orderedAdditional -> this.modelMapperService.forDto().map(orderedAdditional, OrderedAdditionalListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<OrderedAdditionalListDto>>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<List<OrderedAdditionalListDto>> getById(int orderedAdditionalId) throws BusinessException {
        checkIfOrderedAdditionalIdExists(orderedAdditionalId);

        List<OrderedAdditional> response = this.orderedAdditionalDao.findAllByOrderedAdditionalId(orderedAdditionalId);
        List<OrderedAdditionalListDto> result = response
                .stream()
                .map(orderedAdditional -> this.modelMapperService.forDto().map(orderedAdditional, OrderedAdditionalListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<OrderedAdditionalListDto>>(result, orderedAdditionalId + BusinessMessages.ORDERED_ADDITIONAL_LISTED);
    }

    @Override
    public void addAdditionals(int rentId, List<Integer> additionalList) throws BusinessException {
        this.rentCarService.checkIfRentCarExists(rentId);

        for (Integer additionalId : additionalList) {
            this.additionalService.checkIfAdditionIdExists(additionalId);
            CreateOrderedAdditionalRequest createOrderedAdditionalRequest = new CreateOrderedAdditionalRequest(rentId, additionalId);
            this.add(createOrderedAdditionalRequest);
        }
    }

    private void checkIfOrderedAdditionalIdExists(int orderedAdditionalId) throws BusinessException {
        if (!this.orderedAdditionalDao.existsById(orderedAdditionalId)) {
            throw new BusinessException(orderedAdditionalId + BusinessMessages.ORDERED_ADDITIONAL_NOT_FOUND);
        }
    }

    @Override
    public List<Integer> getAdditionalIdsByRentId(int rentId) {
        List<OrderedAdditional> orderedAdditional = this.orderedAdditionalDao.getByRentCarRentCarId(rentId);
        return orderedAdditional.stream()
                .map(additional -> this.modelMapperService.forDto().map(additional.getAdditional().getAdditionalId(), Integer.class))
                .collect(Collectors.toList());
    }


    public double totalAdditionalDailyPriceCalculator(List<Integer> additionalList) throws BusinessException {
        double total = 0;
        for (Integer integer : additionalList) {
            total += this.additionalService.getById(integer).getData().getDailyPrice();
        }
        return total;
    }

}

package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.color.ColorGetDto;
import com.turkcell.rentACar.business.dtos.color.ColorListDto;
import com.turkcell.rentACar.business.request.color.CreateColorRequest;
import com.turkcell.rentACar.business.request.color.DeleteColorRequest;
import com.turkcell.rentACar.business.request.color.UpdateColorRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface ColorService {

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    DataResult<List<ColorListDto>> getAll();

    DataResult<ColorGetDto> getById(int colorId) throws BusinessException;

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException;

    void checkIfColorIdExists(int colorId) throws BusinessException;

}
//Brand update,delete,getById
//Color update,delete,getById
//Car-- id,dailyPrice,modelYear,description,Brand,Color
//Carlist,add,update,delete,getById
//Araba listelenirken brandName,colorName
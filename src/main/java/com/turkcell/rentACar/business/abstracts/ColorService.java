package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.ColorGetDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.request.CreateColorRequest;
import com.turkcell.rentACar.business.request.DeleteColorRequest;
import com.turkcell.rentACar.business.request.UpdateColorRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface ColorService {

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    DataResult<List<ColorListDto>> getAll();

    DataResult<ColorGetDto> getById(int colorId);

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result delete(DeleteColorRequest deleteColorRequest);

}
//Brand update,delete,getById
//Color update,delete,getById
//Car-- id,dailyPrice,modelYear,description,Brand,Color
//Carlist,add,update,delete,getById
//Araba listelenirken brandName,colorName
package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.ColorGetDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.request.*;
import com.turkcell.rentACar.core.exception.BusinessException;

import java.util.List;

public interface ColorService {

    void add(CreateColorRequest createColorRequest) throws BusinessException;

    List<ColorListDto> getAll();

    ColorGetDto getById(int colorId);

    void update(UpdateColorRequest updateColorRequest) throws BusinessException;

    void delete(DeleteColorRequest deleteColorRequest);

}
//Brand update,delete,getById
//Color update,delete,getById
//Car-- id,dailyPrice,modelYear,description,Brand,Color
//Carlist,add,update,delete,getById
//Araba listelenirken brandName,colorName
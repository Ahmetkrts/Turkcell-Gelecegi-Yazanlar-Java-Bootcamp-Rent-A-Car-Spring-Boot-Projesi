package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorGetDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.request.CreateColorRequest;
import com.turkcell.rentACar.business.request.DeleteColorRequest;
import com.turkcell.rentACar.business.request.UpdateColorRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concrates.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public void add(CreateColorRequest createColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

        if (checkIfName(color.getName())) {
            this.colorDao.save(color);
        }

    }

    @Override
    public List<ColorListDto> getAll() {
        List<Color> result = this.colorDao.findAll();
        return result
                .stream()
                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public ColorGetDto getById(int colorId) {
        Color result = this.colorDao.getById(colorId);
        return this.modelMapperService.forDto().map(result, ColorGetDto.class);
    }

    @Override
    public void update(UpdateColorRequest updateColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

        if (checkIfName(color.getName())) {
            this.colorDao.save(color);
        }
    }

    @Override
    public void delete(DeleteColorRequest deleteColorRequest) {
        Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
        this.colorDao.delete(color);
    }

    private boolean checkIfName(String name) throws BusinessException {
        var result = this.getAll();

        for (ColorListDto colorListDto : result) {
            if (colorListDto.getName().equals(name)) {
                throw new BusinessException("İsimler Aynı Olamaz");

            }
        }
        return true;

    }
}

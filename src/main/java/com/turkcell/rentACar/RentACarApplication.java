package com.turkcell.rentACar;

import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.ErrorDataResult;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestControllerAdvice
public class RentACarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentACarApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }


    //Global Exception Handler Advice-> VAlidation,BussinessException
    //CarMaintenance-->id,descriptions,returnDate,Car
    //CRUD Operations,getByCarId,Create,Update,Delete
    //bakımda olan araç kiraya verilemez

    //arabalar kiralanırken müşteriler ek hizmetler alabilir bebe koltugu ,scoter vs AdditionalService
    //Arabalar kiralanırken farklı bir şehire bırakılabilir
    //Kiralamaya 750 tl eklenir

    //OrderedAdditionalService

    //-----------------



    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationErrors(MethodArgumentNotValidException argumentNotValidException) {
        Map<String, String> handleValidation = new HashMap<String, String>();

        for (FieldError fieldError : argumentNotValidException.getBindingResult().getFieldErrors()) {
            handleValidation.put(fieldError.getField(), fieldError.getCode());
        }
        ErrorDataResult<Object> error = new ErrorDataResult<Object>(handleValidation, "Validations Error");
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleBusinessErrors(BusinessException businessException) {
        return new ErrorDataResult<Object>(businessException.getMessage(), "Business Error");
    }

}

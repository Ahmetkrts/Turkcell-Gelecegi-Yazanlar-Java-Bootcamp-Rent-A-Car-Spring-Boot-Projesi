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
/*
* #Çalışmayan kısımlar
* Customer silme işlemleri sıkıntılı silme gerçekleşmiyor
*
* */

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

    /*
    IndividualCustomer(id,FirstName,LastName,NationalIdetny
    CorporateCustomer(id,CompanyName,TaxNumber
    müşteriler Aynı Zamanda kullanıcıdır,
    User-> id,Email,Password

    bireysele kiralama
    kurumsala kiralama

    tüm kiralama sonuncunda fatura kesilecek(Invoice,)
    * Şirketimiz büyüdü. Kurumsal müşteriler araba kiralayabilmelidir.
    *  (Kurumsal Müşteri – vergiNo, Şirket ismi,email…. Kiralama kuralları bireysel müşteri ile aynıdır.
    *  14- Tüm kiralamalar sonucunda fatura kesilmelidir. (
    Fatura No, Oluşturma Tarihi, Kiralama tarihleri
    * , Toplam kiralama gün sayısı, kiralama tutarı,musteri) Belirli tarih aralığında tüm faturalar listelenebilmelidir.
    *  Müşteriye ait faturalar listelenebilmelidi
    * */

    /*
     * 16- arabalara güncel kilometre bilgisi eklenmelidir.
     * kiralamalarda balangıç kilometresi girilmelidir.
     * kiralama dönüşlerinde dönüş kilometresi bilgisi girilmesidir. Bu bilgi arabada da güncellenmelidir.
     * 17- arabaya ait hasarlar girilebilimelidir.(id,CarId,Hasar bilgisi)
     *
     * */


    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationErrors(MethodArgumentNotValidException argumentNotValidException) {
        Map<String, String> handleValidation = new HashMap<String, String>();

        for (FieldError fieldError : argumentNotValidException.getBindingResult().getFieldErrors()) {
            handleValidation.put(fieldError.getField(), fieldError.getCode());
        }
        return new ErrorDataResult<Object>(handleValidation, "Validations Error");

    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleBusinessErrors(BusinessException businessException) {
        return new ErrorDataResult<Object>(businessException.getMessage(), "Business Error");
    }

    ///exception globale taşınacak




}

package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.entities.concrates.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {
    boolean existsByCardNo(String cardNo) throws BusinessException;
}

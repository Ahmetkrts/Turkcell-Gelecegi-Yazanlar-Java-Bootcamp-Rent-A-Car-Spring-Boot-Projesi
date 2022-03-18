package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.exception.BusinessException;

public interface CustomerService {

    void chechIfCustomerIdExist(int customerId) throws BusinessException;
}

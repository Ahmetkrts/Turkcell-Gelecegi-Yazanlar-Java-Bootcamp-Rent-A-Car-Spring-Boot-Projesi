package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.exception.BusinessException;

public interface CustomerService {

    void checkIfCustomerIdExist(int customerId) throws BusinessException;
}

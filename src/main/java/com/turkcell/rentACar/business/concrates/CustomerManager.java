package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager implements CustomerService {
    private CustomerDao customerDao;

    @Autowired
    public CustomerManager(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void chechIfCustomerIdExist(int customerId) throws BusinessException {
        if (!this.customerDao.existsById(customerId)) {
            throw new BusinessException(customerId + " No'lu Müşteri Bulunamadı...");
        }
    }
}

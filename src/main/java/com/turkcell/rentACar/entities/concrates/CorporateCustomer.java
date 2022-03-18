package com.turkcell.rentACar.entities.concrates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer {


    @Column(name = "tax_number", length = 10)
    private String taxNumber;
    @Column(name = "company_name")
    private String companyName;

}

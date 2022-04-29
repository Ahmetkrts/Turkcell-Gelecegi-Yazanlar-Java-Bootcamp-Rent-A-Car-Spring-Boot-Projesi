package com.turkcell.rentACar.entities.concrates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_card_id")
    private int creditCardId;

    @Column(name = "holder_name")
    private String holderName;
    @Column(name = "card_no")
    private String cardNo;
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    @Column(name = "security_code")
    private String securityCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}

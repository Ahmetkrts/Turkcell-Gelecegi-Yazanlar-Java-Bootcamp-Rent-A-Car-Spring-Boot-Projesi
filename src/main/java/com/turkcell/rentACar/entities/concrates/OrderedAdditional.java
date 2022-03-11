package com.turkcell.rentACar.entities.concrates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_additionals")
public class OrderedAdditional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_additional_id")
    private int orderedAdditionalId;

    @ManyToOne
    @JoinColumn(name = "add_service_id")
    private Additional additional;

    @ManyToOne
    @JoinColumn(name = "rent_car_id")
    private RentCar rentCar;


}

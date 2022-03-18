package com.turkcell.rentACar.entities.concrates;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carDamages")
public class CarDamage {
    //(id,CarId,Hasar bilgisi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_damage_id")
    private int carDamageId;

    @Column(name = "damage_info")
    private String damageInfo;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}

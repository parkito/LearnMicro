package com.parkito.learnmicro.monolith.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Artem Karnov @date 11/13/2017.
 * artem.karnov@t-systems.com
 */
@Entity
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "lg_rods_vehicle")
public class RodsVehicleEntity extends RodsBaseEntity {
    @Tolerate
    public RodsVehicleEntity() {
    }

    @Column(name = "vehicle_uuid", nullable = false, unique = true)
    private String vehicleUuid;

    @Column(name = "vehicle_name", nullable = false)
    private String vehicleName;

    @Column(name = "vehicle_vin", nullable = false)
    private String vehicleVin;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "customer_uuid")
    private String customerUuid;

}

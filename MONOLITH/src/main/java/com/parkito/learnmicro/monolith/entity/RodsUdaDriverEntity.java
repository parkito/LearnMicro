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
 * @author Artem Karnov @date 11/15/2017.
 * artem.karnov@t-systems.com
 */
@Entity
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "lg_rods_company_uda_driver")
public class RodsUdaDriverEntity extends RodsBaseEntity {
    @Tolerate
    public RodsUdaDriverEntity() {
    }

    @Column(name = "company_uuid", nullable = false, unique = true)
    private String companyUuid;

    @Column(name = "uda_driver_uuid")
    private String udaDriverUuid;
}

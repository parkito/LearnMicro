package com.parkito.learnmicro.monolith.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@Table(name = "lg_rods_customer")
public class RodsCustomerEntity extends RodsBaseEntity {
    @Tolerate
    public RodsCustomerEntity() {
    }

    @Column(name = "customer_uuid", nullable = false, unique = true)
    private String customerUuid;

    @Column(name = "company_id", nullable = false)
    private String companyId;

    @Column(name = "fatigue_driving_pre_warn_time")
    private String fatigueDrivingPreWarnTime;
}

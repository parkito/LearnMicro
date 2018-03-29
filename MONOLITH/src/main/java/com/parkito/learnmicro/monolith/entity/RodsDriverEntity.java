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
@Table(name = "lg_rods_driver")
public class RodsDriverEntity extends RodsBaseEntity {
    @Tolerate
    public RodsDriverEntity() {
    }

    @Column(name = "driver_id", nullable = false)
    private String driverId;

    @Column(name = "driver_uuid", nullable = false, unique = true)
    private String driverUuid;

    @Column(name = "customer_uuid", nullable = false)
    private String customerUuid;

    @Column(name = "driver_username", nullable = false)
    private String driverUsername;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "license_issuing_state")
    private String licenseIssuingState;

    @Column(name = "password", nullable = false)
    private byte[] password;

    @Column(name = "login_method")
    private String loginMethod;

    @Column(name = "us_hos_schedule")
    private String usHosSchedule;

    @Column(name = "ca_hos_schedule")
    private String caHosSchedule;

    @Column(name = "is_allow_34hour_restart")
    private Boolean isAllow34HourRestart;

    @Column(name = "period_start_time_minutes")
    private Integer periodStartTimeMinutes;

    @Column(name = "home_time_zone")
    private String homeTimeZone;

    @Column(name = "exemption_driver_configuration")
    private String exemptionDriverConfiguration;

    @Column(name = "exempt_explanation")
    private String exemptExplanation;

    @Column(name = "special_driving_category")
    private String specialDrivingCategory;

    @Column(name = "regulation")
    private String regulation;

    @Column(name = "personal_use_distance_limit")
    private Integer personalUseDistanceLimit;

    @Column(name = "allow30_exemption_break")
    private Boolean allow30ExemptionBreak;

    @Column(name = "special_permits")
    private String specialPermits;

    @Column(name = "eld_language")
    private String eldLanguage;

    @Column(name = "eld_illumination")
    private Integer eldIllumination;

    @Column(name = "eld_sound")
    private boolean eldSound;
}

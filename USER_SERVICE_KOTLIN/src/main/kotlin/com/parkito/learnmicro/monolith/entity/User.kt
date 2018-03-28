package com.parkito.learnmicro.monolith.entity;

import lombok.Builder
import lombok.Data
import javax.persistence.*

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Entity
@Builder
@Data
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val userId: Long,

        @Column(nullable = false, unique = true)
        val email: String,

        @Column(nullable = false)
        val firstName: String,

        @Column(nullable = false)
        var lastName: String
)
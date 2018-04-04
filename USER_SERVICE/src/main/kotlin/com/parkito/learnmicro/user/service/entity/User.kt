package com.parkito.learnmicro.user.service.entity;

import javax.persistence.*

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val userId: Long = -1,

        @Column(nullable = false, unique = true)
        val email: String = "",

        @Column(nullable = false)
        val firstName: String = "",

        @Column(nullable = false)
        val lastName: String = ""
)
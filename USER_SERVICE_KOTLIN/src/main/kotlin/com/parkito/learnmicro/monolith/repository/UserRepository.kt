package com.parkito.learnmicro.monolith.repository;

import com.parkito.learnmicro.monolith.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User
    fun deleteByEmail(email: String): User;
}

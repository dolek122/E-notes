package com.arekproject.studiaprojekt.repository;

import com.arekproject.studiaprojekt.entity.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    public UserDtls findByEmail(String email);

    boolean existsByEmail(String email);
}

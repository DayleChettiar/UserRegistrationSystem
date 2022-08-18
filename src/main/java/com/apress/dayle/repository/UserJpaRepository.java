package com.apress.dayle.repository;

import com.apress.dayle.dto.UsersDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UsersDTO, Long> {

    UsersDTO findById(Long id);

    UsersDTO findByName(String name);
}

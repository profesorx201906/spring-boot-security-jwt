package com.security.springbootsecurityjwt.repository;

import com.security.springbootsecurityjwt.model.RoleEntity;
import com.security.springbootsecurityjwt.model.RoleEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
}

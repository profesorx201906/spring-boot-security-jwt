package com.security.springbootsecurityjwt.service;

import com.security.springbootsecurityjwt.model.RoleEntity;
import com.security.springbootsecurityjwt.model.RoleEnum;

import java.util.Optional;

public interface RolService {

    public Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
}

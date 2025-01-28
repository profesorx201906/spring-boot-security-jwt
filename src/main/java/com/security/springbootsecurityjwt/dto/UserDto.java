package com.security.springbootsecurityjwt.dto;

import com.security.springbootsecurityjwt.model.Rol;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private String username;
    private String password;
    private String email;
    private Set<Rol> roles;

}

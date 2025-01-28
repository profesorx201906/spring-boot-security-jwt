package com.security.springbootsecurityjwt.dto;

import java.util.List;

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
public class RegisterDto {

    private String username;
    private String password;
    private String email;
   private List<RoleDto> role;

}

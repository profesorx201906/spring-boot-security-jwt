package com.security.springbootsecurityjwt.dto;


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
public class RoleDto {

  private String role;
  private Set<String> permissionList;

}

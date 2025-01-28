package com.security.springbootsecurityjwt.service.imple;

import com.security.springbootsecurityjwt.dto.JwtResponseDto;
import com.security.springbootsecurityjwt.dto.LoginDto;
import com.security.springbootsecurityjwt.dto.RegisterDto;
import com.security.springbootsecurityjwt.dto.RoleDto;
import com.security.springbootsecurityjwt.dto.UserDto;
import com.security.springbootsecurityjwt.exceptions.ConflictException;
import com.security.springbootsecurityjwt.exceptions.JwtAuthenticationException;
import com.security.springbootsecurityjwt.exceptions.NotFoundException;
import com.security.springbootsecurityjwt.model.RoleEntity;
import com.security.springbootsecurityjwt.model.RoleEnum;
import com.security.springbootsecurityjwt.model.UserEntity;
import com.security.springbootsecurityjwt.repository.UserRepository;
import com.security.springbootsecurityjwt.security.JwtGenerator;
import com.security.springbootsecurityjwt.service.RolService;
import com.security.springbootsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImple implements UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RolService rolService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtGenerator jwtGenerator;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDto register(RegisterDto registerDto) {
    if (userRepository.existsByEmail(registerDto.getEmail())) {
      throw new ConflictException("El usuario " + registerDto.getEmail() + " no existe.");
    }
    UserEntity user = new UserEntity();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setEmail(registerDto.getEmail());

    List<RoleDto> strRoles = registerDto.getRole();
    Set<RoleEntity> roles = new HashSet<>();

    if (strRoles == null) {
      RoleEntity userRole = rolService.findByRoleEnum(RoleEnum.USER)
          .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role.getRole()) {
          case "admin":
            RoleEntity adminRole = rolService.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            roles.add(adminRole);
            break;
          case "inv":
            RoleEntity invRole = rolService.findByRoleEnum(RoleEnum.INVITED)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            roles.add(invRole);
            break;
          case "dev":
            RoleEntity devRole = rolService.findByRoleEnum(RoleEnum.DEVELOPER)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            roles.add(devRole);
            break;
          default:
            RoleEntity userRole = rolService.findByRoleEnum(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            roles.add(userRole);
        }
      });
    }

    // RoleEntity rol = rolService.findByRoleEnum("USER").orElseThrow(()-> new
    // NotFoundException("Rol no encontrado!"));
    // Set<RoleEntity> roles = new HashSet<>();
    // roles.add(rol);
    user.setRoles(roles);
    userRepository.save(user);

    UserDto userDto = new UserDto();
    userDto.setUsername(user.getUsername());
    userDto.setPassword(user.getPassword());
    userDto.setEmail(user.getEmail());
    userDto.setRoles(user.getRoles());
    return userDto;
  }

  @Override
  public JwtResponseDto login(LoginDto loginDto) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginDto.getEmail(),
              loginDto.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = jwtGenerator.generateToken(authentication);
      return new JwtResponseDto(token);
    } catch (AuthenticationException e) {
      throw new JwtAuthenticationException("Credenciales inválidas");
    }
  }

  @Override
  public UserDto getLoguedUser(HttpHeaders headers) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String email = ((User) authentication.getPrincipal()).getUsername();

    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    UserDto userDto = new UserDto();
    userDto.setEmail(user.getEmail());
    userDto.setUsername(user.getUsername());
    userDto.setRoles(user.getRoles());
    return userDto;
  }

}
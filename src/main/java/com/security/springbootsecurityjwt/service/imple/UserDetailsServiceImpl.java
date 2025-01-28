package com.security.springbootsecurityjwt.service.imple;

import com.security.springbootsecurityjwt.model.UserEntity;
import com.security.springbootsecurityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailService")
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("El usuario " + email + " no existe."));
    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    userEntity.getRoles()
        .forEach(role -> authorityList.add(new SimpleGrantedAuthority(role.getRoleEnum().name())));

    userEntity.getRoles().stream()
        .flatMap(role -> role.getPermissionList().stream())
        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

    return new User(userEntity.getEmail(),
        userEntity.getPassword(),
        userEntity.isEnabled(),
        userEntity.isAccountNoExpired(),
        userEntity.isCredentialNoExpired(),
        userEntity.isAccountNoLocked(),
        authorityList);
  }
}

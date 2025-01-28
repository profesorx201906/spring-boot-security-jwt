package com.security.springbootsecurityjwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.security.springbootsecurityjwt.model.PermissionEntity;
import com.security.springbootsecurityjwt.model.RoleEntity;
import com.security.springbootsecurityjwt.model.RoleEnum;
import com.security.springbootsecurityjwt.model.UserEntity;
import com.security.springbootsecurityjwt.repository.UserRepository;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityJwtApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
  }

  @Bean
  CommandLineRunner init(UserRepository userRepository) {
    return args -> {
      /* Create PERMISSIONS */
      PermissionEntity createPermission = PermissionEntity.builder()
          .name("CREATE")
          .build();

      PermissionEntity readPermission = PermissionEntity.builder()
          .name("READ")
          .build();

      PermissionEntity updatePermission = PermissionEntity.builder()
          .name("UPDATE")
          .build();

      PermissionEntity deletePermission = PermissionEntity.builder()
          .name("DELETE")
          .build();

      PermissionEntity refactorPermission = PermissionEntity.builder()
          .name("REFACTOR")
          .build();

      /* Create ROLES */
      RoleEntity roleAdmin = RoleEntity.builder()
          .roleEnum(RoleEnum.ADMIN)
          .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
          .build();

      RoleEntity roleUser = RoleEntity.builder()
          .roleEnum(RoleEnum.USER)
          .permissionList(Set.of(createPermission, readPermission))
          .build();

      RoleEntity roleInvited = RoleEntity.builder()
          .roleEnum(RoleEnum.INVITED)
          .permissionList(Set.of(readPermission))
          .build();

      RoleEntity roleDeveloper = RoleEntity.builder()
          .roleEnum(RoleEnum.DEVELOPER)
          .permissionList(
              Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
          .build();

      /* CREATE USERS */
      UserEntity userSantiago = UserEntity.builder()
          .username("santiago")
          .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
          .isEnabled(true)
          .accountNoExpired(true)
          .accountNoLocked(true)
          .credentialNoExpired(true)
          .email("santiago@unir.net")
          .roles(Set.of(roleAdmin))
          .build();

      UserEntity userDaniel = UserEntity.builder()
          .username("daniel")
          .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
          .isEnabled(true)
          .accountNoExpired(true)
          .accountNoLocked(true)
          .credentialNoExpired(true)
          .email("daniel@unir.net")
          .roles(Set.of(roleUser))
          .build();

      UserEntity userAndrea = UserEntity.builder()
          .username("andrea")
          .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
          .isEnabled(true)
          .accountNoExpired(true)
          .accountNoLocked(true)
          .credentialNoExpired(true)
          .email("andrea@unir.net")
          .roles(Set.of(roleInvited))
          .build();

      UserEntity userAnyi = UserEntity.builder()
          .username("anyi")
          .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
          .isEnabled(true)
          .accountNoExpired(true)
          .accountNoLocked(true)
          .credentialNoExpired(true)
          .email("anyi@unir.net")
          .roles(Set.of(roleDeveloper))
          .build();

      userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
    };
  }
}

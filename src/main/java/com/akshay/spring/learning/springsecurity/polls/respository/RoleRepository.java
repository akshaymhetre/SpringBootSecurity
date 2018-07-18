package com.akshay.spring.learning.springsecurity.polls.respository;

import com.akshay.spring.learning.springsecurity.polls.model.Role;
import com.akshay.spring.learning.springsecurity.polls.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}

package com.akshay.spring.learning.SpringSecurityBasics.polls.respository;

import com.akshay.spring.learning.SpringSecurityBasics.polls.model.Role;
import com.akshay.spring.learning.SpringSecurityBasics.polls.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}

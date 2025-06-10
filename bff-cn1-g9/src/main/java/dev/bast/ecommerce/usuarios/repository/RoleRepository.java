package dev.bast.ecommerce.usuarios.repository;

import dev.bast.ecommerce.usuarios.model.Role;
import dev.bast.ecommerce.usuarios.model.Role.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(ERole name);
}
package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Set<Roles> findAllByIdIn(Set<Long> ids);
    Optional<Roles> findByName(String name);
}
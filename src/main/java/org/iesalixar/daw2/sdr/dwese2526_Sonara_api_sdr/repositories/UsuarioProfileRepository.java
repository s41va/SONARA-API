package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.UsuarioProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioProfileRepository extends JpaRepository<UsuarioProfile, Long> {
    Optional<UsuarioProfile> findByUsuario_Id(Long usuarioId);
    Optional<UsuarioProfile> findByUsuario_Email(String email);
    boolean existsByUsuario_Id(Long usuarioId);

}
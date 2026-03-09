package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    @EntityGraph(attributePaths = "roles")
    Optional<Usuario> findByEmail(String email);

    @Override
    @EntityGraph(attributePaths = "roles")
    Page<Usuario> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "roles")
    List<Usuario> findAll();

    Optional<Usuario> findByEmailIgnoreCase(String email);
}
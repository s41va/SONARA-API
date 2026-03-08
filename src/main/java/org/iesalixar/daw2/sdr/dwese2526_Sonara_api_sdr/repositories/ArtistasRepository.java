package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Artista;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ArtistasRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNombre(String nombre);
    boolean existsByNombre(String name) ;
    boolean existsByNombreAndIdNot(String name, Long id);
    List<Artista> findAll();




}

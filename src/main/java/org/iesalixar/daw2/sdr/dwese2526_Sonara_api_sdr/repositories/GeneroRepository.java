package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    boolean existsByNombre(String name) ;
    boolean existsByNombreAndIdNot(String name, Long id);
    List<Genero> findAll();
}

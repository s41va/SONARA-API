package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.*;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GeneroService {

    Page<GenerosDTO> list(Pageable pageable);
    GenerosUpdateDTO getForEdit(Long id);
    void create(GenerosCreateDTO dto);
    void update(GenerosUpdateDTO dto);
    void delete(Long id);
    GenerosDetailDTO getDetail(Long id);
    List<Genero> findAllGeneros();
}

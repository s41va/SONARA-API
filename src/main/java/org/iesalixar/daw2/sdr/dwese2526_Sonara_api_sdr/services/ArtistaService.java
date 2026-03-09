package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasCreateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDetailDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasUpdateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArtistaService {

    Page<ArtistasDTO> list(Pageable pageable);
    ArtistasUpdateDTO getForEdit(Long id);
    ArtistasDTO create(ArtistasCreateDTO dto);
    ArtistasDTO update(ArtistasUpdateDTO dto);
    void delete(Long id);
    ArtistasDetailDTO getDetail(Long id);
    List<Genero> findAllGeneros();
    List<ArtistasDTO> listAll(Sort name);

}

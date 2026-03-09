package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.*;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;
import java.util.List;

public interface UsuarioService {
    Page<UsuarioDTO> list(Pageable pageable);
    UsuarioUpdateDTO getForEdit(Long id);
    UsuarioDTO create(UsuarioCreateDTO dto);
    UsuarioDTO update(UsuarioUpdateDTO dto, Set<Roles> roles);
    void delete(Long id);
    UsuarioDetailDTO getDetail(Long id);
    List<UsuarioDTO> listAll(Sort name);

}

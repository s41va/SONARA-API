package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioProfileDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioProfilePatchDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioProfileService {
    UsuarioProfileDTO getFormByEmail(String email);
    void updateProfile(String email, UsuarioProfilePatchDTO patchDTO, MultipartFile profileImageFile);
}
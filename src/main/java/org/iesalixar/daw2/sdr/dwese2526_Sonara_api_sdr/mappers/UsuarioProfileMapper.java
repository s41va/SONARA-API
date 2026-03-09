package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.mappers;


import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioProfileDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Usuario;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.UsuarioProfile;

public class UsuarioProfileMapper {
    public static UsuarioProfileDTO toFormDto(Usuario user, UsuarioProfile profile){
        if (user == null) {
            return null;
        }
        UsuarioProfileDTO dto = new UsuarioProfileDTO();
        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());

        if (profile != null){
            dto.setFirstName(profile.getFirstName());
            dto.setLastName(profile.getLastName());
            dto.setPhoneNumber(profile.getPhoneNumber());
            dto.setProfileImage(profile.getProfileImage());
            dto.setBio(profile.getBio());
            dto.setLocale(profile.getLocale());
        }
        return dto;
    }
    public static UsuarioProfile toNewEntity(UsuarioProfileDTO dto, Usuario user) {
        if (dto == null || user == null) {
            return null;
        }
        UsuarioProfile profile = new UsuarioProfile();
        // Relacion 1:1 con shared primary key
        profile.setUsuario(user);
        // NO setear id manualmente: @MapsId se encarga
        // profile.setId(user.getId());
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setProfileImage(dto.getProfileImage());
        profile.setBio(dto.getBio());
        profile.setLocale(dto.getLocale());

        return profile;
    }

    public static void copyToExistingEntity(UsuarioProfileDTO dto, UsuarioProfile profile) {
        if (dto == null || profile == null) {
            return;
        }
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setProfileImage(dto.getProfileImage());
        profile.setBio(dto.getBio());
        profile.setLocale(dto.getLocale());
    }


}
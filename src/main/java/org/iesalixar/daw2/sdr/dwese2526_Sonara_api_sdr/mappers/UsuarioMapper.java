package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.mappers;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioCreateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioDetailDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioUpdateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Roles;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Usuario;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMapper {

    // Entity -> DTO (listado/tabla básico)
    public static UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setFechaRegistro(entity.getFechaRegistro());

        if (entity.getRoles() != null) {
            dto.setRoles(
                    entity.getRoles()
                            .stream()
                            .map(Roles::getDisplayName)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setRoles(Set.of());
        }

        return dto;
    }

    public static List<UsuarioDTO> toDTOList(List<Usuario> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(UsuarioMapper::toDTO).toList();
    }

    // Entity -> DTO detalle completo
    public static UsuarioDetailDTO toDetailDTO(Usuario entity) {
        if (entity == null) return null;
        UsuarioDetailDTO dto = new UsuarioDetailDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setFechaRegistro(entity.getFechaRegistro());

        if (entity.getRoles() != null) {
            dto.setRoles(
                    entity.getRoles()
                            .stream()
                            .map(Roles::getDisplayName)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setRoles(Set.of());
        }

        return dto;
    }

    // Entity -> DTO para edición
    public static UsuarioUpdateDTO toUpdateDTO(Usuario entity) {
        if (entity == null) return null;
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setFechaRegistro(entity.getFechaRegistro());
        dto.setFechaNacimiento(entity.getFechaNacimiento());

        if (entity.getRoles() != null) {
            dto.setRolesIds(
                    entity.getRoles()
                            .stream()
                            .map(Roles::getId)
                            .collect(Collectors.toSet())
            );
        }
        return dto;
    }

    // DTO (Create) -> Entity
    public static Usuario toEntity(UsuarioCreateDTO dto) {
        if (dto == null) return null;
        Usuario e = new Usuario();
        e.setNombre(dto.getNombre());
        e.setEmail(dto.getEmail());
        e.setContrasenaHash(dto.getContrasenaHash());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setFechaRegistro(dto.getFechaRegistro());
        return e;
    }

    // DTO (Update) -> Entity
    public static Usuario toEntity(UsuarioUpdateDTO dto) {
        if (dto == null) return null;
        Usuario e = new Usuario();
        e.setId(dto.getId());
        e.setNombre(dto.getNombre());
        e.setEmail(dto.getEmail());
        e.setContrasenaHash(dto.getContrasenaHash());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setFechaRegistro(dto.getFechaRegistro());
        return e;
    }

    // DTO (Básico) -> Entity
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario e = new Usuario();
        e.setId(dto.getId());
        e.setNombre(dto.getNombre());
        e.setEmail(dto.getEmail());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setFechaRegistro(dto.getFechaRegistro());
        return e;
    }

    // Actualizar entidad existente
    public static void copyToExistingEntity(UsuarioUpdateDTO dto, Usuario entity, Set<Roles> roles) {
        if (dto == null || entity == null) return;
        entity.setNombre(dto.getNombre());
        entity.setEmail(dto.getEmail());

        if (dto.getContrasenaHash() != null && !dto.getContrasenaHash().isBlank()) {
            entity.setContrasenaHash(dto.getContrasenaHash());
        }

        entity.setFechaNacimiento(dto.getFechaNacimiento());

        if (dto.getFechaRegistro() != null) {
            entity.setFechaRegistro(dto.getFechaRegistro());
        }

        entity.setRoles(roles);
    }
}
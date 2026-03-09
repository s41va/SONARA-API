package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String contrasenaHash;
    private LocalDate fechaNacimiento;
    private Set<String> generosFavoritos;
    private String localidadNombre;
    private LocalDateTime fechaRegistro;
    // Roles asociados al usuario (nombres técnicos: ROLE_ADMIN, ROLE_USER, etc.)
    private Set<String> roles;
}
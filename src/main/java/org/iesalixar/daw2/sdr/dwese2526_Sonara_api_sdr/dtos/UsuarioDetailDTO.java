package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

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
public class UsuarioDetailDTO {
    private Long id;
    private String nombre;
    private String email;
    private LocalDate fechaNacimiento;
    private Set<String> generosFavoritos;
    private LocalDateTime fechaRegistro;
    // ────────────────────────────────────────────
    // Roles del usuario (nombres de rol)
    // ────────────────────────────────────────────
    private Set<String> roles;
}

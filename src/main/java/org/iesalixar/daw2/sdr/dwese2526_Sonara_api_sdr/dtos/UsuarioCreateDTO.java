package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {
    private Long id;

    @NotBlank(message = "{msg.usuario.nombre.notEmpty}")
    @Size(max = 100, message = "{msg.usuario.nombre.size}")
    private String nombre;

    @NotBlank(message = "{msg.usuario.email.notEmpty}")
    @Email(message = "{msg.usuario.email.valid}")
    private String email;

    @NotBlank(message = "{msg.usuario.contrasena.notEmpty}")
    @Size(min = 6, max = 100, message = "{msg.usuario.contrasena.size}")
    private String contrasenaHash;

    @NotNull(message = "{msg.usuario.fechaNacimiento.notNull}")
    @Past(message = "{msg.usuario.fechaNacimiento.past}")
    private LocalDate fechaNacimiento;

    private Set<String> generosFavoritos;

    private LocalDateTime fechaRegistro;

    // ─────────────────────────────────────
    // Roles seleccionados (ids de Role) - OBLIGATORIOS
    // ─────────────────────────────────────
    @NotEmpty(message = "{msg.user.roles.notempty}")
    private Set<Long> rolesIds = new HashSet<>();

}
package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerosUpdateDTO {

    private Long id;
    @NotEmpty(message = "{msg.genero.nombre.size}")
    @Size(max = 50)
    private String nombre;
    @NotEmpty(message = "{msg.genero.descripcion.size}")
    @Size(max = 400, message = "{msg.genero.descripcion.size}")
    private String descripcion;
}

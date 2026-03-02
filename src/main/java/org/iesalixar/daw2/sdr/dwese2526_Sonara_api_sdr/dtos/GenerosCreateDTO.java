package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerosCreateDTO {


    private Long id;
    @NotNull(message = "{msg.genero.nombre.notNull}")
    private String nombre;
    @NotNull(message = "{msg.genero.descripcion.notNull}")
    private String descripcion;

}

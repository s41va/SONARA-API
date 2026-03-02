package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistasCreateDTO {

    private Long id;

    @NotNull(message = "{msg.artista.nombre.notNull}")
    private String nombre;
    @NotNull(message = "{msg.artista.pais.notNull}")
    private String pais;
    @NotNull(message = "{msg.artista.descripcion.notNull}")
    private String descripcion;
    @NotNull(message = "{msg.artista.generoId.notNull}")
    private Long generoId;
}

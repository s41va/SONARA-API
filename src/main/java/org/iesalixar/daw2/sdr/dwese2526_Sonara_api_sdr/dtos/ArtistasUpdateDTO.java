package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistasUpdateDTO {


    private Long id;

    @NotBlank(message = "{msg.artista.nombre.notBlank}")
    @Size(max = 50,  message = "{msg.artista.nombre.size}")
    private String nombre;

    @NotBlank(message = "{msg.artista.pais.notBlank}")
    @Size(max = 100, message = "{msg.artista.pais.size}")
    private String pais;


    @Size(max = 400, message = "{msg.artista.descripcion.size}")
    private String descripcion;

    private Long generoId;

}

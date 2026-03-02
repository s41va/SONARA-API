package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistasDTO {


    private Long id;

    @NotEmpty(message = "{msg.artista.nombre.notEmpty}")
    @Size(max = 50)
    private String nombre;

    @NotEmpty(message = "{msg.artista.pais.notEmpty}")
    @Size(max = 100)
    private String pais;


    @Size(max = 400)
    private String descripcion;


   private GeneroSimpleDTO genero;

}

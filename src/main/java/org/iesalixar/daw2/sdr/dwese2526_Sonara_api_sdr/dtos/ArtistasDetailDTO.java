package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistasDetailDTO {

    private Long id;
    private String nombre;
    private String pais;
    private String descripcion;
    private GeneroSimpleDTO genero;

}

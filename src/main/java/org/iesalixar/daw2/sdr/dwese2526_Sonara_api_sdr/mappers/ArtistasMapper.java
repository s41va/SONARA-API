package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.mappers;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasCreateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDetailDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasUpdateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.GeneroSimpleDTO; // Importar el nuevo DTO
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Artista;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero; // Importar la entidad Genero

import java.util.List;
import java.util.stream.Collectors;

public class ArtistasMapper {

    // ==========================================================
    // MÉTODOS AUXILIARES para mapear Género
    // ==========================================================

    /**
     * Convierte una entidad Genero a GeneroSimpleDTO.
     */
    public static GeneroSimpleDTO toSimpleDTO(Genero entity){
        if (entity == null) return null;
        GeneroSimpleDTO dto = new GeneroSimpleDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }


    // ==========================================================
    // Mapeo Artista -> ArtistasDTO (vista simple/lista)
    // ==========================================================

    /**
     * Convierte una entidad {@link Artista} a {@link ArtistasDTO} (vista simple).
     * Incluye el género del artista.
     */
    public static ArtistasDTO toDTO(Artista entity){
        if (entity == null) return null;
        ArtistasDTO dto = new ArtistasDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPais(entity.getPais());
        dto.setDescripcion(entity.getDescripcion());

        // --- NUEVO: Mapear el Género ---
        // Accede a la propiedad 'genero' de la entidad Artista
        // y la convierte al GeneroSimpleDTO.
        dto.setGenero(toSimpleDTO(entity.getGenero()));
        // ---------------------------------

        return dto;
    }

    /**
     * Convierte una lista de entidades {@link Artista} a {@link ArtistasDTO}.
     */
    public static List<ArtistasDTO> toDTOList(List<Artista> entities){
        if (entities == null) return List.of();
        return entities.stream().map(ArtistasMapper::toDTO).collect(Collectors.toList());
    }

    // ==========================================================
    // Mapeo Artista -> ArtistasDetailDTO (detalle)
    // ==========================================================

    /**
     * Convierte una {@link Artista} a {@link ArtistasDetailDTO}, mapeando todos sus campos.
     * Incluye el género del artista.
     */
    public static ArtistasDetailDTO toDetailDTO(Artista entity) {
        if (entity == null) return null;

        ArtistasDetailDTO dto = new ArtistasDetailDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPais(entity.getPais());
        dto.setDescripcion(entity.getDescripcion());

        // --- NUEVO: Mapear el Género ---
        dto.setGenero(toSimpleDTO(entity.getGenero()));
        // ---------------------------------

        return dto;
    }

    // ==========================================================
    // Mapeo DTOs de Creación y Actualización (Sin cambios relevantes)
    // ==========================================================

    /**
     * Convierte un DTO de creación {@link ArtistasCreateDTO} a la entidad {@link Artista}.
     */
    public static Artista toEntity(ArtistasCreateDTO dto){
        if (dto == null) return null;
        Artista a = new Artista();
        a.setNombre(dto.getNombre());
        a.setPais(dto.getPais());
        a.setDescripcion(dto.getDescripcion());
        // Nota: El género se establecería en el servicio/controlador antes de guardar,
        // ya que solo tenemos el ID del género en el DTO de creación/actualización.
        return a;
    }

    /**
     * Copia las propiedades de un DTO de actualización {@link ArtistasUpdateDTO} a una entidad {@link Artista} **existente**.
     */
    public static void copyToExistingEntity(ArtistasUpdateDTO dto, Artista entity){
        if (dto == null || entity == null) return;

        entity.setNombre(dto.getNombre());
        entity.setPais(dto.getPais());
        entity.setDescripcion(dto.getDescripcion());


        // Nota: Aquí se actualizaría el género, pero eso requiere una búsqueda en el repositorio,
        // lo cual se hace en la capa de Servicio, no en el Mapper.
    }

    // ... (otros métodos como toEntity(ArtistasUpdateDTO) y toUpdateDTO(Artista) se dejan sin cambios,
    // ya que el mapeo de Artista a DTO de actualización no necesita el objeto Genero completo) ...

    public static Artista toEntity(ArtistasUpdateDTO dto){
        if (dto == null) return null;
        Artista a = new Artista();

        a.setNombre(dto.getNombre());
        a.setPais(dto.getPais());
        a.setDescripcion(dto.getDescripcion());


        return a;
    }


    /**
     * Convierte una entidad {@link Artista} a {@link ArtistasUpdateDTO}.
     */
    public static ArtistasUpdateDTO toUpdateDTO(Artista entity) {
        if (entity == null) return null;
        ArtistasUpdateDTO dto = new ArtistasUpdateDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPais(entity.getPais());
        dto.setDescripcion(entity.getDescripcion());

        if (entity.getGenero() != null) {
            dto.setGeneroId(entity.getGenero().getId());
        }


        return dto;
    }
}
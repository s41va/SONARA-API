package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasCreateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDetailDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasUpdateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Artista;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Genero;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.exceptions.DuplicateResourceException;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.mappers.ArtistasMapper;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories.ArtistasRepository;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories.GeneroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ArtistasServiceImpl implements ArtistaService{

    @Autowired
    private ArtistasRepository artistasRepository;

    @Autowired
    private GeneroRepository generoRepository;



    @Override
    public Page<ArtistasDTO> list(Pageable pageable) {
        return artistasRepository.findAll(pageable).map(ArtistasMapper::toDTO);
    }

    @Override
    public ArtistasUpdateDTO getForEdit(Long id) {
        Artista artista = artistasRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("user", "id", id));
        return ArtistasMapper.toUpdateDTO(artista);
    }

    @Override
    public void create(ArtistasCreateDTO dto) {
        if (artistasRepository.existsByNombre(dto.getNombre())){
            throw new DuplicateResourceException("artista", "name", dto.getNombre());
        }
        Artista artista = ArtistasMapper.toEntity(dto);
        artistasRepository.save(artista);
    }

    @Override
    public void update(ArtistasUpdateDTO dto) {
        if (artistasRepository.existsByNombreAndIdNot(dto.getNombre(), dto.getId())){
            throw new DuplicateResourceException("artista", "name", dto.getNombre());
        }
        Artista artista = artistasRepository.findById(dto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("user", "id", dto.getId()));


        ArtistasMapper.copyToExistingEntity(dto,artista);
        artistasRepository.save(artista);
    }

    @Override
    public void delete(Long id) {
        if (!artistasRepository.existsById(id)){
            throw new ResourceNotFoundException("artista", "id", id);
        }
        artistasRepository.deleteById(id);
    }

    @Override
    public ArtistasDetailDTO getDetail(Long id) {
        Artista artista = artistasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("artista", "id", id));
        return ArtistasMapper.toDetailDTO(artista);
    }

    @Override
    public List<Genero> findAllGeneros() {
        return generoRepository.findAll();
    }
}

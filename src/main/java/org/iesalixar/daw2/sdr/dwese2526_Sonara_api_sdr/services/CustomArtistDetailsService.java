package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services;

import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Artista;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories.ArtistasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomArtistDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomArtistDetailsService.class);

    @Autowired
    private ArtistasRepository artistaRepository;

    /**
     * Carga el Artista por su nombre_artistico o email y le asigna un permiso básico.
     * * @param username El identificador introducido en el login (ej: nombre_artistico).
     * @return UserDetails para Spring Security.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Intentando autenticar al artista: {}", username);

        // Buscamos al artista en su repositorio específico
        // Nota: Ajusta 'findByNombre' según el método de búsqueda que tengas en tu repositorio
        Artista artista = artistaRepository.findByNombre(username)
                .orElseThrow(() -> {
                    log.warn("Artista no encontrado: {}", username);
                    return new UsernameNotFoundException("Artista no encontrado: " + username);
                });

        // Construimos el UserDetails.
        // Aunque no uses roles, Spring Security exige al menos una 'authority' para funcionar.
        return org.springframework.security.core.userdetails.User
                .withUsername(artista.getNombre())
                .password(artista.getPassword()) // Debe ser un hash BCrypt almacenado en la DB
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false) // O artista.isActive() si tienes ese campo
                .build();
    }
}
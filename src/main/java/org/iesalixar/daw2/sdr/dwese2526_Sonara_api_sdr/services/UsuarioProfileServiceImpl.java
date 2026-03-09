package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services;

import jakarta.transaction.Transactional;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioProfileDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioProfilePatchDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.Usuario;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities.UsuarioProfile;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.exceptions.InvalidFileException;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.mappers.UsuarioProfileMapper;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories.UsuarioProfileRepository;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
public class UsuarioProfileServiceImpl implements UsuarioProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioProfileServiceImpl.class);

    private static final long MAX_IMAMGE_SIZE_BYTES = 5 * 1024 * 1024;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private UsuarioProfileRepository userProfileRepository;

    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public UsuarioProfileDTO getFormByEmail(String email) {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        Optional<UsuarioProfile> profileOpt = userProfileRepository.findByUsuario_Id(user.getId());
        UsuarioProfile profile = profileOpt.orElse(null);

        return UsuarioProfileMapper.toFormDto(user, profile);
    }

    @Override
    @Transactional
    public void updateProfile(String email, UsuarioProfilePatchDTO patchDto, MultipartFile profileImageFile) {
        logger.info("Parchando perfil para email={}", email);


        // 1) Fuente de verdad: user por email (NO confiar en userId/email del cliente)
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user", "email", email));


        Long userId = user.getId();


        // 2) Cargar perfil; si no existe, crearlo (PATCH upsert)
        UsuarioProfile profile = userProfileRepository.findByUsuario_Id(userId)
                .orElseGet(() -> {
                    UsuarioProfile p = new UsuarioProfile();
                    p.setUsuario(user);
                    return p;
                });


        // 3) Merge de campos: solo tocar si vienen (no-null)
        if (patchDto.getFirstName() != null) {
            profile.setFirstName(patchDto.getFirstName());
        }
        if (patchDto.getLastName() != null) {
            profile.setLastName(patchDto.getLastName());
        }
        if (patchDto.getPhoneNumber() != null) {
            profile.setPhoneNumber(patchDto.getPhoneNumber());
        }
        if (patchDto.getBio() != null) {
            profile.setBio(patchDto.getBio());
        }
        if (patchDto.getLocale() != null) {
            profile.setLocale(patchDto.getLocale());
        }


        // 4) Imagen: validar + guardar nueva + borrar anterior
        if (profileImageFile != null && !profileImageFile.isEmpty()) {


            // Validaciones semánticas (lanza InvalidFileException si algo no cuadra)
            validateProfileImage(profileImageFile);


            // OJO: la ruta anterior está en la ENTIDAD, no en el DTO de entrada
            String oldImagePath = profile.getProfileImage();


            String newImageWebPath = fileStorageService.saveFile(profileImageFile);
            if (newImageWebPath == null || newImageWebPath.isBlank()) {
                throw new InvalidFileException(
                        "userProfile",
                        "profileImageFile",
                        profileImageFile.getOriginalFilename(),
                        "No se pudo guardar la imagen de perfil."
                );
            }


            profile.setProfileImage(newImageWebPath);


            // Borrar anterior si existía y es distinta (evitas borrarte a ti mismo si reusas nombre)
            if (oldImagePath != null && !oldImagePath.isBlank() && !oldImagePath.equals(newImageWebPath)) {
                fileStorageService.deleteFile(oldImagePath);
            }
        }


        // 5) Persistir (save sirve tanto para nuevo como existente)
        userProfileRepository.save(profile);
    }


    private void validateProfileImage(MultipartFile file) {
        String contentType = file.getContentType();
        // MIME invalido
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidFileException(
                    "userProfile",
                    "profileImageFile",
                    contentType,
                    "Tipo de archivo no permitido"
            );
        }
        // Tamaño excedido
        if (file.getSize() > MAX_IMAMGE_SIZE_BYTES) {
            throw new InvalidFileException(
                    "userProfile",
                    "profileImageFile",
                    file.getSize(),
                    "Archivo demasiado grande (maximo " + MAX_IMAMGE_SIZE_BYTES + " bytes)"
            );
        }
    }
}
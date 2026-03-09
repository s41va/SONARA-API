package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.controllers;


import jakarta.validation.Valid;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioProfilePatchDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.UsuarioProfileDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services.UsuarioProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Locale;

@Validated
@RestController
@RequestMapping("/api/profile")
public class UsuarioProfileController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioProfileController.class);

    @Autowired
    private UsuarioProfileService userProfileService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping
    public ResponseEntity<UsuarioProfileDTO> getMyProfile(Principal principal) {
        String email = principal.getName();
        logger.info("API getMyProfile para {}", email);

        UsuarioProfileDTO dto = userProfileService.getFormByEmail(email);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioProfileDTO> patchMyProfile(
            @ModelAttribute UsuarioProfilePatchDTO patchDTO,
            @RequestParam(value = "profileImageFile", required = false) MultipartFile profileImageFile,
            Principal principal) {

        String email = principal.getName();
        logger.info("API patchMyProfile para {}", email);

        userProfileService.updateProfile(email, patchDTO, profileImageFile);

        UsuarioProfileDTO updated = userProfileService.getFormByEmail(email);
        return ResponseEntity.ok(updated);
    }
}
package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.controllers;


import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasCreateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasDetailDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.dtos.ArtistasUpdateDTO;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.exceptions.DuplicateResourceException;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.services.ArtistaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Locale;

@Controller
@RequestMapping("/api/artistas")
public class ArtistasController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistasController.class);

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping
    public ResponseEntity<Page<ArtistasDTO>> listArtistas(
            @PageableDefault(size = 10, sort = "nombre_artistico", direction = Sort.Direction.ASC) Pageable pageable) {

        logger.info("Listando artistas (REST) page={}, size={}, sort={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        // Si aquí salta una excepción, la convertirá el @RestControllerAdvice a un HTTP status adecuado
        Page<ArtistasDTO> page = artistaService.list(pageable);

        logger.info("Se han cargado {} artistas en la página {}.",
                page.getNumberOfElements(), page.getNumber());

        return ResponseEntity.ok(page);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ArtistasDetailDTO> getArtistaById(@PathVariable Long id){
        logger.info("Mostrando detalle (REST) de la region con ID{}, ", id);

        ArtistasDetailDTO artistasDetailDTO = artistaService.getDetail(id);

        return ResponseEntity.ok(artistasDetailDTO);
    }

    @PostMapping
    public ResponseEntity<ArtistasDTO> createArtista(@Valid @RequestBody ArtistasCreateDTO dto) {

        logger.info("Solicitud REST para crear artista: {}", dto.getNombre());

        // 1) Delegamos la creación al servicio (incluye reglas de negocio y excepciones)
        ArtistasDTO created = artistaService.create(dto);

        // 2) Construimos la cabecera Location con la URL del recurso recién creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        // 3) Respondemos con 201 Created + Location + body con el DTO creado
        return ResponseEntity.created(location).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ArtistasDTO> updateArtista(@PathVariable Long id,
                                                     @Valid @RequestBody ArtistasUpdateDTO artistaDTO) {

        logger.info("Actualizando artista con ID {} (REST)", id);

        // Buena práctica: asegurar consistencia entre path y body
        artistaDTO.setId(id);

        ArtistasDTO updated = artistaService.update(artistaDTO);

        logger.info("Artista con ID {} actualizado con éxito.", id);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')") // Comentado hasta que se habilite la seguridad
    public ResponseEntity<Void> deleteArtista(@PathVariable Long id) {

        logger.info("Eliminando artista (REST) con ID {}", id);

        // 1) Delegamos en el servicio:
        //    - si existe: elimina
        //    - si no existe: lanza ResourceNotFoundException (se convertirá a 404 en el @RestControllerAdvice)
        artistaService.delete(id);

        logger.info("Artista con ID {} eliminado con éxito.", id);

        // 2) En REST, lo habitual en un DELETE correcto es 204 No Content (sin body)
        return ResponseEntity.noContent().build();
    }





    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo artista.");
        model.addAttribute("artista", new ArtistasCreateDTO());
        model.addAttribute("generosDisponibles", artistaService.findAllGeneros());
        return "views/artista/artista-form";
    }

    @PostMapping("/insert")
    public String insertArtista(@Valid @ModelAttribute("artista") ArtistasCreateDTO artistaDTO,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                Locale locale) {

        logger.info("Insertando nuevo artista: {}", artistaDTO.getNombre());

        if (result.hasErrors()) {
            model.addAttribute("generosDisponibles", artistaService.findAllGeneros());
            return "views/artista/artista-form";
        }

        try {
            artistaService.create(artistaDTO);
            String successMessage = messageSource.getMessage("msg.artista-controller.insert.success", null, locale);
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            return "redirect:/artistas";

        } catch (DuplicateResourceException ex) {
            logger.warn("El nombre artístico {} ya existe.", artistaDTO.getNombre());
            String errorMessage = messageSource.getMessage("msg.artista-controller.insert.nameExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/artistas/new";

        } catch (Exception e) {
            logger.error("Error al insertar artista {}: {}", artistaDTO.getNombre(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.artista-controller.insert.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/artistas/new";
        }
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Mostrando formulario de edición ID {}", id);
        try {
            ArtistasUpdateDTO artistaDTO = artistaService.getForEdit(id);
            model.addAttribute("artista", artistaDTO);
            model.addAttribute("generosDisponibles", artistaService.findAllGeneros());
            return "views/artista/artista-form";

        } catch (ResourceNotFoundException ex) {
            String msg = messageSource.getMessage("msg.artista-controller.edit.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/artistas";

        } catch (Exception e) {
            logger.error("Error al obtener artista ID {}: {}", id, e.getMessage());
            String msg = messageSource.getMessage("msg.artista-controller.edit.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/artistas";
        }
    }

    @PostMapping("/update")
    public String updateArtista(@Valid @ModelAttribute("artista") ArtistasUpdateDTO artistaDTO,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                Locale locale) {

        logger.info("Actualizando artista ID {}", artistaDTO.getId());

        if (result.hasErrors()) {
            model.addAttribute("generosDisponibles", artistaService.findAllGeneros());
            return "views/artista/artista-form";
        }

        try {
            artistaService.update(artistaDTO);
            String successMessage = messageSource.getMessage("msg.artista-controller.update.success", null, locale);
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            return "redirect:/artistas";

        } catch (DuplicateResourceException ex) {
            String errorMessage = messageSource.getMessage("msg.artista-controller.update.artistaExists", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/artistas/edit?id=" + artistaDTO.getId();

        } catch (ResourceNotFoundException ex) {
            String msg = messageSource.getMessage("msg.artista-controller.edit.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/artistas";

        } catch (Exception e) {
            logger.error("Error al actualizar artista ID {}: {}", artistaDTO.getId(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.artista-controller.update.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/artistas/edit?id=" + artistaDTO.getId();
        }
    }

    @PostMapping("/delete")
    public String deleteArtista(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Eliminando artista ID {}", id);
        try {
            artistaService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Artista eliminado con éxito.");
            return "redirect:/artistas";

        } catch (ResourceNotFoundException ex) {
            String msg = messageSource.getMessage("msg.artista-controller.edit.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/artistas";

        } catch (Exception e) {
            logger.error("Error al eliminar artista ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar el artista.");
            return "redirect:/artistas";
        }
    }

    @GetMapping("/detail")
    public String showDetail(@RequestParam("id") Long id,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             Locale locale) {
        try {
            ArtistasDetailDTO detailDTO = artistaService.getDetail(id);
            model.addAttribute("artista", detailDTO);
            return "views/artista/artista-detail";

        } catch (ResourceNotFoundException ex) {
            String msg = messageSource.getMessage("msg.artista-controller.detail.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/artistas";

        } catch (Exception e) {
            logger.error("Error detalle artista ID {}: {}", id, e.getMessage());
            return "redirect:/artistas";
        }
    }
}
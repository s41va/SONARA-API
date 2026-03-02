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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/artistas")
public class ArtistasController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistasController.class);

    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Lista los artistas con paginación y ordenación.
     */
    @GetMapping
    public String listArtistas(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        logger.info("Listando artistas page={}, size={}, sort={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        try {
            Page<ArtistasDTO> page = artistaService.list(pageable);
            model.addAttribute("page", page);

            String sortParam = "nombre,asc";
            if (page.getSort().isSorted()) {
                Sort.Order order = page.getSort().iterator().next();
                sortParam = order.getProperty() + "," + order.getDirection().name().toLowerCase();
            }
            model.addAttribute("sortParam", sortParam);

        } catch (Exception e) {
            logger.error("Error al listar los artistas: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Error al listar los artistas.");
        }

        return "views/artista/artista-list";
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
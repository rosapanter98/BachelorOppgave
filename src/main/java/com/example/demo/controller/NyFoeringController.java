package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Forskrift;
import com.example.demo.model.Sporsmol;
import com.example.demo.model.Standard;
import com.example.demo.model.Tilleggsinformasjon;
import com.example.demo.model.Verdi;
import com.example.demo.service.ForskriftService;
import com.example.demo.service.SporsmolService;
import com.example.demo.service.StandardService;
import com.example.demo.service.TilleggsinformasjonService;
import com.example.demo.service.VerdiForingService;
import com.example.demo.service.VerdiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.IOException;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/nyfoering")
public class NyFoeringController {
    @Autowired
    private VerdiService verdiService;
    
    @Autowired
    private VerdiForingService verdiFøringService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private SporsmolService sporsmolService;

    @Autowired
    private ForskriftService forskriftService;

    @Autowired TilleggsinformasjonService tilleggsinformasjonService;
    /**
     * Displays the 'Ny Foering' page, loading necessary data for the view.
     *
     * @param session the HTTP session for storing and retrieving session attributes
     * @param model the model to pass attributes to the view
     * @return the name of the view to display
     */
    @GetMapping
    public String getNyFoering(HttpSession session, Model model) {
        ObjectMapper mapper = new ObjectMapper();
    
        // Retrieve all "verdi" objects
        List<Verdi> allVerdier = verdiService.hentAlleVerdier();
        List<Forskrift> alleForskrifter = forskriftService.hentAlleForskrift();
        String verdier = allVerdier.stream()
        .map(Verdi::getTittel) // Assuming Verdi class has getTittel() method
        .collect(Collectors.joining(","));

    // Check if the values are valid and add them to the model
    if (allVerdier != null && !allVerdier.isEmpty()) {
        model.addAttribute("verdier", verdier);
    }
    if (alleForskrifter != null && !alleForskrifter.isEmpty()) {
        try {
            String forskriftJson = mapper.writeValueAsString(alleForskrifter);
            System.out.println("Verdier JSON FORSKRIFTAAAAAAAA: " + forskriftJson); // Log JSON string
            model.addAttribute("forskriftJson", forskriftJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    } else {
        model.addAttribute("forskriftJson", "[]"); // Sikrer at vi alltid har gyldig JSON.
    }
        
        try {
            // Convert allVerdier to JSON
            String verdierJson = mapper.writeValueAsString(allVerdier);
            System.out.println("Verdier JSON: " + verdierJson); // Log JSON string
    
            // Add JSON string to model
            model.addAttribute("verdierJson", verdierJson); 
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log any JSON processing exceptions
            model.addAttribute("verdierJson", "[]"); // Add empty array as fallback
        }
    
        // Retrieve all parent and child questions
        try {
            List<Sporsmol> parents = sporsmolService.hentAlleSporsmol();
            List<Sporsmol> children = new ArrayList<>(parents);
    
            // Convert lists to JSON strings
            String parentJson = (parents != null && !parents.isEmpty()) ? mapper.writeValueAsString(parents) : "[]";
            String childJson = (children != null && !children.isEmpty()) ? mapper.writeValueAsString(children) : "[]";
    
            // Add to model attributes
            model.addAttribute("parentSporsmolJson", parentJson);
            model.addAttribute("childSporsmolJson", childJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            model.addAttribute("parentSporsmolJson", "[]");
            model.addAttribute("childSporsmolJson", "[]");
        }
    
        return "NyFoeringView";  // Return view name
    }
    


/**
 * Handles the post request for creating a new Foering.
 *
 * @param session The HttpSession object.
 * @param ra The RedirectAttributes object.
 * @param verdi_tittel The title of the value (optional).
 * @param sporsmol_beskrivelse The description of the question (optional).
 * @param forskrift_tittel The title of the regulation (optional).
 * @param forskrift_beskrivelse The description of the regulation (optional).
 * @param forskrift_publiseringsdato The publication date of the regulation (optional).
 * @param forskrift_url The URL of the regulation (optional).
 * @param verdi_titler The titles of the values (optional).
 * @param verdi_verdier The values of the values (optional).
 * @param parent_sporsmol_ids The IDs of the parent questions (optional).
 * @param child_sporsmol_ids The IDs of the child questions (optional).
 * @param sporsmol_tittel The title of the question (optional).
 * @param verdi_sporsmol_ids The IDs of the values associated with the question (optional).
 * @param forskrift_ids The IDs of the regulations associated with the question (optional).
 * @param allParams A map containing all the request parameters (optional).
 * @param forside_pdf The PDF file for the front page (optional).
 * @param tilleggsinformasjon_pdf The PDF file for additional information (optional).
 * @return The redirect URL for the new Foering.
 * @throws IOException If an I/O error occurs.
 * @throws java.io.IOException If an I/O error occurs.
 */
@PostMapping
public String postNyFoering(HttpSession session, RedirectAttributes ra,
                            @RequestParam(required = false) String verdi_tittel,
                            @RequestParam(required = false) String sporsmol_beskrivelse,
                            @RequestParam(required = false) String forskrift_tittel,
                            @RequestParam(required = false) String forskrift_beskrivelse,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate forskrift_publiseringsdato,
                            @RequestParam(required = false) String forskrift_url,
                            @RequestParam(required = false) List<String> verdi_titler,
                            @RequestParam(required = false) List<String> verdi_verdier,
                            @RequestParam(required = false) List<Long> parent_sporsmol_ids,
                            @RequestParam(required = false) List<Long> child_sporsmol_ids,
                            @RequestParam(required = false) String sporsmol_tittel,
                            @RequestParam(required = false) List<Long> verdi_sporsmol_ids,
                            @RequestParam(required=false) List<Long> forskrift_ids, // New parameter for "verdi_sporsmol"
                            @RequestParam(required = false) Map<String, String> allParams,
                            @RequestParam(required = false) MultipartFile forside_pdf,
                            @RequestParam(required = false) MultipartFile tilleggsinformasjon_pdf) throws IOException, java.io.IOException {
    if (verdi_tittel != null && !verdi_tittel.isEmpty()) {
        Verdi verdi=new Verdi(verdi_tittel);
        verdiService.lagVerdi(verdi);
    }
    if(forskrift_tittel!=null && !forskrift_tittel.isEmpty()){
        Forskrift forskrift = new Forskrift(forskrift_tittel, forskrift_beskrivelse, forskrift_publiseringsdato, forskrift_url);
        forskriftService.lagForskrift(forskrift);
    }

    if (allParams.containsKey("standard_tittel") && !allParams.get("standard_tittel").isEmpty()) {
        String standardTittel = allParams.get("standard_tittel");
        String standardNummer = allParams.get("standard_nummer");
        int publiseringsAr = Integer.parseInt(allParams.get("publiserings_ar"));
        List<String> amendments = Arrays.asList(allParams.getOrDefault("amendments", "").split(","));

        Standard standard = standardService.lagStandard(standardTittel, standardNummer, publiseringsAr, amendments);

        if (verdi_titler != null && verdi_verdier != null && verdi_titler.size() == verdi_verdier.size()) {
            Map<String, String> verdiForingVerdier = IntStream.range(0, verdi_titler.size())
                .boxed()
                .collect(Collectors.toMap(verdi_titler::get, verdi_verdier::get));
            verdiFøringService.lagVerdiforing(standard, verdiForingVerdier);
        }
    }

    if (sporsmol_tittel != null && !sporsmol_tittel.isEmpty()) {
        List<Verdi> verdier = Optional.ofNullable(verdi_sporsmol_ids)
            .orElseGet(ArrayList::new)
            .stream()
            .map(verdiService::hentVerdi)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    
        @SuppressWarnings("unused")
        List<Forskrift> forskrifter = Optional.ofNullable(forskrift_ids)
            .orElseGet(ArrayList::new)
            .stream()
            .map(forskriftService::hentForskrift)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    
        Long tilleggsinformasjonId = handleTilleggsinformasjon(tilleggsinformasjon_pdf);
    
        Sporsmol sporsmol = sporsmolService.lagSporsmol(sporsmol_tittel, sporsmol_beskrivelse, parent_sporsmol_ids, child_sporsmol_ids, verdier, forskrift_ids, tilleggsinformasjonId);
        if (sporsmol != null) {
            ra.addFlashAttribute("success", "Spørsmål lagt til med ID: " + sporsmol.getId());
        } else {
            ra.addFlashAttribute("fail", "Ingenting lagt til.");
        }
    }
    if(forside_pdf!=null && !forside_pdf.isEmpty()){
        byte[] pdfBytes = (byte[])forside_pdf.getBytes();
        @SuppressWarnings("unused")
        Tilleggsinformasjon tilleggsinformasjon = tilleggsinformasjonService.lagForside(pdfBytes);
    }

    return "redirect:/nyfoering";
    }
    // Additional method to handle the tilleggsinformasjon processing
    /**
     * Handles the additional information provided in the form of a PDF file.
     * 
     * @param tilleggsinformasjon_pdf The PDF file containing the additional information.
     * @return The ID of the created Tilleggsinformasjon object, or null if no file was received.
     * @throws java.io.IOException If an I/O error occurs while reading the PDF file.
     */
    private Long handleTilleggsinformasjon(MultipartFile tilleggsinformasjon_pdf) throws java.io.IOException {
        if (tilleggsinformasjon_pdf != null && !tilleggsinformasjon_pdf.isEmpty()) {
            System.out.println("Received file: " + tilleggsinformasjon_pdf.getOriginalFilename() + " Size: " + tilleggsinformasjon_pdf.getSize());
            byte[] pdfBytes = tilleggsinformasjon_pdf.getBytes();
            Tilleggsinformasjon tilleggsinformasjon = tilleggsinformasjonService.lagTilleggsinformasjon(pdfBytes);
            return Optional.ofNullable(tilleggsinformasjon)
                .map(Tilleggsinformasjon::getId)
                .orElse(null);
        } else {
            System.out.println("No file received");
            return null;
        }
    }
}
package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Sporsmol;
import com.example.demo.model.Standard;
import com.example.demo.model.Verdi;
import com.example.demo.model.VerdiForing;
import com.example.demo.service.SporsmolService;
import com.example.demo.service.TilleggsinformasjonService;
import com.example.demo.util.VeilederUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/veileder")
public class VeilederController {

    private VeilederUtil veilederUtil = new VeilederUtil();
    
    @Autowired
    private SporsmolService sporsmolService;

    @Autowired
    private TilleggsinformasjonService tilleggsinformasjonService;
    /**
     * Retrieve and display the guidance view or redirect to PDF preparation if needed.
     * This method checks if the guidance process is complete and either continues to collect input
     * or proceeds to prepare a PDF for download or viewing.
     *
     * @param session the HTTP session holding stateful information
     * @param model the model to pass attributes to the view
     * @param response the HttpServletResponse object to modify the response attributes
     * @param request the HttpServletRequest object, providing request information
     * @return the name of the view or redirect directive
     */
    @GetMapping
    public String getVeileder(HttpSession session, Model model, HttpServletResponse response, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<Sporsmol> sporsmolList = (List<Sporsmol>) session.getAttribute("sporsmolList");
        Boolean ferdig = (Boolean) session.getAttribute("ferdig");
        if (ferdig == null) {
            session.setAttribute("ferdig", false);
            ferdig = false;
        }

        if (!ferdig) {
            // Load initial or ongoing questionnaire
            if (sporsmolList == null || sporsmolList.isEmpty()) {
                sporsmolList = sporsmolService.hentAlleStartSporsmol();
                session.setAttribute("sporsmolList", sporsmolList);
            }
            model.addAttribute("sporsmolList", sporsmolList);
            return "VeilederView"; // Return the view for ongoing input
        } else {
            // Check if PDF preparation is needed
            return "redirect:/veileder/prepare-pdf"; // Redirect to a method that checks/prepares PDF data
        }
    }
    /**
     * View a PDF document inline.
     * This method retrieves a PDF from the session and sends it to the browser to be displayed inline.
     *
     * @param session the HTTP session where the PDF is stored
     * @param response the HttpServletResponse used to send the PDF to the client
     * @throws IOException if an I/O error occurs during PDF handling
     */
    @GetMapping("/view-pdf")
    public void viewPdf(HttpSession session, HttpServletResponse response) throws IOException {
        byte[] pdfBytes = (byte[]) session.getAttribute("preparedPdfBytes");
        if (pdfBytes != null) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=standarder_og_poeng.pdf");
            response.setContentLength(pdfBytes.length);
            try (InputStream bis = new ByteArrayInputStream(pdfBytes)) {
                FileCopyUtils.copy(bis, response.getOutputStream());
            }
        }
    }
    
    /**
     * Download a PDF document.
     * This method retrieves a PDF from the session and prompts the user to download it.
     *
     * @param session the HTTP session where the PDF is stored
     * @param response the HttpServletResponse used to send the PDF to the client
     * @throws IOException if an I/O error occurs during PDF handling
     */
    @GetMapping("/download-pdf")
    public void downloadPdf(HttpSession session, HttpServletResponse response) throws IOException {
        byte[] pdfBytes = (byte[]) session.getAttribute("preparedPdfBytes");
        if (pdfBytes != null) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=standarder_og_poeng.pdf");
            response.setContentLength(pdfBytes.length);
            try (InputStream bis = new ByteArrayInputStream(pdfBytes)) {
                FileCopyUtils.copy(bis, response.getOutputStream());
            }
        }
    }

/**
     * Prepare a PDF for viewing or downloading.
     * This method checks if a PDF has already been prepared and redirects to the logic to prepare it if not.
     *
     * @param session the HTTP session that might already contain the prepared PDF
     * @param model the model to pass attributes to the view
     * @return a redirect string to either the PDF options view or the preparation logic
     */
    @GetMapping("/prepare-pdf")
    public String preparePdf(HttpSession session, Model model) {
        // Assume 'preparedPdf' is the session attribute that checks if PDF is already prepared
        ByteArrayInputStream preparedPdf = (ByteArrayInputStream) session.getAttribute("preparedPdf");
        if (preparedPdf == null) {
            // Logic to prepare PDF if not done yet
            return "redirect:/veileder/prepare-pdf-logic"; // A method that actually prepares the PDF
        }
        return "pdfOptionsView"; // A view that gives users options to view or download the PDF
    }

    /**
     * Logic to prepare a PDF based on session data.
     * This method generates a PDF based on user responses and session-stored data, saving the resulting PDF back into the session.
     *
     * @param session the HTTP session used to store the PDF and associated data
     * @return a redirect string to the PDF options view or back to the main guidance view
     */
    @GetMapping("/prepare-pdf-logic")
    public String preparePdfLogic(HttpSession session) {
        try {
            @SuppressWarnings("unchecked")
            Map<Standard, Integer> standardPoeng = (Map<Standard, Integer>) session.getAttribute("standardPoeng");
            @SuppressWarnings("unchecked")
            List<Sporsmol> alleJaSvar = (List<Sporsmol>) session.getAttribute("alleJaSvar");
            List<Long> sporsmolIds = alleJaSvar.stream().map(Sporsmol::getId).collect(Collectors.toList());

            if (!sporsmolIds.isEmpty() || (standardPoeng != null && !standardPoeng.isEmpty())) {
                byte[] pdfBytes = veilederUtil.generatePdf(standardPoeng, sporsmolIds, tilleggsinformasjonService, sporsmolService);
                session.setAttribute("preparedPdfBytes", pdfBytes);
                return "redirect:/veileder/pdf-options";  // Redirect to the options view
            } else {
                return "redirect:/veileder";  // Redirect back to the main view if no data
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Handle exceptions properly
        }
    }
    /**
     * Process answers submitted via the questionnaire and update session state accordingly.
     * This method updates session attributes based on the answers provided, recalculating scores and determining next steps.
     *
     * @param session the session holding current state, including already provided answers
     * @param redirectAttributes attributes used for passing data in case of a redirect
     * @param jaSvarIds list of IDs corresponding to 'yes' answers in the questionnaire
     * @return a redirect string to the next appropriate view or action
     */
    
    @SuppressWarnings("unchecked")
    @PostMapping
    public String behandleSvar(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam List<Long> jaSvarIds) {
        List<Sporsmol> jaSvarSporsmol = jaSvarIds.stream()
            .flatMap(id -> sporsmolService.hentSporsmol(id).map(Stream::of).orElseGet(Stream::empty))
            .collect(Collectors.toList());

        Set<Sporsmol> children = new HashSet<>();
        Map<Standard, Integer> standardPoeng = (Map<Standard, Integer>) session.getAttribute("standardPoeng");
        if (standardPoeng == null) {
            standardPoeng = new HashMap<>();
        }

        // Henter den eksisterende listen av ja-svarte spørsmål fra session, eller initialiserer den om nødvendig
        List<Sporsmol> alleJaSvar = (List<Sporsmol>) session.getAttribute("alleJaSvar");
        if (alleJaSvar == null) {
            alleJaSvar = new ArrayList<>();
        }
        // Legger til de nye ja-svarte spørsmålene til listen
        alleJaSvar.addAll(jaSvarSporsmol);
        session.setAttribute("alleJaSvar", alleJaSvar);
        
        for (Sporsmol sporsmol : jaSvarSporsmol) {
            children.addAll(sporsmol.getChildren());
            for (Verdi verdi : sporsmol.getVerdier()) {
                for (VerdiForing verdiForing : verdi.getVerdiFøringer()) {
                    Standard standard = verdiForing.getStandard();
                    Long poeng = verdiForing.getNumeriskVerdi();
                    standardPoeng.put(standard, standardPoeng.getOrDefault(standard, 0) + poeng.intValue());
                }
            }
        }
        if (children.isEmpty()) {
            session.setAttribute("ferdig", true);
            session.setAttribute("sporsmolList", new ArrayList<>());
        } else {
            session.setAttribute("sporsmolList", new ArrayList<>(children));
        }

        session.setAttribute("standardPoeng", standardPoeng);
        return "redirect:/veileder";
    }
    
}
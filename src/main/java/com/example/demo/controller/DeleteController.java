package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Forskrift;
import com.example.demo.model.Sporsmol;
import com.example.demo.model.Standard;
import com.example.demo.model.Verdi;
import com.example.demo.service.ForskriftService;
import com.example.demo.service.SporsmolService;
import com.example.demo.service.StandardService;
import com.example.demo.service.VerdiService;

import jakarta.servlet.http.HttpSession;


/**
 * The `DeleteController` class is a controller responsible for handling delete operations for various entities.
 * It provides endpoints for deleting questions, standards, values, and regulations.
 */
@RestController
public class DeleteController {
    
    @Autowired
    private SporsmolService sporsmolService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private VerdiService verdiService;

    @Autowired
    private ForskriftService forskriftService;
    
    /**
             * Represents a model and view for rendering a web page.
             * 
             * The `ModelAndView` class is used to pass data from the controller to the view
             * and specify the view name to be rendered.
             * 
             * In this case, the `ModelAndView` object is created with the view name "SlettFoeringView"
             * and the lists of `sporsmolList`, `standardsList`, `verdiList`, and `forskriftList` are added as attributes.
             * 
             * @see org.springframework.web.servlet.ModelAndView
             */
    @GetMapping("/slettfoering")
    public ModelAndView getQuestions(HttpSession session) {
        List<Sporsmol> sporsmolList = sporsmolService.hentAlleSporsmol();
        List<Standard> standardsList = standardService.hentAlleStandarder();
        List<Verdi> verdiList = verdiService.hentAlleVerdier();
        List<Forskrift> forskriftList = forskriftService.hentAlleForskrift();
        
        session.setAttribute("sporsmolList", sporsmolList);
        session.setAttribute("standardsList", standardsList);
        session.setAttribute("verdiList", verdiList);
        session.setAttribute("forskriftList", forskriftList);

        ModelAndView modelAndView = new ModelAndView("SlettFoeringView");
        modelAndView.addObject("sporsmolList", sporsmolList);
        modelAndView.addObject("standardsList", standardsList);
        modelAndView.addObject("verdiList", verdiList);
        modelAndView.addObject("forskriftList", forskriftList);

        return modelAndView;
    }

    /**
     * Deletes multiple Forskrifts based on the provided IDs.
     *
     * @param payload A map containing the IDs of the Forskrifts to be deleted.
     * @return A ResponseEntity with a success message if the deletion is successful, or a bad request response if no IDs are provided.
     */
    @PostMapping("/slettforskrift")
    public ResponseEntity<String> deleteForskrifts(@RequestBody Map<String, List<Long>> payload){
        List<Long> ids = payload.get("ids");
        if(ids == null || ids.isEmpty()){
            return ResponseEntity.badRequest().body("No IDs provided.");
        }

        for (Long id : ids) {
            forskriftService.slettForskrift(id);
        }
        return ResponseEntity.ok("Deletion successful.");
    }

    /**
        * Deletes multiple questions based on the provided IDs.
        *
        * @param payload A map containing the IDs of the questions to be deleted.
        * @return A ResponseEntity with a success message if the deletion is successful, or a bad request message if no IDs are provided.
        */
    @PostMapping("/slettsporsmol")
    public ResponseEntity<String> deleteQuestions(@RequestBody Map<String, List<Long>> payload) {
        List<Long> ids = payload.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("No IDs provided.");
        }

        for (Long id : ids) {
            sporsmolService.slettSporsmol(id);
        }
        return ResponseEntity.ok("Deletion successful.");
        
    }

    /**
        * Deletes the standards with the provided IDs.
        *
        * @param payload A map containing the IDs of the standards to be deleted.
        * @return A ResponseEntity with a success message if the deletion is successful, or a bad request message if no IDs are provided.
        */
    @PostMapping("/slettstandard")
    public ResponseEntity<String> deleteStandard(@RequestBody Map<String, List<Long>> payload) {
        List<Long> ids = payload.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("No IDs provided.");
        }

        for (Long id : ids) {
            standardService.slettStandard(id);
        }

        return ResponseEntity.ok("Deletion successful.");
    }

    /**
        * Deletes the values with the specified IDs.
        *
        * @param payload the request payload containing the IDs of the values to be deleted
        * @return a ResponseEntity with a success message if the deletion is successful, or a bad request response if no IDs are provided
        */
    @PostMapping("/slettverdi")
    public ResponseEntity<String> deleteVerdier(@RequestBody Map<String, List<Long>> payload) {
        List<Long> ids = payload.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("No IDs provided.");
        }

        for (Long id : ids) {
            verdiService.slettVerdi(id);
        }

        return ResponseEntity.ok("Deletion successful.");
    }

}

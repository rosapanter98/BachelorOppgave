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

import com.example.demo.model.Sporsmol;
import com.example.demo.model.Standard;
import com.example.demo.model.Verdi;
import com.example.demo.service.SporsmolService;
import com.example.demo.service.StandardService;
import com.example.demo.service.VerdiService;

import jakarta.servlet.http.HttpSession;

@RestController
public class UpdateController {

    @Autowired
    private SporsmolService sporsmolService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private VerdiService verdiService;


    /**
     * Retrieves the entities needed for updating and sets them in the session.
     * Returns a ModelAndView object with the necessary data for the update view.
     *
     * @param session the HttpSession object
     * @return a ModelAndView object with the necessary data for the update view
     */
    @GetMapping("/update")
    public ModelAndView getEntitiesForUpdate(HttpSession session) {
        List<Sporsmol> sporsmolList = sporsmolService.hentAlleSporsmol();
        List<Standard> standardsList = standardService.hentAlleStandarder();
        List<Verdi> verdiList = verdiService.hentAlleVerdier();

        session.setAttribute("updateSporsmolList", sporsmolList);
        session.setAttribute("updateStandardsList", standardsList);
        session.setAttribute("updateVerdiList", verdiList);

        ModelAndView modelAndView = new ModelAndView("UpdateFoeringView");
        modelAndView.addObject("sporsmolList", sporsmolList);
        modelAndView.addObject("standardsList", standardsList);
        modelAndView.addObject("verdiList", verdiList);

        return modelAndView;
    }

    /**
     * Updates a specific question with the given data.
     *
     * @param updateData the request body containing the ID, new title, children IDs, parent IDs, and value IDs
     * @return a ResponseEntity with a success message if the update was successful,
     *         or an error message if the update failed or the request body is invalid
     */
    @PostMapping("/updatesporsmol")
    @SuppressWarnings("unchecked")
    public ResponseEntity<String> updateQuestion(@RequestBody Map<String, Object> updateData) {
        Long id = Long.valueOf(updateData.get("id").toString());
        String newTitle = updateData.get("newTitle").toString();
        List<Long> childIds = (List<Long>) updateData.get("children");
        List<Long> parentIds = (List<Long>) updateData.get("parents");
        List<Long> verdiIds = (List<Long>) updateData.get("verdier");

        boolean updated = sporsmolService.updateSporsmol(id, newTitle, childIds, parentIds, verdiIds);
        if (updated) {
            return ResponseEntity.ok("Update successful.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update. Check if all IDs are correct and no cycles would be created.");
        }
    }

    /**
     * Updates a standard with the given data.
     *
     * @param updateData the request body containing the ID and new title
     * @return a ResponseEntity with a success message if the update was successful,
     *         or an error message if the update failed
     */
    @PostMapping("/updatestandard")
    public ResponseEntity<String> updateStandard(@RequestBody Map<String, Object> updateData) {
        Long id = Long.valueOf(updateData.get("id").toString());
        String newTitle = updateData.get("newTitle").toString();
        // Assume more fields as needed
        boolean updated = standardService.updateStandard(id, newTitle);
        if (updated) {
            return ResponseEntity.ok("Update successful.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update.");
        }
    }

    /**
     * Updates a value with the given ID and new title.
     *
     * @param updateData the request body containing the ID and new title
     * @return a ResponseEntity with a success message if the update was successful,
     *         or an error message if the update failed or the request body is invalid
     */
    @PostMapping("/updateverdi")
    public ResponseEntity<String> updateValue(@RequestBody Map<String, Object> updateData) {
        if (updateData == null) {
            return ResponseEntity.badRequest().body("Request body cannot be empty.");
        }
    
        Object idObj = updateData.get("id");
        Object titleObj = updateData.get("newTitle");
    
        if (idObj == null || titleObj == null) {
            return ResponseEntity.badRequest().body("Missing required fields: 'id' and/or 'newTitle'.");
        }
    
        Long id;
        String newTitle;
    
        try {
            id = Long.valueOf(idObj.toString());
            newTitle = titleObj.toString();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid format for 'id'. Must be a long.");
        }
    
        boolean updated = verdiService.updateVerdi(id, newTitle);
        if (updated) {
            return ResponseEntity.ok("Update successful.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update.");
        }
    }
}


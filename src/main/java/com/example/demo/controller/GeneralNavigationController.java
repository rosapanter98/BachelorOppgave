package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;



/**
 * This class handles the general navigation within the application.
 * It maps different URLs to their corresponding views.
 */
@Controller
@RequestMapping("")
public class GeneralNavigationController {

    /**
     * Handles the HTTP GET request for the default URL "/".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "home".
     */
    @GetMapping
    public String home1(Model model) {
        model.addAttribute("currentPage", "home");
        return "home";
    }

    /**
     * Handles the HTTP GET request for the URL "/veileder/pdf-options".
     * 
     * @param model The Model object to add attributes to.
     * @param session The HttpSession object.
     * @return The name of the view to render, which is "pdfOptionsView".
     */
    @GetMapping("/veileder/pdf-options")
    public String pdfOptions(Model model, HttpSession session) {
        model.addAttribute("currentPage", "pdf-options");
        return "pdfOptionsView";
    }

    /**
     * Handles the HTTP GET request for the URL "/admin".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "AdminView".
     */
    @GetMapping("/admin")
    public String getAdmin(Model model) {
        model.addAttribute("currentPage", "admin");
        return "AdminView";
    }

    /**
     * Handles the HTTP GET request for the URL "/error".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "error".
     */
    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("currentPage", "error");
        return "error";
    }

    /**
     * Handles the HTTP GET request for the URL "/index".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "home".
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("currentPage", "home");
        return "home";
    }

    /**
     * Handles the HTTP GET request for the URL "/home".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "home".
     */
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("currentPage", "home");
        return "home";
    }

    /**
     * Handles the HTTP GET request for the URL "/about".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "about".
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("currentPage", "about");
        return "about";
    }

    /**
     * Handles the HTTP GET request for the URL "/contact".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "contact".
     */
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("currentPage", "contact");
        return "contact";
    }

    /**
     * Handles the HTTP GET request for the URL "/login".
     * 
     * @param model The Model object to add attributes to.
     * @return The name of the view to render, which is "login".
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("currentPage", "login");
        return "login";
    }
}

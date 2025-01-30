package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.service.UserService;

/**
 * Controller class for handling user registration.
 * This class manages the registration workflow including showing the registration form
 * and processing the form submission.
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * Display the user registration form.
     * This method adds an empty {@link UserRegistrationDTO} object to the model to hold form data
     * and identifies the current page as 'register' for highlighting purposes in the view.
     *
     * @param model the {@link Model} object that holds the data for the view
     * @return the name of the view to show ('register')
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("currentPage", "register");
        return "register";
    }

    /**
     * Process the user registration form submission.
     * This method compares the password and confirmation password for equality and, if valid,
     * persists the user data using {@link UserService}. If the passwords do not match,
     * an error message is added to the model, and the user is redirected back to the registration form.
     *
     * @param registrationDto the DTO that captures user registration data
     * @param redirectAttributes the attributes for a redirect scenario, used here for flash attributes
     * @return a redirect string to either the registration form if there are errors or to the home page on success
     */
    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDTO registrationDto, RedirectAttributes redirectAttributes) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords must match!");
            return "redirect:/register";
        }
        userService.save(registrationDto);
        return "redirect:/home";
    }

}

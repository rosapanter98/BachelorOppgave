package com.example.demo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.controller.GeneralNavigationController;

/**
 * Global controller advice to enrich models with user authentication status.
 * This advice is applied to controllers in the same package as {@link GeneralNavigationController},
 * automatically adding authentication-related attributes to all model objects that are handled
 * by those controllers.
 */
@ControllerAdvice(basePackageClasses = GeneralNavigationController.class)
public class UserAuthenticationAdvice {

    /**
     * Adds attributes related to the user's login status, username, and role to the model.
     * This method checks if the user is logged in and extracts information from the security context
     * to populate the model before it is returned to the view. This enriches the view layer with
     * necessary user context, such as showing or hiding elements based on the user's authentication status
     * or role.
     *
     * @param model the {@link Model} object to which the login status attributes are added
     */
    @SuppressWarnings("null")
    @ModelAttribute
    public void addLoggedInStatus(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");
        model.addAttribute("loggedIn", isLoggedIn);
        model.addAttribute("username", isLoggedIn ? auth.getName() : "");
        model.addAttribute("role", isLoggedIn ? auth.getAuthorities().iterator().next().getAuthority() : "");
    }
}

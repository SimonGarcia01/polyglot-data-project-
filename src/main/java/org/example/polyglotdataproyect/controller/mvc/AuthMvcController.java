package org.example.polyglotdataproyect.controller.mvc;

import jakarta.servlet.http.HttpSession;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthMvcController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        User user = authenticationService.login(username, password);

        if (user != null) {
            // Guardar información del usuario en la sesión
            session.setAttribute("currentUser", username);
            session.setAttribute("currentUserRole", user.getRole());

            // Extraer el userId para pasarlo al dashboard
            String userId = authenticationService.extractSqlUserId(user);
            session.setAttribute("currentUserId", userId);

            switch (user.getRole()) {
                case "ADMIN":
                    return "redirect:/dashboard/admin";
                case "TRAINER":
                    return "redirect:/dashboard/trainer?trainerId=" + userId;
                case "STUDENT":
                case "EMPLOYEE":
                default:
                    return "redirect:/dashboard?userId=" + userId;
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}

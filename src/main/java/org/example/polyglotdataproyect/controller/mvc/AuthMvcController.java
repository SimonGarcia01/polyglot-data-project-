package org.example.polyglotdataproyect.controller.mvc;

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
                        RedirectAttributes redirectAttributes) {

        User user = authenticationService.login(username, password);

        if (user != null) {
            switch (user.getRole()) {
                case "ADMIN":
                    return "redirect:/dashboard/admin";
                case "TRAINER":
                    return "redirect:/dashboard/trainer";
                case "STUDENT":
                case "EMPLOYEE":
                default:
                    return "redirect:/dashboard";
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", true);
            return "redirect:/auth/login";
        }
    }
}

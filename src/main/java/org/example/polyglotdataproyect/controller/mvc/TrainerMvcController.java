package org.example.polyglotdataproyect.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer")
public class TrainerMvcController {

    @GetMapping("/dashboard")
    public String trainerDashboard() {
        return "trainer/dashboard";
    }

    @GetMapping("/assigned-users")
    public String viewAssignedUsers() {
        return "trainer/assigned-users";
    }
}

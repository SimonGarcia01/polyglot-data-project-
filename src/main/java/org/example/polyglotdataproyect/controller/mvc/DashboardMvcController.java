package org.example.polyglotdataproyect.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardMvcController {

    @GetMapping
    public String dashboard() {
        // Logic to determine which dashboard to show based on user role
        return "dashboard/user"; // Example, will need more logic
    }

    @GetMapping("/admin")                                                                          
    public String adminDashboard() {                                                              
    return "dashboard/admin"; 
    }                                                                 

}

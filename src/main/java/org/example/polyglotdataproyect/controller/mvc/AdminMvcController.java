package org.example.polyglotdataproyect.controller.mvc;

import org.example.polyglotdataproyect.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMvcController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/assign-trainer")
    public String assignTrainer() {
        return "admin/assign-trainer";
    }

    @GetMapping("/assignments")
    public String viewAssignments(Model model) {
        model.addAttribute("assignments", adminService.getAllAssignments());
        return "admin/assignments";
    }

    @GetMapping("/assignments")                                                                    
    public String viewAssignments() {                                                              
    return "admin/assignments";                                                                
    }
}

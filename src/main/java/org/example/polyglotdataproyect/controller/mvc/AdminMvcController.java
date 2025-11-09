package org.example.polyglotdataproyect.controller.mvc;

import org.example.polyglotdataproyect.entities.MongoUser;
import org.example.polyglotdataproyect.entities.TrainerTrainee;
import org.example.polyglotdataproyect.services.AdminService;
import org.example.polyglotdataproyect.services.DataMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminMvcController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DataMigrationService dataMigrationService;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        model.addAttribute("assignments", adminService.getAllAssignments());
        return "dashboard/admin";
    }

    @GetMapping("/assign-trainer")
    public String assignTrainerForm(Model model) {
        model.addAttribute("trainers", adminService.getAllTrainers());
        model.addAttribute("students", adminService.getAllStudents());
        model.addAttribute("collaborators", adminService.getAllCollaborators());
        return "admin/assign-trainer";
    }

    @PostMapping("/assign-trainer")
    public String assignTrainer(@RequestParam String trainerId,
                               @RequestParam String traineeId,
                               RedirectAttributes redirectAttributes) {
        try {
            adminService.assignTrainer(trainerId, traineeId);
            redirectAttributes.addFlashAttribute("success", "Entrenador asignado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al asignar entrenador: " + e.getMessage());
        }
        return "redirect:/admin/assignments";
    }

    @GetMapping("/assignments")
    public String viewAssignments(Model model) {
        List<TrainerTrainee> assignments = adminService.getAllAssignments();
        List<MongoUser> allUsers = adminService.getAllUsers();

        // Crear un mapa de ID a usuario para búsqueda rápida
        Map<String, MongoUser> userMap = new HashMap<>();
        for (MongoUser user : allUsers) {
            userMap.put(user.getId(), user);
        }

        model.addAttribute("assignments", assignments);
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("userMap", userMap);
        return "admin/assignments";
    }

    @PostMapping("/assignments/{assignmentId}/delete")
    public String deleteAssignment(@PathVariable String assignmentId,
                                  RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteAssignment(assignmentId);
            redirectAttributes.addFlashAttribute("success", "Asignación eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar asignación: " + e.getMessage());
        }
        return "redirect:/admin/assignments";
    }

    @GetMapping("/migrate-data")
    public String showMigrationPage(Model model) {
        return "admin/migrate-data";
    }

    @PostMapping("/migrate-data")
    public String migrateData(@RequestParam(required = false, defaultValue = "false") boolean cleanFirst,
                             RedirectAttributes redirectAttributes) {
        try {
            int migratedCount;
            if (cleanFirst) {
                migratedCount = dataMigrationService.cleanAndMigrate();
                redirectAttributes.addFlashAttribute("success",
                    "Migración completa ejecutada. " + migratedCount + " usuarios migrados desde SQL a MongoDB");
            } else {
                migratedCount = dataMigrationService.migrateAllUsersToMongo();
                redirectAttributes.addFlashAttribute("success",
                    "Migración incremental ejecutada. " + migratedCount + " usuarios nuevos migrados desde SQL a MongoDB");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                "Error durante la migración: " + e.getMessage());
        }
        return "redirect:/admin/migrate-data";
    }
}

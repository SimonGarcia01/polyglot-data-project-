package org.example.polyglotdataproyect.controller.mvc;

import org.example.polyglotdataproyect.services.ExerciseService;
import org.example.polyglotdataproyect.services.MongoUserService;
import org.example.polyglotdataproyect.services.ProgressService;
import org.example.polyglotdataproyect.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class DashboardMvcController {

    @Autowired
    private MongoUserService mongoUserService;

    @Autowired
    private RoutineService routineService;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public String dashboard(@RequestParam(required = false) String userId, Model model) {
        // Dashboard general para usuarios (estudiantes/colaboradores)
        if (userId != null && !userId.isEmpty()) {
            mongoUserService.getUserById(userId).ifPresent(user -> {
                model.addAttribute("user", user);
                model.addAttribute("routines", routineService.getRoutinesByOwner(userId));
                model.addAttribute("progressEntries", progressService.getProgressEntriesByUser(userId));
                model.addAttribute("templateRoutines", routineService.getTemplateRoutines());
                model.addAttribute("userId", userId);
                model.addAttribute("routineCount", routineService.getRoutinesByOwner(userId).size());
            });
        }
        model.addAttribute("exercises", exerciseService.getAllExercises());
        return "dashboard/user";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", mongoUserService.getAllUsers().size());
        model.addAttribute("totalExercises", exerciseService.getAllExercises().size());
        model.addAttribute("totalRoutines", routineService.getAllRoutines().size());
        model.addAttribute("recentUsers", mongoUserService.getAllUsers());
        return "dashboard/admin";
    }

    @GetMapping("/trainer")
    public String trainerDashboard(@RequestParam(required = false) String trainerId, Model model) {
        // Redirigir al dashboard específico del entrenador
        if (trainerId != null && !trainerId.isEmpty()) {
            mongoUserService.getUserById(trainerId).ifPresent(trainer -> {
                model.addAttribute("trainer", trainer);
            });
            return "redirect:/trainer/dashboard?trainerId=" + trainerId;
        }
        return "dashboard/trainer";
    }
}

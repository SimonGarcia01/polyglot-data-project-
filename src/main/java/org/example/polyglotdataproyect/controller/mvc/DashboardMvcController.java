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
        if (userId == null || userId.isEmpty()) {
            // Si no hay userId, redirigir al login
            return "redirect:/auth/login";
        }

        var userOptional = mongoUserService.getUserBySqlUserId(userId);
        if (userOptional.isEmpty()) {
            // Si no se encuentra el usuario en MongoDB, redirigir al login
            return "redirect:/auth/login?error=user_not_found";
        }

        var user = userOptional.get();
        var routines = routineService.getRoutinesByOwner(userId);
        var progressEntries = progressService.getProgressEntriesByUser(userId);

        model.addAttribute("user", user);
        model.addAttribute("routines", routines);
        model.addAttribute("progressEntries", progressEntries);
        model.addAttribute("templateRoutines", routineService.getTemplateRoutines());
        model.addAttribute("userId", userId);
        model.addAttribute("routineCount", routines.size());
        model.addAttribute("exercises", exerciseService.getAllExercises());

        // Agregar estadísticas del mes
        var stats = new java.util.HashMap<String, Integer>();
        stats.put("routinesStarted", routines.size());
        stats.put("progressLogged", progressEntries.size());
        model.addAttribute("stats", stats);

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
            mongoUserService.getUserBySqlUserId(trainerId).ifPresent(trainer -> {
                model.addAttribute("trainer", trainer);
            });
            return "redirect:/trainer/dashboard?trainerId=" + trainerId;
        }
        return "dashboard/trainer";
    }
}

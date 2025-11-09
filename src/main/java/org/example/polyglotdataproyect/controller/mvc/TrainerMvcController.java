package org.example.polyglotdataproyect.controller.mvc;

import org.example.polyglotdataproyect.entities.MongoUser;
import org.example.polyglotdataproyect.entities.ProgressEntry;
import org.example.polyglotdataproyect.entities.TrainerTrainee;
import org.example.polyglotdataproyect.services.AssignmentService;
import org.example.polyglotdataproyect.services.MongoUserService;
import org.example.polyglotdataproyect.services.ProgressService;
import org.example.polyglotdataproyect.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/trainer")
public class TrainerMvcController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private MongoUserService mongoUserService;

    @Autowired
    private ProgressService progressService;

    @Autowired
    private RoutineService routineService;

    @GetMapping("/dashboard")
    public String trainerDashboard(@RequestParam(required = false) String trainerId, Model model) {
        if (trainerId != null && !trainerId.isEmpty()) {
            // Obtener información del trainer
            mongoUserService.getUserById(trainerId).ifPresent(trainer -> {
                model.addAttribute("trainer", trainer);
            });

            List<TrainerTrainee> assignments = assignmentService.getAssignmentsByTrainer(trainerId);
            model.addAttribute("assignments", assignments);
            model.addAttribute("trainerId", trainerId);
            model.addAttribute("assignedUserCount", assignments.size());

            // Obtener información de los usuarios asignados
            List<MongoUser> assignedUsers = new ArrayList<>();
            for (TrainerTrainee assignment : assignments) {
                Optional<MongoUser> user = mongoUserService.getUserById(assignment.getTraineeId());
                user.ifPresent(assignedUsers::add);
            }
            model.addAttribute("assignedUsers", assignedUsers);
        }
        return "trainer/dashboard";
    }

    @GetMapping("/assigned-users")
    public String viewAssignedUsers(@RequestParam(required = false) String trainerId, Model model) {
        if (trainerId != null && !trainerId.isEmpty()) {
            List<TrainerTrainee> assignments = assignmentService.getAssignmentsByTrainer(trainerId);
            model.addAttribute("assignments", assignments);

            // Obtener información detallada de cada usuario asignado
            List<MongoUser> assignedUsers = new ArrayList<>();
            for (TrainerTrainee assignment : assignments) {
                Optional<MongoUser> user = mongoUserService.getUserById(assignment.getTraineeId());
                user.ifPresent(assignedUsers::add);
            }
            model.addAttribute("assignedUsers", assignedUsers);
            model.addAttribute("trainerId", trainerId);
        }
        return "trainer/assigned-users";
    }

    @GetMapping("/user/{userId}/progress")
    public String viewUserProgress(@PathVariable String userId, Model model) {
        List<ProgressEntry> progressEntries = progressService.getProgressEntriesByUser(userId);
        Optional<MongoUser> user = mongoUserService.getUserById(userId);

        model.addAttribute("progressEntries", progressEntries);
        model.addAttribute("user", user.orElse(null));

        return "trainer/user-progress";
    }

    @GetMapping("/user/{userId}/routines")
    public String viewUserRoutines(@PathVariable String userId, Model model) {
        Optional<MongoUser> user = mongoUserService.getUserById(userId);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("routines", routineService.getRoutinesByOwner(userId));
        return "trainer/user-routines";
    }

    @PostMapping("/feedback")
    public String provideFeedback(@RequestParam String userId,
                                 @RequestParam String routineId,
                                 @RequestParam int entryIndex,
                                 @RequestParam String feedback,
                                 RedirectAttributes redirectAttributes) {
        try {
            progressService.addTrainerFeedback(userId, routineId, entryIndex, feedback);
            redirectAttributes.addFlashAttribute("success", "Retroalimentación enviada exitosamente");
            return "redirect:/trainer/user/" + userId + "/progress";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al enviar retroalimentación: " + e.getMessage());
            return "redirect:/trainer/user/" + userId + "/progress";
        }
    }
}

package org.example.polyglotdataproyect.controller.mvc;

import jakarta.servlet.http.HttpSession;
import org.example.polyglotdataproyect.entities.MongoUser;
import org.example.polyglotdataproyect.entities.ProgressEntry;
import org.example.polyglotdataproyect.entities.TrainerTrainee;
import org.example.polyglotdataproyect.services.AssignmentService;
import org.example.polyglotdataproyect.services.MongoUserService;
import org.example.polyglotdataproyect.services.ProgressService;
import org.example.polyglotdataproyect.services.RoutineService;
import org.example.polyglotdataproyect.util.UserContext;
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

    @Autowired
    private UserContext userContext;

    /**
     * Verifica que el usuario tenga rol de TRAINER o ADMIN
     */
    private boolean checkTrainerRole(HttpSession session, RedirectAttributes redirectAttributes) {
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        if (currentUserRole == null || (!currentUserRole.equals("TRAINER") && !currentUserRole.equals("ADMIN"))) {
            redirectAttributes.addFlashAttribute("error", "Acceso denegado. Solo entrenadores pueden acceder a esta sección.");
            return false;
        }
        return true;
    }

    /**
     * Verifica que el trainer tenga asignado al usuario especificado
     * @param sqlTrainerId SQL user ID del entrenador
     * @param traineeId MongoDB ID del usuario asignado
     */
    private boolean checkTrainerHasTrainee(String sqlTrainerId, String traineeId) {
        // Convertir SQL user ID a MongoDB ID
        Optional<MongoUser> trainerOpt = mongoUserService.getUserBySqlUserId(sqlTrainerId);
        if (trainerOpt.isEmpty()) {
            return false;
        }
        String mongoTrainerId = trainerOpt.get().getId();
        List<TrainerTrainee> assignments = assignmentService.getAssignmentsByTrainer(mongoTrainerId);
        return assignments.stream().anyMatch(a -> a.getTraineeId().equals(traineeId));
    }

    @GetMapping("/dashboard")
    public String trainerDashboard(@RequestParam(required = false) String trainerId,
                                  HttpSession session,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (!checkTrainerRole(session, redirectAttributes)) {
            return "redirect:/auth/login";
        }

        // Usar trainerId de la sesión si no se proporciona
        if (trainerId == null || trainerId.isEmpty()) {
            trainerId = (String) session.getAttribute("currentUserId");
        }

        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        if (trainerId != null && !trainerId.isEmpty()) {
            // Obtener información del trainer (trainerId es el sqlUserId del login)
            Optional<MongoUser> trainerOpt = mongoUserService.getUserBySqlUserId(trainerId);
            if (trainerOpt.isPresent()) {
                MongoUser trainer = trainerOpt.get();
                model.addAttribute("trainer", trainer);

                // Usar MongoDB ID para buscar asignaciones
                String mongoTrainerId = trainer.getId();
                List<TrainerTrainee> assignments = assignmentService.getAssignmentsByTrainer(mongoTrainerId);
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
        }
        return "trainer/dashboard";
    }

    @GetMapping("/assigned-users")
    public String viewAssignedUsers(@RequestParam(required = false) String trainerId,
                                   HttpSession session,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (!checkTrainerRole(session, redirectAttributes)) {
            return "redirect:/auth/login";
        }

        // Usar trainerId de la sesión si no se proporciona
        if (trainerId == null || trainerId.isEmpty()) {
            trainerId = (String) session.getAttribute("currentUserId");
        }

        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        if (trainerId != null && !trainerId.isEmpty()) {
            // Convertir SQL user ID a MongoDB ID
            Optional<MongoUser> trainerOpt = mongoUserService.getUserBySqlUserId(trainerId);
            if (trainerOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Entrenador no encontrado");
                return "redirect:/dashboard";
            }

            String mongoTrainerId = trainerOpt.get().getId();
            List<TrainerTrainee> assignments = assignmentService.getAssignmentsByTrainer(mongoTrainerId);
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
    public String viewUserProgress(@PathVariable String userId,
                                   HttpSession session,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (!checkTrainerRole(session, redirectAttributes)) {
            return "redirect:/auth/login";
        }

        String trainerId = (String) session.getAttribute("currentUserId");
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        // Verificar que el trainer tenga asignado este usuario (excepto si es admin)
        if (!"ADMIN".equals(currentUserRole) && !checkTrainerHasTrainee(trainerId, userId)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para ver el progreso de este usuario.");
            return "redirect:/trainer/dashboard";
        }

        List<ProgressEntry> progressEntries = progressService.getProgressEntriesByUser(userId);
        Optional<MongoUser> user = mongoUserService.getUserById(userId);

        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", currentUserRole);
        model.addAttribute("progressEntries", progressEntries);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("trainerId", trainerId);

        return "trainer/user-progress";
    }

    @GetMapping("/user/{userId}/routines")
    public String viewUserRoutines(@PathVariable String userId,
                                   HttpSession session,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (!checkTrainerRole(session, redirectAttributes)) {
            return "redirect:/auth/login";
        }

        String trainerId = (String) session.getAttribute("currentUserId");
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        // Verificar que el trainer tenga asignado este usuario (excepto si es admin)
        if (!"ADMIN".equals(currentUserRole) && !checkTrainerHasTrainee(trainerId, userId)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para ver las rutinas de este usuario.");
            return "redirect:/trainer/dashboard";
        }

        Optional<MongoUser> user = mongoUserService.getUserById(userId);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", currentUserRole);
        model.addAttribute("user", user.orElse(null));
        model.addAttribute("routines", routineService.getRoutinesByOwner(userId));
        return "trainer/user-routines";
    }

    @PostMapping("/feedback")
    public String provideFeedback(@RequestParam String userId,
                                 @RequestParam String routineId,
                                 @RequestParam int entryIndex,
                                 @RequestParam String feedback,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!checkTrainerRole(session, redirectAttributes)) {
            return "redirect:/auth/login";
        }

        String trainerId = (String) session.getAttribute("currentUserId");
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        // Verificar que el trainer tenga asignado este usuario (excepto si es admin)
        if (!"ADMIN".equals(currentUserRole) && !checkTrainerHasTrainee(trainerId, userId)) {
            redirectAttributes.addFlashAttribute("error", "No tienes permiso para dar feedback a este usuario.");
            return "redirect:/trainer/dashboard";
        }

        try {
            progressService.addTrainerFeedback(userId, routineId, entryIndex, feedback, trainerId);
            redirectAttributes.addFlashAttribute("success", "Retroalimentación enviada exitosamente");
            return "redirect:/trainer/user/" + userId + "/progress";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al enviar retroalimentación: " + e.getMessage());
            return "redirect:/trainer/user/" + userId + "/progress";
        }
    }
}

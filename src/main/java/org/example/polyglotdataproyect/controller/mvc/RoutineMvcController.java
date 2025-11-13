package org.example.polyglotdataproyect.controller.mvc;

import jakarta.servlet.http.HttpSession;
import org.example.polyglotdataproyect.entities.Routine;
import org.example.polyglotdataproyect.services.ExerciseService;
import org.example.polyglotdataproyect.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/routines")
public class RoutineMvcController {

    @Autowired
    private RoutineService routineService;

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public String viewRoutines(HttpSession session,
                              Model model,
                              @RequestParam(required = false) String ownerId,
                              @RequestParam(required = false) Boolean templates) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));
        model.addAttribute("userId", session.getAttribute("currentUserId"));

        if (templates != null && templates) {
            model.addAttribute("routines", routineService.getTemplateRoutines());
            model.addAttribute("isTemplateView", true);
        } else if (ownerId != null && !ownerId.isEmpty()) {
            model.addAttribute("routines", routineService.getRoutinesByOwner(ownerId));
            model.addAttribute("isTemplateView", false);
        } else {
            // Si no se especifica, mostrar las del usuario actual
            String currentUserId = (String) session.getAttribute("currentUserId");
            if (currentUserId != null) {
                model.addAttribute("routines", routineService.getRoutinesByOwner(currentUserId));
            } else {
                model.addAttribute("routines", routineService.getAllRoutines());
            }
            model.addAttribute("isTemplateView", false);
        }
        return "routines/list";
    }

    @GetMapping("/new")
    public String createRoutineForm(HttpSession session, Model model) {
        model.addAttribute("routine", new Routine());
        model.addAttribute("exercises", exerciseService.getAllExercises());
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));
        return "routines/create";
    }

    @PostMapping("/new")
    public String createRoutine(@ModelAttribute Routine routine,
                               @RequestParam(required = false) String ownerId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        try {
            // Si no se proporciona ownerId, usar el usuario actual de la sesión
            if (ownerId == null || ownerId.isEmpty()) {
                ownerId = (String) session.getAttribute("currentUserId");
            }
            routine.setOwnerId(ownerId);
            routineService.createRoutine(routine);
            redirectAttributes.addFlashAttribute("success", "Rutina creada exitosamente");
            return "redirect:/routines";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear rutina: " + e.getMessage());
            return "redirect:/routines/new";
        }
    }

    @GetMapping("/{id}")
    public String viewRoutine(@PathVariable String id, HttpSession session, Model model) {
        Optional<Routine> routine = routineService.getRoutineById(id);
        if (routine.isPresent()) {
            // Create a map of exercises for easy lookup
            var allExercises = exerciseService.getAllExercises();
            var exerciseMap = new java.util.HashMap<String, org.example.polyglotdataproyect.entities.Exercise>();
            for (var exercise : allExercises) {
                exerciseMap.put(exercise.getId(), exercise);
            }

            model.addAttribute("routine", routine.get());
            model.addAttribute("exerciseMap", exerciseMap);
            model.addAttribute("currentUser", session.getAttribute("currentUser"));
            model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));
            model.addAttribute("userId", session.getAttribute("currentUserId"));
            return "routines/view";
        }
        return "redirect:/routines";
    }

    @GetMapping("/{id}/edit")
    public String editRoutineForm(@PathVariable String id, Model model) {
        Optional<Routine> routine = routineService.getRoutineById(id);
        if (routine.isPresent()) {
            model.addAttribute("routine", routine.get());
            model.addAttribute("exercises", exerciseService.getAllExercises());
            return "routines/edit";
        }
        return "redirect:/routines";
    }

    @PostMapping("/{id}/edit")
    public String updateRoutine(@PathVariable String id,
                               @ModelAttribute Routine routine,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        String currentUserId = (String) session.getAttribute("currentUserId");

        try {
            routineService.updateRoutine(id, routine, currentUserId);
            redirectAttributes.addFlashAttribute("success", "Rutina actualizada exitosamente");
            return "redirect:/routines/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar rutina: " + e.getMessage());
            return "redirect:/routines/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/copy")
    public String copyRoutine(@PathVariable String id,
                             @RequestParam String newOwnerId,
                             RedirectAttributes redirectAttributes) {
        try {
            Routine copiedRoutine = routineService.copyRoutineForUser(id, newOwnerId);
            if (copiedRoutine != null) {
                redirectAttributes.addFlashAttribute("success", "Rutina copiada exitosamente");
                return "redirect:/routines/" + copiedRoutine.getId();
            } else {
                redirectAttributes.addFlashAttribute("error", "No se pudo copiar la rutina");
                return "redirect:/routines";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al copiar rutina: " + e.getMessage());
            return "redirect:/routines";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteRoutine(@PathVariable String id,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        String currentUserId = (String) session.getAttribute("currentUserId");

        try {
            routineService.deleteRoutine(id, currentUserId);
            redirectAttributes.addFlashAttribute("success", "Rutina eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar rutina: " + e.getMessage());
        }
        return "redirect:/routines";
    }

    @GetMapping("/templates")
    public String viewTemplates(HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));
        model.addAttribute("userId", session.getAttribute("currentUserId"));
        model.addAttribute("templateRoutines", routineService.getTemplateRoutines());
        return "routines/templates";
    }
}

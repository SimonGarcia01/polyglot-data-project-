package org.example.polyglotdataproyect.controller.mvc;

import org.example.polyglotdataproyect.entities.ProgressEntry;
import org.example.polyglotdataproyect.services.ProgressService;
import org.example.polyglotdataproyect.services.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/progress")
public class ProgressMvcController {

    @Autowired
    private ProgressService progressService;

    @Autowired
    private RoutineService routineService;

    @GetMapping
    public String viewProgress(Model model, @RequestParam(required = false) String userId) {
        if (userId != null && !userId.isEmpty()) {
            List<ProgressEntry> progressEntries = progressService.getProgressEntriesByUser(userId);
            model.addAttribute("progressEntries", progressEntries);
            model.addAttribute("userId", userId);
        } else {
            model.addAttribute("progressEntries", progressService.getAllProgressEntries());
        }
        return "progress/view";
    }

    @GetMapping("/log")
    public String logProgressForm(Model model,
                                 @RequestParam(required = false) String userId,
                                 @RequestParam(required = false) String routineId) {
        model.addAttribute("userId", userId);
        model.addAttribute("routineId", routineId);
        if (routineId != null && !routineId.isEmpty()) {
            model.addAttribute("routine", routineService.getRoutineById(routineId).orElse(null));
        }
        return "progress/log";
    }

    @PostMapping("/log")
    public String logProgress(@RequestParam String userId,
                            @RequestParam String routineId,
                            @RequestParam Integer completedExercises,
                            @RequestParam Integer effortLevel,
                            RedirectAttributes redirectAttributes) {
        try {
            progressService.addProgressEntry(userId, routineId, completedExercises, effortLevel);
            redirectAttributes.addFlashAttribute("success", "Progreso registrado exitosamente");
            return "redirect:/progress?userId=" + userId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar progreso: " + e.getMessage());
            return "redirect:/progress/log?userId=" + userId + "&routineId=" + routineId;
        }
    }

    @GetMapping("/{id}")
    public String viewProgressDetail(@PathVariable String id, Model model) {
        Optional<ProgressEntry> progressEntry = progressService.getProgressEntryById(id);
        if (progressEntry.isPresent()) {
            model.addAttribute("progressEntry", progressEntry.get());
            model.addAttribute("routine", routineService.getRoutineById(progressEntry.get().getRoutineId()).orElse(null));
            return "progress/detail";
        }
        return "redirect:/progress";
    }

    @PostMapping("/{id}/feedback")
    public String addFeedback(@PathVariable String id,
                             @RequestParam String userId,
                             @RequestParam String routineId,
                             @RequestParam int entryIndex,
                             @RequestParam String feedback,
                             RedirectAttributes redirectAttributes) {
        try {
            progressService.addTrainerFeedback(userId, routineId, entryIndex, feedback);
            redirectAttributes.addFlashAttribute("success", "Retroalimentación agregada exitosamente");
            return "redirect:/progress/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar retroalimentación: " + e.getMessage());
            return "redirect:/progress/" + id;
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteProgress(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            progressService.deleteProgressEntry(id);
            redirectAttributes.addFlashAttribute("success", "Progreso eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar progreso: " + e.getMessage());
        }
        return "redirect:/progress";
    }
}

package org.example.polyglotdataproyect.controller.mvc;

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
    public String viewRoutines(Model model,
                              @RequestParam(required = false) String ownerId,
                              @RequestParam(required = false) Boolean templates) {
        if (templates != null && templates) {
            model.addAttribute("routines", routineService.getTemplateRoutines());
            model.addAttribute("isTemplateView", true);
        } else if (ownerId != null && !ownerId.isEmpty()) {
            model.addAttribute("routines", routineService.getRoutinesByOwner(ownerId));
            model.addAttribute("isTemplateView", false);
        } else {
            model.addAttribute("routines", routineService.getAllRoutines());
            model.addAttribute("isTemplateView", false);
        }
        return "routines/list";
    }

    @GetMapping("/new")
    public String createRoutineForm(Model model) {
        model.addAttribute("routine", new Routine());
        model.addAttribute("exercises", exerciseService.getAllExercises());
        return "routines/create";
    }

    @PostMapping("/new")
    public String createRoutine(@ModelAttribute Routine routine,
                               @RequestParam(required = false) String ownerId,
                               RedirectAttributes redirectAttributes) {
        try {
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
    public String viewRoutine(@PathVariable String id, Model model) {
        Optional<Routine> routine = routineService.getRoutineById(id);
        if (routine.isPresent()) {
            model.addAttribute("routine", routine.get());
            model.addAttribute("allExercises", exerciseService.getAllExercises());
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
                               RedirectAttributes redirectAttributes) {
        try {
            routineService.updateRoutine(id, routine);
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
    public String deleteRoutine(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            routineService.deleteRoutine(id);
            redirectAttributes.addFlashAttribute("success", "Rutina eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar rutina: " + e.getMessage());
        }
        return "redirect:/routines";
    }

    @GetMapping("/templates")
    public String viewTemplates(Model model) {
        model.addAttribute("routines", routineService.getTemplateRoutines());
        return "routines/templates";
    }
}

package org.example.polyglotdataproyect.controller.mvc;

import jakarta.servlet.http.HttpSession;
import org.example.polyglotdataproyect.entities.Exercise;
import org.example.polyglotdataproyect.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/exercises")
public class ExerciseMvcController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public String listExercises(HttpSession session,
                               Model model,
                               @RequestParam(required = false) String type,
                               @RequestParam(required = false) String difficulty,
                               @RequestParam(required = false) String search) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));
        model.addAttribute("userId", session.getAttribute("currentUserId"));

        if (search != null && !search.isEmpty()) {
            model.addAttribute("exercises", exerciseService.searchExercisesByName(search));
        } else if (type != null && !type.isEmpty()) {
            model.addAttribute("exercises", exerciseService.getExercisesByType(type));
        } else if (difficulty != null && !difficulty.isEmpty()) {
            model.addAttribute("exercises", exerciseService.getExercisesByDifficulty(difficulty));
        } else {
            model.addAttribute("exercises", exerciseService.getAllExercises());
        }
        return "exercises/list";
    }

    @GetMapping("/new")
    public String newExerciseForm(Model model) {
        model.addAttribute("exercise", new Exercise());
        return "exercises/create";
    }

    @PostMapping("/new")
    public String createExercise(@ModelAttribute Exercise exercise,
                                @RequestParam(required = false) String createdBy,
                                RedirectAttributes redirectAttributes) {
        try {
            exercise.setCreatedBy(createdBy);
            exerciseService.createExercise(exercise);
            redirectAttributes.addFlashAttribute("success", "Ejercicio creado exitosamente");
            return "redirect:/exercises";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear ejercicio: " + e.getMessage());
            return "redirect:/exercises/new";
        }
    }

    @GetMapping("/{id}")
    public String viewExercise(@PathVariable String id,
                              HttpSession session,
                              Model model) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        Optional<Exercise> exercise = exerciseService.getExerciseById(id);
        if (exercise.isPresent()) {
            model.addAttribute("exercise", exercise.get());
            return "exercises/view";
        }
        return "redirect:/exercises";
    }

    @GetMapping("/{id}/edit")
    public String editExerciseForm(@PathVariable String id,
                                  HttpSession session,
                                  Model model) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        Optional<Exercise> exercise = exerciseService.getExerciseById(id);
        if (exercise.isPresent()) {
            model.addAttribute("exercise", exercise.get());
            return "exercises/edit";
        }
        return "redirect:/exercises";
    }

    @PostMapping("/{id}/edit")
    public String updateExercise(@PathVariable String id,
                                @ModelAttribute Exercise exercise,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        String currentUserId = (String) session.getAttribute("currentUserId");
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        try {
            exerciseService.updateExercise(id, exercise, currentUserId, currentUserRole);
            redirectAttributes.addFlashAttribute("success", "Ejercicio actualizado exitosamente");
            return "redirect:/exercises/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar ejercicio: " + e.getMessage());
            return "redirect:/exercises/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteExercise(@PathVariable String id,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        String currentUserId = (String) session.getAttribute("currentUserId");
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        try {
            exerciseService.deleteExercise(id, currentUserId, currentUserRole);
            redirectAttributes.addFlashAttribute("success", "Ejercicio eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar ejercicio: " + e.getMessage());
        }
        return "redirect:/exercises";
    }
}

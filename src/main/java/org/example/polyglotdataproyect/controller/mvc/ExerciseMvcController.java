package org.example.polyglotdataproyect.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exercises")
public class ExerciseMvcController {

    @GetMapping
    public String listExercises() {
        return "exercises/list";
    }

    @GetMapping("/new")
    public String newExercise() {
        return "exercises/create";
    }
}

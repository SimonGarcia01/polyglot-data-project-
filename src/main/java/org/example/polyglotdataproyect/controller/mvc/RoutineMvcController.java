package org.example.polyglotdataproyect.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routines")
public class RoutineMvcController {

    @GetMapping
    public String viewRoutines() {
        return "routines/view";
    }

    @GetMapping("/new")
    public String createRoutine() {
        return "routines/create";
    }
}

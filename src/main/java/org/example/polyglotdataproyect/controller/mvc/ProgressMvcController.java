package org.example.polyglotdataproyect.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/progress")
public class ProgressMvcController {

    @GetMapping
    public String viewProgress() {
        return "progress/view";
    }

    @GetMapping("/log")
    public String logProgress() {
        return "progress/log";
    }
}

package org.example.polyglotdataproyect.controller.mvc;

import jakarta.servlet.http.HttpSession;
import org.example.polyglotdataproyect.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportMvcController {

    @Autowired
    private ReportService reportService;

    /**
     * Dashboard de reportes - muestra todos los informes disponibles
     */
    @GetMapping
    public String reportsIndex(HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));
        model.addAttribute("userId", session.getAttribute("currentUserId"));

        return "reports/index";
    }

    /**
     * INFORME 1: Activity Heatmap
     * Muestra un mapa de calor de la actividad del usuario en los últimos 365 días
     */
    @GetMapping("/activity-heatmap")
    public String activityHeatmap(HttpSession session,
                                 Model model,
                                 @RequestParam(required = false) String userId) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        // Si no se especifica userId, usar el del usuario actual
        if (userId == null || userId.isEmpty()) {
            userId = (String) session.getAttribute("currentUserId");
        }

        model.addAttribute("userId", userId);

        // Obtener datos del heatmap
        Map<String, Integer> heatmapData = reportService.getActivityHeatmap(userId);
        model.addAttribute("heatmapData", heatmapData);

        return "reports/activity-heatmap";
    }

    /**
     * INFORME 2: Progress by Exercise Type
     * Análisis del progreso agrupado por tipo de ejercicio
     */
    @GetMapping("/progress-by-type")
    public String progressByType(HttpSession session,
                                Model model,
                                @RequestParam(required = false) String userId) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        // Si no se especifica userId, usar el del usuario actual
        if (userId == null || userId.isEmpty()) {
            userId = (String) session.getAttribute("currentUserId");
        }

        model.addAttribute("userId", userId);

        // Obtener estadísticas por tipo
        Map<String, ReportService.ExerciseTypeStats> typeStats = reportService.getProgressByExerciseType(userId);
        model.addAttribute("cardioStats", typeStats.get("cardio"));
        model.addAttribute("fuerzaStats", typeStats.get("fuerza"));
        model.addAttribute("movilidadStats", typeStats.get("movilidad"));

        return "reports/progress-by-type";
    }

    /**
     * INFORME 3: Consistency Report
     * Análisis de consistencia del usuario
     */
    @GetMapping("/consistency")
    public String consistencyReport(HttpSession session,
                                   Model model,
                                   @RequestParam(required = false) String userId) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        // Si no se especifica userId, usar el del usuario actual
        if (userId == null || userId.isEmpty()) {
            userId = (String) session.getAttribute("currentUserId");
        }

        model.addAttribute("userId", userId);

        // Obtener reporte de consistencia
        ReportService.ConsistencyReport consistencyReport = reportService.getConsistencyReport(userId);
        model.addAttribute("report", consistencyReport);

        return "reports/consistency";
    }

    /**
     * INFORME 4: Popular Templates
     * Rutinas template más copiadas
     */
    @GetMapping("/popular-templates")
    public String popularTemplates(HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", session.getAttribute("currentUserRole"));

        // Obtener rutinas más populares
        List<ReportService.RoutinePopularity> popularRoutines = reportService.getMostPopularTemplates();
        model.addAttribute("popularRoutines", popularRoutines);

        return "reports/popular-templates";
    }
}

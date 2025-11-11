package org.example.polyglotdataproyect.controller.mvc;

import jakarta.servlet.http.HttpSession;
import org.example.polyglotdataproyect.entities.TrainerStatistics;
import org.example.polyglotdataproyect.entities.UserStatistics;
import org.example.polyglotdataproyect.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/statistics")
public class StatisticsMvcController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Vista principal de estadísticas - redirige según el rol del usuario
     */
    @GetMapping
    public String statistics(HttpSession session, Model model) {
        String currentUserRole = (String) session.getAttribute("currentUserRole");
        String currentUserId = (String) session.getAttribute("currentUserId");

        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", currentUserRole);
        model.addAttribute("userId", currentUserId);

        if ("TRAINER".equals(currentUserRole)) {
            return "redirect:/statistics/trainer";
        } else if ("ADMIN".equals(currentUserRole)) {
            return "redirect:/statistics/all";
        } else {
            return "redirect:/statistics/user";
        }
    }

    /**
     * Estadísticas de un usuario específico
     */
    @GetMapping("/user")
    public String userStatistics(HttpSession session,
                                 @RequestParam(required = false) Integer year,
                                 Model model) {
        String currentUserId = (String) session.getAttribute("currentUserId");
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        if (currentUserId == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", currentUserRole);
        model.addAttribute("userId", currentUserId);

        // Si no se especifica año, usar el año actual
        int selectedYear = (year != null) ? year : LocalDate.now().getYear();
        model.addAttribute("selectedYear", selectedYear);

        // Obtener estadísticas del usuario
        List<UserStatistics> statistics = statisticsService.getUserStatisticsByYear(currentUserId, selectedYear);
        model.addAttribute("statistics", statistics);

        // Calcular totales del año
        int totalRoutines = statistics.stream()
                .mapToInt(UserStatistics::getRoutinesStarted)
                .sum();
        int totalProgressLogs = statistics.stream()
                .mapToInt(UserStatistics::getProgressLogsCount)
                .sum();

        model.addAttribute("totalRoutines", totalRoutines);
        model.addAttribute("totalProgressLogs", totalProgressLogs);

        return "statistics/user";
    }

    /**
     * Estadísticas de un entrenador específico
     */
    @GetMapping("/trainer")
    public String trainerStatistics(HttpSession session,
                                   @RequestParam(required = false) Integer year,
                                   Model model) {
        String currentUserId = (String) session.getAttribute("currentUserId");
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        if (currentUserId == null || !"TRAINER".equals(currentUserRole)) {
            return "redirect:/auth/login";
        }

        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", currentUserRole);
        model.addAttribute("userId", currentUserId);

        // Si no se especifica año, usar el año actual
        int selectedYear = (year != null) ? year : LocalDate.now().getYear();
        model.addAttribute("selectedYear", selectedYear);

        // Obtener estadísticas del entrenador
        List<TrainerStatistics> statistics = statisticsService.getTrainerStatisticsByYear(currentUserId, selectedYear);
        model.addAttribute("statistics", statistics);

        // Calcular totales del año
        int totalAssignments = statistics.stream()
                .mapToInt(TrainerStatistics::getNewAssignmentsCount)
                .sum();
        int totalFeedbacks = statistics.stream()
                .mapToInt(TrainerStatistics::getFeedbacksGivenCount)
                .sum();

        model.addAttribute("totalAssignments", totalAssignments);
        model.addAttribute("totalFeedbacks", totalFeedbacks);

        return "statistics/trainer";
    }

    /**
     * Vista de todas las estadísticas (solo para administradores)
     */
    @GetMapping("/all")
    public String allStatistics(HttpSession session,
                               @RequestParam(required = false) Integer month,
                               @RequestParam(required = false) Integer year,
                               Model model) {
        String currentUserRole = (String) session.getAttribute("currentUserRole");

        if (!"ADMIN".equals(currentUserRole)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("currentUserRole", currentUserRole);
        model.addAttribute("userId", session.getAttribute("currentUserId"));

        LocalDate now = LocalDate.now();
        int selectedMonth = (month != null) ? month : now.getMonthValue();
        int selectedYear = (year != null) ? year : now.getYear();

        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("selectedYear", selectedYear);

        // Obtener todas las estadísticas del mes
        List<UserStatistics> userStats = statisticsService.getAllUserStatisticsForMonth(selectedMonth, selectedYear);
        List<TrainerStatistics> trainerStats = statisticsService.getAllTrainerStatisticsForMonth(selectedMonth, selectedYear);

        model.addAttribute("userStatistics", userStats);
        model.addAttribute("trainerStatistics", trainerStats);

        return "statistics/all";
    }
}

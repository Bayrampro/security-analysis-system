/**
 * Контроллер dashboard: передает агрегированные метрики и данные графиков в представление.
 */

package com.byprogger.security_analysis_system.controller;

import com.byprogger.security_analysis_system.dto.DashboardMetricsDto;
import com.byprogger.security_analysis_system.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        DashboardMetricsDto metrics = dashboardService.buildMetrics();
        model.addAttribute("metrics", metrics);
        return "dashboard";
    }
}

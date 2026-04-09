/**
 * Контроллер отчетов: фильтрация по датам и вывод сводной аналитики по рискам.
 */

package com.byprogger.security_analysis_system.controller;

import com.byprogger.security_analysis_system.dto.ReportFilterDto;
import com.byprogger.security_analysis_system.dto.ThreatViewDto;
import com.byprogger.security_analysis_system.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public String reports(@ModelAttribute("filter") ReportFilterDto filter, Model model) {
        List<ThreatViewDto> reportRows = reportService.getThreatReport(filter);
        Map<String, Long> summary = reportService.buildRiskSummary(reportRows);

        model.addAttribute("rows", reportRows);
        model.addAttribute("summary", summary);
        return "reports";
    }
}

/**
 * Контроллер угроз: отображение реестра угроз, создание, редактирование и удаление.
 */

package com.byprogger.security_analysis_system.controller;

import com.byprogger.security_analysis_system.dto.ThreatDto;
import com.byprogger.security_analysis_system.service.AssetService;
import com.byprogger.security_analysis_system.service.ThreatService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ThreatController {

    private final ThreatService threatService;
    private final AssetService assetService;

    public ThreatController(ThreatService threatService, AssetService assetService) {
        this.threatService = threatService;
        this.assetService = assetService;
    }

    @GetMapping("/threats")
    public String threatsPage(Model model) {
        if (!model.containsAttribute("threatForm")) {
            model.addAttribute("threatForm", new ThreatDto());
        }
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("threats", threatService.getAllThreats());
        model.addAttribute("editMode", false);
        return "threats";
    }

    @GetMapping("/threats/{id}/edit")
    public String editThreatPage(@PathVariable Long id, Model model) {
        model.addAttribute("threatForm", threatService.getThreatDtoById(id));
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("threats", threatService.getAllThreats());
        model.addAttribute("editMode", true);
        return "threats";
    }

    @PostMapping("/threats")
    public String createThreat(
            @Valid @ModelAttribute("threatForm") ThreatDto threatForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("assets", assetService.getAllAssets());
            model.addAttribute("threats", threatService.getAllThreats());
            model.addAttribute("editMode", false);
            return "threats";
        }

        threatService.createThreat(threatForm);
        redirectAttributes.addFlashAttribute("successMessage", "Угроза успешно создана");
        return "redirect:/threats";
    }

    @PostMapping("/threats/{id}")
    public String updateThreat(
            @PathVariable Long id,
            @Valid @ModelAttribute("threatForm") ThreatDto threatForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("assets", assetService.getAllAssets());
            model.addAttribute("threats", threatService.getAllThreats());
            model.addAttribute("editMode", true);
            return "threats";
        }

        threatService.updateThreat(id, threatForm);
        redirectAttributes.addFlashAttribute("successMessage", "Угроза обновлена");
        return "redirect:/threats";
    }

    @PostMapping("/threats/{id}/delete")
    public String deleteThreat(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        threatService.deleteThreat(id);
        redirectAttributes.addFlashAttribute("successMessage", "Угроза удалена");
        return "redirect:/threats";
    }
}

/**
 * Контроллер объектов защиты: отображение списка и CRUD-операции через web-формы.
 */

package com.byprogger.security_analysis_system.controller;

import com.byprogger.security_analysis_system.dto.AssetDto;
import com.byprogger.security_analysis_system.service.AssetService;
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
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/assets")
    public String assetsPage(Model model) {
        if (!model.containsAttribute("assetForm")) {
            model.addAttribute("assetForm", new AssetDto());
        }
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("editMode", false);
        return "assets";
    }

    @GetMapping("/assets/{id}/edit")
    public String editAssetPage(@PathVariable Long id, Model model) {
        model.addAttribute("assetForm", assetService.getAssetDtoById(id));
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("editMode", true);
        return "assets";
    }

    @PostMapping("/assets")
    public String createAsset(
            @Valid @ModelAttribute("assetForm") AssetDto assetForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("assets", assetService.getAllAssets());
            model.addAttribute("editMode", false);
            return "assets";
        }

        assetService.createAsset(assetForm);
        redirectAttributes.addFlashAttribute("successMessage", "Объект защиты успешно создан");
        return "redirect:/assets";
    }

    @PostMapping("/assets/{id}")
    public String updateAsset(
            @PathVariable Long id,
            @Valid @ModelAttribute("assetForm") AssetDto assetForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("assets", assetService.getAllAssets());
            model.addAttribute("editMode", true);
            return "assets";
        }

        assetService.updateAsset(id, assetForm);
        redirectAttributes.addFlashAttribute("successMessage", "Объект защиты обновлен");
        return "redirect:/assets";
    }

    @PostMapping("/assets/{id}/delete")
    public String deleteAsset(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        assetService.deleteAsset(id);
        redirectAttributes.addFlashAttribute("successMessage", "Объект защиты удален");
        return "redirect:/assets";
    }
}

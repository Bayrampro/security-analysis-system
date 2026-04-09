/**
 * REST API-контроллер угроз: методы получения списка, создания и удаления угроз.
 */

package com.byprogger.security_analysis_system.controller;

import com.byprogger.security_analysis_system.dto.ThreatDto;
import com.byprogger.security_analysis_system.dto.ThreatViewDto;
import com.byprogger.security_analysis_system.service.ThreatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// REST API для угроз, как указано в ТЗ.
@RestController
@RequestMapping("/api/threats")
public class ThreatApiController {

    private final ThreatService threatService;

    public ThreatApiController(ThreatService threatService) {
        this.threatService = threatService;
    }

    @GetMapping
    public List<ThreatViewDto> getAllThreats() {
        return threatService.getAllThreats();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ThreatViewDto createThreat(@Valid @RequestBody ThreatDto threatDto) {
        Long id = threatService.createThreat(threatDto).getId();
        return threatService.getThreatById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThreat(@PathVariable Long id) {
        threatService.deleteThreat(id);
    }
}

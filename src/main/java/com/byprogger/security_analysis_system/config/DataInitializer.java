/**
 * Инициализация демонстрационных данных: стартовые пользователи, объекты защиты и угрозы.
 */

package com.byprogger.security_analysis_system.config;

import com.byprogger.security_analysis_system.dto.AssetDto;
import com.byprogger.security_analysis_system.dto.ThreatDto;
import com.byprogger.security_analysis_system.entity.Role;
import com.byprogger.security_analysis_system.entity.Threat;
import com.byprogger.security_analysis_system.repository.AssetRepository;
import com.byprogger.security_analysis_system.repository.ThreatRepository;
import com.byprogger.security_analysis_system.service.AssetService;
import com.byprogger.security_analysis_system.service.ThreatService;
import com.byprogger.security_analysis_system.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadDemoData(
            UserService userService,
            AssetService assetService,
            AssetRepository assetRepository,
            ThreatService threatService,
            ThreatRepository threatRepository
    ) {
        return args -> {
            // Стартовые учетные записи для демонстрации ролей.
            userService.createIfMissing("admin", "admin123", Role.ADMIN);
            userService.createIfMissing("analyst", "analyst123", Role.ANALYST);

            if (assetRepository.count() == 0) {
                AssetDto crm = new AssetDto();
                crm.setName("CRM-система");
                crm.setDescription("Центральная система работы с клиентскими данными");
                assetService.createAsset(crm);

                AssetDto db = new AssetDto();
                db.setName("База персональных данных");
                db.setDescription("Сервер БД с ограниченным доступом");
                assetService.createAsset(db);
            }

            if (threatRepository.count() == 0) {
                Long crmId = assetRepository.findByName("CRM-система").orElseThrow().getId();
                Long dbId = assetRepository.findByName("База персональных данных").orElseThrow().getId();

                ThreatDto t1 = new ThreatDto();
                t1.setName("Несанкционированный доступ");
                t1.setDescription("Попытка входа в систему с компрометированными учетными данными");
                t1.setProbability(0.8);
                t1.setImpact(9.0);
                t1.setAssetId(dbId);
                threatService.createThreat(t1);

                ThreatDto t2 = new ThreatDto();
                t2.setName("Утечка данных через API");
                t2.setDescription("Уязвимость endpoint-а без ограничения частоты запросов");
                t2.setProbability(0.5);
                t2.setImpact(7.0);
                t2.setAssetId(crmId);
                threatService.createThreat(t2);

                ThreatDto t3 = new ThreatDto();
                t3.setName("Ошибочная конфигурация прав");
                t3.setDescription("Избыточные роли у внутренних пользователей");
                t3.setProbability(0.3);
                t3.setImpact(4.0);
                t3.setAssetId(crmId);
                threatService.createThreat(t3);

                // Небольшая динамика по датам для графика dashboard.
                List<Threat> threats = threatRepository.findAll();
                if (threats.size() >= 3) {
                    threats.get(0).setCreatedAt(LocalDateTime.now().minusDays(3));
                    threats.get(1).setCreatedAt(LocalDateTime.now().minusDays(1));
                    threats.get(2).setCreatedAt(LocalDateTime.now());
                    threatRepository.saveAll(threats);
                }
            }
        };
    }
}

package com.Gdz.stats.controller;

import com.Gdz.stats.dto.SteamStatsDto;
import com.Gdz.stats.service.SteamStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final SteamStatsService steamStatsService;

    @GetMapping("/{telegramId}")
    public ResponseEntity<SteamStatsDto> getStats(@PathVariable Long telegramId) {
        SteamStatsDto stats = steamStatsService.getStatsForTelegramUser(telegramId);
        if (stats == null) {
            return ResponseEntity.notFound().build(); // Бот получит 404 и поймет, что аккаунт не привязан
        }
        return ResponseEntity.ok(stats);
    }
}
package com.Gdz.stats.service;

import com.Gdz.stats.dto.SteamStatsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SteamStatsService {

    private final AuthIntegrationService authIntegrationService;
    private final RestTemplate restTemplate;

    @Value("${steam.api.key}")
    private String steamApiKey;

    public SteamStatsDto getStatsForTelegramUser(Long telegramId) {
        // 1. Узнаем Steam ID пользователя через микросервис авторизации
        String steamId = authIntegrationService.getSteamIdByTelegramId(telegramId);
        
        if (steamId == null || steamId.isBlank()) {
            return null; // Возвращаем null, контроллер отдаст 404, а бот напишет "Аккаунт не привязан"
        }

        // 2. Идем в реальный Steam Web API по полученному steamId
        return fetchRealSteamStats(steamId);
    }

    private SteamStatsDto fetchRealSteamStats(String steamId) {
        // TODO: Здесь должен быть реальный HTTP вызов к api.steampowered.com
        // Например: http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=KEY&steamid=ID&format=json
        
        log.info("Запрашиваем статистику Steam для аккаунта: {}", steamId);

        // ВРЕМЕННАЯ ЗАГЛУШКА (Mock) для проверки работы:
        return SteamStatsDto.builder()
                .nickname("Player_" + steamId.substring(Math.max(0, steamId.length() - 4))) // берем последние 4 цифры
                .gamesCount(142)
                .hoursTotal(3450)
                .topGame("Counter-Strike 2")
                .build();
    }
}
package com.Gdz.bot.service;

import com.Gdz.bot.dto.SteamStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SteamStatsService {

    private final RestTemplate restTemplate;
    private final DeepSeekService deepSeekService; // Подключаем ИИ
    
    @Value("${steam.service.url}")
    private String steamUrl;

    public SteamStatsDto getStats(Long telegramId) {
        // 1. Предположим, здесь мы получаем данные из реального Steam или БД
        // В продакшене тут будет вызов: restTemplate.getForObject(steamUrl + "/raw/" + telegramId, ...)
        
        // Для примера создадим "сырые" данные
        String nickname = "Gamer1337";
        int games = 120;
        int hours = 2500;
        String topGame = "Dota 2";

        // 2. Отправляем эти данные в DeepSeek для анализа
        String aiAnalysis = deepSeekService.analyzeStats(nickname, games, hours, topGame);

        // 3. Возвращаем полный объект
        return SteamStatsDto.builder()
                .nickname(nickname)
                .gamesCount(games)
                .hoursTotal(hours)
                .topGame(topGame)
                .deepSeekAnalysis(aiAnalysis)
                .build();
    }
}

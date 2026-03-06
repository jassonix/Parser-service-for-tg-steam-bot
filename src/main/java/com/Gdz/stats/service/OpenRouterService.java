package com.Gdz.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenRouterService {

    private final RestTemplate restTemplate;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.api.url}")
    private String apiUrl;

    @Value("${openrouter.api.model}")
    private String model;

    public String analyzeStats(String nickname, int games, int hours, String topGame) {
        String systemPrompt = "Ты — игровой аналитик. Пиши кратко и с юмором.";
        String userPrompt = String.format("Игрок %s: %d игр, %d ч. в сумме, топ: %s.", 
                                           nickname, games, hours, topGame);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        // Обязательные заголовки для OpenRouter
        headers.set("HTTP-Referer", "https://github.com/Gdz/steam-bot"); 
        headers.set("X-Title", "Steam Stats Bot");

        Map<String, Object> body = Map.of(
            "model", model, // Используем модель из конфига
            "messages", List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", userPrompt)
            )
        );

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            // OpenRouter возвращает структуру точно такую же, как DeepSeek/OpenAI
            var response = restTemplate.postForObject(apiUrl, entity, Map.class);
            
            List choices = (List) response.get("choices");
            Map choice = (Map) choices.get(0);
            Map message = (Map) choice.get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            return "Нейросеть задумалась о смысле жизни, вот твои цифры.";
        }
    }
}

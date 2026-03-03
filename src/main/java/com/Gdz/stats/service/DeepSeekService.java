package com.Gdz.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeepSeekService {

    private final RestTemplate restTemplate;

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    public String analyzeStats(String nickname, int games, int hours, String topGame) {
        String prompt = String.format(
            "Проанализируй игровую статистику пользователя Steam. Ник: %s, Всего игр: %d, Общее время: %d часов, Любимая игра: %s. " +
            "Дай краткий, остроумный комментарий о его геймерском стиле.",
            nickname, games, hours, topGame
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Формируем тело запроса в формате OpenAI/DeepSeek
        Map<String, Object> requestBody = Map.of(
            "model", "deepseek-chat",
            "messages", List.of(
                Map.of("role", "system", "content", "Ты — эксперт по видеоиграм с отличным чувством юмора."),
                Map.of("role", "user", "content", prompt)
            )
        );

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            
            // Извлекаем текст из ответа (упрощенно)
            List choices = (List) response.getBody().get("choices");
            Map message = (Map) ((Map) choices.get(0)).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            return "DeepSeek временно недоступен, но цифры говорят сами за себя!";
        }
    }
}

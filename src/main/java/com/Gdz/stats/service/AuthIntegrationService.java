package com.Gdz.stats.service;

import com.Gdz.stats.dto.SteamIdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${auth.service.url}")
    private String authUrl;

    public String getSteamIdByTelegramId(Long telegramId) {
        String url = authUrl + "/users/" + telegramId + "/steam-id";
        try {
            SteamIdResponse response = restTemplate.getForObject(url, SteamIdResponse.class);
            return response != null ? response.getSteamId() : null;
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Пользователь {} не найден в сервисе привязки", telegramId);
            return null; // Аккаунт не привязан
        } catch (Exception e) {
            log.error("Ошибка при обращении к Auth Service", e);
            throw new RuntimeException("Auth service unavailable");
        }
    }
}
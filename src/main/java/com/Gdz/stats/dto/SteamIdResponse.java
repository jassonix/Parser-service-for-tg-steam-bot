package com.Gdz.stats.dto;

import lombok.Data;

@Data
public class SteamIdResponse {
    private String steamId; // Полученный Steam ID или ссылка от Auth Service
}
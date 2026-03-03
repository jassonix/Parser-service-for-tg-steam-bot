package com.Gdz.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SteamStatsDto {
    private String nickname;
    private int gamesCount;
    private int hoursTotal;
    private String topGame;
    private String deepSeekAnalysis; // Поле для ответа от ИИ
}

package com.Gdz.stats.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SteamStatsDto {
    private String nickname;
    private int gamesCount;
    private int hoursTotal;
    private String topGame;
}
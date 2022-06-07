package com.paf.exercise.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PlayerDto {
    private Long playerId;
    private String playerName;

    private Long tournamentId;
}

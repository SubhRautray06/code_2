package com.paf.exercise.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class TournamentDto {

    private Long id;
    private String tournamentName;
    private int rewardAmount;
    private List<PlayerDto> players;

}

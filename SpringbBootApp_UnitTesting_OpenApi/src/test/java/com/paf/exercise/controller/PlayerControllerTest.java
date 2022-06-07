package com.paf.exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paf.exercise.dto.PlayerDto;
import com.paf.exercise.dto.TournamentDto;
import com.paf.exercise.model.Player;
import com.paf.exercise.model.Tournament;
import com.paf.exercise.service.PlayerService;
import com.paf.exercise.service.TournamentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerService playerService;
    ArrayList<Tournament> tournamentList;

    @Autowired
    private ObjectMapper objectMapper;

    Player player = null;
    PlayerDto playerDto =null;
    List<Player> playerList;
    @BeforeEach
    void setUp() {
        playerList = new ArrayList<>();
        player = new Player();
        playerDto = new PlayerDto();
        player.setPlayerId(1l);
        player.setPlayerName("Gandalf");
        player.setTournamentId(1l);
        playerDto.setPlayerName("Gandalf");
        playerDto.setTournamentId(1l);
        playerList.add(player);
    }

    @Test
    void addPlayerTest() throws Exception {
        when(playerService.addPlayer(ArgumentMatchers.any())).thenReturn(player);
        this.mockMvc.perform(post("/api/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDto)))
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    void getAllPlayers() throws Exception {
        when(playerService.getAllPlayers(ArgumentMatchers.any())).thenReturn(playerList);
        this.mockMvc.perform(get("/api/v1/players/{tournamentId}",1l    )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tournamentList)))
                .andExpect(status().isOk()).andDo(print());
    }


    @Test
    void testDeletePlayer() throws Exception {
        Mockito.doNothing().when(playerService).deletePlayerByTournament(ArgumentMatchers.any(),ArgumentMatchers.anyLong());

        mockMvc.perform(delete("/api/v1/players/{tournamentId}/{playerId}", 1l,1l))
                .andExpect(status().isAccepted());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

}

package com.paf.exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paf.exercise.dto.TournamentDto;
import com.paf.exercise.model.Tournament;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TournamentController.class)
public class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TournamentService tournamentService;
    ArrayList<Tournament> tournamentList;

    @Autowired
    private ObjectMapper objectMapper;

    Tournament tournament = null;
    TournamentDto dto = null;

    @BeforeEach
    void setUp() {

        tournamentList = new ArrayList<>();
        tournament = new Tournament();
        tournament.setTournamentId(1l);
        tournament.setTournamentName("HOCKEY");
        tournamentList.add(tournament);
        dto = new TournamentDto();
        dto.setTournamentName("HOCKEY");
    }


    @Test
    void getAllTournamentsTest() throws Exception {
        when(tournamentService.getAllTournaments()).thenReturn(tournamentList);
        this.mockMvc.perform(get("/api/v1/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tournamentList)))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void addTournamentTest() throws Exception {
        when(tournamentService.addTournament(ArgumentMatchers.any())).thenReturn(tournament);
        this.mockMvc.perform(post("/api/v1/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    void testGetTournamentById() throws Exception {
        when(tournamentService.getTournamentById(ArgumentMatchers.any())).thenReturn(tournament);
        mockMvc.perform(get("/api/v1/tournaments/{tournamentId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTournament() throws Exception {
        Mockito.doNothing().when(tournamentService).deleteTournamentById(ArgumentMatchers.any());

        mockMvc.perform(delete("/api/v1/tournaments/{tournamentId}", 1))
                .andExpect(status().isAccepted());
    }

    @Test
    void testUpdateTournament() throws Exception {

        when(tournamentService.updateTournament(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(tournament);
        mockMvc.perform(put("/api/v1/tournaments/{tournamentId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tournament)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tournamentName").value("HOCKEY"));
    }


    @TestConfiguration
    static class TestConfig {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

}

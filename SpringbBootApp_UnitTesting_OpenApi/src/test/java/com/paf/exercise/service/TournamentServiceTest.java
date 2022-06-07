package com.paf.exercise.service;

import com.paf.exercise.dto.TournamentDto;
import com.paf.exercise.exception.ResourceNotFoundException;
import com.paf.exercise.model.Tournament;
import com.paf.exercise.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TournamentServiceTest {

    @Autowired
    private TournamentService service;
    Tournament tournament = null;
    @MockBean
    private TournamentRepository tournamentRepository;
    @BeforeEach
    void setUp() {

        tournament = new Tournament();
        tournament.setTournamentId(1l);
        tournament.setTournamentName("HOCKEY");
        tournament.setRewardAmount(1000);
    }
    @Test
    public void getAllTournamentTest() {
        when(tournamentRepository.findAll()).thenReturn(Stream.of(tournament).collect(Collectors.toList()));
        assertEquals(1, service.getAllTournaments().size());
    }

    @Test
    public void getTournamentByIdTest(){
        when(tournamentRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(tournament));
        Tournament tourn = service.getTournamentById(1l);
        assertSame(tournament,tourn);

    }
    @Test
    public void addTournamentTest(){
        when(tournamentRepository.save(ArgumentMatchers.any())).thenReturn(tournament);
        Tournament tourn = service.addTournament(tournament);
        assertSame(tournament,tourn);

    }
    @Test
    public void deleteTournamentTest(){
        when(tournamentRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(tournament));
        service.deleteTournamentById(1l);

    }
    @Test
    public void updateTournamentTest(){
        when(tournamentRepository.save(ArgumentMatchers.any())).thenReturn(tournament);
        when(tournamentRepository.checkByTournamentName(ArgumentMatchers.any())).thenReturn(Boolean.FALSE);
        when(tournamentRepository.findByTournamentId(ArgumentMatchers.any())).thenReturn(Optional.of(tournament));
        Tournament tourn = service.updateTournament(tournament,1L);
        assertEquals(1000,tourn.getRewardAmount());

    }
    @Test
    public void testGetElementByIdException() {
        when(tournamentRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getTournamentById(1l));
    }
}

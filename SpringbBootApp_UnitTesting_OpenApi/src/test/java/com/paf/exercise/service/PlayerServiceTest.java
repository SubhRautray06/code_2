package com.paf.exercise.service;

import com.paf.exercise.model.Player;
import com.paf.exercise.model.Tournament;
import com.paf.exercise.repository.PlayerRepository;
import com.paf.exercise.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PlayerServiceTest {

    @Autowired
    private PlayerService service;
    Player player = null;
    @MockBean
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {

        player = new Player();
        player.setPlayerId(1l);
        player.setPlayerName("Gandalf");
        player.setTournamentId(1L);


    }

    @Test
    public void getAllPlayersTest() {
        when(playerRepository.findByTournamentId(ArgumentMatchers.any())).thenReturn(Optional.of(Stream.of(player).collect(Collectors.toList())));
        assertEquals(1, service.getAllPlayers(1L).size());
    }


    @Test
    public void addPlayerTest(){
        when(playerRepository.save(ArgumentMatchers.any())).thenReturn(player);
        Player playerRes = service.addPlayer(player);
        assertSame(playerRes, player);

    }
    @Test
    public void deletePlayerTest(){
        when(playerRepository.findByTournamentIdAndPlayerId(ArgumentMatchers.anyLong(),ArgumentMatchers.anyLong())).thenReturn(Optional.of(player));
        service.deletePlayerByTournament(1l,1L);
        verify(playerRepository, times(1)).delete(player);

    }

}

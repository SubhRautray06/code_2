package com.paf.exercise.service;

import com.paf.exercise.model.Player;

import java.util.List;


public interface PlayerService {
    List<Player> getAllPlayers(Long tournamentId);

    Player addPlayer(Player playerReq);

    void deletePlayerByTournament(Long tournamentId, Long playerId);
}

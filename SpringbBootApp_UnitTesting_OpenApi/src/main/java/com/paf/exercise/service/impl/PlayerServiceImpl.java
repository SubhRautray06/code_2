package com.paf.exercise.service.impl;

import com.paf.exercise.constant.CommonConstants;
import com.paf.exercise.exception.ResourceNotFoundException;
import com.paf.exercise.model.Player;

import com.paf.exercise.repository.PlayerRepository;
import com.paf.exercise.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Gets all player in a tournament.
     * @param tournamentId
     * @return List of players.
     */
    @Override
    public List<Player> getAllPlayers(Long tournamentId) {
        log.info("Fetching player details for tournament id:"+ tournamentId);
        Optional<List<Player>> players = playerRepository.findByTournamentId(tournamentId);
        if(players.isEmpty()) {
            throw new ResourceNotFoundException("Error retreiving player details for tournament id: "+tournamentId, CommonConstants.NOTAVAILABLE);
        }
        return players.get();



    }

    /**
     * Adds a player
     * @param playerReq
     * @return Player
     */
    @Override
    public Player addPlayer(Player playerReq) {
        return playerRepository.save(playerReq);
    }

    /**
     * Delete a player
     * @param tournamentId
     * @param playerId
     */
    @Override
    public void deletePlayerByTournament(Long tournamentId, Long playerId) {
        log.info("Deleting player "+playerId+" from  tournament id: "+ tournamentId);
        Player playerResult = playerRepository.findByTournamentIdAndPlayerId(tournamentId,playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Error retreiving player details for tournament id: "+tournamentId, CommonConstants.NOTAVAILABLE));

        playerRepository.delete(playerResult);
    }
}

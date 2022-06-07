package com.paf.exercise.repository;

import com.paf.exercise.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    Optional<List<Player>> findByTournamentId(Long tournamentId);

    Optional<Player> findByTournamentIdAndPlayerId(Long tournamentId, Long playerId);
}

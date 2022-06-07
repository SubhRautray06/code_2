package com.paf.exercise.repository;

import com.paf.exercise.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestH2TournamentRepository extends JpaRepository<Tournament,Long> {

    Optional<Tournament> findByTournamentName(String tournamentName);
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tournament t WHERE t.tournamentName = :tournamentName")
    boolean checkByTournamentName(@Param("tournamentName")String tournamentName);

    Optional<Tournament> findByTournamentId(long tournamentId);
}

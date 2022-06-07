package com.paf.exercise.service;

import com.paf.exercise.model.Tournament;

import java.util.List;


public interface TournamentService {
    List<Tournament> getAllTournaments();

    Tournament getTournamentById(Long tournamentId);

    Tournament addTournament(Tournament tournament);

    void deleteTournamentById(Long tournamentId);

    Tournament updateTournament(Tournament tournament, Long tournamentId);
}

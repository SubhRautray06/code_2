package com.paf.exercise.service.impl;

import com.paf.exercise.constant.CommonConstants;
import com.paf.exercise.exception.ResourceAvailableException;
import com.paf.exercise.exception.ResourceNotFoundException;
import com.paf.exercise.model.Tournament;
import com.paf.exercise.repository.TournamentRepository;
import com.paf.exercise.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    /**
     * Fetching all tournaments details.
     * @return List of tournament
     */
    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    /**
     * Fetching a tournament by given tournament id.
     * @param tournamentId
     * @return Tournament
     */
    @Override
    public Tournament getTournamentById(Long tournamentId) {
        log.info("Fetching tornament details for tournament id:"+ tournamentId);
            Optional<Tournament> tournamentresult = tournamentRepository.findById(tournamentId);
        if (!tournamentresult.isPresent()) {
            log.info("Error while retrieving tournament details for id:"+ tournamentId);
            throw new ResourceNotFoundException(CommonConstants.ERROR_RETREIVING+tournamentId, CommonConstants.NOTAVAILABLE);
        }
        return tournamentresult.get();
    }


    /**
     * Adding a new tournament.
     * @param tournament tournament details
     * @return tournament response
     */
    @Override
    public Tournament addTournament(Tournament tournament) {
    if(tournamentRepository.checkByTournamentName(tournament.getTournamentName())){
        log.info("Tournament with same name already present"+tournament.getTournamentName());
        throw new ResourceAvailableException("Error retreiving tournament details.", CommonConstants.AVAILABLE);

    }
        return tournamentRepository.save(tournament);
    }

    /**
     * Deleting a tournament.
     * @param tournamentId
     * @return void
     */
    @Override
    public void deleteTournamentById(Long tournamentId) {
        Tournament tournamentResult = tournamentRepository.findById(tournamentId)

                .orElseThrow(() -> {
                    log.info("Error deleting tournament of id:"+ tournamentId);
                    throw new ResourceNotFoundException(CommonConstants.ERROR_RETREIVING + tournamentId, CommonConstants.NOTAVAILABLE);
                });

        tournamentRepository.delete(tournamentResult);
    }

    /**
     * Updates the tournament.
     * @param tournamentReq
     * @param tournamentId
     * @return Tournament
     */
    @Override
    public Tournament updateTournament(Tournament tournamentReq, Long tournamentId) {

        Tournament tournamentResult = tournamentRepository.findByTournamentId(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException(CommonConstants.ERROR_RETREIVING+tournamentId, CommonConstants.NOTAVAILABLE));
        tournamentResult.setTournamentName(tournamentReq.getTournamentName());
        tournamentResult.setRewardAmount(tournamentReq.getRewardAmount());

        return tournamentRepository.save(tournamentResult);
    }


}

package com.paf.exercise.controller;


import com.paf.exercise.dto.TournamentDto;
import com.paf.exercise.model.Tournament;
import com.paf.exercise.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private ModelMapper modelMapper;


    @Operation(summary = "The api is to fetch all the tournaments stored in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched All the tournaments from database",
                    content = {@Content(mediaType = "application/json")}),

    })
    @GetMapping("/tournaments")
    public ResponseEntity<List<TournamentDto>> getAllTournaments(){

        List<TournamentDto> tournamentDtos = tournamentService.getAllTournaments().stream().map(tournament -> modelMapper.map(tournament, TournamentDto.class))
                .collect(Collectors.toList());
        log.info("Fetching all tournament");
        return new ResponseEntity<>(tournamentDtos, HttpStatus.OK);
    }

    @Operation(summary = "The api is to fetch the particular tournament details with respect to tournament Id.")
    @GetMapping("/tournaments/{tournamentId}")
        public ResponseEntity<TournamentDto> getTournamentById(@PathVariable("tournamentId") Long tournamentId){
        log.info("Fetching tournament details for "+tournamentId);
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        TournamentDto tournamentDto = modelMapper.map(tournament, TournamentDto.class);

        return new ResponseEntity<>(tournamentDto, HttpStatus.OK);
    }

    @Operation(summary = "The api is to add tournament.")
    @PostMapping("/tournaments")
    public ResponseEntity<TournamentDto> addTournament(@RequestBody TournamentDto tournamentDto){
        log.info("Creating tournament");
        Tournament tournamentReq = modelMapper.map(tournamentDto, Tournament.class);
        Tournament tournamentSaved = tournamentService.addTournament(tournamentReq);
        TournamentDto tournamentRes = modelMapper.map(tournamentSaved,TournamentDto.class);

        return new ResponseEntity<>(tournamentRes, HttpStatus.CREATED);

    }

    @Operation(summary = "This api is to delete tournament.")
    @DeleteMapping("/tournaments/{tournamentId}")
    public ResponseEntity<Void> deleteTournamentById(@PathVariable("tournamentId") Long tournamentId){

        tournamentService.deleteTournamentById(tournamentId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(summary = "The api is to update the tournament with respect to tournament name.")
    @PutMapping("/tournaments/{tournamentId}")
    public ResponseEntity<TournamentDto> updateTournament(@RequestBody TournamentDto tournamentDto, @PathVariable("tournamentId") Long tournamentId){

        Tournament tournament = modelMapper.map(tournamentDto,Tournament.class);
        Tournament tournamentUpdated = tournamentService.updateTournament(tournament,tournamentId);
        TournamentDto tournamentDtoResponse = modelMapper.map(tournamentUpdated, TournamentDto.class);

        return new ResponseEntity<>(tournamentDtoResponse, HttpStatus.CREATED);

    }
}


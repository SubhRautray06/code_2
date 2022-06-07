package com.paf.exercise.controller;


import com.paf.exercise.dto.PlayerDto;
    import com.paf.exercise.model.Player;
import com.paf.exercise.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "The api is to fetch all the players with respect to tournament Id.")
    @GetMapping("/players/{tournamentId}")
    public ResponseEntity<List<PlayerDto>> getAllPlayers(@PathVariable("tournamentId") Long tournamentId){

        List<PlayerDto> playerDtos = playerService.getAllPlayers(tournamentId).stream().map(player -> modelMapper.map(player, PlayerDto.class))
                .collect(Collectors.toList());


        return new ResponseEntity<>(playerDtos, HttpStatus.OK);
    }

    @Operation(summary = "The api is to add a player.")
    @PostMapping("/players")
    public ResponseEntity<PlayerDto> addPlayer(@RequestBody PlayerDto playerDto){

        Player playerReq = modelMapper.map(playerDto, Player.class);
        Player playerSaved = playerService.addPlayer(playerReq);
        PlayerDto playerRes = modelMapper.map(playerSaved,PlayerDto.class);

        return new ResponseEntity<>(playerRes, HttpStatus.CREATED);

    }

    @Operation(summary = "The api is to delete a player with respect to player id and tournament id.")
    @DeleteMapping("/players/{tournamentId}/{playerId}")
    public ResponseEntity<Void> deletePlayerByTournament(@PathVariable("tournamentId") Long tournamentId, @PathVariable("playerId") Long playerId){

        playerService.deletePlayerByTournament(tournamentId,playerId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}

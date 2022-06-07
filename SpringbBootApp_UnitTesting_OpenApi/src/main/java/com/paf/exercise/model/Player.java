package com.paf.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tbl_player"
)
public class Player {
    @Id
    @SequenceGenerator(name= "player_sequence",sequenceName = "player_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_sequence")
    private Long playerId;

    @Column(nullable = false)
    private String playerName;

    private Long tournamentId;


}

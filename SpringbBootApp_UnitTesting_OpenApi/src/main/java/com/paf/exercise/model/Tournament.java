package com.paf.exercise.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(
        name = "tbl_tournament",
        uniqueConstraints = @UniqueConstraint(
                name = "tournamentname_unique",
                columnNames = "tournamentName"
        )
)
public class Tournament {
    @Id
    @SequenceGenerator(name= "tournament_sequence",sequenceName = "tournament_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_sequence")

    private Long tournamentId;

    @Column(
            nullable = false
    )
    private String tournamentName;
    private int rewardAmount;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name="tournamentId", referencedColumnName="tournamentId")
    private List<Player> players;



}

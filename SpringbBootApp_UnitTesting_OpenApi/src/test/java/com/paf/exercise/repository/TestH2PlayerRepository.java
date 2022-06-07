package com.paf.exercise.repository;

import com.paf.exercise.model.Player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2PlayerRepository extends JpaRepository<Player,Long> {
}

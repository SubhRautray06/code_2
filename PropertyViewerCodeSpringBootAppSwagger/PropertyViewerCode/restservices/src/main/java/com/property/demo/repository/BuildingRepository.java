package com.property.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.property.demo.domain.Building;

@Repository
public interface BuildingRepository extends PagingAndSortingRepository<Building, Long> {
	@Query("SELECT b FROM Building b where b.postalcode = :postalcode and b.number = :number ") 
	Optional<Building> findByCodeAndNumber(@Param("postalcode") String postalcode, @Param("number") int number);

}

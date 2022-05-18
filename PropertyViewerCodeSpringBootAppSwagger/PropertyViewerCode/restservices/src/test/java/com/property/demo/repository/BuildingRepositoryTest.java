package com.property.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.property.demo.domain.Building;

@DataJpaTest
public class BuildingRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BuildingRepository buildingRepository;
    @Test
    public void whenfindByCodeAndNumber_thenReturnBuilding() {
        // given
        Building building = new Building();
		building.setStreetname("Satamatie");
		building.setCity("Lappeenranta");
		building.setCity("Finland");
		building.setPostalcode("53900");
		building.setBuildingname("Prisma");;
        entityManager.persist(building);
        entityManager.flush();

        // when
        Optional<Building> found = buildingRepository.findByCodeAndNumber(building.getPostalcode(),building.getNumber());

        // then
        assertThat(found.get().getStreetname())
          .isEqualTo(building.getStreetname());
    }
}

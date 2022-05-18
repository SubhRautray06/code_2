package com.property.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.property.demo.domain.Building;
import com.property.demo.repository.BuildingRepository;
import com.property.demo.service.impl.BuildingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BuildingServiceImplTest {
	
	@Mock
	private BuildingRepository buildingRepository;
	@InjectMocks
    private BuildingServiceImpl buildingService;
    private List<Building> buildings;
    
    @BeforeEach
    void setUp() {
    	this.buildings = new ArrayList<>();
    	Building building = new Building();
		building.setStreetname("Satamatie");
		building.setCity("Lappeenranta");
		building.setCountry("Finland");
		building.setPostalcode("53900");
		building.setBuildingname("Prisma");
    	this.buildings.add(building);
       // this.building.add(new Building(2L, "", "",0, 0, "", null, null, 0, 0));
    }
   
	@Test
	public void addBuildingTest() {
		when(buildingRepository.saveAll(ArgumentMatchers.anyList())).thenReturn(buildings);
		List<Building> b = buildingService.addBuilding(buildings);
		assertThat(b).isNotNull();
		
	}
	
		
	@Test
	public void getBuildingDetailsTest() {
		Page<Building> builds = new PageImpl(buildings);
		Mockito.when(this.buildingRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(builds);
		List<Building> b = buildingService.getBuildingDetails(0,1,"country");
		assertThat(b).isNotNull();
		
	}
	@Test
	public void getBuildingDetailsNoResultsTest() {
		Page<Building> buildings = Mockito.mock(Page.class);
		when(buildingRepository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(buildings);
		List<Building> b = buildingService.getBuildingDetails(0,1,"country");
		assertThat(b).isNotNull();
		
	}
	
	@Test
	public void updateBuildingTest() {
		Building building = new Building();
		building.setStreetname("Satamatie");
		building.setCity("Lappeenranta");
		building.setCity("Finland");
		building.setPostalcode("53900");
		building.setBuildingname("Prisma");
		when(buildingRepository.findByCodeAndNumber(ArgumentMatchers.anyString(),ArgumentMatchers.anyInt())).thenReturn(Optional.of(building));
		String msg = buildingService.updateBuilding(building);
		assertEquals(msg, "Successfully updated the building details");
		
	}
	
	@Test
	public void deleteBuildingTest() {
		Building building = new Building();
		building.setStreetname("Satamatie");
		building.setCity("Lappeenranta");
		building.setCity("Finland");
		building.setPostalcode("53900");
		building.setBuildingname("Prisma");
		when(buildingRepository.findByCodeAndNumber(ArgumentMatchers.anyString(),ArgumentMatchers.anyInt())).thenReturn(Optional.of(building));
		String msg = buildingService.deleteBuilding(building);
		assertEquals(msg, "Successfully deleted the building details");
		
	}

}

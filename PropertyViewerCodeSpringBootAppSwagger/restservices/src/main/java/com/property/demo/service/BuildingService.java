package com.property.demo.service;

import java.util.List;

import com.property.demo.domain.Building;

public interface BuildingService {

	List<Building> addBuilding(List<Building> buildings);

	List<Building> getBuildingDetails(Integer pageNo, Integer pageSize, String sortBy);

	String updateBuilding(Building building);

	String deleteBuilding(Building building);

}

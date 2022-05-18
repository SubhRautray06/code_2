package com.property.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.property.demo.domain.Building;
import com.property.demo.exceptionhandler.ServiceException;
import com.property.demo.repository.BuildingRepository;
import com.property.demo.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepository buildingRepository;

	@Override
	public List<Building> addBuilding(List<Building> buildings) {
		
		
		buildings.forEach( building -> findCoordinates(building));
		
		List<Building> savedBuildings = (List<Building>) buildingRepository.saveAll(buildings);
		return savedBuildings;
		
		
	}
	private void findCoordinates(Building building) {
		StringBuffer text = new StringBuffer();
		validateBuilding(building);
		text.append(building.getNumber()).append(" ").append(building.getStreetname()).append(", ")
		.append(building.getCity()).append(" ")
		.append(building.getPostalcode()).append(", ").append(building.getCountry());
		
		String apiKey = "1300ee56ff63441d8fea370055fa6f56";		
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "https://api.geoapify.com/v1/geocode/search?text="+text+"&apiKey="+apiKey;
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			Double lon = null;
			Double lat = null;
			JsonNode obj = mapper.readTree(response.getBody());
			JsonNode features = obj.get("features");
			if (features.isArray()) {
			    for (final JsonNode objNode : features) {
			    	lon = objNode.get("properties").get("lon").doubleValue();
			    	lat = objNode.get("properties").get("lat").doubleValue();
			    	
			    }
			}
			if(null!=lon && null!=lat) {
				building.setLattitude(lat);
				building.setLongitude(lon);
			}
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}

				
	}
	private void validateBuilding(Building building) {
		if(null==building) {
			throw new ServiceException("600","Empty details");
		}
		if(building.getBuildingname().isEmpty()) {
			throw new ServiceException("601","Please send building name");
		}
		if(building.getStreetname().isEmpty()) {
			throw new ServiceException("602","Please send street name");
		}
		if(building.getCity().isEmpty()) {
			throw new ServiceException("603","Please send city name");
		}
		if(building.getPostalcode().isEmpty()) {
			throw new ServiceException("604","Please send postal code");
		}
		if(building.getCountry().isEmpty()) {
			throw new ServiceException("605","Please send country code");
		}
	}
	@Override
	public List<Building> getBuildingDetails(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		
        Page<Building> result = buildingRepository.findAll(paging);
         
        if(result.hasContent()) {
            return result.getContent();
        } else {
            return new ArrayList<Building>();
        }
		
	}
	
	
	@Override
	public String updateBuilding(Building building) {
		Optional<Building> build = buildingRepository.findByCodeAndNumber(building.getPostalcode(),building.getNumber());
		if(build.isPresent()) {
			
			Building b = build.get();
			b.setBuildingname(building.getBuildingname());
			b.setDescription(building.getDescription());
			buildingRepository.save(b);
		}
		return "Successfully updated the building details";
	}
	@Override
	public String deleteBuilding(Building building) {
		Optional<Building> build = buildingRepository.findByCodeAndNumber(building.getPostalcode(),building.getNumber());
		if(build.isPresent()) {
			buildingRepository.delete(build.get());
		}
		return "Successfully deleted the building details";
	}

	
	
	
	
}

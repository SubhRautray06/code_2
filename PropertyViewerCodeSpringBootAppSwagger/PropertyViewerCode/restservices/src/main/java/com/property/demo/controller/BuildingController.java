package com.property.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.property.demo.domain.Building;
import com.property.demo.exceptionhandler.ControllerException;
import com.property.demo.exceptionhandler.ServiceException;
import com.property.demo.service.BuildingService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/property")
public class BuildingController {
	
    private static final Logger logger = LoggerFactory.getLogger(BuildingController.class);
	
	@Autowired
	private BuildingService buildingService;
	
	@ApiOperation(value = "add details of buildings")
    @PostMapping("/buildings")
	public ResponseEntity<?> addBuilding(@RequestBody List<Building> buildings) {
		
		try {
			List<Building> savedBuildings= buildingService.addBuilding(buildings);
			return new ResponseEntity<List<Building>>(savedBuildings, HttpStatus.OK);
		}catch (ServiceException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(),e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			ControllerException ce = new ControllerException("611","Something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@ApiOperation(value = "fetch all the details of buildings")
	@GetMapping("/buildings")
	public List<Building> getBuildings(
			@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "country") String sortBy
			
			) {
		return buildingService.getBuildingDetails(pageNo, pageSize, sortBy);
		
	}
	
	@ApiOperation(value = "update the details of a building")
	@PutMapping("/buildings")
	public String updateBuilding(@RequestBody Building building) {
		return buildingService.updateBuilding(building);
	}
	
	@ApiOperation(value = "Delete a building record")
	@DeleteMapping("/buildings")
	public String delete(@RequestBody Building building) {
		return buildingService.deleteBuilding(building);
		
	}
	
}

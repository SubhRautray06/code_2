package com.property.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.property.demo.domain.Building;
import com.property.demo.service.BuildingService;
@WebMvcTest(controllers = BuildingController.class)
@ActiveProfiles("test")
public class BuildingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
	private BuildingService buildingService;
    
    private List<Building> buildingList;
    
    @BeforeEach
    void setUp() {
    	this.buildingList = new ArrayList<>();
    	this.buildingList.add(new Building(1L, "A", "Taipalsaarentie",3, "53900", "Lappeenranta", "Finland", "House", 23.0000, 45.0000));
       // this.building.add(new Building(2L, "", "",0, 0, "", null, null, 0, 0));
    }
    
        @Test
    void addBuildingTest() throws Exception {

        //given(buildingService.addBuilding(buildingList)).willReturn(buildingList);
        when(buildingService.addBuilding(ArgumentMatchers.anyList())).thenReturn(buildingList);
        this.mockMvc.perform(post("/property/buildings")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(objectMapper.writeValueAsString(buildingList)))
                .andExpect(status().isOk()).andDo(print());
                //.andExpect(jsonPath("$.size()", is(buildingList.size()))).andDo(print());
    }
        
        
    @Test
    void getBuildingsTest() throws Exception {

        //given(buildingService.addBuilding(buildingList)).willReturn(buildingList);
        when(buildingService.getBuildingDetails(ArgumentMatchers.anyInt(),ArgumentMatchers.anyInt(),ArgumentMatchers.anyString())).thenReturn(buildingList);
        this.mockMvc.perform(get("/property/buildings?pageNo=0&pageSize=1&sortBy=country")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(objectMapper.writeValueAsString(buildingList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(buildingList.size()))).andDo(print());
    }   
    
    
    
	@Test
	void updateBuildingsTest() throws Exception {
    	Building building = new Building(1L, "A", "Taipalsaarentie",3, "53900", "Lappeenranta", "Finland", "House", 23.0000, 45.0000);

	    //given(buildingService.addBuilding(buildingList)).willReturn(buildingList);
	    when(buildingService.updateBuilding(ArgumentMatchers.any())).thenReturn("Successfully updated the building detail");
	    this.mockMvc.perform(put("/property/buildings")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.content(objectMapper.writeValueAsString(building)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("Successfully updated the building detail")).andDo(print());
	} 
    
	@Test
	void deleteBuildingsTest() throws Exception {
    	Building building = new Building(1L, "A", "Taipalsaarentie",3, "53900", "Lappeenranta", "Finland", "House", 23.0000, 45.0000);

	    //given(buildingService.addBuilding(buildingList)).willReturn(buildingList);
	    when(buildingService.deleteBuilding(ArgumentMatchers.any())).thenReturn("Successfully deleted the building detail");
	    this.mockMvc.perform(delete("/property/buildings")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.content(objectMapper.writeValueAsString(building)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("Successfully deleted the building detail")).andDo(print());
	} 
        
}

package com.property.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//no need to use this below three line
@Entity
@Getter
@Setter 
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Building {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true,notes = "The database generated product ID")
	private long id;
	@JsonProperty("buildingname")
	@ApiModelProperty(notes = "Name of the building")
	private String buildingname;
	//@ApiModelProperty(notes = "Name of the street where the building the situated")
	@JsonProperty
	private String streetname;
	@JsonProperty
	//@ApiModelProperty(notes = "Number of the building")
	private int number;
	@JsonProperty
	//@ApiModelProperty(notes = "Postal code of the location where the building located")
	private String postalcode;
	@JsonProperty
	//@ApiModelProperty(notes = "City name where building located")
	private String city;
	@JsonProperty
	//@ApiModelProperty(notes = "Name of the country")
	private String country;
	@JsonProperty
	//@ApiModelProperty(notes = "description")
	private String description;
    @ApiModelProperty(hidden = true)
	private double lattitude;
    @ApiModelProperty(hidden = true)
	private double longitude;

}

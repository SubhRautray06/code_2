
package com.property.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Feature {
//	@JsonProperty("lon")
//	private double lon;
//	@JsonProperty("lat")
//	private double lat;
	@JsonProperty("properties")
	private Property properties;
	
}

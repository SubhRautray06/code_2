package com.property.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Property {
	@JsonProperty("lon")
	private double lon;
	@JsonProperty("lat")
	private double lat;
}

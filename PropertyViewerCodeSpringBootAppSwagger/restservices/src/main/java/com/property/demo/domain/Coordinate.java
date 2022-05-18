package com.property.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
	@JsonProperty("type")
	private String type;
	@JsonProperty("features")
	private Feature features;
}

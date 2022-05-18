package com.property.demo.domain;

import java.util.List;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

//@Entity

@Getter
@Setter 
//@NoArgsConstructor
public class Buildings {
	private List<Building> building;
}

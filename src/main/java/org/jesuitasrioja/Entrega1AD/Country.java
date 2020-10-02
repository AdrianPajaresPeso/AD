package org.jesuitasrioja.Entrega1AD;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Country {
	private String code;
	private String name;
	private String continent;
	private String region;
	private float surfaceArea;
	private Integer indepYear;
	private Integer population;
	private float lifeExpectancy;
	private float gnp;
	private float gnpOld;
	private String localName;
	private String governmentForm;
	private String headOfState;
	private Integer capital;
	private String code2;
}

package org.jesuitasrioja.Entrega1AD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor
public class City {
	private Integer id;
	private String name;
	private Country country;
	private String district;
	private Integer population;
}

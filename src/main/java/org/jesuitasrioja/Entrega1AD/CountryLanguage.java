package org.jesuitasrioja.Entrega1AD;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CountryLanguage {
	private String countryCode;
	private String language;
	private String isOfficial;
	private float percentage;
}
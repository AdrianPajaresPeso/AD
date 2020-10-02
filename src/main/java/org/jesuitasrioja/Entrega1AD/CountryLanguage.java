package org.jesuitasrioja.Entrega1AD;

import lombok.Data;

@Data
public class CountryLanguage {
	private String countryCode;
	private String language;
	private boolean isOfficial;
	private float percentage;
}
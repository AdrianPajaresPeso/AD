package org.jesuitasrioja.Entrega1AD;

import java.util.List;
import java.util.Set;

public interface IPersistencia {
	public Set<City> listaCiudades(); //a

	public Set<Country> listaPaises(); //b

	public Boolean existeCiudad(Integer codCiudad);//c

	public Boolean existePais(String codPais);//d

	public City getCity(Integer codCiudad);//e

	public Country getCountry(String codPais);//f

	public Set<City> listaCiudades(String nombrePais);//g

	public Country getPaisDeCiudad(Integer codCiudad);//h
	
	public Boolean estaCiudadEnPais(Integer codCiudad, String codPais);//i
	
	public void cambiarNombreCiudad(Integer codCiudad, String nuevoNombre);//j
	
	public void addCiudad(City nuevoNombre);//k
	
	public void addPais(Country nuevoPais);//l
	
	public List<CountryLanguage> getAllLanguages();//m
	
	public Set<CountryLanguage> listaIdiomas(String codPais);//n
}

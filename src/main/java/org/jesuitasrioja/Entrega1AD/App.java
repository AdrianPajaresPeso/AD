package org.jesuitasrioja.Entrega1AD;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		IPersistencia ip = new PersistenciaJDBC();
//		System.out.println(ip.listaPaises());
//		System.out.println(ip.existeCiudad(75));
//		System.out.println(ip.existePais("JAJA"));
//		System.out.println(ip.getCountry("ESP"));
//		System.out.println(ip.listaCiudades("Spain"));
//		System.out.println(ip.listaCiudades());
//		System.out.println(ip.getAllLanguages());//no termina de mostrar y no se porque
//		System.out.println(ip.getPaisDeCiudad(55));
//		System.out.println(ip.listaIdiomas("ESP"));
//		System.out.println(ip.getCity(413));
//		System.out.println(ip.estaCiudadEnPais(711, "ESP"));
//		ip.cambiarNombreCiudad(1, "Kabul");
//		ip.cambiarNombreCiudad(1, "HolaCarlos");

		Country c = new Country("HOL", "Patata", "Asia", "Norte", 192.16f, 420, 69, 41, 456.15f, 54.646f, "conQueso",
				"DictaduraAdriatica", "Adrian", 1, "CP");

		City ci = new City(1, "Macarroni", c, "ConQuesoni", 115);
		ip.addPais(c);
		ip.addCiudad(ci);
	}
}

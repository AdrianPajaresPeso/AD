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
//		System.out.println(ip.getAllLanguages());
//		System.out.println(ip.getPaisDeCiudad(55));
		System.out.println(ip.listaIdiomas("ESP"));
	}
}

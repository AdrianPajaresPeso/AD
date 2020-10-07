/**
 * @author Adrián Pajares Peso
 * */

package org.jesuitasrioja.Entrega1AD;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.mysql.cj.jdbc.Driver;

public class PersistenciaJDBC implements IPersistencia {

	// parametros de la conexion
	private String user;
	private String pass;
	private String url;

	/**
	 * Constructor de la clase que inicializa los parametros de conexion
	 * */
	public PersistenciaJDBC() {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("src/main/resources/propiedades.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saca una lista de todas las ciudades que hay en la base de datos
	 */
	@Override
	public Set<City> listaCiudades() {
		Connection con = null;
		Set<City> returnCiudad = new HashSet<>();
		try {
			// creamos la conexion
			con = DriverManager.getConnection(url, user, pass);

			// prepraramos y ejecutamos la consulta
			PreparedStatement ps = con.prepareStatement("SELECT * FROM world.city");
			ResultSet rs = ps.executeQuery();

			// recogemos el resultado de la query y obtenemos el pais entero de la ciudad
			while (rs.next()) {
				City c = new City(rs.getInt(1), rs.getString(2), this.getCountry(rs.getString(3)), rs.getString(4),
						rs.getInt(5));
				returnCiudad.add(c);
			}

			// se cierran conexiones
			rs.close();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			// si algo falla y no se consigue cerrar la conexion se cierra aqui
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return returnCiudad;
	}

	/**
	 * Obtiene una lista de todos los paises que hay en la base de datos
	 * 
	 */
	@Override
	public Set<Country> listaPaises() {
		Connection con = null;

		// Lista de paises que vamos a retornar
		Set<Country> sCountry = new HashSet<>();

		try {

			// creamos la conexion
			con = DriverManager.getConnection(url, user, pass);

			PreparedStatement ps = con.prepareStatement("Select * From country");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Country c = new Country(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getFloat(5), rs.getInt(6), rs.getInt(7), rs.getFloat(8), rs.getFloat(9), rs.getFloat(10),
						rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getString(15));

				sCountry.add(c);
			}

			// cerramos la conexion, el ResultSet y el PreparedStatement
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			// si falla algo y no conseguimos cerrar la conexion
			// se cierra aqui si la conexion no se ha llegado a cerrar
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sCountry;
	}

	/**
	 * Metodo que devuelve verdadero si la ciudad que se busca por su identificador
	 * existe en la base de datos.
	 * 
	 * @param <b>codCiudad</b>
	 * 
	 * @return <b>true</b> si la consulta devuelve una row con el mismo ID que se
	 *         especifica.</br>
	 *         en el parametro</br>
	 *         <b>false</b> valor por defecto a retornar.
	 */
	@Override
	public Boolean existeCiudad(Integer codCiudad) {
		// bandera para retornar true o false
		Boolean flag = false;

		Connection con = null;

		try {
			// Se crea la conexion a la BBDD
			con = DriverManager.getConnection(url, user, pass);

			// se prepara la Query con el parámetro CodCiudad
			PreparedStatement ps = con.prepareStatement("Select * from city where city.ID = ? ");
			ps.setInt(1, codCiudad);

			// ejecutamos la query
			ResultSet rs = ps.executeQuery();
			// Solo cogemos el primer resultado de la query
			if (rs.next()) {
				if (rs.getString(1).equals(codCiudad.toString()))
					flag = true;
			}

			// cerramos ResultSet, PreparedStatement y conexion
			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// si falla algo y no conseguimos cerrar la conexion
			// se cierra aqui si la conexion no se ha llegado a cerrar
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * 
	 * Metodo que comprueba la existencia de un pais.
	 * 
	 * @param codPais
	 * @return<b>true</b> si la consulta devuelve una row con el mismo Codigo de
	 *                    Pais que se especifica.</br>
	 *                    en el parametro</br>
	 *                    <b>false</b> valor por defecto a retornar.
	 */
	@Override
	public Boolean existePais(String codPais) {
		// bandera que siempre devuelve falso
		Boolean flag = false;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = con.prepareStatement("Select * from country where country.Code = ? ");

			ps.setString(1, codPais);

			ResultSet rs = ps.executeQuery();
			// si la row contiene el mismo id la bandera cambia a true
			if (rs.next()) {
				if (rs.getString(1).equals(codPais))
					flag = true;
			}
			// cerramos ResultSet, PreparedStatement y conexion.
			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			// si falla algo y no conseguimos cerrar la conexion
			// se cierra aqui si la conexion no se ha llegado a cerrar
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * Metodo que recoge la informacion de una sola ciudad especificada por
	 * parámetro
	 * 
	 * 
	 */
	@Override
	public City getCity(Integer codCiudad) {

		Connection con = null;
		// Creamos la ciudad que vamos a retornar
		City c = null;

		try {
			// se crea la conexion a la BBDD
			con = DriverManager.getConnection(url, user, pass);

			// preparamos la query con el parametro como codigo de ciudad
			PreparedStatement ps = con.prepareStatement("Select * from city where ID = ?");
			ps.setInt(1, codCiudad);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				c = new City(rs.getInt(1), rs.getString(2), this.getCountry(rs.getString(3)), rs.getString(4),
						rs.getInt(5));
			}

			// Se cierra el resultSet, el preparedStatement y la conexion
			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// si falla algo y no conseguimos cerrar la conexion
			// se cierra aqui si la conexion no se ha llegado a cerrar
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return c;
	}

	/**
	 * Metodo que recoge y devuelve el pais que se le especifica por parametro
	 * 
	 */
	@Override
	public Country getCountry(String codPais) {
		Connection con = null;
		Country c = null;
		try {
			con = DriverManager.getConnection(url, user, pass);

			PreparedStatement ps = con.prepareStatement("Select * From country WHERE code = ?");
			ps.setString(1, codPais);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				c = new Country(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5),
						rs.getInt(6), rs.getInt(7), rs.getFloat(8), rs.getFloat(9), rs.getFloat(10), rs.getString(11),
						rs.getString(12), rs.getString(13), rs.getInt(14), rs.getString(15));
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return c;
	}

	@Override
	public Set<City> listaCiudades(String nombrePais) {
		Connection con = null;
		Set<City> returnCiudad = new HashSet<>();
		try {
			con = DriverManager.getConnection(url, user, pass);

			PreparedStatement ps = con.prepareStatement(
					"SELECT * FROM world.city c join world.country co on c.CountryCode = co.Code where co.Name =?");
			ps.setString(1, nombrePais);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				City c = new City(rs.getInt(1), rs.getString(2), this.getCountry(rs.getString(3)), rs.getString(4),
						rs.getInt(5));
				returnCiudad.add(c);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return returnCiudad;
	}

	@Override
	public Country getPaisDeCiudad(Integer codCiudad) {
		Connection con = null;
		Country c = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = con.prepareStatement(
					"Select * from country where code in (Select CountryCode from city where ID = ?)");
			ps.setInt(1, codCiudad);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				c = new Country(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5),
						rs.getInt(6), rs.getInt(7), rs.getFloat(8), rs.getFloat(9), rs.getFloat(10), rs.getString(11),
						rs.getString(12), rs.getString(13), rs.getInt(14), rs.getString(15));
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return c;
	}

	@Override
	public Boolean estaCiudadEnPais(Integer codCiudad, String codPais) {
		boolean flag = false;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = con.prepareStatement(
					"Select * from country where code in (Select CountryCode from city where ID = ?)");
			ps.setInt(1, codCiudad);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals(codPais))
					flag = true;
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public void cambiarNombreCiudad(Integer codCiudad, String nuevoNombre) {
		Connection con = null;
		City lastCity = this.getCity(codCiudad);
		City newCity = null;
		try {

			con = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = con.prepareStatement("UPDATE city SET Name = ? WHERE ID = ?");
			ps.setString(1, nuevoNombre);
			ps.setInt(2, codCiudad);

			int rows = ps.executeUpdate();
			System.out.println("Se ha ejecutado la consulta con un total de " + rows + " fila(s) afectada(s)");

			// comprobacion de que se ha realizado correctamente
			ps = con.prepareStatement("Select * from city where ID = ?");
			ps.setInt(1, codCiudad);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				newCity = new City(rs.getInt(1), rs.getString(2), this.getCountry(rs.getString(3)), rs.getString(4),
						rs.getInt(5));
			}
			if (!lastCity.equals(newCity)) {
				System.out.println("Se ha modificado correctamente");
			} else {
				System.out.println("No se han realizado cambios");
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void addCiudad(City nuevoNombre) {
		Connection con = null;
		int lastRows = 0;
		int newRows = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = con.prepareStatement("Select COUNT(ID) FROM city");
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				lastRows = rs.getInt(1); // lineas de la BBDD sin insert

			ps = con.prepareStatement(
					"INSERT INTO city (ID, Name, CountryCode, District, Population) values (?,?,?,?,?)");
			ps.setInt(1, lastRows + 1);
			ps.setString(2, nuevoNombre.getName());
			ps.setString(3, nuevoNombre.getCountry().getCode());
			ps.setString(4, nuevoNombre.getDistrict());
			ps.setInt(5, nuevoNombre.getPopulation());

			ps.executeUpdate();

			ps = con.prepareStatement("Select COUNT(ID) FROM city"); // cuenta las lineas que hay de nuevo
			rs = ps.executeQuery();
			if (rs.next())
				newRows = rs.getInt(1); // nuevo valor de lineas en la bbdd

			if (lastRows < newRows) {
				System.out.println("Se ha añadido la ciudad a la base de datos");
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addPais(Country nuevoPais) {
		Connection con = null;
		int lastRows = 0;
		int newRows = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			PreparedStatement rows = con.prepareStatement("SELECT COUNT(Code) FROM country");
			ResultSet rs = rows.executeQuery();
			if (rs.next())
				lastRows = rs.getInt(1);

			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO country (Code, Name, Continent, Region, SurfaceArea, IndepYear, Population, LifeExpectancy, GNP, GNPOld, LocalName, GovernmentForm, HeadOfState, Capital, Code2) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, nuevoPais.getCode());
			ps.setString(2, nuevoPais.getName());
			ps.setString(3, nuevoPais.getContinent());
			ps.setString(4, nuevoPais.getRegion());
			ps.setFloat(5, nuevoPais.getSurfaceArea());
			ps.setInt(6, nuevoPais.getIndepYear());
			ps.setInt(7, nuevoPais.getPopulation());
			ps.setFloat(8, nuevoPais.getLifeExpectancy());
			ps.setFloat(9, nuevoPais.getGnp());
			ps.setFloat(10, nuevoPais.getGnpOld());
			ps.setString(11, nuevoPais.getLocalName());
			ps.setString(12, nuevoPais.getGovernmentForm());
			ps.setString(13, nuevoPais.getHeadOfState());
			ps.setInt(14, nuevoPais.getCapital());
			ps.setString(15, nuevoPais.getCode2());

			ps.executeUpdate();

			rs = rows.executeQuery();
			if (rs.next())
				newRows = rs.getInt(1);
			if (lastRows < newRows)
				System.out.println("Se ha añadido el pais a la base de datos");

			rs.close();
			rows.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public List<CountryLanguage> getAllLanguages() {
		Connection con = null;
		ArrayList<CountryLanguage> lstLanguages = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url, user, pass);
			PreparedStatement ps = con.prepareStatement("Select * from countrylanguage");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CountryLanguage cl = new CountryLanguage(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getFloat(4));
				lstLanguages.add(cl);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstLanguages;
	}

	@Override
	public Set<CountryLanguage> listaIdiomas(String codPais) {
		Connection con = null;
		Set<CountryLanguage> clSetReturn = new HashSet<>();
		try {
			con = DriverManager.getConnection(url, user, pass);
			if (this.existePais(codPais)) {
				PreparedStatement ps = con.prepareStatement(
						"Select * from countrylanguage where countrycode in (Select Code from country where code = ?)");
				ps.setString(1, codPais);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					CountryLanguage cl = new CountryLanguage(rs.getString(1), rs.getString(2), rs.getString(3),
							rs.getFloat(4));
					clSetReturn.add(cl);
				}
				rs.close();
				ps.close();
			} else {
				System.out.println("No existe el pais seleccionado");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return clSetReturn;
	}

}

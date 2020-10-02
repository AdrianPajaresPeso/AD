/**
 * @author Adrián Pajares Peso
 * */

package org.jesuitasrioja.Entrega1AD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersistenciaJDBC implements IPersistencia {

	private String userPassword = "root";
	private String conexion = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";

	@Override
	public Set<City> listaCiudades() {
		Connection con = null;
		Set<City> returnCiudad = new HashSet<>();
		try {
			con = DriverManager.getConnection(conexion, userPassword, userPassword);

			PreparedStatement ps = con.prepareStatement("SELECT * FROM world.city");
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
				// TODO: handle exception
			}
		}
		return returnCiudad;
	}

	@Override
	public Set<Country> listaPaises() {
		Connection con = null;
		Set<Country> sCountry = new HashSet<>();
		try {
			con = DriverManager.getConnection(conexion, userPassword, userPassword);

			PreparedStatement ps = con.prepareStatement("Select * From country");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Country c = new Country(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getFloat(5), rs.getInt(6), rs.getInt(7), rs.getFloat(8), rs.getFloat(9), rs.getFloat(10),
						rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getString(15));

				sCountry.add(c);
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
				// TODO: handle exception
			}
		}
		return sCountry;
	}

	@Override
	public Boolean existeCiudad(Integer codCiudad) {
		Boolean flag = false;
		Connection con = null;
		try {
			con = DriverManager.getConnection(conexion, userPassword, userPassword);
			PreparedStatement ps = con.prepareStatement("Select * from city where city.ID = ? ");

			ps.setString(1, codCiudad.toString());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(codCiudad.toString()))
					flag = true;
			}
			rs.close();
			ps.close();
			con.close();
			;
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
	public Boolean existePais(String codPais) {
		Boolean flag = false;
		Connection con = null;
		try {
			con = DriverManager.getConnection(conexion, userPassword, userPassword);
			PreparedStatement ps = con.prepareStatement("Select * from country where country.Code = ? ");

			ps.setString(1, codPais);

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
	public City getCity(Integer codCiudad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country getCountry(String codPais) {
		Connection con = null;
		Country c = null;
		try {
			con = DriverManager.getConnection(conexion, userPassword, userPassword);

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
				// TODO: handle exception
			}
		}
		return c;
	}

	@Override
	public Set<City> listaCiudades(String nombrePais) {
		Connection con = null;
		Set<City> returnCiudad = new HashSet<>();
		try {
			con = DriverManager.getConnection(conexion, userPassword, userPassword);

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
				// TODO: handle exception
			}
		}
		return returnCiudad;
	}

	@Override
	public Country getPaisDeCiudad(Integer codCiudad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean estaCiudadEnPais(Integer codCiudad, String codPais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cambiarNombreCiudad(Integer codCiudad, String nuevoNombre) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCiudad(City nuevoNombre) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPais(Country nuevoPais) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CountryLanguage> getAllLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<CountryLanguage> listaIdiomas(String codPais) {
		// TODO Auto-generated method stub
		return null;
	}

}

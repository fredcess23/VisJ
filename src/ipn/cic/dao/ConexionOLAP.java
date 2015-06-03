package ipn.cic.dao;

import java.sql.*;
import ipn.cic.modelo.Conexion;

public class ConexionOLAP {

	private static String cadena;
	private static String driver;
	private static String esquema;
	
	
	public ConexionOLAP(Conexion conexion)
	{
		esquema = "Catalog='file://Users/fredcess23/" +conexion.getEsquema()+"';";
			
		if(conexion.getConexion().equals("mysql"))
		{
			cadena = "Jdbc='jdbc:mysql://"+ conexion.getNombreHost() +":"+ conexion.getNumeroPuerto() +"/"+ 
			conexion.getNombreBD() +"?user="+ conexion.getUsuario() +"&password="+ conexion.getContrasena() +"';";
			
			driver = "JdbcDrivers=com.mysql.jdbc.Driver;";	
		}
	}
	
	
	public static Connection getConexionOLAP() throws Exception
	{
        Connection connection = null;

        Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
        connection = DriverManager.getConnection(
	            "jdbc:mondrian:"
	                + cadena
	                + esquema
	                + driver);
	    
        
		return connection;
	}	
	

}

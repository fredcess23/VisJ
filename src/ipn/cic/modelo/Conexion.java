package ipn.cic.modelo;

import java.io.Serializable;

public class Conexion implements Serializable{
	
	private String conexion="";
	private String nombreHost="";
	private String nombreBD="";
	private String numeroPuerto="";
	private String usuario="";
	private String contrasena="";
	private String esquema="";
	
	public String getEsquema() {
		return esquema;
	}
	public void setEsquema(String esquema) {
		if(esquema!=null)
		this.esquema = esquema;
	}
	public String getConexion() {
		return conexion;
	}
	public void setConexion(String conexion) {
		if(conexion!=null)
		this.conexion = conexion;
	}
	public String getNombreHost() {
		return nombreHost;
	}
	public void setNombreHost(String nombreHost) {
		if(nombreHost!=null)
		this.nombreHost = nombreHost;
	}
	public String getNombreBD() {
		return nombreBD;
	}
	public void setNombreBD(String nombreBD) {
		if(nombreBD!=null)
		this.nombreBD = nombreBD;
	}
	public String getNumeroPuerto() {
		return numeroPuerto;
	}
	public void setNumeroPuerto(String numeroPuerto) {
		if(numeroPuerto!=null)
		this.numeroPuerto = numeroPuerto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		if(usuario!=null)
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		if(contrasena!=null)
		this.contrasena = contrasena;
	}

}

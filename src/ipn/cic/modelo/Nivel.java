package ipn.cic.modelo;

import java.io.Serializable;

public class Nivel implements Serializable {
	
	String nombre;
	String nombre_unico;
	
	
	public String getNombre_unico() {
		return nombre_unico;
	}
	
	public void setNombre_unico(String nombre_unico) {
		this.nombre_unico = nombre_unico;
	}

	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}

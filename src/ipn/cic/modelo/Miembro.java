package ipn.cic.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Miembro implements Serializable
{
	
	String nombre;
	String nombre_unico;
	Double valor;
	Integer radio;
	String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getRadio() {
		return radio;
	}

	public void setRadio(Integer radio) {
		this.radio = radio;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

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

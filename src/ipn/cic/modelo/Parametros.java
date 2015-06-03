package ipn.cic.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Parametros implements Serializable
{
	String cubo;
	String dimension;
	String nivel;
	String hecho;
	String grafica;
	String eficiencia;
	String tipoEficiencia;
	String year;
	Integer rango1;
	Integer rango2;
	String nodo;		// nombre unico
	String nombre_nodo;	// nombre corto
	String sub_nodo;	
	String nombre_sub_nodo;
	ArrayList<String> dominio;
	String dom;
	
	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public ArrayList<String> getDominio() {
		return dominio;
	}

	public void setDominio(ArrayList<String> dominio) {
		this.dominio = dominio;
	}

	public String getNombreSubNodo() {
		setNombrSubNodo(getSubNodo());
		return nombre_sub_nodo;
	}

	public void setNombrSubNodo(String nombre_sub_nodo) {
		int inicio = nombre_sub_nodo.lastIndexOf("[");
		int fin = nombre_sub_nodo.lastIndexOf("]");
		nombre_sub_nodo = nombre_sub_nodo.substring(inicio+1,fin);
		this.nombre_sub_nodo = nombre_sub_nodo;
	}

	public String getSubNodo() {
		return sub_nodo;
	}

	public void setSubNodo(String sub_nodo) {
		this.sub_nodo = sub_nodo;
	}

	public String getNombreNodo() {
		setNombreNodo(getNodo());
		return nombre_nodo;
	}

	public void setNombreNodo(String nombre_nodo) {
		int inicio = nombre_nodo.lastIndexOf("[");
		int fin = nombre_nodo.lastIndexOf("]");
		nombre_nodo = nombre_nodo.substring(inicio+1,fin);
		this.nombre_nodo = nombre_nodo;
	}

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public Integer getRango1() {
		return rango1;
	}

	public void setRango1(Integer rango1) {
		if(rango1==null)
			this.rango1 = 0;
		else
			this.rango1 = rango1;
	}

	public Integer getRango2() {
		return rango2;
	}

	public void setRango2(Integer rango2) {
		if(rango2==null)
			this.rango2 = 0;
		else
			this.rango2 = rango2;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTipoEficiencia() {
		return tipoEficiencia;
	}

	public void setTipoEficiencia(String tipoEficiencia) {
		this.tipoEficiencia = tipoEficiencia;
	}

	public String getEficiencia() {
		return eficiencia;
	}

	public void setEficiencia(String eficiencia) {
		this.eficiencia = eficiencia;
	}

	public String getCubo() {
		return cubo;
	}
	
	public void setCubo(String cubo) {
		this.cubo = cubo;
	}
	
	public String getDimension() {
		return dimension;
	}
	
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	
	public String getNivel() {
		return nivel;
	}
	
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	public String getHecho() {
		return hecho;
	}
	
	public void setHecho(String hecho) {
		this.hecho = hecho;
	}
	
	public String getGrafica() {
		return grafica;
	}
	
	public void setGrafica(String grafica)
	{
		if(grafica == null)
			this.grafica = "";
		else
			this.grafica = grafica;
		
	}
	
}

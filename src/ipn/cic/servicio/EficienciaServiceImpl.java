package ipn.cic.servicio;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ipn.cic.dao.EficienciaDAO;
import ipn.cic.modelo.Miembro;
import ipn.cic.modelo.Parametros;
import ipn.cic.xml.XML;


public class EficienciaServiceImpl implements EficienciaService{
	
	EficienciaDAO EficienciaDAO;

	public EficienciaDAO getEficienciaDAO() {
		return EficienciaDAO;
	}
	
	public void setEficienciaDAO(EficienciaDAO eficienciaDAO) {
		EficienciaDAO = eficienciaDAO;
	}
	
	
    /*****************************************************************************
    * Metodo que obtiene los miembros de 2 cubos definidos en el esquema.
    * @param:   Parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    28/Febrero/2011
    * @modify:  8/Marzo/2011
    *****************************************************************************/
	@Override
	public String getEficienciaMapa(Parametros parametros) {
		
		String strXML = null;
		ArrayList<ArrayList> miembros = new ArrayList<ArrayList>();
		
		ArrayList<String> dominio = parametros.getDominio();
		
		for(String tiempo:dominio)
		{
			parametros.setYear(tiempo);
			ArrayList<Miembro> miembros_cubo = this.EficienciaDAO.getALLMembers(parametros);
			miembros.add(miembros_cubo);
		}
	
		ArrayList<Miembro> miembros_eficiencia = calculoEficiencia(miembros);
		
		if(parametros.getEficiencia().equals("peores"))
		{		
			Collections.sort(miembros_eficiencia, new MiembroValorComparator());
			XML xml = new XML();
			
			if(parametros.getGrafica().equals("DragNode.swf"))
				strXML = xml.formaMapaNodos(miembros_eficiencia,parametros);

			if(parametros.getGrafica().equals("DragNode2D.swf"))
				strXML = xml.formaMapaNodos2D(miembros_eficiencia,parametros);
			
			if(parametros.getGrafica().equals("HeatMap.swf"))
				strXML = xml.formaMapaHeat(miembros_eficiencia,parametros);
			
			if(parametros.getGrafica().equals("MultiLevelPie.swf"))
				strXML = xml.formaMapaMultiLevelPie(miembros_eficiencia,parametros);
			
			
		}
		
		if(parametros.getEficiencia().equals("mejores"))
		{		
			Collections.sort(miembros_eficiencia, new MiembroValorComparator());
			Collections.reverse(miembros_eficiencia);
			XML xml = new XML();
			
			if(parametros.getGrafica().equals("DragNode.swf"))
				strXML = xml.formaMapaNodos(miembros_eficiencia,parametros);
			
			if(parametros.getGrafica().equals("DragNode2D.swf"))
				strXML = xml.formaMapaNodos2D(miembros_eficiencia,parametros);			
			
			if(parametros.getGrafica().equals("HeatMap.swf"))
				strXML = xml.formaMapaHeat(miembros_eficiencia,parametros);
			
			if(parametros.getGrafica().equals("MultiLevelPie.swf"))
				strXML = xml.formaMapaMultiLevelPie(miembros_eficiencia,parametros);
		}
	
		return strXML;
		
	}
	
	
	/*****************************************************************************
    * Metodo que calcula la eficiencia de 2 cubos definidos en el esquema.
    * @param:   ArrayList<Miembro>, ArrayList<Miembro> 
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    28/Febrero/2011
    * @modify:  8/Marzo/2011
    *****************************************************************************/
	public ArrayList<Miembro> calculoEficiencia(ArrayList<ArrayList> miembros)
	{
		
		ArrayList<Miembro> miembros_eficiencia = new ArrayList<Miembro>();
		ArrayList<Miembro> miembros_cubo_1 = null;
		ArrayList<Miembro> miembros_cubo_2 = null;		
		Miembro miembro_eficiencia=null;
		Double eficiencia = 0.0;
		int longitud=0;
		
		miembros_cubo_1 = miembros.get(0);
		miembros_cubo_2 = miembros.get(1);

		if(miembros_cubo_1.size() > miembros_cubo_2.size())
			longitud = miembros_cubo_1.size();
		else
			longitud = miembros_cubo_2.size();
		
		for(int i=0; i<longitud ;i++)
		{
			
				if(miembros_cubo_1.get(i).getValor()!=null && miembros_cubo_2.get(i).getValor()!=null)
				{
					miembro_eficiencia = new Miembro();
					eficiencia =  100 * ((miembros_cubo_2.get(i).getValor() - miembros_cubo_1.get(i).getValor())/miembros_cubo_1.get(i).getValor());
					miembro_eficiencia.setNombre(miembros_cubo_2.get(i).getNombre());
					miembro_eficiencia.setNombre_unico(miembros_cubo_2.get(i).getNombre_unico());
					miembro_eficiencia.setValor(eficiencia);	
					miembros_eficiencia.add(miembro_eficiencia);
				}
		}
		return miembros_eficiencia;	
		
	}

	
	/*****************************************************************************
    * Metodo que invoca al metodo desvanecer para la grafica de "Mapa de Nodos".
    * @param:   String , String
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    8/Marzo/2011
    * @modify:  8/Marzo/2011
    *****************************************************************************/
	@Override
	public String getDesvanece(String nodo, String strXML)
	{
		XML xml = new XML();
		strXML = xml.getDesvanece(nodo, strXML);
		return strXML;
	}

	
}

/*****************************************************************************
 * Clase de compara 2 valores de una Lista, generalmente usado para ordenar Listas.
 * @param:   
 * @return:  
 * @author:  Alfredo Cesar Cruz Alvarez
 * @date:    8/Marzo/2011
 * @modify:  8/Marzo/2011
 *****************************************************************************/

class MiembroValorComparator implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		Miembro u1 = (Miembro) o1;
		Miembro u2 = (Miembro) o2;
		return (u1.getValor().compareTo(u2.getValor()));
	}

	public boolean equals(Object o) {
		return this == o;
	}
}


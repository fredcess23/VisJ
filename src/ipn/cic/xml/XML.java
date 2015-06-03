package ipn.cic.xml;

import ipn.cic.dao.EsquemaDAO;
import ipn.cic.modelo.Miembro;
import ipn.cic.modelo.Nivel;
import ipn.cic.modelo.Parametros;



import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class XML {

	
    /*****************************************************************************
    * Metodo que arma el xml del "Mapa de Nodos" para la consulta de Eficiencia Grupal
    * @param:   ArrayList<Miembro> miembros, Parametros parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    9/Marzo/2011
    * @modify:  9/Marzo/2011
    *****************************************************************************/
	
	public String formaMapaNodos(ArrayList<Miembro> miembros, Parametros parametros)
	{
		String strXML = "<chart palette='2' bgAlpha='0,0' showBorder='0' enableLinks='1' xAxisMinValue='0' xAxisMaxValue='100' yAxisMinValue='0' " +
				"yAxisMaxValue='100' is3D='1' showFormBtn='0' viewMode='1' exportEnabled='1' exportAtClient='1' exportHandler='fcExporter1'>";

		strXML += dataSet(miembros,parametros);
		strXML += connectors(miembros,parametros);
		strXML +="</chart>";
        
		return strXML;
	}
	

	public String formaMapaNodos2D(ArrayList<Miembro> miembros, Parametros parametros)
	{
		String strXML = "<chart palette='2' bgAlpha='0,0' showBorder='0' enableLinks='0' xAxisMinValue='0' xAxisMaxValue='100' yAxisMinValue='0' " +
				"yAxisMaxValue='100' is3D='0' showFormBtn='0' viewMode='1' exportEnabled='1' exportAtClient='1' exportHandler='fcExporter1'>";

		strXML += dataSetTam(miembros,parametros);
		strXML += connectors(miembros,parametros);
		strXML +="</chart>";
        
		return strXML;
	}
	
	public String formaMapaNodosTamano(ArrayList<Miembro> miembros, Parametros parametros)
	{
		String strXML = "<chart palette='2' bgAlpha='0,0' showBorder='0' enableLinks='1' xAxisMinValue='0' xAxisMaxValue='100' yAxisMinValue='0' " +
				"yAxisMaxValue='100' is3D='1' showFormBtn='0' viewMode='1' exportEnabled='1' exportAtClient='1' exportHandler='fcExporter1'>";

		strXML += dataSet(miembros,parametros);
		strXML += connectors(miembros,parametros);
		strXML +="</chart>";
        
		return strXML;
	}	
	
    /*****************************************************************************
	    * Metodo que arma el xml del "Mapa de Multi Nivel Pie" para la consulta de Eficiencia Grupal
	    * @param:   ArrayList<Miembro> miembros, Parametros parametros
	    * @return:  String
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    25/Marzo/2011
	    * @modify:  25/Marzo/2011
	    *****************************************************************************/
	
	public String formaMapaMultiLevelPie(ArrayList<Miembro> miembros, Parametros parametros)
	{
		
		ArrayList<Miembro> elementos = null;
		List<String> vector = null;
		List<List> matriz = new ArrayList<List>();
		String strXML="<chart palette='3' piefillAlpha='45' pieBorderThickness='3' hoverFillColor='60FF72' " +
				"pieBorderColor='FFFFFF' showShadow='0' shadowColor='C0C0C0' showToolTipShadow='1' baseFontColor='000000' " +
				"baseFontSize='10' useHoverColor='1' caption='Mapa de puntos de interes' exportEnabled='1' exportAtClient='1' exportHandler='fcExporter1'>";
		
		String estilos = "<styles><definition><style name='myBevel' type='bevel' distance='8'/>"+
						"</definition><application><apply toObject='DATAPLOT' styles='myBevel' /></application></styles>";

		EsquemaDAO esquemaDAO = new EsquemaDAO();
		ArrayList<Nivel> niveles = esquemaDAO.getEsquemaDimensionNivel(parametros);
		
		elementos = filtro(miembros,parametros);
		
		Collections.sort(elementos, new MiembroNombreComparator());
		
        
		for(Miembro m: elementos)
		{
        	String []chop = m.getNombre_unico().split("\\. ");
        	vector = Arrays.asList(chop);
        	matriz.add(vector);
        }
        
        	/*Creacion de Arbol*/
        
		Arbol root = null;
        if (root == null)
            root = new Arbol ("dimension");
        
        
        root.insert(vector.get(0));

		for(List<String> renglon: matriz)
		{
			for(int i=1; i<renglon.size();i++)
			{
				root = root.insert(root,renglon.get(i-1),renglon.get(i));	
			}
		}
		
		String strXMLArbol = "";
		strXMLArbol = display(root,"");
		System.out.println("Acabe gracias a Dios");

		
		strXML += strXMLArbol + estilos + "</chart>";
		return strXML;
	}
	
    /*****************************************************************************
	    * Metodo que recorre el arbol generado que almacena la estructura Arbol, que contiene la jerarquia del elemento de interes.
	    * @param:   ArrayList<Miembro> miembros, Parametros parametros
	    * @return:  String
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    25/Marzo/2011
	    * @modify:  25/Marzo/2011
	    *****************************************************************************/
	
	static String display (Arbol a,String strXML)
	{
		for(Arbol hijo : a.hijos)
		{
			strXML += "<category label = '" + hijo.elemento + "'>";
			strXML = display(hijo,strXML);
			strXML += hijo.tag;
			
		}
		return strXML;
	}
	
	
    /*****************************************************************************
	    *Metodo que aplica el filtro (la condicion a aplicar).
	    * @param:   ArrayList<Miembro> miembros, Parametros parametros
	    * @return:  String
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    23/Marzo/2011
	    * @modify:  23/Marzo/2011
	    *****************************************************************************/
	
	public ArrayList<Miembro> filtro(ArrayList<Miembro> miembros, Parametros parametros)
	{
		//ArrayList<String> elementos = new ArrayList<String>();
		ArrayList<Miembro> elementos = new ArrayList<Miembro>();
		
		if(parametros.getTipoEficiencia().equals("Por rango"))
        {
    		int porcentaje_inf = parametros.getRango1();
    		int porcentaje_sup = parametros.getRango2();
            
            for(Miembro miembro: miembros)
            {
            	if( (miembro.getValor() >= porcentaje_inf) && (miembro.getValor() <= porcentaje_sup))
            	{
            		String nombre_unico = miembro.getNombre_unico();
            		nombre_unico = nombre_unico.replaceAll("\\[", " ");
            		nombre_unico = nombre_unico.replaceAll("\\]", "");
            		Miembro m = new Miembro();
            		m.setNombre_unico(nombre_unico);
            		m.setValor(miembro.getValor());
            		elementos.add(m);
            	}  
            }
  	
        }
		
        if(parametros.getTipoEficiencia().equals("Por numero"))
        {
    		int limite = parametros.getRango1();
    		
            for(int i=0;i<limite;i++)
            {
        		String nombre_unico = miembros.get(i).getNombre_unico();
        		nombre_unico = nombre_unico.replaceAll("\\[", " ");
        		nombre_unico = nombre_unico.replaceAll("\\]", "");
        		Miembro m = new Miembro();
        		m.setNombre_unico(nombre_unico);
        		m.setValor(miembros.get(i).getValor());
        		elementos.add(m);
            }
    		
        }
        
		if(parametros.getTipoEficiencia().equals("Por porciento"))
        {
    		int porcentaje = parametros.getRango1();

    		for(Miembro miembro: miembros)
            {	
            	if(parametros.getEficiencia().equals("peores"))
            	{	if(miembro.getValor() <= porcentaje)
	            	{
	            		String nombre_unico = miembro.getNombre_unico();
	            		nombre_unico = nombre_unico.replaceAll("\\[", " ");
	            		nombre_unico = nombre_unico.replaceAll("\\]", "");
	            		Miembro m = new Miembro();
	            		m.setNombre_unico(nombre_unico);
	            		m.setValor(miembro.getValor());
	            		elementos.add(m);
	            		
	            	}
            	}
            	
            	else if(parametros.getEficiencia().equals("mejores"))
	            	{	if(miembro.getValor() >= porcentaje)
		            	{
		            		String nombre_unico = miembro.getNombre_unico();
		            		nombre_unico = nombre_unico.replaceAll("\\[", " ");
		            		nombre_unico = nombre_unico.replaceAll("\\]", "");
		            		Miembro m = new Miembro();
		            		m.setNombre_unico(nombre_unico);
		            		m.setValor(miembro.getValor());
		            		elementos.add(m);
		            		
		            	}
	            	}
            	
		    		else
		    			break;   
            }
        }
		
		return elementos;
	}
	
	
    /*****************************************************************************
	    * Metodo que arma el xml del "Mapa de Calor" para la consulta de Eficiencia Grupal
	    * @param:   ArrayList<Miembro> miembros, Parametros parametros
	    * @return:  String
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    25/Marzo/2011
	    * @modify:  25/Marzo/2011
	    *****************************************************************************/
	
	public String formaMapaHeat(ArrayList<Miembro> miembros, Parametros parametros)
	{
		ArrayList<String> paleta = new ArrayList<String>();
		paleta.add("FFBA7A"); paleta.add("F5FF98"); paleta.add("ADFFA2"); paleta.add("00F48D"); 
		paleta.add("A8FBFF"); paleta.add("38A4FF"); paleta.add("C042FF"); paleta.add("FF666D");
		
		String strXML = "<chart caption='Mapa de puntos de interes' brType='Eficiencia' xAxisName='Nivel' yAxisName='Elemento' canvasBgColor='FFFFFF' is3D='1' mapByCategory='1' exportEnabled='1' exportAtClient='1' exportHandler='fcExporter1'>";
		String estilo ="<styles><definition><style name='brLabelFont' type='font' font='Arial' size='9'color='800080' bold='1'/>" +
						"<style name='datavaluesFont' type='font' font='Arial' size='12' color='2A3FAA' bold='1'/>" +
						"<style name='datavaluesShadow' type='shadow' strength='2' quality='1'/></definition>" +
						"<application><apply toObject='datavalues' styles='datavaluesFont'/>" +
						"<apply toObject='brdatavalues' styles='brLabelFont'/></application></styles>";
		
		ArrayList<Miembro> elementos = null;
		List<String> vector = null;
		List<List> matriz = new ArrayList<List>();
		String nodo = "";
		
		EsquemaDAO esquemaDAO = new EsquemaDAO();
		ArrayList<Nivel> niveles = esquemaDAO.getEsquemaDimensionNivel(parametros);
		
		elementos = filtro(miembros,parametros);
		
		
		Collections.sort(elementos, new MiembroNombreComparator());
        
        for(Miembro m: elementos)
        {
        	String []chop = m.getNombre_unico().split("\\. ");
        	vector = Arrays.asList(chop);
        	matriz.add(vector);
        }

        
        String colorRange = "<colorRange>";
        int indiceColor = 0;
		for(List<String> renglon: matriz)
		{
			colorRange += "<color code ='" + paleta.get(indiceColor) + "' label='"+renglon.get(renglon.size()-1) + "'/>";
			
			indiceColor++;
			if(indiceColor == paleta.size())
				indiceColor = 0;		
				
		}
		colorRange += "</colorRange>";
        
        String set="<dataset>";
        int i=0;
        int indiceNivel=0;
		for(List<String> renglon: matriz)
		{
			i++;
			indiceNivel = 0;
			nodo="";
			for(String celda : renglon)
			{
				
				nodo += "[" + celda + "].";
				set += "<set rowId='" + i +"' columnId='" + niveles.get(indiceNivel).getNombre() + "' colorRangeLabel='" + renglon.get(renglon.size()-1) +"' displayValue='" 
				+ celda + "'  link='"+ "javaScript:eficienciaNivel1(" + "&quot;" + nodo + "&quot;);" +"'/>";
				indiceNivel++;
					
			}
			
		}
		
        set += "</dataset>";
        strXML += colorRange + estilo + set + "</chart>";
        
		return strXML;
	}
	
	
    /*****************************************************************************
	    *Metodo que crea el xml con los nodos del "Mapa de Nodos" , para la consulta de Eficiencia Grupal
	    * @param:   ArrayList<Miembro> miembros, Parametros parametros
	    * @return:  String
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    9/Marzo/2011
	    * @modify:  9/Marzo/2011
	    *****************************************************************************/
	
	private String dataSetTam(ArrayList<Miembro> miembros, Parametros parametros)
	{
		ArrayList<String> paleta = new ArrayList<String>();
		paleta.add("FF8D22"); paleta.add("FFFF04"); paleta.add("B4FF0E"); paleta.add("60FF72"); 
		paleta.add("1EFFF9"); paleta.add("38A4FF"); paleta.add("C042FF"); paleta.add("FF3C49");
		DecimalFormat formateador = new DecimalFormat("####.00");
		//formateador.format
		
		ArrayList<String> sets = new ArrayList<String>();
		ArrayList<Miembro> elementos = null;
		String strSeries = "<dataset seriesName='DS1'>";
		String strSerie;
		int x;
		int y;
		int indiceColor;
        
		elementos = filtro(miembros,parametros);
		Collections.sort(elementos, new MiembroNombreComparator());
        
		
		List<String> rojos = new ArrayList<String>();
		rojos.add("FA5858");
		rojos.add("FE2E2E");
		rojos.add("FF0000");
		rojos.add("DF0101");
		rojos.add("B40404");
		rojos.add("8A0808");
		rojos.add("610B0B");
		int indColor = 0;
		
		List <Miembro>nodos = null;
		nodos = (List <Miembro>) elementos.clone();
		Collections.sort(nodos,new MiembroNumeroComparator());
		
		List <Miembro>radio = new ArrayList();
		int r = 30;
		
			//Radio
		for(int j=0; j< nodos.size(); j++)
		{
			nodos.get(j).setRadio(r);
			r+=4;
		}
			//Color
		
		for(int j=0; j< nodos.size(); j++)
		{
			nodos.get(j).setColor(rojos.get(indColor));
			
			if(indColor<rojos.size())
				indColor++;
			if(indColor == rojos.size()) 
				indColor=0;
			

		}
		
		for(int i=0; i< elementos.size(); i++)
			for(int j=0; j< nodos.size(); j++)
			{
				if(elementos.get(i).getNombre_unico().equals(nodos.get(j).getNombre_unico()))
				{					
					elementos.get(i).setRadio(nodos.get(j).getRadio());
					elementos.get(i).setColor(nodos.get(j).getColor());
				}
					
			}
		
		y = 92;
		
		for(int j=0;j<elementos.size();j++)
		{
			x = 10;
			indiceColor = 0;
			
			String []chop = elementos.get(j).getNombre_unico().split("\\. ");
			String union = "";
			
			for(int i=0;i<chop.length;i++)
    	    {
				union += "[" + chop[i] + "].";
				
				if(i==chop.length-1){
    	    		strSerie = "<set x='"+x+"' y='"+y+"' radius='"+ elementos.get(j).getRadio()+"' shape='circle' name='"+chop[i] + " : " + formateador.format(elementos.get(j).getValor())+ "%' " +
    	    				"color='"+ elementos.get(j).getColor()+"' id='"+chop[i]+"'/>";
							//"color='"+ paleta.get(paleta.size()-1)+"' id='"+chop[i]+"' link='"+ "javaScript:desvanece(" + "&quot;" + union + "&quot;);javaScript:eficienciaNivel1(" + "&quot;" + union + "&quot;);" +"'/>";
				}
				else
    	    		strSerie = "<set x='"+x+"' y='"+y+"' radius='30' shape='circle' name='"+chop[i]+"' " +
    	    				"color='"+paleta.get(indiceColor)+"' id='"+chop[i]+"' link='"+ "javaScript:eficienciaNivel1(" + "&quot;" + union + "&quot;);" +"'/>";
    	    	
    	    	if(!sets.contains(chop[i]))
    	    	{
    	    		sets.add(chop[i]);
    	    		strSeries += strSerie;
    	    	}
    	    	
    	    	x+=12;
    	    	indiceColor++;
    	    }
    	    
    	    y-=14;
		}
        strSeries += "</dataset>";
    	
        return strSeries;
	}
	

	
	private String dataSet(ArrayList<Miembro> miembros, Parametros parametros)
	{
		ArrayList<String> paleta = new ArrayList<String>();
		paleta.add("FF8D22"); paleta.add("FFFF04"); paleta.add("B4FF0E"); paleta.add("60FF72"); 
		paleta.add("1EFFF9"); paleta.add("38A4FF"); paleta.add("C042FF"); paleta.add("FF3C49");
		DecimalFormat formateador = new DecimalFormat("####.00");
		//formateador.format
		
		ArrayList<String> sets = new ArrayList<String>();
		ArrayList<Miembro> elementos = null;
		String strSeries = "<dataset seriesName='DS1'>";
		String strSerie;
		int x;
		int y;
		int indiceColor;
        
		elementos = filtro(miembros,parametros);
		Collections.sort(elementos, new MiembroNombreComparator());
  	
		y = 92;
		
		for(int j=0;j<elementos.size();j++)
		{
			x = 10;
			indiceColor = 0;
			
			String []chop = elementos.get(j).getNombre_unico().split("\\. ");
			String union = "";
			
			for(int i=0;i<chop.length;i++)
    	    {
				union += "[" + chop[i] + "].";
				
				if(i==chop.length-1)
    	    		strSerie = "<set x='"+x+"' y='"+y+"' radius='30' shape='circle' name='"+chop[i] + " : " + formateador.format(elementos.get(j).getValor())+ "%' " +
    	    				"color='"+ paleta.get(paleta.size()-1)+"' id='"+chop[i]+"' link='"+ "javaScript:desvanece(" + "&quot;" + union + "&quot;);" +"'/>";
							//"color='"+ paleta.get(paleta.size()-1)+"' id='"+chop[i]+"' link='"+ "javaScript:desvanece(" + "&quot;" + union + "&quot;);javaScript:eficienciaNivel1(" + "&quot;" + union + "&quot;);" +"'/>";
    	    	else
    	    		strSerie = "<set x='"+x+"' y='"+y+"' radius='30' shape='circle' name='"+chop[i]+"' " +
    	    				"color='"+paleta.get(indiceColor)+"' id='"+chop[i]+"' link='"+ "javaScript:eficienciaNivel1(" + "&quot;" + union + "&quot;);" +"'/>";
    	    	
    	    	if(!sets.contains(chop[i]))
    	    	{
    	    		sets.add(chop[i]);
    	    		strSeries += strSerie;
    	    	}
    	    	
    	    	x+=12;
    	    	indiceColor++;
    	    }
    	    
    	    y-=14;
		}
        strSeries += "</dataset>";
    	
        return strSeries;
	}
	
	
    /*****************************************************************************
    *Metodo que arma el xml de las conexiones de los nodos del "Mapa de Nodos", para la consulta de Eficiencia Grupal
    * @param:   ArrayList<Miembro> miembros, Parametros parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    9/Marzo/2011
    * @modify:  9/Marzo/2011
    *****************************************************************************/
	
	private String connectors(ArrayList<Miembro> miembros, Parametros parametros)
	{
		String strConnectors = "<connectors color='FF0000' stdThickness='5'>";
		
		if(parametros.getTipoEficiencia().equals("Por rango"))
        {
    		int porcentaje_inf = parametros.getRango1();
    		int porcentaje_sup = parametros.getRango2();
            
            for(Miembro miembro: miembros)
            {
            	if( (miembro.getValor() >= porcentaje_inf) && (miembro.getValor() <= porcentaje_sup))
            	{
            		String nombre_unico = miembro.getNombre_unico();
            		nombre_unico = nombre_unico.replaceAll("\\[", " ");
            		nombre_unico = nombre_unico.replaceAll("\\]", "");
            		String []chop = nombre_unico.split("\\. ");
            	    
            	    for(int i=0;i<chop.length-1;i++)
            	    {
            	    	strConnectors += "<connector strength='0.3' from='"+chop[i]+"' to='"+chop[i+1]+"' color='4281FF' arrowAtStart='0' arrowAtEnd='1' />";
            	    }	
            	}  
            }
            strConnectors += "</connectors>";
        }
		
		if(parametros.getTipoEficiencia().equals("Por porciento"))
        {
    		int porcentaje = parametros.getRango1();
            
            for(Miembro miembro: miembros)
            {
            	if(parametros.getEficiencia().equals("peores"))
            	{
            		if(miembro.getValor() <= porcentaje)
	            	{
	            		
	            		String nombre_unico = miembro.getNombre_unico();
	            		nombre_unico = nombre_unico.replaceAll("\\[", " ");
	            		nombre_unico = nombre_unico.replaceAll("\\]", "");
	            		String []chop = nombre_unico.split("\\. ");
	            	    
	            	    for(int i=0;i<chop.length-1;i++)
	            	    {
	            	    	strConnectors += "<connector strength='0.3' from='"+chop[i]+"' to='"+chop[i+1]+"' color='4281FF' arrowAtStart='0' arrowAtEnd='1' />";
	            	    }	
          	    
	            	}
            	}
            	else if(parametros.getEficiencia().equals("mejores"))
            	{	
            		if(miembro.getValor() >= porcentaje)
	            	{
	            		
	            		String nombre_unico = miembro.getNombre_unico();
	            		nombre_unico = nombre_unico.replaceAll("\\[", " ");
	            		nombre_unico = nombre_unico.replaceAll("\\]", "");
	            		String []chop = nombre_unico.split("\\. ");
	            	    
	            	    for(int i=0;i<chop.length-1;i++)
	            	    {
	            	    	strConnectors += "<connector strength='0.3' from='"+chop[i]+"' to='"+chop[i+1]+"' color='4281FF' arrowAtStart='0' arrowAtEnd='1' />";
	            	    }	
          	    
	            	}            		
            	}
            	
            	else
            		break;   
            }
           
            strConnectors += "</connectors>";
        }
		
        if(parametros.getTipoEficiencia().equals("Por numero"))
        {
    		int limite = parametros.getRango1();
    		
            for(int i=0;i<limite;i++)
            {
            	String nombre_unico = miembros.get(i).getNombre_unico();
        		nombre_unico = nombre_unico.replaceAll("\\[", " ");
        		nombre_unico = nombre_unico.replaceAll("\\]", "");
        		String []chop = nombre_unico.split("\\. ");
        	    
        	    for(int j=0;j<chop.length-1;j++)
        	    {
        	    	strConnectors += "<connector strength='0.3' from='"+chop[j]+"' to='"+chop[j+1]+"' color='4281FF' arrowAtStart='0' arrowAtEnd='1' />";
        	    }	
   		        
            }
            strConnectors += "</connectors>";	
        }
		
		return strConnectors;
	}
	
	
    /*****************************************************************************
    * Metodo que inhibe los nodos No seleccionados del "Mapa de Nodos" para la consulta de Eficiencia Grupal
    * @param:   ArrayList<Miembro> miembros, Parametros parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    9/Marzo/2011
    * @modify:  9/Marzo/2011
    *****************************************************************************/
	
	public String getDesvanece(String nodo, String XML)
	{
		String strXML = "<chart palette='2' bgAlpha='30' showBorder='0' enableLinks='1' xAxisMinValue='0' xAxisMaxValue='100' yAxisMinValue='0' " +
		"yAxisMaxValue='100' is3D='1' showFormBtn='0' viewMode='1'>";
		
		
		nodo = nodo.replaceAll("\\[","");
		nodo = nodo.replaceAll("\\]","");
		String []chop = nodo.split("\\.");
		List<String> list_nodo = new ArrayList<String>();
		list_nodo = Arrays.asList(chop);
		
		String []chopXML = XML.split("<");
		List<String> listXML = new ArrayList<String>();
		listXML =  Arrays.asList(chopXML);
		
		List<String>  listSets = new ArrayList<String>();
		String cadenaXML = "";
		String subcadenaXML="";
		
		for(String elemento:listXML)
	    {
			if(elemento.indexOf("set")>=0)
			{
				if(elemento.indexOf("dataset")<0)
				{
					listSets.add("<" + elemento);
				}
					
			}
	    }
		
		ArrayList<String> listSetsEnable = new ArrayList<String>();
		ArrayList<String> listSetsDisable = new ArrayList<String>();
		ArrayList<String> nodos = new ArrayList<String>();
		
		
		for(String lineaSet:listSets)
		{
			for(String elemento: list_nodo)		
			{
				subcadenaXML = "<" + lineaSet.substring(0, lineaSet.indexOf("link")) + "/>";
				if(subcadenaXML.indexOf(elemento) > 0)
				{
					if(!listSetsEnable.contains(lineaSet))
					{
						if(!nodos.contains(elemento))
						{
							listSetsEnable.add(lineaSet);
							nodos.add(elemento);
							break;
						}
					}
				}				
			}
			
		}
		
		
		listSets.removeAll(listSetsEnable);
		
		
		for(String elemento:listSets)
		{
			cadenaXML = elemento;
			cadenaXML = cadenaXML.replace("FF8D22", "FFFAF0");
			cadenaXML = cadenaXML.replace("FFFF04", "FFFAF0");
			cadenaXML = cadenaXML.replace("B4FF0E", "FFFAF0");
			cadenaXML = cadenaXML.replace("60FF72", "FFFAF0");
			cadenaXML = cadenaXML.replace("1EFFF9", "FFFAF0");
			cadenaXML = cadenaXML.replace("38A4FF", "FFFAF0");
			cadenaXML = cadenaXML.replace("C042FF", "FFFAF0");
			cadenaXML = cadenaXML.replace("FF3C49", "FFFAF0");
			
			listSetsDisable.add(cadenaXML);
		}
		
		List<String> Sets = new ArrayList<String>(); 
		Sets.addAll(listSetsEnable); 
		Sets.addAll(listSetsDisable); 
		
		
		List<String> listConnectors = new ArrayList<String>();
		
		for(String elemento:listXML)
	    {
			if(elemento.indexOf("connector")>=0)
			{
				if(elemento.indexOf("connectors")<0)
					listConnectors.add("<"+elemento);
			}
	    }
		
		ArrayList<String> listConnectorsEnable = new ArrayList<String>();
		ArrayList<String> listConnectorsDisable = new ArrayList<String>();
		
		for(String lineaconector:listConnectors)
			for(int i=0; i<list_nodo.size()-1;i++)		
			{
				if(lineaconector.indexOf(list_nodo.get(i)) > 0)
				{
					if(lineaconector.indexOf(list_nodo.get(i+1)) > 0)
					{
						listConnectorsEnable.add(lineaconector);
					}
					
				}
			}
		
		listConnectors.removeAll(listConnectorsEnable);
		
		for(String lineaconector:listConnectors)
		{
			cadenaXML = lineaconector;
			cadenaXML = cadenaXML.replace("4281FF", "030046");
			listConnectorsDisable.add(cadenaXML);
		}
		
		List<String> connectors = new ArrayList<String>(); 
		connectors.addAll(listConnectorsEnable); 
		connectors.addAll(listConnectorsDisable); 
		
		
		String strSeries = "<dataset seriesName='DS1'>";
		for(String elemento: Sets){
			strSeries += elemento;
		}
		strSeries += "</dataset>";
		
		String strConnectors = "<connectors color='FF0000' stdThickness='5'>";
		for(String conector: connectors){
			strConnectors += conector;
		}
		strConnectors += "</connectors>";
		
		strXML += strSeries + strConnectors + "</chart>";
		
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

class MiembroNombreComparator implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		Miembro u1 = (Miembro) o1;
		Miembro u2 = (Miembro) o2;
		return (u1.getNombre_unico().compareTo(u2.getNombre_unico()));
	}

	public boolean equals(Object o) {
		return this == o;
	}
}


class MiembroNumeroComparator implements Comparator
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
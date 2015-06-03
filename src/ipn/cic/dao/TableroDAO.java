package ipn.cic.dao;


import ipn.cic.modelo.Parametros;

import java.sql.Connection;
import java.util.ArrayList;

import org.olap4j.Cell;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.olap4j.Position;
import org.olap4j.metadata.Member;

public class TableroDAO 
{
	ArrayList<String> tiempoTrimestre = null;
	ArrayList<String> elementoNivel1 = null;
	
	Connection connection;
	OlapConnection olapConnection;
	OlapStatement statement;	
	
	/*****************************************************************************
    *Metodo que obtiene las categorias (Hijos) de un nivel de una dimension.
    * @param:   
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @throws  
    * @date:    27/Febrero/2011
    * @modify:  28/Febrero/2011
    *****************************************************************************/
	public String getChildrenMemberNameDimension1(Parametros parametros)
	{
		String categorias= "<categories>";
		String dimension="";
		
		try{
	         String mdx = "select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS," +
	                	  " {"+ parametros.getNodo() +"Children} ON ROWS " +
	                	  " from [" + parametros.getCubo() +"] " +
	                	  "where {"+parametros.getYear()+"}";
	         
   	         connection = ConexionOLAP.getConexionOLAP();
	         olapConnection = connection.unwrap(OlapConnection.class);
	         statement = olapConnection.createStatement();
	         CellSet cellSet = statement.executeOlapQuery(mdx);
	                		
            for (Position row : cellSet.getAxes().get(1))
            {
                for (Member member : row.getMembers())
                {                   
                	System.out.println(member.getUniqueName());
                	dimension = member.getName();
                    categorias += "<category label='"+dimension+"'/>";
                }
            }
            categorias += "</categories>";
	            
	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	     }
	     finally
	     {
	    	 try
	    	 {
		    	 olapConnection.close();
		    	 connection.close();  
	    		 
	    	 }
	    	 catch(Exception e)
		     {
		         e.printStackTrace();
		     }
	 
	     }        
	
		return categorias;
	}
	
	
	/*****************************************************************************
    *Metodo que obtiene los valores Hijos de un nivel de una dimension.
    * @param:   
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @throws  
    * @date:    27/Febrero/2011
    * @modify:  28/Febrero/2011
    *****************************************************************************/
	
	public String getChildrenMemberValueDimension1(Parametros parametros)
	{
		String dataset = "<dataset seriesName='"+parametros.getYear()+"'>";
		String dimension="";
		
		try{
			Double medida_num = null;
	     	String mdx = "select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS," +
						 " {"+ parametros.getNodo() +"Children} ON ROWS " +
						 " from [" + parametros.getCubo() +"] " +
						 " where {"+parametros.getYear()+"}";
	     	
	     	
	     	connection = ConexionOLAP.getConexionOLAP();
	     	olapConnection = connection.unwrap(OlapConnection.class);
	     	statement = olapConnection.createStatement();
	     	CellSet cellSet = statement.executeOlapQuery(mdx);	     	
     
			 for (Position row : cellSet.getAxes().get(1))
			 {
			     for (Position column : cellSet.getAxes().get(0))
			     {
			         for (Member member : row.getMembers())
			         {            
			         	dimension = member.getUniqueName();
			         	System.out.println(dimension);
			         }
			     	 
				     String strLink = "javaScript:tableroEficiencia(" + "&quot;" + dimension + "&quot; , &quot;"+ parametros.getYear() + "&quot;);";   
				     final Cell cell = cellSet.getCell(column, row);
				     medida_num = (Double)cell.getValue();
				     System.out.println(medida_num);
				     dataset += "<set value='" +medida_num+ "' link='"+ strLink + "'/>";     
			     }
			 }
			 dataset+="</dataset>";

	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	     }

	     return dataset;
     }
	 
 
	/*****************************************************************************
    *Metodo que obtiene los Trimestres del tiempo, para ser usados como "categories".
    * @param:   
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @throws  
    * @date:    18/Marzo/2011
    * @modify:  18/Marzo/2011
    *****************************************************************************/
	public String getChildrenMemberNameDimension2(Parametros parametros)
	{
		String categorias= "<categories>";
	 	String dimension="";
	 	String mdx="";
	 	tiempoTrimestre = new ArrayList<String>();
		
		try{
	         
	         mdx = 	"select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
		     		"{"+parametros.getYear()+".Children}" +
					" ON ROWS " +
					" from [" + parametros.getCubo() +"] ";
		    		
	     	connection = ConexionOLAP.getConexionOLAP();
	     	olapConnection = connection.unwrap(OlapConnection.class);
	     	statement = olapConnection.createStatement();
	        CellSet cellSet = statement.executeOlapQuery(mdx);
	         
		     for (Position row : cellSet.getAxes().get(1))
		     {
		         for (Member member : row.getMembers())
		         {                  
		         	System.out.println(member.getUniqueName());
		         	dimension = member.getName();
		         	tiempoTrimestre.add(dimension);
		         	categorias += "<category label='"+dimension+"'/>";
		             
		         }
		     }
		     
		     categorias += "</categories>";
		            
	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	     }
	     finally
	     {
	    	 try
	    	 {
		    	 olapConnection.close();
		    	 connection.close();  
	    		 
	    	 }
	    	 catch(Exception e)
		     {
		         e.printStackTrace();
		     }
	 
	     }
	     
	     return categorias;
	}
	
	
	/*****************************************************************************
	*Metodo que obtiene los valores Hijos de un nivel de 2 dimensiones. (Interes y tiempo).
	* @param:   
	* @return:  String
	* @author:  Alfredo Cesar Cruz Alvarez
	* @throws  
	* @date:    18/Marzo/2011
	* @modify:  18/Marzo/2011
	*****************************************************************************/
	
	 public String getChildrenMemberValueDimension2(Parametros parametros)
	 {
		 String dataset="";
		 String set = "";
		 String dimension="";
		 String mdx="";
		 int contador = 0;
		 ArrayList<String> elemento = new ArrayList<String>();
			
	     try{
	
	         Double medida_num = null;
      
	         mdx = 	"select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
	     		"Crossjoin(" +
	    		"{"+ parametros.getNodo() +".Children}, " +
		 		"{"+parametros.getYear()+".Children}" +
					") ON ROWS " +
					" from [" + parametros.getCubo() +"] ";
	    		
			connection = ConexionOLAP.getConexionOLAP();
			olapConnection = connection.unwrap(OlapConnection.class);
			statement = olapConnection.createStatement();
	        CellSet cellSet = statement.executeOlapQuery(mdx);
	         
	         for (Position row : cellSet.getAxes().get(1))
	         {
	        	set = "";

				for (Member member : row.getMembers())
				{                  
					dimension = member.getName();
					if(!tiempoTrimestre.contains(dimension))
					{
						if(!elemento.contains(dimension))
						{
							dataset += "<dataset seriesName='"+dimension+"'>";
							elemento.add(dimension);
						}
						
					}
				    
				}
	                
				for (Position column : cellSet.getAxes().get(0))
				{
					final Cell cell = cellSet.getCell(column, row);
					medida_num = (Double)cell.getValue();
					System.out.println(medida_num);
					set += "<set value='" +medida_num + "'/>";
						
				}
				
				dataset += set;
				contador++;
				
				if(contador==tiempoTrimestre.size())
				{
					dataset += "</dataset>";
					contador = 0;
				}
				
	         }

	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	     }
	
	     return dataset;
	 }
	 
	 
    /*****************************************************************************
    *Metodo que obtiene los almacenes, para ser usados como "categories".
    * @param:   
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @throws  
    * @date:    18/Marzo/2011
    * @modify:  18/Marzo/2011
    *****************************************************************************/
	public String getChildrenMemberNameDimension3(Parametros parametros)
	{
	 	String categorias= "<categories>";
	 	String dimension="";
	 	String mdx="";
	 	elementoNivel1 = new ArrayList<String>();
		
		try{
	         
	         mdx = 	"select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
	         		"{"+ parametros.getNodo() +".Children} " +
					"ON ROWS " +
					" from [" + parametros.getCubo() +"] " +
					"where {"+parametros.getYear()+"}";
		    		
	     	connection = ConexionOLAP.getConexionOLAP();
	     	olapConnection = connection.unwrap(OlapConnection.class);
	     	statement = olapConnection.createStatement();
	        CellSet cellSet = statement.executeOlapQuery(mdx);
	         
	         for (Position row : cellSet.getAxes().get(1))
	         {
	        	 for (Member member : row.getMembers())
	        	 {
	        		 System.out.println(member.getUniqueName());
	        		 dimension = member.getName();
	        		 elementoNivel1.add(dimension);
	        		 categorias += "<category label='"+dimension+"'/>";            
	        	 }
	         }
	         categorias += "</categories>";
     	
		}
		catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	    	try
	    	{
	    		olapConnection.close();
	    		connection.close();  
    		}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }        
	
		return categorias;
	}
	
	
	/*****************************************************************************
    *Metodo que obtiene los valores Hijos de un nivel de 2 dimensiones. (Interes y tiempo).
    * @param:   
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @throws  
    * @date:    18/Marzo/2011
    * @modify:  18/Marzo/2011
    *****************************************************************************/
	
	 public String getChildrenMemberValueDimension3(Parametros parametros)
	 {
	 	String dataset="";
		String set = "";
		String dimension="";
		String dimension_unica="";
		String mdx = "";
		String strLink = "";
		String subStrLink = "";
		int contador = 0;
		ArrayList<String> store = new ArrayList<String>();

		try{
	
	         Double medida_num = null;
   
	         mdx = 	"select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
	     		"Crossjoin(" +
	    		"{[Store].Children}," +
	    		"{"+ parametros.getNodo() +".Children} " +
				") ON ROWS " +
				" from [" + parametros.getCubo() +"] " +
				"where {"+parametros.getYear()+"}";
	    		
	     	connection = ConexionOLAP.getConexionOLAP();
	     	olapConnection = connection.unwrap(OlapConnection.class);
	     	statement = olapConnection.createStatement();
	     	CellSet cellSet = statement.executeOlapQuery(mdx);
	         
	         for (Position row : cellSet.getAxes().get(1))
	         {
	        	set = "";
	        	
				for (Member member : row.getMembers())
				{                  
					dimension = member.getName();
					dimension_unica = member.getUniqueName().replace(" ","*");
					
					if(!elementoNivel1.contains(dimension))
					{
						if(!store.contains(dimension))
						{
							dataset += "<dataset seriesName='"+dimension+"'>";
							subStrLink = dimension_unica;
							store.add(dimension);
						}
						
					}
				    
				}
	                
				for (Position column : cellSet.getAxes().get(0))
				{
					
					strLink = "javaScript:eficienciaNivel4(" + "&quot;" + dimension_unica + "&quot; , &quot;"+ subStrLink + 
					"&quot; , &quot;"+ parametros.getYear() +"&quot;);";
					
					final Cell cell = cellSet.getCell(column, row);
					medida_num = (Double)cell.getValue();
					System.out.println(medida_num);
					set += "<set value='" +medida_num + "' link='"+ strLink + "'/>";
						
				}
				
				dataset += set;
				contador++;
				
				if(contador==elementoNivel1.size())
				{
					dataset += "</dataset>";
					contador = 0;
				}
			
	         }

	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	     }
	
	     return dataset;
	 }
	 
	 public String getChildrenMemberValueDimension4(Parametros parametros)
	 {

		 String nombre_dimension = "";
		 String nombre_unico_dimension = "";
		 String mdx = "";
		 /*
		 String strXML = "<chart caption='" + parametros.getHecho() + " by Store" +
			"   -   " + "Dimension : " +parametros.getDimension() + "  -  Elemento: " + parametros.getNombreSubNodo() +
			"  -  "+ parametros.getYear() + "' xAxisName='" + parametros.getNombreNodo() +"' yAxisName='" + parametros.getHecho() + "' pieSliceDepth='30' showBorder='1' formatNumberScale='0' numberPrefix='$'>";
		 */
		 String strXML = "<map borderColor='005879' fillColor='D7F4FF' numberPrefix='$' includeValueInLabels='1' labelSepChar=': ' baseFontSize='10'>";
		 String entityDef = "<entityDef><entity internalId='02' newId='BC'/>" +
	 	 			"<entity internalId='032' newId='DF'/><entity internalId='011' newId='Guerrero'/>" +
	 	 			"<entity internalId='013' newId='Jalisco'/><entity internalId='029' newId='Veracruz'/>" +
	 	 			"<entity internalId='030' newId='Yucatan'/><entity internalId='031' newId='Zacatecas'/>" +
	 	 			"</entityDef><data>";
		 
		 strXML += entityDef;
		 try
		 {
			 
			 Double medida = null;
			 
	         mdx = 	"select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
	     		"{" + parametros.getSubNodo() + ".Children} " +
				" ON ROWS " +
				" from [" + parametros.getCubo() +"] " +
				" where ("+ parametros.getNodo() +","+parametros.getYear()+")";
	    		         
	     	connection = ConexionOLAP.getConexionOLAP();
	     	olapConnection = connection.unwrap(OlapConnection.class);
	     	statement = olapConnection.createStatement();
	     	CellSet cellSet = statement.executeOlapQuery(mdx);
	        
	         for (Position row : cellSet.getAxes().get(1))
	         {
	        	 for (Position column : cellSet.getAxes().get(0))
	        	 {
	        		 for (Member member : row.getMembers()) 
	        		 {
	        			 nombre_dimension = member.getName();
	        			 nombre_unico_dimension = member.getUniqueName();
	        		 }
			
	        		 final Cell cell = cellSet.getCell(column, row);
	        		 medida = (Double)cell.getValue();
	        		 System.out.println(medida);
			
	        		 String strLink = "javaScript:eficienciaNivel5(" + "&quot;" + parametros.getNodo() + 
	        		 					"&quot; , &quot;"+ nombre_unico_dimension + "&quot; , &quot;"+ parametros.getYear() +"&quot;);";
	        		 
	        		 strXML = strXML + "<entity id='" + nombre_dimension + "' value='" +medida + "' link='" + strLink + "' color='F9FF92'/>";
			        
	        	 }
	         }
	            
	         strXML += "</data></map>";	         
	    		         
		 }
		 catch(Exception e)
	     {
	         e.printStackTrace();
	     }
		 
		 return strXML;
	 }
	 
	 
	 public String getChildrenMemberValueDimension5(Parametros parametros)
	 {

		 String nombre_dimension = "";
		 String mdx = "";
		 String strXML = "<chart caption='" + parametros.getHecho() + " by Store" +
			"   -   " + "Dimension : " +parametros.getDimension() + "  -  Elemento: " + parametros.getNombreSubNodo() +
			"  -  "+ parametros.getYear() + "' xAxisName='" + parametros.getNombreNodo() +"' yAxisName='" + parametros.getHecho() + "' showBorder='1' formatNumberScale='0' numberPrefix='$'>";
		 try
		 {
			 
			 Double medida = null;
	         
	         mdx = 	"select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
	     		"{" + parametros.getSubNodo() + ".Children} " +
				" ON ROWS " +
				" from [" + parametros.getCubo() +"] " +
				" where ("+ parametros.getNodo() +","+parametros.getYear()+")";
	    		         
	     	connection = ConexionOLAP.getConexionOLAP();
	     	olapConnection = connection.unwrap(OlapConnection.class);
	     	statement = olapConnection.createStatement();
	        CellSet cellSet = statement.executeOlapQuery(mdx);
	        
	         for (Position row : cellSet.getAxes().get(1))
	         {
	        	 for (Position column : cellSet.getAxes().get(0))
	        	 {
	        		 for (Member member : row.getMembers()) 
	        		 {
	        			 nombre_dimension = member.getName();
	        		 }
			
	        		 final Cell cell = cellSet.getCell(column, row);
	        		 medida = (Double)cell.getValue();
	        		 System.out.println(medida);
			
	        		 strXML = strXML + "<set label='" + nombre_dimension + "' value='" +medida + "'/>";
			        
	        	 }
	         }
	            
	         strXML += "</chart>";	         
	    		         
		 }
		 catch(Exception e)
	     {
	         e.printStackTrace();
	     }
		 
		 return strXML;
	 }

	
}

package ipn.cic.dao;

import ipn.cic.modelo.Miembro;
import ipn.cic.modelo.Parametros;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.olap4j.Cell;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapDatabaseMetaData;
import org.olap4j.OlapStatement;
import org.olap4j.Position;
import org.olap4j.metadata.Member;

public class EficienciaDAO {
	
	Connection connection;
	OlapConnection olapConnection;
	OlapStatement statement;
	

	
    /*****************************************************************************
    *Metodo que obtiene todos los miembros de un cubo definido en el esquema.
    * @param:   
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @throws SQLException 
    * @date:    27/Febrero/2011
    * @modify:  28/Febrero/2011
    *****************************************************************************/
	
	public ArrayList<Miembro> getALLMembers(Parametros parametros)
	{
		
		ArrayList<Miembro> miembros = new ArrayList<Miembro>();
		Miembro miembro = null;
		 
	    try{

	         Double double_medida = 0.0;
	         String mdx  = "";
	         String rango = parametros.getDom()!=null? parametros.getDom() : "";
	         
	         if(parametros.getNivel().indexOf("All") > 0)
	         {
	        	 mdx = "select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
	 				"{["+parametros.getDimension()+"].members} ON ROWS " +
	 				"from ["+parametros.getCubo()+"] " +
	 				"where {("+parametros.getYear()+ rango +")}";
	         }
	         else
	         {
		         mdx = "select {[Measures].["+parametros.getHecho()+"]} ON COLUMNS, " +
	 				"{["+parametros.getDimension()+"].["+parametros.getNivel()+"].members} ON ROWS " +
	 				"from ["+parametros.getCubo()+"] " +
	 				//"where {"+parametros.getYear()+"}";
	 				"where {("+parametros.getYear()+ rango +")}";
	         }
	        	 
	         System.out.println(mdx);
	         
	         connection = ConexionOLAP.getConexionOLAP();
	         olapConnection = connection.unwrap(OlapConnection.class);
	         statement = olapConnection.createStatement();
	         CellSet cellSet = statement.executeOlapQuery(mdx);
	            		 
	
	         for (Position row : cellSet.getAxes().get(1))
	         {
	        	 miembro = new Miembro();
	             
	        	 for (Position column : cellSet.getAxes().get(0))
	             {
	                 for (Member member : row.getMembers()) 
	                 {
	                     miembro.setNombre_unico(member.getUniqueName());                     
	                     miembro.setNombre(member.getName());
	                 }
	                 
	                 final Cell cell = cellSet.getCell(column, row);
	                 double_medida = (Double) cell.getValue();
	                 
	                 if(double_medida!=null)
	                 	 double_medida = (double) Math.round(double_medida);
	                
	                 miembro.setValor(double_medida);
	                
	             }
	        	 
	        	 //if(miembros_esquema.contains(miembro.getNombre_unico()))
	        		 miembros.add(miembro);
	         }
	
	
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
	 
	     return miembros;
     }    	

    
}
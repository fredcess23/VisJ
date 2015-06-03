package ipn.cic.dao;

import ipn.cic.modelo.Miembro;
import ipn.cic.modelo.Nivel;
import ipn.cic.modelo.Parametros;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.olap4j.OlapConnection;
import org.olap4j.OlapDatabaseMetaData;
import org.olap4j.metadata.NamedList;
import org.olap4j.metadata.Schema;

public class EsquemaDAO {
	
	Connection connection;
	OlapConnection olapConnection;
	OlapDatabaseMetaData meta;
	
	public EsquemaDAO()
	{
		try
		{
			connection = ConexionOLAP.getConexionOLAP();
    		olapConnection = connection.unwrap(OlapConnection.class);
    		meta = olapConnection.getMetaData();
		}
        catch(Exception e)
        {
            e.printStackTrace();
        }		
		
	}
	
	
    /*****************************************************************************
	    *Metodo que recupera los cubos definidos en el esquema.
	    * @param:   
	    * @return:  ArrayList<String>
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    27/Febrero/2011
	    * @modify:  27/Febrero/2011
	    *****************************************************************************/
	
    public ArrayList<String> getEsquemaCubos(){
    	
    	//Connection connection;
    	ArrayList<String> cubos = new ArrayList<String>();
    	
    	try{
    		//connection = (new ConexionOLAP("1997").getConexionOLAP());
    		//connection = ConexionOLAP.getOLAP();
    		//OlapConnection olapConnection = connection.unwrap(OlapConnection.class);
    		//OlapDatabaseMetaData meta = olapConnection.getMetaData();
    		ResultSet rs =  meta.getCubes(null, null, null);
    		
    		while (rs.next()) {

    			   String ServerBD = rs.getString(1);
    			   String esquema = rs.getString(2);
    			   String cubo = rs.getString(3);
    			   String tipo_cubo = rs.getString(4);
    			   cubos.add(cubo);
    			   
    			   System.out.println(ServerBD + "-" + esquema+"-" + cubo +"-" + tipo_cubo);
    			}    		
    		
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    	return cubos;
    }

    
    /*****************************************************************************
	    *Metodo que recupera las dimensiones de un cubo definido en el esquema.
	    * @param:   
	    * @return:  ArrayList<String>
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    27/Febrero/2011
	    * @modify:  27/Febrero/2011
	    *****************************************************************************/
    
    public ArrayList<String> getEsquemaDimension(Parametros parametros){
    	
    	//Connection connection;
    	ArrayList<String> dimensiones = new ArrayList<String>();
    	
    	try{
    		//connection = (new ConexionOLAP("1997").getConexionOLAP());
    		//connection = ConexionOLAP.getOLAP();
    		//OlapConnection olapConnection = connection.unwrap(OlapConnection.class);
    		//OlapDatabaseMetaData meta = olapConnection.getMetaData();
    		ResultSet rs =  meta.getDimensions(null, null, parametros.getCubo() , null);
    		
    		while (rs.next()) {

    			   String ServerBD = rs.getString(1);
    			   String esquema = rs.getString(2);
    			   String cubo = rs.getString(3);
    			   String dimension = rs.getString(4);
    			   String dimension_unica = rs.getString(5);
    			   dimensiones.add("Dimension: " + dimension);
    			   System.out.println(ServerBD + "-" + esquema+"-" + cubo +"-" + dimension+"-" + dimension_unica);
    			}    		
    		
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    	return dimensiones;
    }

    
    /*****************************************************************************
	    *Metodo que recupera los niveles de una dimension de un cubo definido en el esquema.
	    * @param:   
	    * @return:  ArrayList<Nivel>
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    27/Febrero/2011
	    * @modify:  27/Febrero/2011
	    *****************************************************************************/
    
    public ArrayList<Nivel> getEsquemaDimensionNivel(Parametros parametros){
    	
    	ArrayList<Nivel> niveles = new ArrayList<Nivel>();
    	//Connection connection;
    	
    	try{
    		//connection = (new ConexionOLAP("1997").getConexionOLAP());
    		//connection = ConexionOLAP.getOLAP();
    		//OlapConnection olapConnection = connection.unwrap(OlapConnection.class);
    		//OlapDatabaseMetaData meta = olapConnection.getMetaData();
    		ResultSet rs =  meta.getLevels (null, null, parametros.getCubo(),"["+parametros.getDimension()+"]", null,null);

    		while (rs.next())
    		{
    			Nivel nivel = new Nivel();
				String nombre_nivel = rs.getString(6);
				String nombre_unico = rs.getString(7);
				   
				nivel.setNombre(nombre_nivel);
				nivel.setNombre_unico(nombre_unico);
				niveles.add(nivel);
				System.out.println(nombre_nivel + " - " + nombre_unico);
			}    		
    		
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    	return niveles;
    }
    
    
    /*****************************************************************************
	    *Metodo que recupera los elementos (miembros) de un nivel de una dimension de un cubo definido en el esquema.
	    * @param:   
	    * @return:  ArrayList<Miembro>
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    27/Febrero/2011
	    * @modify:  27/Febrero/2011
	    *****************************************************************************/
    
    public ArrayList<String> getEsquemaMiembros(Parametros parametros)
    {
    	
    	//Connection connection;
    	ArrayList<String> miembros = new ArrayList<String>();
    	
    	try{
    		//connection = (new ConexionOLAP("1997").getConexionOLAP());
    		//connection = ConexionOLAP.getOLAP();
    		//OlapConnection olapConnection = connection.unwrap(OlapConnection.class);
    		//OlapDatabaseMetaData meta = olapConnection.getMetaData();
    		ResultSet rs =  meta.getMembers(null, null, parametros.getCubo(), "["+parametros.getDimension()+"]", null, "["+parametros.getDimension()+"].["+parametros.getNivel()+"]", null, null);

    		while (rs.next()) 
    		{
				String member_name = rs.getString(9);
				String member_unique_name = rs.getString(10);
				//System.out.println(member_name + "-" + member_unique_name);
	    		miembros.add(member_unique_name);
			}

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    	return miembros;
    }  

    
    /*****************************************************************************
	    *Metodo que recupera la jerarquia de un cubo definido en el esquema. (TEST)
	    * @param:   
	    * @return:  String
	    * @author:  Alfredo Cesar Cruz Alvarez
	    * @date:    27/Febrero/2011
	    * @modify:  27/Febrero/2011
	    *****************************************************************************/

    public String getEsquemaDimensionJerarquia(){
    	
    	//Connection connection;
    	
    	try{
    		//connection = (new ConexionOLAP("1997").getConexionOLAP());
    		//connection = ConexionOLAP.getOLAP();
    		//OlapConnection olapConnection = connection.unwrap(OlapConnection.class);
    		//OlapDatabaseMetaData meta = olapConnection.getMetaData();
    		ResultSet rs =  meta.getHierarchies(null, null, "Sales","[Product]", null);
    		
    		Schema esquema = olapConnection.getSchema();
    		NamedList cubos = esquema.getCubes();
    		
    		
    		while (rs.next()) {

    			   String ServerBD = rs.getString(1);
    			   //String esquema = rs.getString(2);
    			   String cubo = rs.getString(3);
    			   String dimension = rs.getString(4);
    			   String jerarquia = rs.getString(5);
    			   String jerarquia_unica = rs.getString(6);
    			   String jerarquia_guia = rs.getString(7);
    			}    		
    		
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    	return null;
    }
    
    

}

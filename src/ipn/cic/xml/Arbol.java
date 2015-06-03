package ipn.cic.xml;

import java.util.ArrayList;
import java.util.List;


/*****************************************************************************
 * Clase encargada de formar un arbol con la jerarquia de los elementos de interes. 
 * @param:   
 * @return:  
 * @author:  Alfredo Cesar Cruz Alvarez
 * @date:    26/Marzo/2011
 * @modify:  26/Marzo/2011
 *****************************************************************************/

public class Arbol {

    String elemento;
    String tag = "</category>";
    ArrayList<Arbol> hijos = new ArrayList<Arbol>();
    
     public Arbol (String elemento) {
            this.elemento = elemento;
     }

    public void insert (String elemento)  {

        hijos.add(new Arbol (elemento));
        
    }

    public Arbol insert (Arbol arbol, String padre , String hijo)
    {
    	boolean bandera = false;
    	boolean banderaHijo = false;
    	
    	for( Arbol a : arbol.hijos)
    	{
    		if(a.elemento.equals(padre))
    		{
    			for(Arbol arbolHijo : a.hijos)
    			{
    				banderaHijo = arbolHijo.elemento.equals(hijo);
    				if(banderaHijo==true)	//ya existe un hijo con el mismo nombre
    					return arbol;
    			}
    			
    			if(banderaHijo == false)
    			{
        			bandera = a.hijos.add(new Arbol (hijo));
        			break;
    			}
    		}
    		
    	   	if(bandera == false)
        	{
    	   		insert(a, padre, hijo);
	    	}
    	}
    	
    	
    	return arbol;
    
    }

    
    
}
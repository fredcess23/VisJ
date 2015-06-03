package ipn.cic.xml;

import java.util.ArrayList;
import java.util.List;

/*****************************************************************************
 * Clase de prueba para verificar el funcionamiento de la creacion de un Arbol.
 * @param:   
 * @return:  
 * @author:  Alfredo Cesar Cruz Alvarez
 * @date:    26/Marzo/2011
 * @modify:  26/Marzo/2011
 *****************************************************************************/

public class Test {

	public static void main(String[] args) 
	{
		
		List<String> vector = null;
		List<List> matriz = new ArrayList<List>();
		
		vector = new ArrayList<String>();
		vector.add("Product");vector.add("Drink");vector.add("Beverages");vector.add("Washington");
		matriz.add(vector);
		
		vector = new ArrayList<String>();
		vector.add("Product");vector.add("Food");vector.add("Produce");vector.add("Vegetables");
		matriz.add(vector);

		vector = new ArrayList<String>();
		vector.add("Product");vector.add("Food");vector.add("Produce");vector.add("Fruit");
		matriz.add(vector);
		
		Arbol root = null;
        if (root == null)
            root = new Arbol ("dimension");
        
        
        root.insert("Product");

		for(List<String> renglon: matriz)
		{
			for(int i=1; i<renglon.size();i++)
			{
				root = root.insert(root,renglon.get(i-1),renglon.get(i));	
			}
		}

		String str = "";
		str = display(root,"");
		//displayPrint(root,"");
		System.out.println("Acabe gracias a Dios");
	}
	
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
	
	
	
	static void displayPrint (Arbol a, String tag)
	{
		for(Arbol hijo : a.hijos)
		{
			System.out.println("<category label = '" + hijo.elemento + "'>");
			displayPrint(hijo,tag);
			System.out.println(hijo.tag);
			
		}
		
	}	
	

}

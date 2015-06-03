package ipn.cic.controlador;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;


import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Intbox;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import ipn.cic.modelo.Nivel;
import ipn.cic.modelo.Parametros;
import ipn.cic.servicio.EsquemaService;



public class ControladorZK extends GenericForwardComposer{
	
	
	private Tree arbol;
	private Listbox list;
	private Listbox list_parametros;
	private Listbox list_interes;
	private Listbox eficiencia;
	private Listbox hecho;
	private Listbox grafica;
	private Listbox tipoEficiencia;
	private Intbox intRango1;
	private Intbox intRango2;
	
	
	String cubo_elegido = null;
	String dimension_elegido = null;
	String nodo_elegido = null;
	String dimension_interes = "";
	String nodo_interes= "";
	String otraDimension="";
	
	private ArrayList<String> cubos=null;
	private ArrayList<String> dimensiones=null;
	private ArrayList<Nivel> niveles = null;
	ArrayList<String> dominio = new ArrayList<String>();
	
	
	
	private XmlBeanFactory factory;
	private EsquemaService xmlService;
	

	public void doAfterCompose(org.zkoss.zk.ui.Component comp)throws Exception{
		super.doAfterCompose(comp);
		cargaCubos();
		cargaMedidas();
	
	}
	
	private  void cargaMedidas()
	{
		for(String strcubo:cubos)
		{
			if(strcubo.equals("Tesis"))
			{
				hecho.appendItem("Numero Tesis", "Numero Tesis");
				break;
				/*
				Listitem item = new Listitem();
				item.setParent(hecho);
				new Listcell("Numero Tesis").setParent(item);
				*/
			}
			else{
				
				hecho.appendItem("Unit Sales", "Unit Sales");
				hecho.appendItem("Store Cost", "Store Cost");
				hecho.appendItem("Store Sales", "Store Sales");
				break;
				
				/*
				Listitem item = new Listitem();
				item.setParent(hecho);
				new Listcell("Unit Sales").setParent(item);
				new Listcell("Store Cost").setParent(item);
				new Listcell("Store Sales").setParent(item);
				*/				

			}
				
				
		}
		

		
	}
	
	public void onSelect$tipoEficiencia(){

		getElementosEficiencia();

	}
	
	public void onSelect$arbol(){

		getDimensionesNiveles();

	}
	 
	private  void cargaCubos() 
	{
		factory = new XmlBeanFactory(new ClassPathResource("resources/bean.xml"));
		EsquemaService xmlService = (EsquemaService)factory.getBean("esquemaServiceImpl");
		cubos = xmlService.getEsquemaCubos();
		Treechildren tc = arbol.getTreechildren();
        	
		for(String strcubo:cubos)
		{
	        Treeitem ti = new Treeitem();
	        ti.setLabel(strcubo); 
	        ti.setParent(tc);
		}
        
        System.out.println("Termine");
 
	} 			
		
	public void getDimensionesNiveles()
	{

		Treeitem nodo = arbol.getSelectedItem();
		nodo_elegido = (String)nodo.getLabel();
		
		if(cubos.contains(nodo_elegido))
		{
			cubo_elegido = nodo_elegido;
			Parametros parametros = new Parametros();
			parametros.setCubo(cubo_elegido);
			
			System.out.println("Se selecciono el nodo:" + nodo_elegido);
			Treechildren tc1  = new Treechildren();
			
			factory = new XmlBeanFactory(new ClassPathResource("resources/bean.xml"));
			EsquemaService xmlService = (EsquemaService)factory.getBean("esquemaServiceImpl");
			dimensiones = xmlService.getEsquemaDimension(parametros);
	
			for(String strdimension: dimensiones)
			{	
				Treeitem ti = new Treeitem();
		        ti.setLabel(strdimension);
		        String style = "<style>.mydb{color: Blue;}</style>";
		        ti.setParent(tc1);
			    tc1.setParent(nodo);
			}
		
		}
		
		
		if(dimensiones.contains(nodo_elegido))
		{
			
			dimension_elegido = nodo_elegido;
			int pos = dimension_elegido.indexOf(": ");
			dimension_elegido = dimension_elegido.substring(pos+2, dimension_elegido.length());
			
			Parametros parametros = new Parametros();
			parametros.setCubo(cubo_elegido);
			parametros.setDimension(dimension_elegido);
			
			System.out.println("Se selecciono el cubo y dimension :" + cubo_elegido + "," + dimension_elegido);
			
			Treechildren tc2 = new Treechildren();
			
			factory = new XmlBeanFactory(new ClassPathResource("resources/bean.xml"));
			EsquemaService xmlService = (EsquemaService)factory.getBean("esquemaServiceImpl");
			niveles = xmlService.getEsquemaDimensionNivel(parametros);
			
			for(Nivel nivel: niveles)
			{
				Treeitem ti = new Treeitem();
		        ti.setLabel(nivel.getNombre());
		        ti.setParent(tc2);
			    tc2.setParent(nodo);

			}	

		}
		
	}

	public void onClick$consultar()
	{
		
		Treeitem nodo = arbol.getSelectedItem();
		nodo_elegido = (String)nodo.getLabel();
		
		Parametros parametros = new Parametros();
		parametros.setCubo(cubo_elegido);
		parametros.setDimension(dimension_elegido);
		parametros.setNivel(nodo_elegido);
		
		factory = new XmlBeanFactory(new ClassPathResource("resources/bean.xml"));
		EsquemaService xmlService = (EsquemaService)factory.getBean("esquemaServiceImpl");
		ArrayList<String> miembros = xmlService.getEsquemaMiembros(parametros);
	
		list.getChildren().clear();
	
		list.appendItem("Elegir elemento", "Elegir elemento");
		for(String miembro:miembros)
		{
			list.appendItem(miembro, miembro);
		}
		
	}
	
	public void onClick$interes()
	{

		dimension_interes = "";
		nodo_interes = "";
		Treeitem nodo = arbol.getSelectedItem();
		nodo_elegido = (String)nodo.getLabel();
		
		list_interes.getChildren().clear();
		Listitem item = new Listitem();
		item.setParent(list_interes);
		new Listcell(dimension_elegido + " > " + nodo_elegido).setParent(item);
		
		dimension_interes = dimension_elegido;
		nodo_interes = nodo_elegido;
	}
	
	public void onClick$agregar()
	{

		Listitem item_list = list.getSelectedItem();
		String str_list = (String) item_list.getValue();
		
		Treeitem nodo = arbol.getSelectedItem();
		nodo_elegido = (String)nodo.getLabel();
		String etiqueta = cubo_elegido + " > " +  dimension_elegido + " > " + nodo_elegido;		
		
		//list_parametros.appendItem(etiqueta, etiqueta);
		Listitem item = new Listitem();
		item.setParent(list_parametros);
		new Listcell(etiqueta).setParent(item);
		new Listcell(str_list).setParent(item);
		
		// Guardamos cubo, dimension y nivel en un ArraLyist				
		if(str_list.indexOf("Time") > 0 | str_list.indexOf("tiempo") > 0)
			dominio.add(str_list);
		else
			otraDimension = ","+str_list;
		
		
	}
	
	public void onClick$enviar() throws ServletException, IOException
	{
		
		Listitem item_eficiencia = eficiencia.getSelectedItem();
		Listitem item_hecho = hecho.getSelectedItem();
		Listitem item_grafica = grafica.getSelectedItem();
		Listitem item_tipo = tipoEficiencia.getSelectedItem();
		
		Integer rango1 = intRango1.getValue();
		Integer rango2 = intRango2.getValue();
		Parametros parametros = new Parametros();
		
		parametros.setCubo(cubo_elegido);
		parametros.setDimension(dimension_interes);
		parametros.setNivel(nodo_interes);
		parametros.setHecho((String) item_hecho.getValue());
		parametros.setEficiencia((String) item_eficiencia.getValue());
		parametros.setGrafica((String) item_grafica.getValue());
		parametros.setTipoEficiencia((String)item_tipo.getValue());
		parametros.setRango1(rango1);
		parametros.setRango2(rango2);
		parametros.setDominio(dominio);
		parametros.setDom(otraDimension);

		Object obj3 = desktop.getExecution().getSession().getNativeSession();	
		if(obj3 != null) { 
			org.apache.catalina.session.StandardSessionFacade sesionFacade = (org.apache.catalina.session.StandardSessionFacade)obj3;
			sesionFacade.setAttribute("parametros", parametros);
		}
		
		Executions.getCurrent().sendRedirect("/zk/mapa.htm");
    	
	}
	
	
	public void getElementosEficiencia()
	{
		Listitem tipo = tipoEficiencia.getSelectedItem();
		String str_tipo = (String)tipo.getValue();
		
		if(str_tipo.equals("Por numero")||str_tipo.equals("Por porciento")){
			intRango1.setVisible(true);
			intRango2.setVisible(false);
		}
		if(str_tipo.equals("Por rango")){
			intRango1.setVisible(true);
			intRango2.setVisible(true);
		}

	}
	
}

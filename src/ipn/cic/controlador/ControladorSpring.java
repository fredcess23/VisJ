package ipn.cic.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import ipn.cic.modelo.Parametros;
import ipn.cic.servicio.EficienciaService;
import ipn.cic.servicio.TableroService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;



public class ControladorSpring extends MultiActionController{

	private EficienciaService eficienciaServiceImpl;
	private TableroService tableroServiceImpl;
	
	private Parametros parametros;
	String strXML="";
	
	public ModelAndView doMapa(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		//System.out.println("Entramos al Controlador doMapa...");
		long tiempoInicio = System.currentTimeMillis();
		parametros = (Parametros) session.getAttribute("parametros");
		strXML = eficienciaServiceImpl.getEficienciaMapa(parametros);
		System.out.println(strXML);
    	Object obj = strXML;
    	Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("strXML", obj);
        myModel.put("grafico",this.parametros.getGrafica());
        myModel.put("cubo",this.parametros.getCubo());
        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.out.println("El tiempo de ejecucion es :" + totalTiempo/1000 + " segundos  -  " + totalTiempo + " milisegundos");
        
		return new ModelAndView("mapa","modelo",myModel);
	}

	public ModelAndView doDesvanece(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		
		System.out.println("Entramos al Controlador doDesvanece...");
		PrintWriter out = null;
		String strXML_update = "";
		String nodo = request.getParameter("nodo");
		if(nodo!=null){
			strXML_update = eficienciaServiceImpl.getDesvanece(nodo, strXML);
			System.out.println(strXML_update);
			response.setContentType("text/xml");
			response.setCharacterEncoding("ISO-8859-1");
		}
		
    	response.setHeader("Cache-Control", "no-cache");
        out = response.getWriter();
        out.print(strXML_update);
        out.close();

		return null;
	}
	
	public ModelAndView doTableroNivel1(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException {
		
		System.out.println("Entramos al metodo doTablero...");
		PrintWriter out = null;
		String strXML_tablero="";
    	String nodo="";
    	nodo = request.getParameter("nodo").replace("[ ","[");
    	if(null!=nodo){
    		parametros.setNodo(nodo);
    		strXML_tablero = tableroServiceImpl.getNivel1(parametros);
    		System.out.println(strXML_tablero);
    		response.setContentType("text/xml");
    	}
		
    	response.setHeader("Cache-Control", "no-cache");
        out = response.getWriter();
        out.print(strXML_tablero);
        out.close();   
         
         return null;
	}
	
	public ModelAndView doTableroNivel2(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException {
		
		System.out.println("Entramos al metodo doTableroNivel2...");
		PrintWriter out = null;
		String strXML_tablero="";
    	String nodo = request.getParameter("nodo");
    	String year = request.getParameter("year");
    	if(null!=nodo)
    	{
    		parametros.setNodo(nodo);
    		parametros.setYear(year);
    		strXML_tablero = tableroServiceImpl.getNivel2(parametros);
    		System.out.println(strXML_tablero);
    		response.setContentType("text/xml");
    	}
		
    	response.setHeader("Cache-Control", "no-cache");
        out = response.getWriter();
        out.print(strXML_tablero);
        out.close();   
         
         return null;
	}
	
	
	public ModelAndView doTableroNivel3(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException {
		
		System.out.println("Entramos al metodo doTableroNivel3...");
		PrintWriter out = null;
		String strXML_tablero="";
    	String nodo = request.getParameter("nodo");
    	String year = request.getParameter("year");
    	if(null!=nodo)
    	{
    		parametros.setNodo(nodo);
    		parametros.setYear(year);
    		strXML_tablero = tableroServiceImpl.getNivel3(parametros);
    		System.out.println(strXML_tablero);
    		response.setContentType("text/xml");
    	}
		
    	response.setHeader("Cache-Control", "no-cache");
        out = response.getWriter();
        out.print(strXML_tablero);
        out.close();   
         
         return null;
	}
	
	
	public ModelAndView doTableroNivel4(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException {
		
		System.out.println("Entramos al metodo doTableroNivel4...");
		PrintWriter out = null;
		String strXML_tablero="";
    	String param1 = request.getParameter("param1").replace("*"," ");	//product
    	String param2 = request.getParameter("param2");	//store
    	String param3 = request.getParameter("param3");	//year

    	if(null!=param1)
    	{
    		parametros.setNodo(param1);
    		parametros.setSubNodo(param2);
    		parametros.setYear(param3);
    		strXML_tablero = tableroServiceImpl.getNivel4(parametros);
    		System.out.println(strXML_tablero);
    		response.setContentType("text/xml");
    	}
		
    	response.setHeader("Cache-Control", "no-cache");
        out = response.getWriter();
        out.print(strXML_tablero);
        out.close();   
         
         return null;
	}
	
	
	public ModelAndView doTableroNivel5(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException {
		
		System.out.println("Entramos al metodo doTableroNivel5...");
		PrintWriter out = null;
		String strXML_tablero="";
    	String param1 = request.getParameter("param1");	//product
    	String param2 = request.getParameter("param2");	//store
    	String param3 = request.getParameter("param3");	//year

    	if(null!=param1)
    	{
    		parametros.setNodo(param1);
    		parametros.setSubNodo(param2);
    		parametros.setYear(param3);
    		strXML_tablero = tableroServiceImpl.getNivel5(parametros);
    		System.out.println(strXML_tablero);
    		response.setContentType("text/xml");
    	}
		
    	response.setHeader("Cache-Control", "no-cache");
        out = response.getWriter();
        out.print(strXML_tablero);
        out.close();   
         
         return null;
	}
	
	public EficienciaService getEficienciaServiceImpl() {
		return eficienciaServiceImpl;
	}


	public void setEficienciaServiceImpl(EficienciaService eficienciaServiceImpl) {
		this.eficienciaServiceImpl = eficienciaServiceImpl;
	}
	
	
	public TableroService getTableroServiceImpl() {
		return tableroServiceImpl;
	}

	public void setTableroServiceImpl(TableroService tableroServiceImpl) {
		this.tableroServiceImpl = tableroServiceImpl;
	}
  

}

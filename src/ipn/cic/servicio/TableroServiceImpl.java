package ipn.cic.servicio;

import java.util.ArrayList;

import ipn.cic.dao.TableroDAO;
import ipn.cic.modelo.Miembro;
import ipn.cic.modelo.Parametros;

public class TableroServiceImpl implements TableroService
{
	TableroDAO TableroDAO;
	
	public TableroDAO getTableroDAO() {
		return TableroDAO;
	}

	public void setTableroDAO(TableroDAO tableroDAO) {
		TableroDAO = tableroDAO;
	}	
	
	/*****************************************************************************
    * Metodo que obtiene los datos para formar el XML que define una grafica "Multi Serie-Area" (MSSplineArea.swf)
    * @param:   Parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    15/Marzo/2011
    * @modify:  15/Marzo/2011
    *****************************************************************************/
	@Override
	public String getNivel1(Parametros parametros) 
	{
        String strXML = "<chart caption='" + parametros.getHecho() + 
		"   -   " + "Dimension : " +parametros.getDimension() +  "  -  Elemento: " + parametros.getNombreNodo() +
		"  -  1997 y 1998' xAxisName='" + parametros.getNombreNodo() +"' yAxisName='" + parametros.getHecho() + "' showValues='0' numberPrefix=''>";
        
        String estilo = "<styles> <definition> <style name='CanvasAnim' type='animation' param='_xScale' start='0' duration='1' /> </definition>"+
        		"<application> <apply toObject='Canvas' styles='CanvasAnim'/> </application> </styles>";
        
        
        String categorias = TableroDAO.getChildrenMemberNameDimension1(parametros);
        String dataset = "";
        ArrayList<String> dominio = parametros.getDominio();
		
        for(int i=dominio.size()-1;i>=0;i--)
		{
			parametros.setYear(dominio.get(i));
			dataset += TableroDAO.getChildrenMemberValueDimension1(parametros);
	        
		}

        strXML += categorias + dataset + estilo + "</chart>";
        
		return strXML;
	}
	
	/*****************************************************************************
    * Metodo que obtiene detalle de la grafica de Primer Nivel (Drill-Down) y arma el XML que define la grafica "Multi Serie-Columna" (MSColumn3D.swf)
    * @param:   Parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    15/Marzo/2011
    * @modify:  15/Marzo/2011
    *****************************************************************************/
	@Override
	public String getNivel2(Parametros parametros) 
	{
        String strXML = "<chart caption='" + parametros.getHecho() + 
		"   -   " + "Dimension : " +parametros.getDimension() + "  -  Elemento: " + parametros.getNombreNodo() +
		"  -  "+ parametros.getYear() + "' xAxisName='" + parametros.getNombreNodo() +"' yAxisName='" + parametros.getHecho() + "' showValues='0' numberPrefix=''>";

        String categorias = TableroDAO.getChildrenMemberNameDimension2(parametros);
        String dataset = TableroDAO.getChildrenMemberValueDimension2(parametros);
        
        strXML += categorias + dataset + "</chart>";
        
        return strXML;
	}
	
	
	/*****************************************************************************
    * Metodo que obtiene detalle de la grafica de Primer Nivel (Drill-Down) y arma el XML que define la grafica "Multi Serie-Combinada" (MSCombi3D.swf)
    * @param:   Parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    15/Marzo/2011
    * @modify:  15/Marzo/2011
    *****************************************************************************/
	@Override
	public String getNivel3(Parametros parametros) 
	{
        String strXML = "<chart caption='" + parametros.getHecho() + " by Store" +
		"   -   " + "Dimension : " +parametros.getDimension() + "  -  Elemento: " + parametros.getNombreNodo() +
		"  -  "+ parametros.getYear() + "' xAxisName='" + parametros.getNombreNodo() +"' yAxisName='" + parametros.getHecho() + "' showValues='0' baseFontSize='11' numberPrefix='$'>";
        
        String estilo = " <styles> <definition><style name='bgAnim' type='animation' param='_xScale' start='0' duration='1' /></definition>" +
        		"<application><apply toObject='BACKGROUND' styles='bgAnim'/></application></styles>";

        String categorias = TableroDAO.getChildrenMemberNameDimension3(parametros);
        String dataset = TableroDAO.getChildrenMemberValueDimension3(parametros);
        
        strXML += categorias + dataset + estilo + "</chart>";
        
        return strXML;
	}
	
	
	
	/*****************************************************************************
    * Metodo que obtiene detalle de la grafica de Tercer Nivel (Drill-Down) y arma el XML que define el "Mapa USA/Canda/Mexico" (FCMap_USA.swf)
    * @param:   Parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    15/Marzo/2011
    * @modify:  26/Marzo/2011
    *****************************************************************************/
	@Override
	public String getNivel4(Parametros parametros) 
	{
		String strXML = "";
		strXML = TableroDAO.getChildrenMemberValueDimension4(parametros);
		return strXML;
	}
	
	
	/*****************************************************************************
    * Metodo que obtiene detalle de la grafica de Cuarto Nivel (Drill-Down) y arma el XML que define la grafica "Pie3D" (Pie3D.swf)
    * @param:   Parametros
    * @return:  String
    * @author:  Alfredo Cesar Cruz Alvarez
    * @date:    15/Marzo/2011
    * @modify:  15/Marzo/2011
    *****************************************************************************/
	@Override
	public String getNivel5(Parametros parametros) 
	{
		String strXML = "";
		strXML = TableroDAO.getChildrenMemberValueDimension5(parametros);
		return strXML;
	}
	
	
	
}

package ipn.cic.controlador;

import java.io.IOException;

import javax.servlet.ServletException;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import ipn.cic.dao.ConexionOLAP;
import ipn.cic.modelo.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

public class ControladorConexionZK extends GenericForwardComposer{

	private Listbox listConexion;
	private Textbox txtHost;
	private Textbox txtBD;
	private Textbox txtPuerto;
	private Textbox txtUsuario;
	private Textbox txtContrasena;
	private Textbox txtEsquema;
	
	public Conexion beanConexion = new Conexion();
	
	public void onClick$aceptar() throws ServletException, IOException, InterruptedException
	{
		
		beanConexion.setConexion((String)listConexion.getSelectedItem().getValue());
		beanConexion.setNombreHost(txtHost.getValue());
		beanConexion.setNombreBD(txtBD.getValue());
		beanConexion.setNumeroPuerto(txtPuerto.getValue());
		beanConexion.setUsuario(txtUsuario.getValue());
		beanConexion.setContrasena(txtContrasena.getValue());
		beanConexion.setEsquema(txtEsquema.getValue());
		
		new ConexionOLAP(beanConexion); 
		
		try{
			ConexionOLAP.getConexionOLAP();
			Messagebox.show("Conexion Exitosa");
		}
		catch (Exception e) 
		{
		Messagebox.show("ÁError en la conexion! Vuelva a intentarlo");
			e.printStackTrace();
		}
		

	}
	
}

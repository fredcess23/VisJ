package ipn.cic.servicio;

import java.util.ArrayList;
import java.util.Collections;

import ipn.cic.dao.*;
import ipn.cic.modelo.Miembro;
import ipn.cic.modelo.Nivel;
import ipn.cic.modelo.Parametros;
import ipn.cic.xml.XML;

public class EsquemaServiceImpl implements EsquemaService{

	EsquemaDAO EsquemaDAO;
	
	//Obtenemos las refrencias al objeto usuarioDAO por medio de los getter y setters
	//estas refrencias son dadas por el contenedor a el objeto

	public EsquemaDAO getEsquemaDAO() {
		return EsquemaDAO;
	}

	public void setEsquemaDAO(EsquemaDAO esquemaDAO) {
		EsquemaDAO = esquemaDAO;
	}
	
	@Override
	public ArrayList<String> getEsquemaCubos() {
		return this.EsquemaDAO.getEsquemaCubos();
	}

	@Override
	public ArrayList<String> getEsquemaDimension(Parametros parametros) {
		return this.EsquemaDAO.getEsquemaDimension(parametros);
	}

	@Override
	public ArrayList<Nivel> getEsquemaDimensionNivel(Parametros parametros) {
		return this.EsquemaDAO.getEsquemaDimensionNivel(parametros);
	}

	@Override
	public ArrayList<String> getEsquemaMiembros(Parametros parametros) {
		return this.EsquemaDAO.getEsquemaMiembros(parametros);
	}

	
}

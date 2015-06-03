package ipn.cic.servicio;

import java.util.ArrayList;

import ipn.cic.modelo.Nivel;
import ipn.cic.modelo.Parametros;

public interface EsquemaService {
	
	public ArrayList<String> getEsquemaCubos();
	public ArrayList<String> getEsquemaDimension(Parametros parametros);
	public ArrayList<Nivel> getEsquemaDimensionNivel(Parametros parametros);
	public ArrayList<String> getEsquemaMiembros(Parametros parametros);
	
}

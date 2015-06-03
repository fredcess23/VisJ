package ipn.cic.servicio;

import ipn.cic.modelo.Parametros;

public interface EficienciaService {
	public String getEficienciaMapa(Parametros parametros);
    public String getDesvanece(String nodo, String xml);
}

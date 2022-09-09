package es.jcyl.fqs.ocap.ln.acceso;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.oad.acceso.OCAPAccesoOAD;
import es.jcyl.fqs.ocap.oad.areas.OCAPAreasOAD;
import es.jcyl.fqs.ocap.ot.acceso.OCAPAccesoOT;
import es.jcyl.fqs.ocap.ot.areas.OCAPAreasOT;
import es.jcyl.fqs.ocap.util.Seguridad;
import es.jcyl.fqs.ocap.util.Utilidades;

public class OCAPAccesoLN {

	OCAPAccesoOAD ocapAccesoOAD;
	
	public  OCAPAccesoLN() {
		
		
		this.ocapAccesoOAD = OCAPAccesoOAD.getInstance();
		
	}
	
	
	
	/**
	 * Busca la existencia del usuario en la tabla OCAP_ACCESO
	 * @param aDni  Parámetro de busqueda
	 * @return Datos de acceso si el usuario existe.
	 */
	public OCAPAccesoOT buscarAcceso(String aDni)
	   {
		OCAPAccesoOT dato = null;
	     try
	     {
	    	 
	       dato = ocapAccesoOAD.buscarOCAPAcceso(aDni);	       	       	       
	     }
	     catch (Exception e) {
	       dato = null;
	       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
	     }
	 
	     return dato;
	   }
	
	
	public int insertarAcceso(OCAPAccesoOT entrada)
	   {
		int resultado;
	     try
	     {
	    	 resultado=  ocapAccesoOAD.altaOCAPAcceso(entrada);
	    	 
	       
	     }
	     catch (Exception e) {
	       resultado = -1;
	       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
	     }
	 
	     return resultado;
	   }
	
	
}

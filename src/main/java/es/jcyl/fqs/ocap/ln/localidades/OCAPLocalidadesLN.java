 package es.jcyl.fqs.ocap.ln.localidades;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.localidades.OCAPLocalidadesOAD;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.localidades.OCAPLocalidadesOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPLocalidadesLN
 {
   private JCYLUsuario jcylUsuario;
 
   public OCAPLocalidadesLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
	public ArrayList listarLocalidadesProvincia(String cProvId) throws Exception {
		ArrayList resultado = null;
		try {
			OCAPLocalidadesOAD variableOAD = OCAPLocalidadesOAD.getInstance();
			resultado = variableOAD.listarLocalidadesProvincia(cProvId);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return resultado;
	}
 
   public OCAPLocalidadesOT buscarLocalidad(String cLocalidadId)
     throws Exception
   {
     OCAPLocalidadesOT dato = null;
     try
     {
       OCAPLocalidadesOAD variableOAD = OCAPLocalidadesOAD.getInstance();
       dato = variableOAD.buscarLocalidad(cLocalidadId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
   
   public OCAPLocalidadesOT buscarLocalidadByName(String dLocalidad)
     throws Exception
   {
     OCAPLocalidadesOT dato = null;
     try
     {
       OCAPLocalidadesOAD variableOAD = OCAPLocalidadesOAD.getInstance();
       dato = variableOAD.buscarLocalidadByName(dLocalidad);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
   
 }


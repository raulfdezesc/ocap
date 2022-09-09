 package es.jcyl.fqs.ocap.ln.modalidadAnterior;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.modalidadAnterior.OCAPModalidadAnteriorOAD;
 import es.jcyl.fqs.ocap.ot.modalidadAnterior.OCAPModalidadAnteriorOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPModalidadAnteriorLN
 {
   Logger logger;
   public Logger loggerBD;
   private OCAPModalidadAnteriorOAD modalidadAnteriorOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
 
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPCategProfesionalesLN()
   {
     this.modalidadAnteriorOAD = OCAPModalidadAnteriorOAD.getInstance();
   }
   public OCAPModalidadAnteriorLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listarModalidadAnterior()
     throws Exception
   {
     ArrayList resultado = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " listarModalidadAnterior: Inicio");
       this.modalidadAnteriorOAD = OCAPModalidadAnteriorOAD.getInstance();
       resultado = this.modalidadAnteriorOAD.listadoOCAPModAnterior();
       OCAPConfigApp.logger.info(getClass().getName() + " listarModalidadAnterior: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " listarModalidadAnterior: ERROR: " + e.getMessage());
 
       throw e;
     }
 
     return resultado;
   }
 
   public OCAPModalidadAnteriorOT buscarOCAPModalidad(long cModAnteriorId)
     throws SQLException, Exception
   {
     OCAPModalidadAnteriorOT dato = null;
     try
     {
       OCAPConfigApp.logger.info("buscarOCAPModalidad: Inicio");
       this.modalidadAnteriorOAD = OCAPModalidadAnteriorOAD.getInstance();
       dato = this.modalidadAnteriorOAD.buscarOCAPModalidad(cModAnteriorId);
       OCAPConfigApp.logger.info("buscarOCAPModalidad: Fin");
     }
     catch (SQLException exSQL) {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return dato;
   }
   
	/**
	 * Devuelve los identificadores de las modalidades que son de grado
	 * extraordinario
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList listarModalidadAnteriorExtraordinaria() {
		ArrayList resultado = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " listarModalidadAnteriorExtraordinaria: Inicio");
			this.modalidadAnteriorOAD = OCAPModalidadAnteriorOAD.getInstance();
			resultado = this.modalidadAnteriorOAD.listarModalidadAnteriorExtraordinaria();
			OCAPConfigApp.logger.info(getClass().getName() + " listarModalidadAnteriorExtraordinaria: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " listarModalidadAnteriorExtraordinaria: ERROR: " + e.getMessage());
		}
		return resultado;
	}
 }


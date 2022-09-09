 package es.jcyl.fqs.ocap.ln.expedientesCAs;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.expedientesCAs.OCAPExpedientesCAsOAD;
 import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPRepeticionesCuestionariosOT;
 import es.jcyl.fqs.ocap.ot.expedientesCAs.OCAPExpedientesCAsOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.io.InputStream;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPExpedientesCAsLN
 {
   Logger logger;
   private OCAPExpedientesCAsOAD expedientesCAsOAD;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public void OCAPExpedientesCAsLN()
   {
     this.expedientesCAsOAD = OCAPExpedientesCAsOAD.getInstance();
   }
   public OCAPExpedientesCAsLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public long insertarOCAPExpedientesCAs(OCAPExpedientesCAsOT expCAOT)
     throws Exception
   {
     long id = 0L;
     try {
       this.expedientesCAsOAD = OCAPExpedientesCAsOAD.getInstance();
       id = this.expedientesCAsOAD.insertarOCAPExpedientesCAs(expCAOT);
     } catch (Exception e) {
       id = 0L;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return id;
   }
 
   public void insertarRepeticionesCuestionarios(long cExpCAId, ArrayList listaRepeticiones, String usuAlta) throws Exception
   {
     OCAPRepeticionesCuestionariosOT repeCuestionarioOT = null;
     try {
       this.expedientesCAsOAD = OCAPExpedientesCAsOAD.getInstance();
       for (int k = 0; k < listaRepeticiones.size(); k++) {
         repeCuestionarioOT = (OCAPRepeticionesCuestionariosOT)listaRepeticiones.get(k);
 
         this.expedientesCAsOAD.insertarRepeticionesCuestionarios(cExpCAId, repeCuestionarioOT.getCCuestionarioId(), repeCuestionarioOT.getNRepeticion(), usuAlta);
       }
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public ArrayList buscarOCAPExpedientesCAs(OCAPExpedientesCAsOT expCAOT, ArrayList listaRepetciones, boolean bUsuario, boolean bSimulacion)
     throws SQLException
   {
     ArrayList listado = null;
     try
     {
       this.expedientesCAsOAD = OCAPExpedientesCAsOAD.getInstance();
       listado = this.expedientesCAsOAD.buscarOCAPExpedientesCAs(expCAOT, listaRepetciones, bUsuario, bSimulacion);
     }
     catch (SQLException e) {
       listado = null;
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 
   public void guardarDatosBlob(OCAPExpedientesCAsOT expCAOT)
     throws Exception
   {
     try
     {
       this.expedientesCAsOAD = OCAPExpedientesCAsOAD.getInstance();
       this.expedientesCAsOAD.guardarDatosBlob(expCAOT);
     } catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public InputStream buscarExpedientesCA(long cExpCaId)
     throws SQLException
   {
     InputStream datos = null;
     try {
       this.expedientesCAsOAD = OCAPExpedientesCAsOAD.getInstance();
       datos = this.expedientesCAsOAD.buscarExpedientesCA(cExpCaId);
     } catch (SQLException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return datos;
   }
 }


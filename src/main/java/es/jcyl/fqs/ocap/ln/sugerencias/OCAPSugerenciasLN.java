 package es.jcyl.fqs.ocap.ln.sugerencias;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.ot.sugerencias.OCAPSugerenciasOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.UtilCorreo;
 import es.jcyl.framework.JCYLConfiguracion;
 import es.jcyl.framework.JCYLUsuario;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import javax.mail.SendFailedException;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 
 public class OCAPSugerenciasLN
 {
   Logger logger;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
 
   public OCAPSugerenciasLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public void enviarSugerencia(HttpServletResponse response, OCAPSugerenciasOT sugerenciasOT)
     throws SendFailedException, Exception
   {
     String fecha = "";
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: Inicio");
       SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_LETRA);
       Date hoy = new Date();
       fecha = df.format(hoy);
 
       if ((sugerenciasOT.getDCorreoElectronico() != null) && (!sugerenciasOT.getDCorreoElectronico().equals(""))) {
         String servidor_de_correo = JCYLConfiguracion.getValor("SERVIDOR_CORREO");
         String puerto = JCYLConfiguracion.getValor("PUERTO_CORREO");
         String remitente = sugerenciasOT.getDCorreoElectronico();
         String destinatario = JCYLConfiguracion.getValor("EMAIL_DESTINATARIO");
         String asunto = "Carrera Profesional. Buzón de Sugerencias";
         String cuerpo = "<b>Correo Electrónico</b>: " + sugerenciasOT.getDCorreoElectronico() + "<br /><br /> <b>Teléfono:</b> " + sugerenciasOT.getDTelefono() + " <br /><br /> <b>Sugerencia:</b> <br />" + sugerenciasOT.getDSugerencia() + "<br />";
         UtilCorreo.enviar(servidor_de_correo, puerto, remitente, destinatario, asunto, cuerpo, "text/html");
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: Fin");
     } catch (SendFailedException e) {
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: ERROR enviando email: " + e.getMessage());
       throw e;
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " enviarSugerencia: ERROR" + e.getMessage());
       throw new Exception("Informe NO generado");
     }
   }
 }


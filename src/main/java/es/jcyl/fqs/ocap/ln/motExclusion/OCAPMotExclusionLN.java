 package es.jcyl.fqs.ocap.ln.motExclusion;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.oad.motExclusion.OCAPMotExclusionOAD;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.motExclusion.OCAPMotExclusionOT;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPMotExclusionLN
 {
   private JCYLUsuario jcylUsuario;
 
   public OCAPMotExclusionLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public OCAPMotExclusionOT buscarMotivo(String dNombre)
     throws Exception
   {
     OCAPMotExclusionOT motivos = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarMotivo: Inicio");
       OCAPMotExclusionOAD variableOAD = OCAPMotExclusionOAD.getInstance();
       motivos = variableOAD.buscarMotivo(dNombre);
       OCAPConfigApp.logger.info(getClass().getName() + " buscarMotivo: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return motivos;
   }
 
   public String buscarMotivosExpediente(long cExpId)
     throws Exception
   {
     String motivos = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarMotivosExpediente: Inicio");
       OCAPMotExclusionOAD variableOAD = OCAPMotExclusionOAD.getInstance();
       motivos = variableOAD.buscarMotivosExpediente(cExpId);
       OCAPConfigApp.logger.info(getClass().getName() + " buscarMotivosExpediente: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return motivos;
   }
 
   public ArrayList listarMotivosExclusion(String anio)
     throws Exception
   {
     ArrayList motivos = null;
     try
     {      
       OCAPMotExclusionOAD variableOAD = OCAPMotExclusionOAD.getInstance();      
       motivos = variableOAD.listarMotivosExclusion(anio);       
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return motivos;
   }
   
   
   public String listarMotivosExclusionCompleto(Long convocatoriaId)
		     throws Exception
		   {
		    ArrayList motivos=null;
	        StringBuilder sb= new StringBuilder("");
	        String anio = "";
		     try
		     {
		      if(convocatoriaId != null && convocatoriaId != 0) {
		    	  OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(this.jcylUsuario);
		    	  OCAPConvocatoriasOT convocatoriaOT = new OCAPConvocatoriasOT();
		      
		    	  convocatoriaOT = convocatoriaLN.buscarOCAPConvocatorias(convocatoriaId);
		    	  anio = convocatoriaOT.getDAnioConvocatoria()!=null?convocatoriaOT.getDAnioConvocatoria():"";
		      }
		      
		      
		      OCAPMotExclusionOAD variableOAD = OCAPMotExclusionOAD.getInstance();
		      motivos = variableOAD.listarMotivosExclusionCompleto();		
		      
		      if (motivos!= null && motivos.size()>0)
		      {
		    	  for (int i=0;i<motivos.size();i++)
		    	  {
		    		  String codigo =   String.valueOf( ((OCAPMotExclusionOT) motivos.get(i)).getCExclusionId());
		    		  String descripcion =  ((OCAPMotExclusionOT) motivos.get(i)).getDDescripcion();
		    		  descripcion = descripcion.replace("[año]", anio);
		    		  sb.append("Codigo: ").append(String.format("%02d", Integer.parseInt(codigo))).append("  :  ").append(descripcion).append(System.getProperty("line.separator"));
		    		  
		    		  
		    	  }
		    	  
		      }
		      
		      
		     }
		     catch (Exception e) {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		       throw e;
		     }		 
		     return sb.toString();
		   }
  
   
   public String buscarCodigoMotivosExpediente(long cExpId)
		     throws Exception
		   {
		     String motivos = null;
		     try
		     {		       
		       OCAPMotExclusionOAD variableOAD = OCAPMotExclusionOAD.getInstance();
		       motivos = variableOAD.buscarCodigoMotivosExpediente(cExpId);		      
		     }
		     catch (Exception e) {
		       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		       throw e;
		     }
		 
		     return motivos;
		   }
		   
 }


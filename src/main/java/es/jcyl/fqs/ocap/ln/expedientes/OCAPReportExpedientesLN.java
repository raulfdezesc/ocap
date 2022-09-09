 package es.jcyl.fqs.ocap.ln.expedientes;
 
 import java.awt.Font;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.lowagie.text.pdf.Barcode128;

import es.jcyl.fqs.ocap.actionforms.usuarios.OCAPUsuariosForm;
import es.jcyl.fqs.ocap.actions.OCAPDirectorio;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.areas.OCAPAreasLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.creditos.OCAPCreditosLN;
import es.jcyl.fqs.ocap.ln.defMeritosCurriculares.OCAPDefMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
import es.jcyl.fqs.ocap.ln.solicitudes.OCAPSolicitudesLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.oad.expedientes.OCAPReportExpedientesOAD;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedientes.OCAPReportExpedientesOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
 
 public class OCAPReportExpedientesLN
 {
   private static final String campoTitulo = "titulo";
   private static final String campoEtiqueta1 = "etiqueta1";
   private static final String campoValor1 = "valor1";
   private static final String campoEtiqueta2 = "etiqueta2";
   private static final String campoValor2 = "valor2";
   private static final String campoTipo = "tipoValor";
   private static final String campoInvalidado = "invalidado";
   private static final String campoCreditosObtenidos = "creditosObtenidos";
   private static final String campoActividad = "actividad";
   private static final String campoCreditosNecesarios = "creditosNecesarios";
   private static final String campoConseguido = "conseguido";
   private static final String campoDatosPersonales = "datosPersonales";
   private static final String campoCDefMeritoCurricular = "cDefMeritoCurricular";
   private static final String campoDefMeritoCurricular = "defMeritoCurricular";
   private static final String campoDatosMC = "datosMC";
   private static final String campoDatosMCOpcionales = "datosMCOpcionales";
   private static final String campoDefMeritoCurricularOpcionales = "defMeritoCurricularOpcionales";
   public Logger loggerBD;
   public Logger logger;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
 
     this.logger = OCAPConfigApp.logger;
   }
 
   public OCAPReportExpedientesLN(JCYLUsuario jcylUsuario)
   {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ReportOT crearReportExpedientesEtiquetas(OCAPReportExpedientesOT datosBusqueda, String rutaRaiz, long nEtiquetas) throws SQLException, Exception
   {
     ReportOT reportOT = null;
     try
     {
       OCAPReportExpedientesOAD reportMCPorExpOAD = OCAPReportExpedientesOAD.getInstance();
       ArrayList listadoDatosUsuariosPorExpediente = reportMCPorExpOAD.buscarDatosUsuariosPorExpediente(datosBusqueda);
 
       String rutaJasperMeritosCurricularesCompilados = rutaRaiz + File.separator + "reports" + File.separator + "compilados" + File.separator + "expedientes";
 
       String nombreReportPadre = "ExpedientesEtiquetas";
       String nombreSubReportExpEtiqDatosPersonales = "ExpEtiqDatosDatosPersonales";
 
       reportOT = new ReportOT(rutaRaiz, File.separator + "expedientes" + File.separator, nombreReportPadre);
 
       Hashtable parametros = new Hashtable();
 
       JasperReport subReportExpEtiqDatosPersonales = (JasperReport)JRLoader.loadObject(rutaJasperMeritosCurricularesCompilados + File.separator + nombreSubReportExpEtiqDatosPersonales + ".jasper");
 
       parametros.put("SubExpEtiqDatosDatosPersonales", subReportExpEtiqDatosPersonales);
 
       parametros.put("rutaImagen", rutaRaiz);
 
       ConceptoReport datosReport = new ConceptoReport();
 
       for (int i = 0; i < listadoDatosUsuariosPorExpediente.size(); i++) {
         for (int k = 0; k < nEtiquetas; k++) {
           datosReport.addRow();
           datosReport.putElement("datosPersonales", (OCAPReportExpedientesOT)listadoDatosUsuariosPorExpediente.get(i));
         }
       }
 
       reportOT.setParametrosReport(parametros);
       reportOT.setDatosReport(datosReport);
 
       liberarObjetos(listadoDatosUsuariosPorExpediente);
     }
     catch (SQLException exSQL)
     {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return reportOT;
   }
 
   public ArrayList crearReportItinerariosEtiquetas(OCAPReportExpedientesOT datosBusqueda, String rutaRaiz) throws SQLException, Exception
   {
     ArrayList listadoInformes = null;
     ArrayList listadoEvid = null;
     ReportOT reportOT = null;
     Barcode128 code128 = null;
     String codEti = null;
     boolean finArray = false;
     Barcode barcode = null;
     DecimalFormat formato = new DecimalFormat("00");
     String itinerario = null;
     long cItinerarioId = 0L;
     String codEpr = null;
     try
     {
       OCAPItinerariosLN oCAPItinerariosLN = new OCAPItinerariosLN(this.jcylUsuario);
 
       OCAPReportExpedientesOAD reportMCPorExpOAD = OCAPReportExpedientesOAD.getInstance();
       ArrayList listadoDatosItinerarioPorExpediente = reportMCPorExpOAD.buscarDatosItinerariosPorExpediente(datosBusqueda);
 
       String rutaJasperMeritosCurricularesCompilados = rutaRaiz + File.separator + "reports" + File.separator + "compilados" + File.separator + "expedientes";
 
       String nombreReportPadre = "ItinerariosEtiquetas";
       String nombreSubReportExpEtiqItinerarios = "ExpEtiqDatosItinerarios";
 
       listadoInformes = new ArrayList();
 
       for (int i = 0; i < listadoDatosItinerarioPorExpediente.size(); i++)
       {
         listadoEvid = oCAPItinerariosLN.listadoEvidencias(((OCAPReportExpedientesOT)listadoDatosItinerarioPorExpediente.get(i)).getCItinerarioId());
 
         if (listadoEvid.size() != 0)
         {
           reportOT = new ReportOT(rutaRaiz, File.separator + "expedientes" + File.separator, nombreReportPadre);
 
           Hashtable parametros = new Hashtable();
 
           JasperReport subReportExpEtiqItinerarios = (JasperReport)JRLoader.loadObject(rutaJasperMeritosCurricularesCompilados + File.separator + nombreSubReportExpEtiqItinerarios + ".jasper");
 
           parametros.put("SubExpEtiqDatosItinerarios", subReportExpEtiqItinerarios);
 
           parametros.put("rutaImagen", rutaRaiz);
 
           ConceptoReport datosReport = new ConceptoReport();
 
           itinerario = ((OCAPReportExpedientesOT)listadoDatosItinerarioPorExpediente.get(i)).getDNombreItin();
           codEpr = ((OCAPReportExpedientesOT)listadoDatosItinerarioPorExpediente.get(i)).getDCodEpr();
           cItinerarioId = ((OCAPReportExpedientesOT)listadoDatosItinerarioPorExpediente.get(i)).getCItinerarioId();
 
           for (int j = 0; j < listadoEvid.size(); j++) {
             OCAPReportExpedientesOT datosOT = new OCAPReportExpedientesOT();
             datosOT.setCEvidenciaId(formato.format(((OCAPCuestionariosOT)listadoEvid.get(j)).getCEvidenciaId()));
             datosOT.setDNombreItin(itinerario);
             datosOT.setDCodEpr(codEpr);
             datosOT.setCItinerarioId(cItinerarioId);
 
             if (((OCAPCuestionariosOT)listadoEvid.get(j)).getNPreguntas().intValue() != 0)
               datosOT.setDTipoPrueba("P");
             else {
               datosOT.setDTipoPrueba("E");
             }
             datosReport.addRow();
 
             datosReport.putElement("datosItinerarios", datosOT);
 
             code128 = new Barcode128();
 
             codEti = datosOT.getDCodEpr() + "-" + datosOT.getCItinerarioId() + "-" + datosOT.getCEvidenciaId();
 
             barcode = BarcodeFactory.createCode128B(codEti);
 
             barcode.setBarHeight(250);
             barcode.setBarWidth(3);
             barcode.setFont(new Font(null, 0, 30));
 
             datosReport.putElement("codBarras", barcode);
           }
 
           reportOT.setParametrosReport(parametros);
           reportOT.setDatosReport(datosReport);
 
           listadoInformes.add(reportOT);
         }
 
       }
 
       liberarObjetos(listadoDatosItinerarioPorExpediente);
     }
     catch (SQLException exSQL)
     {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listadoInformes;
   }
 
   public ArrayList crearListadoReportsExpedientesMerCurriculares(OCAPReportExpedientesOT datosBusqueda, String rutaRaiz)
     throws SQLException, Exception
   {
     ArrayList listadoInformes = null;
     try
     {
    	 
    	
       OCAPReportExpedientesOAD reportMCPorExpOAD = OCAPReportExpedientesOAD.getInstance();
       ArrayList listadoDatosUsuariosPorExpediente = reportMCPorExpOAD.buscarDatosUsuariosPorExpediente(datosBusqueda);
       Hashtable tablaCreditosDefMCPorExpediente = reportMCPorExpOAD.buscarDatosCreditosDefMCPorExpediente(datosBusqueda);
       Hashtable tablaDatosMCPorExpediente = reportMCPorExpOAD.buscarDatosMCPorExpediente(datosBusqueda);
 
       String rutaJasperExpedientesCompilados = rutaRaiz + File.separator + "reports" + File.separator + "compilados" + File.separator + "expedientes";
 
       String nombreReportPadre = "ExpedientesMeritosCurriculares";
       String nombreSubReportDatosPersonales = "ExpMCDatosPersonales";
       String nombreSubReportDatosCreditosCurriculares = "ExpMCDatosCreditosCurriculares";
       String nombreSubReportDatosMeritosCurriculares = "ExpMCDatosMeritosCurriculares";
       String nombreSubReportDatosMeritosCurricularesOpcionales = "ExpMCDatosMeritosCurricularesOpcionales";
       String nombreSubReportDatosMeritosCurricularesAgrupacion = "ExpMCDatosMeritosCurricularesAgrupacion";
       String nombreSubReportDatosMeritosCurricularesValores = "ExpMCDatosMeritosCurricularesValores";
 
       listadoInformes = new ArrayList();
       
 
       for (int i = 0; i < listadoDatosUsuariosPorExpediente.size(); i++)
       {
         ReportOT reportOT = new ReportOT(rutaRaiz, File.separator + "expedientes" + File.separator, nombreReportPadre);
 
         Hashtable parametros = new Hashtable();
 
         JasperReport subReportDatosPersonales = (JasperReport)JRLoader.loadObject(rutaJasperExpedientesCompilados + File.separator + nombreSubReportDatosPersonales + ".jasper");
 
         parametros.put("SubExpMCDatosPersonales", subReportDatosPersonales);
 
         JasperReport subReportDatosCreditosCurriculares = (JasperReport)JRLoader.loadObject(rutaJasperExpedientesCompilados + File.separator + nombreSubReportDatosCreditosCurriculares + ".jasper");
 
         parametros.put("SubExpMCDatosCreditosCurriculares", subReportDatosCreditosCurriculares);
 
         JasperReport subReportDatosMeritosCurriculares = (JasperReport)JRLoader.loadObject(rutaJasperExpedientesCompilados + File.separator + nombreSubReportDatosMeritosCurriculares + ".jasper");
 
         parametros.put("SubExpMCDatosMeritosCurriculares", subReportDatosMeritosCurriculares);
 
         JasperReport subReportDatosMeritosCurricularesOpcionales = (JasperReport)JRLoader.loadObject(rutaJasperExpedientesCompilados + File.separator + nombreSubReportDatosMeritosCurricularesOpcionales + ".jasper");
 
         parametros.put("SubExpMCDatosMeritosCurricularesOpcionales", subReportDatosMeritosCurricularesOpcionales);
 
         JasperReport subReportDatosMeritosCurricularesAgrupacion = (JasperReport)JRLoader.loadObject(rutaJasperExpedientesCompilados + File.separator + nombreSubReportDatosMeritosCurricularesAgrupacion + ".jasper");
 
         parametros.put("SubExpMCDatosMeritosCurricularesAgrupacion", subReportDatosMeritosCurricularesAgrupacion);
 
         JasperReport subReportDatosMeritosCurricularesValores = (JasperReport)JRLoader.loadObject(rutaJasperExpedientesCompilados + File.separator + nombreSubReportDatosMeritosCurricularesValores + ".jasper");
 
         parametros.put("SubExpMCDatosMeritosCurricularesValores", subReportDatosMeritosCurricularesValores);
 
         parametros.put("rutaImagen", rutaRaiz);
 
         parametros.put("datosPersonales", listadoDatosUsuariosPorExpediente.get(i));
 
         parametros.put("datosCreditosCurriculares", crearExpMCDatosCreditosCurriculares((OCAPReportExpedientesOT)listadoDatosUsuariosPorExpediente.get(i), tablaCreditosDefMCPorExpediente, tablaDatosMCPorExpediente, datosBusqueda));
 
         parametros.put("datosMeritosCurriculares", crearExpMCDatosMeritosCurriculares((OCAPReportExpedientesOT)listadoDatosUsuariosPorExpediente.get(i), tablaCreditosDefMCPorExpediente, tablaDatosMCPorExpediente, datosBusqueda.getBMCUsuario()));
 
 
         ConceptoReport datosReport = new ConceptoReport();
 
         reportOT.setParametrosReport(parametros);
         reportOT.setDatosReport(datosReport);
 
         listadoInformes.add(reportOT);
       }
 
       liberarObjetos(listadoDatosUsuariosPorExpediente);
       liberarObjetos(tablaCreditosDefMCPorExpediente);
       liberarObjetos(tablaDatosMCPorExpediente);
     }
     catch (SQLException exSQL)
     {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listadoInformes;
   }
 
   private ConceptoReport crearExpMCDatosCreditosCurriculares(OCAPReportExpedientesOT datosExpediente, Hashtable tablaCreditosDefMCPorExpediente, Hashtable tablaDatosMCPorExpediente,  OCAPReportExpedientesOT datosBusqueda)
     throws Exception
   {
	  
	   String bMCUsuario = datosBusqueda.get_bMCUsuario();
	   
	  
	   
     ConceptoReport conceptoCreditos = null;
     try
     {
       ArrayList listadoDefMC = (ArrayList)tablaCreditosDefMCPorExpediente.get(Long.valueOf(datosExpediente.getCExpedienteId()));
       Hashtable tablaDefMCMeritosCurriculares = (Hashtable)tablaDatosMCPorExpediente.get(Long.valueOf(datosExpediente.getCExpedienteId()));
       conceptoCreditos = new ConceptoReport();
 
       if (listadoDefMC != null) {
         for (int i = 0; i < listadoDefMC.size(); i++) 
         {
           OCAPReportExpedientesOT datosDefMC = (OCAPReportExpedientesOT)listadoDefMC.get(i);
           conceptoCreditos.addRow();
           conceptoCreditos.putElement("actividad", datosDefMC.getDDefMeritoCurricular());
 
           float credNecesario = datosDefMC.getNCreditosDefMeritoCurricular();
           conceptoCreditos.putElement("creditosNecesarios", Float.valueOf(datosDefMC.getNCreditosDefMeritoCurricular()));
           float credObtenido = calcularExpMCCreditosCurricularesObtenidosPorDefMC(datosDefMC, tablaDefMCMeritosCurriculares, bMCUsuario);
           conceptoCreditos.putElement("creditosObtenidos", Float.valueOf(credObtenido));

           float credCEIS = calcularExpMCCreditosCurricularesCEIS(datosDefMC, tablaDefMCMeritosCurriculares, bMCUsuario);
           conceptoCreditos.putElement("creditosCEIS", Float.valueOf(credCEIS));
           
           float credCC = calcularExpMCCreditosCurricularesCC(datosDefMC, tablaDefMCMeritosCurriculares, bMCUsuario);
           conceptoCreditos.putElement("creditosCC", Float.valueOf(credCC));          
         
           
           if (credObtenido >= credNecesario)
             conceptoCreditos.putElement("conseguido", new Boolean(true));
           else
             conceptoCreditos.putElement("conseguido", new Boolean(false));
           
           
           if (credCEIS >= credNecesario)
               conceptoCreditos.putElement("conseguidoCEIS", new Boolean(true));
           else
               conceptoCreditos.putElement("conseguidoCEIS", new Boolean(false));
           
           if (credCC >= credNecesario)
               conceptoCreditos.putElement("conseguidoCC", new Boolean(true));
           else
               conceptoCreditos.putElement("conseguidoCC", new Boolean(false));
           
           // Pasamos al report los flags para indicar si se pintan o no los bloques de CEIS / CC
                  
           if (datosBusqueda.getDOrigenReport().equals(Constantes.ORIGEN_USUARIOS))
    	   {
        	   conceptoCreditos.putElement("verCEIS", datosBusqueda.getBVerCeisReport());
        	   conceptoCreditos.putElement("verCC", datosBusqueda.getBVerCCReport());
    	   }
    	   
    	   else // origen Expediente. Filtramos por perfil usuario
    	   {
    		   if (datosBusqueda.getJcylUsuario().getUser().getRol().equals(Constantes.OCAP_CEIS)){
    			   
    			   conceptoCreditos.putElement("verCEIS", new Boolean(true));
            	   conceptoCreditos.putElement("verCC", new Boolean(false));
    			   
    		   }else if (datosBusqueda.getJcylUsuario().getUser().getRol().equals(Constantes.OCAP_CC) ||
    				   (datosBusqueda.getJcylUsuario().getUser().getRol().equals(Constantes.OCAP_GRS))){
    			   boolean valMC = isPeriodoEvaluacionMC(datosExpediente);
    			   if(valMC){
    				   conceptoCreditos.putElement("verCC",new Boolean(false));
    			   }else{
    				   conceptoCreditos.putElement("verCC",new Boolean(true));
    			   }
    			   conceptoCreditos.putElement("verCEIS", new Boolean(true));
    		   }
    	   }
           
         }
       }
     }
     catch (Exception e)
     {
       throw e;
     }
 
     return conceptoCreditos;
   }
 
   private float calcularExpMCCreditosCurricularesCEIS(OCAPReportExpedientesOT datosDefMC,
		Hashtable tablaDefMCMeritosCurriculares, String bMCUsuario) {
	   boolean bCalcularCreditosMCCTS = false;
	   float creditosHoraCTSP = 0.0F;
	   float creditosHoraCTSNP = 0.0F;
	   float creditosHoraCTSM = 0.0F;
	   int numHorasCTSP = 0;
	   int numHorasCTSNP = 0;
	   int numHorasCTSM = 0;
	   float suma = 0.0F;
	     
		if (tablaDefMCMeritosCurriculares != null) {
			ArrayList listadoMC = (ArrayList) tablaDefMCMeritosCurriculares.get(Long.valueOf(datosDefMC.getCDefMeritoCurricularId()));
			if (listadoMC != null) {
				for (int j = 0; j < listadoMC.size(); j++) {
					OCAPReportExpedientesOT datosDefMCporExp = (OCAPReportExpedientesOT) listadoMC.get(j);

					if (datosDefMC.getCExpedienteId() == datosDefMCporExp.getCExpedienteId()) {
						if (datosDefMCporExp.getBInvalidadoCEIS() == null || datosDefMCporExp.getBInvalidadoCEIS().equalsIgnoreCase("N")) {

							if ((Constantes.MC_TALLER.equals(datosDefMCporExp.getCTipoMeritoCurricular())) && (Constantes.NO.equals(datosDefMCporExp.getBAcreditadoExpMC()))) {
								if (datosDefMCporExp.getCMerModalidadMC() == 1L) {
									numHorasCTSP += datosDefMCporExp.getNHoras();
									creditosHoraCTSP = datosDefMCporExp.getNCreditosMC();
									bCalcularCreditosMCCTS = true;
								} else if (datosDefMCporExp.getCMerModalidadMC() == 2) {
									numHorasCTSNP += datosDefMCporExp.getNHoras();
									creditosHoraCTSNP = datosDefMCporExp.getNCreditosMC();
									bCalcularCreditosMCCTS = true;
								} else if (datosDefMCporExp.getCMerModalidadMC() == 8) {
									numHorasCTSM += datosDefMCporExp.getNHoras();
									creditosHoraCTSM = datosDefMCporExp.getNCreditosMC();
									bCalcularCreditosMCCTS = true;
								}
							}else {
								suma += datosDefMCporExp.getNCreditosCEIS();
							}
						}
					}
				}
			}
		}

		if (bCalcularCreditosMCCTS) {
			float creditosTotCTSP = (float) (Math.floor(numHorasCTSP / 10) * creditosHoraCTSP);
			float creditosTotCTSNP = (float) (Math.floor(numHorasCTSNP / 10) * creditosHoraCTSNP);
			float creditosTotCTSM = (float) (Math.floor(numHorasCTSM / 10) * creditosHoraCTSM);
			suma = suma + creditosTotCTSP + creditosTotCTSNP + creditosTotCTSM;
		}

		return (float) (Math.rint(suma * 100.0F) / 100.0D);
}

private float calcularExpMCCreditosCurricularesCC(OCAPReportExpedientesOT datosDefMC,
		Hashtable tablaDefMCMeritosCurriculares, String bMCUsuario) {

	   boolean bCalcularCreditosMCCTS = false;
	   float creditosHoraCTSP = 0.0F;
	   float creditosHoraCTSNP = 0.0F;
	   float creditosHoraCTSM = 0.0F;
	   int numHorasCTSP = 0;
	   int numHorasCTSNP = 0;
	   int numHorasCTSM = 0;
	   float suma = 0.0F;
	     
		if (tablaDefMCMeritosCurriculares != null) {
			ArrayList listadoMC = (ArrayList) tablaDefMCMeritosCurriculares.get(Long.valueOf(datosDefMC.getCDefMeritoCurricularId()));
			if (listadoMC != null) {
				for (int j = 0; j < listadoMC.size(); j++) {
					OCAPReportExpedientesOT datosDefMCporExp = (OCAPReportExpedientesOT) listadoMC.get(j);

					if (datosDefMC.getCExpedienteId() == datosDefMCporExp.getCExpedienteId()) {
						if (datosDefMCporExp.getBInvalidadoCC() == null || datosDefMCporExp.getBInvalidadoCC().equalsIgnoreCase("N")) {

							if ((Constantes.MC_TALLER.equals(datosDefMCporExp.getCTipoMeritoCurricular())) && (Constantes.NO.equals(datosDefMCporExp.getBAcreditadoExpMC()))) {
								if (datosDefMCporExp.getCMerModalidadMC() == 1L) {
									numHorasCTSP += datosDefMCporExp.getNHoras();
									creditosHoraCTSP = datosDefMCporExp.getNCreditosMC();
									bCalcularCreditosMCCTS = true;
								} else if (datosDefMCporExp.getCMerModalidadMC() == 2) {
									numHorasCTSNP += datosDefMCporExp.getNHoras();
									creditosHoraCTSNP = datosDefMCporExp.getNCreditosMC();
									bCalcularCreditosMCCTS = true;
								} else if (datosDefMCporExp.getCMerModalidadMC() == 8) {
									numHorasCTSM += datosDefMCporExp.getNHoras();
									creditosHoraCTSM = datosDefMCporExp.getNCreditosMC();
									bCalcularCreditosMCCTS = true;
								}
							}else {
								suma += datosDefMCporExp.getNCreditosCC();
							}
						}
					}
				}
			}
		}

		if (bCalcularCreditosMCCTS) {
			float creditosTotCTSP = (float) (Math.floor(numHorasCTSP / 10) * creditosHoraCTSP);
			float creditosTotCTSNP = (float) (Math.floor(numHorasCTSNP / 10) * creditosHoraCTSNP);
			float creditosTotCTSM = (float) (Math.floor(numHorasCTSM / 10) * creditosHoraCTSM);
			suma = suma + creditosTotCTSP + creditosTotCTSNP + creditosTotCTSM;
		}

		return (float) (Math.rint(suma * 100.0F) / 100.0D);	
    
}

private boolean isPeriodoEvaluacionMC(OCAPReportExpedientesOT datosExpediente) {
	   	OCAPUsuariosLN usuariosLN = new OCAPUsuariosLN(jcylUsuario);
		OCAPUsuariosOT usuariosOT;
		try {
			usuariosOT = usuariosLN.datosPersonalesUsuario(datosExpediente.getCDNIReal(), datosExpediente.getCExpedienteId(), jcylUsuario);

		   	OCAPConvocatoriasLN convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convoOT = convocatoriaLN.buscarOCAPConvocatorias(datosExpediente.getCConvocatoriaId());
			OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
			OCAPExpedientecarreraOT expedienteCarreraOT = expCarreraLN.buscarExpedienteCarreraPorUsuarioConvocatoria(
					datosExpediente.getCUsuarioId(), Long.valueOf(datosExpediente.getCConvocatoriaId()));
	
			/*
			 * Fijamos si estamos en periodo de Valoración de MC
			 * Tomanos las fechas indicadas en el expediente, no en la convocatoria
			 */
			String fechaOriginalValoracionMC = convoOT.getFInicioValoracionMC();
			long plazoOriginalValMC = convoOT.getNDiasValMc();
			Calendar c = Calendar.getInstance();
			/* Datos de convocatoria antiguos => calculamos fechas */
			if (expedienteCarreraOT.getFFinEvalMc() == null) {
				Calendar aux = Calendar.getInstance();
				/* F Inicio Valoracion MC = F Inicio MC + Plazo autoev MC */
				aux.setTime(DateUtil.convertStringToDate(convoOT.getFInicioMC()));
				aux.add(Calendar.DATE, (int) convoOT.getNDiasAutoMc());
				convoOT.setFInicioValoracionMC(DateUtil.convertDateToString(aux.getTime()));
				aux.setTime(DateUtil.convertStringToDate(convoOT.getFInicioValoracionMC()));
			    // F Fin Valoracion MC = F Inicio Valoracion MC + Plazo valoracion MC
				aux.add(Calendar.DATE, (int) convoOT.getNDiasValMc());
				convoOT.setFFinValoracionMC(DateUtil.convertDateToString(aux.getTime()));
				expedienteCarreraOT.setFFinEvalMc(aux.getTime());
			}
			
	
			/* Fijamos si estamos en periodo de Valoración de CC */
			c = Calendar.getInstance();		
	
			/*En estos puntos se actualiza la fecha de inicio de evaluación de MC, reemplazando lo que haya en el expediente, por lo que diga
			 *       la convocatoria asociada
			 *       Cambiamos la asociación a los nuevos campos de Convocatorioa (si en el expediente no hay datos de Finicio/FFin evaluacion MC, cogera
			 *       los campos similares de la convocatoria.
			 */
			if ((convoOT.getFInicioValoracionMC() != null) && ((usuariosOT.getFInicioEvalMC() == null) ||
				(usuariosOT.getFInicioEvalMC().before(DateUtil.convertStringToDate(convoOT.getFInicioValoracionMC()))))) {
				expedienteCarreraOT.setFInicioEvalMc(DateUtil.convertStringToDate(fechaOriginalValoracionMC));
			}
	
			if ((convoOT.getFFinValoracionMC() != null) && ((usuariosOT.getFFinEvalMC() == null)
					|| (usuariosOT.getFFinEvalMC().before(DateUtil.convertStringToDate(convoOT.getFFinValoracionMC()))))) {
				Calendar aux = Calendar.getInstance();
				aux.setTime(DateUtil.convertStringToDate(fechaOriginalValoracionMC));
				aux.add(Calendar.DATE, (int)plazoOriginalValMC);
				expedienteCarreraOT.setFFinEvalMc(aux.getTime());
			}
			
			/* Durante el último día de Val MC también se valoran méritos */
			c.setTime(expedienteCarreraOT.getFFinEvalMc());
			c.add(Calendar.DATE, 1);
			/* Si estamos entre las fechas de inicio y fin */
			if ((new Date()).getTime() >= (expedienteCarreraOT.getFInicioEvalMc()).getTime()
					&& (new Date()).getTime() < c.getTime().getTime()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	
   }

private float calcularExpMCCreditosCurricularesObtenidosPorDefMC(OCAPReportExpedientesOT datosDefMC, Hashtable tablaDefMCMeritosCurriculares, String bMCUsuario)
   {
     float suma = 0.0F;
     int numHorasCTSP = 0;
     int numHorasCTSNP = 0;
     int numHorasCTSM = 0;
     float creditosHoraCTSP = 0.0F;
     float creditosHoraCTSNP = 0.0F;
     float creditosHoraCTSM = 0.0F;
     boolean bCalcularCreditosMCCTS = false;
 
     if (tablaDefMCMeritosCurriculares != null)
     {
       ArrayList listadoMC = (ArrayList)tablaDefMCMeritosCurriculares.get(Long.valueOf(datosDefMC.getCDefMeritoCurricularId()));
       if (listadoMC != null) {
         for (int i = 0; i < listadoMC.size(); i++) {
           OCAPReportExpedientesOT datosMC = (OCAPReportExpedientesOT)listadoMC.get(i);
 
           if ((Constantes.MC_TALLER.equals(datosMC.getCTipoMeritoCurricular())) && (Constantes.NO.equals(datosMC.getBAcreditadoExpMC())))
           {
            //if ((Constantes.SI.equals(bMCUsuario)) || ((Constantes.NO.equals(bMCUsuario)) && 
           // 		 ((datosMC.getBInvalidadoCEIS() == null) || (Constantes.NO.equals(datosMC.getBInvalidadoCEIS()))))){
               if (datosMC.getCMerModalidadMC() == 1L) {
                 numHorasCTSP += datosMC.getNHoras();
                 creditosHoraCTSP = datosMC.getNCreditosMC();
                 bCalcularCreditosMCCTS = true;
               } else if (datosMC.getCMerModalidadMC() == 2) {
                 numHorasCTSNP += datosMC.getNHoras();
                 creditosHoraCTSNP = datosMC.getNCreditosMC();
                 bCalcularCreditosMCCTS = true;
               } else if (datosMC.getCMerModalidadMC() == 8) {
                 numHorasCTSM += datosMC.getNHoras();
                 creditosHoraCTSM = datosMC.getNCreditosMC();
                 bCalcularCreditosMCCTS = true;
               }
 
            // }
 
           }
//           else if (Constantes.NO.equals(bMCUsuario)) {
//        	   if (Constantes.SI.equals(datosMC.getBInvalidadoCEIS())) {
//        		   suma += 0;
//        	   } else {
//        		   suma += datosMC.getNCreditosCEIS();
//        	   }
//           }
           else {
             suma += datosMC.getNCreditosUsuario();
           }
         }
       }
 
     }
 
     if (bCalcularCreditosMCCTS)
     {
       float creditosTotCTSP = (float)(Math.floor(numHorasCTSP / 10) * creditosHoraCTSP);
       float creditosTotCTSNP = (float)(Math.floor(numHorasCTSNP / 10) * creditosHoraCTSNP);
       float creditosTotCTSM = (float)(Math.floor(numHorasCTSM / 10) * creditosHoraCTSM);
       suma = suma + creditosTotCTSP + creditosTotCTSNP + creditosTotCTSM;
     }
 
     return (float)(Math.rint(suma * 100.0F) / 100.0D);
   }
 
   private ConceptoReport crearExpMCDatosMeritosCurriculares(OCAPReportExpedientesOT datosExpediente, Hashtable tablaCreditosDefMCPorExpediente, Hashtable tablaDatosMCPorExpediente, String bMCUsuario)
     throws Exception
   {
     ConceptoReport conceptoMeritos = null;
     try
     {
       ArrayList listadoDefMC = (ArrayList)tablaCreditosDefMCPorExpediente.get(Long.valueOf(datosExpediente.getCExpedienteId()));
       Hashtable tablaDefMCMeritosCurriculares = (Hashtable)tablaDatosMCPorExpediente.get(Long.valueOf(datosExpediente.getCExpedienteId()));
 
       conceptoMeritos = new ConceptoReport();
 
       if (listadoDefMC != null) {
         for (int i = 0; i < listadoDefMC.size(); i++)
         {
           OCAPReportExpedientesOT datosDefMC = (OCAPReportExpedientesOT)listadoDefMC.get(i);
 
           if ((tablaDefMCMeritosCurriculares != null) && (tablaDefMCMeritosCurriculares.containsKey(Long.valueOf(datosDefMC.getCDefMeritoCurricularId()))))
           {
             conceptoMeritos.addRow();
             conceptoMeritos.putElement("cDefMeritoCurricular", Long.valueOf(datosDefMC.getCDefMeritoCurricularId()));
             conceptoMeritos.putElement("defMeritoCurricular", datosDefMC.getDDefMeritoCurricular());
 
             ConceptoReport conceptoMC = new ConceptoReport();
             ConceptoReport conceptoMCOpcionales = new ConceptoReport();
 
             ArrayList listadoMC = (ArrayList)tablaDefMCMeritosCurriculares.get(Long.valueOf(datosDefMC.getCDefMeritoCurricularId()));
             if (Constantes.DEF_MERITO_OPCIONALES.equals(String.valueOf(datosDefMC.getCDefMeritoCurricularId())))
               conceptoMCOpcionales = crearExpMCDatosMeritosCurricularesOpcionales(listadoMC, bMCUsuario);
             else {
               conceptoMC = crearExpMCDatosMeritosCurricularesAgrupacion(listadoMC, bMCUsuario);
             }
 
             conceptoMeritos.putElement("datosMC", conceptoMC);
             conceptoMeritos.putElement("datosMCOpcionales", conceptoMCOpcionales);
           }
         }
       }
 
     }
     catch (Exception e)
     {
       throw e;
     }
 
     return conceptoMeritos;
   }
 
   private ConceptoReport crearExpMCDatosMeritosCurricularesAgrupacion(ArrayList listadoMC, String bMCUsuario)
     throws Exception
   {
     ConceptoReport conceptoMCAgrupacion = null;
     ConceptoReport conceptoValores = null;
     try
     {
       String campoDAgrupacionValores = "dAgrupacionValores";
       String campoDatosValores = "datosValores";
 
       conceptoMCAgrupacion = new ConceptoReport();
       String agrupacion = "";
       boolean siCorrido = false;
       boolean bMostrarTotalCredCTSNA = false;
       String tipoMeritoAgrupacion = "";
       long cExpId = 0L;
 
       for (int i = 0; i < listadoMC.size(); i++)
       {
         OCAPReportExpedientesOT datosMC = (OCAPReportExpedientesOT)listadoMC.get(i);
 
         if (!agrupacion.equals(datosMC.getDMeritoCurricular()))
         {
           if (i != 0)
           {
             if (("Taller".equals(tipoMeritoAgrupacion)) && (bMostrarTotalCredCTSNA))
             {
               OCAPReportExpedientesOAD reportExpedientesOAD = OCAPReportExpedientesOAD.getInstance();
               float creditosTotalesCTSNA = reportExpedientesOAD.calcularCreditosTotalesCTSNA(cExpId, bMCUsuario);
               conceptoValores.addRow();
               conceptoValores.putElement("tipoValor", "Creditos");
               conceptoValores.putElement("creditosObtenidos", String.valueOf(creditosTotalesCTSNA));
               conceptoValores.addRow();
               conceptoValores.putElement("tipoValor", "SeparadorMeritos");
             }
 
             conceptoMCAgrupacion.addRow();
             conceptoMCAgrupacion.putElement(campoDAgrupacionValores, agrupacion);
             conceptoMCAgrupacion.putElement(campoDatosValores, conceptoValores);
 
             conceptoValores = new ConceptoReport();
           }
           else if (i == 0) {
             conceptoValores = new ConceptoReport();
           }
 
           agrupacion = datosMC.getDMeritoCurricular();
           tipoMeritoAgrupacion = datosMC.getCTipoMeritoCurricular();
           cExpId = datosMC.getCExpedienteId();
         }
 
         if (("Taller".equals(datosMC.getCTipoMeritoCurricular())) && (Constantes.NO.equals(datosMC.getBAcreditadoExpMC()))) {
           bMostrarTotalCredCTSNA = true;
         }
 
         rellenarDatosExpMC(conceptoValores, datosMC, bMCUsuario, jcylUsuario);
 
         siCorrido = true;
       }
 
       if (siCorrido)
       {
         if (("Taller".equals(tipoMeritoAgrupacion)) && (bMostrarTotalCredCTSNA))
         {
           OCAPReportExpedientesOAD reportExpedientesOAD = OCAPReportExpedientesOAD.getInstance();
           float creditosTotalesCTSNA = reportExpedientesOAD.calcularCreditosTotalesCTSNA(cExpId, bMCUsuario);
           conceptoValores.addRow();
           conceptoValores.putElement("tipoValor", "Creditos");
           conceptoValores.putElement("creditosObtenidos", String.valueOf(creditosTotalesCTSNA));
           conceptoValores.addRow();
           conceptoValores.putElement("tipoValor", "SeparadorMeritos");
         }
 
         conceptoMCAgrupacion.addRow();
         conceptoMCAgrupacion.putElement(campoDAgrupacionValores, agrupacion);
         conceptoMCAgrupacion.putElement(campoDatosValores, conceptoValores);
       }
     }
     catch (Exception e) {
       throw e;
     }
 
     return conceptoMCAgrupacion;
   }
 
   private ConceptoReport crearExpMCDatosMeritosCurricularesOpcionales(ArrayList listadoMC, String bMCUsuario)
     throws Exception
   {
     ConceptoReport conceptoMCOpcionales = null;
     try
     {
       Hashtable tablaDefMCOpcionales = new Hashtable();
       ArrayList listadoMCIntroducidosParaDefMCOpcional = new ArrayList();
       ArrayList listadoDefMCAuxiliar = new ArrayList();
       OCAPReportExpedientesOT datosAgrupacion = new OCAPReportExpedientesOT();
       boolean siCorrido = false;
       for (int k = 0; k < listadoMC.size(); k++) {
         OCAPReportExpedientesOT datosMC = (OCAPReportExpedientesOT)listadoMC.get(k);
 
         if (datosAgrupacion.getCDefMeritoCurricularId() != datosMC.getCDefMeritoCurricularId()) {
           if (k != 0) {
             tablaDefMCOpcionales.put(datosAgrupacion, listadoMCIntroducidosParaDefMCOpcional);
             listadoMCIntroducidosParaDefMCOpcional = new ArrayList();
             listadoDefMCAuxiliar.add(datosAgrupacion);
           }
           datosAgrupacion = datosMC;
         }
 
         listadoMCIntroducidosParaDefMCOpcional.add(datosMC);
         siCorrido = true;
       }
 
       if (siCorrido) {
         tablaDefMCOpcionales.put(datosAgrupacion, listadoMCIntroducidosParaDefMCOpcional);
         listadoDefMCAuxiliar.add(datosAgrupacion);
       }
 
       conceptoMCOpcionales = new ConceptoReport();
       for (int k = 0; k < listadoDefMCAuxiliar.size(); k++) {
         OCAPReportExpedientesOT datosDefMCAuxiliar = (OCAPReportExpedientesOT)listadoDefMCAuxiliar.get(k);
 
         ArrayList listado = (ArrayList)tablaDefMCOpcionales.get(datosDefMCAuxiliar);
         conceptoMCOpcionales.addRow();
         conceptoMCOpcionales.putElement("defMeritoCurricularOpcionales", datosDefMCAuxiliar.getDDefMeritoCurricular());
         conceptoMCOpcionales.putElement("datosMC", crearExpMCDatosMeritosCurricularesAgrupacion(listado, bMCUsuario));
       }
     }
     catch (Exception e) {
       throw e;
     }
 
     return conceptoMCOpcionales;
   }
 
   private void rellenarDatosExpMC(ConceptoReport conceptoValores, OCAPReportExpedientesOT datosMC, String bMCUsuario, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       try
       {
         Object instanciaClaseValores = Class.forName("es.jcyl.fqs.ocap.ln.expedientes.OCAPReportExpedientesMerCurricuValoresLN").newInstance();
         Class claseValores = instanciaClaseValores.getClass();
 
         Class[] tiposClasesArgumentos = { ConceptoReport.class, OCAPReportExpedientesOT.class, JCYLUsuario.class };
         Method metodo = claseValores.getDeclaredMethod("rellenarDatosExpMC" + datosMC.getCTipoMeritoCurricular(), tiposClasesArgumentos);
 
         Object[] argumentos = { conceptoValores, datosMC , jcylUsuario};
         metodo.invoke(instanciaClaseValores, argumentos);
       }
       catch (ClassNotFoundException cnfe) {
         conceptoValores.addRow();
         conceptoValores.putElement("titulo", datosMC.getDExpedienteMC());
         conceptoValores.putElement("tipoValor", "Titulo");
         this.logger.error("Error al buscar la clase que rellena el tipo de mérito curricular: " + datosMC.getCTipoMeritoCurricular(), cnfe);
       } catch (IllegalAccessException iae) {
         conceptoValores.addRow();
         conceptoValores.putElement("titulo", datosMC.getDExpedienteMC());
         conceptoValores.putElement("tipoValor", "Titulo");
         this.logger.error("Error al acceder la clase que rellena el tipo de mérito curricular: " + datosMC.getCTipoMeritoCurricular(), iae);
       } catch (NoSuchMethodException nsme) {
         conceptoValores.addRow();
         conceptoValores.putElement("titulo", datosMC.getDExpedienteMC());
         conceptoValores.putElement("tipoValor", "Titulo");
         this.logger.error("Error al buscar el método que rellena el tipo de mérito curricular: " + datosMC.getCTipoMeritoCurricular(), nsme);
       } catch (InvocationTargetException ite) {
         throw new Exception(ite.getTargetException());
       }
 
       if (("Taller".equals(datosMC.getCTipoMeritoCurricular())) && (Constantes.NO.equals(datosMC.getBAcreditadoExpMC())))
       {
         if ((Constantes.NO.equals(bMCUsuario)) && (Constantes.SI.equals(datosMC.getBInvalidadoCEIS()))) {
        	 conceptoValores.addRow();
        	 conceptoValores.putElement("tipoValor", "InvalidadoCeis");
        	 conceptoValores.putElement("invalidado", "INVALIDADO");
         }else if ((Constantes.CC.equals(bMCUsuario)) && (Constantes.SI.equals(datosMC.getBInvalidadoCC()))){
        	 conceptoValores.addRow();
             conceptoValores.putElement("tipoValor", "InvalidadoCc");
             conceptoValores.putElement("invalidado", "INVALIDADO");
         }
 
       } else if (Constantes.NO.equals(bMCUsuario)) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement("tipoValor", "Creditos");
    	   conceptoValores.putElement("creditosObtenidos", Float.valueOf(datosMC.getNCreditosCEIS()).toString());
 
         if (Constantes.SI.equals(datosMC.getBInvalidadoCEIS())) {
           conceptoValores.addRow();
           conceptoValores.putElement("tipoValor", "InvalidadoCeis");
           conceptoValores.putElement("invalidado", "INVALIDADO");
         }
       }else if (Constantes.CC.equals(bMCUsuario)) {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement("tipoValor", "Creditos");
    	   conceptoValores.putElement("creditosObtenidos", Float.valueOf(datosMC.getNCreditosCC()).toString());
 
         if (Constantes.SI.equals(datosMC.getBInvalidadoCC())) {
           conceptoValores.addRow();
           conceptoValores.putElement("tipoValor", "InvalidadoCc");
           conceptoValores.putElement("invalidado", "INVALIDADO");
         }
       }else {
    	   conceptoValores.addRow();
    	   conceptoValores.putElement("tipoValor", "Creditos");
    	   conceptoValores.putElement("creditosObtenidos", Float.valueOf(datosMC.getNCreditosUsuario()).toString());
       }
 
       conceptoValores.addRow();
       conceptoValores.putElement("tipoValor", "SeparadorMeritos");
     }
     catch (Exception e)
     {
       throw e;
     }
   }
 
   private void liberarObjetos(Object objeto)
   {
     if (objeto != null)
     {
       if ((objeto instanceof ArrayList))
       {
         for (int i = ((ArrayList)objeto).size() - 1; i >= 0; i--) {
           liberarObjetos(((ArrayList)objeto).get(i));
           ((ArrayList)objeto).remove(i);
         }
 
         objeto = null;
       }
       else if ((objeto instanceof Hashtable))
       {
         Enumeration e = ((Hashtable)objeto).keys();
 
         while (e.hasMoreElements()) {
           Object subObjeto = e.nextElement();
           liberarObjetos(((Hashtable)objeto).get(subObjeto));
           ((Hashtable)objeto).remove(subObjeto);
           subObjeto = null;
         }
         objeto = null;
       }
       else {
         objeto = null;
       }
     }
   }
 
   public ArrayList crearListadoReportsExpedientesInfMotivado(OCAPReportExpedientesOT datosBusqueda, long cEstadoId, String rutaRaiz, JCYLUsuario jcylUsuario)
     throws SQLException, Exception
   {
     ArrayList listadoDatosUsuariosPorExpediente = null;
     ArrayList listadoInformes = null;
     JasperReport jasperSubReport = null;
     ConceptoReport matrizDatosReport = null;
     SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
     String fechaForm = "";
     OCAPUsuariosOT usuariosOT = null;
     OCAPUsuariosLN usuariosLN = null;
     OCAPSolicitudesLN solicitudesLN = null;
     OCAPCreditosLN creditosLN = null;
     OCAPDefMeritoscurricularesLN defMCLN = null;
     OCAPConvocatoriasLN convoLN = null;
     OCAPConvocatoriasOT convoOT = null;
     OCAPReportExpedientesOT datosOT = null;
     OCAPSolicitudesOT solicitudesOT = null;
     String nombreSubReport = null;
     String nombreReportPadre = null;
     String textoInfMotivadoDenegado ="";
     Properties configuracion = OCAPDirectorio.getProps();
     try
     {
       OCAPReportExpedientesOAD reportMCPorExpOAD = OCAPReportExpedientesOAD.getInstance();
       listadoDatosUsuariosPorExpediente = reportMCPorExpOAD.buscarDatosUsuariosPorExpediente(datosBusqueda);
 
       if ((10 == cEstadoId) || (14 == cEstadoId)) {
    	 textoInfMotivadoDenegado =  configuracion.getProperty("textoInfMotivadoDenegado");
         nombreReportPadre = "informeMCmotivadoDenegado";
       }else {
         nombreReportPadre = "informeMCmotivadoAceptado";
       }
 
       listadoInformes = new ArrayList();
       usuariosLN = new OCAPUsuariosLN(jcylUsuario);
       solicitudesLN = new OCAPSolicitudesLN(jcylUsuario);
       creditosLN = new OCAPCreditosLN(jcylUsuario);
       defMCLN = new OCAPDefMeritoscurricularesLN(jcylUsuario);
       ConceptoReport datosReport = new ConceptoReport();
 
       for (int i = 0; i < listadoDatosUsuariosPorExpediente.size(); i++) {
         ReportOT reportOT = new ReportOT(rutaRaiz, File.separator, nombreReportPadre);
         Hashtable parametros = new Hashtable();
 
         datosOT = (OCAPReportExpedientesOT)listadoDatosUsuariosPorExpediente.get(i);
         solicitudesOT = new OCAPSolicitudesOT();
         solicitudesOT.setCExp_id(datosOT.getCExpedienteId());
         solicitudesOT.setCUsr_id(datosOT.getCUsuarioId());
         solicitudesOT.setCGrado_id(datosOT.getCGradoId());
         solicitudesOT.setDGrado_des(datosOT.getDGrado());
         solicitudesOT.setDGerencia_nombre(datosOT.getDGerencia());
         solicitudesOT.setDGerente(datosOT.getDGerente());
         solicitudesOT.setBSexo(datosOT.getBSexo());
         solicitudesOT.setDNombre(datosOT.getDNombre());
         solicitudesOT.setDApellido1(datosOT.getDApellidos());
         solicitudesOT.setCDni(datosOT.getCDNI());
         solicitudesOT.setCDniReal(datosOT.getCDNIReal());
         solicitudesOT.setDProfesional_nombre(datosOT.getDCategProfesional());
         solicitudesOT.setCProfesional_id(datosOT.getCCategProfesionalId());
         solicitudesOT.setDSitAdministrativaAux_nombre(datosOT.getDSitAdministrativa());
         solicitudesOT.setDProvincia(datosOT.getDProvinciaGerencia());
         solicitudesOT.setCConvocatoriaId(datosOT.getCConvocatoriaId());
 
         solicitudesOT.setDMotivo_neg(datosOT.getDMotivoNegMC());
 
         usuariosOT = usuariosLN.datosPersonalesUsuario(solicitudesOT.getCDni(), solicitudesOT.getCExp_id(), jcylUsuario);
 
         ArrayList listaCreditos = creditosLN.buscarOCAPCreditosPorCategProfesional(usuariosOT, new Integer((int)usuariosOT.getCGrado_id()), "", jcylUsuario);
 
         listaCreditos.remove(0);
         solicitudesOT.setListaCreditos(listaCreditos);
 
         ArrayList listaCreditosNoAlcanzados = new ArrayList();
         OCAPCreditosOT creditosOT = null;
         for (int k = 0; k < listaCreditos.size(); k++) {
           creditosOT = (OCAPCreditosOT)listaCreditos.get(k);
           if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
             if (creditosOT.getNCreditosValidadosCeis().doubleValue() < creditosOT.getNCreditos())
               listaCreditosNoAlcanzados.add(creditosOT);
           }
           else if (creditosOT.getNCreditosValidadosCc().doubleValue() < creditosOT.getNCreditos()) {
             listaCreditosNoAlcanzados.add(creditosOT);
           }
         }
         String cadenaCreditos = null;
         if (listaCreditosNoAlcanzados.size() > 1) {
           cadenaCreditos = "las áreas de ";
         }
         for (int k = 0; k < listaCreditosNoAlcanzados.size() - 2; k++) {
           creditosOT = (OCAPCreditosOT)listaCreditosNoAlcanzados.get(k);
           cadenaCreditos = cadenaCreditos + "<style pdfFontName=\"Helvetica-Bold\">" + creditosOT.getDMerito() + "</style>, ";
         }
         if (listaCreditosNoAlcanzados.size() > 1) {
           creditosOT = (OCAPCreditosOT)listaCreditosNoAlcanzados.get(listaCreditosNoAlcanzados.size() - 2);
           OCAPCreditosOT creditosOT2 = (OCAPCreditosOT)listaCreditosNoAlcanzados.get(listaCreditosNoAlcanzados.size() - 1);
           if (creditosOT2.getDMerito().toUpperCase().startsWith("I"))
             cadenaCreditos = cadenaCreditos + "<style pdfFontName=\"Helvetica-Bold\">" + creditosOT.getDMerito() + "</style> e <style pdfFontName=\"Helvetica-Bold\">" + creditosOT2.getDMerito() + "</style>";
           else
             cadenaCreditos = cadenaCreditos + "<style pdfFontName=\"Helvetica-Bold\">" + creditosOT.getDMerito() + "</style> y <style pdfFontName=\"Helvetica-Bold\">" + creditosOT2.getDMerito() + "</style>";
         } else if (listaCreditosNoAlcanzados.size() == 1) {
           creditosOT = (OCAPCreditosOT)listaCreditosNoAlcanzados.get(0);
           cadenaCreditos = "el área de <style pdfFontName=\"Helvetica-Bold\">" + creditosOT.getDMerito() + "</style>";
         } else {
           cadenaCreditos = "el área de ..........";
         }parametros.put("areasNoSuperadas", cadenaCreditos);
 
         nombreSubReport = rutaRaiz + File.separator + "reports" + File.separator + "compilados" + File.separator + "subinformeListaDefMeritos" + ".jasper";
         File fichSubreport = new File(nombreSubReport);
         jasperSubReport = (JasperReport)JRLoader.loadObject(fichSubreport);
         parametros.put("SUB_REPORT", jasperSubReport);
 
         matrizDatosReport = new ConceptoReport();
         ArrayList listaDefMeritos = defMCLN.listarDefmeritosPorCatProfesional(solicitudesOT.getCGrado_id(), solicitudesOT.getCProfesional_id());
         matrizDatosReport = solicitudesLN.generarTablaDefMeritos(listaDefMeritos);
         parametros.put("datosReport", matrizDatosReport);
         parametros.put("textoInfMotivadoDenegado", textoInfMotivadoDenegado);
         Date hoy = new Date();
         String fecha = df.format(hoy);
         SimpleDateFormat dfForma = new SimpleDateFormat(Constantes.FECHA_LETRA);
         fechaForm = dfForma.format(hoy);
 
         File fichReportPadre = new File(nombreReportPadre);
 
         parametros.put("ruta", rutaRaiz);
 
         parametros.put("fecha", DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA, DateUtil.convertStringToDate(fecha)));
         parametros.put("fechaForm", fechaForm);
 
         parametros.put("datosDocu", solicitudesOT);
 
         convoLN = new OCAPConvocatoriasLN(jcylUsuario);
         convoOT = convoLN.buscarOCAPConvocatorias(solicitudesOT.getCConvocatoriaId());
         parametros.put("fechaConvocatoria", DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA, DateUtil.convertStringToDate(convoOT.getFResolucion())));
 
         if (solicitudesOT.getBSexo().equals(Constantes.SEXO_HOMBRE_VALUE))
           parametros.put("sexo", "D.");
         else {
           parametros.put("sexo", "Dña.");
         }
 
         reportOT.setParametrosReport(parametros);
         reportOT.setDatosReport(datosReport);
 
         listadoInformes.add(reportOT);
       }
 
       liberarObjetos(listadoDatosUsuariosPorExpediente);
     }
     catch (SQLException exSQL)
     {
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       this.logger.error(e);
       throw e;
     }
 
     return listadoInformes;
   }
 }


 
 package es.jcyl.fqs.ocap.ln.solicitudes;

import es.jcyl.cf.seguridad.util.Usuario;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.estados.OCAPEstadosLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.expedientesexclusion.OCAPExpedientesExclusionLN;
import es.jcyl.fqs.ocap.ln.otrosCentros.OCAPOtrosCentrosLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.oad.expedienteCarrera.OCAPExpedientecarreraOAD;
import es.jcyl.fqs.ocap.oad.solicitudes.OCAPNuevaSolicitudOAD;
import es.jcyl.fqs.ocap.ot.estados.OCAPEstadosOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedientesexclusion.OCAPExpedientesExclusionOT;
import es.jcyl.fqs.ocap.ot.otrosCentros.OCAPOtrosCentrosOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;
 
 public class OCAPNuevaSolicitudLN
 {
   Logger logger;
   private JCYLUsuario jcylUsuario;
 
   private void $init$()
   {
     this.logger = OCAPConfigApp.logger;
   }
   public OCAPNuevaSolicitudLN(JCYLUsuario jcylUsuario) { $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public ArrayList listaSituaciones()
     throws Exception
   {
     ArrayList listado = new ArrayList();
     listado.add(new LabelValueBean("Activo", "1"));
     listado.add(new LabelValueBean("Servicios Especiales", "2"));
     listado.add(new LabelValueBean("Excedencia por prestar servicios en sector público", "3"));
     listado.add(new LabelValueBean("Excedencia cuidado familiares", "4"));
     listado.add(new LabelValueBean("Otras", "5"));
 
     return listado;
   }
 
   public ArrayList listaVinculos()
     throws Exception
   {
     ArrayList listado = new ArrayList();
     listado.add(new LabelValueBean("Estatutario Propietario", "EP"));
     listado.add(new LabelValueBean("Estatutario Temporal", "ET"));
     listado.add(new LabelValueBean("Funcionario de Carrera", "FC"));
     listado.add(new LabelValueBean("Funcionario Temporal", "FT"));
     listado.add(new LabelValueBean("Laboral Fijo", "LF"));
     listado.add(new LabelValueBean("Laboral Temporal", "LT"));
     listado.add(new LabelValueBean("Interino funcionario sanitario", "IF"));
     listado.add(new LabelValueBean("Interino estatutario", "IE"));
 
     return listado;
   }
 
   public int insertar(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     int idExpediente = 0;
     int idSolicitud = 0;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " insertar: Inicio");
 
       con = JCYLGestionTransacciones.getConnection();
 
       con.setAutoCommit(false);
 
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       idSolicitud = solicitudesOAD.altaOCAPSolicitudes(solicitudesOT, con);
 
       ArrayList cadenasCentros = new ArrayList();
       if ((solicitudesOT.getResumenCentros() != null) && (!"".equals(solicitudesOT.getResumenCentros()))) {
         cadenasCentros = Cadenas.obtenerArrayListTokens(solicitudesOT.getResumenCentros(), "#");
       }
       OCAPOtrosCentrosOT otrosCentrosOT = null;
       ArrayList listaOtrosCentros = new ArrayList();
 
       for (int i = 0; i < cadenasCentros.size(); i++) {
         ArrayList campos = new ArrayList();
         String cadena = (String)cadenasCentros.get(i);
         StringTokenizer token = new StringTokenizer(cadena, "$");
         while (token.hasMoreTokens()) {
           campos.add(token.nextToken());
         }
         otrosCentrosOT = new OCAPOtrosCentrosOT();
         otrosCentrosOT.setDNombre((String)campos.get(0));
         otrosCentrosOT.setAProvincia((String)campos.get(1));
         otrosCentrosOT.setACategoria((String)campos.get(2));
         otrosCentrosOT.setAVinculo((String)campos.get(3));
         otrosCentrosOT.setFInicio(DateUtil.convertStringToDate((String)campos.get(4)));
         otrosCentrosOT.setFFin(DateUtil.convertStringToDate((String)campos.get(5)));
         otrosCentrosOT.setCSolicitud_id(idSolicitud);
         listaOtrosCentros.add(otrosCentrosOT);
       }
       OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
       otrosCentrosLN.removeAllCentros(solicitudesOT.getCSolicitudId(), solicitudesOT.getCExp_id());
       otrosCentrosLN.altaOtrosCentros(idExpediente, listaOtrosCentros, jcylUsuario);
 
       con.commit();
 
       OCAPConfigApp.logger.info(getClass().getName() + " insertar: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " insertar: ERROR: " + e.getMessage());
       con.rollback();
       throw e;
     }
     finally
     {
       con.setAutoCommit(true);
       JCYLGestionTransacciones.close(con.getAutoCommit());
     }
 
     return idSolicitud;
   }
   
	/**
	 * 
	 * @param solicitudesOT
	 * @param jcylUsuario
	 * @throws Exception
	 */
	public void actualizar(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {

		Connection con = null;

		try {
			OCAPConfigApp.logger.info(getClass().getName() + " actualizar: Inicio");

			con = JCYLGestionTransacciones.getConnection();

			con.setAutoCommit(false);

			OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
			solicitudesOAD.actualizar(solicitudesOT, con);

			con.commit();

			OCAPConfigApp.logger.info(getClass().getName() + " actualizar: Fin");
			
			
			ArrayList cadenasCentros = new ArrayList();
			if ((solicitudesOT.getResumenCentros() != null) && (!"".equals(solicitudesOT.getResumenCentros()))) {
				cadenasCentros = Cadenas.obtenerArrayListTokens(solicitudesOT.getResumenCentros(), "#");
			}
			OCAPOtrosCentrosOT otrosCentrosOT = null;
			ArrayList listaOtrosCentros = new ArrayList();

			for (int i = 0; i < cadenasCentros.size(); i++) {
				ArrayList campos = new ArrayList();
				String cadena = (String) cadenasCentros.get(i);
				StringTokenizer token = new StringTokenizer(cadena, "$");
				while (token.hasMoreTokens()) {
					campos.add(token.nextToken());
				}
				otrosCentrosOT = new OCAPOtrosCentrosOT();
				
				if (campos.size() == 6) {
					otrosCentrosOT.setDNombre((String) campos.get(0));
					otrosCentrosOT.setAProvincia((String) campos.get(1));
					otrosCentrosOT.setACategoria((String) campos.get(2));
					otrosCentrosOT.setAVinculo((String) campos.get(3));
					otrosCentrosOT.setFInicio(DateUtil.convertStringToDate((String) campos.get(4)));
					otrosCentrosOT.setFFin(DateUtil.convertStringToDate((String) campos.get(5)));
				} else if (campos.size() > 6) {
					otrosCentrosOT.setDNombre((String) campos.get(0));
					otrosCentrosOT.setAProvincia((String) campos.get(1));
					otrosCentrosOT.setACategoria((String) campos.get(2));
					if(campos.get(4)!=null){
						otrosCentrosOT.setAVinculo((String) campos.get(4));
					}
					otrosCentrosOT.setFInicio(DateUtil.convertStringToDate((String) campos.get(5)));
					otrosCentrosOT.setFFin(DateUtil.convertStringToDate((String) campos.get(6)));
				}
				otrosCentrosOT.setCSolicitud_id(solicitudesOT.getCSolicitudId());
				listaOtrosCentros.add(otrosCentrosOT);
			}
			OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
			otrosCentrosLN.removeAllCentros(solicitudesOT.getCSolicitudId(), solicitudesOT.getCExp_id());
			otrosCentrosLN.altaOtrosCentros(0, listaOtrosCentros, jcylUsuario);

			con.commit();
		       
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " actualizar: ERROR: " + e.getMessage());
			con.rollback();
			throw e;
		} finally {
			con.setAutoCommit(true);
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}
	}
 
   public ArrayList buscarSolicitudes(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     ArrayList solicitudesTotal = null;
     ArrayList listaEstados = null;
     OCAPSolicitudesOT lista = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesDNI: Inicio");
 
       OCAPEstadosLN estadosLN = new OCAPEstadosLN(jcylUsuario);
       OCAPEstadosOT estadoOT = null;
 
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       solicitudesTotal = solicitudesOAD.buscarSolicitudes(inicio, cuantos, solicitudesOT, jcylUsuario);
       listaEstados = new ArrayList();
 
       for (int i = 0; i < solicitudesTotal.size(); i++) {
         lista = (OCAPSolicitudesOT)solicitudesTotal.get(i);
         if ((lista.getCEstado_id() == 1L) || (lista.getCEstado_id() == 8))
           estadoOT = estadosLN.buscarEstados(lista.getCEstado_id());
         else
           estadoOT = estadosLN.buscarEstados(lista.getCEstadoId());
         lista.setCEstado(estadoOT.getDNombre());
         listaEstados.add(lista);
       }
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesDNI: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesDNI: ERROR: " + e.getMessage());
       throw e;
     }
 
     return listaEstados;
   }
 
   public int contarSolicitudes(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     int contador = 0;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Inicio");
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       contador = solicitudesOAD.contarSolicitudes(solicitudesOT, jcylUsuario);
       OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: ERROR: " + e.getMessage());
       throw e;
     }
 
     return contador;
   }
 
   public OCAPSolicitudesOT detalleSolicitud(long cSolicitudId)
     throws Exception
   {
     OCAPSolicitudesOT solicitudOT = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitud: Inicio");
 
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       solicitudOT = solicitudesOAD.detalleSolicitud(cSolicitudId);
 
       OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(this.jcylUsuario);
       ArrayList listaOtrosCentros = otrosCentrosLN.buscarOCAPOtrosCentrosSolic(cSolicitudId);
       solicitudOT.setListaOtrosCentros(listaOtrosCentros);
       OCAPOtrosCentrosOT otrosCentrosOT = null;
       String cadenaCentros = "";
       for (int i = 0; i < listaOtrosCentros.size(); i++) {
         otrosCentrosOT = (OCAPOtrosCentrosOT)listaOtrosCentros.get(i);
         cadenaCentros = cadenaCentros + otrosCentrosOT.getDNombre() + "$" + otrosCentrosOT.getAProvincia() + "$" + otrosCentrosOT.getACategoria() + "$" + otrosCentrosOT.getASituacion() + "$" + otrosCentrosOT.getAVinculo() + "$" + DateUtil.convertDateToString(otrosCentrosOT.getFInicio()) + "$" + DateUtil.convertDateToString(otrosCentrosOT.getFFin()) + "#";
       }
 
       if (!"".equals(cadenaCentros)) {
         solicitudOT.setResumenCentros(cadenaCentros.substring(0, cadenaCentros.length() - 1));
       }
       OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitud: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " detalleSolicitud: ERROR: " + e.toString());
       throw e;
     }
 
     return solicitudOT;
   }
 
   public void modificarSolicitud(OCAPSolicitudesOT solicitudesOT)
     throws Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " modificarSolicitud: Inicio");
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       solicitudesOAD.modificarSolicitud(solicitudesOT);
       OCAPConfigApp.logger.info(getClass().getName() + " modificarSolicitud: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " modificarSolicitud: ERROR: " + e.getMessage());
       throw e;
     }
   }
 
   public void cambiarEstados(OCAPSolicitudesOT solicitudesOT)
     throws Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " cambiarEstados: Inicio");
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       solicitudesOAD.cambiarEstados(solicitudesOT);
       OCAPConfigApp.logger.info(getClass().getName() + " cambiarEstados: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " cambiarEstados: ERROR: " + e.getMessage());
       throw e;
     }
   }
 
   public long buscarExpediente(OCAPSolicitudesOT solicitudesOT)
     throws Exception
   {
     long cExpId = 0L;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarExpediente: Inicio");
       OCAPExpedientecarreraOAD expedientesOAD = OCAPExpedientecarreraOAD.getInstance();
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarExpediente: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarExpediente: ERROR: " + e.getMessage());
       throw e;
     }
 
     return cExpId;
   }
 
   public void crearInformeDetalle(HttpServletResponse response, OCAPSolicitudesOT solicitudesOT, String pathBase)
     throws Exception
   {
     ArrayList codigosMut = null;
     String nombreReportPadre = null;
     Hashtable parametros = null;
     JasperReport jasperReport = null;
     JasperPrint jasperPrint = null;
     String sexo = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: Inicio");
 
       nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator + "informeDetalle" + ".jasper";
 
       OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: Cargar report padre");
       File fichReportPadre = new File(nombreReportPadre);
       jasperReport = (JasperReport)JRLoader.loadObject(fichReportPadre);
 
       parametros = new Hashtable();
       parametros.put("ruta", pathBase);
 
       if (solicitudesOT.getBSexo().equals(Constantes.SEXO_HOMBRE_VALUE))
         sexo = Constantes.SEXO_HOMBRE;
       else {
         sexo = Constantes.SEXO_MUJER;
       }
 
       parametros.put("sexo", sexo);
       parametros.put("anio", Long.toString(solicitudesOT.getNAniosantiguedad()));
       parametros.put("fecha", DateUtil.convertDateToString(solicitudesOT.getFPlazapropiedad() == null ? new Date() : solicitudesOT.getFPlazapropiedad()));
 
       parametros.put("datosDocu", solicitudesOT);
 
       jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
 
       byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
 
       OutputStream out = response.getOutputStream();
       response.setHeader("Content-disposition", "attachment; filename=\"informeDetalle" + solicitudesOT.getCExp_id() + ".pdf\"");
       response.setContentType("application/pdf");
       response.setContentLength(bytes.length);
       out.write(bytes);
       out.flush();
       out.close();
 
       OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " crearInformeDetalle: ERROR" + e.getMessage());
       throw new Exception("Informe NO generado");
     }
   }
 
   public void asignarUsuarioExiste(OCAPUsuariosOT usuariosOT, OCAPExpedientecarreraOT expedienteCarreraOT, OCAPSolicitudesOT solicitudOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuarioExiste: Inicio");
       JCYLGestionTransacciones.open(false);
 
       OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
       OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
       OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
 
       usuarioLN.modificacionOCAPUsuarios(usuariosOT);
       expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
       expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, true);
 
       OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
       otrosCentrosLN.modificarOtrosCentros(expedienteCarreraOT.getCExpId().intValue(), expedienteCarreraOT.getCSolicitudId(), expedienteCarreraOT.getAModificadoPor(), null);
 
       OCAPExpedientesExclusionLN expedientesLN = new OCAPExpedientesExclusionLN(jcylUsuario);
       OCAPExpedientesExclusionOT expedientesExclusionOT = new OCAPExpedientesExclusionOT();
 
       expedientesExclusionOT.setCUsumodi(expedienteCarreraOT.getAModificadoPor());
       expedientesExclusionOT.setCExpId(expedienteCarreraOT.getCExpId().longValue());
       expedientesLN.bajaExpediente(expedientesExclusionOT);
 
       solicitudesLN.modificarSolicitud(solicitudOT);
 
       solicitudOT.setCEstado_id(8);
       solicitudesLN.cambiarEstados(solicitudOT);
 
       JCYLGestionTransacciones.commit(true);
       OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuarioExiste: Fin");
     }
     catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuarioExiste: ERROR: " + e.getMessage());
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
   }
 
   public void asignarUsuarioNuevo(OCAPUsuariosOT usuariosOT, OCAPExpedientecarreraOT expedienteCarreraOT, OCAPSolicitudesOT solicitudOT, JCYLUsuario jcylUsuario)
     throws Exception
   {
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuarioNuevo: Inicio");
       JCYLGestionTransacciones.open(false);
 
       OCAPUsuariosLN usuarioLN = new OCAPUsuariosLN(jcylUsuario);
       usuarioLN.modificacionOCAPUsuarios(usuariosOT);
 
       OCAPExpedienteCarreraLN expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
       OCAPNuevaSolicitudLN solicitudesLN = new OCAPNuevaSolicitudLN(jcylUsuario);
       int cExpId = expedienteCarreraLN.insertarExpedienteCarrera_a(expedienteCarreraOT);
       expedienteCarreraOT.setCExpId(new Long(cExpId));
 
       OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
       otrosCentrosLN.modificarOtrosCentros(expedienteCarreraOT.getCExpId().intValue(), expedienteCarreraOT.getCSolicitudId(), expedienteCarreraOT.getACreadoPor(), null);
 
       solicitudesLN.modificarSolicitud(solicitudOT);
 
       JCYLGestionTransacciones.commit(true);
       OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuarioNuevo: Fin");
     }
     catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.info(getClass().getName() + " asignarUsuarioNuevo: ERROR: " + e.getMessage());
       throw e;
     } finally {
       JCYLGestionTransacciones.close(true);
     }
   }
 
   public String reasignarUsuario(OCAPExpedientecarreraOT expCarreraOT, String cUsrIdanterior, JCYLUsuario jcylUsuario)
     throws Exception
   {
     OCAPExpedientecarreraOT expedienteCarreraOT = null;
     OCAPExpedienteCarreraLN expedienteCarreraLN = null;
     OCAPExpedientesExclusionLN expedientesExclusionLN = null;
     OCAPSolicitudesOT solicitudOT = null;
     OCAPUsuariosLN usuariosLN = null;
     OCAPUsuariosOT usuariosOT = null;
     String mensaje = null;
     long idConvocatoria = 0L;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " reasignarUsuario: Inicio");
 
       JCYLGestionTransacciones.open(false);
 
       expedienteCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
 
       expedienteCarreraOT = new OCAPExpedientecarreraOT();
       expedienteCarreraOT.setCUsrId(expCarreraOT.getCUsrId());
 
       expedienteCarreraOT.setCConvocatoriaId(expCarreraOT.getCConvocatoriaId());
       idConvocatoria = expCarreraOT.getCConvocatoriaId();
       expedienteCarreraOT = expedienteCarreraLN.buscarExpediente(expedienteCarreraOT);
 
       if ((expedienteCarreraOT != null) && (expedienteCarreraOT.getCExpId() != null) && (expedienteCarreraOT.getCExpId().longValue() != 0L))
       {
         if (expedienteCarreraOT.getFRegistroOficial().after(expCarreraOT.getFRegistroOficial()))
         {
           expedientesExclusionLN = new OCAPExpedientesExclusionLN(jcylUsuario);
           expedientesExclusionLN.borraExpediente(expCarreraOT.getCExpId().longValue());
 
           OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
           otrosCentrosLN.desasociarOtrosCentrosExpediente(expCarreraOT.getCExpId().longValue(), expCarreraOT.getAModificadoPor(), null);
 
           expedienteCarreraLN.bajaOCAPExpedientecarrera(expCarreraOT.getCExpId());
 
           solicitudOT = new OCAPSolicitudesOT();
           solicitudOT.setCUsr_id(expCarreraOT.getCUsrId().longValue());
           solicitudOT.setCSolicitudId(expCarreraOT.getCSolicitudId());
           solicitudOT.setCEstado_id(8);
           solicitudOT.setAModificadoPor(expCarreraOT.getAModificadoPor());
           modificarSolicitud(solicitudOT);
         }
         else
         {
           expedientesExclusionLN = new OCAPExpedientesExclusionLN(jcylUsuario);
           expedientesExclusionLN.borraExpediente(expedienteCarreraOT.getCExpId().longValue());
 
           OCAPOtrosCentrosLN otrosCentrosLN = new OCAPOtrosCentrosLN(jcylUsuario);
           otrosCentrosLN.desasociarOtrosCentrosExpediente(expedienteCarreraOT.getCExpId().longValue(), expCarreraOT.getAModificadoPor(), null);
 
           expedienteCarreraLN.bajaOCAPExpedientecarrera(expedienteCarreraOT.getCExpId());
 
           solicitudOT = new OCAPSolicitudesOT();
           solicitudOT.setCSolicitudId(expedienteCarreraOT.getCSolicitudId());
           solicitudOT.setCEstado_id(8);
           solicitudOT.setAModificadoPor(expCarreraOT.getAModificadoPor());
           modificarSolicitud(solicitudOT);
 
           expedienteCarreraOT = new OCAPExpedientecarreraOT();
           expedienteCarreraOT.setCUsrId(expCarreraOT.getCUsrId());
           expedienteCarreraOT.setCExpId(expCarreraOT.getCExpId());
           expedienteCarreraOT.setAModificadoPor(this.jcylUsuario.getUsuario().getC_usr_id());
           expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
 
           solicitudOT = new OCAPSolicitudesOT();
           solicitudOT.setCSolicitudId(expCarreraOT.getCSolicitudId());
           solicitudOT.setCUsr_id(expCarreraOT.getCUsrId().longValue());
           solicitudOT.setAModificadoPor(expCarreraOT.getAModificadoPor());
           modificarSolicitud(solicitudOT);
         }
 
       }
       else
       {
         expedienteCarreraOT.setCUsrId(expCarreraOT.getCUsrId());
         expedienteCarreraOT.setCExpId(expCarreraOT.getCExpId());
         expedienteCarreraLN.modificacionOCAPExpedientecarrera(expedienteCarreraOT, false);
 
         solicitudOT = new OCAPSolicitudesOT();
         solicitudOT.setCSolicitudId(expCarreraOT.getCSolicitudId());
         solicitudOT.setCUsr_id(expCarreraOT.getCUsrId().longValue());
         solicitudOT.setAModificadoPor(expCarreraOT.getAModificadoPor());
         modificarSolicitud(solicitudOT);
       }
 
       expedienteCarreraOT = expedienteCarreraLN.buscarOCAPExpedientecarreraPorUsuario(Long.valueOf(cUsrIdanterior));
       if ((expedienteCarreraOT == null) || (expedienteCarreraOT.getCExpId() == null) || (expedienteCarreraOT.getCExpId().longValue() == 0L))
       {
         solicitudOT = buscarUltimaSolicitudAnuladaUsuario(Long.valueOf(cUsrIdanterior), idConvocatoria);
 
         if ((solicitudOT == null) || (solicitudOT.getCSolicitudId() == 0L)) {
           usuariosLN = new OCAPUsuariosLN(jcylUsuario);
           usuariosLN.bajaOCAPUsuarios(Long.valueOf(cUsrIdanterior));
           mensaje = "El usuario reasociado no tiene más solicitudes. Se ha eliminado del sistema. ";
         }
         else
         {
           expedienteCarreraOT.setCUsrId(Long.valueOf(solicitudOT.getCUsr_id()));
           expedienteCarreraOT.setCGradoId(Integer.valueOf(String.valueOf(solicitudOT.getCGrado_id())));
           expedienteCarreraOT.setCConvocatoriaId(solicitudOT.getCConvocatoriaId());
           expedienteCarreraOT.setCProcedimientoId(solicitudOT.getCProcedimientoId());
           expedienteCarreraOT.setCEstatutId(solicitudOT.getCEstatutId());
           expedienteCarreraOT.setNAniosAntiguedad(solicitudOT.getNAniosantiguedad());
           expedienteCarreraOT.setNMesesAntiguedad(solicitudOT.getNMesesantiguedad());
           expedienteCarreraOT.setNDiasAntiguedad(solicitudOT.getNDiasantiguedad());
           expedienteCarreraOT.setFRegistroSolic(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFRegistro_solic()));
           expedienteCarreraOT.setCSitAdministrativaAuxId(solicitudOT.getCSitAdministrativaAuxId());
           expedienteCarreraOT.setDSitAdministrativaAuxOtros(solicitudOT.getDSitAdministrativaOtros());
           expedienteCarreraOT.setCJuridico(solicitudOT.getCJuridicoId());
           expedienteCarreraOT.setFRegistroOficial(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFRegistro_oficial()));
           expedienteCarreraOT.setACreadoPor(jcylUsuario.getUser().getC_usr_id());
           expedienteCarreraOT.setBOtroServicio(solicitudOT.getBOtroServicio());
           expedienteCarreraOT.setADondeServicio(solicitudOT.getADondeServicio());
           expedienteCarreraOT.setBBorrado(Constantes.NO);
           expedienteCarreraOT.setAServicio(solicitudOT.getAServicio());
           expedienteCarreraOT.setAPuesto(solicitudOT.getAPuesto());
           expedienteCarreraOT.setCSolicitudId(solicitudOT.getCSolicitudId());
           expedienteCarreraOT.setCProfesionalId(solicitudOT.getCProfesional_id());
           expedienteCarreraOT.setCEspecId(solicitudOT.getCEspec_id());
           expedienteCarreraOT.setDRegimenJuridicoOtros(solicitudOT.getDRegimenJuridicoOtros());
           expedienteCarreraOT.setCModAnteriorId(solicitudOT.getCModAnterior_id());
           expedienteCarreraOT.setCEstadoId(2);
 
           solicitudOT.setCEstado_id(2);
 
           usuariosOT = new OCAPUsuariosOT();
           usuariosOT.setCUsrId(new Long(solicitudOT.getCUsr_id()));
           usuariosOT.setCCentrotrabajoId(Integer.valueOf(String.valueOf(solicitudOT.getCCentrotrabajo_id())));
           usuariosOT.setCGerenciaId(Integer.valueOf(String.valueOf(solicitudOT.getCGerencia_id())));
           usuariosOT.setDNombre(solicitudOT.getDNombre());
           usuariosOT.setDApellido1(solicitudOT.getDApellido1());
           usuariosOT.setCDniReal(solicitudOT.getCDniReal());
           usuariosOT.setBSexo(solicitudOT.getBSexo());
           usuariosOT.setDCorreoelectronico(solicitudOT.getDCorreoelectronico());
           usuariosOT.setDDomicilio(solicitudOT.getDDomicilio());
           usuariosOT.setCProvinciaUsu_id(solicitudOT.getCProvincia_id());
           usuariosOT.setDLocalidadUsu(solicitudOT.getDLocalidad());
           usuariosOT.setCPostalUsu(solicitudOT.getCPostalUsu());
           usuariosOT.setNTelefono1(Integer.valueOf(solicitudOT.getNTelefono1() == null ? "0" : solicitudOT.getNTelefono1()));
           usuariosOT.setNTelefono2(Integer.valueOf(solicitudOT.getNTelefono2() == null ? "0" : solicitudOT.getNTelefono2()));
           usuariosOT.setBBorrado(Constantes.NO);
           usuariosOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
 
           asignarUsuarioNuevo(usuariosOT, expedienteCarreraOT, solicitudOT, jcylUsuario);
 
           mensaje = "El usuario reasociado tiene otras solicitudes. No se elimina del sistema. ";
         }
       }
       else
       {
         solicitudOT = buscarUltimaSolicitudAnuladaUsuario(Long.valueOf(cUsrIdanterior), idConvocatoria);
 
         if ((solicitudOT != null) && (solicitudOT.getCSolicitudId() != 0L)) {
           expedienteCarreraOT.setCUsrId(Long.valueOf(solicitudOT.getCUsr_id()));
           expedienteCarreraOT.setCGradoId(Integer.valueOf(String.valueOf(solicitudOT.getCGrado_id())));
           expedienteCarreraOT.setCConvocatoriaId(solicitudOT.getCConvocatoriaId());
           expedienteCarreraOT.setCProcedimientoId(solicitudOT.getCProcedimientoId());
           expedienteCarreraOT.setCEstatutId(solicitudOT.getCEstatutId());
           expedienteCarreraOT.setNAniosAntiguedad(solicitudOT.getNAniosantiguedad());
           expedienteCarreraOT.setNMesesAntiguedad(solicitudOT.getNMesesantiguedad());
           expedienteCarreraOT.setNDiasAntiguedad(solicitudOT.getNDiasantiguedad());
           expedienteCarreraOT.setFRegistroSolic(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFRegistro_solic()));
           expedienteCarreraOT.setCSitAdministrativaAuxId(solicitudOT.getCSitAdministrativaAuxId());
           expedienteCarreraOT.setDSitAdministrativaAuxOtros(solicitudOT.getDSitAdministrativaOtros());
           expedienteCarreraOT.setCJuridico(solicitudOT.getCJuridicoId());
           expedienteCarreraOT.setFRegistroOficial(DateUtil.convertStringToDate(Constantes.FECHA_COMPLETA_DATEUTIL, solicitudOT.getFRegistro_oficial()));
           expedienteCarreraOT.setACreadoPor(jcylUsuario.getUser().getC_usr_id());
           expedienteCarreraOT.setBOtroServicio(solicitudOT.getBOtroServicio());
           expedienteCarreraOT.setADondeServicio(solicitudOT.getADondeServicio());
           expedienteCarreraOT.setBBorrado(Constantes.NO);
           expedienteCarreraOT.setAServicio(solicitudOT.getAServicio());
           expedienteCarreraOT.setAPuesto(solicitudOT.getAPuesto());
           expedienteCarreraOT.setCSolicitudId(solicitudOT.getCSolicitudId());
           expedienteCarreraOT.setCProfesionalId(solicitudOT.getCProfesional_id());
           expedienteCarreraOT.setCEspecId(solicitudOT.getCEspec_id());
           expedienteCarreraOT.setDRegimenJuridicoOtros(solicitudOT.getDRegimenJuridicoOtros());
           expedienteCarreraOT.setCModAnteriorId(solicitudOT.getCModAnterior_id());
           expedienteCarreraOT.setCEstadoId(2);
 
           solicitudOT.setCEstado_id(2);
 
           usuariosOT = new OCAPUsuariosOT();
           usuariosOT.setCUsrId(new Long(solicitudOT.getCUsr_id()));
           usuariosOT.setCCentrotrabajoId(Integer.valueOf(String.valueOf(solicitudOT.getCCentrotrabajo_id())));
           usuariosOT.setCGerenciaId(Integer.valueOf(String.valueOf(solicitudOT.getCGerencia_id())));
           usuariosOT.setDNombre(solicitudOT.getDNombre());
           usuariosOT.setDApellido1(solicitudOT.getDApellido1());
           usuariosOT.setCDniReal(solicitudOT.getCDniReal());
           usuariosOT.setBSexo(solicitudOT.getBSexo());
           usuariosOT.setDCorreoelectronico(solicitudOT.getDCorreoelectronico());
           usuariosOT.setDDomicilio(solicitudOT.getDDomicilio());
           usuariosOT.setCProvinciaUsu_id(solicitudOT.getCProvincia_id());
           usuariosOT.setDLocalidadUsu(solicitudOT.getDLocalidad());
           usuariosOT.setCPostalUsu(solicitudOT.getCPostalUsu());
           usuariosOT.setNTelefono1(Integer.valueOf(solicitudOT.getNTelefono1() == null ? "0" : solicitudOT.getNTelefono1()));
           usuariosOT.setNTelefono2(Integer.valueOf(solicitudOT.getNTelefono2() == null ? "0" : solicitudOT.getNTelefono2()));
           usuariosOT.setBBorrado(Constantes.NO);
           usuariosOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
 
           asignarUsuarioNuevo(usuariosOT, expedienteCarreraOT, solicitudOT, jcylUsuario);
           mensaje = "El usuario reasociado tiene otras solicitudes. No se elimina del sistema. ";
         } else {
           mensaje = "El usuario reasociado tiene expedientes en otras convocatorias. No se elimina del sistema. ";
         }
       }
 
       JCYLGestionTransacciones.commit();
 
       OCAPConfigApp.logger.info(getClass().getName() + " reasignarUsuario: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " reasignarUsuario: ERROR: " + e);
       JCYLGestionTransacciones.rollback();
       throw e;
     }
     finally
     {
       JCYLGestionTransacciones.close(true);
     }
 
     return mensaje;
   }
 
   public OCAPSolicitudesOT buscarUltimaSolicitudAnuladaUsuario(Long cUsrIdanterior, long cConvocatoriaId)
     throws Exception
   {
     OCAPNuevaSolicitudOAD solicitudesOAD = null;
     OCAPSolicitudesOT solicitudOT = null;
     try
     {
       solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       solicitudOT = solicitudesOAD.buscarUltimaSolicitudAnuladaUsuario(cUsrIdanterior, cConvocatoriaId);
       if ((solicitudOT != null) && (solicitudOT.getCSolicitudId() != 0L))
         solicitudOT = detalleSolicitud(solicitudOT.getCSolicitudId());
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarUltimaSolicitudAnuladaUsuario: ERROR: " + e.getMessage());
       throw e;
     }
 
     return solicitudOT;
   }
 
   public OCAPSolicitudesOT buscarSolicitud(long cSolicitudId)
     throws Exception
   {
     OCAPSolicitudesOT solicitudOT = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitud: Inicio");
       OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
       solicitudOT = solicitudesOAD.detalleSolicitud(cSolicitudId);
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitud: Fin");
     }
     catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitud: ERROR: " + e.toString());
       throw e;
     }

		return solicitudOT;
	}

	/**
	 * 
	 * @param cExpId
	 * @return
	 * @throws Exception
	 */
	public OCAPSolicitudesOT detalleSolicitudconIdExpdte(Long cExpId) throws Exception {
		if(cExpId != null){
			OCAPNuevaSolicitudOAD solicitudesOAD = OCAPNuevaSolicitudOAD.getInstance();
			long idSolicitud = solicitudesOAD.obtenerIdSolicitud(cExpId);
			if(idSolicitud > 0)
				return detalleSolicitud(idSolicitud);
		}
		return null;
	}
 }


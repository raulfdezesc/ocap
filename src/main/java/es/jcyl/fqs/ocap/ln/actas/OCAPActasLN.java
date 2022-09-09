package es.jcyl.fqs.ocap.ln.actas;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.grado.OCAPGradoLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.oad.actas.OCAPActasOAD;
import es.jcyl.fqs.ocap.oad.solicitudes.OCAPSolicitudesOAD;
import es.jcyl.fqs.ocap.oad.usuarios.OCAPUsuariosOAD;
import es.jcyl.fqs.ocap.ot.actas.OCAPActasOT;
import es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT;
import es.jcyl.fqs.ocap.ot.categProfesionales.OCAPCategProfesionalesOT;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.ot.grado.OCAPGradoOT;
import es.jcyl.fqs.ocap.ot.reports.OCAPAsistenteOT;
import es.jcyl.fqs.ocap.ot.reports.OCAPUsuarioOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.io.File;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

public class OCAPActasLN {
	public Logger logger;
	public Logger loggerBD;
	private JCYLUsuario jcylUsuario;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;

		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public OCAPActasLN(JCYLUsuario jcylUsuario) {
		$init$();
		this.jcylUsuario = jcylUsuario;
	}

	public long altaDatosActa(OCAPActasOT actaOT) throws SQLException, Exception {
		long resultado = 0L;
		try {
			this.logger.debug("Inicio");
			OCAPActasOAD actasOAD = OCAPActasOAD.getInstance();
			JCYLGestionTransacciones.open(false);

			resultado = actasOAD.altaActa(actaOT);

			actaOT.setCActaId(resultado);
			actasOAD.altaMiembrosActa(actaOT);

			if (!"C".equals(actaOT.getCTipoActa()))
				actasOAD.altaExpedientesActa(actaOT);
			JCYLGestionTransacciones.commit(true);
			this.logger.debug("Fin");
		} catch (SQLException exSQL) {
			JCYLGestionTransacciones.rollback();
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			JCYLGestionTransacciones.rollback();
			this.logger.error(e);
			throw e;
		} finally {
			JCYLGestionTransacciones.close(true);
		}

		return resultado;
	}

	public OCAPActasOT buscarDatosActa(long cActaId) throws SQLException, Exception {
		OCAPActasOT actaOT = null;
		ArrayList listaMiembros = null;
		ArrayList listadoVocales = null;
		ArrayList listadoVocalesSuplentes = null;
		OCAPMiembrosComitesOT miembroOT = null;
		OCAPAsistenteOT vocalOT = null;
		ArrayList listaExpedientes = null;
		ArrayList listadoUsuariosInfMotivado = null;
		ArrayList listadoUsuariosAclaraciones = null;
		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuarioOT = null;
		OCAPUsuarioOT usuOT = null;
		try {
			this.logger.debug("Inicio");
			OCAPActasOAD actasOAD = OCAPActasOAD.getInstance();

			actaOT = actasOAD.buscarDatosActa(cActaId);

			listaMiembros = buscarMiembrosActa(cActaId);
			listadoVocales = new ArrayList();
			listadoVocalesSuplentes = new ArrayList();
			for (int i = 0; i < listaMiembros.size(); i++) {
				miembroOT = (OCAPMiembrosComitesOT) listaMiembros.get(i);

				if ((Constantes.PRESIDENTE.equals(miembroOT.getCTipo())) && ("T".equals(miembroOT.getCEnCalidad()))) {
					actaOT.setDNombreApellidosPresi1(miembroOT.getDNombre() + " " + miembroOT.getDApellidos());
					actaOT.setBSexoPresi1(miembroOT.getCSexo());
					actaOT.setBEnCalidadPresi1(miembroOT.getCEnCalidad());
				}

				if ((Constantes.PRESIDENTE.equals(miembroOT.getCTipo()))
						&& (Constantes.SECRETARIO.equals(miembroOT.getCEnCalidad()))) {
					actaOT.setDNombreApellidosPresi2(miembroOT.getDNombre() + " " + miembroOT.getDApellidos());
					actaOT.setBSexoPresi2(miembroOT.getCSexo());
					actaOT.setBEnCalidadPresi2(miembroOT.getCEnCalidad());
				}

				if ((Constantes.SECRETARIO.equals(miembroOT.getCTipo())) && ("T".equals(miembroOT.getCEnCalidad()))) {
					actaOT.setDNombreApellidosSecre1(miembroOT.getDNombre() + " " + miembroOT.getDApellidos());
					actaOT.setBSexoSecre1(miembroOT.getCSexo());
					actaOT.setBEnCalidadSecre1(miembroOT.getCEnCalidad());
				}

				if ((Constantes.SECRETARIO.equals(miembroOT.getCTipo())) && (!"T".equals(miembroOT.getCEnCalidad()))) {
					actaOT.setDNombreApellidosSecre2(miembroOT.getDNombre() + " " + miembroOT.getDApellidos());
					actaOT.setBSexoSecre2(miembroOT.getCSexo());
					actaOT.setBEnCalidadSecre2(miembroOT.getCEnCalidad());
				}

				if (Constantes.VOCAL.equals(miembroOT.getCTipo())) {
					vocalOT = new OCAPAsistenteOT();
					vocalOT.setDNombreApellidos(miembroOT.getDNombre() + " " + miembroOT.getDApellidos());
					vocalOT.setBSexo(miembroOT.getCSexo());
					vocalOT.setBEnCalidad(miembroOT.getCEnCalidad());
					if ("T".equals(miembroOT.getCEnCalidad()))
						listadoVocales.add(vocalOT);
					else
						listadoVocalesSuplentes.add(vocalOT);
				}
			}
			actaOT.setListadoVocalesTitulares(listadoVocales);
			actaOT.setListadoVocalesSuplentes(listadoVocalesSuplentes);

			listaExpedientes = buscarExpedientesActa(cActaId);
			usuariosLN = new OCAPUsuariosLN(this.jcylUsuario);

			listadoUsuariosInfMotivado = new ArrayList();
			for (int i = 0; i < listaExpedientes.size(); i++) {
				usuOT = (OCAPUsuarioOT) listaExpedientes.get(i);
				usuarioOT = usuariosLN.buscarUsuarioPorExpId(usuOT.getCExpId());
				usuarioOT.setDDescripcion(usuOT.getDAccion());
				listadoUsuariosInfMotivado.add(usuarioOT);
			}
			actaOT.setListadoUsuariosInfMotivado(listadoUsuariosInfMotivado);

			this.logger.debug("Fin");
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			this.logger.error(e);
			throw e;
		}

		return actaOT;
	}

	public ArrayList buscarMiembrosActa(long cActaId) throws SQLException, Exception {
		ArrayList listado = null;
		try {
			this.logger.debug("Inicio");
			OCAPActasOAD actasOAD = OCAPActasOAD.getInstance();
			listado = actasOAD.buscarMiembrosActa(cActaId);
			this.logger.debug("Fin");
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			this.logger.error(e);
			throw e;
		}

		return listado;
	}

	public ArrayList buscarExpedientesActa(long cActaId) throws SQLException, Exception {
		ArrayList listado = null;
		try {
			this.logger.debug("Inicio");
			OCAPActasOAD actasOAD = OCAPActasOAD.getInstance();
			listado = actasOAD.buscarExpedientesActa(cActaId);
			this.logger.debug("Fin");
		} catch (SQLException exSQL) {
			this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			this.logger.error(e);
			throw e;
		}

		return listado;
	}

	public void crearActaConstitucion(JCYLUsuario jcylUsuario, HttpServletResponse response, OCAPActasOT actasOT,
			String pathBase) throws Exception {
		String nombreReportPadre = null;
		String nombreSubreportVocales = null;
		String nombreSubreportVocalesSuplentes = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperReport jasperSubReport = null;
		JasperReport jasperSubReportSuplentes = null;
		JasperPrint jasperPrint = null;
		ConceptoReport datosSubReportListadoVocales = null;
		ConceptoReport datosSubReportListadoVocalesSuplentes = null;
		OCAPGradoOT grado = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "actaConstitucion" + ".jasper";
			nombreSubreportVocales = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocales" + ".jasper";
			nombreSubreportVocalesSuplentes = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocalesSuplentes" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			File fichSubReportListadoVocales = new File(nombreSubreportVocales);
			jasperSubReport = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocales);

			File fichSubReportListadoVocalesSuplentes = new File(nombreSubreportVocalesSuplentes);
			jasperSubReportSuplentes = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocalesSuplentes);

			parametros.put("ruta", pathBase);

			grado = getGrado(jcylUsuario, actasOT);
			parametros.put("grado", grado);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoria = convocatoriasLN.buscarOCAPConvocatorias(actasOT.getCConvocatoriaId());
			parametros.put("convocatoria", convocatoria);
			
			
			//OCAP-637
			String perfilCompleto = jcylUsuario.getUser().getRol();
			perfilCompleto = perfilCompleto.replace("OCAP_", "");
			parametros.put("usuario", perfilCompleto);

			String cabecera = "";
			if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())){
				cabecera = "REUNIÓN DE LA COMISIÓN CENTRAL EVALUADORA DE LAS SOLICITUDES DE RECONOCIMIENTO DE GRADO FORMULADAS POR EL PERSONAL ESTATUTARIO FIJO QUE DESEMPEÑE PUESTOS DE CARÁCTER DIRECTIVO O PUESTOS EN LA ESTRUCTURA ADMINISTRATIVA O DE GESTIÓN DE LA GERENCIA REGIONAL DE SALUD CONVOCADO POR RESOLUCIÓN DE ###FECHA### DE LA GERENCIA REGIONAL DE SALUD";
			}
			else if (Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol())){
				cabecera = "REUNIÓN DEL COMITÉ ESPECÍFICO "
						+"DE INSTITUCIONES SANITARIAS DE LA ###GERENCIA### " 
						+"VALORADOR DE MÉRITOS PARA EL RECONOCIMIENTO DE ###GRADO### "  
						+"DE CARRERA PROFESIONAL DEL SERVICIO DE SALUD DE CASTILLA Y LEÓN " 
						+"CONVOCADO POR RESOLUCIÓN DE ###FECHA### DE LA GERENCIA REGIONAL DE SALUD";			}
			else{
				cabecera = "REUNIÓN DEL COMITÉ ESPECÍFICO DE INSTITUCIONES SANITARIAS VALORADOR DE LOS MÉRITOS CURRICULARES DE LA AUTOEVALUACIÓN PARA EL RECONOCIMIENTO INDIVIDUAL DE GRADO DE CARRERA PROFESIONAL DEL PERSONAL ESTATUTARIO, DEL SERVICIO DE SALUD DE CASTILLA Y LEÓN CONVOCADO POR RESOLUCIÓN DE ###FECHA### DE LA GERENCIA REGIONAL DE SALUD";
			}
			//Cabecera
			cabecera = cabecera.replaceFirst("###FECHA###", DateUtil.convertDateToStringLargaMesMayus(
					Constantes.FECHA_LETRA, DateUtil.convertStringToDate(convocatoria.getFResolucion())));
			
			if (Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol())){
				cabecera = cabecera.replaceFirst("###GERENCIA###", getGerencia(jcylUsuario).getDNombre());
				if(Utilidades.isNullOrIsEmpty(getGrado(jcylUsuario, actasOT).getDDescripcion())){
					cabecera = cabecera.replaceFirst("###GRADO###", "GRADO");
				}else{
					cabecera = cabecera.replaceFirst("###GRADO###", getGrado(jcylUsuario, actasOT).getDDescripcion().substring(3));
				}
			}
			
			parametros.put("nombreComite", cabecera.toUpperCase());

			
			//Categoría
			OCAPCategProfesionalesOT categoria = getCategoria(jcylUsuario, actasOT);
			parametros.put("categoria", categoria);

			//Gerencia
			OCAPGerenciasOT gerencia = getGerencia(jcylUsuario);
			parametros.put("gerencia", gerencia);

			anhadirTratamientoActaConstitucion(actasOT);
			anhadirEnCalidadActaConstitucion(actasOT);
			
			//Datos Docu
			String fSesionImprimible = DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA, actasOT.getFSesion());
			actasOT.setFSesionImprimible(fSesionImprimible);
			parametros.put("datosDocu", actasOT);

			parametros.put("listadoVocales", jasperSubReport);
			datosSubReportListadoVocales = new ConceptoReport();
			datosSubReportListadoVocales = generarDatosSubReportListadoVocales(actasOT.getListadoVocalesTitulares(),
					jcylUsuario);

			if (datosSubReportListadoVocales == null)
				parametros.put("vocalesVacio", "");
			else {
				parametros.put("datosListadoVocales", datosSubReportListadoVocales);
			}

			parametros.put("listadoVocalesSuplentes", jasperSubReportSuplentes);
			datosSubReportListadoVocalesSuplentes = new ConceptoReport();
			datosSubReportListadoVocalesSuplentes = generarDatosSubReportListadoVocales(
					actasOT.getListadoVocalesSuplentes(), jcylUsuario);

			if (datosSubReportListadoVocalesSuplentes == null)
				parametros.put("vocalesSuplentesVacio", "");
			else {
				parametros.put("datosListadoVocalesSuplentes", datosSubReportListadoVocalesSuplentes);
			}

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "actaConstitucion" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaConstitucion: ERROR" + e.getMessage());
			throw new Exception("Acta de Constitución NO generada");
		}
	}

	private OCAPCategProfesionalesOT getCategoria(JCYLUsuario jcylUsuario, OCAPActasOT actasOT)
			throws SQLException, Exception {
		OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
		OCAPCategProfesionalesOT categoria = categProfesionalesLN
				.buscarOCAPCategProfesionales(actasOT.getCProfesionalId());
		return categoria;
	}

	private OCAPGerenciasOT getGerencia(JCYLUsuario jcylUsuario) throws SQLException, Exception {
		OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
		Map parametrosUsuario = jcylUsuario.getParametrosUsuario();
		OCAPGerenciasOT gerencia = gerenciasLN
				.buscarOCAPGerencias(Long.parseLong((String) parametrosUsuario.get("PARAM_GERENCIA")));
		gerencia.setDProvinciaNombre(Cadenas.capitalizar(gerencia.getDProvinciaNombre()));
		return gerencia;
	}

	private void anhadirTratamientoActaConstitucion(OCAPActasOT actasOT) {
		if (!Cadenas.EsVacia(actasOT.getBSexoPresi1())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(actasOT.getBSexoPresi1()))
				actasOT.setDNombreApellidosPresi1("D. " + actasOT.getDNombreApellidosPresi1());
			else {
				actasOT.setDNombreApellidosPresi1("Dña. " + actasOT.getDNombreApellidosPresi1());
			}
		}

		if (!Cadenas.EsVacia(actasOT.getBSexoPresi2())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(actasOT.getBSexoPresi2()))
				actasOT.setDNombreApellidosPresi2("D. " + actasOT.getDNombreApellidosPresi2());
			else {
				actasOT.setDNombreApellidosPresi2("Dña. " + actasOT.getDNombreApellidosPresi2());
			}
		}

		for (int i = 0; i < actasOT.getListadoVocalesTitulares().size(); i++) {
			if (!Cadenas.EsVacia(((OCAPAsistenteOT) actasOT.getListadoVocalesTitulares().get(i)).getBSexo())) {
				if (Constantes.SEXO_HOMBRE_VALUE
						.equals(((OCAPAsistenteOT) actasOT.getListadoVocalesTitulares().get(i)).getBSexo()))
					((OCAPAsistenteOT) actasOT.getListadoVocalesTitulares().get(i)).setDNombreApellidos("D. "
							+ ((OCAPAsistenteOT) actasOT.getListadoVocalesTitulares().get(i)).getDNombreApellidos());
				else {
					((OCAPAsistenteOT) actasOT.getListadoVocalesTitulares().get(i)).setDNombreApellidos("Dña. "
							+ ((OCAPAsistenteOT) actasOT.getListadoVocalesTitulares().get(i)).getDNombreApellidos());
				}
			}
		}

		for (int i = 0; i < actasOT.getListadoVocalesSuplentes().size(); i++) {
			if (!Cadenas.EsVacia(((OCAPAsistenteOT) actasOT.getListadoVocalesSuplentes().get(i)).getBSexo())) {
				if (Constantes.SEXO_HOMBRE_VALUE
						.equals(((OCAPAsistenteOT) actasOT.getListadoVocalesSuplentes().get(i)).getBSexo()))
					((OCAPAsistenteOT) actasOT.getListadoVocalesSuplentes().get(i)).setDNombreApellidos("D. "
							+ ((OCAPAsistenteOT) actasOT.getListadoVocalesSuplentes().get(i)).getDNombreApellidos());
				else {
					((OCAPAsistenteOT) actasOT.getListadoVocalesSuplentes().get(i)).setDNombreApellidos("Dña. "
							+ ((OCAPAsistenteOT) actasOT.getListadoVocalesSuplentes().get(i)).getDNombreApellidos());
				}
			}
		}

		if (!Cadenas.EsVacia(actasOT.getBSexoSecre1())) {
			if (Constantes.SEXO_HOMBRE_VALUE.equals(actasOT.getBSexoSecre1()))
				actasOT.setDNombreApellidosSecre1("D. " + actasOT.getDNombreApellidosSecre1());
			else {
				actasOT.setDNombreApellidosSecre1("Dña. " + actasOT.getDNombreApellidosSecre1());
			}
		}

		if (!Cadenas.EsVacia(actasOT.getBSexoSecre2()))
			if (Constantes.SEXO_HOMBRE_VALUE.equals(actasOT.getBSexoSecre2()))
				actasOT.setDNombreApellidosSecre2("D. " + actasOT.getDNombreApellidosSecre2());
			else
				actasOT.setDNombreApellidosSecre2("Dña. " + actasOT.getDNombreApellidosSecre2());
	}

	private void anhadirEnCalidadActaConstitucion(OCAPActasOT actasOT) {
		if (!Cadenas.EsVacia(actasOT.getBEnCalidadPresi1())) {
			if ("T".equals(actasOT.getBEnCalidadPresi1()))
				actasOT.setDNombreApellidosPresi1(actasOT.getDNombreApellidosPresi1() + " (Titular)");
			else {
				actasOT.setDNombreApellidosPresi1(actasOT.getDNombreApellidosPresi1() + " (Suplente)");
			}
		}

		if (!Cadenas.EsVacia(actasOT.getBEnCalidadPresi2())) {
			if ("T".equals(actasOT.getBEnCalidadPresi2()))
				actasOT.setDNombreApellidosPresi2(actasOT.getDNombreApellidosPresi2() + " (Titular)");
			else {
				actasOT.setDNombreApellidosPresi2(actasOT.getDNombreApellidosPresi2() + " (Suplente)");
			}

		}

		if (!Cadenas.EsVacia(actasOT.getBEnCalidadSecre1())) {
			if ("T".equals(actasOT.getBEnCalidadSecre1()))
				actasOT.setDNombreApellidosSecre1(actasOT.getDNombreApellidosSecre1() + " (Titular)");
			else {
				actasOT.setDNombreApellidosSecre1(actasOT.getDNombreApellidosSecre1() + " (Suplente)");
			}
		}

		if (!Cadenas.EsVacia(actasOT.getBEnCalidadSecre2()))
			if ("T".equals(actasOT.getBEnCalidadSecre2()))
				actasOT.setDNombreApellidosSecre2(actasOT.getDNombreApellidosSecre2() + " (Titular)");
			else
				actasOT.setDNombreApellidosSecre2(actasOT.getDNombreApellidosSecre2() + " (Suplente)");
	}

	public ConceptoReport generarDatosSubReportListadoVocales(ArrayList listadoVocales, JCYLUsuario jcylUsuario)
			throws Exception {
		ConceptoReport report = null;
		OCAPAsistenteOT asistenteOT = null;
		try {
			OCAPConfigApp.logger.info("Inicio");

			if (listadoVocales.size() != 0) {
				report = new ConceptoReport();
			}

			for (int i = 0; i < listadoVocales.size(); i++) {
				asistenteOT = (OCAPAsistenteOT) listadoVocales.get(i);
				report.addRow();
				report.putElement("dNombreApellidos", asistenteOT.getDNombreApellidos());
				i++;
				if (i < listadoVocales.size()) {
					asistenteOT = (OCAPAsistenteOT) listadoVocales.get(i);
					asistenteOT = (OCAPAsistenteOT) listadoVocales.get(i);
					report.putElement("dNombreApellidos2", asistenteOT.getDNombreApellidos());
				}
			}

			OCAPConfigApp.logger.info(" Fin");

			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
	}

	public void crearActaReunion(JCYLUsuario jcylUsuario, HttpServletResponse response, OCAPActasOT actasOT,
			String pathBase) throws Exception {
		String nombreReportPadre = null;
		String nombreSubreportListadoVocales = null;
		String nombreSubreportListadoVocalesSuplentes = null;
		String nombreSubreportListadoUsuarios = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperReport jasperSubReportListadoVocales = null;
		JasperReport jasperSubReportListadoVocalesSuplentes = null;
		JasperReport jasperSubReportListadoUsuarios = null;
		JasperPrint jasperPrint = null;
		ConceptoReport datosSubReportListadoVocales = null;
		ConceptoReport datosSubReportListadoVocalesSuplentes = null;
		ConceptoReport datosSubReportListadoUsuarios = null;
		OCAPUsuariosOT usuarioOT = null;
		OCAPGradoOT grado = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "actaReunion" + ".jasper";
			nombreSubreportListadoVocales = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocales" + ".jasper";
			nombreSubreportListadoVocalesSuplentes = pathBase + File.separator + "reports" + File.separator
					+ "compilados" + File.separator + "actasListadoVocalesSuplentes" + ".jasper";
			nombreSubreportListadoUsuarios = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoUsuarios" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			File fichSubReportListadoVocales = new File(nombreSubreportListadoVocales);
			jasperSubReportListadoVocales = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocales);
			File fichSubReportListadoVocalesSuplentes = new File(nombreSubreportListadoVocalesSuplentes);
			jasperSubReportListadoVocalesSuplentes = (JasperReport) JRLoader
					.loadObject(fichSubReportListadoVocalesSuplentes);
			File fichSubReportListadoUsuarios = new File(nombreSubreportListadoUsuarios);
			jasperSubReportListadoUsuarios = (JasperReport) JRLoader.loadObject(fichSubReportListadoUsuarios);

			parametros.put("ruta", pathBase);

			grado = getGrado(jcylUsuario, actasOT);
			parametros.put("grado", grado);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoria = convocatoriasLN.buscarOCAPConvocatorias(actasOT.getCConvocatoriaId());
			parametros.put("convocatoria", convocatoria);

			String cabecera = "";
			if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
				cabecera = "REUNIÓN DE LA COMISIÓN CENTRAL EVALUADORA DE LAS SOLICITUDES DE RECONOCIMIENTO DE GRADO FORMULADAS POR EL PERSONAL ESTATUTARIO FIJO QUE DESEMPEÑE PUESTOS DE CARÁCTER DIRECTIVO O PUESTOS EN LA ESTRUCTURA ADMINISTRATIVA O DE GESTIÓN DE LA GERENCIA REGIONAL DE SALUD CONVOCADO POR RESOLUCIÓN DE ###FECHA### DE LA GERENCIA REGIONAL DE SALUD";
			else
				cabecera = "REUNIÓN DEL COMITÉ ESPECÍFICO DE INSTITUCIONES SANITARIAS VALORADOR DE LOS MÉRITOS CURRICULARES DE LA AUTOEVALUACIÓN PARA EL RECONOCIMIENTO INDIVIDUAL DE GRADO DE CARRERA PROFESIONAL DEL PERSONAL ESTATUTARIO, DEL SERVICIO DE SALUD DE CASTILLA Y LEÓN CONVOCADO POR RESOLUCIÓN DE ###FECHA### DE LA GERENCIA REGIONAL DE SALUD";
			cabecera = cabecera.replaceFirst("###FECHA###", DateUtil.convertDateToStringLargaMesMayus(
					Constantes.FECHA_LETRA, DateUtil.convertStringToDate(convocatoria.getFResolucion())));
			parametros.put("nombreComite", cabecera.toUpperCase());

			OCAPCategProfesionalesOT categoria = getCategoria(jcylUsuario, actasOT);
			parametros.put("categoria", categoria);

			OCAPGerenciasOT gerencia = getGerencia(jcylUsuario);
			parametros.put("gerencia", gerencia);

			ArrayList listadoUsuarios = new ArrayList();
			for (int i = 0; (actasOT.getListadoUsuariosInfMotivado() != null)
					&& (i < actasOT.getListadoUsuariosInfMotivado().size()); i++) {
				usuarioOT = (OCAPUsuariosOT) actasOT.getListadoUsuariosInfMotivado().get(i);
				String dNombreApellidos = usuarioOT.getDNombre() + " " + usuarioOT.getDApellido1();
				String dDNI = usuarioOT.getCDni();
				if ("A".equals(usuarioOT.getDDescripcion()))
					listadoUsuarios
							.add(new OCAPUsuarioOT(dNombreApellidos, dDNI, "Aclaraciones", usuarioOT.getDGrado_des()));
				else {
					listadoUsuarios.add(
							new OCAPUsuarioOT(dNombreApellidos, dDNI, "Informe motivado", usuarioOT.getDGrado_des()));
				}
			}

			anhadirTratamientoActaConstitucion(actasOT);
			anhadirEnCalidadActaConstitucion(actasOT);

			String fSesionImprimible = DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA, actasOT.getFSesion());
			actasOT.setFSesionImprimible(fSesionImprimible);

			parametros.put("datosDocu", actasOT);

			parametros.put("listadoVocales", jasperSubReportListadoVocales);
			datosSubReportListadoVocales = new ConceptoReport();
			datosSubReportListadoVocales = generarDatosSubReportListadoVocales(actasOT.getListadoVocalesTitulares(),
					jcylUsuario);

			if (datosSubReportListadoVocales == null)
				parametros.put("vocalesVacio", "");
			else {
				parametros.put("datosListadoVocales", datosSubReportListadoVocales);
			}

			parametros.put("listadoVocalesSuplentes", jasperSubReportListadoVocalesSuplentes);
			datosSubReportListadoVocalesSuplentes = new ConceptoReport();
			datosSubReportListadoVocalesSuplentes = generarDatosSubReportListadoVocales(
					actasOT.getListadoVocalesSuplentes(), jcylUsuario);

			if (datosSubReportListadoVocalesSuplentes == null)
				parametros.put("vocalesSuplentesVacio", "");
			else {
				parametros.put("datosListadoVocalesSuplentes", datosSubReportListadoVocalesSuplentes);
			}

			parametros.put("listadoUsuarios", jasperSubReportListadoUsuarios);
			datosSubReportListadoUsuarios = new ConceptoReport();
			datosSubReportListadoUsuarios = generarDatosSubReportListadoUsuarios(listadoUsuarios, jcylUsuario);

			if (datosSubReportListadoUsuarios == null)
				parametros.put("usuariosVacio", "No se ha valorado ningún profesional en esta fecha.");
			else {
				parametros.put("datosListadoUsuarios", datosSubReportListadoUsuarios);
			}

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "actaReunion" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaReunion: ERROR" + e.getMessage());
			throw new Exception("Acta de Constitución NO generada");
		}
	}

	private OCAPGradoOT getGrado(JCYLUsuario jcylUsuario, OCAPActasOT actasOT) throws SQLException, Exception {
		OCAPGradoOT grado;
		OCAPGradoLN gradoLN = new OCAPGradoLN(jcylUsuario);
		if (actasOT.getCGradoId() != 0L) {
			grado = gradoLN.buscarOCAPGrado(actasOT.getCGradoId());
			grado.setDDescripcion(" a " + grado.getDDescripcion());
		} else {
			grado = new OCAPGradoOT();
			grado.setDDescripcion("");
		}
		return grado;
	}

	private ArrayList obtenerVocalesTitulares(ArrayList listadoVocales) {
		ArrayList listadoVocalesTitulares = new ArrayList();

		for (int i = 0; i < listadoVocales.size(); i++) {
			if ("T".equals(((OCAPAsistenteOT) listadoVocales.get(i)).getBEnCalidad())) {
				listadoVocalesTitulares.add(listadoVocales.get(i));
			}

		}

		return listadoVocalesTitulares;
	}

	private ArrayList obtenerVocalesSuplentes(ArrayList listadoVocales) {
		ArrayList listadoVocalesSuplentes = new ArrayList();

		for (int i = 0; i < listadoVocales.size(); i++) {
			if (Constantes.SI_ESP.equals(((OCAPAsistenteOT) listadoVocales.get(i)).getBEnCalidad())) {
				listadoVocalesSuplentes.add(listadoVocales.get(i));
			}

		}

		return listadoVocalesSuplentes;
	}

	public void crearActaSoliAclaracion(JCYLUsuario jcylUsuario, HttpServletResponse response, OCAPActasOT actasOT,
			String pathBase) throws Exception {
		String nombreReportPadre = null;
		String nombreSubreportListadoVocales = null;
		String nombreSubreportListadoVocalesSuplentes = null;
		String nombreSubreportListadoUsuarios = null;
		Hashtable parametros = new Hashtable();
		JasperReport jasperReport = null;
		JasperReport jasperSubReportListadoVocales = null;
		JasperReport jasperSubReportListadoVocalesSuplentes = null;
		JasperReport jasperSubReportListadoUsuarios = null;
		JasperPrint jasperPrint = null;
		ConceptoReport datosSubReportListadoVocales = null;
		ConceptoReport datosSubReportListadoVocalesSuplentes = null;
		ConceptoReport datosSubReportListadoUsuarios = null;
		OCAPUsuariosOT usuarioOT = null;
		OCAPGradoOT grado = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: Inicio");

			nombreReportPadre = pathBase + File.separator + "reports" + File.separator + "compilados" + File.separator
					+ "actaAclaraciones" + ".jasper";
			nombreSubreportListadoVocales = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoVocales" + ".jasper";
			nombreSubreportListadoVocalesSuplentes = pathBase + File.separator + "reports" + File.separator
					+ "compilados" + File.separator + "actasListadoVocalesSuplentes" + ".jasper";
			nombreSubreportListadoUsuarios = pathBase + File.separator + "reports" + File.separator + "compilados"
					+ File.separator + "actasListadoUsuarios" + ".jasper";

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: Cargar report padre");
			File fichReportPadre = new File(nombreReportPadre);
			jasperReport = (JasperReport) JRLoader.loadObject(fichReportPadre);

			File fichSubReportListadoVocales = new File(nombreSubreportListadoVocales);
			jasperSubReportListadoVocales = (JasperReport) JRLoader.loadObject(fichSubReportListadoVocales);
			File fichSubReportListadoVocalesSuplentes = new File(nombreSubreportListadoVocalesSuplentes);
			jasperSubReportListadoVocalesSuplentes = (JasperReport) JRLoader
					.loadObject(fichSubReportListadoVocalesSuplentes);
			File fichSubReportListadoUsuarios = new File(nombreSubreportListadoUsuarios);
			jasperSubReportListadoUsuarios = (JasperReport) JRLoader.loadObject(fichSubReportListadoUsuarios);

			parametros.put("ruta", pathBase);

			grado = getGrado(jcylUsuario, actasOT);
			parametros.put("grado", grado);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			OCAPConvocatoriasOT convocatoria = convocatoriasLN.buscarOCAPConvocatorias(actasOT.getCConvocatoriaId());
			parametros.put("convocatoria", convocatoria);

			String cabecera = "";
			if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
				cabecera = "REUNIÓN DE LA COMISIÓN CENTRAL EVALUADORA DE LAS SOLICITUDES DE RECONOCIMIENTO DE GRADO FORMULADAS POR EL PERSONAL ESTATUTARIO FIJO QUE DESEMPEÑE PUESTOS DE CARÁCTER DIRECTIVO O PUESTOS EN LA ESTRUCTURA ADMINISTRATIVA O DE GESTIÓN DE LA GERENCIA REGIONAL DE SALUD CONVOCADO POR RESOLUCIÓN DE ###FECHA### DE LA GERENCIA REGIONAL DE SALUD";
			else
				cabecera = "REUNIÓN DEL COMITÉ ESPECÍFICO DE INSTITUCIONES SANITARIAS VALORADOR DE LOS MÉRITOS CURRICULARES DE LA AUTOEVALUACIÓN PARA EL RECONOCIMIENTO INDIVIDUAL DE GRADO DE CARRERA PROFESIONAL DEL PERSONAL ESTATUTARIO, DEL SERVICIO DE SALUD DE CASTILLA Y LEÓN CONVOCADO POR RESOLUCIÓN DE ###FECHA### DE LA GERENCIA REGIONAL DE SALUD";
			cabecera = cabecera.replaceFirst("###FECHA###", DateUtil.convertDateToStringLargaMesMayus(
					Constantes.FECHA_LETRA, DateUtil.convertStringToDate(convocatoria.getFResolucion())));
			parametros.put("nombreComite", cabecera.toUpperCase());

			OCAPCategProfesionalesOT categoria = getCategoria(jcylUsuario, actasOT);
			parametros.put("categoria", categoria);

			OCAPGerenciasOT gerencia = getGerencia(jcylUsuario);
			parametros.put("gerencia", gerencia);

			OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();

			ArrayList listadoUsuarios = new ArrayList();
			for (int i = 0; (actasOT.getListadoUsuariosInfMotivado() != null)
					&& (i < actasOT.getListadoUsuariosInfMotivado().size()); i++) {
				usuarioOT = (OCAPUsuariosOT) actasOT.getListadoUsuariosInfMotivado().get(i);
				String dNombreApellidos = usuarioOT.getDNombre() + " " + usuarioOT.getDApellido1();
				String dDNI = usuarioOT.getCDni();
				if ("A".equals(usuarioOT.getDDescripcion()))
					listadoUsuarios
							.add(new OCAPUsuarioOT(dNombreApellidos, dDNI, "Aclaraciones", usuarioOT.getDGrado_des()));
				else {
					listadoUsuarios.add(
							new OCAPUsuarioOT(dNombreApellidos, dDNI, "Informe motivado", usuarioOT.getDGrado_des()));
				}

			}

			anhadirTratamientoActaConstitucion(actasOT);
			anhadirEnCalidadActaConstitucion(actasOT);

			String fSesionImprimible = DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA, actasOT.getFSesion());
			actasOT.setFSesionImprimible(fSesionImprimible);
			parametros.put("datosDocu", actasOT);

			parametros.put("listadoVocales", jasperSubReportListadoVocales);
			datosSubReportListadoVocales = new ConceptoReport();
			datosSubReportListadoVocales = generarDatosSubReportListadoVocales(actasOT.getListadoVocalesTitulares(),
					jcylUsuario);

			if (datosSubReportListadoVocales == null)
				parametros.put("vocalesVacio", "");
			else {
				parametros.put("datosListadoVocales", datosSubReportListadoVocales);
			}

			parametros.put("listadoVocalesSuplentes", jasperSubReportListadoVocalesSuplentes);
			datosSubReportListadoVocalesSuplentes = new ConceptoReport();
			datosSubReportListadoVocalesSuplentes = generarDatosSubReportListadoVocales(
					actasOT.getListadoVocalesSuplentes(), jcylUsuario);

			if (datosSubReportListadoVocalesSuplentes == null)
				parametros.put("vocalesSuplentesVacio", "");
			else {
				parametros.put("datosListadoVocalesSuplentes", datosSubReportListadoVocalesSuplentes);
			}

			parametros.put("listadoUsuarios", jasperSubReportListadoUsuarios);
			datosSubReportListadoUsuarios = new ConceptoReport();
			datosSubReportListadoUsuarios = generarDatosSubReportListadoUsuarios(listadoUsuarios, jcylUsuario);

			if (datosSubReportListadoUsuarios == null)
				parametros.put("usuariosVacio", "No se ha valorado ningún profesional en esta fecha.");
			else {
				parametros.put("datosListadoUsuarios", datosSubReportListadoUsuarios);
			}

			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

			byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);

			OutputStream out = response.getOutputStream();
			SimpleDateFormat ahora = new SimpleDateFormat("ddMMyyyy_hhmm");
			String nombreFichero = "actaSolicitudAclaraciones" + ahora.format(new Date()) + ".pdf";
			response.setHeader("Content-disposition", "attachment; filename=\"" + nombreFichero + "\"");
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();

			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " crearActaSoliAclaracion: ERROR" + e.getMessage());
			throw new Exception("Acta de Solicitud Aclaraciones NO generada");
		}
	}

	public ConceptoReport generarDatosSubReportListadoUsuarios(ArrayList listadoUsuarios, JCYLUsuario jcylUsuario)
			throws Exception {
		ConceptoReport report = null;
		OCAPUsuarioOT usuarioOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " generarDatosSubReportListadoUsuarios: Inicio");

			if (listadoUsuarios.size() != 0) {
				report = new ConceptoReport();
			}

			for (int i = 0; i < listadoUsuarios.size(); i++) {
				usuarioOT = (OCAPUsuarioOT) listadoUsuarios.get(i);
				report.addRow();
				report.putElement("dNombreApellidos", usuarioOT.getDNombreApellidos());
				report.putElement("dDNI", usuarioOT.getDNIF());
				report.putElement("dAccion", usuarioOT.getDAccion());
				report.putElement("dGrado", usuarioOT.getDGrado());
			}

			OCAPConfigApp.logger.info(getClass().getName() + " generarDatosSubReportListadoUsuarios: Fin");

			return report;
		} catch (Exception e) {
			OCAPConfigApp.logger
					.info(getClass().getName() + " generarDatosSubReportListadoUsuarios: ERROR" + e.getMessage());
			throw e;
		}
	}
	
	  public int contarActas(OCAPActasOT actaOT)
			    throws Exception
			  {
			    int contador = 0;
			    try
			    {
			      OCAPConfigApp.logger.info(getClass().getName() + " contarActas: Inicio");
			      
			      OCAPActasOAD actasOAD = OCAPActasOAD.getInstance();
			      
			      contador = actasOAD.contarActas(actaOT);
			      
			      OCAPConfigApp.logger.info(getClass().getName() + " contarActas: Fin");
			    }
			    catch (Exception e)
			    {
			      OCAPConfigApp.logger.info(getClass().getName() + " contarActas: ERROR: " + e.getMessage());
			      throw e;
			    }
			    return contador;
			  }	
	

	  public ArrayList buscarActas(int inicio, int cuantos, OCAPActasOT actasOT)
			    throws Exception
			  {
			    ArrayList actasTotal = null;

			    try
			    {
			      OCAPConfigApp.logger.info(getClass().getName() + " buscarActas: Inicio");
			      OCAPActasOAD actasOAD = OCAPActasOAD.getInstance();
			      actasTotal = actasOAD.buscarActas(inicio, cuantos, actasOT);

			      OCAPConfigApp.logger.info(getClass().getName() + " buscarActas: Fin");
			    }
			    catch (Exception e)
			    {
			      OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			      throw e;
			    }
			    return actasTotal;
			  }
}

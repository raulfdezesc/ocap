package es.jcyl.fqs.ocap.actions.actas;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import au.com.bytecode.opencsv.CSVReader;
import es.jcyl.fqs.ocap.actionforms.actas.OCAPMiembrosComitesForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.actas.OCAPMiembrosComitesLN;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ot.actas.OCAPMiembrosComitesOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;

public class OCAPMiembrosComitesAction extends OCAPGenericAction {
	public ActionForward irCargar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = new ArrayList();
		ArrayList listadoConvocatorias = new ArrayList();
		try {
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger.info(getClass().getName() + " irCargar: Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
			listadoCategorias = categProfesionalesLN.listarCategoriasProfesionales();
			session.setAttribute(Constantes.COMBO_CATEGORIA, listadoCategorias);

			OCAPConvocatoriasLN convocatoriasLN = new OCAPConvocatoriasLN(jcylUsuario);
			listadoConvocatorias = convocatoriasLN.listarConvocatorias();
			session.setAttribute(Constantes.COMBO_CONVOCATORIAS, listadoConvocatorias);

			session.setAttribute("iRegistro", Integer.toString(1));

			int primerRegistro = 1;
			int registrosPorPagina = 20;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null
					: session.getAttribute("iRegistro").toString()) != null) {
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			OCAPMiembrosComitesLN miembrosComitesLN = new OCAPMiembrosComitesLN(jcylUsuario);
			OCAPMiembrosComitesOT miembrosComitesOT = new OCAPMiembrosComitesOT();

			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				String paramGerencia = (String) parametros.get("PARAM_GERENCIA");
				miembrosComitesOT.setCGerenciaId(Long.parseLong(paramGerencia));
			}

			ArrayList listadoMiembros = miembrosComitesLN.buscar(primerRegistro, registrosPorPagina, miembrosComitesOT);
			int numMiembros = miembrosComitesLN.contarMiembros(miembrosComitesOT, jcylUsuario);

			if (session.getAttribute("listadoMiembros") != null)
				session.removeAttribute("listadoMiembros");
			session.setAttribute("listadoMiembros", listadoMiembros);

			session.setAttribute("numMiembros", new Integer(numMiembros));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoMiembros);
			pagina.setNRegistros(((Integer) session.getAttribute("numMiembros")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPConfigApp.logger.info(getClass().getName() + " irCargar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irCargar: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irCargar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- ALTA OCAP_MIEMBROS_COMITES --------- ");

			ActionErrors errores = new ActionErrors();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPMiembrosComitesLN oCAPMiembrosComitesLN = new OCAPMiembrosComitesLN(jcylUsuario);
			OCAPMiembrosComitesOT datos = new OCAPMiembrosComitesOT();

			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();

			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				String paramGerencia = (String) parametros.get("PARAM_GERENCIA");
				datos.setCGerenciaId(Long.parseLong(paramGerencia));
			}

			OCAPMiembrosComitesForm formulario = (OCAPMiembrosComitesForm) form;
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");
			int result = 0;
			String datosMiembro ;
			if (formulario.getDDatosMiembro() != null) {
				if(formulario.getDDatosMiembro().endsWith("\\‌​r|\\n")){
					datosMiembro = formulario.getDDatosMiembro().replaceAll("\\‌​r|\\n", "").trim();

				}else{
					datosMiembro=formulario.getDDatosMiembro().trim();
				}
				datos.setDDatosMiembro(datosMiembro);
				datos.setCProfesionalId(formulario.getCProfesionalId());
				datos.setCConvocatoria(formulario.getCConvocatoria());

				datos.setCUsuAlta(
						((JCYLUsuario) request.getSession().getAttribute("JCYLUsuario")).getUsuario().getC_usr_id());
				datos.setDRol(jcylUsuario.getUser().getRol());
				result = oCAPMiembrosComitesLN.altaOCAPMiembrosComites(datos);
			}

			if (result == 1) {
				request.setAttribute("mensaje", "El registro se ha insertado con Ã©xito en el sistema ");
				request.setAttribute("rutaVuelta", "OCAPMiembrosComites.do?accion=irCargar");
				sig = "exito";
			} else {
				request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
				sig = "error";
			}
			OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA OCAP_MIEMBROS_COMITES --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";

			if (ex.getMessage().startsWith("ORA-00001")) {
				mensajeError = "Error: Registro duplicado";
			}

			request.setAttribute("mensaje", mensajeError);
		}

		return mapping.findForward(sig);
	}

	public ActionForward file(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String miembro = "";
		String listaMiembro = "";
		String insertarListaMiembro = "";
		String msg = "";
		int contMiembros = 0;

		String sig = "irCargar";
		OCAPMiembrosComitesForm uploadForm = (OCAPMiembrosComitesForm) form;
		FormFile formFile = uploadForm.getArchivoExcel();
		String path = getServlet().getServletContext().getRealPath("") + "/" + formFile.getFileName();

		try {

			if (path != null && !path.equals("")) {
				if (path.endsWith(Constantes.APP_XLS)) {
					try {
						HSSFWorkbook wb = new HSSFWorkbook(formFile.getInputStream());
						DataFormatter objDefaultFormat = new DataFormatter();
						FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
						if (wb.getNumberOfSheets() > 0) {
							HSSFSheet sheet = wb.getSheetAt(Constantes.ZERO);
							int rows = sheet.getPhysicalNumberOfRows();
							int filasTotalExcel = 0;
							int filasProcExcel = 0;

							// Si el excel está vacío
							if (sheet.getPhysicalNumberOfRows() < 1) {
								msg = "El fichero está vacío";
							}
							// Si el excel mide mas de 300
							if (sheet.getPhysicalNumberOfRows() > 301) {
								msg = "El fichero supera los 300 registros";
								rows = 0;

							}

							for (int r = 1; r < rows; r++) {
								miembro = "";
								contMiembros = 0;
								HSSFRow row = sheet.getRow(r);

								if (row != null && row.getLastCellNum() == 4) {
									for (int i = 0; i < row.getLastCellNum(); i++) {

										String cellString = null;
										HSSFCell cellHSS = row.getCell(i);
										objFormulaEvaluator.evaluate(cellHSS);
										cellString = (objDefaultFormat.formatCellValue(cellHSS, objFormulaEvaluator));

										if (i == 0 && cellString != null && cellString != "") {
											miembro += cellString;
											contMiembros++;
										} else if (i == 1 && cellString != null && cellString != "") {
											miembro += ";";
											miembro += cellString;
											contMiembros++;
										}

										else if (i == 2 && cellString != null && cellString != ""
												&& (cellString.equals("P") || cellString.equals("V")
														|| cellString.equals("S"))) {
											miembro += ";";
											miembro += cellString;
											contMiembros++;
										}

										else if (i == 3 && cellString != null && cellString != ""
												&& (cellString.equals("H") || cellString.equals("M"))) {
											miembro += ";";
											miembro += cellString;
											miembro += ";";
											miembro += "\r\n";
											contMiembros++;
										}
									}

								}
								if (contMiembros == 4) {
									listaMiembro += miembro;
									filasProcExcel++;
								}
								filasTotalExcel++;
							}
							// Calcular filas procesadas
							if (filasTotalExcel == 0||filasProcExcel==0) {
								msg = "No se han añadido ninguna fila, por favor revise el fichero";
							} else {
								if (filasTotalExcel == filasProcExcel) {
									msg = "Se han cargado todas las filas con éxito";
								} else {

								}
								if (filasProcExcel < filasTotalExcel) {
									msg = "Se han cargado " + filasProcExcel + " de las " + filasTotalExcel
											+ " existentes. Por favor revise el fichero";
								}
							}

							if (!listaMiembro.isEmpty()) {

								insertarListaMiembro = listaMiembro.substring(0, listaMiembro.length() - 2);
							}
							OCAPMiembrosComitesForm formulario = (OCAPMiembrosComitesForm) form;
							formulario.setDDatosMiembro(insertarListaMiembro);
							request.setAttribute("insercionExcel", "true");
							request.setAttribute("mensajeExcel", msg);
							cargarMiembros(mapping, formulario, request, response);

						}

					} catch (Exception ex) {
						request.setAttribute("mensaje", "Error al cargar el fichero excel");
						sig = "error";
						return mapping.findForward(sig);
					}

				} else if (path.endsWith(Constantes.APP_CSV)) {

			        String line = "";

					String miembroCsv = "";
					String ListaMiembroCsv = "";
					String insertarListaMiembroCsv = "";
					int filasTotalCsv = 0;
					int filasProcCsv = 0;

					int numCsv;
					try {
						 BufferedReader br = null;
						   br = new BufferedReader(new InputStreamReader(formFile.getInputStream()));
						   while ((line = br.readLine()) != null) {
							numCsv = 0;
					
							// Convertimos la linea en un array de Strings

							String strar[] = line.split(Constantes.PUNTO_COMA);

							// Si la linea no contiene 4 columnas no la
							// añadimos al array final
							if (strar.length == 4) {
								for (int i = 0; i < strar.length; i++) {
									miembroCsv = "";
									miembroCsv = strar[i].replaceAll(Constantes.COMILLAS, Constantes.EMPTY);

									if (i == 2 && !(miembroCsv.equals("P") || miembroCsv.equals("S")
											|| miembroCsv.equals("V"))) {
										break;
									} else if (i == 3 && !(miembroCsv.equals("H") || miembroCsv.equals("M"))) {
										break;
									} else {
										numCsv++;
									}
								}

								if (numCsv == 4) {
									ListaMiembroCsv += line;
									if(!line.endsWith(";")){
										ListaMiembroCsv += ";";	
									}
									ListaMiembroCsv += "\r\n";
									filasProcCsv++;
								}

							}
							line = Constantes.EMPTY;
							filasTotalCsv++;
						}
						// Calcular filas procesadas
						if (filasTotalCsv == 0||filasProcCsv==0) {
							msg = "No se han añadido ninguna fila, por favor revise el fichero";
						} else {
							if (filasTotalCsv == filasProcCsv) {
								msg = "Se han cargado todas las filas con éxito";
							} else {

							}
							if (filasProcCsv< filasTotalCsv) {
								msg = "Se han cargado " + filasProcCsv + " de las " + filasTotalCsv
										+ " existentes. Por favor revise el fichero";
							}
						}
						if (!ListaMiembroCsv.isEmpty()) {
							insertarListaMiembroCsv = ListaMiembroCsv.substring(0, ListaMiembroCsv.length() - 2);
						}
						OCAPMiembrosComitesForm formulario = (OCAPMiembrosComitesForm) form;
						formulario.setDDatosMiembro(insertarListaMiembroCsv);
						request.setAttribute("insercionExcel", "true");
						request.setAttribute("mensajeExcel", msg);
						cargarMiembros(mapping, formulario, request, response);

					} catch (Exception e) {
						request.setAttribute("mensaje", "Error al cargar el fichero excel");
						sig = "error";
						return mapping.findForward(sig);
					}

				}else{
					msg="La extension del fichero no es correcta, por favor seleccione ficheros *.csv o *.xls";
					OCAPMiembrosComitesForm formulario = (OCAPMiembrosComitesForm) form;
					formulario.setDDatosMiembro(insertarListaMiembro);
					request.setAttribute("insercionExcel", "true");
					request.setAttribute("mensajeExcel", msg);
					cargarMiembros(mapping, formulario, request, response);
				}

			}

		} catch (

		Exception e) {
			request.setAttribute("mensaje", "Error al insertar los miembros");
			sig = "error";
		}

		return mapping.findForward(sig);
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		Logger log = null;
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_MIEMBROS_COMITES --------- ");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			OCAPMiembrosComitesLN oCAPMiembrosComitesLN = new OCAPMiembrosComitesLN(jcylUsuario);
			OCAPMiembrosComitesForm formulario = (OCAPMiembrosComitesForm) form;

			String cMiembroIdS = request.getParameter("cMiembroIdS");
			int cMiembroId;
			if ((cMiembroIdS != null) && (!cMiembroIdS.equals(""))) {
				cMiembroId = Integer.parseInt(cMiembroIdS);
				OCAPConfigApp.logger.info("cMiembroId: " + cMiembroId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			int result = oCAPMiembrosComitesLN.bajaOCAPMiembrosComites(cMiembroId);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPMiembrosComites.do?accion=irCargar");
				sig = "exito";
			}

			OCAPConfigApp.logger.info("---------- BAJA DE OCAP_MIEMBROS_COMITES --------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", "Se ha producido un error");
		}

		return mapping.findForward(sig);
	}

	public ActionForward cargarMiembros(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		String mensajeError = "Se ha producido un error";
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listadoCategorias = new ArrayList();
		ArrayList listadoConvocatorias = new ArrayList();
		try {
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger.info("Inicio");

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			session.setAttribute("iRegistro", Integer.toString(1));

			int primerRegistro = 1;
			int registrosPorPagina = 20;
			String registro = null;

			if ((registro = request.getParameter("iRegistro")) != null)
				try {
					primerRegistro = Integer.parseInt(registro);
					session.setAttribute("iRegistro", primerRegistro + "");
				} catch (NumberFormatException ne) {
				}
			else if ((registro = session.getAttribute("iRegistro") == null ? null
					: session.getAttribute("iRegistro").toString()) != null) {
				try {
					primerRegistro = Integer.parseInt(registro);
				} catch (NumberFormatException ne) {
				}
			}
			OCAPMiembrosComitesLN miembrosComitesLN = new OCAPMiembrosComitesLN(jcylUsuario);
			OCAPMiembrosComitesOT miembrosComitesOT = new OCAPMiembrosComitesOT();

			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				String paramGerencia = (String) parametros.get("PARAM_GERENCIA");
				miembrosComitesOT.setCGerenciaId(Long.parseLong(paramGerencia));
			}

			miembrosComitesOT.setCProfesionalId(((OCAPMiembrosComitesForm) form).getCProfesionalId());
			miembrosComitesOT.setCConvocatoria(((OCAPMiembrosComitesForm) form).getCConvocatoria());

			ArrayList listadoMiembros = miembrosComitesLN.buscar(primerRegistro, registrosPorPagina, miembrosComitesOT);
			int numMiembros = miembrosComitesLN.contarMiembros(miembrosComitesOT, jcylUsuario);

			if (session.getAttribute("listadoMiembros") != null)
				session.removeAttribute("listadoMiembros");
			session.setAttribute("listadoMiembros", listadoMiembros);

			session.setAttribute("numMiembros", new Integer(numMiembros));

			Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
			pagina.setElementos(listadoMiembros);
			pagina.setNRegistros(((Integer) session.getAttribute("numMiembros")).intValue());
			pagina.setRegistroActual(primerRegistro);
			pagina.setRegistrosPorPagina(registrosPorPagina);

			request.setAttribute("listados", pagina);

			OCAPConfigApp.logger.info(getClass().getName() + " irCargar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irCargar: ERROR: " + e.getMessage());
			request.setAttribute("mensaje", mensajeError);
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irCargar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}
}

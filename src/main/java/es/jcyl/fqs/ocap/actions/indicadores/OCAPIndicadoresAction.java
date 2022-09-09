package es.jcyl.fqs.ocap.actions.indicadores;

import es.jcyl.fqs.ocap.actionforms.componentesfqs.OCAPComponentesfqsForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
import es.jcyl.fqs.ocap.ln.gestionCtes.OCAPGestionCtesLN;
import es.jcyl.fqs.ocap.ln.indicadores.OCAPIndicadoresLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public class OCAPIndicadoresAction extends OCAPGenericAction {
	public ActionForward buscarIndicador(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mensajeError = "Se ha producido un error";
		OCAPComponentesfqsForm datosForm = (OCAPComponentesfqsForm) form;
		OCAPUsuariosOT datosArray = null;
		float porcInd = 0.0F;
		ArrayList listadoIndicador = new ArrayList();
		ArrayList listadoIndicadorMod = new ArrayList();
		ArrayList listadoIndicadorNoMod = new ArrayList();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- CONSULTA DE EVALUADORES --------- ");
			OCAPUsuariosOT datosEvaluados = null;
			OCAPUsuariosOT datosEvaluadosNoMod = null;
			OCAPUsuariosOT datosEvaluadosMod = null;
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
			OCAPIndicadoresLN oCAPIndicadoresLN = new OCAPIndicadoresLN(jcylUsuario);

			if ("evalfqs".equals(request.getParameter("opcion"))) {
				datosEvaluados = oCAPIndicadoresLN.buscarIndicadorEvaluados(request.getParameter("opcion"));
				datosEvaluadosNoMod = oCAPIndicadoresLN.buscarIndicadorEvaluadosNoMod(request.getParameter("opcion"),
						jcylUsuario, false);
				datosEvaluadosMod = oCAPIndicadoresLN.buscarIndicadorEvaluadosMod(request.getParameter("opcion"),
						jcylUsuario, true);
			} else if ("cte".equals(request.getParameter("opcion"))) {
				datosEvaluados = oCAPIndicadoresLN.buscarIndicadorEvaluados(request.getParameter("opcion"));
				datosEvaluadosNoMod = oCAPIndicadoresLN.buscarIndicadorCTEsNoMod(request.getParameter("opcion"),
						jcylUsuario, false);
				datosEvaluadosMod = oCAPIndicadoresLN.buscarIndicadorCTEsMod(request.getParameter("opcion"),
						jcylUsuario, true);
			} else if ("ce".equals(request.getParameter("opcion"))) {
				datosEvaluados = oCAPIndicadoresLN.buscarIndicadorEvaluados(request.getParameter("opcion"));
				datosEvaluadosNoMod = oCAPIndicadoresLN.buscarIndicadorCTEsNoMod(request.getParameter("opcion"),
						jcylUsuario, false);
				datosEvaluadosMod = oCAPIndicadoresLN.buscarIndicadorCTEsMod(request.getParameter("opcion"),
						jcylUsuario, true);
			} else {
				datosEvaluados = oCAPEvaluadoresLN.buscarIndicador(request.getParameter("opcion"));
			}
			int numEvaluadosGRS = oCAPIndicadoresLN.buscarIndicadorEvaluadosGRS();

			if (datosEvaluados.getTotalIndicadores() != 0) {
				OCAPConfigApp.logger.info(
						"Se han recuperado " + datosEvaluados.getTotalEvaluados() + " Evaluados para la consulta");
				datosForm.setTotalEvaluadosGRS(numEvaluadosGRS);
				datosForm.setTotalEvaluados(
						datosEvaluadosMod.getTotalEvaluadosMod() + datosEvaluadosNoMod.getTotalEvaluadosNoMod());
				datosForm.setTotalIndicadores(datosEvaluados.getTotalIndicadores());
				datosForm.setTotalIndicadoresOK(datosEvaluadosMod.getTotalIndicadoresModOK()
						+ datosEvaluadosNoMod.getTotalIndicadoresNoModOK());
				datosForm.setTotalIndicadoresKO(datosEvaluadosMod.getTotalIndicadoresModKO()
						+ datosEvaluadosNoMod.getTotalIndicadoresNoModKO());
				porcInd = (float) (100.0D
						* (datosEvaluadosMod.getTotalIndicadoresModOK()
								+ datosEvaluadosNoMod.getTotalIndicadoresNoModOK())
						/ (datosEvaluadosMod.getTotalEvaluadosMod() + datosEvaluadosNoMod.getTotalEvaluadosNoMod()));
				porcInd = (float) (Math.rint(porcInd * 100.0F) / 100.0D);
				datosForm.setPorcIndicadoresOK(porcInd);
				porcInd = (float) (100.0D
						* (datosEvaluadosMod.getTotalIndicadoresModKO()
								+ datosEvaluadosNoMod.getTotalIndicadoresNoModKO())
						/ (datosEvaluadosMod.getTotalEvaluadosMod() + datosEvaluadosNoMod.getTotalEvaluadosNoMod()));
				porcInd = (float) (Math.rint(porcInd * 100.0F) / 100.0D);
				datosForm.setPorcIndicadoresKO(porcInd);

				for (int i = 0; i < datosEvaluadosMod.getListaIndicadoresMod().size(); i++) {
					datosArray = (OCAPUsuariosOT) datosEvaluadosMod.getListaIndicadoresMod().get(i);

					porcInd = (float) (100.0D * datosArray.getTotalIndicadorMod()
							/ datosEvaluadosMod.getTotalEvaluadosMod());

					porcInd = (float) (Math.rint(porcInd * 100.0F) / 100.0D);

					datosArray.setPorcIndicador(porcInd);

					listadoIndicador.add(datosArray);
				}
				for (int i = 0; i < datosEvaluadosNoMod.getListaIndicadoresNoMod().size(); i++) {
					OCAPUsuariosOT datosArray2 = (OCAPUsuariosOT) datosEvaluadosNoMod.getListaIndicadoresNoMod().get(i);

					porcInd = (float) (100.0D * datosArray2.getTotalIndicadorNoMod()
							/ datosEvaluadosNoMod.getTotalEvaluadosNoMod());

					porcInd = (float) (Math.rint(porcInd * 100.0F) / 100.0D);

					datosArray2.setPorcIndicador(porcInd);

					listadoIndicador.add(datosArray2);
				}

				datosForm.setListadoAct(listadoIndicador);

				request.setAttribute("opcion", request.getParameter("opcion"));
			} else {
				request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
			}

			if (datosEvaluadosMod.getTotalIndicadoresMod() != 0) {
				OCAPConfigApp.logger.info("Se han recuperado " + datosEvaluadosMod.getTotalEvaluadosMod()
						+ " Evaluados para la consulta MOD");
				datosForm.setTotalEvaluadosMod(datosEvaluadosMod.getTotalEvaluadosMod());

				datosForm.setTotalIndicadoresModOK(datosEvaluadosMod.getTotalIndicadoresModOK());
				datosForm.setTotalIndicadoresModKO(datosEvaluadosMod.getTotalIndicadoresModKO());
				datosForm.setPorcIndicadoresModOK(datosEvaluadosMod.getPorcIndicadoresModOK());
				datosForm.setPorcIndicadoresModKO(datosEvaluadosMod.getPorcIndicadoresModKO());

				for (int i = 0; i < datosEvaluadosMod.getListaIndicadoresMod().size(); i++) {
					datosArray = (OCAPUsuariosOT) datosEvaluadosMod.getListaIndicadoresMod().get(i);

					porcInd = (float) (100.0D * datosArray.getTotalIndicadorMod()
							/ datosEvaluadosMod.getTotalEvaluadosMod());

					porcInd = (float) (Math.rint(porcInd * 100.0F) / 100.0D);

					datosArray.setPorcIndicadorMod(porcInd);

					listadoIndicadorMod.add(datosArray);
				}

				datosForm.setListadoActMod(listadoIndicadorMod);
			} else {
				request.setAttribute("sinDatosMod", "No existen registros con los parametros especificados");
			}

			OCAPUsuariosOT datosArray2 = null;

			if ((datosEvaluadosMod.getTotalIndicadoresMod() != 0)
					&& (datosEvaluadosNoMod.getTotalIndicadoresNoMod() != 0)) {
				OCAPConfigApp.logger.info("Se han recuperado " + datosEvaluadosNoMod.getTotalEvaluadosNoMod()
						+ " Evaluados para la consulta No MOD");
				datosForm.setTotalEvaluadosMod(datosEvaluadosNoMod.getTotalEvaluadosNoMod());

				datosForm.setTotalIndicadoresNoModOK(datosEvaluadosNoMod.getTotalIndicadoresNoModOK());
				datosForm.setTotalIndicadoresNoModKO(datosEvaluadosNoMod.getTotalIndicadoresNoModKO());
				datosForm.setPorcIndicadoresNoModOK(datosEvaluadosNoMod.getPorcIndicadoresNoModOK());
				datosForm.setPorcIndicadoresNoModKO(datosEvaluadosNoMod.getPorcIndicadoresNoModKO());

				for (int i = 0; i < datosEvaluadosNoMod.getListaIndicadoresNoMod().size(); i++) {
					datosArray2 = (OCAPUsuariosOT) datosEvaluadosNoMod.getListaIndicadoresNoMod().get(i);

					porcInd = (float) (100.0D * datosArray.getTotalIndicadorNoMod()
							/ datosEvaluadosNoMod.getTotalEvaluadosNoMod());

					porcInd = (float) (Math.rint(porcInd * 100.0F) / 100.0D);

					datosArray2.setPorcIndicadorNoMod(porcInd);

					listadoIndicadorNoMod.add(datosArray2);
				}

				datosForm.setListadoActNoMod(listadoIndicadorNoMod);
			} else {
				request.setAttribute("sinDatosNoMod", "No existen registros con los parametros especificados");
			}

			OCAPConfigApp.logger.info("---------- FIN CONSULTA OCAPEvaluadores ------- ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			((OCAPComponentesfqsForm) form).setjspInicio(false);
			return mapping.findForward("error");
		}

		return mapping.findForward("irIndicadores");
	}

	public ActionForward buscarIndicadorProf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mensajeError = "Se ha producido un error";
		Logger log = null;
		ActionForward actionForward = null;
		Utilidades utilidades = new Utilidades();
		ActionMessages messages = new ActionMessages();
		OCAPUsuariosLN usuLN = null;
		OCAPIndicadoresLN indiLN = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorProf: Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;
			DecimalFormat formateador = new DecimalFormat("###.##%");

			usuLN = new OCAPUsuariosLN(jcylUsuario);
			ArrayList listaEvaluadosFaseIII = usuLN.listarEvaluados(new OCAPUsuariosOT(), jcylUsuario, 1, -1);
			int totalEvaluadosFaseIII = listaEvaluadosFaseIII == null ? 0 : listaEvaluadosFaseIII.size();
			formulario.setTotalEvaluadosEnFaseIII(totalEvaluadosFaseIII);

			indiLN = new OCAPIndicadoresLN(jcylUsuario);

			int contadorExcluidos = indiLN.contarEvaluadosExcluidos();
			formulario.setTotalEvaluadosExcluidos(contadorExcluidos);

			int contadorEvaluadosFQS = indiLN.contarEvaluadosFQS(null);
			int contadorEvaluadosFQS_OK = indiLN.contarEvaluadosAutoevaluacion(Constantes.SI, Constantes.SI);
			int contadorEvaluadosFQS_KO = indiLN.contarEvaluadosAutoevaluacion(Constantes.NO, Constantes.SI);
			formulario.setTotalEvaluados(contadorEvaluadosFQS);
			formulario.setTotalIndicadoresOK(contadorEvaluadosFQS_OK);
			formulario.setTotalIndicadoresKO(contadorEvaluadosFQS_KO);

			String porcEvaluadosFQS = "0%";
			String porcEvaluadosFQS_OK = "0%";
			String porcEvaluadosFQS_KO = "0%";
			if (totalEvaluadosFaseIII != 0)
				porcEvaluadosFQS = formateador.format(contadorEvaluadosFQS / totalEvaluadosFaseIII);
			if (contadorEvaluadosFQS != 0) {
				porcEvaluadosFQS_OK = formateador.format(contadorEvaluadosFQS_OK / contadorEvaluadosFQS);
				porcEvaluadosFQS_KO = formateador.format(contadorEvaluadosFQS_KO / contadorEvaluadosFQS);
			}
			formulario.setPorcEvaluadosFQS(porcEvaluadosFQS);
			formulario.setPorcEvaluadosFQSOK(porcEvaluadosFQS_OK);
			formulario.setPorcEvaluadosFQSKO(porcEvaluadosFQS_KO);

			int contadorEvaluadosGRS = indiLN.contarEvaluadosGRS(null);

			int contadorEvaluadosGRS_OK = indiLN.contarEvaluadosAutoevaluacion(Constantes.SI, Constantes.NO);
			int contadorEvaluadosGRS_KO = indiLN.contarEvaluadosAutoevaluacion(Constantes.NO, Constantes.NO);
			formulario.setTotalEvaluadosGRS(contadorEvaluadosGRS);
			formulario.setTotalIndicadorOK(contadorEvaluadosGRS_OK);
			formulario.setTotalIndicadorKO(contadorEvaluadosGRS_KO);

			String porcEvaluadosGRS = "0%";
			String porcEvaluadosGRS_OK = "0%";
			String porcEvaluadosGRS_KO = "0%";
			if (totalEvaluadosFaseIII != 0)
				porcEvaluadosGRS = formateador.format(contadorEvaluadosGRS / totalEvaluadosFaseIII);
			if (contadorEvaluadosGRS != 0) {
				porcEvaluadosGRS_OK = formateador.format(contadorEvaluadosGRS_OK / contadorEvaluadosGRS);
				porcEvaluadosGRS_KO = formateador.format(contadorEvaluadosGRS_KO / contadorEvaluadosGRS);
			}
			formulario.setPorcEvaluadosGRS(porcEvaluadosGRS);
			formulario.setPorcEvaluadosGRSOK(porcEvaluadosGRS_OK);
			formulario.setPorcEvaluadosGRSKO(porcEvaluadosGRS_KO);

			ArrayList listaCategoriasFQS = indiLN.buscarEvaluadosPorCategoria("evalfqs", contadorEvaluadosFQS);
			ArrayList listaCategoriasGRS = indiLN.buscarEvaluadosPorCategoria("gere", contadorEvaluadosGRS);
			formulario.setListaFQS(listaCategoriasFQS);
			formulario.setListaGRS(listaCategoriasGRS);

			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorProf: Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			((OCAPComponentesfqsForm) form).setjspInicio(false);
			return mapping.findForward("error");
		}

		return mapping.findForward("irIndicadoresProfesional");
	}

	public ActionForward buscarIndicadorEvaluador(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mensajeError = "Se ha producido un error";
		Logger log = null;
		ActionForward actionForward = null;
		Utilidades utilidades = new Utilidades();
		ActionMessages messages = new ActionMessages();
		OCAPUsuariosLN usuLN = null;
		OCAPIndicadoresLN indiLN = null;
		ArrayList listadoEvaluadores = null;
		OCAPUsuariosOT usuOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorEvaluador: Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;
			DecimalFormat formateador = new DecimalFormat("###.##%");
			usuLN = new OCAPUsuariosLN(jcylUsuario);
			indiLN = new OCAPIndicadoresLN(jcylUsuario);

			int contadorEvaluadores = indiLN.contarEvaluadoresFQS();
			formulario.setTotalEvaluadoresFQS(contadorEvaluadores);

			int contadorEvaluadoresBaja = indiLN.contarEvaluadoresBajaFQS();
			formulario.setTotalEvaluadoresBajaFQS(contadorEvaluadoresBaja);
			formulario.setPorcEvaluadoresBajaFQS(formateador.format(contadorEvaluadoresBaja / contadorEvaluadores));

			int contadorEvaluadoresFin = indiLN.contarEvaluadoresFinFQS();
			formulario.setTotalEvaluadoresFinFQS(contadorEvaluadoresFin);
			formulario.setPorcEvaluadoresFinFQS(formateador.format(contadorEvaluadoresFin / contadorEvaluadores));

			formulario.setListaCategorias(indiLN.buscarEvaluadoresPorCategoria(contadorEvaluadores));
			listadoEvaluadores = indiLN.buscarEvaluadosPorItinerarioPorEvaluador();
			formulario.setListaEvaluadores(listadoEvaluadores);
			int contConformes = 0;
			int contNoConformes = 0;
			int contAuditorias = 0;
			for (int i = 0; i < listadoEvaluadores.size(); i++) {
				usuOT = (OCAPUsuariosOT) listadoEvaluadores.get(i);
				contConformes += usuOT.getTotalEvaluadosMod();
				contNoConformes += usuOT.getTotalEvaluadosNoMod();
				contAuditorias += usuOT.getTotalEvaluados();
			}
			formulario.setTotalConformes(contConformes);
			formulario.setTotalNoConformes(contNoConformes);
			formulario.setTotalAuditorias(contAuditorias);

			formulario.setListaAuditorias(indiLN.buscarAuditoriasPorCategoria(contAuditorias));

			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorEvaluador: Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			((OCAPComponentesfqsForm) form).setjspInicio(false);
			return mapping.findForward("error");
		}

		return mapping.findForward("irIndicadoresEvaluador");
	}

	public ActionForward buscarIndicadorSecretaria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mensajeError = "Se ha producido un error";
		Logger log = null;
		ActionForward actionForward = null;
		ActionMessages messages = new ActionMessages();
		OCAPIndicadoresLN indiLN = null;
		ArrayList listadoEvaluadores = null;
		OCAPUsuariosOT usuOT = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorSecretaria: Inicio");
			ActionErrors errores = new ActionErrors();
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;
			DecimalFormat formateador = new DecimalFormat("###.##%");
			indiLN = new OCAPIndicadoresLN(jcylUsuario);
			formulario.setTotalIndicador(indiLN.contarInformesFQS());
			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorSecretaria: Fin ");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			((OCAPComponentesfqsForm) form).setjspInicio(false);
			return mapping.findForward("error");
		}

		return mapping.findForward("irIndicadoresSecretaria");
	}

	public ActionForward buscarIndicadorCTE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mensajeError = "Se ha producido un error";
		Logger log = null;
		ActionForward actionForward = null;
		Utilidades utilidades = new Utilidades();
		ActionMessages messages = new ActionMessages();
		OCAPUsuariosLN usuLN = null;
		OCAPIndicadoresLN indiLN = null;
		ArrayList listadoCTEs = null;
		ArrayList listadoCTEs2 = null;
		ArrayList listadoCTEsTotal = null;
		OCAPUsuariosOT usuOT = null;
		OCAPUsuariosOT usuOT2 = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorCTE: Inicio");
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;
			usuLN = new OCAPUsuariosLN(jcylUsuario);
			indiLN = new OCAPIndicadoresLN(jcylUsuario);

			listadoCTEs = indiLN.buscarEvaluadosPorCategoriaPorCTE();
			listadoCTEs2 = indiLN.buscarEvaluadosPorCTE();

			listadoCTEsTotal = new ArrayList();
			for (int i = 0; (i < listadoCTEs.size()) && (i < listadoCTEs2.size()); i++) {
				usuOT = (OCAPUsuariosOT) listadoCTEs.get(i);
				usuOT2 = (OCAPUsuariosOT) listadoCTEs2.get(i);
				usuOT2.setListaCategorias(usuOT.getListaCategorias());

				listadoCTEsTotal.add(usuOT2);
			}
			formulario.setListaCTE2(listadoCTEsTotal);

			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorCTE: Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			((OCAPComponentesfqsForm) form).setjspInicio(false);
			return mapping.findForward("error");
		}

		return mapping.findForward("irIndicadoresCTE");
	}

	public ActionForward buscarIndicadorCE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String mensajeError = "Se ha producido un error";
		ActionMessages messages = new ActionMessages();
		OCAPIndicadoresLN indiLN = null;
		ArrayList listadoEvaluadores = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorCE: Inicio");
			HttpSession session = request.getSession();
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute("JCYLUsuario");
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;
			DecimalFormat formateador = new DecimalFormat("###.##%");
			indiLN = new OCAPIndicadoresLN(jcylUsuario);

			int contadorInformes = indiLN.contarInformesCE(null);
			int contadorInformesConforme = indiLN.contarInformesCE("C");
			int contadorInformesNoConforme = indiLN.contarInformesCE(Constantes.NO);
			formulario.setTotalIndicadores(contadorInformes);
			formulario.setTotalConformes(contadorInformesConforme);
			formulario.setTotalNoConformes(contadorInformesNoConforme);
			formulario.setPorcConformes(formateador.format(contadorInformesConforme / contadorInformes));
			formulario.setPorcNoConformes(formateador.format(contadorInformesNoConforme / contadorInformes));

			formulario.setListaCTE(indiLN.buscarEvaluadosPorCE(contadorInformesConforme, "C"));
			formulario.setListaCTE2(indiLN.buscarEvaluadosPorCE(contadorInformesNoConforme, Constantes.NO));

			OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadorCE: Fin");
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			request.setAttribute("mensaje", mensajeError);
			((OCAPComponentesfqsForm) form).setjspInicio(false);
			return mapping.findForward("error");
		}

		return mapping.findForward("irIndicadoresCE");
	}
}

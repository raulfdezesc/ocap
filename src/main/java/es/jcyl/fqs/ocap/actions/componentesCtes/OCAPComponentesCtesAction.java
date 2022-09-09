package es.jcyl.fqs.ocap.actions.componentesCtes;

import es.jcyl.fqs.ocap.actionforms.componentesfqs.OCAPComponentesfqsForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.categProfesionales.OCAPCategProfesionalesLN;
import es.jcyl.fqs.ocap.ln.componentesCtes.OCAPComponentesCtesLN;
import es.jcyl.fqs.ocap.ln.comunidades.OCAPComunidadesLN;
import es.jcyl.fqs.ocap.ln.evaluadores.OCAPEvaluadoresLN;
import es.jcyl.fqs.ocap.ln.gestionCtes.OCAPGestionCtesLN;
import es.jcyl.fqs.ocap.ln.localidades.OCAPLocalidadesLN;
import es.jcyl.fqs.ocap.ln.provincias.OCAPProvinciasLN;
import es.jcyl.fqs.ocap.ot.componentesfqs.OCAPComponentesfqsOT;
import es.jcyl.fqs.ocap.ot.gestionCtes.OCAPGestionCtesOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Pagina;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class OCAPComponentesCtesAction extends OCAPGenericAction {
	public ActionForward irInsertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		ArrayList listadoCargos = null;
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Inicio");
			validarAccion(mapping, form, request, response);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			request.setAttribute("opcion", request.getParameter("opcion"));

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPComunidadesLN comunidadesLN = new OCAPComunidadesLN(jcylUsuario);
			Collection listadoComu = comunidadesLN.listarComunidades();
			Object[] listadoComunidades = listadoComu.toArray();

			if (request.getParameter("opcion").equals("CTE")) {
				OCAPGestionCtesLN ctesLN = new OCAPGestionCtesLN(jcylUsuario);
				Collection listadoCte = ctesLN.consultaOCAPCtesAct();
				Object[] listadoCtes = listadoCte.toArray();

				session.setAttribute(Constantes.COMBO_CTES, utilidades.ArrayObjectToArrayList(listadoCtes));
			}

			OCAPComponentesCtesLN componentesLN = new OCAPComponentesCtesLN(jcylUsuario);
			listadoCargos = componentesLN.listadoCargos();

			session.setAttribute(Constantes.COMBO_COMUNIDADES, utilidades.ArrayObjectToArrayList(listadoComunidades));
			session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
			session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			session.setAttribute(Constantes.COMBO_CARGOS, listadoCargos);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irInsertar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward insertar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- ALTA Componente CTE --------- ");
			validarAccion(mapping, form, request, response);

			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			String opcion = request.getParameter("opcion");

			request.setAttribute("opcion", opcion);

			messages = validar(form, request, opcion);

			sig = "irInsertar";

			if ((messages == null) || (messages.isEmpty())) {
				OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
				OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();

				OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;
				OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

				componente.setDNombre(formulario.getDNombre());
				componente.setDApellidos(formulario.getDApellidos());
				componente.setCDni(formulario.getCDni());
				if ((formulario.getBSexo() != null) && (!formulario.getBSexo().equals(""))) {
					componente.setBSexo(formulario.getBSexo());
				}
				if ((formulario.getNTelefono1() != null) && (!formulario.getNTelefono1().equals(""))) {
					componente.setNTelefono1(formulario.getNTelefono1());
				}
				if ((formulario.getACorreoelectronico() != null) && (!formulario.getACorreoelectronico().equals(""))) {
					componente.setACorreoelectronico(formulario.getACorreoelectronico());
				}

				if ((formulario.getADir_nombre() != null) && (!formulario.getADir_nombre().equals(""))) {
					componente.setADirNombre(formulario.getADir_nombre());
				}

				if ((formulario.getADir_num() != null) && (!formulario.getADir_num().equals(""))) {
					componente.setADirNum(formulario.getADir_num());
				}

				if ((formulario.getADir_escalera() != null) && (!formulario.getADir_escalera().equals(""))) {
					componente.setADirEscalera(formulario.getADir_escalera());
				}

				if ((formulario.getADir_piso() != null) && (!formulario.getADir_piso().equals(""))) {
					componente.setADirPiso(formulario.getADir_piso());
				}

				if ((formulario.getADir_letra() != null) && (!formulario.getADir_letra().equals(""))) {
					componente.setADirLetra(formulario.getADir_letra());
				}

				if ((formulario.getCCp() != null) && (!formulario.getCCp().equals(""))) {
					componente.setCCp(formulario.getCCp());
				}

				if ((formulario.getCComunidad_id() != null) && (!formulario.getCComunidad_id().equals(""))) {
					componente.setCComunidadId(formulario.getCComunidad_id());
				}

				if ((formulario.getCProv_id() != null) && (!formulario.getCProv_id().equals(""))) {
					componente.setCProvId(formulario.getCProv_id());
				}

				if ((formulario.getCLocalidad_id() != null) && (!formulario.getCLocalidad_id().equals(""))) {
					componente.setCLocalidadId(formulario.getCLocalidad_id());
				}

				if ((formulario.getACategoria() != null) && (!formulario.getACategoria().equals(""))) {
					componente.setACategoria(formulario.getACategoria());
				}

				if ((formulario.getADatosprofesionales() != null)
						&& (!formulario.getADatosprofesionales().equals(""))) {
					componente.setADatosprofesionales(formulario.getADatosprofesionales());
				}

				componente.setCCteId(formulario.getCCte_id());
				componente.setACargo(formulario.getACargo());

				componente.setFVinculacion(formulario.getFVinculacion());
				componente.setFFinalizacion(formulario.getFFinalizacion());

				componente.setaCreadoPorComponente(
						((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
				componente.setACreadoPorExp(componente.getaCreadoPorComponente());

				int result = oCAPComponentesCtesLN.altaOCAPComponentesCtes(componente, opcion);

				if (result == 1) {
					request.setAttribute("mensaje", "El registro se ha insertado con éxito en el sistema ");
					request.setAttribute("rutaVuelta", "OCAPComponentesCtes.do?accion=irInsertar&opcion=" + opcion);
					sig = "exito";
				} else {
					request.setAttribute("mensaje", "Se ha producido un error al insertar el registro");
					sig = "error";
				}

				OCAPConfigApp.logger.info("---------- FIN GRABAR ALTA Componente Cte--------- ");
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			if (ex.getMessage().startsWith("ORA-00001"))
				messages.add("Componente", new ActionMessage("registro.duplicados", "Componente de Cte"));
			else {
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(ex, "error.general")));
			}
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward("exito");
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);
		request.setAttribute("tipoAccion", Constantes.INSERTAR);
		request.setAttribute("opcion", request.getParameter("opcion"));

		return mapping.findForward(sig);
	}

	public ActionForward listarComponentes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- Listar componentes de Cte --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
			OCAPGestionCtesLN oCAPGestionCtesLN = new OCAPGestionCtesLN(jcylUsuario);
			OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();

			int primerRegistro = 1;
			int registrosPorPagina = 10;

			String s = null;
			s = request.getParameter("iRegistro");
			if (s != null)
				primerRegistro = Integer.parseInt(s);
			s = request.getParameter("nRegistrosP");
			if (s != null) {
				registrosPorPagina = Integer.parseInt(s);
			}
			String cCteIdS = request.getParameter("cCteIdS");
			long cCteId;
			if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
				cCteId = Long.parseLong(cCteIdS);
				OCAPConfigApp.logger.info("cCteId: " + cCteId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			datos.setCCteId(cCteId);
			Collection elementos = oCAPComponentesCtesLN.listarComponentes(datos, "CTE", primerRegistro,
					registrosPorPagina);
			
			try{
				OCAPGestionCtesOT elemento = oCAPGestionCtesLN.buscarOCAPCte(cCteId);
				if(elemento != null && elemento.getDNombre() != null && !elemento.getDNombre().equals("")){
					request.setAttribute("Dnombre", elemento.getDNombre());
				}else{
					request.setAttribute("Dnombre", request.getParameter("nombre"));
				}
			}catch (Exception e){
				OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
				request.setAttribute("Dnombre", request.getParameter("nombre"));
			}

			if (elementos != null) {
				OCAPConfigApp.logger.info("Se han recuperado " + elementos.size() + " Evaluadores para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.getSession().removeAttribute("listados");
				} else {
					int nListado = 0;
					nListado = oCAPComponentesCtesLN.listarComponentesCuenta(datos, "CTE");

					session.setAttribute("numComponentes", new Integer(nListado));
					request.removeAttribute("sinDatos");
					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setRegistroActual(primerRegistro);
					pagina.setNRegistros(((Integer) session.getAttribute("numComponentes")).intValue());
					pagina.setRegistrosPorPagina(registrosPorPagina);

					request.setAttribute("listados", pagina);
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}

			OCAPConfigApp.logger.info("...........se sale del Action");

			request.setAttribute("nombre", request.getParameter("nombre"));
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irComponentes");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irEditar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irEditar: Inicio");
			HttpSession session = request.getSession();
			Utilidades utilidades = new Utilidades();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
			OCAPComponentesfqsOT datos = new OCAPComponentesfqsOT();
			OCAPComponentesfqsOT entrada = new OCAPComponentesfqsOT();
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;

			String cCompfqsIdS = request.getParameter("cCompfqsIdS");
			long cCompfqsId;
			if ((cCompfqsIdS != null) && (!cCompfqsIdS.equals(""))) {
				cCompfqsId = Long.parseLong(cCompfqsIdS);
				OCAPConfigApp.logger.info("cCompfqsId: " + cCompfqsId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			entrada.setCCompfqsId(cCompfqsId);
			datos = oCAPComponentesCtesLN.buscarOCAPComponentesCtes(entrada);

			if (datos.getCCompfqsId() == 0L) {
				OCAPConfigApp.logger.info("No encontramos OCAPComponentesfqsOT");
				sig = "error";
				request.setAttribute("mensaje", "No se encuentra el registro");
			} else {
				OCAPConfigApp.logger.info("Se ha encontrado OCAPComponentesfqsOT");
				request.setAttribute("OCAPComponentesfqsOT", datos);

				OCAPCategProfesionalesLN categProfesionalesLN = new OCAPCategProfesionalesLN(jcylUsuario);
				Collection listadoProf = categProfesionalesLN.listarCategoriasProfesionales();
				Object[] listadoProfesional = listadoProf.toArray();

				OCAPComunidadesLN comunidadesLN = new OCAPComunidadesLN(jcylUsuario);
				Collection listadoComu = comunidadesLN.listarComunidades();
				Object[] listadoComunidades = listadoComu.toArray();

				session.setAttribute(Constantes.COMBO_COMUNIDADES, utilidades.ArrayObjectToArrayList(listadoComunidades));

				if ((datos.getCComunidadId() != null) && (!datos.getCComunidadId().equals(""))) {
					OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
					Collection listadoProv = provinciasLN.listarProvinciasComunidad(datos.getCComunidadId());
					Object[] listadoProvincias = listadoProv.toArray();

					session.setAttribute(Constantes.COMBO_PROVINCIAS, utilidades.ArrayObjectToArrayList(listadoProvincias));
				} else {
					session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
				}

				OCAPGestionCtesLN ctesLN = new OCAPGestionCtesLN(jcylUsuario);
				Collection listadoCte = ctesLN.consultaOCAPCtesAct();
				Object[] listadoCtes = listadoCte.toArray();

				OCAPComponentesCtesLN componentesLN = new OCAPComponentesCtesLN(jcylUsuario);
				Collection listadoCarg = componentesLN.listadoCargos();
				Object[] listadoCargos = listadoCarg.toArray();

				session.setAttribute(Constantes.COMBO_PROFESIONAL, utilidades.ArrayObjectToArrayList(listadoProfesional));
				session.setAttribute(Constantes.COMBO_CTES, utilidades.ArrayObjectToArrayList(listadoCtes));
				session.setAttribute(Constantes.COMBO_CARGOS, listadoCargos);

				if ((datos.getCProvId() != null) && (!datos.getCProvId().equals(""))) {
					OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
					Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(datos.getCProvId());
					Object[] listadoLocalidades = listadoLocal.toArray();

					session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
				} else {
					session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
				}

				formulario.setCCompfqs_id(datos.getCCompfqsId());
				formulario.setDNombre(datos.getDNombre());
				formulario.setDApellidos(datos.getDApellidos());
				formulario.setCDni(datos.getCDni());
				formulario.setBSexo(datos.getBSexo());
				formulario.setNTelefono1(datos.getNTelefono1());
				formulario.setACorreoelectronico(datos.getACorreoelectronico());

				formulario.setADir_nombre(datos.getADirNombre());
				formulario.setADir_num(datos.getADirNum());
				formulario.setADir_escalera(datos.getADirEscalera());
				formulario.setADir_piso(datos.getADirPiso());
				formulario.setADir_letra(datos.getADirLetra());
				formulario.setCCp(datos.getCCp());

				formulario.setCComunidad_id(datos.getCComunidadId());
				formulario.setCProv_id(datos.getCProvId());
				formulario.setCLocalidad_id(datos.getCLocalidadId());

				formulario.setACategoria(datos.getACategoria());
				formulario.setADatosprofesionales(datos.getADatosprofesionales());

				formulario.setCCte_id(datos.getCCteId());
				formulario.setACargo(datos.getACargo());
				formulario.setCCte_id_ant(datos.getCCteId());
				formulario.setFVinculacion(datos.getFVinculacion());
				formulario.setFFinalizacion(datos.getFFinalizacion());

				formulario.setCCompfqs_convo_id(datos.getCCompfqsConvoId());

				OCAPConfigApp.logger.info("Se ha encontrado OCAPComponentesCtes");
				sig = "irModificar";
				request.setAttribute("tipoAccion", Constantes.MODIFICAR);

				request.setAttribute("opcion", "CTE");

				request.setAttribute("nombre", request.getParameter("nombre"));
			}

			OCAPConfigApp.logger.info("...........se sale del Action");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertar: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward grabar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		String opcion = null;
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- GRABAR Componente --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
			OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;

			if (formulario.getCCompfqs_id() != 0L) {
				OCAPConfigApp.logger.info("cCompfqsId: " + formulario.getCCompfqs_id());
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			componente.setCCompfqsId(formulario.getCCompfqs_id());
			OCAPConfigApp.logger.info("Se recuperan datos de la jsp");

			opcion = request.getParameter("opcion");

			messages = validar(form, request, opcion);

			if ((messages == null) || (messages.isEmpty())) {
				componente.setCCompfqsConvoId(formulario.getCCompfqs_convo_id());
				componente.setDNombre(formulario.getDNombre());
				componente.setDApellidos(formulario.getDApellidos());
				componente.setCDni(formulario.getCDni());

				if ((formulario.getNTelefono1() != null) && (!formulario.getNTelefono1().equals(""))) {
					componente.setNTelefono1(formulario.getNTelefono1());
				}

				if ((formulario.getACorreoelectronico() != null) && (!formulario.getACorreoelectronico().equals(""))) {
					componente.setACorreoelectronico(formulario.getACorreoelectronico());
				}

				if ((formulario.getADir_nombre() != null) && (!formulario.getADir_nombre().equals(""))) {
					componente.setADirNombre(formulario.getADir_nombre());
				}

				if ((formulario.getADir_num() != null) && (!formulario.getADir_num().equals(""))) {
					componente.setADirNum(formulario.getADir_num());
				}

				if ((formulario.getADir_escalera() != null) && (!formulario.getADir_escalera().equals(""))) {
					componente.setADirEscalera(formulario.getADir_escalera());
				}

				if ((formulario.getADir_piso() != null) && (!formulario.getADir_piso().equals(""))) {
					componente.setADirPiso(formulario.getADir_piso());
				}

				if ((formulario.getADir_letra() != null) && (!formulario.getADir_letra().equals(""))) {
					componente.setADirLetra(formulario.getADir_letra());
				}

				if ((formulario.getCCp() != null) && (!formulario.getCCp().equals(""))) {
					componente.setCCp(formulario.getCCp());
				}

				if ((formulario.getCComunidad_id() != null) && (!formulario.getCComunidad_id().equals(""))) {
					componente.setCComunidadId(formulario.getCComunidad_id());
				}

				if ((formulario.getCProv_id() != null) && (!formulario.getCProv_id().equals(""))) {
					componente.setCProvId(formulario.getCProv_id());
				}

				if ((formulario.getCLocalidad_id() != null) && (!formulario.getCLocalidad_id().equals(""))) {
					componente.setCLocalidadId(formulario.getCLocalidad_id());
				}

				if ((formulario.getCLocalidad_id() != null) && (!formulario.getCLocalidad_id().equals(""))) {
					componente.setACategoria(formulario.getACategoria());
				}

				if ((formulario.getADatosprofesionales() != null)
						&& (!formulario.getADatosprofesionales().equals(""))) {
					componente.setADatosprofesionales(formulario.getADatosprofesionales());
				}

				componente.setCCteId(formulario.getCCte_id());
				componente.setACargo(formulario.getACargo());
				componente.setFVinculacion(formulario.getFVinculacion());
				componente.setFFinalizacion(formulario.getFFinalizacion());

				componente.setAModificadoPorComponente(
						((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
				componente.setAModificadoPorExp(componente.getAModificadoPorComponente());

				int result = oCAPComponentesCtesLN.modificacionOCAPComponentesCtes(componente);

				if (result == 1) {
					request.setAttribute("mensaje", "El registro se ha modificado con éxito");
					request.setAttribute("rutaVuelta", "OCAPComponentesCtes.do?accion=listarComponentes&cCteIdS="
							+ formulario.getCCte_id_ant() + "&nombre=" + request.getParameter("nombre"));
					sig = "exito";
				} else {
					request.setAttribute("mensaje", "Se ha producido un error al modificar el registro");
					sig = "error";
				}
				OCAPConfigApp.logger.info("---------- FIN GRABAR MODIFICACION Componentes --------- ");
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			if (ex.getMessage().startsWith("ORA-00001"))
				messages.add("Componente", new ActionMessage("registro.duplicados", "Componente de Cte"));
			else {
				errors.add("org.apache.struts.action.GLOBAL_ERROR",
						new ActionError(TrataError.tratarError(ex, "error.general")));
			}
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward(sig);
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);

		request.setAttribute("opcion", opcion);
		request.setAttribute("tipoAccion", Constantes.MODIFICAR);
		request.setAttribute("nombre", request.getParameter("nombre"));

		return mapping.findForward("irModificar");
	}

	public ActionMessages validar(ActionForm form, HttpServletRequest request, String opcion) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFVincula = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPComponentesfqsForm) form).getDNombre() == null)
					|| (((OCAPComponentesfqsForm) form).getDNombre().equals(""))) {
				messages.add("dNombre", new ActionMessage("errors.required", "Nombre"));
			}

			if ((((OCAPComponentesfqsForm) form).getDApellidos() == null)
					|| (((OCAPComponentesfqsForm) form).getDApellidos().equals(""))) {
				messages.add("dApellidos", new ActionMessage("errors.required", "Apellidos"));
			}

			if ((((OCAPComponentesfqsForm) form).getCDni() == null)
					|| (((OCAPComponentesfqsForm) form).getCDni().equals("")))
				messages.add("cDni", new ActionMessage("errors.required", "NIF/NIE"));
			else if (!Utilidades.esDNI(((OCAPComponentesfqsForm) form).getCDni())) {
				messages.add("cDni", new ActionMessage("error.maskmsg", "NIF/NIE"));
			}

			if ((opcion.equals("CTE")) && (((OCAPComponentesfqsForm) form).getCCte_id() == 0L)) {
				messages.add("cCteId", new ActionMessage("errors.required", "Comité"));
			}

			if ((((OCAPComponentesfqsForm) form).getACargo() == null)
					|| (((OCAPComponentesfqsForm) form).getACargo().equals(""))) {
				if (opcion.equals("CTE")) {
					messages.add("aCargo", new ActionMessage("errors.required", "Cargo dentro del CTE"));
				} else {
					messages.add("aCargo", new ActionMessage("errors.required", "Cargo dentro del CE"));
				}
			}

			if ((((OCAPComponentesfqsForm) form).getFVinculacion() == null)
					|| (((OCAPComponentesfqsForm) form).getFVinculacion().equals(""))) {
				messages.add("fVinculacion", new ActionMessage("errors.required", "Fecha Vinculación"));
			} else if (((OCAPComponentesfqsForm) form).getFVinculacion().length() < 10) {
				messages.add("fVinculacion", new ActionMessage("error.maskmsg", "Fecha Vinculación"));
			} else if (!((OCAPComponentesfqsForm) form).getFVinculacion().matches("\\d{2}/\\d{2}/\\d{4}")) {
				messages.add("fVinculacion", new ActionMessage("error.maskmsg", "Fecha Vinculación"));
			} else {
				dFVincula = df.parse(((OCAPComponentesfqsForm) form).getFVinculacion(), pos);
				if (dFVincula == null) {
					messages.add("fVinculacion", new ActionMessage("error.maskmsg", "Fecha Vinculación"));
				}

			}

		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " validar: ERROR: " + e.getMessage());
			throw e;
		}

		return messages;
	}

	public ActionForward cargarComboLocalidades(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		String sig = "error";
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboEspecialidades: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;

			if ((formulario.getCProv_id() != null) && (!formulario.getCProv_id().equals(""))) {
				OCAPLocalidadesLN localidadesLN = new OCAPLocalidadesLN(jcylUsuario);
				Collection listadoLocal = localidadesLN.listarLocalidadesProvincia(formulario.getCProv_id());
				Object[] listadoLocalidades = listadoLocal.toArray();

				session.setAttribute(Constantes.COMBO_LOCALIDADES, utilidades.ArrayObjectToArrayList(listadoLocalidades));
			} else {
				session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());
			}

			if (request.getParameter("jspVuelta").equals(Constantes.INSERTAR)) {
				request.setAttribute("tipoAccion", Constantes.INSERTAR);
			} else {
				request.setAttribute("tipoAccion", Constantes.MODIFICAR);
				request.setAttribute("nombre", request.getParameter("nombre"));
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("tarea", request.getParameter("tarea"));

			sig = "irInsertar";

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboLocalidades: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboLocalidades: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward irBorrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		Collection convocatorias = null;
		try {
			OCAPConfigApp.logger.info("Inicio");
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;
			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);

			String cCompfqsIdS = request.getParameter("cCompfqsIdS");
			int cCompfqsId;
			if ((cCompfqsIdS != null) && (!cCompfqsIdS.equals(""))) {
				cCompfqsId = Integer.parseInt(cCompfqsIdS);
				OCAPConfigApp.logger.info("cCompfqsId: " + cCompfqsId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();
			componente.setCCompfqsId(cCompfqsId);
			convocatorias = oCAPComponentesCtesLN.listarComponentes(componente, request.getParameter("opcion"), 1, -1);

			if (convocatorias.size() != 0) {
				request.setAttribute("convocatorias", Constantes.SI);
			}
			request.setAttribute("opcion", request.getParameter("opcion"));

			formulario.setCCompfqs_id(cCompfqsId);
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irBorrar: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			request.setAttribute("nombre", request.getParameter("nombre"));

			return mapping.findForward("irBorrar");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward borrar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String sig = "error";
		Logger log = null;
		try {
			OCAPConfigApp.logger.info("");
			OCAPConfigApp.logger.info("---------- BAJA DE COMPONENTE DE CTE --------- ");
			HttpSession session = request.getSession();

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
			OCAPComponentesfqsOT dato = new OCAPComponentesfqsOT();
			String cCompfqsIdS = request.getParameter("cCompfqsIdS");
			int cCompfqsId;
			if ((cCompfqsIdS != null) && (!cCompfqsIdS.equals(""))) {
				cCompfqsId = Integer.parseInt(cCompfqsIdS);
				OCAPConfigApp.logger.info("cCompfqsId: " + cCompfqsId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}
			dato.setCCompfqsId(cCompfqsId);
			dato.setAModificadoPorComponente(
					((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());
			dato.setAModificadoPorExp(dato.getAModificadoPorComponente());

			int result = oCAPComponentesCtesLN.bajaOCAPComponentesCtes(dato);

			if (result == 0) {
				OCAPConfigApp.logger.info("Se ha producido un error al borrar el registro");
				sig = "error";
				request.setAttribute("mensaje", "Se ha producido un error al borrar el registro");
			} else {
				OCAPConfigApp.logger.info("El registro se ha eliminado con éxito");
				request.setAttribute("mensaje", "El registro se ha eliminado con éxito");
				request.setAttribute("rutaVuelta", "OCAPGestionCtes.do?accion=buscar");

				sig = "exito";
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			sig = "error";
			request.setAttribute("mensaje", "Se ha producido un error");
		}

		return mapping.findForward(sig);
	}

	public ActionForward irListarEval(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		ActionMessages messages = new ActionMessages();
		String opcion = null;
		String mensajeError = "Se ha producido un error";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " irListarEval: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);

			OCAPComponentesfqsOT componente = new OCAPComponentesfqsOT();

			if ((jcylUsuario.getUser().getRol() != null) && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CTE))) {
				opcion = "CTE";
				componente.setCDni(
						((JCYLUsuario) request.getSession().getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO)).getUsuario().getC_usr_id());

				componente = oCAPComponentesCtesLN.buscarOCAPComponentesCtes(componente);

				if (componente.getCCteId() == 0L) {
					messages.add("cCompfqsId", new ActionMessage("errors.componenteNoExiste"));
					mensajeError = "Componente de CTE no dado de alta.";
				}

			}

			request.setAttribute("opcion", opcion);

			if ((messages == null) || (messages.isEmpty())) {
				((OCAPComponentesfqsForm) form).setCCte_id(componente.getCCteId());

				((OCAPComponentesfqsForm) form).setjspInicio(false);

				if ((!Cadenas.EsVacia(request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR)))
						&& (request.getParameter(Constantes.RECUPERAR_BUSQUEDA_ANTERIOR).equals(Constantes.SI))) {
					form = (OCAPComponentesfqsForm) request.getSession().getAttribute("OCAPComponentesfqsFormBuscador");
					request.setAttribute("OCAPComponentesfqsForm", form);
				} else {
					request.getSession().setAttribute("OCAPComponentesfqsFormBuscador", form);
					session.setAttribute("iRegistro", 1 + "");
				}

				int primerRegistro = 1;
				int registrosPorPagina = 10;

				String s = null;
				s = request.getParameter("iRegistro");
				if (s != null)
					primerRegistro = Integer.parseInt(s);
				s = request.getParameter("nRegistrosP");
				if (s != null) {
					registrosPorPagina = Integer.parseInt(s);
				}
				OCAPEvaluadoresLN oCAPEvaluadoresLN = new OCAPEvaluadoresLN(jcylUsuario);
				Collection elementos = oCAPEvaluadoresLN.consultaOCAPEvaluadores(componente, opcion, primerRegistro,
						registrosPorPagina);

				if (elementos != null) {
					OCAPConfigApp.logger
							.info("Se han recuperado " + elementos.size() + " Evaluadores para la consulta");
					if (elementos.size() == 0) {
						request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
						request.getSession().removeAttribute("listados");
					} else {
						int nListado = 0;
						nListado = oCAPEvaluadoresLN.listadoOCAPEvaluadoresCuenta(componente, opcion);

						session.setAttribute("numEvaluadores", new Integer(nListado));
						request.removeAttribute("sinDatos");
						Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
						pagina.setElementos(elementos);
						pagina.setRegistroActual(primerRegistro);
						pagina.setNRegistros(((Integer) session.getAttribute("numEvaluadores")).intValue());
						pagina.setRegistrosPorPagina(registrosPorPagina);
						request.setAttribute("listados", pagina);
						request.removeAttribute("menu");
						request.getSession().removeAttribute("menu");
					}
				} else {
					request.setAttribute("errorConsultando", "Error consultando en la base de datos");
				}
			}

			OCAPConfigApp.logger.info(getClass().getName() + " irListarEval: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " irListar: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty()))) {
			return mapping.findForward("irListarEval");
		}
		if ((errors != null) && (!errors.isEmpty())) {
			saveErrors(request, errors);

			return mapping.findForward("fallo");
		}
		saveMessages(request, messages);
		request.setAttribute("mensaje", mensajeError);

		return mapping.findForward("error");
	}

	public ActionForward cargarComboProvincias(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		Utilidades utilidades = new Utilidades();
		String sig = "";
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboProvincias: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;

			if ((formulario.getCComunidad_id() != null) && (!formulario.getCComunidad_id().equals(""))) {
				OCAPProvinciasLN provinciasLN = new OCAPProvinciasLN(jcylUsuario);
				Collection listadoProv = provinciasLN.listarProvinciasComunidad(formulario.getCComunidad_id());
				Object[] listadoProvincias = listadoProv.toArray();

				session.setAttribute(Constantes.COMBO_PROVINCIAS, utilidades.ArrayObjectToArrayList(listadoProvincias));
			} else {
				session.setAttribute(Constantes.COMBO_PROVINCIAS, new ArrayList());
			}

			session.setAttribute(Constantes.COMBO_LOCALIDADES, new ArrayList());

			if (request.getParameter("jspVuelta").equals(Constantes.INSERTAR)) {
				request.setAttribute("tipoAccion", Constantes.INSERTAR);
			} else {
				request.setAttribute("tipoAccion", Constantes.MODIFICAR);
				request.setAttribute("nombre", request.getParameter("nombre"));
			}

			request.setAttribute("opcion", request.getParameter("opcion"));
			request.setAttribute("tarea", request.getParameter("tarea"));

			sig = "irInsertar";

			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboProvincias: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " cargarComboProvincias: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward(sig);
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}

	public ActionForward listarEvaluados(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			OCAPConfigApp.logger.info(getClass().getName() + " listarEvaluados: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPComponentesCtesLN oCAPComponentesCtesLN = new OCAPComponentesCtesLN(jcylUsuario);
			int primerRegistro = 1;
			int registrosPorPagina = 10;

			String s = null;
			s = request.getParameter("iRegistro");
			if (s != null)
				primerRegistro = Integer.parseInt(s);
			s = request.getParameter("nRegistrosP");
			if (s != null) {
				registrosPorPagina = Integer.parseInt(s);
			}
			OCAPComponentesfqsForm formulario = (OCAPComponentesfqsForm) form;

			if (request.getParameter("nombre") != null)
				session.setAttribute("nombreCTE", request.getParameter("nombre"));
			if (request.getParameter("cCteIdS") != null) {
				session.setAttribute("cCteIdS", request.getParameter("cCteIdS"));
			}

			formulario.setDNombreCte((String) (request.getParameter("nombre") == null
					? session.getAttribute("nombreCTE") : request.getParameter("nombre")));

			String cCteIdS = (String) (request.getParameter("cCteIdS") == null ? session.getAttribute("cCteIdS")
					: request.getParameter("cCteIdS"));
			long cCteId;
			if ((cCteIdS != null) && (!cCteIdS.equals(""))) {
				cCteId = Long.parseLong(cCteIdS);
				OCAPConfigApp.logger.info("cCteId: " + cCteId);
			} else {
				request.setAttribute("mensaje", "Se ha producido un error en la aplicación");
				return mapping.findForward("error");
			}

			Collection elementos = oCAPComponentesCtesLN.listarEvaluados(cCteId, primerRegistro, registrosPorPagina);

			if (elementos != null) {
				OCAPConfigApp.logger
						.info("Se han recuperado " + elementos.size() + " Evaluadores/evaluados para la consulta");
				if (elementos.size() == 0) {
					request.setAttribute("sinDatos", "No existen registros con los parametros especificados");
					request.getSession().removeAttribute("listados");
				} else {
					int numRegistros = oCAPComponentesCtesLN.contarEvaluados(cCteId);

					session.setAttribute("numRegistros", new Integer(numRegistros));

					Pagina pagina = new Pagina(mapping.getPath() + ".do", request);
					pagina.setElementos(elementos);
					pagina.setNRegistros(((Integer) session.getAttribute("numRegistros")).intValue());
					pagina.setRegistroActual(primerRegistro);
					pagina.setRegistrosPorPagina(registrosPorPagina);

					request.setAttribute("listados", pagina);
				}
			} else {
				request.setAttribute("errorConsultando", "Error consultando en la base de datos");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " listarEvaluados: Fin");
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " listarEvaluados: ERROR: " + e.getMessage());
			errors.add("org.apache.struts.action.GLOBAL_ERROR",
					new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if ((errors == null) || (errors.isEmpty())) {
			return mapping.findForward("irListarEvaluadoresEvaluados");
		}
		saveErrors(request, errors);

		return mapping.findForward("fallo");
	}
}

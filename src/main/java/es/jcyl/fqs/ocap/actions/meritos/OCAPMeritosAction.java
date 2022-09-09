package es.jcyl.fqs.ocap.actions.meritos;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.jcyl.fqs.ocap.actionforms.meritos.OCAPMeritosForm;
import es.jcyl.fqs.ocap.actions.OCAPGenericAction;
import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.convocatorias.OCAPConvocatoriasLN;
import es.jcyl.fqs.ocap.ln.expedienteCarrera.OCAPExpedienteCarreraLN;
import es.jcyl.fqs.ocap.ln.expedienteMC.OCAPExpedientemcLN;
import es.jcyl.fqs.ocap.ln.factoresImpacto.OCAPFactoresImpactoLN;
import es.jcyl.fqs.ocap.ln.gerencias.OCAPGerenciasLN;
import es.jcyl.fqs.ocap.ln.meritos.OCAPMeritosLN;
import es.jcyl.fqs.ocap.ln.meritosActividad.OCAPMeractividadLN;
import es.jcyl.fqs.ocap.ln.meritosCurriculares.OCAPMeritoscurricularesLN;
import es.jcyl.fqs.ocap.ln.meritosModalidad.OCAPMerModalidadLN;
import es.jcyl.fqs.ocap.ln.modalidadAnterior.OCAPModalidadAnteriorLN;
import es.jcyl.fqs.ocap.ln.tiposFirmante.OCAPTiposFirmanteLN;
import es.jcyl.fqs.ocap.ln.usuarios.OCAPUsuariosLN;
import es.jcyl.fqs.ocap.ot.convocatorias.OCAPConvocatoriasOT;
import es.jcyl.fqs.ocap.ot.expedienteCarrera.OCAPExpedientecarreraOT;
import es.jcyl.fqs.ocap.ot.expedienteMC.OCAPExpedientemcOT;
import es.jcyl.fqs.ocap.ot.gerencias.OCAPGerenciasOT;
import es.jcyl.fqs.ocap.ot.meritosActividad.OCAPMeractividadOT;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Cadenas;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.TrataError;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLConfiguracion;
import es.jcyl.framework.JCYLUsuario;

public class OCAPMeritosAction extends OCAPGenericAction
{
	
	
	 public static String FIN_ANNIO = "31/12/";
	 
	 
	public ActionForward controlAccionMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMaster: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMaster: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMaster(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMaster(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMaster: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMaster: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMaster".equals(vuelta.getName()))
			{
				return irInsertarMaster(mapping, form, request, response);
			}
			if ("irModificarMaster".equals(vuelta.getName()))
			{
				return irModificarMaster(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMaster: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMaster: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMaster: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarMaster");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMaster: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarMaster(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				OCAPExpedientemcLN expMcLn = new OCAPExpedientemcLN(jcylUsuario);
				expMcLn.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarMaster: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMaster");
	}

	public ActionMessages validarMaster(ActionForm form, long cConvocatoriaId, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getCActividadId() == null) || (((OCAPMeritosForm) form).getCActividadId().intValue() == 0))
			{
				messages.add("cActividadId", new ActionMessage("errors.required", "Tipo de Actividad Formativa"));
			}
			if ((((OCAPMeritosForm) form).getCModalidadId() == null) || (((OCAPMeritosForm) form).getCModalidadId().intValue() == 0))
			{
				messages.add("cModalidadId", new ActionMessage("errors.required", "Modalidad"));
			}
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			} else if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else
			{
				dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
				if (dFFin == null)
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				else
				{
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFFinalizacion(), "fFinalizacion",
							"Fecha de Finalización", cConvocatoriaId, jcylUsuario,false,false));
				}

			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Organizador"));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMaster: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getCModalidadId() == null)
				((OCAPMeritosForm) form).setCModalidadId(expedientemcOT.getCModalidadId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMaster: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMaster: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarMaster");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMaster: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarMaster(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarMaster: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMaster");
	}

	public ActionForward controlAccionTaller(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionTaller: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarTaller(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarTaller(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionTaller: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarTaller".equals(vuelta.getName()))
			{
				return irInsertarTaller(mapping, form, request, response);
			}
			if ("irModificarTaller".equals(vuelta.getName()))
			{
				return irModificarTaller(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarTaller(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarTaller: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			if (request.getParameter("personal") != null)
			{
				((OCAPMeritosForm) form).setCPersonalId(request.getParameter("personal"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarTaller: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarTaller: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarTaller");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarTaller(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarTaller: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();
			

			messages = validarTaller(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setBAcreditado(((OCAPMeritosForm) form).getBAcreditado());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				if (Constantes.SI.equals(expedientemcOT.getBAcreditado()))
				{
					expedientemcOT.setNCreditosCurso(((OCAPMeritosForm) form).getNCreditosCurso());
				} else
				{
					expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
					if ((((OCAPMeritosForm) form).getNHoras() != null) && (!"".equals(((OCAPMeritosForm) form).getNHoras())))
						expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
					else
						expedientemcOT.setNHoras(Integer.valueOf("0"));
					if ((((OCAPMeritosForm) form).getNDias() != null) && (!"".equals(((OCAPMeritosForm) form).getNDias())))
						expedientemcOT.setNDias(Integer.valueOf(((OCAPMeritosForm) form).getNDias()));
					else
						expedientemcOT.setNDias(Integer.valueOf("0"));
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				OCAPExpedientemcLN expMcLn = new OCAPExpedientemcLN(jcylUsuario);
				expMcLn.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
			}

			OCAPConfigApp.logger.info(getClass().getName() + " insertarTaller: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarTaller");
	}

	private boolean esGradoExtraordinario(Long cExpId, JCYLUsuario jcylUsuario) {
		OCAPExpedienteCarreraLN expCarreraLN = new OCAPExpedienteCarreraLN(jcylUsuario);
		OCAPModalidadAnteriorLN modAnteriorLN = new OCAPModalidadAnteriorLN(jcylUsuario);
		
		Integer gradoAnterior = expCarreraLN.buscarModAnteriorExpedienteCarrera(cExpId);
		ArrayList gradosExtraordinarios = modAnteriorLN.listarModalidadAnteriorExtraordinaria();
		
		if (gradosExtraordinarios.contains(gradoAnterior)) {
			return true;
		}
		
		return false;
	}

	public ActionMessages validarTaller(ActionForm form, long cConvocatoriaId, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		float numCred = 0.0F;
		String parteDecimal = null;
		
		String id_hilo =  " Hilo Id: " + String.valueOf(Thread.currentThread().getId());
		
		
		
		//HACK		
		OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- " + id_hilo + " Entra en ValidarTaller con valor de cConvocatorioaId=" + String.valueOf(cConvocatoriaId) +  " DNI: " +jcylUsuario.getUser().getC_usr_id());				
		//END HACK
		
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			
			boolean tieneGradoExtraordinario = esGradoExtraordinario(((OCAPMeritosForm) form).getCExpId(), jcylUsuario);

			if ((((OCAPMeritosForm) form).getBAcreditado() == null) || ("".equals(((OCAPMeritosForm) form).getBAcreditado())))
			{
				messages.add("bAcreditado", new ActionMessage("errors.required", "Acreditación"));
			}
			if ((Constantes.NO.equals(((OCAPMeritosForm) form).getBAcreditado())) && ((((OCAPMeritosForm) form).getCModalidadId() == null)
					|| (((OCAPMeritosForm) form).getCModalidadId().intValue() == 0)))
			{
				messages.add("cModalidadId", new ActionMessage("errors.required", "Modalidad"));
			}

			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				} else
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFInicio(), "fInicio", "Fecha de Inicio", cConvocatoriaId,
							jcylUsuario,true,tieneGradoExtraordinario));
			}

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			} else if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else
			{
				dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
				if (dFFin == null)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					int aux = DateUtil.getNumAnnios(dFFin, DateUtil.getTodayDate());
					if (DateUtil.getNumAnnios(dFFin, DateUtil.getTodayDate()) > 10000)
						messages.add("fFinalizacion", new ActionMessage("error.condicionmsg", "Fecha de Finalización"));
					else if ((Constantes.NO.equals(((OCAPMeritosForm) form).getBAcreditado()))
							&& (!dFFin.before(DateUtil.convertStringToDate(Constantes.FECHA_ORDEN_SAN)))
							&& (!"2".equals(((OCAPMeritosForm) form).getCPersonalId())))
					{
						messages.add("fFinalizacion",
								new ActionMessage("error.fechaPosteriorOrden", "Fecha de Finalización", Constantes.FECHA_ORDEN_SAN));
					} else
						messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFFinalizacion(), "fFinalizacion",
								"Fecha de Finalización", cConvocatoriaId, jcylUsuario,true,tieneGradoExtraordinario));
				}

			}

			if ((Constantes.SI.equals(((OCAPMeritosForm) form).getBAcreditado())) && ((((OCAPMeritosForm) form).getNCreditosCurso() == null)
					|| ("".equals(((OCAPMeritosForm) form).getNCreditosCurso()))))
			{
				messages.add("nCreditosCurso", new ActionMessage("errors.required", "Nº de créditos"));
			}

			if (Constantes.NO.equals(((OCAPMeritosForm) form).getBAcreditado()))
			{
				if ((((OCAPMeritosForm) form).getNHoras() != null) && (!"".equals(((OCAPMeritosForm) form).getNHoras()))
						&& (!"0".equals(((OCAPMeritosForm) form).getNHoras())))
					try
					{
						Integer.parseInt(((OCAPMeritosForm) form).getNHoras());
					} catch (NumberFormatException e)
					{
						messages.add("nHoras", new ActionMessage("error.maskmsg", "Nº de horas"));
					}
				else
				{
					messages.add("nHoras", new ActionMessage("errors.required", "Nº de horas"));
				}
			}

			if ((((OCAPMeritosForm) form).getNCreditosCurso() != null) && (!"".equals(((OCAPMeritosForm) form).getNCreditosCurso())))
			{
				try
				{
					((OCAPMeritosForm) form).setNCreditosCurso(((OCAPMeritosForm) form).getNCreditosCurso().replaceAll(",", "."));

					if (((OCAPMeritosForm) form).getNCreditosCurso().indexOf('.') != -1)
					{
						parteDecimal = ((OCAPMeritosForm) form).getNCreditosCurso().substring(
								((OCAPMeritosForm) form).getNCreditosCurso().indexOf('.') + 1,
								((OCAPMeritosForm) form).getNCreditosCurso().length());
						if (parteDecimal.length() == 2)
						{
							messages.add("nCreditosCurso", new ActionMessage("error.maskmsg.decimal", "Nº de créditos"));
						}
					}
					numCred = Float.parseFloat(((OCAPMeritosForm) form).getNCreditosCurso());

					if (numCred > 99.9)
						messages.add("nCreditosCurso", new ActionMessage("error.creditoMax", "Nº de créditos"));
				} catch (NumberFormatException e)
				{
					messages.add("nCreditosCurso", new ActionMessage("error.maskmsg", "Nº de créditos"));
				}

			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}

			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Organizador"));
			}
			if ((((OCAPMeritosForm) form).getNDias() != null) && (!"".equals(((OCAPMeritosForm) form).getNDias())))
				try
				{
					Integer.parseInt(((OCAPMeritosForm) form).getNDias());
				} catch (NumberFormatException e)
				{
					messages.add("nDias", new ActionMessage("error.maskmsg", "Nº de días"));
				}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " validarTaller: ERROR: " + e.toString());
			throw e;
		}

		
		OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- " + id_hilo + " Sale de  ValidarTaller con valor de cConvocatorioaId=" + String.valueOf(cConvocatoriaId) +  " DNI: " +jcylUsuario.getUser().getC_usr_id());
		
		return messages;
	}

	public ActionForward irModificarTaller(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarTaller: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCModalidadId() == null)
				((OCAPMeritosForm) form).setCModalidadId(expedientemcOT.getCModalidadId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getNHoras() == null)
				((OCAPMeritosForm) form).setNHoras(expedientemcOT.getNHoras().intValue() == 0 ? "" : expedientemcOT.getNHoras().toString());
			if (((OCAPMeritosForm) form).getNDias() == null)
				((OCAPMeritosForm) form).setNDias(expedientemcOT.getNDias().intValue() == 0 ? "" : expedientemcOT.getNDias().toString());
			if (((OCAPMeritosForm) form).getNCreditosCurso() == null)
				((OCAPMeritosForm) form).setNCreditosCurso(expedientemcOT.getNCreditosCurso());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			if (request.getParameter("personal") != null)
			{
				((OCAPMeritosForm) form).setCPersonalId(request.getParameter("personal"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			OCAPMeritoscurricularesOT datos = new OCAPMeritoscurricularesOT();
			OCAPMeritoscurricularesLN oCAPMeritoscurricularesLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			datos = oCAPMeritoscurricularesLN.buscarOCAPMeritoscurriculares(expedientemcOT.getCMeritoId().longValue());

			((OCAPMeritosForm) form).setBAcreditado(datos.getBAcreditado());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarTaller: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarTaller: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarTaller");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarTaller(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarTaller: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			
			messages = validarTaller(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setBAcreditado(((OCAPMeritosForm) form).getBAcreditado());
				if (Constantes.SI.equals(expedientemcOT.getBAcreditado()))
				{
					expedientemcOT.setNCreditosCurso(((OCAPMeritosForm) form).getNCreditosCurso());
					expedientemcOT.setNHoras(Integer.valueOf("0"));
					expedientemcOT.setNDias(Integer.valueOf("0"));
				} else
				{
					expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
					if ((((OCAPMeritosForm) form).getNHoras() != null) && (!"".equals(((OCAPMeritosForm) form).getNHoras())))
						expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
					else
						expedientemcOT.setNHoras(Integer.valueOf("0"));
					if ((((OCAPMeritosForm) form).getNDias() != null) && (!"".equals(((OCAPMeritosForm) form).getNDias())))
						expedientemcOT.setNDias(Integer.valueOf(((OCAPMeritosForm) form).getNDias()));
					else
						expedientemcOT.setNDias(Integer.valueOf("0"));
					expedientemcOT.setNCreditosCurso("0");
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
			}

			OCAPConfigApp.logger.info(getClass().getName() + " modificarTaller: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarTaller");
	}

	public ActionForward controlAccionCongreso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCongreso: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionCongreso: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarCongreso(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarCongreso(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCongreso: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCongreso: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarCongreso".equals(vuelta.getName()))
			{
				return irInsertarCongreso(mapping, form, request, response);
			}
			if ("irModificarCongreso".equals(vuelta.getName()))
			{
				return irModificarCongreso(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarCongreso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCongreso: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			if (request.getParameter("personal") != null)
			{
				((OCAPMeritosForm) form).setCPersonalId(request.getParameter("personal"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCongreso: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCongreso: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarCongreso");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarCongreso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarCongreso: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarCongreso(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setBParticipacion(((OCAPMeritosForm) form).getBParticipacion());
				expedientemcOT.setANombreRevista(((OCAPMeritosForm) form).getANombreRevista());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setBAcreditado(((OCAPMeritosForm) form).getBAcreditado());
				if (Constantes.SI.equals(expedientemcOT.getBAcreditado()))
				{
					expedientemcOT.setNCreditosCurso(((OCAPMeritosForm) form).getNCreditosCurso());
				}

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				OCAPExpedientemcLN expMcLn = new OCAPExpedientemcLN(jcylUsuario);
				expMcLn.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarCongreso: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarCongreso");
	}

	public ActionMessages validarCongreso(ActionForm form, long cConvocatoriaId, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		float numCred = 0.0F;
		String parteDecimal = null;
		try
		{
			boolean tieneGradoExtraordinario = esGradoExtraordinario(((OCAPMeritosForm) form).getCExpId(), jcylUsuario);
			
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);

			if ((((OCAPMeritosForm) form).getBAcreditado() == null) || ("".equals(((OCAPMeritosForm) form).getBAcreditado())))
			{
				messages.add("bAcreditado", new ActionMessage("errors.required", "Acreditación"));
			}
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				} else
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFInicio(), "fInicio", "Fecha de Inicio", cConvocatoriaId,
							jcylUsuario,true,tieneGradoExtraordinario));
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			} else if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else
			{
				dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
				if (dFFin == null)
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				else if ((Constantes.NO.equals(((OCAPMeritosForm) form).getBAcreditado()))
						&& (!dFFin.before(DateUtil.convertStringToDate(Constantes.FECHA_ORDEN_SAN)))
						&& (!"2".equals(((OCAPMeritosForm) form).getCPersonalId())))
				{
					messages.add("fFinalizacion",
							new ActionMessage("error.fechaPosteriorOrden", "Fecha de Finalización", Constantes.FECHA_ORDEN_SAN));
				} else
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFFinalizacion(), "fFinalizacion",
							"Fecha de Finalización", cConvocatoriaId, jcylUsuario,true,tieneGradoExtraordinario));

			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}

			if ((Constantes.SI.equals(((OCAPMeritosForm) form).getBAcreditado())) && ((((OCAPMeritosForm) form).getNCreditosCurso() == null)
					|| ("".equals(((OCAPMeritosForm) form).getNCreditosCurso()))))
			{
				messages.add("nCreditosCurso", new ActionMessage("errors.required", "Nº de créditos"));
			}

			if ((((OCAPMeritosForm) form).getNCreditosCurso() != null) && (!"".equals(((OCAPMeritosForm) form).getNCreditosCurso())))
			{
				try
				{
					((OCAPMeritosForm) form).setNCreditosCurso(((OCAPMeritosForm) form).getNCreditosCurso().replaceAll(",", "."));

					if (((OCAPMeritosForm) form).getNCreditosCurso().indexOf('.') != -1)
					{
						parteDecimal = ((OCAPMeritosForm) form).getNCreditosCurso().substring(
								((OCAPMeritosForm) form).getNCreditosCurso().indexOf('.') + 1,
								((OCAPMeritosForm) form).getNCreditosCurso().length());

						if (parteDecimal.length() == 2)
						{
							messages.add("nCreditosCurso", new ActionMessage("error.maskmsg.decimal", "Nº de créditos"));
						}
					}
					numCred = Float.parseFloat(((OCAPMeritosForm) form).getNCreditosCurso());

					if (numCred > 99.989999999999995D)
						messages.add("nCreditosCurso", new ActionMessage("error.creditoMax", "Nº de créditos"));
				} catch (NumberFormatException e)
				{
					messages.add("nCreditosCurso", new ActionMessage("error.maskmsg", "Nº de créditos"));
				}
			}
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Organizador"));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarCongreso(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCongreso: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			if (request.getParameter("personal") != null)
			{
				((OCAPMeritosForm) form).setCPersonalId(request.getParameter("personal"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeritoscurricularesOT datos = new OCAPMeritoscurricularesOT();
			OCAPMeritoscurricularesLN oCAPMeritoscurricularesLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			datos = oCAPMeritoscurricularesLN.buscarOCAPMeritoscurriculares(expedientemcOT.getCMeritoId().longValue());

			((OCAPMeritosForm) form).setBAcreditado(datos.getBAcreditado());

			if (((OCAPMeritosForm) form).getNCreditosCurso() == null)
			{
				((OCAPMeritosForm) form).setNCreditosCurso(expedientemcOT.getNCreditosCurso());
			}
			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCongreso: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCongreso: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarCongreso");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarCongreso(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarCongreso: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarCongreso(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setBAcreditado(((OCAPMeritosForm) form).getBAcreditado());
				if (Constantes.SI.equals(expedientemcOT.getBAcreditado()))
					expedientemcOT.setNCreditosCurso(((OCAPMeritosForm) form).getNCreditosCurso());
				else
					expedientemcOT.setNCreditosCurso("0");
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarCongreso: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarCongreso");
	}

	public ActionForward controlAccionSesionClinica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionSesionClinica: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionSesionClinica: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarSesionClinica(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarSesionClinica(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionSesionClinica: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionSesionClinica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarSesionClinica".equals(vuelta.getName()))
			{
				return irInsertarSesionClinica(mapping, form, request, response);
			}
			if ("irModificarSesionClinica".equals(vuelta.getName()))
			{
				return irModificarSesionClinica(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarSesionClinica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarSesionClinica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarSesionClinica: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarSesionClinica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarSesionClinica");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarSesionClinica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarSesionClinica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarSesionClinica(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());

				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarSesionClinica: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarSesionClinica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarSesionClinica");
	}

	public ActionMessages validarSesionClinica(ActionForm form, long cConvocatoriaId, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		try
		{
			boolean tieneGradoExtraordinario = esGradoExtraordinario(((OCAPMeritosForm) form).getCExpId(), jcylUsuario);
			
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título de la Investigación"));
			}

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				else
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFInicio(), "fInicio", "Fecha de Inicio", cConvocatoriaId,
							jcylUsuario,true,tieneGradoExtraordinario));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarSesionClinica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarSesionClinica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());

			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			if (((OCAPMeritosForm) form).getFInicio() == null)
			{
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			}
			if (((OCAPMeritosForm) form).getDTitulo() == null)
			{
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarSesionClinica: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarSesionClinica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarSesionClinica");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarSesionClinica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarSesionClinica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarSesionClinica(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());

				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarSesionClinica: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarSesionClinica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarSesionClinica");
	}

	public ActionForward controlAccionFormaNuevoPersonal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionFormaNuevoPersonal: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionFormaNuevoPersonal: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarFormaNuevoPersonal(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarFormaNuevoPersonal(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionFormaNuevoPersonal: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionFormaNuevoPersonal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarFormaNuevoPersonal".equals(vuelta.getName()))
			{
				return irInsertarFormaNuevoPersonal(mapping, form, request, response);
			}
			if ("irModificarFormaNuevoPersonal".equals(vuelta.getName()))
			{
				return irModificarFormaNuevoPersonal(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarFormaNuevoPersonal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarFormaNuevoPersonal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
			{
				String tipoMerito = "";
				if (request.getParameter("tipoMerito").contains("ano"))
					tipoMerito = request.getParameter("tipoMerito").replaceAll("ano", "año");
				else
					tipoMerito = request.getParameter("tipoMerito");

				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(tipoMerito));
			}
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarFormaNuevoPersonal: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarFormaNuevoPersonal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarFormaNuevoPersonal");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarFormaNuevoPersonal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		long cExpmcId = 0L;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarFormaNuevoPersonal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			messages = validarFormaNuevoPersonal(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());

				cExpmcId = expedientemcLN.buscarExpMCRepetidoPorFAnnio(expedientemcOT, jcylUsuario);

				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				if (cExpmcId == 0L)
				{
					OCAPExpedientemcLN expMcLn = new OCAPExpedientemcLN(jcylUsuario);
					expMcLn.insertar(expedientemcOT, jcylUsuario);
				} else
				{
					messages.add("meritoRepetido",
							new ActionMessage("errors.meritoFormaNuevoPersonalRepetido", expedientemcOT.getFAnnio().toString()));
				}
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarFormaNuevoPersonal: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarFormaNuevoPersonal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarFormaNuevoPersonal");
	}

	public ActionMessages validarFormaNuevoPersonal(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			if ((((OCAPMeritosForm) form).getFAnnio() == null) || (((OCAPMeritosForm) form).getFAnnio().intValue() == 0))
			{
				messages.add("fAnnio", new ActionMessage("errors.required", "Año"));
			} else if (((OCAPMeritosForm) form).getFAnnio().toString().length() < 4)
			{
				messages.add("fAnnio", new ActionMessage("error.maskmsg", "Año"));
			} else
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy");
				df.setLenient(false);
				ParsePosition pos = new ParsePosition(0);
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFAnnio().toString(), pos);
				if (dFExpedicion == null)
				{
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Año de la dirección"));
				} else
				{
					Date annioActual = new Date();
					if (dFExpedicion.after(annioActual))
						messages.add("fAnnio", new ActionMessage("error.annioPosterior", "Año de la dirección"));
					else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFAnnio().toString(), "fAnnio"));
					}
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarFormaNuevoPersonal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarFormaNuevoPersonal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());

			if (((OCAPMeritosForm) form).getFAnnio() == null)
				((OCAPMeritosForm) form).setFAnnio(expedientemcOT.getFAnnio());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
			{
				String tipoMerito = "";
				if (request.getParameter("tipoMerito").contains("ano"))
					tipoMerito = request.getParameter("tipoMerito").replaceAll("ano", "año");
				else
					tipoMerito = request.getParameter("tipoMerito");

				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(tipoMerito));
			}
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarFormaNuevoPersonal: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarFormaNuevoPersonal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarFormaNuevoPersonal");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarFormaNuevoPersonal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		long cExpmcId = 0L;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarFormaNuevoPersonal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarFormaNuevoPersonal(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}

				cExpmcId = expedientemcLN.buscarExpMCRepetidoPorFAnnio(expedientemcOT, jcylUsuario);

				if (cExpmcId == 0L)
				{
					expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
					expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				} else if (cExpmcId != expedientemcOT.getCExpmcId().longValue())
				{
					messages.add("meritoRepetido",
							new ActionMessage("errors.meritoFormaNuevoPersonalRepetido", expedientemcOT.getFAnnio().toString()));
				}
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarFormaNuevoPersonal: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarFormaNuevoPersonal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarFormaNuevoPersonal");
	}

	public ActionForward controlAccionEspecialidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEspecialidad: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionEspecialidad: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarEspecialidad(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarEspecialidad(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEspecialidad: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEspecialidad: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarEspecialidad".equals(vuelta.getName()))
			{
				return irInsertarEspecialidad(mapping, form, request, response);
			}
			if ("irModificarEspecialidad".equals(vuelta.getName()))
			{
				return irModificarEspecialidad(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarEspecialidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEspecialidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEspecialidad: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEspecialidad: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarEspecialidad");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarEspecialidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarEspecialidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "Especialidad", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarEspecialidad: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarEspecialidad: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarEspecialidad");
	}

	public ActionForward irModificarEspecialidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEspecialidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEspecialidad: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEspecialidad: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarEspecialidad");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarEspecialidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarEspecialidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "Especialidad", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarEspecialidad: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarEspecialidad: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarEspecialidad");
	}

	public ActionForward controlAccionLicenDiplo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionLicenDiplo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionLicenDiplo: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarLicenDiplo(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarLicenDiplo(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionLicenDiplo: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionLicenDiplo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarLicenDiplo".equals(vuelta.getName()))
			{
				return irInsertarLicenDiplo(mapping, form, request, response);
			}
			if ("irModificarLicenDiplo".equals(vuelta.getName()))
			{
				return irModificarLicenDiplo(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarLicenDiplo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarLicenDiplo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = null;
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarLicenDiplo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarLicenDiplo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarLicenDiplo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarLicenDiplo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarLicenDiplo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "Licenciatura/Diplomatura", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarLicenDiplo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarLicenDiplo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarLicenDiplo");
	}

	public ActionForward irModificarLicenDiplo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarLicenDiplo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarLicenDiplo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarLicenDiplo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarLicenDiplo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarLicenDiplo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarLicenDiplo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "Licenciatura/Diplomatura", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarLicenDiplo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarLicenDiplo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarLicenDiplo");
	}

	public ActionForward controlAccionOtroFP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtroFP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtroFP: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarOtroFP(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarOtroFP(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtroFP: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtroFP: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarOtroFP".equals(vuelta.getName()))
			{
				return irInsertarOtroFP(mapping, form, request, response);
			}
			if ("irModificarOtroFP".equals(vuelta.getName()))
			{
				return irModificarOtroFP(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarOtroFP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtroFP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtroFP: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtroFP: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarOtroFP");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarOtroFP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarOtroFP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "Titulación", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarOtroFP: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarOtroFP: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarOtroFP");
	}

	public ActionForward irModificarOtroFP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtroFP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtroFP: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtroFP: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarOtroFP");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarOtroFP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarOtroFP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "Titulación", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarOtroFP: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarOtroFP: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarOtroFP");
	}

	public ActionForward controlAccionFPSuperior(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionFPSuperior: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionFPSuperior: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarFPSuperior(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarFPSuperior(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionFPSuperior: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionFPSuperior: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarFPSuperior".equals(vuelta.getName()))
			{
				return irInsertarFPSuperior(mapping, form, request, response);
			}
			if ("irModificarFPSuperior".equals(vuelta.getName()))
			{
				return irModificarFPSuperior(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarFPSuperior(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarFPSuperior: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarFPSuperior: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarFPSuperior: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarFPSuperior");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarFPSuperior(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarFPSuperior: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "FPSup", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarFPSuperior: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarFPSuperior: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarFPSuperior");
	}

	public ActionForward irModificarFPSuperior(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarFPSuperior: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarFPSuperior: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarFPSuperior: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarFPSuperior");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarFPSuperior(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarFPSuperior: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstudios(form, "FPSup", cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarFPSuperior: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarFPSuperior: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarFPSuperior");
	}

	public ActionForward controlAccionDoctorado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDoctorado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionDoctorado: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarDoctorado(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarDoctorado(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDoctorado: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDoctorado: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarDoctorado".equals(vuelta.getName()))
			{
				return irInsertarDoctorado(mapping, form, request, response);
			}
			if ("irModificarDoctorado".equals(vuelta.getName()))
			{
				return irModificarDoctorado(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarDoctorado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDoctorado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDoctorado: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDoctorado: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarDoctorado");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarDoctorado(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDoctorado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarDoctorado(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarDoctorado: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDoctorado: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarDoctorado");
	}

	public ActionMessages validarDoctorado(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Doctorado"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha de Expedición"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Expedición"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Expedición"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
				{
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Expedición"));
				} else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
			if ((((OCAPMeritosForm) form).getALugarExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getALugarExpedicion())))
			{
				messages.add("aLugarExpedicion", new ActionMessage("errors.required", "Lugar de Expedición"));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarDoctorado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDoctorado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDoctorado: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDoctorado: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarDoctorado");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarDoctorado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarDoctorado: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarDoctorado(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarDoctorado: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarDoctorado: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarDoctorado");
	}

	public ActionForward controlAccionEstancia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEstancia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionEstancia: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarEstancia(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarEstancia(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEstancia: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEstancia: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarEstancia".equals(vuelta.getName()))
			{
				return irInsertarEstancia(mapping, form, request, response);
			}
			if ("irModificarEstancia".equals(vuelta.getName()))
			{
				return irModificarEstancia(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarEstancia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEstancia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEstancia: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEstancia: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarEstancia");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarEstancia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarEstancia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstancia(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarEstancia: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarEstancia");
	}

	public ActionMessages validarEstancia(ActionForm form, long cConvocatoriaId, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			boolean tieneGradoExtraordinario = esGradoExtraordinario(((OCAPMeritosForm) form).getCExpId(), jcylUsuario);
			
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
				messages.add("dTitulo", new ActionMessage("errors.required", "Descripción"));
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
				else
				{
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFInicio(), "fInicio",
							"Fecha de Inicio", cConvocatoriaId, jcylUsuario,true,tieneGradoExtraordinario));
				}

			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			else if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalizacion"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					else
					{
						messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFFinalizacion(), "fFinalizacion",
								"Fecha de Finalización", cConvocatoriaId, jcylUsuario,true,tieneGradoExtraordinario));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}
			if ((((OCAPMeritosForm) form).getALugarExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getALugarExpedicion())))
				messages.add("aLugarExpedicion", new ActionMessage("errors.required", "Lugar"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarEstancia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEstancia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEstancia: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEstancia: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarEstancia");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarEstancia(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarEstancia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarEstancia(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarEstancia: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarEstancia");
	}

	public ActionForward controlAccionBeca(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBeca: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBeca: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarBeca(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarBeca(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBeca: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBeca: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarBeca".equals(vuelta.getName()))
			{
				return irInsertarBeca(mapping, form, request, response);
			}
			if ("irModificarBeca".equals(vuelta.getName()))
			{
				return irModificarBeca(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarBeca(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBeca: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBeca: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBeca: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarBeca");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarBeca(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarBeca: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarBeca(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarBeca: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarBeca");
	}

	public ActionMessages validarBeca(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			else if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de fFinalizacion"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}
			if ((((OCAPMeritosForm) form).getALugarExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getALugarExpedicion())))
				messages.add("aLugarExpedicion", new ActionMessage("errors.required", "Lugar"));
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
				messages.add("aOrganizador", new ActionMessage("errors.required", "Entidad que otorga la beca"));
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
				messages.add("dTitulo", new ActionMessage("errors.required", "Título de la Investigación"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarBeca(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBeca: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBeca: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBeca: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarBeca");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarBeca(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarBeca: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarBeca(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarBeca: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarBeca");
	}

	public ActionForward controlAccionCursoDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCursoDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionCursoDoc: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarCursoDoc(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarCursoDoc(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCursoDoc: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCursoDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarCursoDoc".equals(vuelta.getName()))
			{
				return irInsertarCursoDoc(mapping, form, request, response);
			}
			if ("irModificarCursoDoc".equals(vuelta.getName()))
			{
				return irModificarCursoDoc(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarCursoDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCursoDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCursoDoc: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCursoDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarCursoDoc");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarCursoDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarCursoDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarCursoDoc(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				if ((((OCAPMeritosForm) form).getNHoras() != null) && (!"".equals(((OCAPMeritosForm) form).getNHoras())))
				{
					expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
				}

				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarCursoDoc: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarCursoDoc");
	}

	public ActionMessages validarCursoDoc(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			} else if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalizacion"));
			} else
			{
				dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
				if (dFFin == null)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
				}

			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Organizador"));
			}
			if ((((OCAPMeritosForm) form).getNHoras() == null) || ("".equals(((OCAPMeritosForm) form).getNHoras())))
				messages.add("nHoras", new ActionMessage("errors.required", "Nº de horas"));
			else
			{
				try
				{
					Integer.parseInt(((OCAPMeritosForm) form).getNHoras());
				} catch (NumberFormatException e)
				{
					messages.add("nHoras", new ActionMessage("error.maskmsg", "Nº de horas"));
				}

			}

		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarCursoDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCursoDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getNHoras() == null)
			{
				((OCAPMeritosForm) form).setNHoras(expedientemcOT.getNHoras().intValue() == 0 ? "" : expedientemcOT.getNHoras().toString());
			}

			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCursoDoc: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCursoDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarCursoDoc");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarCursoDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarCursoDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarCursoDoc(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				if ((((OCAPMeritosForm) form).getNHoras() != null) && (!"".equals(((OCAPMeritosForm) form).getNHoras())))
					expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
				else
					expedientemcOT.setNHoras(Integer.valueOf("0"));
				if ((((OCAPMeritosForm) form).getNDias() != null) && (!"".equals(((OCAPMeritosForm) form).getNDias())))
					expedientemcOT.setNDias(Integer.valueOf(((OCAPMeritosForm) form).getNDias()));
				else
					expedientemcOT.setNDias(Integer.valueOf("0"));
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarCursoDoc: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarCursoDoc");
	}

	public ActionForward controlAccionBibliograficas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBibliograficas: Inicio");
			validarAccion(mapping, form, request, response);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionBibliograficas: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarBibliograficas(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarBibliograficas(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBibliograficas: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBibliograficas: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarBibliograficas".equals(vuelta.getName()))
			{
				return irInsertarBibliograficas(mapping, form, request, response);
			}
			if ("irModificarBibliograficas".equals(vuelta.getName()))
			{
				return irModificarBibliograficas(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarBibliograficas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBibliograficas: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);
			
			
			OCAPConvocatoriasLN convocLN = new OCAPConvocatoriasLN(jcylUsuario);						
			OCAPConvocatoriasOT  convocOt =  convocLN.buscarOCAPConvocatoriasPorExpediente(new Long(request.getParameter("expId")));
			((OCAPMeritosForm) form).setFComprobMeritos(FIN_ANNIO+ convocOt.getDAnioConvocatoria());
			

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBibliograficas: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBibliograficas: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarBibliograficas");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarBibliograficas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarBibliograficas: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
			expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
			expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
			expedientemcOT.setAObjetivo(((OCAPMeritosForm) form).getAObjetivo());
			expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());

			if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
					&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
				expedientemcOT.setBOpcional(Constantes.SI);
			} else
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
				expedientemcOT.setBOpcional(Constantes.NO);
			}
			expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
			expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
			expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getResumenCertificados());

			expedientemcLN.insertar(expedientemcOT, jcylUsuario);

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));

			OCAPConfigApp.logger.info(getClass().getName() + " insertarBibliograficas: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarBibliograficas: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarBibliograficas");
	}

	public ActionForward irModificarBibliograficas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		ArrayList listadoMeritos = null;
		ArrayList cadenasCertificados = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBibliograficas: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);
			
			
			
			OCAPConvocatoriasLN convocLN = new OCAPConvocatoriasLN(jcylUsuario);						
			OCAPConvocatoriasOT  convocOt =  convocLN.buscarOCAPConvocatoriasPorExpediente(expedientemcOT.getCExpId());
			((OCAPMeritosForm) form).setFComprobMeritos(FIN_ANNIO+ convocOt.getDAnioConvocatoria());
			
			

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCModalidadId() == null)
			{
				((OCAPMeritosForm) form).setCModalidadId(expedientemcOT.getCModalidadId());
			}

			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			cadenasCertificados = Cadenas.obtenerArrayListTokens(expedientemcOT.getAOrganizador(), "#");

			OCAPExpedientemcOT expMCOT = new OCAPExpedientemcOT();
			listadoMeritos = new ArrayList();
			for (int j = 0; j < cadenasCertificados.size(); j++)
			{
				expMCOT = new OCAPExpedientemcOT();
				String cadena = (String) cadenasCertificados.get(j);
				expMCOT.setDTitulo(cadena.substring(0, cadena.indexOf("$")));
				expMCOT.setDTituloCodificado(escapeHTML(cadena.substring(0, cadena.indexOf("$"))));
				expMCOT.setFInicio(cadena.substring(cadena.indexOf("$") + 1, cadena.indexOf("&")));
				cadena = cadena.substring(cadena.indexOf("&") + 1);
				expMCOT.setNHoras(new Integer(cadena));
				listadoMeritos.add(expMCOT);
			}

			request.setAttribute("resumenCertificados", listadoMeritos);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBibliograficas: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarBibliograficas");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarBibliograficas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarBibliograficas: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
			expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
			expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
			expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

			if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
					&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
				expedientemcOT.setBOpcional(Constantes.SI);
			} else
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
				expedientemcOT.setBOpcional(Constantes.NO);
			}
			expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

			expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getResumenCertificados());

			idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));

			OCAPConfigApp.logger.info(getClass().getName() + " modificarBibliograficas: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarBibliograficas: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarBibliograficas");
	}

	public ActionForward controlAccionBibliograficasAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBibliograficasAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionBibliograficasAct: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarBibliograficasAct(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarBibliograficasAct(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBibliograficasAct: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBibliograficasAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarBibliograficasAct".equals(vuelta.getName()))
			{
				return irInsertarBibliograficasAct(mapping, form, request, response);
			}
			if ("irModificarBibliograficasAct".equals(vuelta.getName()))
			{
				return irModificarBibliograficasAct(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarBibliograficasAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBibliograficasAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBibliograficasAct: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBibliograficas: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarBibliograficasAct");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarBibliograficasAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarBibliograficasAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarBibliograficasAct(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarBibliograficasAct: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarBibliograficasAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarBibliograficasAct");
	}

	public ActionMessages validarBibliograficasAct(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título de la Sesión"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			else if ((((OCAPMeritosForm) form).getFInicio() != null) && (!"".equals(((OCAPMeritosForm) form).getFInicio()))
					&& (((OCAPMeritosForm) form).getFInicio().length() == 10))
			{
				if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				} else
				{
					dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
					if (dFInicio == null)
					{
						messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFInicio().toString(), "fInicio"));
					}
				}
			}
			if ((((OCAPMeritosForm) form).getListaActividades() != null) && (((OCAPMeritosForm) form).getListaActividades().size() > 0)
					&& ((((OCAPMeritosForm) form).getCActividadId() == null) || ("".equals(((OCAPMeritosForm) form).getCActividadId()))))
			{
				messages.add("cActividadId", new ActionMessage("errors.required", "Tipo de Actividad Formativa"));
			}
			if ((((OCAPMeritosForm) form).getNHoras() == null) || ("".equals(((OCAPMeritosForm) form).getNHoras())))
				messages.add("nHoras", new ActionMessage("errors.required", "Nº de horas"));
			else
				try
				{
					int nHor = Integer.parseInt(((OCAPMeritosForm) form).getNHoras());
					if (nHor == 0)
						messages.add("nHoras", new ActionMessage("errors.required", "Nº de horas"));
				} catch (NumberFormatException e)
				{
					messages.add("nHoras", new ActionMessage("error.maskmsg", "Nº de horas"));
				}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarBibliograficasAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		ArrayList listadoMeritos = null;
		ArrayList cadenasCertificados = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBibliograficasAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());

			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
			{
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			}
			if (((OCAPMeritosForm) form).getNHoras() == null)
			{
				((OCAPMeritosForm) form).setNHoras(expedientemcOT.getNHoras().intValue() == 0 ? "" : expedientemcOT.getNHoras().toString());
			}
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBibliograficasAct: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBibliograficasAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarBibliograficasAct");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarBibliograficasAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarBibliograficasAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarBibliograficasAct(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}

				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarBibliograficasAct: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarBibliograficasAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarBibliograficasAct");
	}

	public ActionForward controlAccionSeminariosDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionSeminariosDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionSeminariosDoc: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarSeminariosDoc(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarSeminariosDoc(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionSeminariosDoc: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionSeminariosDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarSeminariosDoc".equals(vuelta.getName()))
			{
				return irInsertarSeminariosDoc(mapping, form, request, response);
			}
			if ("irModificarSeminariosDoc".equals(vuelta.getName()))
			{
				return irModificarSeminariosDoc(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarSeminariosDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarSeminariosDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarSeminariosDoc: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarSeminariosDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarSeminariosDoc");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarSeminariosDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarSeminariosDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			messages = validarSeminariosDoc(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				if ((((OCAPMeritosForm) form).getNHoras() != null) && (!"".equals(((OCAPMeritosForm) form).getNHoras())))
				{
					expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
				}

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarSeminariosDoc: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarSeminariosDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarSeminariosDoc");
	}

	public ActionMessages validarSeminariosDoc(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título Seminario/Jornada"));
			}
			if ((((OCAPMeritosForm) form).getCActividadId() == null) || (((OCAPMeritosForm) form).getCActividadId().intValue() == 0))
			{
				messages.add("cActividadId", new ActionMessage("errors.required", "Ámbito"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha de Celebración"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Celebración"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Celebración"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
				{
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Celebración"));
				} else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
			if ((((OCAPMeritosForm) form).getListaModalidades() != null) && (((OCAPMeritosForm) form).getListaModalidades().size() > 0))
			{
				if ((((OCAPMeritosForm) form).getCModalidadId() == null) || ("".equals(((OCAPMeritosForm) form).getCModalidadId())))
					messages.add("cModalidadId", new ActionMessage("errors.required", "Modalidad"));
				else if (((OCAPMeritosForm) form).getCModalidadId().intValue() == 14)
					if ((((OCAPMeritosForm) form).getNHoras() == null) || ("".equals(((OCAPMeritosForm) form).getNHoras())))
						messages.add("nHoras", new ActionMessage("errors.required", "Nº de horas"));
					else
						try
						{
							int nHor = Integer.parseInt(((OCAPMeritosForm) form).getNHoras());
							if (nHor == 0)
								messages.add("nHoras", new ActionMessage("errors.required", "Nº de horas"));
						} catch (NumberFormatException e)
						{
							messages.add("nHoras", new ActionMessage("error.maskmsg", "Nº de horas"));
						}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarSeminariosDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarSeminariosDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getCModalidadId() == null)
				((OCAPMeritosForm) form).setCModalidadId(expedientemcOT.getCModalidadId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getNHoras() == null)
				((OCAPMeritosForm) form).setNHoras(expedientemcOT.getNHoras().intValue() == 0 ? "" : expedientemcOT.getNHoras().toString());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarSeminariosDoc: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarSeminariosDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarSeminariosDoc");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarSeminariosDoc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarSeminariosDoc: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			messages = validarSeminariosDoc(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				if ((((OCAPMeritosForm) form).getNHoras() != null) && (!"".equals(((OCAPMeritosForm) form).getNHoras())))
					expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarSeminariosDoc: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarSeminariosDoc: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarSeminariosDoc");
	}

	public ActionForward controlAccionModeradorMesa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionModeradorMesa: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionModeradorMesa: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarModeradorMesa(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarModeradorMesa(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionModeradorMesa: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionModeradorMesa: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarModeradorMesa".equals(vuelta.getName()))
			{
				return irInsertarModeradorMesa(mapping, form, request, response);
			}
			if ("irModificarModeradorMesa".equals(vuelta.getName()))
			{
				return irModificarModeradorMesa(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarModeradorMesa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarModeradorMesa: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarModeradorMesa: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarModeradorMesa: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarModeradorMesa");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarModeradorMesa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarModeradorMesa: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarModeradorMesa(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}

				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarModeradorMesa: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarModeradorMesa: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarModeradorMesa");
	}

	public ActionMessages validarModeradorMesa(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFAnnio = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha del evento"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha del evento"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha del evento"));
			} else
			{
				dFAnnio = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFAnnio == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha del evento"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarModeradorMesa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarModeradorMesa: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFAnnio() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarModeradorMesa: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarModeradorMesa: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarModeradorMesa");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarModeradorMesa(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarModeradorMesa: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarModeradorMesa(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarModeradorMesa: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarModeradorMesa: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarModeradorMesa");
	}

	public ActionForward controlAccionDocenciaPre(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDocenciaPre: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionDocenciaPre: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarDocenciaPre(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarDocenciaPre(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDocenciaPre: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDocenciaPre: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarDocenciaPre".equals(vuelta.getName()))
			{
				return irInsertarDocenciaPre(mapping, form, request, response);
			}
			if ("irModificarDocenciaPre".equals(vuelta.getName()))
			{
				return irModificarDocenciaPre(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarDocenciaPre(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDocenciaPre: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDocenciaPre: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarDocenciaPre");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarDocenciaPre(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		long cExpmcId = 0L;
		OCAPMeractividadOT merActOT = null;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDocenciaPre: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarDocenciaPre(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());

				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());

				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarDocenciaPre: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarDocenciaPre");
	}

	public ActionMessages validarDocenciaPre(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFAnnio = null;
		Calendar anioAct = new GregorianCalendar();
		Calendar anioIni = new GregorianCalendar();
		try
		{
			if ((((OCAPMeritosForm) form).getDTipoMerito() != null)
					&& (((OCAPMeritosForm) form).getDTipoMerito().toUpperCase().indexOf("PREGRADO") != -1)
					&& ((((OCAPMeritosForm) form).getCActividadId() == null)
							|| (((OCAPMeritosForm) form).getCActividadId().intValue() == 0)))
			{
				messages.add("cActividadId", new ActionMessage("errors.required", "Tipo de docencia"));
			}

			if ((((OCAPMeritosForm) form).getDTipoMerito() != null)
					&& (((OCAPMeritosForm) form).getDTipoMerito().toUpperCase().indexOf("ASOCIADO") != -1)
					&& ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo()))))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Departamento"));
			}
			if ((((OCAPMeritosForm) form).getDTipoMerito() != null)
					&& (((OCAPMeritosForm) form).getDTipoMerito().toUpperCase().indexOf("VINCULADO") != -1)
					&& ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo()))))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Especialidad"));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFAnnio() == null) || (((OCAPMeritosForm) form).getFAnnio().intValue() == 0))
			{
				messages.add("fAnnio", new ActionMessage("errors.required", "Año de Inicio"));
			} else if (((OCAPMeritosForm) form).getFAnnio().toString().length() < 4)
			{
				messages.add("fAnnio", new ActionMessage("error.maskmsg", "Año de Inicio"));
			} else
			{
				dFAnnio = df.parse(((OCAPMeritosForm) form).getFAnnio().toString(), pos);
				if (dFAnnio == null)
				{
					messages.add("fAnnio", new ActionMessage("error.maskmsg", "Año de Inicio"));
				} else
				{
					anioIni.setTime(dFAnnio);
					if (anioIni.get(1) >= anioAct.get(1))
					{
						messages.add("fAnnio", new ActionMessage("error.annioInferior", "Año de Inicio"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFAnnio().toString(), "fAnnio"));
					}

				}

			}

		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarDocenciaPre(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		OCAPMeractividadOT merActOT = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDocenciaPre: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getNAnnios() == null)
				((OCAPMeritosForm) form).setNAnnios(expedientemcOT.getNAnnios().toString());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFAnnio() == null)
			{
				((OCAPMeritosForm) form).setFAnnio(expedientemcOT.getFAnnio());
			}
			((OCAPMeritosForm) form).setFAnnioFin(new Integer(expedientemcOT.getFAnnio().intValue() + 1));

			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDocenciaPre: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDocenciaPre: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarDocenciaPre");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarDocenciaPre(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarDocenciaPre: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarDocenciaPre(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());

				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarDocenciaPre: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarDocenciaPre");
	}

	public ActionForward controlAccionDocenciaPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDocenciaPost: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			String accionBoton = ((OCAPMeritosForm) form).getAccionBoton();
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDocenciaPost: ACCION: " + accionBoton);
			if (Constantes.INSERTAR.equals(accionBoton))
				vuelta = insertarDocenciaPost(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(accionBoton))
			{
				vuelta = modificarDocenciaPost(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDocenciaPost: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionDocenciaPost: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarDocenciaPost".equals(vuelta.getName()))
			{
				return irInsertarDocenciaPost(mapping, form, request, response);
			}
			if ("irModificarDocenciaPost".equals(vuelta.getName()))
			{
				return irModificarDocenciaPost(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarDocenciaPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDocenciaPost: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + " " + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);
			
			
			OCAPConvocatoriasLN convocLN = new OCAPConvocatoriasLN(jcylUsuario);						
			OCAPConvocatoriasOT  convocOt =  convocLN.buscarOCAPConvocatoriasPorExpediente(new Long(request.getParameter("expId")));
			((OCAPMeritosForm) form).setFComprobMeritos(FIN_ANNIO+ convocOt.getDAnioConvocatoria());
			

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDocenciaPost: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarDocenciaPost: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarDocenciaPost");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarDocenciaPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		long cExpmcId = 0L;
		OCAPMeractividadOT merActOT = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDocenciaPost: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
			expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

			if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
					&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
				expedientemcOT.setBOpcional(Constantes.SI);
			} else
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
				expedientemcOT.setBOpcional(Constantes.NO);
			}
			expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());

			expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
			if ((((OCAPMeritosForm) form).getNAnnios() != null) && (!"".equals(((OCAPMeritosForm) form).getNAnnios())))
				expedientemcOT.setNAnnios(Integer.valueOf(((OCAPMeritosForm) form).getNAnnios()));
			if ((((OCAPMeritosForm) form).getNMeses() != null) && (!"".equals(((OCAPMeritosForm) form).getNMeses())))
			{
				expedientemcOT.setNMeses(Integer.valueOf(((OCAPMeritosForm) form).getNMeses()));
			}
			expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getResumenCertificados());

			expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

			idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));

			OCAPConfigApp.logger.info(getClass().getName() + " insertarDocenciaPost: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarDocenciaPost: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarDocenciaPost");
	}

	public ActionMessages validarDocenciaPost(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		try
		{
			if ((((OCAPMeritosForm) form).getCActividadId() == null) || (((OCAPMeritosForm) form).getCActividadId().intValue() == 0))
			{
				messages.add("cActividadId", new ActionMessage("errors.required", "Tipo de docencia"));
			} else if ((((OCAPMeritosForm) form).getCActividadId().intValue() == 7)
					|| (((OCAPMeritosForm) form).getCActividadId().intValue() == 8))
			{
				if ((((OCAPMeritosForm) form).getNAnnios() == null) || ("".equals(((OCAPMeritosForm) form).getNAnnios())))
					messages.add("nAnnios", new ActionMessage("errors.required", "Nº de años"));
				else
				{
					try
					{
						if (Integer.parseInt(((OCAPMeritosForm) form).getNAnnios()) == 0)
							messages.add("nAnnios", new ActionMessage("errors.required", "Nº de años"));
					} catch (NumberFormatException e)
					{
						messages.add("nAnnios", new ActionMessage("error.maskmsg", "Nº de años"));
					}
				}
				if ((((OCAPMeritosForm) form).getNMeses() != null) && (!"".equals(((OCAPMeritosForm) form).getNMeses())))
				{
					try
					{
						Integer.parseInt(((OCAPMeritosForm) form).getNMeses());
					} catch (NumberFormatException e)
					{
						messages.add("nMeses", new ActionMessage("error.maskmsg", "Nº de meses"));
					}
				}

			} else if ((((OCAPMeritosForm) form).getCActividadId().intValue() == 9)
					|| (((OCAPMeritosForm) form).getCActividadId().intValue() == 19))
			{
				if ((((OCAPMeritosForm) form).getNMeses() == null) || ("".equals(((OCAPMeritosForm) form).getNMeses())))
					messages.add("nMeses", new ActionMessage("errors.required", "Nº de meses"));
				else
				{
					try
					{
						if (Integer.parseInt(((OCAPMeritosForm) form).getNMeses()) == 0)
							messages.add("nMeses", new ActionMessage("errors.required", "Nº de meses"));
					} catch (NumberFormatException e)
					{
						messages.add("nMeses", new ActionMessage("error.maskmsg", "Nº de meses"));
					}
				}
				if ((((OCAPMeritosForm) form).getNAnnios() != null) && (!"".equals(((OCAPMeritosForm) form).getNAnnios())))
				{
					try
					{
						Integer.parseInt(((OCAPMeritosForm) form).getNAnnios());
					} catch (NumberFormatException e)
					{
						messages.add("nAnnios", new ActionMessage("error.maskmsg", "Nº de años"));
					}
				}

			} else
			{
				if ((((OCAPMeritosForm) form).getNAnnios() == null) || ("".equals(((OCAPMeritosForm) form).getNAnnios())))
					messages.add("nAnnios", new ActionMessage("errors.required", "Nº de años"));
				else
				{
					try
					{
						if (Integer.parseInt(((OCAPMeritosForm) form).getNAnnios()) == 0)
							messages.add("nAnnios", new ActionMessage("errors.required", "Nº de años"));
					} catch (NumberFormatException e)
					{
						messages.add("nAnnios", new ActionMessage("error.maskmsg", "Nº de años"));
					}
				}
				if ((((OCAPMeritosForm) form).getNMeses() == null) || ("".equals(((OCAPMeritosForm) form).getNMeses())))
					messages.add("nMeses", new ActionMessage("errors.required", "Nº de meses"));
				else
					try
					{
						if (Integer.parseInt(((OCAPMeritosForm) form).getNMeses()) == 0)
							messages.add("nMeses", new ActionMessage("errors.required", "Nº de meses"));
					} catch (NumberFormatException e)
					{
						messages.add("nMeses", new ActionMessage("error.maskmsg", "Nº de meses"));
					}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarDocenciaPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		ArrayList listaMeritos = null;
		ArrayList listadoMeritos = new ArrayList();
		ArrayList listadoMeritosTotales = new ArrayList();
		ArrayList cadenasCertificados = new ArrayList();
		OCAPMeractividadOT merActOT = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDocenciaPost: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			/* hack */
			Integer cEstatutId = null;
			String cDefMeritoId = null;
			String dTipoMerito = null;
			Long cExpId = null;
			String pestana = null;
			String tipo = null;

			if (request.getParameter("cEstatutId") != null)
			{
				cEstatutId = Integer.valueOf(request.getParameter("cEstatutId"));
			} else if (request.getParameter("CEstatutId") != null)
			{
				cEstatutId = Integer.valueOf(request.getParameter("CEstatutId"));
			}

			if (request.getParameter("cDefMeritoId") != null)
			{
				cDefMeritoId = request.getParameter("cDefMeritoId");
			} else if (request.getParameter("CDefMeritoId") != null)
			{
				cDefMeritoId = request.getParameter("CDefMeritoId");
			}

			if (request.getParameter("tipoMerito") != null)
			{
				dTipoMerito = request.getParameter("tipoMerito");
			} else if (request.getParameter("DTipoMerito") != null)
			{
				dTipoMerito = request.getParameter("DTipoMerito");
			}

			if (request.getParameter("expId") != null)
			{
				cExpId = Long.parseLong(request.getParameter("expId"));
			} else if (request.getParameter("CExpId") != null)
			{
				cExpId = Long.parseLong(request.getParameter("CExpId"));
			}

			if (request.getParameter("pestana") != null)
			{
				pestana = request.getParameter("pestana");
			} else if (request.getParameter("pestanaSeleccionada") != null)
			{
				pestana = request.getParameter("pestanaSeleccionada");
			}

			if (request.getParameter("tipo") != null)
			{
				tipo = request.getParameter("tipo");
			} else if (request.getParameter("CTipoMerito") != null)
			{
				tipo = request.getParameter("CTipoMerito");
			}

			expedientemcOT.setCEstatutId(cEstatutId);
			expedientemcOT.setCDefMeritoId(cDefMeritoId);
			expedientemcOT.setDTipoMerito(dTipoMerito);
			expedientemcOT.setCExpId(cExpId);

			expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
			
			
			
			OCAPConvocatoriasLN convocLN = new OCAPConvocatoriasLN(jcylUsuario);						
			OCAPConvocatoriasOT  convocOt =  convocLN.buscarOCAPConvocatoriasPorExpediente(cExpId);
			((OCAPMeritosForm) form).setFComprobMeritos(FIN_ANNIO+ convocOt.getDAnioConvocatoria());
			

			if (pestana != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(pestana);
			if (dTipoMerito != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(dTipoMerito));
			if (cEstatutId != null)
				((OCAPMeritosForm) form).setCEstatutId(cEstatutId);
			if (cDefMeritoId != null)
				((OCAPMeritosForm) form).setCDefMeritoId(cDefMeritoId);
			if (cExpId != null)
				((OCAPMeritosForm) form).setCExpId(cExpId);
			if (tipo != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(tipo);
			}

			if ((pestana != null) && (pestana.equals(Constantes.DEF_MERITO_OPCIONALES)))
			{
				listaMeritos = expedientemcLN.buscarPorMeritoId(expedientemcOT, jcylUsuario, Constantes.FLAG_MERITOS_OPCIONALES);
			} else if ((pestana != null) && !pestana.equals(Constantes.DEF_MERITO_OPCIONALES))
			{
				listaMeritos = expedientemcLN.buscarPorMeritoId(expedientemcOT, jcylUsuario, Constantes.FLAG_MERITOS_NO_OPCIONALES);
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + " " + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			((OCAPMeritosForm) form).setBInvalidado(Constantes.SI);
			((OCAPMeritosForm) form).setBPdteAclarar(Constantes.NO);

			for (int i = 0; i < listaMeritos.size(); i++)
			{
				if ((((OCAPExpedientemcOT) listaMeritos.get(i)).getAOrganizador() != null)
						&& (((OCAPExpedientemcOT) listaMeritos.get(i)).getAOrganizador().length() > 0))
					cadenasCertificados = Cadenas.obtenerArrayListTokens(((OCAPExpedientemcOT) listaMeritos.get(i)).getAOrganizador(), "#");
				else
				{
					cadenasCertificados = new ArrayList();
				}
				OCAPExpedientemcOT expMCOT = new OCAPExpedientemcOT();
				listadoMeritos = new ArrayList();
				for (int j = 0; j < cadenasCertificados.size(); j++)
				{
					expMCOT = new OCAPExpedientemcOT();
					String cadena = (String) cadenasCertificados.get(j);
					expMCOT.setFAnnio(new Integer(cadena.substring(0, cadena.indexOf("$"))));
					cadena = cadena.substring(cadena.indexOf("$") + 1);

					if (dTipoMerito.equals(Constantes.MER_DOC_POS_ACRED))
					{
						expMCOT.setDTitulo(cadena);
					} else if ((((OCAPExpedientemcOT) listaMeritos.get(i)).getCActividadId().intValue() == 9)
							|| (((OCAPExpedientemcOT) listaMeritos.get(i)).getCActividadId().intValue() == 19))
					{
						expMCOT.setFAnnioFin(new Integer(cadena.substring(0, cadena.indexOf("$"))));
						cadena = cadena.substring(cadena.indexOf("$") + 1);
						expMCOT.setNMeses(new Integer(cadena.substring(0, cadena.indexOf("$"))));
						cadena = cadena.substring(cadena.indexOf("$") + 1);
						expMCOT.setNDias(new Integer(cadena));
					} else
					{
						expMCOT.setFAnnioFin(new Integer(cadena));
					}

					expMCOT.setCActividadId(((OCAPExpedientemcOT) listaMeritos.get(i)).getCActividadId());
					expMCOT.setCExpmcId(((OCAPExpedientemcOT) listaMeritos.get(i)).getCExpmcId());

					if (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) && Constantes.SI.equals(((OCAPExpedientemcOT) listaMeritos.get(i)).getBPdteAclararCeis())) {
						((OCAPMeritosForm) form).setBPdteAclarar(((OCAPExpedientemcOT) listaMeritos.get(i)).getBPdteAclararCeis());
					}
					
					//OCAP-1402
					if (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS) && Constantes.SI.equals(((OCAPExpedientemcOT) listaMeritos.get(i)).getBPdteAclararCc()))
					{
						((OCAPMeritosForm) form).setBPdteAclarar(((OCAPExpedientemcOT) listaMeritos.get(i)).getBPdteAclararCc());
					}
					
					if (null == ((OCAPExpedientemcOT) listaMeritos.get(i)).getBInvalidadoCc()) {
						((OCAPMeritosForm) form).setBInvalidado(Constantes.NO);
					}else if (Constantes.NO.equals(((OCAPExpedientemcOT) listaMeritos.get(i)).getBInvalidadoCc())) {
						((OCAPMeritosForm) form).setBInvalidado(((OCAPExpedientemcOT) listaMeritos.get(i)).getBInvalidadoCc());
					}
					listadoMeritos.add(expMCOT);
				}
				listadoMeritosTotales.add(listadoMeritos);
			}

			request.setAttribute("resumenCertificados", listadoMeritosTotales);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDocenciaPost: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarDocenciaPost: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarDocenciaPost");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarDocenciaPost(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		ArrayList listaActividades = new ArrayList();
		ArrayList listaMeritos = new ArrayList();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarDocenciaPost: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
			expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
			expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
			if ((((OCAPMeritosForm) form).getNAnnios() != null) && (!"".equals(((OCAPMeritosForm) form).getNAnnios())))
				expedientemcOT.setNAnnios(Integer.valueOf(((OCAPMeritosForm) form).getNAnnios()));
			else
				expedientemcOT.setNAnnios(Integer.valueOf("0"));
			if ((((OCAPMeritosForm) form).getNMeses() != null) && (!"".equals(((OCAPMeritosForm) form).getNMeses())))
				expedientemcOT.setNMeses(Integer.valueOf(((OCAPMeritosForm) form).getNMeses()));
			else
				expedientemcOT.setNMeses(Integer.valueOf("0"));
			expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
			expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

			if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
					&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
				expedientemcOT.setBOpcional(Constantes.SI);
			} else
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
				expedientemcOT.setBOpcional(Constantes.NO);
			}

			listaMeritos = expedientemcLN.buscarPorMeritoId(expedientemcOT, jcylUsuario, Constantes.FLAG_MERITOS_TODOS);
			String expedientesMC = "";
			for (int i = 0; i < listaMeritos.size(); i++)
			{
				if(expedientemcOT.getBOpcional().equals(Constantes.NO)) {
					if (((OCAPExpedientemcOT) listaMeritos.get(i)).getBOpcional().equals(Constantes.NO)) {
						expedientesMC = expedientesMC + String.valueOf(((OCAPExpedientemcOT) listaMeritos.get(i)).getCExpmcId()) + "#";
					}
				}else {
					if (((OCAPExpedientemcOT) listaMeritos.get(i)).getBOpcional().equals(Constantes.SI)) {
						expedientesMC = expedientesMC + String.valueOf(((OCAPExpedientemcOT) listaMeritos.get(i)).getCExpmcId()) + "#";
					}
				}
				
			}

			expedientemcOT.setALugarExpedicion(expedientesMC.substring(0, expedientesMC.length() - 1));

			expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getResumenCertificados());
			expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

			idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));

			OCAPConfigApp.logger.info(getClass().getName() + " modificarDocenciaPost: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarDocenciaPost: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarDocenciaPost");
	}

	public ActionForward controlAccionMeritoSiNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMeritoSiNo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionMeritoSiNo: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoSiNo(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoSiNo(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMeritoSiNo: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMeritoSiNo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoSiNo".equals(vuelta.getName()))
			{
				return irInsertarMeritoSiNo(mapping, form, request, response);
			}
			if ("irModificarMeritoSiNo".equals(vuelta.getName()))
			{
				return irModificarMeritoSiNo(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarMeritoSiNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMeritoSiNo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMeritoSiNo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMeritoSiNo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarMeritoSiNo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarMeritoSiNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMeritoSiNo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarMeritoSiNo(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setBParticipacion(((OCAPMeritosForm) form).getBParticipacion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}

				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarMeritoSiNo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMeritoSiNo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMeritoSiNo");
	}

	public ActionMessages validarMeritoSiNo(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		try
		{
			if ((((OCAPMeritosForm) form).getBParticipacion() == null) || ("".equals(((OCAPMeritosForm) form).getBParticipacion())))
			{
				messages.add("bParticipacion", new ActionMessage("errors.required", "Participación"));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarMeritoSiNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMeritoSiNo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getBParticipacion() == null)
				((OCAPMeritosForm) form).setBParticipacion(expedientemcOT.getBParticipacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMeritoSiNo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMeritoSiNo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarMeritoSiNo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarMeritoSiNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMeritoSiNo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarMeritoSiNo(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setBParticipacion(((OCAPMeritosForm) form).getBParticipacion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarMeritoSiNo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMeritoSiNo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMeritoSiNo");
	}

	public ActionForward controlAccionParticipaProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionParticipaProyecto: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionParticipaProyecto: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarParticipaProyecto(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarParticipaProyecto(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionParticipaProyecto: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionParticipaProyecto: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarParticipaProyecto".equals(vuelta.getName()))
			{
				return irInsertarParticipaProyecto(mapping, form, request, response);
			}
			if ("irModificarParticipaProyecto".equals(vuelta.getName()))
			{
				return irModificarParticipaProyecto(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarParticipaProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarParticipaProyecto: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarParticipaProyecto: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarParticipaProyecto: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarParticipaProyecto");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarParticipaProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;

		ArrayList listaActividades = null;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarParticipaProyecto: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			messages = validarParticipaProyecto(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarParticipaProyecto: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarParticipaProyecto");
	}

	public ActionMessages validarParticipaProyecto(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();

		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getListaActividades() != null) && (((OCAPMeritosForm) form).getListaActividades().size() > 0)
					&& ((((OCAPMeritosForm) form).getCActividadId() == null) || ("".equals(((OCAPMeritosForm) form).getCActividadId()))))
				messages.add("cActividadId", new ActionMessage("errors.required", "Actividad"));
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
				messages.add("fFinalizacion", new ActionMessage("error.required", "Fecha de Finalización"));
			else if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
				messages.add("aOrganizador", new ActionMessage("errors.required", "Entidad que lo convoca"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarParticipaProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarParticipaProyecto: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarParticipaProyecto: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarParticipaProyecto: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarParticipaProyecto");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarParticipaProyecto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarParticipaProyecto: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			messages = validarParticipaProyecto(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarParticipaProyecto: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarParticipaProyecto");
	}

	public ActionForward controlAccionParticipaProyectoInvestigacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionParticipaProyectoInvestigacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionParticipaProyectoInvestigacion: ACCION: "
					+ ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarParticipaProyectoInvestigacion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarParticipaProyectoInvestigacion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionParticipaProyectoInvestigacion: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionParticipaProyectoInvestigacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarParticipaProyectoInvestigacion".equals(vuelta.getName()))
			{
				return irInsertarParticipaProyectoInvestigacion(mapping, form, request, response);
			}
			if ("irModificarParticipaProyectoInvestigacion".equals(vuelta.getName()))
			{
				return irModificarParticipaProyectoInvestigacion(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarParticipaProyectoInvestigacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarParticipaProyectoInvestigacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarParticipaProyectoInvestigacion: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarParticipaProyectoInvestigacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarParticipaProyectoInvestigacion");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarParticipaProyectoInvestigacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		long cExpmcId = 0L;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarParticipaProyectoInvestigacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarParticipaProyectoInvestigacion(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((expedientemcOT.getCActividadId() != null) && (12 == expedientemcOT.getCActividadId().intValue()))
				{
					expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
					expedientemcOT.setBAcreditado(Constantes.SI);
				}
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}

				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarParticipaProyectoInvestigacion: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarParticipaProyectoInvestigacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarParticipaProyectoInvestigacion");
	}

	public ActionMessages validarParticipaProyectoInvestigacion(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getCActividadId() == null) || ("".equals(((OCAPMeritosForm) form).getCActividadId())))
			{
				messages.add("cActividadId", new ActionMessage("errors.required", "Actividad"));
			}
			if ((((OCAPMeritosForm) form).getCActividadId() != null) && (12 == ((OCAPMeritosForm) form).getCActividadId().intValue())
					&& ((((OCAPMeritosForm) form).getNHoras() == null) || ("".equals(((OCAPMeritosForm) form).getNHoras()))))
			{
				messages.add("nHoras", new ActionMessage("errors.required", "Nº Horas"));
			}
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Comisión de Investigación del Centro o Gerencia"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarParticipaProyectoInvestigacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarParticipaProyectoInvestigacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getNHoras() == null)
				((OCAPMeritosForm) form)
						.setNHoras(expedientemcOT.getNHoras().intValue() == 0 ? "0" : expedientemcOT.getNHoras().toString());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarParticipaProyectoInvestigacion: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarParticipaProyectoInvestigacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarParticipaProyectoInvestigacion");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarParticipaProyectoInvestigacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarParticipaProyectoInvestigacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarParticipaProyectoInvestigacion(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((expedientemcOT.getCActividadId() != null) && (12 == expedientemcOT.getCActividadId().intValue()))
				{
					expedientemcOT.setNHoras(Integer.valueOf(((OCAPMeritosForm) form).getNHoras()));
					expedientemcOT.setBAcreditado(Constantes.SI);
				}
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarParticipaProyectoInvestigacion: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarParticipaProyectoInvestigacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarParticipaProyectoInvestigacion");
	}

	public ActionForward controlAccionJefeServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionJefeServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionJefeServicio: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarJefeServicio(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarJefeServicio(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionJefeServicio: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionJefeServicio: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarJefeServicio(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarJefeServicio(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarJefeServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarJefeServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarJefeServicio: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarJefeServicio");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarJefeServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarJefeServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarJefeServicio: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarJefeServicio");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionResponsableUnidadTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableUnidadTutor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionResponsableUnidadTutor: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarResponsableUnidadTutor(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarResponsableUnidadTutor(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableUnidadTutor: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableUnidadTutor: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarResponsableUnidadTutor".equals(vuelta.getName()))
			{
				return irInsertarResponsableUnidadTutor(mapping, form, request, response);
			}
			if ("irModificarResponsableUnidadTutor".equals(vuelta.getName()))
			{
				return irModificarResponsableUnidadTutor(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarResponsableUnidadTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarResponsableUnidadTutor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarResponsableUnidadTutor(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarResponsableUnidadTutor: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarResponsableUnidadTutor");
	}

	public ActionForward modificarResponsableUnidadTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableUnidadTutor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarResponsableUnidadTutor(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableUnidadTutor: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarResponsableUnidadTutor");
	}

	public ActionMessages validarResponsableUnidadTutor(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);

			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
				messages.add("dTitulo", new ActionMessage("errors.required", "Unidad"));
			if ((((OCAPMeritosForm) form).getCActividadId() == null) || (((OCAPMeritosForm) form).getCActividadId().intValue() == 0))
				messages.add("cActividadId", new ActionMessage("errors.required", "Tutor"));
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", " Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
				messages.add("fFinalizacion", new ActionMessage("error.required", "Fecha de Finalización"));
			else if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irInsertarResponsableUnidadTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableUnidadTutor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableUnidadTutor: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableUnidadTutor");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarResponsableUnidadTutor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableUnidadTutor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableUnidadTutor: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableUnidadTutor: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableUnidadTutor");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionResponsableMaterialUnidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableMaterialUnidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Unidad/Servicio");

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionResponsableMaterialUnidad: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableMaterialUnidad: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableMaterialUnidad: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarResponsableMaterialUnidad(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarResponsableMaterialUnidad(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarResponsableMaterialUnidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableMaterialUnidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableMaterialUnidad: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableMaterialUnidad");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarResponsableMaterialUnidad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableMaterialUnidad: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableMaterialUnidad: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableMaterialUnidad: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableMaterialUnidad");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionResponsableMaterialServ(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableMaterialServ: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Servicio");

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionResponsableMaterialServ: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableMaterialServ: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableMaterialServ: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarResponsableMaterialServ(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarResponsableMaterialServ(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarResponsableMaterialServ(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableMaterialServ: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableMaterialServ: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableMaterialServ");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarResponsableMaterialServ(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableMaterialServ: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableMaterialServ: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableMaterialServ: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableMaterialServ");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionResponsableDocencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableDocencia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Unidad/Servicio/Centro");

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionResponsableDocencia: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			String dTipoMeritoOriginal = ((OCAPMeritosForm) form).getDTipoMerito();
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = insertarResponsableDocencia(mapping, form, request, response);
			} else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarResponsableDocencia(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableDocencia: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (dTipoMeritoOriginal == null ? "" : dTipoMeritoOriginal));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableDocencia: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarResponsableDocencia".equals(vuelta.getName()))
			{
				return irInsertarResponsableDocencia(mapping, form, request, response);
			}
			if ("irModificarResponsableDocencia".equals(vuelta.getName()))
			{
				return irModificarResponsableDocencia(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarResponsableDocencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableDocencia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableDocencia: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableDocencia");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarResponsableDocencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarResponsableDocencia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarResponsableDocencia(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarResponsableDocencia: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarResponsableDocencia");
	}

	public ActionMessages validarResponsableDocencia(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);

			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Unidad / Servicio / Centro"));
			}
			if ((((OCAPMeritosForm) form).getFAnnio() == null) || (((OCAPMeritosForm) form).getFAnnio().intValue() == 0))
			{
				messages.add("fAnnio", new ActionMessage("errors.required", "Año de inicio"));
			} else if (((OCAPMeritosForm) form).getFAnnio().toString().length() < 4)
			{
				messages.add("fAnnio", new ActionMessage("error.maskmsg", "Año de inicio"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFAnnio().toString(), pos);
				if (dFExpedicion == null)
				{
					messages.add("fAnnio", new ActionMessage("error.maskmsg", "Año de inicio"));
				} else
				{
					Date annioActual = new Date();
					if (dFExpedicion.after(annioActual))
						messages.add("fAnnio", new ActionMessage("error.annioPosterior", "Año de inicio"));
					else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFAnnio().toString(), "fAnnio"));
					}
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarResponsableDocencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableDocencia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
			{
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			}

			if (((OCAPMeritosForm) form).getFAnnio() == null)
			{
				((OCAPMeritosForm) form).setFAnnio(expedientemcOT.getFAnnio());
			}
			((OCAPMeritosForm) form).setFAnnioFin(new Integer(expedientemcOT.getFAnnio().intValue() + 1));

			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.MODIFICAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableDocencia: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableDocencia");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarResponsableDocencia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableDocencia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarResponsableDocencia(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableDocencia: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarResponsableDocencia");
	}

	public ActionForward controlAccionResponsableComisionCal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableComisionCal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Centro/Servicio");

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionResponsableComisionCal: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableComisionCal: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableComisionCal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarResponsableComisionCal(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarResponsableComisionCal(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarResponsableComisionCal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableComisionCal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableComisionCal: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableComisionCal");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarResponsableComisionCal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableComisionCal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableComisionCal: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableComisionCal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableComisionCal");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionResponsableCartServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableCartServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionResponsableCartServicio: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarResponsableCartServicio(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarResponsableCartServicio(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableCartServicio: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableCartServicio: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarResponsableCartServicio".equals(vuelta.getName()))
			{
				return irInsertarResponsableCartServicio(mapping, form, request, response);
			}
			if ("irModificarResponsableCartServicio".equals(vuelta.getName()))
			{
				return irModificarResponsableCartServicio(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarResponsableCartServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableCartServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			ArrayList listadoGerencias = new ArrayList();
			Map parametros = jcylUsuario.getParametrosUsuario();
			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
			listadoGerencias = gerenciasLN.listadoOCAPGerenciasMasOtros();
			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableCartServicio: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableCartServicio");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarResponsableCartServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableCartServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getAObjetivo() == null)
				((OCAPMeritosForm) form).setAObjetivo(expedientemcOT.getAObjetivo());
			if (((OCAPMeritosForm) form).getACargo() == null)
				((OCAPMeritosForm) form).setACargo(expedientemcOT.getACargo());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			ArrayList listadoGerencias = new ArrayList();
			Map parametros = jcylUsuario.getParametrosUsuario();
			OCAPGerenciasLN gerenciasLN = new OCAPGerenciasLN(jcylUsuario);
			OCAPGerenciasOT gerenciasOT = new OCAPGerenciasOT();
			listadoGerencias = gerenciasLN.listadoOCAPGerenciasMasOtros();

			session.setAttribute(Constantes.COMBO_GERENCIA, listadoGerencias);

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.MODIFICAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableCartServicio: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableCartServicio");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarResponsableCartServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarResponsableCartServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarResponsableCartServicio(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if (((OCAPMeritosForm) form).getDTitulo() != null)
					expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAObjetivo(((OCAPMeritosForm) form).getAObjetivo());
				expedientemcOT.setACargo(((OCAPMeritosForm) form).getACargo());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarResponsableCartServicio: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarResponsableCartServicio");
	}

	public ActionMessages validarResponsableCartServicio(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Gerencia"));
			}
			if (("Otros".equals(((OCAPMeritosForm) form).getAOrganizador()))
					&& ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo()))))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Nombre"));
			}
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Asunción"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Asunción"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Asunción"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Asunción"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			} else if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else
			{
				dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
				if (dFFin == null)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
			{
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
			}
			if ((((OCAPMeritosForm) form).getAObjetivo() == null) || ("".equals(((OCAPMeritosForm) form).getAObjetivo())))
			{
				messages.add("aObjetivo", new ActionMessage("errors.required", "Programa Asistencial"));
			}
			if ((((OCAPMeritosForm) form).getACargo() == null) || ("".equals(((OCAPMeritosForm) form).getACargo())))
			{
				messages.add("aCargo", new ActionMessage("errors.required", "Cargo o puesto que desempeña"));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward modificarResponsableCartServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableCartServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarResponsableCartServicio(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if (((OCAPMeritosForm) form).getDTitulo() != null)
					expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				else
					expedientemcOT.setDTitulo("");
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAObjetivo(((OCAPMeritosForm) form).getAObjetivo());
				expedientemcOT.setACargo(((OCAPMeritosForm) form).getACargo());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableCartServicio: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarResponsableCartServicio");
	}

	public ActionForward modificarResponsableCartServici(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableCartServici: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarResponsableCartServicio(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableCartServici: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarResponsableCartServici: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarResponsableCartServici");
	}

	public ActionForward controlAccionResponsableActEspecifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableActEspecifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Actividad");

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionResponsableActEspecifica: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableActEspecifica: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableActEspecifica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarResponsableActEspecifica(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarResponsableActEspecifica(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarResponsableActEspecifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableActEspecifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableActEspecifica: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableActEspecifica");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarResponsableActEspecifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableActEspecifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableActEspecifica: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableActEspecifica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableActEspecifica");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionResponsableActAE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableActAE: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Actividad");

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionResponsableActAE: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableActAE: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionResponsableActAE: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarResponsableActAE(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarResponsableActAE(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarResponsableActAE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableActAE: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarResponsableActAE: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarResponsableActAE");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarResponsableActAE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableActAE: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableActAE: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarResponsableActAE: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarResponsableActAE");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionOrganizadorDifusor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOrganizadorDifusor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Titulo");

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionOrganizadorDifusor: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOrganizadorDifusor: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOrganizadorDifusor: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarOrganizadorDifusor(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarOrganizadorDifusor(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarOrganizadorDifusor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOrganizadorDifusor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOrganizadorDifusor: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarOrganizadorDifusor");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarOrganizadorDifusor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOrganizadorDifusor: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOrganizadorDifusor: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOrganizadorDifusor: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarOrganizadorDifusor");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionMiembroTribunal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroTribunal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionMiembroTribunal: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMiembroTribunal(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMiembroTribunal(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroTribunal: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroTribunal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMiembroTribunal".equals(vuelta.getName()))
			{
				return irInsertarMiembroTribunal(mapping, form, request, response);
			}
			if ("irModificarMiembroTribunal".equals(vuelta.getName()))
			{
				return irModificarMiembroTribunal(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarMiembroTribunal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroTribunal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroTribunal: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarMiembroTribunal");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarMiembroTribunal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroTribunal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarMiembroTribunal(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
				{
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				}

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroTribunal: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMiembroTribunal");
	}

	public ActionForward modificarMiembroTribunal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroTribunal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarMiembroTribunal(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroTribunal: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMiembroTribunal");
	}

	public ActionMessages validarMiembroTribunal(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
				messages.add("dTitulo", new ActionMessage("errors.required", "Boletín"));
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Publicación"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Publicación"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Publicación"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Publicación"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFInicio().toString(), "fInicio"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarMiembroTribunal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroTribunal: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
			{
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			}

			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroTribunal: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroTribunal: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarMiembroTribunal");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionMiembroConsejo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroConsejo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "ZBS/AS");

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionMiembroConsejo: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroConsejo: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroConsejo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarMiembroConsejo(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarMiembroConsejo(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarMiembroConsejo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroConsejo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroConsejo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarMiembroConsejo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarMiembroConsejo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroConsejo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroConsejo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroConsejo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarMiembroConsejo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionMiembroComision(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroComision: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Comision");

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionMiembroComision: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroComision: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroComision: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarMiembroComision(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarMiembroComision(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarMiembroComision(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroComision: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroComision: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarMiembroComision");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarMiembroComision(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroComision: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroComision: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroComision: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarMiembroComision");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionCoordinaEAP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCoordinaEAP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionCoordinaEAP: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarAccionCoordinaEAP(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarAccionCoordinaEAP(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCoordinaEAP: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCoordinaEAP: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarCoordinaEAP(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarCoordinaEAP(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarAccionCoordinaEAP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarAccionCoordinaEAP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarAccionCoordinaEAP(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " insertarAccionCoordinaEAP: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMeritoGestion");
	}

	public ActionMessages validarAccionCoordinaEAP(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Designación"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Designación"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Designación"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Designación"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
				messages.add("fFinalizacion", new ActionMessage("error.required", "Fecha de Finalización"));
			else if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward modificarAccionCoordinaEAP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarAccionCoordinaEAP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarAccionCoordinaEAP(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());
				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);
				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));
				OCAPConfigApp.logger.info(getClass().getName() + " modificarAccionCoordinaEAP: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMeritoGestion");
	}

	public ActionForward irInsertarCoordinaEAP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCoordinaEAP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.INSERTAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCoordinaEAP: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarCoordinaEAP");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarCoordinaEAP(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCoordinaEAP: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());
			request.setAttribute("tipoAccion", Constantes.MODIFICAR);
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCoordinaEAP: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarCoordinaEAP");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionCoResponsableMaterialServ(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCoResponsableMaterialServ: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Servicio");

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionCoResponsableMaterialServ: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCoResponsableMaterialServ: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCoResponsableMaterialServ: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarCoResponsableMaterialServ(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarCoResponsableMaterialServ(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarCoResponsableMaterialServ(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCoResponsableMaterialServ: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCoResponsableMaterialServ: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCoResponsableMaterialServ: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarCoResponsableMaterialServ");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarCoResponsableMaterialServ(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCoResponsableMaterialServ: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCoResponsableMaterialServ: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCoResponsableMaterialServ: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarCoResponsableMaterialServ");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionBResponsableCO(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBResponsableCO: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			session.setAttribute("Procedencia", "Actividad");

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionBResponsableCO: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMeritoGestion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMeritoGestion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBResponsableCO: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionBResponsableCO: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMeritoGestion".equals(vuelta.getName()))
			{
				return irInsertarBResponsableCO(mapping, form, request, response);
			}
			if ("irModificarMeritoGestion".equals(vuelta.getName()))
			{
				return irModificarBResponsableCO(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarBResponsableCO(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBResponsableCO: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBResponsableCO: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarBResponsableCO: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarBResponsableCO");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarBResponsableCO(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBResponsableCO: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBResponsableCO: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarBResponsableCO: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarBResponsableCO");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarMeritoGestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMeritoGestion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarMeritoGestion(form, (String) session.getAttribute("Procedencia"));

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarMeritoGestion: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMeritoGestion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMeritoGestion");
	}

	public ActionForward insertarJefeServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarJefeServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarJefeServicio(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarJefeServicio: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarJefeServicio: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMeritoGestion");
	}

	public ActionMessages validarJefeServicio(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Designación"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Designación"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Designación"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Designación"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward modificarJefeServicio(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarJefeServicio: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarJefeServicio(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarJefeServicio: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarJefeServicio: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMeritoGestion");
	}

	public ActionMessages validarMeritoGestion(ActionForm form, String titulo) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", titulo));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward modificarMeritoGestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMeritoGestion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarMeritoGestion(form, (String) session.getAttribute("Procedencia"));

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarMeritoGestion: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMeritoGestion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMeritoGestion");
	}

	public ActionForward controlAccionMiembroGrupo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroGrupo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionMiembroGrupo: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMiembroGrupo(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMiembroGrupo(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroGrupo: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroGrupo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMiembroGrupo".equals(vuelta.getName()))
			{
				return irInsertarMiembroGrupo(mapping, form, request, response);
			}
			if ("irModificarMiembroGrupo".equals(vuelta.getName()))
			{
				return irModificarMiembroGrupo(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarMiembroGrupo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroGrupo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroGrupo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroGrupo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarMiembroGrupo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarMiembroGrupo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroGrupo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarMiembroGrupo(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setACargo(((OCAPMeritosForm) form).getACargo());
				expedientemcOT.setAObjetivo(((OCAPMeritosForm) form).getAObjetivo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroGrupo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroGrupo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMiembroGrupo");
	}

	public ActionMessages validarMiembroGrupo(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Grupo de trabajo"));
			}
			if ((((OCAPMeritosForm) form).getACargo() == null) || ("".equals(((OCAPMeritosForm) form).getACargo())))
			{
				messages.add("aCargo", new ActionMessage("errors.required", "Cargo"));
			}
			if ((((OCAPMeritosForm) form).getAObjetivo() == null) || ("".equals(((OCAPMeritosForm) form).getAObjetivo())))
			{
				messages.add("aObjetivo", new ActionMessage("errors.required", "Objetivo"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarMiembroGrupo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroGrupo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getACargo() == null)
				((OCAPMeritosForm) form).setACargo(expedientemcOT.getACargo());
			if (((OCAPMeritosForm) form).getAObjetivo() == null)
				((OCAPMeritosForm) form).setAObjetivo(expedientemcOT.getAObjetivo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroGrupo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroGrupo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarMiembroGrupo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarMiembroGrupo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroGrupo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarMiembroGrupo(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setACargo(((OCAPMeritosForm) form).getACargo());
				expedientemcOT.setAObjetivo(((OCAPMeritosForm) form).getAObjetivo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroGrupo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroGrupo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMiembroGrupo");
	}

	public ActionForward controlAccionTesis(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionTesis: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionTesis: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarTesis(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarTesis(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionTesis: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionTesis: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarTesis".equals(vuelta.getName()))
			{
				return irInsertarTesis(mapping, form, request, response);
			}
			if ("irModificarTesis".equals(vuelta.getName()))
			{
				return irModificarTesis(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarTesis(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarTesis: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarTesis: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarTesis: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarTesis");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarTesis(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarTesis: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarTesis(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarTesis: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarTesis: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarTesis");
	}

	public ActionMessages validarTesis(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Universidad"));
			}
			if ("Tesis Doctoral".equals(((OCAPMeritosForm) form).getDTipoMerito()))
			{
				SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
				df.setLenient(false);
				ParsePosition pos = new ParsePosition(0);
				if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
				{
					messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha de defensa de la Tesis"));
				} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
				{
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de defensa de la Tesis"));
				} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de defensa de la Tesis"));
				} else
				{
					dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
					if (dFExpedicion == null)
						messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de defensa de la Tesis"));
					else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
					}
				}

			} else if (("Dirección de Tesis Doctoral".equals(((OCAPMeritosForm) form).getDTipoMerito()))
					|| ("Codirección de Tesis Doctoral".equals(((OCAPMeritosForm) form).getDTipoMerito()))
					|| ("Suficiencia Investigadora".equals(((OCAPMeritosForm) form).getDTipoMerito())))
			{
				String titulo = null;
				if (((OCAPMeritosForm) form).getDTipoMerito().equals("Suficiencia Investigadora"))
					titulo = "Año de defensa";
				else
				{
					titulo = "Año de la dirección";
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy");
				df.setLenient(false);
				ParsePosition pos = new ParsePosition(0);
				if ((((OCAPMeritosForm) form).getFAnnio() == null) || (((OCAPMeritosForm) form).getFAnnio().intValue() == 0))
				{
					messages.add("fAnnio", new ActionMessage("errors.required", titulo));
				} else if (((OCAPMeritosForm) form).getFAnnio().toString().length() < 4)
				{
					messages.add("fAnnio", new ActionMessage("error.maskmsg", titulo));
				} else
				{
					dFExpedicion = df.parse(((OCAPMeritosForm) form).getFAnnio().toString(), pos);
					if (dFExpedicion == null)
					{
						messages.add("fAnnio", new ActionMessage("error.maskmsg", titulo));
					} else
					{
						Date annioActual = new Date();
						Date annioAntiguo = new Date();
						Calendar c = Calendar.getInstance();
						c.add(Calendar.YEAR, -100);
						if (dFExpedicion.after(annioActual)) {
							messages.add("fAnnio", new ActionMessage("error.annioPosterior", titulo));
						}else if (dFExpedicion.before(c.getTime())){
							//http://xamp.sacyl.es/jira/browse/OCAP-1477
							messages.add("fAnnio", new ActionMessage("error.annioAnt", titulo));
						}else
						{
							messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFAnnio().toString(), "fAnnio"));
						}
					}
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarTesis(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarTesis: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getFAnnio() == null)
				((OCAPMeritosForm) form).setFAnnio(expedientemcOT.getFAnnio());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarTesis: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarTesis: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarTesis");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarTesis(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarTesis: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarTesis(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarTesis: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarTesis: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarTesis");
	}

	public ActionForward controlAccionRevistaCientifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevistaCientifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionRevistaCientifica: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarRevistaCientifica(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarRevistaCientifica(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevistaCientifica: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevistaCientifica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarRevistaCientifica".equals(vuelta.getName()))
			{
				return irInsertarRevistaCientifica(mapping, form, request, response);
			}
			if ("irModificarRevistaCientifica".equals(vuelta.getName()))
			{
				return irModificarRevistaCientifica(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarRevistaCientifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevistaCientifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevistaCientifica: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevistaCientifica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarRevistaCientifica");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarRevistaCientifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarRevistaCientifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarRevistaCientifica(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarRevistaCientifica: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarRevistaCientifica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarRevistaCientifica");
	}

	public ActionMessages validarRevistaCientifica(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha de revisión"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de revisión"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de revisión"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de revisión"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " validarRevistaCientifica: ERROR: " + e.toString());
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarRevistaCientifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevistaCientifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getFAnnio() == null)
				((OCAPMeritosForm) form).setFAnnio(expedientemcOT.getFAnnio());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevistaCientifica: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevistaCientifica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarRevistaCientifica");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarRevistaCientifica(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarRevistaCientifica: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarRevistaCientifica(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarRevistaCientifica: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarRevistaCientifica: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarRevistaCientifica");
	}

	public ActionForward controlAccionPatentesModelos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPatentesModelos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionPatentesModelos: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarPatentesModelos(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarPatentesModelos(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPatentesModelos: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPatentesModelos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarPatentesModelos".equals(vuelta.getName()))
			{
				return irInsertarPatentesModelos(mapping, form, request, response);
			}
			if ("irModificarPatentesModelos".equals(vuelta.getName()))
			{
				return irModificarPatentesModelos(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarPatentesModelos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPatentesModelos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPatentesModelos: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPatentesModelos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarPatentesModelos");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarPatentesModelos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPatentesModelos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarPatentesModelos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarPatentesModelos: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPatentesModelos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarPatentesModelos");
	}

	public ActionMessages validarPatentesModelos(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Nombre de patente y/o modelo"));
			}
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha "));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha "));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " validarPatentesModelos: ERROR: " + e.toString());
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarPatentesModelos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPatentesModelos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getFAnnio() == null)
				((OCAPMeritosForm) form).setFAnnio(expedientemcOT.getFAnnio());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPatentesModelos: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPatentesModelos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarPatentesModelos");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarPatentesModelos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPatentesModelos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarPatentesModelos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarPatentesModelos: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPatentesModelos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarPatentesModelos");
	}

	public ActionForward controlAccionEvaluadorProyectos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEvaluadorProyectos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionEvaluadorProyectos: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarEvaluadorProyectos(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarEvaluadorProyectos(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEvaluadorProyectos: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionEvaluadorProyectos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarEvaluadorProyectos".equals(vuelta.getName()))
			{
				return irInsertarEvaluadorProyectos(mapping, form, request, response);
			}
			if ("irModificarEvaluadorProyectos".equals(vuelta.getName()))
			{
				return irModificarEvaluadorProyectos(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarEvaluadorProyectos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEvaluadorProyectos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEvaluadorProyectos: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarEvaluadorProyectos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarEvaluadorProyectos");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarEvaluadorProyectos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarEvaluadorProyectos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarEvaluadorProyectos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarEvaluadorProyectos: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarEvaluadorProyectos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarEvaluadorProyectos");
	}

	public ActionMessages validarEvaluadorProyectos(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título del proyecto"));
			}
			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Agencia pública"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarEvaluadorProyectos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEvaluadorProyectos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());
			if ((mcOT.getDNombre() != null) && (mcOT.getDNombre().length() > 100))
				((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre().substring(0, 100) + "...");
			else
				((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEvaluadorProyectos: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarEvaluadorProyectos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarEvaluadorProyectos");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarEvaluadorProyectos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarEvaluadorProyectos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarEvaluadorProyectos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarEvaluadorProyectos: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarEvaluadorProyectos: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarEvaluadorProyectos");
	}

	public ActionForward controlAccionMiembroComite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroComite: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionMiembroComite: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarMiembroComite(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarMiembroComite(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroComite: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionMiembroComite: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarMiembroComite".equals(vuelta.getName()))
			{
				return irInsertarMiembroComite(mapping, form, request, response);
			}
			if ("irModificarMiembroComite".equals(vuelta.getName()))
			{
				return irModificarMiembroComite(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarMiembroComite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroComite: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroComite: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarMiembroComite: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarMiembroComite");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarMiembroComite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroComite: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarMiembroComite(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroComite: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarMiembroComite: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarMiembroComite");
	}

	public ActionMessages validarMiembroComite(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Nombre del Congreso"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarMiembroComite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroComite: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroComite: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarMiembroComite: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarMiembroComite");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarMiembroComite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroComite: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarMiembroComite(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroComite: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarMiembroComite: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarMiembroComite");
	}

	public ActionForward controlAccionPublicacionRevistaProf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPublicacionRevistaProf: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionPublicacionRevistaProf: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarPublicacionRevistaProf(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarPublicacionRevistaProf(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPublicacionRevistaProf: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPublicacionRevistaProf: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarPublicacionRevistaProf".equals(vuelta.getName()))
			{
				return irInsertarPublicacionRevistaProf(mapping, form, request, response);
			}
			if ("irModificarPublicacionRevistaProf".equals(vuelta.getName()))
			{
				return irModificarPublicacionRevistaProf(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarPublicacionRevistaProf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPublicacionRevistaProf: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPublicacionRevistaProf: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPublicacionRevistaProf: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarPublicacionRevistaProf");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarPublicacionRevistaProf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPublicacionRevistaProf: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarPublicacionRevistaProf(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setANombreRevista(((OCAPMeritosForm) form).getANombreRevista());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarPublicacionRevistaProf: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPublicacionRevistaProf: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarPublicacionRevistaProf");
	}

	public ActionMessages validarPublicacionRevistaProf(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título de la publicación"));
			}
			if ((((OCAPMeritosForm) form).getANombreRevista() == null) || ("".equals(((OCAPMeritosForm) form).getANombreRevista())))
			{
				messages.add("aNombreRevista", new ActionMessage("errors.required", "Nombre de la revista"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha de publicación"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de publicación"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de publicación"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de publicación"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarPublicacionRevistaProf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPublicacionRevistaProf: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getANombreRevista() == null)
				((OCAPMeritosForm) form).setANombreRevista(expedientemcOT.getANombreRevista());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPublicacionRevistaProf: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPublicacionRevistaProf: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarPublicacionRevistaProf");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarPublicacionRevistaProf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPublicacionRevistaProf: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarPublicacionRevistaProf(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setANombreRevista(((OCAPMeritosForm) form).getANombreRevista());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarPublicacionRevistaProf: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPublicacionRevistaProf: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarPublicacionRevistaProf");
	}

	public ActionForward controlAccionExponeCasosSesCli(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionExponeCasosSesCli: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionExponeCasosSesCli: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarExponeCasosSesCli(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarExponeCasosSesCli(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionExponeCasosSesCli: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionExponeCasosSesCli: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarExponeCasosSesCli".equals(vuelta.getName()))
			{
				return irInsertarExponeCasosSesCli(mapping, form, request, response);
			}
			if ("irModificarExponeCasosSesCli".equals(vuelta.getName()))
			{
				return irModificarExponeCasosSesCli(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarExponeCasosSesCli(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarExponeCasosSesCli: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarExponeCasosSesCli: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarExponeCasosSesCli: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarExponeCasosSesCli");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarExponeCasosSesCli(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarExponeCasosSesCli: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarExponeCasosYProtocolos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setBParticipacion(((OCAPMeritosForm) form).getBParticipacion());
				expedientemcOT.setANombreRevista(((OCAPMeritosForm) form).getANombreRevista());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarExponeCasosSesCli: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarExponeCasosSesCli: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarExponeCasosSesCli");
	}

	public ActionForward irModificarExponeCasosSesCli(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarExponeCasosSesCli: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarExponeCasosSesCli: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarExponeCasosSesCli: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarExponeCasosSesCli");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarExponeCasosSesCli(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarExponeCasosSesCli: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarExponeCasosYProtocolos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarExponeCasosSesCli: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarExponeCasosSesCli: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarExponeCasosSesCli");
	}

	public ActionForward controlAccionRevisaProtocoloGuia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevisaProtocoloGuia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionRevisaProtocoloGuia: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarRevisaProtocolo(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarRevisaProtocolo(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevisaProtocoloGuia: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevisaProtocoloGuia: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarRevisaProtocolo".equals(vuelta.getName()))
			{
				return irInsertarRevisaProtocoloGuia(mapping, form, request, response);
			}
			if ("irModificarRevisaProtocolo".equals(vuelta.getName()))
			{
				return irModificarRevisaProtocoloGuia(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarRevisaProtocoloGuia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevisaProtocoloGuia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevisaProtocoloGuia: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevisaProtocoloGuia: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarRevisaProtocoloGuia");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarRevisaProtocoloGuia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevisaProtocoloGuia: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevisaProtocoloGuia: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevisaProtocoloGuia: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarRevisaProtocoloGuia");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionCreaProtocolo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCreaProtocolo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionCreaProtocolo: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarRevisaProtocolo(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarRevisaProtocolo(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCreaProtocolo: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCreaProtocolo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarCreaProtocolo".equals(vuelta.getName()))
			{
				return irInsertarCreaProtocolo(mapping, form, request, response);
			}
			if ("irModificarCreaProtocolo".equals(vuelta.getName()))
			{
				return irModificarCreaProtocolo(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarCreaProtocolo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCreaProtocolo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCreaProtocolo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCreaProtocolo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarCreaProtocolo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarCreaProtocolo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCreaProtocolo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCreaProtocolo: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCreaProtocolo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarCreaProtocolo");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward controlAccionRevisaProtocoloProced(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevisaProtocoloProced: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger.info(
					getClass().getName() + " controlAccionRevisaProtocoloProced: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarRevisaProtocolo(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarRevisaProtocolo(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevisaProtocoloProced: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionRevisaProtocoloProced: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarRevisaProtocolo".equals(vuelta.getName()))
			{
				return irInsertarRevisaProtocoloProced(mapping, form, request, response);
			}
			if ("irModificarRevisaProtocolo".equals(vuelta.getName()))
			{
				return irModificarRevisaProtocoloProced(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarRevisaProtocoloProced(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevisaProtocoloProced: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevisaProtocoloProced: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarRevisaProtocoloProced: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarRevisaProtocoloProced");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarRevisaProtocoloProced(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevisaProtocoloProced: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevisaProtocoloProced: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarRevisaProtocoloProced: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarRevisaProtocoloProced");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarRevisaProtocolo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarRevisaProtocolo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validaProtocolos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarRevisaProtocolo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarRevisaProtocolo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		if (Constantes.MC_CREA_PROTOCOLO.equals(((OCAPMeritosForm) form).getCTipoMerito()))
		{
			return mapping.findForward("irInsertarCreaProtocolo");
		}

		return mapping.findForward("irInsertarRevisaProtocolo");
	}

	public ActionForward modificarRevisaProtocolo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarRevisaProtocolo: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validaProtocolos(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarRevisaProtocolo: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarRevisaProtocolo: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		if (Constantes.MC_CREA_PROTOCOLO.equals(((OCAPMeritosForm) form).getCTipoMerito()))
		{
			return mapping.findForward("irModificarCreaProtocolo");
		}

		return mapping.findForward("irModificarRevisaProtocolo");
	}

	public ActionMessages validarExponeCasosYProtocolos(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			} else if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else
			{
				dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
				if (dFFin == null)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionMessages validaProtocolos(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
			}

			if (Constantes.MC_CREA_PROTOCOLO.equals(((OCAPMeritosForm) form).getCTipoMerito()))
			{
				if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
				{
					messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!((OCAPMeritosForm) form).getFFinalizacion().equals("")))
			{
				if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward controlAccionPublicacionIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPublicacionIndex: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionPublicacionIndex: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarPublicacionIndex(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarPublicacionIndex(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPublicacionIndex: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPublicacionIndex: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarPublicacionIndex".equals(vuelta.getName()))
			{
				return irInsertarPublicacionIndex(mapping, form, request, response);
			}
			if ("irModificarPublicacionIndex".equals(vuelta.getName()))
			{
				return irModificarPublicacionIndex(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarPublicacionIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPublicacionIndex: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPublicacionIndex: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPublicacionIndex: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarPublicacionIndex");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarPublicacionIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPublicacionIndex: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarPublicacionIndex(form, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCFactorId(((OCAPMeritosForm) form).getCFactorId());
				expedientemcOT.setCTipoId(((OCAPMeritosForm) form).getCTipoId());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarPublicacionIndex: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPublicacionIndex: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarPublicacionIndex");
	}

	public ActionMessages validarPublicacionIndex(ActionForm form, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			ArrayList listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(
					new Integer(((OCAPMeritosForm) form).getCDefMeritoId()), ((OCAPMeritosForm) form).getCEstatutId(),
					((OCAPMeritosForm) form).getDTipoMerito(), ((OCAPMeritosForm) form).getCActividadId());
			if ((listaActividades != null) && (listaActividades.size() != 0) && ((((OCAPMeritosForm) form).getCActividadId() == null)
					|| (((OCAPMeritosForm) form).getCActividadId().intValue() == 0)))
			{
				messages.add("cActividadId", new ActionMessage("errors.required", "Tipo de publicación"));
			}

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			ArrayList listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(
					new Integer(((OCAPMeritosForm) form).getCDefMeritoId()), ((OCAPMeritosForm) form).getCEstatutId(),
					((OCAPMeritosForm) form).getDTipoMerito(), ((OCAPMeritosForm) form).getCFactorId());
			if ((listaFactores != null) && (listaFactores.size() != 0)
					&& ((((OCAPMeritosForm) form).getCFactorId() == null) || (((OCAPMeritosForm) form).getCFactorId().intValue() == 0)))
			{
				messages.add("cFactorId", new ActionMessage("errors.required", "Factor de impacto"));
			}

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			ArrayList listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(
					new Integer(((OCAPMeritosForm) form).getCDefMeritoId()), ((OCAPMeritosForm) form).getCEstatutId(),
					((OCAPMeritosForm) form).getDTipoMerito(), ((OCAPMeritosForm) form).getCTipoId());
			if ((listaTiposFirmante != null) && (listaTiposFirmante.size() != 0)
					&& ((((OCAPMeritosForm) form).getCTipoId() == null) || (((OCAPMeritosForm) form).getCTipoId().intValue() == 0)))
			{
				messages.add("cTipoId", new ActionMessage("errors.required", "Tipo de Firmante"));
			}
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título de la publicación"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() != null) && (!"".equals(((OCAPMeritosForm) form).getFExpedicion()))
					&& (((OCAPMeritosForm) form).getFExpedicion().length() < 10))
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de publicación"));
			else if ((((OCAPMeritosForm) form).getFExpedicion() != null) && (!"".equals(((OCAPMeritosForm) form).getFExpedicion()))
					&& (((OCAPMeritosForm) form).getFExpedicion().length() == 10))
				if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de publicación"));
				} else
				{
					dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
					if (dFExpedicion == null)
						messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de publicación"));
					else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
					}
				}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarPublicacionIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPublicacionIndex: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getCFactorId() == null)
				((OCAPMeritosForm) form).setCFactorId(expedientemcOT.getCFactorId());
			if (((OCAPMeritosForm) form).getCTipoId() == null)
				((OCAPMeritosForm) form).setCTipoId(expedientemcOT.getCTipoId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCFactorId());
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCTipoId());
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPublicacionIndex: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPublicacionIndex: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarPublicacionIndex");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarPublicacionIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPublicacionIndex: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			messages = validarPublicacionIndex(form, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());

				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCFactorId(((OCAPMeritosForm) form).getCFactorId());
				expedientemcOT.setCTipoId(((OCAPMeritosForm) form).getCTipoId());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());

				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarPublicacionIndex: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPublicacionIndex: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarPublicacionIndex");
	}

	public ActionForward controlAccionOtraPublicacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtraPublicacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionOtraPublicacion: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarOtraPublicacion(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarOtraPublicacion(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtraPublicacion: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtraPublicacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarOtraPublicacion".equals(vuelta.getName()))
			{
				return irInsertarOtraPublicacion(mapping, form, request, response);
			}
			if ("irModificarOtraPublicacion".equals(vuelta.getName()))
			{
				return irModificarOtraPublicacion(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarOtraPublicacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtraPublicacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtraPublicacion: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtraPublicacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarOtraPublicacion");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarOtraPublicacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarOtraPublicacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			messages = validarOtraPublicacion(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCFactorId(((OCAPMeritosForm) form).getCFactorId());
				expedientemcOT.setCTipoId(((OCAPMeritosForm) form).getCTipoId());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setAObjetivo(((OCAPMeritosForm) form).getAObjetivo());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarOtraPublicacion: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarOtraPublicacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarOtraPublicacion");
	}

	public ActionMessages validarOtraPublicacion(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getListaTiposFirmante() != null) && (((OCAPMeritosForm) form).getListaTiposFirmante().size() > 0)
					&& ((((OCAPMeritosForm) form).getCTipoId() == null) || (((OCAPMeritosForm) form).getCTipoId().intValue() == 0)))
				messages.add("cTipoId", new ActionMessage("errors.required", "Tipo de Firmante"));
			if ((((OCAPMeritosForm) form).getListaModalidades() != null) && (((OCAPMeritosForm) form).getListaModalidades().size() > 0)
					&& ((((OCAPMeritosForm) form).getCModalidadId() == null)
							|| (((OCAPMeritosForm) form).getCModalidadId().intValue() == 0)))
			{
				messages.add("cModalidadId", new ActionMessage("errors.required", "Ámbito del Congreso"));
			}
			if ((((OCAPMeritosForm) form).getDTipoMerito() != null)
					&& (((OCAPMeritosForm) form).getDTipoMerito().toUpperCase().indexOf("PÓSTER") != -1))
			{
				if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
				{
					messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
				}
			} else
				((OCAPMeritosForm) form).setDTitulo(((OCAPMeritosForm) form).getAOrganizador());

			if ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador())))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Nombre del Congreso"));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha de celebración"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de celebración"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de celebración"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de celebración"));
				else
				{
					messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFExpedicion().toString(), "fExpedicion"));
				}
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarOtraPublicacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtraPublicacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getCFactorId() == null)
				((OCAPMeritosForm) form).setCFactorId(expedientemcOT.getCFactorId());
			if (((OCAPMeritosForm) form).getCTipoId() == null)
				((OCAPMeritosForm) form).setCTipoId(expedientemcOT.getCTipoId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getFExpedicion() == null)
				((OCAPMeritosForm) form).setFExpedicion(expedientemcOT.getFExpedicion());
			if (((OCAPMeritosForm) form).getCModalidadId() == null)
				((OCAPMeritosForm) form).setCModalidadId(expedientemcOT.getCModalidadId());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCFactorId());
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCTipoId());
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtraPublicacion: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtraPublicacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarOtraPublicacion");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarOtraPublicacion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarOtraPublicacion: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			messages = validarOtraPublicacion(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());

				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCFactorId(((OCAPMeritosForm) form).getCFactorId());
				expedientemcOT.setCTipoId(((OCAPMeritosForm) form).getCTipoId());
				expedientemcOT.setFExpedicion(((OCAPMeritosForm) form).getFExpedicion());
				expedientemcOT.setAObjetivo(((OCAPMeritosForm) form).getAObjetivo());

				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarOtraPublicacion: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarOtraPublicacion: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarOtraPublicacion");
	}

	public ActionForward controlAccionOtraPublicacionISBN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtraPublicacionISBN: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionOtraPublicacionISBN: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarOtraPublicacionISBN(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarOtraPublicacionISBN(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtraPublicacionISBN: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionOtraPublicacionISBN: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarOtraPublicacionISBN".equals(vuelta.getName()))
			{
				return irInsertarOtraPublicacionISBN(mapping, form, request, response);
			}
			if ("irModificarOtraPublicacionISBN".equals(vuelta.getName()))
			{
				return irModificarOtraPublicacionISBN(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarOtraPublicacionISBN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtraPublicacionISBN: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtraPublicacionISBN: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarOtraPublicacionISBN: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarOtraPublicacionISBN");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarOtraPublicacionISBN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarOtraPublicacionISBN: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			messages = validarOtraPublicacionISBN(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setNISBN(((OCAPMeritosForm) form).getNISBN());
				expedientemcOT.setDEditorial(((OCAPMeritosForm) form).getDEditorial());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCFactorId(((OCAPMeritosForm) form).getCFactorId());
				expedientemcOT.setCTipoId(((OCAPMeritosForm) form).getCTipoId());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarOtraPublicacionISBN: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarOtraPublicacionISBN: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarOtraPublicacionISBN");
	}

	public ActionMessages validarOtraPublicacionISBN(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getListaModalidades() != null) && (((OCAPMeritosForm) form).getListaModalidades().size() > 0)
					&& ((((OCAPMeritosForm) form).getCModalidadId() == null)
							|| (((OCAPMeritosForm) form).getCModalidadId().intValue() == 0)))
				messages.add("cModalidadId", new ActionMessage("errors.required", "Modalidad"));
			if ((((OCAPMeritosForm) form).getListaTiposFirmante() != null) && (((OCAPMeritosForm) form).getListaTiposFirmante().size() > 0)
					&& ((((OCAPMeritosForm) form).getCTipoId() == null) || (((OCAPMeritosForm) form).getCTipoId().intValue() == 0)))
				messages.add("cTipoId", new ActionMessage("errors.required", "Tipo de Firmante"));
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Título"));
			}
			if ((((OCAPMeritosForm) form).getCModalidadId() != null) && (((OCAPMeritosForm) form).getCModalidadId().intValue() == 10)
					&& ((((OCAPMeritosForm) form).getAOrganizador() == null) || ("".equals(((OCAPMeritosForm) form).getAOrganizador()))))
			{
				messages.add("aOrganizador", new ActionMessage("errors.required", "Título del Capítulo"));
			}
			if ((((OCAPMeritosForm) form).getNISBN() == null) || ("".equals(((OCAPMeritosForm) form).getNISBN())))
			{
				messages.add("nISBN", new ActionMessage("errors.required", "ISBN"));
			} else if (((OCAPMeritosForm) form).getNISBN().length() < 10)
				messages.add("nISBN", new ActionMessage("errors.minlength", "ISBN", "10"));
			if ((((OCAPMeritosForm) form).getDEditorial() == null) || ("".equals(((OCAPMeritosForm) form).getDEditorial())))
			{
				messages.add("dEditorial", new ActionMessage("errors.required", "Editorial"));
			}
			if ((((OCAPMeritosForm) form).getFAnnio() == null) || (((OCAPMeritosForm) form).getFAnnio().intValue() == 0))
			{
				messages.add("fAnnio", new ActionMessage("errors.required", "Año de publicación"));
			} else if (((OCAPMeritosForm) form).getFAnnio().toString().length() < 4)
			{
				messages.add("fAnnio", new ActionMessage("error.maskmsg", "Año de publicación"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFAnnio().toString(), pos);
				if (dFExpedicion == null)
				{
					messages.add("fAnnio", new ActionMessage("error.maskmsg", "Año de publicación"));
				} else
				{
					Date annioActual = new Date();
					if (dFExpedicion.after(annioActual))
						messages.add("fAnnio", new ActionMessage("error.annioPosterior", "Año de publicación"));
					else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFAnnio().toString(), "fAnnio"));
					}
				}
			}
			if ((((OCAPMeritosForm) form).getALugarExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getALugarExpedicion())))
			{
				messages.add("aLugarExpedicion", new ActionMessage("errors.required", "Lugar de publicación"));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarOtraPublicacionISBN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtraPublicacionISBN: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getCActividadId() == null)
				((OCAPMeritosForm) form).setCActividadId(expedientemcOT.getCActividadId());
			if (((OCAPMeritosForm) form).getCModalidadId() == null)
				((OCAPMeritosForm) form).setCModalidadId(expedientemcOT.getCModalidadId());
			if (((OCAPMeritosForm) form).getCFactorId() == null)
				((OCAPMeritosForm) form).setCFactorId(expedientemcOT.getCFactorId());
			if (((OCAPMeritosForm) form).getCTipoId() == null)
				((OCAPMeritosForm) form).setCTipoId(expedientemcOT.getCTipoId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getAOrganizador() == null)
				((OCAPMeritosForm) form).setAOrganizador(expedientemcOT.getAOrganizador());
			if (((OCAPMeritosForm) form).getNISBN() == null)
				((OCAPMeritosForm) form).setNISBN(expedientemcOT.getNISBN());
			if (((OCAPMeritosForm) form).getDEditorial() == null)
				((OCAPMeritosForm) form).setDEditorial(expedientemcOT.getDEditorial());
			if (((OCAPMeritosForm) form).getALugarExpedicion() == null)
				((OCAPMeritosForm) form).setALugarExpedicion(expedientemcOT.getALugarExpedicion());
			if (((OCAPMeritosForm) form).getFAnnio() == null)
				((OCAPMeritosForm) form).setFAnnio(expedientemcOT.getFAnnio());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCActividadId());
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCFactorId());
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCTipoId());
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCModalidadId());
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtraPublicacionISBN: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarOtraPublicacionISBN: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarOtraPublicacionISBN");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarOtraPublicacionISBN(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		ArrayList listaActividades = null;
		ArrayList listaFactores = null;
		ArrayList listaTiposFirmante = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarOtraPublicacionISBN: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeractividadLN actividadLN = new OCAPMeractividadLN(jcylUsuario);
			listaActividades = actividadLN.buscarOCAPMeractividadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaActividades(listaActividades);

			OCAPFactoresImpactoLN factorLN = new OCAPFactoresImpactoLN(jcylUsuario);
			listaFactores = factorLN.buscarOCAPFactoresImpactoDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaFactores(listaFactores);

			OCAPTiposFirmanteLN tiposLN = new OCAPTiposFirmanteLN(jcylUsuario);
			listaTiposFirmante = tiposLN.buscarOCAPTiposFirmanteDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaTiposFirmante(listaTiposFirmante);

			OCAPMerModalidadLN modalidadLN = new OCAPMerModalidadLN(jcylUsuario);
			listaModalidades = modalidadLN.buscarOCAPMerModalidadDeMeritocurricular(new Integer(((OCAPMeritosForm) form).getCDefMeritoId()),
					((OCAPMeritosForm) form).getCEstatutId(), ((OCAPMeritosForm) form).getDTipoMerito(), new Integer(0));
			((OCAPMeritosForm) form).setListaModalidades(listaModalidades);

			messages = validarOtraPublicacionISBN(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());

				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());

				expedientemcOT.setAOrganizador(((OCAPMeritosForm) form).getAOrganizador());
				expedientemcOT.setNISBN(((OCAPMeritosForm) form).getNISBN());
				expedientemcOT.setDEditorial(((OCAPMeritosForm) form).getDEditorial());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCModalidadId(((OCAPMeritosForm) form).getCModalidadId());
				expedientemcOT.setCActividadId(((OCAPMeritosForm) form).getCActividadId());
				expedientemcOT.setCFactorId(((OCAPMeritosForm) form).getCFactorId());
				expedientemcOT.setCTipoId(((OCAPMeritosForm) form).getCTipoId());
				expedientemcOT.setFAnnio(((OCAPMeritosForm) form).getFAnnio());

				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarOtraPublicacionISBN: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarOtraPublicacionISBN: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarOtraPublicacionISBN");
	}

	public ActionForward controlAccionPreparaImparteAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPreparaImparteAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionPreparaImparteAct: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarPreparaImparteAct(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarPreparaImparteAct(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPreparaImparteAct: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionPreparaImparteAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarPreparaImparteAct".equals(vuelta.getName()))
			{
				return irInsertarPreparaImparteAct(mapping, form, request, response);
			}
			if ("irModificarPreparaImparteAct".equals(vuelta.getName()))
			{
				return irModificarPreparaImparteAct(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarPreparaImparteAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPreparaImparteAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPreparaImparteAct: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarPreparaImparteAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarPreparaImparteAct");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarPreparaImparteAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPreparaImparteAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarPreparaImparteAct(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarPreparaImparteAct: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarPreparaImparteAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarPreparaImparteAct");
	}

	public ActionMessages validarPreparaImparteAct(ActionForm form, long cConvocatoriaId, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			boolean tieneGradoExtraordinario = esGradoExtraordinario(((OCAPMeritosForm) form).getCExpId(), jcylUsuario);
			
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", "Actividad"));
			}
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}
				else
				{
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFInicio(), "fInicio",
							"Fecha de Inicio", cConvocatoriaId, jcylUsuario,true,tieneGradoExtraordinario));
				}
			}
			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				messages.add("fFinalizacion", new ActionMessage("errors.required", "Fecha de Finalización"));
			} else if (((OCAPMeritosForm) form).getFFinalizacion().length() < 10)
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
			} else
			{
				dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
				if (dFFin == null)
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				else
				{
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFFinalizacion(), "fFinalizacion",
							"Fecha de Finalización", cConvocatoriaId, jcylUsuario,true,tieneGradoExtraordinario));
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarPreparaImparteAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPreparaImparteAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getDTitulo() == null)
				((OCAPMeritosForm) form).setDTitulo(expedientemcOT.getDTitulo());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPreparaImparteAct: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarPreparaImparteAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarPreparaImparteAct");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarPreparaImparteAct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPreparaImparteAct: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			long cConvocatoriaId = ((Long) session.getAttribute("Convocatoria")).longValue();

			messages = validarPreparaImparteAct(form, cConvocatoriaId, jcylUsuario);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTitulo(((OCAPMeritosForm) form).getDTitulo());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				expedientemcOT.setALugarExpedicion(((OCAPMeritosForm) form).getALugarExpedicion());
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarPreparaImparteAct: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarPreparaImparteAct: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarPreparaImparteAct");
	}

	public ActionForward controlAccionCentinela(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward vuelta = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCentinela: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPConfigApp.logger
					.info(getClass().getName() + " controlAccionCentinela: ACCION: " + ((OCAPMeritosForm) form).getAccionBoton());
			if (Constantes.INSERTAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
				vuelta = insertarCentinela(mapping, form, request, response);
			else if (Constantes.MODIFICAR.equals(((OCAPMeritosForm) form).getAccionBoton()))
			{
				vuelta = modificarCentinela(mapping, form, request, response);
			}
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCentinela: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " controlAccionCentinela: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if ("irInsertarCentinela".equals(vuelta.getName()))
			{
				return irInsertarCentinela(mapping, form, request, response);
			}
			if ("irModificarCentinela".equals(vuelta.getName()))
			{
				return irModificarCentinela(mapping, form, request, response);
			}

			return vuelta;
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irInsertarCentinela(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCentinela: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (request.getParameter("expId") != null)
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.INSERTAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCentinela: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irInsertarCentinela: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irInsertarCentinela");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward insertarCentinela(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarCentinela: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarCentinela(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.insertar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " insertarCentinela: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " insertarCentinela: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irInsertarCentinela");
	}

	public ActionMessages validarCentinela(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFFin = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			if ((((OCAPMeritosForm) form).getFInicio() == null) || ("".equals(((OCAPMeritosForm) form).getFInicio())))
			{
				messages.add("fInicio", new ActionMessage("errors.required", "Fecha de Inicio"));
			} else if (((OCAPMeritosForm) form).getFInicio().length() < 10)
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else if (!((OCAPMeritosForm) form).getFInicio().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
			} else
			{
				dFInicio = df.parse(((OCAPMeritosForm) form).getFInicio(), pos);
				if (dFInicio == null)
				{
					messages.add("fInicio", new ActionMessage("error.maskmsg", "Fecha de Inicio"));
				}

			}

			if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				if (!((OCAPMeritosForm) form).getFFinalizacion().matches("\\d{2}/\\d{2}/\\d{4}"))
				{
					messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
				} else
				{
					dFFin = df.parse(((OCAPMeritosForm) form).getFFinalizacion(), pos2);
					if (dFFin == null)
					{
						messages.add("fFinalizacion", new ActionMessage("error.maskmsg", "Fecha de Finalización"));
					} else
					{
						messages.add(validarAnioFecha(((OCAPMeritosForm) form).getCExpId(),((OCAPMeritosForm) form).getFFinalizacion().toString(), "fFinalizacion"));
					}
				}
			}

			if ((dFInicio != null) && (dFFin != null) && (dFFin.before(dFInicio)))
				messages.add("fFechas", new ActionMessage("error.fechaAnterior", "Fecha de Finalización", "Fecha de Inicio"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionForward irModificarCentinela(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ArrayList listaActividades = null;
		ArrayList listaModalidades = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCentinela: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

			if (request.getParameter("idMer") != null)
				expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
			else
				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
			expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);

			if ((request.getParameter("detalle") != null) && (Constantes.SI.equals(request.getParameter("detalle"))))
				((OCAPMeritosForm) form).setBDetalle(Constantes.SI);
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
			{
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
			}
			((OCAPMeritosForm) form).setCExpmcId(expedientemcOT.getCExpmcId());
			((OCAPMeritosForm) form).setCExpId(expedientemcOT.getCExpId());
			if (((OCAPMeritosForm) form).getFInicio() == null)
				((OCAPMeritosForm) form).setFInicio(expedientemcOT.getFInicio());
			if (((OCAPMeritosForm) form).getFFinalizacion() == null)
				((OCAPMeritosForm) form).setFFinalizacion(expedientemcOT.getFFinalizacion());
			if (request.getParameter("pestana") != null)
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			if (request.getParameter("tipoMerito") != null)
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			if (request.getParameter("cEstatutId") != null)
				((OCAPMeritosForm) form).setCEstatutId(new Integer(request.getParameter("cEstatutId")));
			if (request.getParameter("cDefMeritoId") != null)
				((OCAPMeritosForm) form).setCDefMeritoId(request.getParameter("cDefMeritoId"));
			if (request.getParameter("tipo") != null)
			{
				((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("tipo"));
			}
			((OCAPMeritosForm) form).setBPdteAclarar(expedientemcOT.getBPdteAclararCc());
			((OCAPMeritosForm) form).setBInvalidado(expedientemcOT.getBInvalidadoCc());

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(((OCAPMeritosForm) form).getDTipoMerito(),
					((OCAPMeritosForm) form).getCEstatutId().toString());

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			request.setAttribute("tipoAccion", Constantes.MODIFICAR);

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCentinela: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCentinela: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarCentinela");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarCentinela(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarCentinela: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if ((((OCAPMeritosForm) form).getFFinalizacion() == null) || ("".equals(((OCAPMeritosForm) form).getFFinalizacion())))
			{
				((OCAPMeritosForm) form).setFFinalizacion(df.format(new Date()));
			}

			messages = validarCentinela(form);

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expedientemcOT.setFInicio(((OCAPMeritosForm) form).getFInicio());
				if ((((OCAPMeritosForm) form).getFFinalizacion() != null) && (!"".equals(((OCAPMeritosForm) form).getFFinalizacion())))
					expedientemcOT.setFFinalizacion(((OCAPMeritosForm) form).getFFinalizacion());
				else
					expedientemcOT.setFFinalizacion(df.format(new Date()));
				expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
				expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());

				if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
						&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
					expedientemcOT.setBOpcional(Constantes.SI);
				} else
				{
					expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
					expedientemcOT.setBOpcional(Constantes.NO);
				}
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				idExpediente = expedientemcLN.modificar(expedientemcOT, jcylUsuario);

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana=" + (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
								: ((OCAPMeritosForm) form).getPestanaSeleccionada()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarCentinela: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarCentinela: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarCentinela");
	}

	public ActionMessages validarEstudios(ActionForm form, String tipo, long cConvocatoriaId, JCYLUsuario jcylUsuario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFExpedicion = null;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);

			if ((((OCAPMeritosForm) form).getDTitulo() == null) || ("".equals(((OCAPMeritosForm) form).getDTitulo())))
			{
				messages.add("dTitulo", new ActionMessage("errors.required", tipo));
			}
			if ((((OCAPMeritosForm) form).getFExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getFExpedicion())))
			{
				messages.add("fExpedicion", new ActionMessage("errors.required", "Fecha de Expedición"));
			} else if (((OCAPMeritosForm) form).getFExpedicion().length() < 10)
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Expedición"));
			} else if (!((OCAPMeritosForm) form).getFExpedicion().matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Expedición"));
			} else
			{
				dFExpedicion = df.parse(((OCAPMeritosForm) form).getFExpedicion(), pos);
				if (dFExpedicion == null)
					messages.add("fExpedicion", new ActionMessage("error.maskmsg", "Fecha de Expedición"));
				else
				{
					messages.add(validarFechaFormacion(((OCAPMeritosForm) form).getFExpedicion(), "fExpedicion", "Fecha de Expedición",
							cConvocatoriaId, jcylUsuario,false,false));
				}
			}

			if ((((OCAPMeritosForm) form).getALugarExpedicion() == null) || ("".equals(((OCAPMeritosForm) form).getALugarExpedicion())))
				messages.add("aLugarExpedicion", new ActionMessage("errors.required", "Lugar de Expedición"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " validarEstudios: ERROR: " + e.toString());
			throw e;
		}

		return messages;
	}

	public ActionForward irEliminarMerito(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		boolean esCEIS = false;
		OCAPExpedientemcLN expedientemcLN = null;
		OCAPExpedientemcOT expedientemcOT = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irEliminarMerito: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			((OCAPMeritosForm) form).setCExpmcId(new Long(request.getParameter("idMer")));
			((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			((OCAPMeritosForm) form).setDTitulo(request.getParameter("nombre"));

			if (request.getParameter("tipoMerito") != null)
			{
				((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			}
			if (request.getParameter("expId") != null)
			{
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			}

			OCAPMeritoscurricularesLN mcLN = new OCAPMeritoscurricularesLN(jcylUsuario);
			OCAPMeritoscurricularesOT mcOT = new OCAPMeritoscurricularesOT();
			mcOT = mcLN.buscarOCAPMeritoscurricularesPorNombre(Cadenas.convierteMas(request.getParameter("tipoMerito")),
					request.getParameter("cEstatutId"));

			((OCAPMeritosForm) form).setDNombreMerito(mcOT.getDNombre());

			((OCAPMeritosForm) form).setDDescripcionMerito(mcOT.getDDescripcion() + mcOT.getDObservaciones());

			OCAPConfigApp.logger.info(getClass().getName() + " irEliminarMerito: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));

			if ((request.getParameter("CEIS") != null) && (Constantes.SI.equals(request.getParameter("CEIS"))))
			{
				esCEIS = true;
				expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				expedientemcOT = new OCAPExpedientemcOT();
				if (request.getParameter("idMer") != null)
					expedientemcOT.setCExpmcId(new Long(request.getParameter("idMer")));
				else
					expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);
				if ((expedientemcOT != null) && (Constantes.SI.equals(expedientemcOT.getBInvalidadoCc())))
					request.setAttribute("invalidar", Constantes.NO);
				else
					request.setAttribute("invalidar", Constantes.SI);
				if (request.getParameter("estado") != null)
					request.setAttribute("estado", request.getParameter("estado"));
			}
			if (request.getParameter("cDni") != null)
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			if (request.getParameter("estado") != null)
				((OCAPMeritosForm) form).setCEstado(request.getParameter("estado"));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irEliminarMerito: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			if (esCEIS)
			{
				return mapping.findForward("irInvalidarMerito");
			}

			return mapping.findForward("irEliminarMerito");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward eliminarMerito(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPExpedientemcLN expmcLN = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " eliminarMerito: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			expmcLN = new OCAPExpedientemcLN(jcylUsuario);
			expmcLN.eliminar(((OCAPMeritosForm) form).getCExpmcId(),jcylUsuario.getUser().getC_usr_id());

			OCAPConfigApp.logger.info(getClass().getName() + " eliminarMerito: Fin");

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("exitoMerito");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward invalidarMerito(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPMeritosLN meritosLN = null;
		OCAPExpedientecarreraOT expOT = null;
		OCAPExpedienteCarreraLN expLN = null;
		String perfil = null;
		boolean bInvalidar = false;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " invalidarMerito: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);
			OCAPMeritosForm formulario = (OCAPMeritosForm) form;

			if ((request.getParameter("invalidar") != null) && (Constantes.SI.equals(request.getParameter("invalidar"))))
			{
				bInvalidar = true;
			}

			expOT = new OCAPExpedientecarreraOT();
			expOT.setCExpId(formulario.getCExpId());

			perfil = jcylUsuario.getUser().getRol();

			if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
			{
				expLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				expOT = expLN.buscarOCAPExpedienteCarrera(expOT);
				Date dHoy = new Date();
				if (dHoy.before(expOT.getFInicioEvalMc()))
				{
					expOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					expOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					perfil = Constantes.OCAP_CEIS;
				}
			} else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
			{
				expOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			}
			expOT.setFAceptacionCC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			expOT.setFRespuestaInconf_MC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));

			meritosLN = new OCAPMeritosLN(jcylUsuario);

			meritosLN.invalidarMerito(formulario, bInvalidar, perfil, expOT);

			if (request.getParameter("estado") != null)
			{
				request.setAttribute("estado", request.getParameter("estado"));
			}

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&cDni=" + ((OCAPMeritosForm) form).getCDni() + "&estado="
					+ request.getParameter("estado") + "&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
			OCAPConfigApp.logger.info(getClass().getName() + " invalidarMerito: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&cDni=" + ((OCAPMeritosForm) form).getCDni() + "&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("exitoMerito");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward aclararMerito(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPExpedientemcLN expmcLN = null;
		OCAPUsuariosLN usuariosLN = null;
		OCAPUsuariosOT usuariosOT = null;
		boolean bPedirAclarar = false;
		OCAPExpedientecarreraOT expOT = null;
		OCAPExpedienteCarreraLN expLN = null;
		String perfil = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " aclararMerito: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			perfil = jcylUsuario.getUser().getRol();

			if ((request.getParameter("aclarar") != null) && (Constantes.SI.equals(request.getParameter("aclarar"))))
				bPedirAclarar = true;
			expmcLN = new OCAPExpedientemcLN(jcylUsuario);

			expOT = new OCAPExpedientecarreraOT();
			expOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
			if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
			{
				expLN = new OCAPExpedienteCarreraLN(jcylUsuario);
				expOT = expLN.buscarOCAPExpedienteCarrera(expOT);

				Date dHoy = new Date();
				if (dHoy.before(expOT.getFInicioEvalMc()))
				{
					expOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					expOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					perfil = Constantes.OCAP_CEIS;
				}
			} else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
			{
				expOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			}
			expOT.setFAceptacionCC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			expOT.setFRespuestaInconf_MC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));

			OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();
			expedientemcOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
			expedientemcOT.setCEstatutId(((OCAPMeritosForm) form).getCEstatutId());
			expedientemcOT.setDTipoMerito(((OCAPMeritosForm) form).getDTipoMerito());
			expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

			if ((Constantes.DEF_MERITO_OPCIONALES.equals(((OCAPMeritosForm) form).getPestanaSeleccionada()))
					&& (((OCAPMeritosForm) form).getCDefMeritoId() != null) && (!"".equals(((OCAPMeritosForm) form).getCDefMeritoId())))
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getCDefMeritoId());
				expedientemcOT.setBOpcional(Constantes.SI);
			} else
			{
				expedientemcOT.setCDefMeritoId(((OCAPMeritosForm) form).getPestanaSeleccionada());
				expedientemcOT.setBOpcional(Constantes.NO);
			}

			if ("DocenciaPost".equals(expedientemcOT.getCTipoMerito()))
			{
				ArrayList listaMeritos = expmcLN.buscarPorMeritoId(expedientemcOT, jcylUsuario, Constantes.FLAG_MERITOS_TODOS);
				String expedientesMC = "";
				for (int i = 0; i < listaMeritos.size(); i++)
					if (null == ((OCAPExpedientemcOT) listaMeritos.get(i)).getBInvalidadoCc() || Constantes.NO.equals(((OCAPExpedientemcOT) listaMeritos.get(i)).getBInvalidadoCc()))
						expmcLN.aclarar(((OCAPExpedientemcOT) listaMeritos.get(i)).getCExpmcId(), bPedirAclarar, perfil, expOT);
			} else
			{
				expmcLN.aclarar(((OCAPMeritosForm) form).getCExpmcId(), bPedirAclarar, perfil, expOT);
			}

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&cDni=" + request.getParameter("cDNI") + "&estado="
					+ request.getParameter("estado") + "&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
			OCAPConfigApp.logger.info(getClass().getName() + " aclararMerito: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&cDni=" + request.getParameter("cDNI") + "&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("exitoMerito");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward eliminarAclaracionesInvalidarMeritos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPMeritosLN meritosLN = null;
		boolean bPedirAclarar = false;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " eliminarAclaracionesInvalidarMeritos: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			OCAPMeritosForm formulario = (OCAPMeritosForm) form;

			if ((request.getParameter("aclarar") != null) && (Constantes.SI.equals(request.getParameter("aclarar"))))
			{
				bPedirAclarar = true;
			}
			if (request.getParameter("estado") != null)
			{
				request.setAttribute("estado", request.getParameter("estado"));
			}
			meritosLN = new OCAPMeritosLN(jcylUsuario);

			meritosLN.eliminarAclaracionesInvalidarMeritos(formulario, jcylUsuario);

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&cDni=" + ((OCAPMeritosForm) form).getCDni() + "&estado="
					+ request.getParameter("estado") + "&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada())
					+ "#" + (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));
			OCAPConfigApp.logger.info(getClass().getName() + " aclararMerito: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&cDni=" + ((OCAPMeritosForm) form).getCDni() + "&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("exitoMerito");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward enviarMeritoAOpcionales(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		OCAPExpedientemcLN expmcLN = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " enviarMeritoOpcionales: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			expmcLN = new OCAPExpedientemcLN(jcylUsuario);
			expmcLN.enviarAOpcionales(new Long(request.getParameter("idMer")),jcylUsuario.getUsuario().getC_usr_id());

			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana=" + request.getParameter("pestana"));

			OCAPConfigApp.logger.info(getClass().getName() + " enviarMeritoOpcionales: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("exitoMerito");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward irModificarCreditoMerito(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCreditoMerito: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			((OCAPMeritosForm) form).setCExpmcId(new Long(request.getParameter("idMer")));
			OCAPExpedientemcOT ot = new OCAPExpedientemcOT();
			OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);				
			  ot =  expedientemcLN.buscarOCAPExpeditentemcModif(new Long(request.getParameter("idMer")), jcylUsuario);
			 

			if (request.getParameter("pestana") != null)
			{
				((OCAPMeritosForm) form).setPestanaSeleccionada(request.getParameter("pestana"));
			}
			if (request.getParameter("cDni") != null)
			{
				((OCAPMeritosForm) form).setCDni(request.getParameter("cDni"));
			}
			if (request.getParameter("expId") != null)
			{
				((OCAPMeritosForm) form).setCExpId(new Long(request.getParameter("expId")));
			}
			((OCAPMeritosForm) form).setNCredUsuario(request.getParameter("nCred"));

			//OCAP-1870
			if(!Utilidades.isNullOrIsEmpty(request.getParameter("nHoras"))) {
				((OCAPMeritosForm) form).setNHorasUsuario(request.getParameter("nHoras"));
			}
			
			if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
				((OCAPMeritosForm) form).setNCredModif(request.getParameter("nCredCeis"));
				
				if(null!=ot.getDMotivosCeis())
					((OCAPMeritosForm) form).setDMotivoCredModif(ot.getDMotivosCeis());
				else
					((OCAPMeritosForm) form).setDMotivoCredModif("");
				
				//OCAP-1870
				if(!Utilidades.isNullOrIsEmpty(request.getParameter("nHoras")) && !Utilidades.isNullOrIsEmpty(ot.getDMotivosCc())) {
					((OCAPMeritosForm) form).setDMotivoCredModif(ot.getDMotivosCc());
				}
				
			}else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
				((OCAPMeritosForm) form).setNCredModif(request.getParameter("nCredCc"));
				
				if(null!=ot.getDMotivosCc())
					((OCAPMeritosForm) form).setDMotivoCredModif(ot.getDMotivosCc());
				else
					((OCAPMeritosForm) form).setDMotivoCredModif("");
			}else {
				((OCAPMeritosForm) form).setNCredModif(request.getParameter("nCredCc"));
				
				if(null!=ot.getDMotivosCc())
					((OCAPMeritosForm) form).setDMotivoCredModif(ot.getDMotivosCc());
				else
					((OCAPMeritosForm) form).setDMotivoCredModif("");
			}
			((OCAPMeritosForm) form).setDTipoMerito(Cadenas.convierteMas(request.getParameter("tipoMerito")));
			((OCAPMeritosForm) form).setDTitulo(request.getParameter("nombre"));
			((OCAPMeritosForm) form).setCTipoMerito(request.getParameter("CTipo"));

			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCreditoMerito: Fin");
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " irModificarCreditoMerito: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if ((errors == null) || (errors.isEmpty()))
		{
			return mapping.findForward("irModificarCreditoMerito");
		}
		saveErrors(request, errors);

		return mapping.findForward("falloMerito");
	}

	public ActionForward modificarCreditoMerito(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		int idExpediente = 0;
		OCAPExpedientecarreraOT expOT = null;
		OCAPExpedienteCarreraLN expLN = null;
		try
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarCreditoMerito: Inicio");
			validarAccion(mapping, form, request, response);

			JCYLUsuario jcylUsuario = (JCYLUsuario) session.getAttribute(JCYLConfiguracion.NOMBRE_ATRIBUTO_USUARIO);

			if (Utilidades.isNullOrIsEmpty(((OCAPMeritosForm) form).getNCredUsuario())) {
				messages = validarHoras(form);
			}else {
				messages = validarCreditoMerito(form);
			}
			

			if ((messages == null) || (messages.isEmpty()))
			{
				OCAPExpedientemcLN expedientemcLN = new OCAPExpedientemcLN(jcylUsuario);
				OCAPExpedientemcOT expedientemcOT = new OCAPExpedientemcOT();

				expedientemcOT.setCExpmcId(((OCAPMeritosForm) form).getCExpmcId());
				expedientemcOT.setCTipoMerito(((OCAPMeritosForm) form).getCTipoMerito());

				expOT = new OCAPExpedientecarreraOT();
				expOT.setCExpId(((OCAPMeritosForm) form).getCExpId());
				expOT.setFAceptacionCC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				expOT.setFRespuestaInconf_MC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
				
				if (Utilidades.isNullOrIsEmpty(((OCAPMeritosForm) form).getNCredUsuario())) {
					expedientemcOT.setNHoras(new Integer(((OCAPMeritosForm) form).getNHorasModif()));
					
					if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
						insertarJustificacionHoras(expedientemcOT,expedientemcLN, form, jcylUsuario);
						expOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
						expOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					}else if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
						insertarJustificacionHoras(expedientemcOT,expedientemcLN, form, jcylUsuario);
						borrarFechasCeis(expOT, expLN, jcylUsuario);
					}
					idExpediente = expedientemcLN.modificarCreditosMerito(expedientemcOT, jcylUsuario, expOT, true);
					
				}else {
					
					if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
					{
						expedientemcOT.setNCredCc(new Float(((OCAPMeritosForm) form).getNCredModif()));
						expedientemcOT.setDMotivosCc(((OCAPMeritosForm) form).getDMotivoCredModif());
						borrarFechasCeis(expOT, expLN, jcylUsuario);

					}else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
					{
						expedientemcOT.setNCredCeis(new Float(((OCAPMeritosForm) form).getNCredModif()));
						
						//OCAP-1577
						expedientemcOT.setNCredCc(new Float(((OCAPMeritosForm) form).getNCredModif()));
						
						expedientemcOT.setDMotivosCeis(((OCAPMeritosForm) form).getDMotivoCredModif());
						expOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
						expOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
					}
					
					idExpediente = expedientemcLN.modificarCreditosMerito(expedientemcOT, jcylUsuario, expOT,false);
				}
				

				request.setAttribute("rutaVuelta",
						"OCAPUsuarios.do?accion=irInsertar&pestana="
								+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? ""
										: ((OCAPMeritosForm) form).getPestanaSeleccionada())
								+ "&cDni=" + (((OCAPMeritosForm) form).getCDni() == null ? "" : ((OCAPMeritosForm) form).getCDni()) + "#"
								+ (((OCAPMeritosForm) form).getDTipoMerito() == null ? "" : ((OCAPMeritosForm) form).getDTipoMerito()));

				OCAPConfigApp.logger.info(getClass().getName() + " modificarCreditoMerito: Fin");
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " modificarCreditoMerito: ERROR: " + e.toString());
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(TrataError.tratarError(e, "error.general")));
			request.setAttribute("rutaVuelta", "OCAPUsuarios.do?accion=irInsertar&pestana="
					+ (((OCAPMeritosForm) form).getPestanaSeleccionada() == null ? "" : ((OCAPMeritosForm) form).getPestanaSeleccionada()));
		}

		if (((errors == null) || (errors.isEmpty())) && ((messages == null) || (messages.isEmpty())))
		{
			return mapping.findForward("exitoMerito");
		}
		if ((errors != null) && (!errors.isEmpty()))
		{
			saveErrors(request, errors);

			return mapping.findForward("falloMerito");
		}
		saveMessages(request, messages);

		return mapping.findForward("irModificarCreditoMerito");
	}

	private void insertarJustificacionHoras(OCAPExpedientemcOT expedientemcOT, OCAPExpedientemcLN expedientemcLN, ActionForm form, JCYLUsuario jcylUsuario) throws SQLException, Exception {
		OCAPExpedientemcOT meritoBbdd = expedientemcLN.buscarPorId(expedientemcOT.getCExpmcId(), jcylUsuario);
		String texto;
		if(Utilidades.isNullOrIsEmpty(meritoBbdd.getDMotivosCc())) {
			//Cogemos el mensaje tipo del fichero de propiedades
			texto = leerPropiedad(meritoBbdd.getNHoras());
			
			//Insertamos las horas que originalmente introdujo el usuario
			expedientemcOT.setDMotivosCc(texto +" "+ ((OCAPMeritosForm) form).getDMotivoCredModif());
		}else {
			String motivoOriginal = meritoBbdd.getDMotivosCc();
			if(motivoOriginal.indexOf(":") != -1) {
				String historicoOriginal = motivoOriginal.substring(0, motivoOriginal.indexOf(":")+1);
				if(((OCAPMeritosForm) form).getDMotivoCredModif().contains(historicoOriginal)) {
					expedientemcOT.setDMotivosCc(((OCAPMeritosForm) form).getDMotivoCredModif());
				}else {
					expedientemcOT.setDMotivosCc(historicoOriginal +" "+ ((OCAPMeritosForm) form).getDMotivoCredModif());
				}
			}else {
				//Se ha borrado el motivo original, por tanto hemos perdido el historico
				expedientemcOT.setDMotivosCc(((OCAPMeritosForm) form).getDMotivoCredModif());
			}
		}
		
	}

	private String leerPropiedad(Integer nHoras) {
		String retorno;
		try {
			String propiedad = ResourceBundle.getBundle("ApplicationResources").getString("modificacionHoras.original");
			retorno = MessageFormat.format(propiedad, nHoras);
		} catch (MissingResourceException mse) {
			retorno = "";
		}
		return retorno;
	}

	private void borrarFechasCeis(OCAPExpedientecarreraOT expOT, OCAPExpedienteCarreraLN expLN, JCYLUsuario jcylUsuario) throws Exception {
		expLN = new OCAPExpedienteCarreraLN(jcylUsuario);
		expOT = expLN.buscarOCAPExpedienteCarrera(expOT);

		Date dHoy = new Date();
		if (dHoy.before(expOT.getFInicioEvalMc()))
		{
			expOT.setFAceptacionceis(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
			expOT.setFNegacionMC(DateUtil.convertStringToDate(Constantes.FECHA_NULA));
		}
		
	}

	private ActionMessages validarHoras(ActionForm form) {

		ActionMessages messages = new ActionMessages();

		int numHoras = 0;
		
		try {
			if (Utilidades.isNullOrIsEmpty(((OCAPMeritosForm) form).getNHorasModif())) {
				messages.add("nHorasModif", new ActionMessage("errors.required", "Horas modificadas"));
			} else {
				try {
					((OCAPMeritosForm) form).setNHorasModif(((OCAPMeritosForm) form).getNHorasModif().replaceAll(",", "."));

					numHoras = Integer.parseInt(((OCAPMeritosForm) form).getNHorasModif());

					if (numHoras > 99999)
						messages.add("nHorasModif", new ActionMessage("error.horasMaxModif", "Horas modificadas"));
				} catch (NumberFormatException e) {
					messages.add("nHorasModif", new ActionMessage("error.maskmsg", "Horas modificadas"));
				}
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;

	}

	public ActionMessages validarCreditoMerito(ActionForm form) throws Exception {
		ActionMessages messages = new ActionMessages();

		float numCred = 0.0F;
		try
		{
			if ((((OCAPMeritosForm) form).getNCredModif() == null) || ("".equals(((OCAPMeritosForm) form).getNCredModif())))
				messages.add("nCredModif", new ActionMessage("errors.required", "Nº de créditos"));
			else if ((((OCAPMeritosForm) form).getNCredModif() != null) && (!"".equals(((OCAPMeritosForm) form).getNCredModif())))
				try
				{
					((OCAPMeritosForm) form).setNCredModif(((OCAPMeritosForm) form).getNCredModif().replaceAll(",", "."));

					numCred = Float.parseFloat(((OCAPMeritosForm) form).getNCredModif());

					if (numCred > 999.99000000000001D)
						messages.add("nCredModif", new ActionMessage("error.creditoMaxModif", "Nº de créditos"));
				} catch (NumberFormatException e)
				{
					messages.add("nCredModif", new ActionMessage("error.maskmsg", "Nº de créditos"));
				}
		/*	else {
				if ((((OCAPMeritosForm) form).getNCredModif() != null) && (!"".equals(((OCAPMeritosForm) form).getNCredModif())))
					messages.add("motivoCredModif", new ActionMessage("error.requiered", "Justificación modificación de créditos"));
			}*/
		} catch (Exception e)
		{
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}

		return messages;
	}

	public ActionMessages validarFechaFormacion(String fecha, String fechaCampo, String fechaLiteral, long cConvocatoriaId,
			JCYLUsuario jcylUsuario, boolean limitaDiezAnnos, boolean tieneGradoExtraordinario) throws Exception {
		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFPublica = null;
		Date dFComprobacionMC = null;
		Date dFComprobacionMinMC = null;

		OCAPConvocatoriasLN convocatoriaLN = null;
		OCAPConvocatoriasOT convoOT = null;
		
		String id_hilo =  " Hilo Id: " + String.valueOf(Thread.currentThread().getId());
			
		OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- " + id_hilo + " Entra en validarFechaFormacion con valor de cConvocatorioaId=" + String.valueOf(cConvocatoriaId) +  " DNI: " +jcylUsuario.getUser().getC_usr_id() + " y parametos JCYLUsuario " + jcylUsuario.getParametrosUsuario().toString());
		OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- " + id_hilo + " Valor de JCYLConfiguracion de FECHA_COMPROBAR_MC=" + JCYLConfiguracion.getValor("FECHA_COMPROBAR_MC"));
	
				
		try
		{
			convocatoriaLN = new OCAPConvocatoriasLN(jcylUsuario);
			convoOT = convocatoriaLN.buscarOCAPConvocatorias(cConvocatoriaId);
			
			//HOTFIX ERROR FECHAS MERITOS
			String   fechConvocatoria =  FIN_ANNIO +  (convoOT!=null?convoOT.getDAnioConvocatoria():"0000");
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- " + id_hilo + " Valor traido de BBDD para de FECHA_COMPROBAR_MC= " + fechConvocatoria);
			
			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);
			ParsePosition pos3 = new ParsePosition(0);

			
			dFInicio = df.parse(fecha, pos);
			
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- "+ id_hilo + " Valor de dFInicio" + dFInicio.toString());
			
			dFPublica = df.parse(convoOT.getFPublicacion(), pos2);
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- "+ id_hilo + "  Valor de dFPublica" + dFPublica.toString());
			
			
			dFComprobacionMC = df.parse(fechConvocatoria, pos3);
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- " + id_hilo + " Valor de dFComprobacionMC leida de fechConvocatoria" + dFComprobacionMC.toString());
			dFComprobacionMinMC = DateUtil.addAnios(dFComprobacionMC, -10);
			dFComprobacionMinMC = DateUtil.addDias(dFComprobacionMinMC, 1);
			
			
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- "+ id_hilo + " Valor de dFComprobacionMinMC despues de restar 10 años y sumar 1 dia= " + dFComprobacionMinMC.toString());
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- "+ id_hilo + " Valor de PARAM_FECHA_COMPROBAR_MC= " + JCYLConfiguracion.getValor("PARAM_FECHA_COMPROBAR_MC") +  " DNI: " +jcylUsuario.getUser().getC_usr_id());
	
			
			if (Constantes.SI.equals(JCYLConfiguracion.getValor("PARAM_FECHA_COMPROBAR_MC")))
			{
				if ((dFInicio != null) && (dFComprobacionMC != null) && (dFComprobacionMC.before(dFInicio)))
				{
					messages.add(fechaCampo, new ActionMessage("error.fechaAnteriorPublicaEjecucion", fechaLiteral,
							fechConvocatoria));
				}

				if (limitaDiezAnnos && !tieneGradoExtraordinario) {
					if ((dFInicio != null) && (dFComprobacionMC != null) && (dFComprobacionMinMC != null)
							&& (dFInicio.before(dFComprobacionMinMC) || dFInicio.after(dFComprobacionMC))) {
						messages.add(fechaCampo, new ActionMessage("error.fechaEnIntervaloDiez", fechaLiteral,
								df.format(dFComprobacionMinMC), fechConvocatoria));
					}
				}
			} else if ((dFInicio != null) && (dFPublica != null) && (dFPublica.before(dFInicio)))
			{
				messages.add(fechaCampo, new ActionMessage("error.fechaAnteriorPublica", fechaLiteral, convoOT.getFPublicacion()));
			}
		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " validarFechaFormacion: ERROR: " + e.toString());
			throw e;
		}

		return messages;
	}

	public ActionMessages validarAnioFecha(long cExpId , String fecha, String fechaCampo) throws Exception {

		ActionMessages messages = new ActionMessages();
		Date dFInicio = null;
		Date dFComprobacionMC = null;
		
		try
		{
			

			SimpleDateFormat df = new SimpleDateFormat(Constantes.FECHA_CORTA_DATEUTIL);
			df.setLenient(false);
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);

			if (fecha.matches("\\d{2}/\\d{2}/\\d{4}"))
			{
				// fecha completa

				dFInicio = df.parse(fecha, pos);

			} else if (fecha.matches("\\d{4}"))
			{
				// solo año
				fecha = FIN_ANNIO + fecha;
				dFInicio = df.parse(fecha, pos);
			}
			
			
			
			OCAPConvocatoriasLN convocLN = new OCAPConvocatoriasLN(null);						
			OCAPConvocatoriasOT  convocOt =  convocLN.buscarOCAPConvocatoriasPorExpediente(cExpId);
			
			if (convocOt!=null)
				
			{
				dFComprobacionMC = df.parse(FIN_ANNIO+ convocOt.getDAnioConvocatoria(), pos2);
				
			}
			else
			{
				dFComprobacionMC = df.parse(JCYLConfiguracion.getValor("FECHA_COMPROBAR_MC"), pos2);
				
				
			}
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fechaComoCadena = sdf.format(dFComprobacionMC);
			
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- Valor fecha de comprobacion meritos JCYLConfiguration: " + JCYLConfiguracion.getValor("FECHA_COMPROBAR_MC"));
			OCAPConfigApp.loggerTrazas.warn("TRAZA PROD- Valor fecha de comprobacion meritos resultante: " + fechaComoCadena);

			if ((dFInicio != null) && (dFComprobacionMC != null) && (dFComprobacionMC.before(dFInicio)))
			{
				messages.add(fechaCampo,
						new ActionMessage("error.fechaAnteriorConvocatoria", fechaComoCadena));
			}

		} catch (Exception e)
		{
			OCAPConfigApp.logger.info(getClass().getName() + " validarAnioFecha: ERROR: " + e.toString());
			throw e;
		}

		return messages;
	}
	
	public static String escapeHTML(String s) {
	    StringBuilder out = new StringBuilder(Math.max(16, s.length()));
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        if (c > 127 || c == '"' || c == '\'' || c == '<' || c == '>' || c == '&') {
	            out.append("&#");
	            out.append((int) c);
	            out.append(';');
	        } else {
	            out.append(c);
	        }
	    }
	    return out.toString();
	}

}

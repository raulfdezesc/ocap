package es.jcyl.fqs.ocap.ln.cuestionarios;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ln.calculoCredCuestionarios.OCAPCalculoCredCuestionariosLN;
import es.jcyl.fqs.ocap.ln.creditosCuestionarios.OCAPCreditosCuestionariosLN;
import es.jcyl.fqs.ocap.ln.estadosCuestionario.OCAPEstadosCuestionarioLN;
import es.jcyl.fqs.ocap.ln.expedientesCAs.OCAPExpedientesCAsLN;
import es.jcyl.fqs.ocap.ln.itinerarios.OCAPItinerariosLN;
import es.jcyl.fqs.ocap.ln.preguntas.OCAPPreguntasLN;
import es.jcyl.fqs.ocap.oad.calculoCredCuestionarios.OCAPCalculoCredCuestionariosOAD;
import es.jcyl.fqs.ocap.oad.creditosCuestionarios.OCAPCreditosCuestionariosOAD;
import es.jcyl.fqs.ocap.oad.cuestionarios.OCAPCuestionariosOAD;
import es.jcyl.fqs.ocap.oad.documentos.OCAPDocumentosOAD;
import es.jcyl.fqs.ocap.oad.estadosCuestionario.OCAPEstadosCuestionarioOAD;
import es.jcyl.fqs.ocap.oad.expedientesCAs.OCAPExpedientesCAsOAD;
import es.jcyl.fqs.ocap.ot.areasItinerarios.OCAPAreasItinerariosOT;
import es.jcyl.fqs.ocap.ot.calculoCredCuestionarios.OCAPCalculoCredCuestionariosOT;
import es.jcyl.fqs.ocap.ot.creditosCuestionarios.OCAPCreditosCuestionariosOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPCuestionariosOT;
import es.jcyl.fqs.ocap.ot.cuestionarios.OCAPRepeticionesCuestionariosOT;
import es.jcyl.fqs.ocap.ot.documentos.OCAPDocumentosOT;
import es.jcyl.fqs.ocap.ot.expedientesCAs.OCAPExpedientesCAsOT;
import es.jcyl.fqs.ocap.ot.itinerarios.OCAPItinerariosOT;
import es.jcyl.fqs.ocap.ot.preguntas.OCAPPreguntasOT;
import es.jcyl.fqs.ocap.ot.respuestas.OCAPRespuestasOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.fqs.ocap.util.reports.conceptos.ConceptoReport;
import es.jcyl.fqs.ocap.util.reports.ot.ReportOT;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;

import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class OCAPCuestionariosLN {
	Logger logger;
	public Logger loggerBD;
	Logger loggerXML;
	private OCAPCuestionariosOAD cuestionariosOAD;
	private JCYLUsuario jcylUsuario;

	private void $init$() {
		this.logger = OCAPConfigApp.logger;

		this.loggerBD = OCAPConfigApp.loggerBD;

		this.loggerXML = OCAPConfigApp.loggerXML;
	}

	public  OCAPCuestionariosLN() {
		this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
	}

	public OCAPCuestionariosLN(JCYLUsuario jcylUsuario) {
		$init$();
		this.jcylUsuario = jcylUsuario;
	}

	public void borrarRespuestasCuestionario(OCAPUsuariosOT usuOT, long idCuestionario, ArrayList listaRepeticiones,
			int idRepeticion, long idPadreEvidencia, JCYLUsuario jcylUsuario) throws Exception {
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			this.cuestionariosOAD.borrarRespuestasCuestionario(usuOT, idCuestionario, listaRepeticiones, idRepeticion,
					idPadreEvidencia, jcylUsuario);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
	}

	public void borrarRespuestasExpediente(long cExpId) throws Exception {
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			this.cuestionariosOAD.borrarRespuestasExpediente(cExpId);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
	}

	public ArrayList buscarPreguntasCuestionarioPorUsuario(OCAPUsuariosOT usuOT, long idCuestionario,
			ArrayList listaRepeticiones, int idRepeticion, boolean bSimulacion, boolean bUsuario, int nEvidencia,
			long idPadreEvidencia, JCYLUsuario jcylUsuario) {
		ArrayList listado = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			listado = this.cuestionariosOAD.buscarPreguntasCuestionarioPorUsuario(usuOT, idCuestionario,
					listaRepeticiones, idRepeticion, bSimulacion, bUsuario, nEvidencia, idPadreEvidencia, jcylUsuario);
		} catch (Exception e) {
			listado = null;
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}
		return listado;
	}

	public OCAPCuestionariosOT detalleCuestionario(OCAPUsuariosOT usuOT, OCAPCuestionariosOT cuestOT, int idRepe,
			JCYLUsuario jcylUsuario) {
		OCAPCuestionariosOT datos = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			datos = this.cuestionariosOAD.detalleCuestionario(usuOT, cuestOT, idRepe, jcylUsuario);
		} catch (Exception e) {
			datos = null;
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}
		return datos;
	}

	public ArrayList listadoOCAPCuestionariosPorItinerario(OCAPUsuariosOT usuOT, JCYLUsuario jcylUsuario)
			throws Exception {
		ArrayList listado = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			listado = this.cuestionariosOAD.listadoOCAPCuestionariosPorItinerario(usuOT, jcylUsuario);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return listado;
	}

	public ArrayList listadoOCAPCuestionariosAreaDocumentos(Long cExpId, Long cItinerarioId, String cAreaId)
			throws Exception {
		ArrayList listado = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			listado = this.cuestionariosOAD.listadoOCAPCuestionariosAreaDocumentos(cExpId, cItinerarioId, cAreaId);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return listado;
	}

	public OCAPAreasItinerariosOT informacionArea(long cAreaId, JCYLUsuario jcylUsuario) throws Exception {
		OCAPAreasItinerariosOT dato = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			dato = this.cuestionariosOAD.informacionArea(cAreaId, jcylUsuario);
		} catch (Exception e) {
			dato = null;
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return dato;
	}

	/*public boolean estaContestado(OCAPCuestionariosOT cuestionarioOT, OCAPUsuariosOT usuarioOT, int idRepe,
			ArrayList listaRepeticiones, long idPadreEvidencia, JCYLUsuario jcylUsuario) {
		OCAPCuestionariosOT cuestOT = null;
		OCAPRepeticionesCuestionariosOT repeCuestOT = null;

		OCAPEstadosCuestionarioLN estCuesLN = null;
		String estado = null;
		try {
			long cExpId = usuarioOT.getCExpId().longValue();
			if (idRepe == 0) {
				for (int i = 0; i < cuestionarioOT.getNRepeticiones().intValue(); i++) {
					if ((cuestionarioOT.getNPreguntasContestables().intValue() != 0)
							&& ((Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()))
									|| (!Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())))) {
						estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
						estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestionarioOT.getCCuestionarioId(), i + 1,
								idPadreEvidencia);
						if (!"F".equals(estado)) {
							return false;
						}
					} else if (cuestionarioOT.getCCuestionarioIntermedioId() != 0L) {
						OCAPCuestionariosOT auxCuestOT = new OCAPCuestionariosOT();
						auxCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioIntermedioId());
						auxCuestOT = detalleCuestionario(usuarioOT, auxCuestOT, idRepe, jcylUsuario);
						if (!estaContestado(auxCuestOT, usuarioOT, idRepe, listaRepeticiones, idPadreEvidencia,
								jcylUsuario)) {
							return false;
						}
					}
					String idSubCuestionarioElegido = null;
					if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
						idSubCuestionarioElegido = buscarRespuestaCuestionarioSubdivision(usuarioOT,
								cuestionarioOT.getCCuestionarioId(), 1);
					}
					if (cuestionarioOT.getListaSubCuestionarios() != null) {
						boolean bHijosSinContestar = false;
						for (int j = 0; j < cuestionarioOT.getListaSubCuestionarios().size(); j++) {
							cuestOT = (OCAPCuestionariosOT) cuestionarioOT.getListaSubCuestionarios().get(j);
							repeCuestOT = new OCAPRepeticionesCuestionariosOT();
							repeCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
							repeCuestOT.setNRepeticion(i + 1);
							listaRepeticiones.add(repeCuestOT);
							boolean aux = true;
							if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
								if (cuestOT.getCCuestionarioId() == Long
										.parseLong(idSubCuestionarioElegido == null ? "0" : idSubCuestionarioElegido)) {
									aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia,
											jcylUsuario);
									if (!aux) {
										bHijosSinContestar = true;
									}
								}
							}
							if (!Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
								aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia,
										jcylUsuario);
							}
						}
						if ((bHijosSinContestar) && ((cuestionarioOT.getNMaxHijosContestados() == 0)
								|| ((cuestionarioOT.getNMaxHijosContestados() != 0) && (cuestionarioOT
										.getNMaxHijosContestados() != cuestionarioOT.getNHijosContestados())))) {
							return false;
						}
					}
				}
			} else if ((cuestionarioOT.getNPreguntasContestables().intValue() != 0)
					&& ((Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()))
							|| (!Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())))) {
				estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
				estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestionarioOT.getCCuestionarioId(), idRepe,
						idPadreEvidencia);
				if (!"F".equals(estado)) {
					return false;
				}
			} else if (cuestionarioOT.getCCuestionarioIntermedioId() != 0L) {
				OCAPCuestionariosOT auxCuestOT = new OCAPCuestionariosOT();
				auxCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioIntermedioId());
				auxCuestOT = detalleCuestionario(usuarioOT, auxCuestOT, idRepe, jcylUsuario);
				if (!estaContestado(auxCuestOT, usuarioOT, idRepe, listaRepeticiones, idPadreEvidencia, jcylUsuario)) {
					return false;
				}
			} else if (cuestionarioOT.getListaSubCuestionarios() != null) {
				String idSubCuestionarioElegido = null;
				if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
					idSubCuestionarioElegido = buscarRespuestaCuestionarioSubdivision(usuarioOT,
							cuestionarioOT.getCCuestionarioId(), idRepe);
				}
				boolean bHijosSinContestar = false;
				for (int j = 0; j < cuestionarioOT.getListaSubCuestionarios().size(); j++) {
					cuestOT = (OCAPCuestionariosOT) cuestionarioOT.getListaSubCuestionarios().get(j);
					cuestOT = (OCAPCuestionariosOT) cuestionarioOT.getListaSubCuestionarios().get(j);
					repeCuestOT = new OCAPRepeticionesCuestionariosOT();
					repeCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
					repeCuestOT.setNRepeticion(idRepe);
					listaRepeticiones.add(repeCuestOT);
					boolean aux = true;
					if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
						if (cuestOT.getCCuestionarioId() == Long
								.parseLong(idSubCuestionarioElegido == null ? "0" : idSubCuestionarioElegido)) {
							aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia,
									jcylUsuario);
							if (!aux) {
								bHijosSinContestar = true;
							}
						}
					}
					aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia, jcylUsuario);
				}
				if ((bHijosSinContestar) && ((cuestionarioOT.getNMaxHijosContestados() == 0)
						|| ((cuestionarioOT.getNMaxHijosContestados() != 0) && (cuestionarioOT
								.getNMaxHijosContestados() != cuestionarioOT.getNHijosContestados())))) {
					return false;
				}
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}
		return true;
	}*/
	
	public boolean estaContestado(OCAPCuestionariosOT cuestionarioOT, OCAPUsuariosOT usuarioOT, int idRepe, ArrayList listaRepeticiones, long idPadreEvidencia, JCYLUsuario jcylUsuario) {
	      OCAPExpedientesCAsOT expCAOT = null;
	      OCAPExpedientesCAsLN expCALN = new OCAPExpedientesCAsLN(jcylUsuario);
	      ArrayList listaExpedientesCA = null;
	      OCAPCuestionariosOT cuestOT = null;
	      OCAPRepeticionesCuestionariosOT repeCuestOT = null;
	      OCAPCreditosCuestionariosLN creditosCuestLN = null;
	      ArrayList listaCreditosCuestionario = null;
	      OCAPEstadosCuestionarioLN estCuesLN = null;
	      String estado = null;
	      try {
	         long cExpId = usuarioOT.getCExpId().longValue();
	         if (idRepe == 0) {
	            for (int i = 0; i < cuestionarioOT.getNRepeticiones().intValue(); i++) {
	               //Si es un cuestionario con preguntas
	               //if (cuestionarioOT.getNPreguntasContestables().intValue() != 0 && (Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()) || (Constantes.NO.equals(cuestionarioOT.getBContestarUsuario()) && Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())))) {
	               if (cuestionarioOT.getNPreguntasContestables().intValue() != 0 && (Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()) || !Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol()))) {
	                     /* Cuando no se permitia pausar los cuestionarios
	                     //Buscamos a ver si hay respuestas
	                     expCAOT = new OCAPExpedientesCAsOT();
	                     expCAOT.setCExpId(cExpId);
	                     expCAOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
	                     expCAOT.setNRepeticion(i+1);
	                     listaExpedientesCA = expCALN.buscarOCAPExpedientesCAs(expCAOT, listaRepeticiones);
	                     //Si no hay respuestas, el formulario no está contestado
	                     if (listaExpedientesCA == null || listaExpedientesCA.size()==0)
	                        return false;
	                     */
	                     estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
	                     estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestionarioOT.getCCuestionarioId(),i+1, idPadreEvidencia);
	                     if (!Constantes.EST_CUEST_FINALIZAR.equals(estado))
	                        return false;
	               } //else if (!Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()) && cuestionarioOT.getCCuestionarioIntermedioId()!=0) {
	               else if (cuestionarioOT.getCCuestionarioIntermedioId()!=0) {
	                  OCAPCuestionariosOT auxCuestOT = new OCAPCuestionariosOT();
	                  auxCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioIntermedioId());
	                  auxCuestOT= detalleCuestionario(usuarioOT, auxCuestOT, idRepe, jcylUsuario);
	                  if (!estaContestado(auxCuestOT,usuarioOT, idRepe, listaRepeticiones, idPadreEvidencia, jcylUsuario))
	                     return false;
	               }
	               //Si es de SubDivision...
	               String idSubCuestionarioElegido= null;
	               if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())){
	                  idSubCuestionarioElegido = buscarRespuestaCuestionarioSubdivision(usuarioOT, cuestionarioOT.getCCuestionarioId(),1);
	               }
	               if (cuestionarioOT.getListaSubCuestionarios() != null) {
	                  boolean bHijosSinContestar = false;
	                  for (int j=0; j < cuestionarioOT.getListaSubCuestionarios().size(); j++) {
	                     cuestOT = (OCAPCuestionariosOT)cuestionarioOT.getListaSubCuestionarios().get(j);
	                     repeCuestOT = new OCAPRepeticionesCuestionariosOT();
	                     repeCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
	                     repeCuestOT.setNRepeticion(i+1);
	                     listaRepeticiones.add(repeCuestOT);
	                     boolean aux = true;
	                     //Si es subdivision, comprobar si está relleno solo el cuestionario hijo elegido en la subdivision
	                     if (Constantes.SI.equals(cuestionarioOT.getBSubdivision()) && cuestOT.getCCuestionarioId() == Long.parseLong(idSubCuestionarioElegido==null?"0":idSubCuestionarioElegido))
	                        aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia, jcylUsuario);
	                     else if (!Constantes.SI.equals(cuestionarioOT.getBSubdivision()))
	                        aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia, jcylUsuario);
	                     if (!aux) 
	                        bHijosSinContestar = true;
	                  }
	                  if (bHijosSinContestar){
	                     if (cuestionarioOT.getNMaxHijosContestados()==0 || (cuestionarioOT.getNMaxHijosContestados()!=0 && cuestionarioOT.getNMaxHijosContestados()!=cuestionarioOT.getNHijosContestados()))
	                        return false;
	                  }
	               }
	            }
	         } else {
	            //Si es un cuestionario con preguntas
	            if (cuestionarioOT.getNPreguntasContestables().intValue() != 0 && (Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()) || !Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol()))) {
	                  /*
	                  //Buscamos a ver si hay respuestas
	                  expCAOT = new OCAPExpedientesCAsOT();
	                  expCAOT.setCExpId(cExpId);
	                  expCAOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
	                  expCAOT.setNRepeticion(idRepe);
	                  listaExpedientesCA = expCALN.buscarOCAPExpedientesCAs(expCAOT, listaRepeticiones);
	                  //Si no hay respuestas, el formulario no está contestado
	                  if (listaExpedientesCA == null || listaExpedientesCA.size()==0)
	                     return false;
	                  */
	                  estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
	                  estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestionarioOT.getCCuestionarioId(),idRepe, idPadreEvidencia);
	                  if (!Constantes.EST_CUEST_FINALIZAR.equals(estado))
	                     return false;
	            } else if (cuestionarioOT.getCCuestionarioIntermedioId()!=0) {
	               OCAPCuestionariosOT auxCuestOT = new OCAPCuestionariosOT();
	               auxCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioIntermedioId());
	               auxCuestOT= detalleCuestionario(usuarioOT, auxCuestOT, idRepe, jcylUsuario);
	               if (!estaContestado(auxCuestOT,usuarioOT, idRepe, listaRepeticiones, idPadreEvidencia, jcylUsuario))
	                  return false;
	            } else if (cuestionarioOT.getListaSubCuestionarios() != null) {
	               //Si es de SubDivision...
	               String idSubCuestionarioElegido= null;
	               if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())){
	                  idSubCuestionarioElegido = buscarRespuestaCuestionarioSubdivision(usuarioOT, cuestionarioOT.getCCuestionarioId(),idRepe);
	               }
	               boolean bHijosSinContestar = false;
	               for (int j=0; j < cuestionarioOT.getListaSubCuestionarios().size(); j++) {
	                  cuestOT = (OCAPCuestionariosOT)cuestionarioOT.getListaSubCuestionarios().get(j);
	                  cuestOT = (OCAPCuestionariosOT)cuestionarioOT.getListaSubCuestionarios().get(j);
	                  repeCuestOT = new OCAPRepeticionesCuestionariosOT();
	                  repeCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
	                  repeCuestOT.setNRepeticion(idRepe);
	                  listaRepeticiones.add(repeCuestOT);
	                  boolean aux = true;
	                  //Si es subdivision, comprobar si está relleno solo el cuestionario hijo elegido en la subdivision
	                  if (Constantes.SI.equals(cuestionarioOT.getBSubdivision()) && cuestOT.getCCuestionarioId() == Long.parseLong(idSubCuestionarioElegido==null?"0":idSubCuestionarioElegido))
	                     aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia, jcylUsuario);
	                  else 
	                     aux = estaContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia, jcylUsuario);
	                  if (!aux) 
	                     bHijosSinContestar = true;
	               }
	               if (bHijosSinContestar){
	                  if (cuestionarioOT.getNMaxHijosContestados()==0 || (cuestionarioOT.getNMaxHijosContestados()!=0 && cuestionarioOT.getNMaxHijosContestados()!=cuestionarioOT.getNHijosContestados()))
	                     return false;
	               }
	            }
	         }
	      } catch (Exception e) {
	         OCAPConfigApp.logger.info(getClass().getName()+" verCuestionario: "+e.getMessage());
	      }
	      return true;
	     }

	public boolean estaParcialmenteContestado(OCAPCuestionariosOT cuestionarioOT, OCAPUsuariosOT usuarioOT, int idRepe,
			ArrayList listaRepeticiones, long idPadreEvidencia, JCYLUsuario jcylUsuario) {
		OCAPExpedientesCAsOT expCAOT = null;
		OCAPExpedientesCAsLN expCALN = new OCAPExpedientesCAsLN(jcylUsuario);
		ArrayList listaExpedientesCA = null;
		OCAPCuestionariosOT cuestOT = null;
		OCAPRepeticionesCuestionariosOT repeCuestOT = null;
		OCAPCreditosCuestionariosLN creditosCuestLN = null;
		ArrayList listaCreditosCuestionario = null;
		OCAPEstadosCuestionarioLN estCuesLN = null;
		String estado = null;
		try {
			long cExpId = usuarioOT.getCExpId().longValue();
			if (idRepe == 0) {
				for (int i = 0; i < cuestionarioOT.getNRepeticiones().intValue(); i++) {
					if ((cuestionarioOT.getNPreguntasContestables().intValue() != 0)
							&& ((Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()))
									|| (!Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())))) {
						estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
						estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestionarioOT.getCCuestionarioId(), i + 1,
								idPadreEvidencia);
						if ("F".equals(estado)) {
							return true;
						}
					} else if (cuestionarioOT.getCCuestionarioIntermedioId() != 0L) {
						OCAPCuestionariosOT auxCuestOT = new OCAPCuestionariosOT();
						auxCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioIntermedioId());
						auxCuestOT = detalleCuestionario(usuarioOT, auxCuestOT, idRepe, jcylUsuario);
						if (estaParcialmenteContestado(auxCuestOT, usuarioOT, idRepe, listaRepeticiones,
								idPadreEvidencia, jcylUsuario)) {
							return true;
						}
					}
					String idSubCuestionarioElegido = null;
					if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
						idSubCuestionarioElegido = buscarRespuestaCuestionarioSubdivision(usuarioOT,
								cuestionarioOT.getCCuestionarioId(), 1);
					}
					if (cuestionarioOT.getListaSubCuestionarios() != null) {
						for (int j = 0; j < cuestionarioOT.getListaSubCuestionarios().size(); j++) {
							cuestOT = (OCAPCuestionariosOT) cuestionarioOT.getListaSubCuestionarios().get(j);
							repeCuestOT = new OCAPRepeticionesCuestionariosOT();
							repeCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
							repeCuestOT.setNRepeticion(i + 1);
							listaRepeticiones.add(repeCuestOT);
							boolean aux = true;
							if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
								if (cuestOT.getCCuestionarioId() == Long
										.parseLong(idSubCuestionarioElegido == null ? "0" : idSubCuestionarioElegido)) {
									aux = estaParcialmenteContestado(cuestOT, usuarioOT, 0, listaRepeticiones,
											idPadreEvidencia, jcylUsuario);
									if (aux) {
										return true;
									}
								}
							}
							if (!Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
								aux = estaParcialmenteContestado(cuestOT, usuarioOT, 0, listaRepeticiones,
										idPadreEvidencia, jcylUsuario);
							}

							if (cuestOT.getCEvidenciaId() != 0L) {
								estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
								estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestOT.getCEvidenciaId(), i + 1,
										cuestOT.getCCuestionarioId());
								if ("F".equals(estado)) {
									return true;
								}
							}
						}
					}
				}
			} else if ((cuestionarioOT.getNPreguntasContestables().intValue() != 0)
					&& ((Constantes.SI.equals(cuestionarioOT.getBContestarUsuario()))
							|| (!Constantes.OCAP_USUARIO_FASE_III.equals(jcylUsuario.getUser().getRol())))) {
				estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
				estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestionarioOT.getCCuestionarioId(), idRepe,
						idPadreEvidencia);
				if ("F".equals(estado)) {
					return true;
				}
			} else if (cuestionarioOT.getCCuestionarioIntermedioId() != 0L) {
				OCAPCuestionariosOT auxCuestOT = new OCAPCuestionariosOT();
				auxCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioIntermedioId());
				auxCuestOT = detalleCuestionario(usuarioOT, auxCuestOT, idRepe, jcylUsuario);
				if (estaParcialmenteContestado(auxCuestOT, usuarioOT, idRepe, listaRepeticiones, idPadreEvidencia,
						jcylUsuario)) {
					return true;
				}
			} else if (cuestionarioOT.getListaSubCuestionarios() != null) {
				String idSubCuestionarioElegido = null;
				if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
					idSubCuestionarioElegido = buscarRespuestaCuestionarioSubdivision(usuarioOT,
							cuestionarioOT.getCCuestionarioId(), idRepe);
				}
				for (int j = 0; j < cuestionarioOT.getListaSubCuestionarios().size(); j++) {
					cuestOT = (OCAPCuestionariosOT) cuestionarioOT.getListaSubCuestionarios().get(j);
					cuestOT = (OCAPCuestionariosOT) cuestionarioOT.getListaSubCuestionarios().get(j);
					repeCuestOT = new OCAPRepeticionesCuestionariosOT();
					repeCuestOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
					repeCuestOT.setNRepeticion(idRepe);
					listaRepeticiones.add(repeCuestOT);
					boolean aux = true;
					if (Constantes.SI.equals(cuestionarioOT.getBSubdivision())) {
						if (cuestOT.getCCuestionarioId() == Long
								.parseLong(idSubCuestionarioElegido == null ? "0" : idSubCuestionarioElegido)) {
							aux = estaParcialmenteContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia,
									jcylUsuario);
							if (aux) {
								return true;
							}
						}
					}
					aux = estaParcialmenteContestado(cuestOT, usuarioOT, 0, listaRepeticiones, idPadreEvidencia,
							jcylUsuario);
					if (cuestOT.getCEvidenciaId() != 0L) {
						estCuesLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
						estado = estCuesLN.buscarEstadoCuestionario(cExpId, cuestOT.getCEvidenciaId(), idRepe,
								cuestOT.getCCuestionarioId());
						if ("F".equals(estado)) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			OCAPConfigApp.logger.info(getClass().getName() + " verCuestionario: " + e.toString());
		}
		return false;
	}

	public OCAPCuestionariosOT clonarCuestionarioOT(OCAPCuestionariosOT cuestOT) {
		OCAPCuestionariosOT cuestionarioOT = new OCAPCuestionariosOT();
		cuestionarioOT.setBBorrado(cuestOT.getBBorrado());
		cuestionarioOT.setBContestado(cuestOT.getBContestado());
		cuestionarioOT.setBContestarUsuario(cuestOT.getBContestarUsuario());
		cuestionarioOT.setBNumeracion(cuestOT.getBNumeracion());
		cuestionarioOT.setBObligatorio(cuestOT.getBObligatorio());
		cuestionarioOT.setBVerPreguntas(cuestOT.getBVerPreguntas());
		cuestionarioOT.setCAreaId(cuestOT.getCAreaId());
		cuestionarioOT.setCCuestionarioId(cuestOT.getCCuestionarioId());
		cuestionarioOT.setCCuestionarioIntermedioId(cuestOT.getCCuestionarioIntermedioId());
		cuestionarioOT.setCCuestionarioPadreId(cuestOT.getCCuestionarioPadreId());
		cuestionarioOT.setCEspecId(cuestOT.getCEspecId());
		cuestionarioOT.setCItinerarioId(cuestOT.getCItinerarioId());
		cuestionarioOT.setCPreguntaId(cuestOT.getCPreguntaId());
		cuestionarioOT.setCPuestoId(cuestOT.getCPuestoId());
		cuestionarioOT.setCServicioId(cuestOT.getCServicioId());
		cuestionarioOT.setCTipoPregunta(cuestOT.getCTipoPregunta());
		cuestionarioOT.setDArea(cuestOT.getDArea());
		cuestionarioOT.setDAreaLargo(cuestOT.getDAreaLargo());
		cuestionarioOT.setDEvidencia(cuestOT.getDEvidencia());
		cuestionarioOT.setDMensaje(cuestOT.getDMensaje());
		cuestionarioOT.setDNombre(cuestOT.getDNombre());
		cuestionarioOT.setDNombreLargo(cuestOT.getDNombreLargo());
		cuestionarioOT.setDObservaciones(cuestOT.getDObservaciones());
		cuestionarioOT.setDObservacionesEvidencia(cuestOT.getDObservacionesEvidencia());
		cuestionarioOT.setDPregunta(cuestOT.getDPregunta());
		cuestionarioOT.setFCreacion(cuestOT.getFCreacion());
		cuestionarioOT.setFModificacion(cuestOT.getFModificacion());
		cuestionarioOT.setListaPosiblesRespuestas(cuestOT.getListaPosiblesRespuestas());
		cuestionarioOT.setListaRepeticiones(cuestOT.getListaRepeticiones());
		cuestionarioOT.setListaRespuestas(cuestOT.getListaRespuestas());
		cuestionarioOT.setListaSubCuestionarios(cuestOT.getListaSubCuestionarios());
		cuestionarioOT.setNCreditos(cuestOT.getNCreditos());
		cuestionarioOT.setNElementos(cuestOT.getNElementos());
		cuestionarioOT.setNItinerario(cuestOT.getNItinerario());
		cuestionarioOT.setNNivel(cuestOT.getNNivel());
		cuestionarioOT.setNPreguntas(cuestOT.getNPreguntas());
		cuestionarioOT.setNPreguntasContestables(cuestOT.getNPreguntasContestables());
		cuestionarioOT.setNRepeticiones(cuestOT.getNRepeticiones());
		cuestionarioOT.setNSubCuestionarios(cuestOT.getNSubCuestionarios());
		cuestionarioOT.setNSubElementos(cuestOT.getNSubElementos());
		cuestionarioOT.setDRepeticion(cuestOT.getDRepeticion());
		cuestionarioOT.setCEvidenciaId(cuestOT.getCEvidenciaId());

		return cuestionarioOT;
	}

	public String buscarRespuestaCuestionarioSubdivision(OCAPUsuariosOT usuOT, long idCuestionario, int idRepeticion)
			throws Exception {
		String respuesta = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			respuesta = this.cuestionariosOAD.buscarRespuestaCuestionarioSubdivision(usuOT, idCuestionario,
					idRepeticion);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return respuesta;
	}

	public OCAPCuestionariosOT buscarCuestionario(long cCuestionarioId) {
		OCAPCuestionariosOT cuestionarioOT = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			cuestionarioOT = this.cuestionariosOAD.buscarCuestionario(cCuestionarioId);
		} catch (Exception e) {
			cuestionarioOT = null;
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}
		return cuestionarioOT;
	}

	public long buscarCuestionarioAsociado(long cItinerarioId, int nEvidencia, Integer cuestionarioPadre) throws Exception {
		long idCuestionario = 0L;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			idCuestionario = this.cuestionariosOAD.buscarCuestionarioAsociado(cItinerarioId, nEvidencia, cuestionarioPadre);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return idCuestionario;
	}

	public void guardarCuestionario(OCAPCuestionariosOT cuestionarioOT) throws Exception {
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			this.cuestionariosOAD.guardarCuestionarioObservEvidencia(cuestionarioOT);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
	}

	public int insertarCuestionario(OCAPUsuariosOT usuarioOT, long idCuestionario, int idRepeticion,
			ArrayList listaAux2, String cadenaRespuestas, boolean bFinalizar, String idCuestDespuesIntermedio,
			int nEvidencia, long idPadreEvidencia, JCYLUsuario jcylUsuario, HttpSession session)
			throws SQLException, Exception {
		OCAPCuestionariosOT cuestionarioOT;
		long idCuest;
		int cuentaPreguntasNoVacias;
		boolean autoCalcularCreditos;

		ArrayList listaPreguntasOriginal = null;
		ArrayList listaCalculos = null;

		OCAPExpedientesCAsOT expedienteCAOriginalT = null;
		OCAPExpedientesCAsOT expedienteDuplicadoOT = null;

		cuestionarioOT = null;
		idCuest = 0L;
		cuentaPreguntasNoVacias = 0;
		autoCalcularCreditos = false;

		float puntuacion = 0.0F;
		try {
			OCAPConfigApp.logger.debug("insertarCuestionario INICIO");
			JCYLGestionTransacciones.open(false);
			if (!Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
				borrarRespuestasCuestionario(usuarioOT, idCuestionario, listaAux2, idRepeticion, idPadreEvidencia,
						jcylUsuario);
				OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
						.append(" insertarCuestionario: Respuestas anteriores borradas").toString());
			}
			OCAPPreguntasLN preguntasLN = new OCAPPreguntasLN(jcylUsuario);
			ArrayList listaPreguntas = buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux2,
					idRepeticion, false, false, nEvidencia, idPadreEvidencia, jcylUsuario);
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
					.append(" insertarCuestionario: Preguntas buscadas").toString());
			HashMap tablaRespuestas = new HashMap();
			if (cadenaRespuestas != null) {
				StringTokenizer token = new StringTokenizer(cadenaRespuestas, "&");
				StringTokenizer token2 = null;
				String aux1;
				String aux2;
				for (; token.hasMoreTokens(); tablaRespuestas.put(aux1, aux2)) {
					token2 = new StringTokenizer(token.nextToken(), "=");
					aux1 = "";
					aux2 = "";
					if (token2.hasMoreTokens())
						aux1 = token2.nextToken();
					if (token2.hasMoreTokens())
						aux2 = token2.nextToken();
				}

			}
			OCAPExpedientesCAsOT expedienteCAOT = new OCAPExpedientesCAsOT();
			OCAPExpedientesCAsLN expedienteCALN = new OCAPExpedientesCAsLN(jcylUsuario);
			expedienteCAOT.setCExpId(usuarioOT.getCExpId().longValue());
			float creditos = 0.0F;
			ArrayList listaExpedienteCas = new ArrayList();
			for (int k = 0; k < listaPreguntas.size(); k++) {
				expedienteCAOT = new OCAPExpedientesCAsOT();
				expedienteCAOT.setCExpId(usuarioOT.getCExpId().longValue());
				cuestionarioOT = (OCAPCuestionariosOT) listaPreguntas.get(k);
				expedienteCAOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
				expedienteCAOT.setCPadreEvidenciaId(idPadreEvidencia);
				idCuest = cuestionarioOT.getCCuestionarioId();
				expedienteCAOT.setCPreguntaId(cuestionarioOT.getCPreguntaId());
				expedienteCAOT.setNRepeticion(idRepeticion);
				expedienteCAOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
				cuestionarioOT.setListaRepeticiones(listaAux2);
				if ("radio".equals(cuestionarioOT.getCTipoPregunta())) {
					for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
						expedienteCAOT.setDValor(tablaRespuestas.get((new StringBuilder()).append("pregunta")
								.append(cuentaPreguntasNoVacias).append("_").append(i).append("_0").toString())
								.toString());
						long cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOT);
						OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
								.append(" insertarCuestionario: se inserta una respuesta de tipo radio").toString());
						if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null) {
							expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
									cuestionarioOT.getListaRepeticiones(), jcylUsuario.getUsuario().getC_usr_id());
							OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
									.append(" insertarCuestionario: se insertan las repeticiones de una respuesta de tipo radio")
									.toString());
						}
					}

					cuentaPreguntasNoVacias++;
				} else if ("radioText".equals(cuestionarioOT.getCTipoPregunta())) {
					for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
						expedienteCAOT.setDValor(tablaRespuestas.get((new StringBuilder()).append("pregunta")
								.append(cuentaPreguntasNoVacias).append("_").append(i).append("_0").toString())
								.toString());
						long cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOT);
						OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
								.append(" insertarCuestionario: se inserta una respuesta de tipo radioText")
								.toString());
						if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null) {
							expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
									cuestionarioOT.getListaRepeticiones(), jcylUsuario.getUsuario().getC_usr_id());
							OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
									.append(" insertarCuestionario: se insertan las repeticiones de una respuesta de tipo radioText")
									.toString());
						}
						if ("O".equals(expedienteCAOT.getDValor())) {
							String valor = tablaRespuestas.get((new StringBuilder()).append("pregunta")
									.append(cuentaPreguntasNoVacias).append("_").append(i).append("_1").toString())
									.toString();
							valor = valor.replaceAll(Constantes.SALTO_LINEA, "\n");
							valor = valor.replaceAll(Constantes.RETORNO_CARRO, "\r");
							valor = valor.replaceAll(Constantes.MAS, "+");
							valor = valor.replaceAll(Constantes.IGUAL, "=");
							OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
									.append(" insertarCuestionario: VALOR: ").append(valor).toString());
							expedienteCAOT.setDValor(valor);
							cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOT);
							OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
									.append(" insertarCuestionario: se inserta una respuesta").toString());
							if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null) {
								expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
										cuestionarioOT.getListaRepeticiones(), jcylUsuario.getUsuario().getC_usr_id());
								OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
										.append(" insertarCuestionario: se insertan las repeticiones de una respuesta de tipo radio")
										.toString());
							}
						}
					}

					cuentaPreguntasNoVacias++;
				} else if (!"vacio".equals(cuestionarioOT.getCTipoPregunta())
						&& !"comentario".equals(cuestionarioOT.getCTipoPregunta())) {
					for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
						for (int j = 0; j < cuestionarioOT.getNSubElementos(); j++) {
							String valor = tablaRespuestas
									.get((new StringBuilder()).append("pregunta").append(cuentaPreguntasNoVacias)
											.append("_").append(i).append("_").append(j).toString())
									.toString();
							valor = valor.replaceAll(Constantes.SALTO_LINEA, "\n");
							valor = valor.replaceAll(Constantes.RETORNO_CARRO, "\r");
							valor = valor.replaceAll(Constantes.MAS, "+");
							valor = valor.replaceAll(Constantes.IGUAL, "=");
							 valor = valor.replaceAll("`","'");
			                  valor = valor.replaceAll("´","'");
							OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
									.append(" insertarCuestionario: VALOR: ").append(valor).toString());
							expedienteCAOT.setDValor(valor);
							if (cuestionarioOT.getBIndicadores().equals(Constantes.SI) && expedienteCAOT.getDValor().equals(Constantes.SI_ESP))
								listaExpedienteCas.add(expedienteCAOT);
							long cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOT);
							OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
									.append(" insertarCuestionario: se inserta una respuesta").toString());
							if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null) {
								expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
										cuestionarioOT.getListaRepeticiones(), jcylUsuario.getUsuario().getC_usr_id());
								OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
										.append(" insertarCuestionario: se insertan las repeticiones de una respuesta de tipo text, textarea, etc...")
										.toString());
							}
							if ("creditos".equals(cuestionarioOT.getCTipoPregunta()) && valor != null
									&& !"".equals(valor))
								creditos += Float.parseFloat(valor);
						}

					}

					cuentaPreguntasNoVacias++;
				}
			}

			if (cuestionarioOT.getBIndicadores().equals(Constantes.SI)) {
				long idCuestionarioCopia = idCuestionario;
				Long cuestionarioOriginal = null;

				idCuestionario = Long.parseLong(idCuestDespuesIntermedio);
				borrarRespuestasCuestionario(usuarioOT, idCuestionario, listaAux2, idRepeticion, 0L, jcylUsuario);
				listaPreguntasOriginal = buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux2,
						idRepeticion, false, false, 0, 0L, jcylUsuario);
				expedienteCALN = new OCAPExpedientesCAsLN(jcylUsuario);
				creditos = 0.0F;
				for (int i = 0; i < listaPreguntasOriginal.size(); i++) {
					expedienteCAOriginalT = new OCAPExpedientesCAsOT();
					cuestionarioOT = (OCAPCuestionariosOT) listaPreguntasOriginal.get(i);
					if (!Constantes.SI.equals(cuestionarioOT.getBPregIndicadores())) {
						expedienteCAOriginalT.setCExpId(usuarioOT.getCExpId().longValue());
						expedienteCAOriginalT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
						expedienteCAOriginalT.setCPreguntaId(cuestionarioOT.getCPreguntaId());
						expedienteCAOriginalT.setNRepeticion(idRepeticion);
						expedienteCAOriginalT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
						cuestionarioOT.setListaRepeticiones(listaAux2);
						if ("radio".equals(cuestionarioOT.getCTipoPregunta())
								|| "radioText".equals(cuestionarioOT.getCTipoPregunta())) {
							for (int cont_i = 0; cont_i < cuestionarioOT.getNElementos(); cont_i++) {
								expedienteCAOriginalT.setDValor("");
								long cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOriginalT);
								if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null)
									expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
											cuestionarioOT.getListaRepeticiones(),
											jcylUsuario.getUsuario().getC_usr_id());
							}

						} else if (!"vacio".equals(cuestionarioOT.getCTipoPregunta())
								&& !"comentario".equals(cuestionarioOT.getCTipoPregunta())) {
							for (int cont_i = 0; cont_i < cuestionarioOT.getNElementos(); cont_i++) {
								for (int j = 0; j < cuestionarioOT.getNSubElementos(); j++) {
									expedienteCAOriginalT.setDValor("");
									long cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOriginalT);
									if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null)
										expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
												cuestionarioOT.getListaRepeticiones(),
												jcylUsuario.getUsuario().getC_usr_id());
								}

							}

						}
					}
				}

				for (int i = 0; i < listaPreguntasOriginal.size(); i++) {
					cuestionarioOT = (OCAPCuestionariosOT) listaPreguntasOriginal.get(i);
					if (Constantes.SI.equals(cuestionarioOT.getBPregIndicadores()))
						break;
				}

				idCuest = cuestionarioOT.getCCuestionarioId();
				if (!"vacio".equals(cuestionarioOT.getCTipoPregunta())
						&& !"comentario".equals(cuestionarioOT.getCTipoPregunta())) {
					int i = 0;
					for (i = 0; i < cuestionarioOT.getNElementos() && i < listaExpedienteCas.size(); i++) {
						for (int j = 0; j < cuestionarioOT.getNSubElementos(); j++) {
							expedienteCAOriginalT = new OCAPExpedientesCAsOT();
							expedienteDuplicadoOT = (OCAPExpedientesCAsOT) listaExpedienteCas.get(i);
							OCAPPreguntasOT preguntaOT = preguntasLN
									.buscarOCAPPregunta(expedienteDuplicadoOT.getCPreguntaId());
							expedienteCAOriginalT.setDValor(preguntaOT.getDNombre());
							expedienteCAOriginalT.setCExpId(usuarioOT.getCExpId().longValue());
							expedienteCAOriginalT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
							expedienteCAOriginalT.setCPreguntaId(cuestionarioOT.getCPreguntaId());
							expedienteCAOriginalT.setNRepeticion(idRepeticion);
							expedienteCAOriginalT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
							cuestionarioOT.setListaRepeticiones(listaAux2);
							String cadena = expedienteCAOriginalT.getDValor();
							String numeroSerie = cadena.split(" - ")[0];
							String textoPregunta = cadena.split(" - ")[1];
							if (j == 0)
								expedienteCAOriginalT.setDValor(numeroSerie);
							else if (j == 1)
								expedienteCAOriginalT.setDValor(textoPregunta);
							String valor = expedienteCAOriginalT.getDValor();
							if (j != 0 && j != 1)
								expedienteCAOriginalT.setDValor("");
							long cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOriginalT);
							if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null)
								expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
										cuestionarioOT.getListaRepeticiones(), jcylUsuario.getUsuario().getC_usr_id());
						}

					}

					for (i = i; i < cuestionarioOT.getNElementos(); i++) {
						for (int j = 0; j < cuestionarioOT.getNSubElementos(); j++) {
							expedienteCAOriginalT = new OCAPExpedientesCAsOT();
							expedienteCAOriginalT.setCExpId(usuarioOT.getCExpId().longValue());
							expedienteCAOriginalT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
							expedienteCAOriginalT.setCPreguntaId(cuestionarioOT.getCPreguntaId());
							expedienteCAOriginalT.setNRepeticion(idRepeticion);
							expedienteCAOriginalT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
							cuestionarioOT.setListaRepeticiones(listaAux2);
							expedienteCAOriginalT.setDValor("");
							long cExpCAId = expedienteCALN.insertarOCAPExpedientesCAs(expedienteCAOriginalT);
							if (cExpCAId != 0L && cuestionarioOT.getListaRepeticiones() != null)
								expedienteCALN.insertarRepeticionesCuestionarios(cExpCAId,
										cuestionarioOT.getListaRepeticiones(), jcylUsuario.getUsuario().getC_usr_id());
						}

					}

				}
				cuentaPreguntasNoVacias++;
				idCuestionario = idCuestionarioCopia;
				idCuest = idCuestionarioCopia;
			}
			puntuacion = calcularPuntuacionCuestionario(usuarioOT, listaAux2, idCuestionario, idRepeticion, jcylUsuario,
					null, nEvidencia, idPadreEvidencia);
			OCAPCreditosCuestionariosLN creditosCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
			OCAPCreditosCuestionariosOT creditosCuestOT = null;
			if (bFinalizar) {
				OCAPCalculoCredCuestionariosLN calculoCredCuestLN = new OCAPCalculoCredCuestionariosLN(jcylUsuario);
				OCAPCalculoCredCuestionariosOT calculoCredCuestOT = null;
				listaCalculos = calculoCredCuestLN.buscarOCAPCalculoCredCuestionario(idCuest);
				OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
						.append(" insertarCuestionario: buscados registros para calcular ponderacion").toString());
				for (int n = 0; n < listaCalculos.size(); n++) {
					calculoCredCuestOT = (OCAPCalculoCredCuestionariosOT) listaCalculos.get(n);
					if (puntuacion >= calculoCredCuestOT.getNPuntuacionMin()
							&& puntuacion <= calculoCredCuestOT.getNPuntuacionMax())
						if (calculoCredCuestOT.getNCreditos() == null)
							creditos = (float) (Math.rint(puntuacion * 100F) / 100D);
						else
							creditos = calculoCredCuestOT.getNCreditos().floatValue();
				}

				if (listaCalculos.size() == 1) {
					calculoCredCuestOT = (OCAPCalculoCredCuestionariosOT) listaCalculos.get(0);
					if (calculoCredCuestOT.getNCreditos() == null
							&& puntuacion > calculoCredCuestOT.getNPuntuacionMax())
						creditos = (float) (Math.rint(calculoCredCuestOT.getNPuntuacionMax() * 100F) / 100D);
				}
				if (listaCalculos.size() > 0)
					autoCalcularCreditos = true;
				else if (Constantes.SI.equals(cuestionarioOT.getBPonderaFormula())) {
					creditos = (puntuacion / (float) cuestionarioOT.getNPreguntasFormula())
							* cuestionarioOT.getNCreditos();
					creditos = (float) (Math.rint(creditos * 100F) / 100D);
					autoCalcularCreditos = true;
				} else if (cuestionarioOT.getCTipoEvidencia() != null) {
					long cuestionariosAsociadoId = buscarCuestionarioAsociado(usuarioOT.getCItinerarioId(), nEvidencia, new Integer((int) idPadreEvidencia));
					ArrayList listaCreditosCuestAsociado = creditosCuestLN.buscarOCAPCreditosCuestionario(
							cuestionariosAsociadoId, idRepeticion, usuarioOT.getCExpId().longValue());
					OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
							.append(" insertarCuestionario: buscados los creditos de un cuestionario").toString());
					if (listaCreditosCuestAsociado != null && listaCreditosCuestAsociado.size() > 0) {
						creditosCuestOT = (OCAPCreditosCuestionariosOT) listaCreditosCuestAsociado.get(0);
						creditos = creditosCuestOT.getNCreditos();
					}
					if (Constantes.PONDERACION_MULTIPLICA.equals(cuestionarioOT.getCTipoEvidencia())) {
						creditos *= puntuacion;
						creditos = (float) (Math.rint(creditos * 1000F) / 1000D);
					} else {
						creditos += puntuacion;
					}
				}
			}
			creditosCuestOT = new OCAPCreditosCuestionariosOT();
			creditosCuestOT.setCExpId(usuarioOT.getCExpId().longValue());
			creditosCuestOT.setCCuestionarioId(idCuest);
			creditosCuestOT.setNRepeticion(expedienteCAOT.getNRepeticion());
			creditosCuestOT.setCPadreEvidenciaId(idPadreEvidencia);
			creditosCuestOT.setNEvidencia(nEvidencia);
			if (!Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
				creditosCuestLN.borrarOCAPCreditosCuestionario(creditosCuestOT);
				OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
						.append(" insertarCuestionario: se borrarn los creditos de un cuestionario").toString());
				creditosCuestOT.setNCreditos(creditos);
				creditosCuestOT.setNPuntuacion(puntuacion);
				creditosCuestOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
				creditosCuestLN.altaOCAPCreditosCuestionarios(creditosCuestOT);
			} else {
				creditosCuestOT.setNCreditosEvaluador(creditos);
				creditosCuestOT.setNCreditosFinales(creditos);
				creditosCuestOT.setAModificadoPor(jcylUsuario.getUsuario().getC_usr_id());
				creditosCuestOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
				creditosCuestLN.modificaOCAPCreditosCuestionarios(creditosCuestOT);
			}
			OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
					.append(" insertarCuestionario: se insertan los creditos de un cuestionario").toString());
			if (bFinalizar) {
				OCAPEstadosCuestionarioLN estCuestLN = new OCAPEstadosCuestionarioLN(jcylUsuario);
				if (!Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
					estCuestLN.borrarOCAPEstadosCuestionario(usuarioOT.getCExpId().longValue(), idCuest,
							expedienteCAOT.getNRepeticion(), idPadreEvidencia);
					OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
							.append(" insertarCuestionario: se borra el estado de un cuestionario ").toString());
					if (autoCalcularCreditos)
						estCuestLN.altaOCAPEstadosCuestionario(usuarioOT.getCExpId().longValue(), idCuest,
								expedienteCAOT.getNRepeticion(), "F", "F", jcylUsuario.getUsuario().getC_usr_id(),
								idPadreEvidencia);
					else
						estCuestLN.altaOCAPEstadosCuestionario(usuarioOT.getCExpId().longValue(), idCuest,
								expedienteCAOT.getNRepeticion(), "F", "", jcylUsuario.getUsuario().getC_usr_id(),
								idPadreEvidencia);
				} else {
					estCuestLN.modificarOCAPEstadoSimulacion(usuarioOT.getCExpId().longValue(), idCuest,
							expedienteCAOT.getNRepeticion(), "F", jcylUsuario.getUsuario().getC_usr_id());
				}
				OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
						.append(" insertarCuestionario: se insertan el estado finalizado de un cuestionario")
						.toString());
			}
			if (!Constantes.SI.equals(session.getAttribute(Constantes.OCAP_PRUEBA_ITINERARIO)))
				JCYLGestionTransacciones.commit();
			OCAPConfigApp.logger.debug("insertarCuestionario FIN");
		} catch (SQLException exSQL) {
			JCYLGestionTransacciones.rollback();
			OCAPConfigApp.loggerBD.error((new StringBuilder()).append("[ERROR-SQL] [[Estado:")
					.append(exSQL.getSQLState()).append("][Código error: ").append(exSQL.getErrorCode())
					.append("][Mensaje: ").append(exSQL.getMessage()).append("]]").toString());
			throw exSQL;
		} catch (Exception e) {
			JCYLGestionTransacciones.rollback();
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		} finally {
			if (!Constantes.SI.equals(session.getAttribute(Constantes.OCAP_PRUEBA_ITINERARIO)))
				JCYLGestionTransacciones.close(true);
		}
		return cuentaPreguntasNoVacias;
	}

	public float calcularPuntuacionCuestionario(OCAPUsuariosOT usuarioOT, ArrayList listaAux2, long idCuestionario,
			int idRepeticion, JCYLUsuario jcylUsuario, ArrayList listaPreguntasConRespuesta, int nEvidencia,
			long cPadreEvidencia) throws SQLException, Exception {
		ArrayList listaPreguntas = null;
		OCAPCuestionariosOT cuestionarioOT = null;
		OCAPExpedientesCAsOT expedienteCAOT = null;
		OCAPRespuestasOT respuestaOT = null;
		if (listaPreguntasConRespuesta != null) {
			listaPreguntas = listaPreguntasConRespuesta;
		} else if (Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) {
			listaPreguntas = buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux2, idRepeticion,
					true, false, nEvidencia, cPadreEvidencia, jcylUsuario);
		} else {
			listaPreguntas = buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux2, idRepeticion,
					false, false, nEvidencia, cPadreEvidencia, jcylUsuario);
		}
		float puntuacionTotal = 0.0F;
		for (int k = 0; k < listaPreguntas.size(); k++) {
			cuestionarioOT = (OCAPCuestionariosOT) listaPreguntas.get(k);
			if ((cuestionarioOT.getListaRespuestas() != null) && (cuestionarioOT.getListaRespuestas().size() != 0)) {
				int numeroRespuestas = 0;
				float puntuacionElementos = 0.0F;

				int contadorElemContestados = 0;
				for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
					if ((cuestionarioOT.getCTipoPregunta().equals("radio"))
							|| (cuestionarioOT.getCTipoPregunta().equals("radioText"))) {
						expedienteCAOT = (OCAPExpedientesCAsOT) cuestionarioOT.getListaRespuestas()
								.get(numeroRespuestas++);
						if ((expedienteCAOT != null) && (expedienteCAOT.getDValor() != null)
								&& (!"".equals(expedienteCAOT.getDValor()))) {
							contadorElemContestados++;
							for (int nRes = 0; nRes < cuestionarioOT.getListaPosiblesRespuestas().size(); nRes++) {
								respuestaOT = (OCAPRespuestasOT) cuestionarioOT.getListaPosiblesRespuestas().get(nRes);
								if (respuestaOT.getDValor().equals(expedienteCAOT.getDValor())) {
									puntuacionElementos += respuestaOT.getNCreditos();
								}
							}
						}
					} else if ((!cuestionarioOT.getCTipoPregunta().equals("vacio"))
							&& (!cuestionarioOT.getCTipoPregunta().equals("comentario"))) {
						float puntuacionSubelementos = 0.0F;
						int contOpcionalesRellenos = 0;
						boolean bTodosSubelemContestados = true;
						for (int nRes = 0; nRes < cuestionarioOT.getListaPosiblesRespuestas().size(); nRes++) {
							expedienteCAOT = (OCAPExpedientesCAsOT) cuestionarioOT.getListaRespuestas()
									.get(numeroRespuestas++);
							respuestaOT = (OCAPRespuestasOT) cuestionarioOT.getListaPosiblesRespuestas().get(nRes);
							if (Constantes.NO.equals(respuestaOT.getBOpcional())) {
								if ((expedienteCAOT.getDValor() != null) && (!"".equals(expedienteCAOT.getDValor()))) {
									puntuacionSubelementos += respuestaOT.getNCreditos();
								} else {
									bTodosSubelemContestados = false;
								}
							} else if ((expedienteCAOT.getDValor() != null)
									&& (!"".equals(expedienteCAOT.getDValor()))) {
								puntuacionSubelementos += respuestaOT.getNCreditos();
								contOpcionalesRellenos++;
							}
						}
						if (cuestionarioOT.getNMinSubElemOpcionales() > contOpcionalesRellenos) {
							bTodosSubelemContestados = false;
						}
						if ((!Constantes.SI.equals(cuestionarioOT.getBPuntuarTodo())) || (bTodosSubelemContestados)) {
							puntuacionElementos += puntuacionSubelementos;
						}
						if (bTodosSubelemContestados) {
							contadorElemContestados++;
						}
					}
				}
				if (cuestionarioOT.getNMinElemRellenos() <= contadorElemContestados) {
					puntuacionTotal += puntuacionElementos;
				}
			}
		}
		return (float) (Math.rint(puntuacionTotal * 10000.0F) / 10000.0D);
	}

	public int guardarCuestionarioPrueba(OCAPUsuariosOT usuarioOT, long idCuestionario, int idRepeticion,
			ArrayList listaAux2, String cadenaRespuestas, boolean bFinalizar, String idCuestDespuesIntermedio,
			int nEvidencia, long idPadreEvidencia, JCYLUsuario jcylUsuario, javax.servlet.http.HttpSession session)
			throws SQLException, Exception {
		OCAPCuestionariosOT cuestionarioOT;
		long idCuest;
		int cuentaPreguntasNoVacias;
		boolean autoCalcularCreditos;
		ArrayList listaPreguntasConRespueta;
		ArrayList listaPreguntas = null;
		ArrayList listaCalculos = null;
		OCAPPreguntasLN preguntasLN = null;
		cuestionarioOT = null;
		idCuest = 0L;
		cuentaPreguntasNoVacias = 0;
		long cExpCAId = 0L;
		autoCalcularCreditos = false;
		HashMap tablaRespuestas = null;
		float puntuacion = 0.0F;
		OCAPExpedientesCAsOT expedienteCAOT = null;
		ArrayList listaRespuestas = null;
		ArrayList listaExpedienteCas = null;
		listaPreguntasConRespueta = new ArrayList();
		try {
			OCAPConfigApp.logger.debug("insertarCuestionario INICIO");
			preguntasLN = new OCAPPreguntasLN(jcylUsuario);
			listaPreguntas = buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario, listaAux2, idRepeticion,
					false, false, nEvidencia, idPadreEvidencia, jcylUsuario);
			tablaRespuestas = new HashMap();
			if (cadenaRespuestas != null) {
				StringTokenizer token = new StringTokenizer(cadenaRespuestas, "&");
				StringTokenizer token2 = null;
				String aux1;
				String aux2;
				for (; token.hasMoreTokens(); tablaRespuestas.put(aux1, aux2)) {
					token2 = new StringTokenizer(token.nextToken(), "=");
					aux1 = "";
					aux2 = "";
					if (token2.hasMoreTokens())
						aux1 = token2.nextToken();
					if (token2.hasMoreTokens())
						aux2 = token2.nextToken();
				}

			}
			expedienteCAOT = new OCAPExpedientesCAsOT();
			expedienteCAOT.setCExpId(usuarioOT.getCExpId().longValue());
			float creditos = 0.0F;
			float creditosAux = 0.0F;
			listaExpedienteCas = new ArrayList();
			for (int k = 0; k < listaPreguntas.size(); k++) {
				listaRespuestas = new ArrayList();
				expedienteCAOT = new OCAPExpedientesCAsOT();
				expedienteCAOT.setCExpId(0L);
				cuestionarioOT = (OCAPCuestionariosOT) listaPreguntas.get(k);
				expedienteCAOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
				idCuest = cuestionarioOT.getCCuestionarioId();
				expedienteCAOT.setCPreguntaId(cuestionarioOT.getCPreguntaId());
				expedienteCAOT.setNRepeticion(idRepeticion);
				expedienteCAOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
				cuestionarioOT.setListaRepeticiones(listaAux2);
				if ("radio".equals(cuestionarioOT.getCTipoPregunta())) {
					for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
						expedienteCAOT.setDValor(tablaRespuestas.get((new StringBuilder()).append("pregunta")
								.append(cuentaPreguntasNoVacias).append("_").append(i).append("_0").toString())
								.toString());
						listaRespuestas.add(expedienteCAOT);
					}

					cuentaPreguntasNoVacias++;
				} else if ("radioText".equals(cuestionarioOT.getCTipoPregunta())) {
					for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
						expedienteCAOT.setDValor(tablaRespuestas.get((new StringBuilder()).append("pregunta")
								.append(cuentaPreguntasNoVacias).append("_").append(i).append("_0").toString())
								.toString());
						listaRespuestas.add(expedienteCAOT);
						if ("O".equals(expedienteCAOT.getDValor())) {
							String valor = tablaRespuestas.get((new StringBuilder()).append("pregunta")
									.append(cuentaPreguntasNoVacias).append("_").append(i).append("_1").toString())
									.toString();
							valor = valor.replaceAll(Constantes.SALTO_LINEA, "\n");
							valor = valor.replaceAll(Constantes.RETORNO_CARRO, "\r");
							valor = valor.replaceAll(Constantes.MAS, "+");
							OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
									.append(" insertarCuestionario: VALOR: ").append(valor).toString());
							expedienteCAOT.setDValor(valor);
							listaRespuestas.add(expedienteCAOT);
						}
					}

					cuentaPreguntasNoVacias++;
				} else if (!"vacio".equals(cuestionarioOT.getCTipoPregunta())
						&& !"comentario".equals(cuestionarioOT.getCTipoPregunta())) {
					for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
						for (int j = 0; j < cuestionarioOT.getNSubElementos(); j++) {
							String valor = tablaRespuestas
									.get((new StringBuilder()).append("pregunta").append(cuentaPreguntasNoVacias)
											.append("_").append(i).append("_").append(j).toString())
									.toString();
							valor = valor.replaceAll(Constantes.SALTO_LINEA, "\n");
							valor = valor.replaceAll(Constantes.RETORNO_CARRO, "\r");
							valor = valor.replaceAll(Constantes.MAS, "+");
							 valor = valor.replaceAll("`","'");
			                  valor = valor.replaceAll("´","'");
							expedienteCAOT.setDValor(valor);
							if (cuestionarioOT.getBIndicadores().equals(Constantes.SI) && expedienteCAOT.getDValor().equals(Constantes.SI_ESP))
								listaExpedienteCas.add(expedienteCAOT);
							listaRespuestas.add(expedienteCAOT);
							if ("creditos".equals(cuestionarioOT.getCTipoPregunta()) && valor != null
									&& !"".equals(valor))
								creditos += Float.parseFloat(valor);
						}

					}

					cuentaPreguntasNoVacias++;
				}
				cuestionarioOT.setListaRespuestas(listaRespuestas);
				listaPreguntasConRespueta.add(cuestionarioOT);
			}

			puntuacion = calcularPuntuacionCuestionario(usuarioOT, listaAux2, idCuestionario, idRepeticion, jcylUsuario,
					listaPreguntasConRespueta, nEvidencia, idPadreEvidencia);
			OCAPCreditosCuestionariosLN creditosCuestLN = new OCAPCreditosCuestionariosLN(jcylUsuario);
			OCAPCreditosCuestionariosOT creditosCuestOT = new OCAPCreditosCuestionariosOT();
			if (bFinalizar) {
				OCAPCalculoCredCuestionariosLN calculoCredCuestLN = new OCAPCalculoCredCuestionariosLN(jcylUsuario);
				OCAPCalculoCredCuestionariosOT calculoCredCuestOT = null;
				listaCalculos = calculoCredCuestLN.buscarOCAPCalculoCredCuestionario(idCuest);
				OCAPConfigApp.logger.info((new StringBuilder()).append(getClass().getName())
						.append(" insertarCuestionario: buscados registros para calcular ponderacion").toString());
				for (int n = 0; n < listaCalculos.size(); n++) {
					calculoCredCuestOT = (OCAPCalculoCredCuestionariosOT) listaCalculos.get(n);
					if (puntuacion >= calculoCredCuestOT.getNPuntuacionMin()
							&& puntuacion <= calculoCredCuestOT.getNPuntuacionMax())
						if (calculoCredCuestOT.getNCreditos() == null)
							creditos = (float) (Math.rint(puntuacion * 100F) / 100D);
						else
							creditos = calculoCredCuestOT.getNCreditos().floatValue();
				}

				if (listaCalculos.size() == 1) {
					calculoCredCuestOT = (OCAPCalculoCredCuestionariosOT) listaCalculos.get(0);
					if (calculoCredCuestOT.getNCreditos() == null
							&& puntuacion > calculoCredCuestOT.getNPuntuacionMax())
						creditos = (float) (Math.rint(calculoCredCuestOT.getNPuntuacionMax() * 100F) / 100D);
				}
				if (listaCalculos.size() > 0)
					autoCalcularCreditos = true;
				else if (Constantes.SI.equals(cuestionarioOT.getBPonderaFormula())) {
					creditos = (puntuacion / (float) cuestionarioOT.getNPreguntasFormula())
							* cuestionarioOT.getNCreditos();
					creditos = (float) (Math.rint(creditos * 100F) / 100D);
					autoCalcularCreditos = true;
				}
				if (cuestionarioOT.getCTipoEvidencia() != null) {
					long cuestionariosAsociadoId = buscarCuestionarioAsociado(usuarioOT.getCItinerarioId(), nEvidencia,null);
					if (session.getAttribute(
							(new StringBuilder()).append("creditosPruebas").append(cuestionariosAsociadoId).append("_")
									.append(idRepeticion).append("_0").toString()) != null)
						creditosAux = Float.parseFloat(session.getAttribute(
								(new StringBuilder()).append("creditosPruebas").append(cuestionariosAsociadoId)
										.append("_").append(idRepeticion).append("_0").toString())
								.toString());
					if (Constantes.PONDERACION_MULTIPLICA.equals(cuestionarioOT.getCTipoEvidencia())) {
						if (autoCalcularCreditos)
							creditos = creditosAux * creditos;
						else
							creditos = creditosAux * puntuacion;
						creditos = (float) (Math.rint(creditos * 1000F) / 1000D);
					} else if (autoCalcularCreditos)
						creditos = creditosAux + creditos;
					else
						creditos = creditosAux + puntuacion;
				}
				session.setAttribute(
						(new StringBuilder()).append("puntosPruebas").append(idCuestionario).append("_")
								.append(idRepeticion).append("_").append(idPadreEvidencia).toString(),
						(new StringBuilder()).append(puntuacion).append("").toString());
				session.setAttribute(
						(new StringBuilder()).append("creditosPruebas").append(idCuestionario).append("_")
								.append(idRepeticion).append("_").append(idPadreEvidencia).toString(),
						(new StringBuilder()).append(creditos).append("").toString());
			}
			OCAPConfigApp.logger.debug("insertarCuestionario FIN");
		} catch (SQLException exSQL) {
			JCYLGestionTransacciones.rollback();
			OCAPConfigApp.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: "
					+ exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
			throw exSQL;
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		} finally {
			if (!Constantes.SI.equals(session.getAttribute(Constantes.OCAP_PRUEBA_ITINERARIO)))
				JCYLGestionTransacciones.close(true);
		}
		return cuentaPreguntasNoVacias;
	}

	public ArrayList crearReportCuestionarios(ArrayList listaPreguntas, OCAPCuestionariosOT cuestionarioOT,
			OCAPUsuariosOT usuarioOT, String rutaRaiz) throws SQLException, Exception {
		ArrayList listadoInformes = null;
		ReportOT reportOT = null;
		String codEti = null;
		OCAPCuestionariosOT cuestionarioAuxOT = null;
		DecimalFormat formato = new DecimalFormat("000000");
		OCAPItinerariosOT itinerarioOT = null;
		OCAPItinerariosLN itinerariosLN = null;
		OCAPExpedientesCAsOT expedienteCAOT = null;
		try {
			String nombreReportPadre = "evidencias";

			listadoInformes = new ArrayList();

			itinerariosLN = new OCAPItinerariosLN(this.jcylUsuario);
			itinerarioOT = itinerariosLN.buscarOCAPItinerarios(usuarioOT.getCItinerarioId());
			for (int i = 0; i < cuestionarioOT.getNRepeticiones().intValue(); i++) {
				reportOT = new ReportOT(rutaRaiz, File.separator, nombreReportPadre);

				Hashtable parametros = new Hashtable();

				parametros.put("rutaImagen", rutaRaiz);

				ConceptoReport datosReport = new ConceptoReport();

				codEti = "EPR" + formato.format(usuarioOT.getCExpId().longValue()) + "-" + usuarioOT.getCItinerarioId()
						+ "-" + cuestionarioOT.getNEvidencia() + "-" + cuestionarioOT.getCCuestionarioId() + "-"
						+ (i + 1);
				if (cuestionarioOT.getCEvidenciaId() != 0L) {
					parametros.put("codBarras", "");
				} else {
					parametros.put("codBarras", codEti);
				}
				parametros.put("nEvidencia", cuestionarioOT.getNEvidencia());
				parametros.put("nRepeticion", String.valueOf(i + 1));
				parametros.put("repeticionesTotales", cuestionarioOT.getNRepeticiones().toString());

				int numPregunta = 0;
				int numPreguntasNivel0 = 0;
				int numPreguntasNivel1 = 0;
				int numPreguntasNivel2 = 0;
				int numeroRespuestas = 0;
				for (int j = 0; j < listaPreguntas.size(); j++) {
					datosReport.addRow();
					cuestionarioAuxOT = null;
					cuestionarioAuxOT = (OCAPCuestionariosOT) listaPreguntas.get(j);
					if ((cuestionarioAuxOT.getDPregunta() != null) && (i == 0)) {
						cuestionarioAuxOT.setDPregunta(cuestionarioAuxOT.getDPregunta() + "&nbsp;");
						cuestionarioAuxOT.setDPregunta(cuestionarioAuxOT.getDPregunta().replaceAll("/>", ">"));
						cuestionarioAuxOT
								.setDPregunta(cuestionarioAuxOT.getDPregunta().replaceAll("<br[ ]*><br[ ]*>", "<br>"));

						String numeracion = "";
						if (!"comentario".equals(cuestionarioAuxOT.getCTipoPregunta())) {
							if (!Constantes.NO_TEXTO_min.equals(cuestionarioAuxOT.getBNumeracion())) {
								if (!"Romana".equals(cuestionarioAuxOT.getBNumeracion())) {
									if (cuestionarioAuxOT.getNNivel() == 0) {
										numPreguntasNivel0++;
										numeracion = String.valueOf(numPreguntasNivel0);
										numPreguntasNivel1 = 0;
										numPreguntasNivel2 = 0;
									}
									if (cuestionarioAuxOT.getNNivel() == 1) {
										numeracion = numPreguntasNivel0 + "." + ++numPreguntasNivel1;
										numPreguntasNivel2 = 0;
									}
									if (cuestionarioAuxOT.getNNivel() == 2) {
										numeracion = numPreguntasNivel0 + "." + numPreguntasNivel1 + "."
												+ ++numPreguntasNivel2;
									}
								} else {
									if (cuestionarioAuxOT.getNNivel() == 0) {
										numPreguntasNivel0++;
										numeracion = Utilidades.getNumeroRomano(numPreguntasNivel0);
										numPreguntasNivel1 = 0;
										numPreguntasNivel2 = 0;
									}
									if (cuestionarioAuxOT.getNNivel() == 1) {
										numeracion = Utilidades.getNumeroRomano(numPreguntasNivel0) + "."
												+ Utilidades.getNumeroRomano(++numPreguntasNivel1);
										numPreguntasNivel2 = 0;
									}
									if (cuestionarioAuxOT.getNNivel() == 2) {
										numeracion = Utilidades.getNumeroRomano(numPreguntasNivel0) + "."
												+ Utilidades.getNumeroRomano(numPreguntasNivel1) + "."
												+ Utilidades.getNumeroRomano(++numPreguntasNivel2);
									}
								}
								numeracion = numeracion + " - ";
							}
							numPregunta++;
						}
						cuestionarioAuxOT.setDPregunta(numeracion + cuestionarioAuxOT.getDPregunta());
					}
					cuestionarioAuxOT.setDNombreItinerario(itinerarioOT.getDDescripcion());
					cuestionarioAuxOT.setDNombreLargo(cuestionarioAuxOT.getDNombreLargo().replaceAll("\n", " "));

					datosReport.putElement("datosPregunta", cuestionarioAuxOT);
					datosReport.putElement("cabeceraPregunta", "");
					if (("radio".equals(cuestionarioAuxOT.getCTipoPregunta()))
							|| ("radioText".equals(cuestionarioAuxOT.getCTipoPregunta()))) {
						if ((cuestionarioAuxOT.getListaRespuestas() != null)
								&& (cuestionarioAuxOT.getListaRespuestas().size() > 0)) {
							expedienteCAOT = (OCAPExpedientesCAsOT) cuestionarioAuxOT.getListaRespuestas().get(0);
						}
						for (int k = 0; k < cuestionarioAuxOT.getListaPosiblesRespuestas().size(); k++) {
							OCAPRespuestasOT respuestaOT = (OCAPRespuestasOT) cuestionarioAuxOT
									.getListaPosiblesRespuestas().get(k);
							datosReport.putElement("datosRespuesta_" + k, respuestaOT);
							if ((expedienteCAOT != null)
									&& (respuestaOT.getDValor().equals(expedienteCAOT.getDValor()))) {
								datosReport.putElement("respuesta_" + k, "X");
							}
						}
					}
					if (("text".equals(cuestionarioAuxOT.getCTipoPregunta()))
							|| ("textarea".equals(cuestionarioAuxOT.getCTipoPregunta()))
							|| ("numero".equals(cuestionarioAuxOT.getCTipoPregunta()))
							|| ("fecha".equals(cuestionarioAuxOT.getCTipoPregunta()))) {
						if ((cuestionarioAuxOT.getListaPosiblesRespuestas() == null)
								|| (cuestionarioAuxOT.getListaPosiblesRespuestas().size() == 0)) {
							for (int k = 0; k < cuestionarioAuxOT.getListaRespuestas().size(); k++) {
								expedienteCAOT = (OCAPExpedientesCAsOT) cuestionarioAuxOT.getListaRespuestas().get(k);
								if (k > 0) {
									datosReport.addRow();
									datosReport.putElement("datosPregunta", cuestionarioAuxOT);
								}
								datosReport.putElement("cabeceraPregunta", "");
								datosReport.putElement("respuesta",
										expedienteCAOT.getDValor() == null ? " " : expedienteCAOT.getDValor());
							}
							if (cuestionarioAuxOT.getListaRespuestas().size() == 0) {
								for (int numElementos = 0; numElementos < cuestionarioAuxOT
										.getNElementos(); numElementos++) {
									for (int numSubElementos = 0; numSubElementos < cuestionarioAuxOT
											.getNSubElementos(); numSubElementos++) {
										datosReport.addRow();
										datosReport.putElement("datosPregunta", cuestionarioAuxOT);
										datosReport.putElement("respuesta", " ");
									}
								}
							}
						} else {
							for (int k = 0; k < cuestionarioAuxOT.getListaPosiblesRespuestas().size(); k++) {
								OCAPRespuestasOT respuestaOT = (OCAPRespuestasOT) cuestionarioAuxOT
										.getListaPosiblesRespuestas().get(k);
								if (k > 0) {
									datosReport.addRow();
									datosReport.putElement("datosPregunta", cuestionarioAuxOT);
								}
								datosReport.putElement("cabeceraPregunta", respuestaOT.getDNombre());
								for (int l = k; l < cuestionarioAuxOT.getListaRespuestas()
										.size(); l += cuestionarioAuxOT.getListaPosiblesRespuestas().size()) {
									expedienteCAOT = (OCAPExpedientesCAsOT) cuestionarioAuxOT.getListaRespuestas()
											.get(l);
									if (l != k) {
										datosReport.addRow();
										datosReport.putElement("datosPregunta", cuestionarioAuxOT);
									}
									datosReport.putElement("respuesta",
											expedienteCAOT.getDValor() == null ? " " : expedienteCAOT.getDValor());
								}
								if (cuestionarioAuxOT.getListaRespuestas().size() == 0) {
									for (int numElementos = 0; numElementos < cuestionarioAuxOT
											.getNElementos(); numElementos++) {
										datosReport.addRow();
										datosReport.putElement("datosPregunta", cuestionarioAuxOT);
										datosReport.putElement("respuesta", " ");
									}
								}
							}
						}
					}
					if ((!"vacio".equals(cuestionarioAuxOT.getCTipoPregunta()))
							&& (!"comentario".equals(cuestionarioAuxOT.getCTipoPregunta()))) {
						numeroRespuestas++;
					}
				}
				reportOT.setParametrosReport(parametros);
				reportOT.setDatosReport(datosReport);

				listadoInformes.add(reportOT);
				parametros = null;
				datosReport = null;
				reportOT = null;
				if (cuestionarioOT.getCEvidenciaId() == 0L) {
					ReportOT reportRespuestasOT = new ReportOT(rutaRaiz, File.separator,
							"plantillaRespuestasEvidencia");
					Hashtable parametros2 = new Hashtable();

					ConceptoReport datosReportRespuestas = new ConceptoReport();
					for (int k = 0; k < listaPreguntas.size(); k++) {
						cuestionarioAuxOT = null;
						cuestionarioAuxOT = (OCAPCuestionariosOT) listaPreguntas.get(k);
						cuestionarioAuxOT.setDNombreItinerario(itinerarioOT.getDDescripcion());
						cuestionarioAuxOT.setDNombreLargo(cuestionarioAuxOT.getDNombreLargo().replaceAll("\n", " "));
						if (("radio".equals(cuestionarioAuxOT.getCTipoPregunta()))
								|| ("radioText".equals(cuestionarioAuxOT.getCTipoPregunta()))) {
							datosReportRespuestas.addRow();
							datosReportRespuestas.putElement("datosPregunta", cuestionarioAuxOT);
						}
					}
					if (datosReportRespuestas.size() > 0) {
						parametros2.put("rutaImagen", rutaRaiz);
						parametros2.put("codBarras", codEti);
						parametros2.put("nEvidencia", cuestionarioOT.getNEvidencia());
						parametros2.put("nRepeticion", String.valueOf(i + 1));
						parametros2.put("repeticionesTotales", cuestionarioOT.getNRepeticiones().toString());

						reportRespuestasOT.setParametrosReport(parametros2);
						reportRespuestasOT.setDatosReport(datosReportRespuestas);
						listadoInformes.add(reportRespuestasOT);
						parametros2 = null;
						datosReportRespuestas = null;
						reportRespuestasOT = null;
					}
				}
			}
		} catch (Exception e) {
			this.logger.error(e);
			throw e;
		}
		return listadoInformes;
	}

	public ReportOT crearPDF(OCAPCuestionariosOT datos, String rutaRaiz, String nombreReportPadre)
			throws SQLException, Exception {
		ArrayList listadoInformes = null;
		ReportOT reportOT = null;
		try {
			reportOT = new ReportOT(rutaRaiz, File.separator, nombreReportPadre);

			Hashtable parametros = new Hashtable();

			parametros.put("ruta", rutaRaiz);
			parametros.put("datosDocu", datos);

			reportOT.setParametrosReport(parametros);
		} catch (Exception e) {
			this.logger.error(e);
			throw e;
		}
		return reportOT;
	}

	public int insertarXML(long idExpediente, long idCuestionario, Map respuestas, int nEvidencia,
			long idPadreEvidencia, boolean bTratar, long idItinerario, OCAPDocumentosOT documentosOT,
			JCYLUsuario jcylUsuario) throws SQLException, Exception {
		OCAPCuestionariosOT cuestionarioOT;
		long idCuest;
		int cuentaPreguntasNoVacias;
		ArrayList listaPreguntas = null;
		ArrayList listaCalculos = null;
		OCAPExpedientesCAsOT expedienteCAOT = null;
		cuestionarioOT = null;
		OCAPUsuariosOT usuarioOT = null;
		idCuest = 0L;
		cuentaPreguntasNoVacias = 0;
		long cExpCAId = 0L;
		boolean autoCalcularCreditos = false;
		float puntuacion = 0.0F;
		String valorResp = null;
		int idRepeticion = 0;
		List cadenaRespuestas = null;
		try {
			OCAPConfigApp.logger.debug("insertarXML: INICIO");
			JCYLGestionTransacciones.open(false);
			if (documentosOT != null && !"".equals(documentosOT.getDDescripcion())) {
				OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
				ArrayList listado = documentosOAD.buscarDocumentosCuestionario(idExpediente, idPadreEvidencia,
						nEvidencia);
				if (listado.size() != 0)
					documentosOAD.borrarDocumento(((OCAPDocumentosOT) listado.get(0)).getCDocumento_id());
				documentosOT.setCDocumento_id(documentosOAD.altaOCAPDocFormulario(documentosOT));
				documentosOAD.guardarDatosBlob(documentosOT);
				loggerXML.info((new StringBuilder()).append("Documento ").append(documentosOT.getDDescripcion())
						.append(" guardado").toString());
			}
			if (respuestas != null && !respuestas.isEmpty()) {
				usuarioOT = new OCAPUsuariosOT();
				usuarioOT.setCExpId(Long.valueOf(idExpediente));
				usuarioOT.setCItinerarioId(idItinerario);
				OCAPCuestionariosOAD cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
				OCAPExpedientesCAsOAD expedientesCAsOAD = OCAPExpedientesCAsOAD.getInstance();
				OCAPEstadosCuestionarioOAD estadosCuestionarioOAD = OCAPEstadosCuestionarioOAD.getInstance();
				OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
				OCAPCalculoCredCuestionariosOAD calculoCredCuestionariosOAD = OCAPCalculoCredCuestionariosOAD
						.getInstance();
				for (Iterator respuestasIT = respuestas.keySet().iterator(); respuestasIT.hasNext();) {
					idRepeticion = ((Integer) respuestasIT.next()).intValue();
					cadenaRespuestas = (ArrayList) respuestas.get(Integer.valueOf(idRepeticion));
					autoCalcularCreditos = false;
					cuentaPreguntasNoVacias = 0;
					if (bTratar)
						cuestionariosOAD.borrarRespuestasCuestionario(usuarioOT, idCuestionario, null, idRepeticion,
								idPadreEvidencia, jcylUsuario);
					listaPreguntas = cuestionariosOAD.buscarPreguntasCuestionarioPorUsuario(usuarioOT, idCuestionario,
							null, idRepeticion, false, false, nEvidencia, idPadreEvidencia, jcylUsuario);
					float creditos = 0.0F;
					float creditosAux = 0.0F;
					for (int l = 0; l < listaPreguntas.size(); l++) {
						cuestionarioOT = (OCAPCuestionariosOT) listaPreguntas.get(l);
						if ("radio".equals(cuestionarioOT.getCTipoPregunta())
								|| "radioText".equals(cuestionarioOT.getCTipoPregunta()))
							cuentaPreguntasNoVacias++;
					}

					if (cuentaPreguntasNoVacias != cadenaRespuestas.size()) {
						loggerXML.error("XML Erróneo. La estructura de respuestas no es correcta");
						JCYLGestionTransacciones.rollback();
						int j = 0;
						JCYLGestionTransacciones.close(true);
						return j;
					}
					cuentaPreguntasNoVacias = 0;
					for (int k = 0; k < listaPreguntas.size(); k++) {
						expedienteCAOT = new OCAPExpedientesCAsOT();
						expedienteCAOT.setCExpId(idExpediente);
						cuestionarioOT = (OCAPCuestionariosOT) listaPreguntas.get(k);
						expedienteCAOT.setCCuestionarioId(cuestionarioOT.getCCuestionarioId());
						idCuest = cuestionarioOT.getCCuestionarioId();
						expedienteCAOT.setCPreguntaId(cuestionarioOT.getCPreguntaId());
						expedienteCAOT.setNRepeticion(idRepeticion);
						expedienteCAOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
						expedienteCAOT.setCPadreEvidenciaId(idPadreEvidencia);
						if ("radio".equals(cuestionarioOT.getCTipoPregunta())) {
							for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
								valorResp = cadenaRespuestas.get(cuentaPreguntasNoVacias).toString();
								if (!valorResp.equals(""))
									valorResp = ((OCAPRespuestasOT) cuestionarioOT.getListaPosiblesRespuestas()
											.get(Integer.valueOf(valorResp).intValue() - 1)).getDValor();
								expedienteCAOT.setDValor(valorResp);
								cExpCAId = expedientesCAsOAD.insertarOCAPExpedientesCAs(expedienteCAOT);
								cuentaPreguntasNoVacias++;
								OCAPConfigApp.logger.info(" insertarXML: se inserta una respuesta de tipo radio");
							}

						} else if ("radioText".equals(cuestionarioOT.getCTipoPregunta())) {
							for (int i = 0; i < cuestionarioOT.getNElementos(); i++) {
								valorResp = cadenaRespuestas.get(cuentaPreguntasNoVacias).toString();
								if (!valorResp.equals(""))
									valorResp = ((OCAPRespuestasOT) cuestionarioOT.getListaPosiblesRespuestas()
											.get(Integer.valueOf(valorResp).intValue() - 1)).getDValor();
								expedienteCAOT.setDValor(valorResp);
								cExpCAId = expedientesCAsOAD.insertarOCAPExpedientesCAs(expedienteCAOT);
								cuentaPreguntasNoVacias++;
								OCAPConfigApp.logger.info(" insertarXML: se inserta una respuesta de tipo radioText");
							}

						}
					}

					puntuacion = calcularPuntuacionCuestionario(usuarioOT, null, idCuestionario, idRepeticion,
							jcylUsuario, null, nEvidencia, idPadreEvidencia);
					OCAPCalculoCredCuestionariosOT calculoCredCuestOT = null;
					listaCalculos = calculoCredCuestionariosOAD.buscarOCAPCalculoCredCuestionario(idCuest);
					OCAPConfigApp.logger.info(" insertarXML: buscados registros para calcular ponderacion");
					for (int n = 0; n < listaCalculos.size(); n++) {
						calculoCredCuestOT = (OCAPCalculoCredCuestionariosOT) listaCalculos.get(n);
						if (puntuacion >= calculoCredCuestOT.getNPuntuacionMin()
								&& puntuacion <= calculoCredCuestOT.getNPuntuacionMax())
							if (calculoCredCuestOT.getNCreditos() == null)
								creditos = (float) (Math.rint(puntuacion * 100F) / 100D);
							else
								creditos = calculoCredCuestOT.getNCreditos().floatValue();
					}

					if (listaCalculos.size() == 1) {
						calculoCredCuestOT = (OCAPCalculoCredCuestionariosOT) listaCalculos.get(0);
						if (calculoCredCuestOT.getNCreditos() == null
								&& puntuacion > calculoCredCuestOT.getNPuntuacionMax())
							creditos = (float) (Math.rint(calculoCredCuestOT.getNPuntuacionMax() * 100F) / 100D);
					}
					if (listaCalculos.size() > 0)
						autoCalcularCreditos = true;
					else if (Constantes.SI.equals(cuestionarioOT.getBPonderaFormula())) {
						creditos = (puntuacion / (float) cuestionarioOT.getNPreguntasFormula())
								* cuestionarioOT.getNCreditos();
						creditos = (float) (Math.rint(creditos * 100F) / 100D);
						autoCalcularCreditos = true;
					}
					OCAPCreditosCuestionariosOT creditosCuestOT = null;
					if (cuestionarioOT.getCTipoEvidencia() != null) {
						long cuestionariosAsociadoId = cuestionariosOAD.buscarCuestionarioAsociado(idItinerario,
								nEvidencia,null);
						ArrayList listaCreditosCuestAsociado = creditosCuestionariosOAD
								.buscarOCAPCreditosCuestionario(cuestionariosAsociadoId, idRepeticion, idExpediente);
						if (listaCreditosCuestAsociado != null && listaCreditosCuestAsociado.size() > 0) {
							creditosCuestOT = (OCAPCreditosCuestionariosOT) listaCreditosCuestAsociado.get(0);
							creditosAux = creditosCuestOT.getNCreditos();
						}
						if (Constantes.PONDERACION_MULTIPLICA.equals(cuestionarioOT.getCTipoEvidencia())) {
							if (autoCalcularCreditos)
								creditos = creditosAux * creditos;
							else
								creditos = creditosAux * puntuacion;
							creditos = (float) (Math.rint(creditos * 1000F) / 1000D);
						} else {
							OCAPCuestionariosOT evidenciaOT = cuestionariosOAD.buscarEvidenciaDocumental(idExpediente,
									idItinerario, nEvidencia);
							if (autoCalcularCreditos) {
								if (evidenciaOT.getNElementos() == 0)
									creditos = creditosAux + creditos;
								else
									creditos = creditos;
							} else if (evidenciaOT.getNElementos() == 0)
								creditos = creditosAux + puntuacion;
							else
								creditos = puntuacion;
						}
					}
					creditosCuestOT = new OCAPCreditosCuestionariosOT();
					creditosCuestOT.setCExpId(idExpediente);
					creditosCuestOT.setCCuestionarioId(idCuest);
					creditosCuestOT.setNRepeticion(idRepeticion);
					creditosCuestOT.setCPadreEvidenciaId(idPadreEvidencia);
					creditosCuestOT.setNEvidencia(nEvidencia);
					creditosCuestionariosOAD.borrarOCAPCreditosCuestionario(creditosCuestOT);
					creditosCuestOT.setNCreditos(creditos);
					creditosCuestOT.setNPuntuacion(puntuacion);
					creditosCuestOT.setACreadoPor(jcylUsuario.getUsuario().getC_usr_id());
					creditosCuestionariosOAD.altaOCAPCreditosCuestionarios(creditosCuestOT);
					estadosCuestionarioOAD.borraOCAPEstadosCuestionario(idExpediente, idCuest, idRepeticion,
							idPadreEvidencia);
					if (autoCalcularCreditos)
						estadosCuestionarioOAD.altaOCAPEstadosCuestionario(idExpediente, idCuest, idRepeticion, "F",
								"F", jcylUsuario.getUsuario().getC_usr_id(), idPadreEvidencia);
					else
						estadosCuestionarioOAD.altaOCAPEstadosCuestionario(idExpediente, idCuest, idRepeticion, "F", "",
								jcylUsuario.getUsuario().getC_usr_id(), idPadreEvidencia);
				}

			}
			JCYLGestionTransacciones.commit();
			OCAPConfigApp.logger.debug("insertarXML FIN");
		} catch (SQLException exSQL) {
			JCYLGestionTransacciones.rollback();
			OCAPConfigApp.loggerBD.error((new StringBuilder()).append("[ERROR-SQL] [[Estado:")
					.append(exSQL.getSQLState()).append("][Código error: ").append(exSQL.getErrorCode())
					.append("][Mensaje: ").append(exSQL.getMessage()).append("]]").toString());
			throw exSQL;
		} catch (NumberFormatException exNum) {
			JCYLGestionTransacciones.rollback();
			OCAPConfigApp.loggerXML.error((new StringBuilder()).append("XML Erróneo. La respuesta ")
					.append(++cuentaPreguntasNoVacias).append(" debe tener un atributo valor ").toString());
			cuentaPreguntasNoVacias = 0;
		} catch (ClassCastException exCast) {
			JCYLGestionTransacciones.rollback();
			OCAPConfigApp.loggerXML.error((new StringBuilder()).append("XML Erróneo. La respuesta ")
					.append(++cuentaPreguntasNoVacias).append(" debe tener un atributo texto ").toString());
			cuentaPreguntasNoVacias = 0;
		} catch (Exception e) {
			JCYLGestionTransacciones.rollback();
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;

		} finally {
			JCYLGestionTransacciones.close(true);
		}
		return cuentaPreguntasNoVacias;

	}

	public int insertarEvidenciaDocumental(OCAPCreditosCuestionariosOT creditosCuestionariosOT, OCAPDocumentosOT documentosOT)
      throws Exception
  {
      int result = 0;
      try
      {
          OCAPConfigApp.logger.debug("insertarEvidenciaDocumental: INICIO");
          JCYLGestionTransacciones.open(false);
          if(documentosOT != null && !"".equals(documentosOT.getDDescripcion()))
          {
              OCAPDocumentosOAD documentosOAD = OCAPDocumentosOAD.getInstance();
              ArrayList listado = documentosOAD.buscarDocumentosCuestionario(documentosOT.getCExpId().longValue(), documentosOT.getCCuestionarioId().longValue(), Long.parseLong(documentosOT.getDTitulo()));
              if(listado.size() != 0)
                  documentosOAD.borrarDocumento(((OCAPDocumentosOT)listado.get(0)).getCDocumento_id());
              documentosOT.setCDocumento_id(documentosOAD.altaOCAPDocFormulario(documentosOT));
              documentosOAD.guardarDatosBlob(documentosOT);
              loggerXML.info((new StringBuilder()).append("Documento ").append(documentosOT.getDDescripcion()).append(" guardado").toString());
              result = 1;
          }
          if(creditosCuestionariosOT != null)
          {
              OCAPCreditosCuestionariosOAD creditosCuestionariosOAD = OCAPCreditosCuestionariosOAD.getInstance();
              creditosCuestionariosOAD.borrarOCAPCreditosCuestionario(creditosCuestionariosOT);
              result = creditosCuestionariosOAD.altaOCAPCreditosCuestionarios(creditosCuestionariosOT);
              loggerXML.info((new StringBuilder()).append("Evidencia documental tratada: Créditos ").append(creditosCuestionariosOT.getNCreditos()).toString());
          }
          JCYLGestionTransacciones.commit();
          OCAPConfigApp.logger.debug("insertarEvidenciaDocumental FIN");
      }
      catch(SQLException exSQL)
      {
          JCYLGestionTransacciones.rollback();
          OCAPConfigApp.loggerBD.error((new StringBuilder()).append("[ERROR-SQL] [[Estado:").append(exSQL.getSQLState()).append("][Código error: ").append(exSQL.getErrorCode()).append("][Mensaje: ").append(exSQL.getMessage()).append("]]").toString());
          throw exSQL;
      }
      catch(Exception e)
      {
          JCYLGestionTransacciones.rollback();
          OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
          throw e;
      }
      finally{
    	  JCYLGestionTransacciones.close(true);
      }
      
      return result;
  }

	public OCAPCuestionariosOT buscarEvidenciaDocumental(long cExpId, long cItinerarioId, int nEvidencia)
			throws Exception {
		OCAPCuestionariosOT evidencia = null;
		try {
			this.cuestionariosOAD = OCAPCuestionariosOAD.getInstance();
			evidencia = this.cuestionariosOAD.buscarEvidenciaDocumental(cExpId, cItinerarioId, nEvidencia);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		}
		return evidencia;
	}
}

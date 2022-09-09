package es.jcyl.fqs.ocap.oad.solicitudes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.ot.acceso.OCAPAccesoOT;
import es.jcyl.fqs.ocap.ot.solicitudes.OCAPSolicitudesOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.DateUtil;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;

public class OCAPSolicitudesOAD {
	public static Logger logger = OCAPConfigApp.logger;
	protected Connection conTransaccion;
	private OCAPSolicitudesOT solicitudesOT;
	private long ESTADO_SIN_ESTADO;
	private static OCAPSolicitudesOAD instance;
	private static final String ORDEN_GERENCIA = "G";
	private static final String ORDEN_ALFABETICO = "A";

	public static OCAPSolicitudesOAD getInstance() {
		if (instance == null) {
			instance = new OCAPSolicitudesOAD();
		}
		return instance;
	}

	public ArrayList buscarSolicitudes(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT,
			JCYLUsuario jcylUsuario, boolean esPDF, boolean esAgrupado, String menu, boolean reasociarGrs) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudes: Inicio");
		PreparedStatement sentencia = null;
		Connection con = null;
		StringBuffer consulta = new StringBuffer();
		ResultSet resultado = null;
		ArrayList listado = null;
		try {
			con = JCYLGestionTransacciones.getConnection();
			consulta.append(
					"SELECT '1' as ORDENFIJO, usu.c_usr_id, solic.d_nombre, solic.d_apellidos, solic.c_dni, solic.c_dni as c_dni_real, solic.b_sexo,")
					.append(" TO_DATE(exp.f_registro_solic, 'DD/MM/RRRR hh24:mi:ss') AS f_registro_solic, exp.c_grado_id, exp.c_convocatoria_id, TO_DATE(con.f_publicacion, 'DD/MM/RRRR') as f_convocatoria, con.d_nombre as d_convocatoria, con.d_nombre_corto AS d_convocatoria_corto, TO_DATE(con.f_alegsolicitud, 'DD/MM/RRRR') as f_listado, TO_DATE (con.f_fin_pgp, 'DD/MM/RRRR') AS f_fin_pgp,")
					.append(" TO_DATE(EXP.F_ACEPTAC_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_ACEPTAC_SOLIC , ")
					.append(" TO_DATE(EXP.f_negacion_solic,'DD/MM/RRRR hh24:mi:ss') as f_negacion_solic , ")
					.append(" TO_DATE(EXP.F_RESPUESTAINCONF_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_RESPUESTAINCONF_SOLIC, ")
					.append(" TO_DATE(EXP.F_ACEPTACIONCEIS,'DD/MM/RRRR hh24:mi:ss') as F_ACEPTACIONCEIS, ")
					.append(" TO_DATE(EXP.f_negacion_mc,'DD/MM/RRRR hh24:mi:ss') as f_negacion_mc, ")
					.append(" TO_DATE(EXP.f_inicio_mc,'DD/MM/RRRR hh24:mi:ss') as f_inicio_mc, ")
					.append(" TO_DATE(EXP.f_fin_mc,'DD/MM/RRRR hh24:mi:ss') as f_fin_mc, ")
					.append(" TO_DATE(EXP.f_inicio_eval_mc,'DD/MM/RRRR hh24:mi:ss') as f_inicio_eval_mc, ")
					.append(" TO_DATE(EXP.f_fin_eval_mc,'DD/MM/RRRR hh24:mi:ss') as f_fin_eval_mc, ")
					.append(" TO_DATE(EXP.f_fin_cc,'DD/MM/RRRR hh24:mi:ss') as f_fin_cc, ")
					.append(" TO_DATE(EXP.f_inconf_mc,'DD/MM/RRRR hh24:mi:ss') as f_inconf_mc, ")
					.append(" TO_DATE(con.f_rec_grado,'DD/MM/RRRR hh24:mi:ss') as f_rec_grado, ")
					.append(" exp.c_profesional_id, exp.c_espec_id, gra.d_descripcion, cat.d_nombre d_nombre_cat, esp.d_nombre d_nombre_esp, exp.c_exp_id, exp.B_VALIDACIONCC, exp.B_INCONF_ANTIGUEDAD, exp.B_INCONF_PLAZAPROP, exp.B_PERSONALES, exp.B_OTROSCENTROS, exp.B_PLAZO, ")
					.append(" exp.c_solicitud_id, exp.c_estado_id, mod.d_nombre as dModalidadCat, ")
					.append(" exp.c_proc_evaluacion_id, exp.c_sit_administrativa_id, exp.a_otrasitadministrativa, solic.c_gerencia_actual_id as C_GERENCIA_ID, ger.d_nombre as d_nombre_ger, ger.a_codldap, exp.a_puesto, exp.f_desistido_mc, exp.b_reconocimiento_grado, est.D_NOMBRE as dEstado,  ")
					.append(" (SELECT count(*) FROM ocap_solicitudes WHERE c_usr_id = usu.c_usr_id AND c_convocatoria_id = exp.c_convocatoria_id AND b_borrado='N') as numSolicitudes ");

			consulta.append(
					" FROM ocap_solicitudes solic, ocap_usuarios usu, ocap_expedientescarrera exp, ocap_estados est, ocap_grados gra, ocap_categ_profesionales cat, ocap_especialidades esp, ocap_convocatorias con, ocap_gerencias ger, ocap_modalidades mod  ");

			consulta.append(" WHERE usu.c_usr_id = exp.c_usr_id").append(" AND exp.c_estado_id = est.c_estado_id(+)")
					.append(" AND cat.c_profesional_id = exp.c_profesional_id ")
					.append(" AND esp.c_espec_id (+) = exp.c_espec_id ")
					.append(" AND gra.c_grado_id (+) = exp.c_grado_id ")
					.append(" AND con.c_convocatoria_id = exp.c_convocatoria_id ")
					.append(" AND ger.c_gerencia_id = solic.c_gerencia_actual_id ")
					.append(" AND solic.c_usr_id = usu.c_usr_id ")
					.append(" AND solic.c_convocatoria_id = exp.c_convocatoria_id ")
					.append(" AND cat.c_modalidad_id = mod.c_modalidad_id ")
					.append(" AND EXP.C_SOLICITUD_ID = SOLIC.C_SOLICITUD_ID ");

			if ((solicitudesOT.getDApellido1() != null) && (!"".equals(solicitudesOT.getDApellido1())))
				consulta.append(
						" AND UPPER(translate(solic.d_apellidos,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%"
								+ solicitudesOT.getDApellido1() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
			if ((solicitudesOT.getDNombre() != null) && (!"".equals(solicitudesOT.getDNombre())))
				consulta.append(
						" AND UPPER(translate(solic.d_nombre,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%"
								+ solicitudesOT.getDNombre() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
			if ((solicitudesOT.getCDni() != null) && (!"".equals(solicitudesOT.getCDni())))
				consulta.append(" AND LPAD(solic.c_dni,10,'0') = LPAD('" + solicitudesOT.getCDni() + "',10,'0') ");
			if ((solicitudesOT.getBSexo() != null) && (!"".equals(solicitudesOT.getBSexo())))
				consulta.append(" AND solic.b_sexo = '" + solicitudesOT.getBSexo() + "' ");
			if ((solicitudesOT.getCDniReal() != null) && (!"".equals(solicitudesOT.getCDniReal())))
				consulta.append(
						" AND LPAD(solic.c_dni,10,'0') = LPAD('" + solicitudesOT.getCDniReal() + "',10,'0') ");
			if (solicitudesOT.getCProfesional_id() != 0L)
				consulta.append(" AND exp.c_profesional_id = '" + solicitudesOT.getCProfesional_id() + "' ");
			if (solicitudesOT.getCEspec_id() != 0L)
				consulta.append(" AND exp.c_espec_id = '" + solicitudesOT.getCEspec_id() + "' ");
			if (solicitudesOT.getCGrado_id() != 0L)
				consulta.append(" AND exp.c_grado_id = '" + solicitudesOT.getCGrado_id() + "' ");
			if ((solicitudesOT.getCProcedimientoId() != null) && (!"".equals(solicitudesOT.getCProcedimientoId())))
				consulta.append(" AND exp.c_proc_evaluacion_id = '" + solicitudesOT.getCProcedimientoId() + "' ");
			if (solicitudesOT.getCConvocatoriaId() != 0L)
				consulta.append(" AND exp.c_convocatoria_id = '" + solicitudesOT.getCConvocatoriaId() + "' ");
			if (solicitudesOT.getCGerencia_id() != 0L)
				consulta.append(" AND ger.c_gerencia_id = '" + solicitudesOT.getCGerencia_id() + "' ");
			if (solicitudesOT.getCExp_id() != 0L) {
				consulta.append(" AND exp.c_exp_id = '" + solicitudesOT.getCExp_id() + "' ");
			}
			if (!menu.equals("fechasExpediente")) {
				if ((solicitudesOT.getCFase() != null) && (!"".equals(solicitudesOT.getCFase()))) {
					if (Constantes.FASE_INICIACION.equals(solicitudesOT.getCFase())) {
						if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
							consulta.append(" AND exp.f_registro_solic is not null ");
						} else if ("P".equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_registro_solic is not null ");
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is null ");
						} else if (Constantes.ESTADO_AUTOEVALUADO_VALUE.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_registro_solic is not null ");
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is null ");
						} else if ("A".equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is not null ");
						} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is not null ");
						} else if (Constantes.ESTADO_DESESTIMADO_VALUE.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is not null ");
							consulta.append(" AND exp.f_respuestainconf_solic is null ");
						} else if ("D".equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_respuestainconf_solic is not null ");
						} else if (Constantes.ESTADO_DESISTIDO_VALUE.equals(solicitudesOT.getCEstado())){
							consulta.append(" AND solic.c_estado_id = 5 ");
						}

					}

					if (Constantes.FASE_MC.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.f_inicio_mc is not null ");
						consulta.append(" AND exp.f_fin_mc is not null ");

						if (!Constantes.FASE_MC.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND SYSDATE < exp.f_fin_mc ");
						}

					}

					if (Constantes.FASE_VALIDACION_LISTADOS.equals(solicitudesOT.getCFase())) {
						if ("A".equals(solicitudesOT.getCEstado())) {
							if ("P".equals(solicitudesOT.getCTipo()))
								consulta.append(" AND exp.F_ACEPTACIONCEIS is not null ");
							else
								consulta.append(" AND exp.F_ACEPTACIONCC is not null ");
						} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
							if ("P".equals(solicitudesOT.getCTipo()))
								consulta.append(" AND exp.f_negacion_mc is not null ");
							else {
								consulta.append(" AND exp.f_respuestainconf_mc is not null ");
							}
						}
					}

					if (Constantes.FASE_VALIDACION.equals(solicitudesOT.getCFase())) {
						//OCAP-886 y OCAP-1386
						consulta.append(" AND EXP.C_SOLICITUD_ID = SOLIC.C_SOLICITUD_ID ");
						//OCAP-1532: Nos piden revertir el cambio introducido en OCAP-886
						/*if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
							if (jcylUsuario.getUser().getD_gerencia() == null || !Constantes.COD_LDAP_GRS.equals(jcylUsuario.getUser().getD_gerencia().toUpperCase())) {
								consulta.append(" AND exp.c_proc_evaluacion_id not in ( 4,5)");
							}
						}*/
						
						if ((Constantes.ESTADO_PENDIENTE_VALUE.equals(solicitudesOT.getCEstado())) || (Constantes.ESTADO_AUTOEVALUADO_VALUE.equals(solicitudesOT.getCEstado()))) {
							consulta.append(" AND exp.f_fin_mc is not null ");
							if (Constantes.ESTADO_PENDIENTE_VALUE.equals(solicitudesOT.getCEstado())) {
								consulta.append(" AND (SYSDATE >= exp.f_fin_mc) ");
							}
							if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
								consulta.append(" AND ((exp.c_proc_evaluacion_id in ( 4,5)");
								consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
								consulta.append(" AND exp.f_negacion_mc is null )");
								consulta.append(" or ((exp.F_ACEPTACIONCEIS is not null ");
								consulta.append(" OR exp.f_negacion_mc is not null) ");
								consulta.append(" AND exp.f_respuestainconf_mc is null ");
								consulta.append(" AND exp.F_ACEPTACIONCC is null ))");
							} else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
								consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
								consulta.append(" AND exp.f_negacion_mc is null ");
							} else {
								consulta.append(" AND exp.F_ACEPTACIONCC is null ");
								consulta.append(" AND exp.f_respuestainconf_mc is null ");
							}
						} else {
							if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) && 
									(solicitudesOT.getCEstado() == null 
									|| (solicitudesOT.getCEstado() != null &&
										!Constantes.ESTADO_RENUNCIA_VALUE.equals(solicitudesOT.getCEstado()) &&
										!Constantes.ESTADO_VACIO_VALUE.equals(solicitudesOT.getCEstado()))
								)) {
								consulta.append(
										" AND (SYSDATE > (exp.F_INICIO_EVAL_MC+con.N_DIAS_INCONF_MC ) OR exp.c_proc_evaluacion_id in ( 4,5))");
							}

							if (Constantes.ESTADO_ACEPTADO_VALUE.equals(solicitudesOT.getCEstado())) {
								if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
									consulta.append(" AND exp.F_ACEPTACIONCEIS is not null ");
								else {
									consulta.append(" AND exp.F_ACEPTACIONCC is not null ");
								}
							} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
								if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
									consulta.append(" AND exp.f_negacion_mc is not null ");
								} else if ((Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol()))
										&& ("alega".equals(solicitudesOT.getCTipo()))) {
									consulta.append(" AND exp.f_negacion_mc is not null ");
									consulta.append(" AND SYSDATE > exp.F_INICIO_EVAL_MC ");
								} else {
									consulta.append(" AND exp.f_respuestainconf_mc is not null ");

									if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
										consulta.append(" AND exp.c_estado_id <> '" + 14 + "'");
								}
							} else if (Constantes.ESTADO_EXCLUIDO_ALEGA_VALUE.equals(solicitudesOT.getCEstado())) {
								consulta.append(" AND exp.c_estado_id = '" + 14 + "'");
							}else if(Constantes.ESTADO_RENUNCIA_VALUE.equals(solicitudesOT.getCEstado())){
								consulta.append(" AND exp.c_estado_id = '" + 15 + "' ");
							}else if (Constantes.ESTADO_CERO_MERITOS_VALUE.equals(solicitudesOT.getCEstado())){
								consulta.append(" AND ( ( SELECT COUNT(*) FROM OCAP_EXPEDIENTESMCS mcs WHERE mcs.C_EXP_ID = exp.C_EXP_ID ) = 0 ) ");
							}else if(Constantes.ESTADO_DESISTIDO_VALUE.equals(solicitudesOT.getCEstado())){
								consulta.append(" AND exp.c_estado_id = '" + 5 + "' ");
							}
							else if (Constantes.ESTADO_VACIO_VALUE.equals(solicitudesOT.getCEstado())){
								if(Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
									//OCAP-1342. El GRS tiene que ver tambien los desistidos
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,6,7,8) ");
								}else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
									//OCAP-1386 --> Mostrar al CEIS solo los expdtes en estado ACEPTADA, ACEPTADA MERITOS, EXCLUIDA MERITOS y ACEPTADO EDC
									consulta.append(" AND EXP.c_estado_id IN (3,9,10,12) ");
								}else if ((Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) || (Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol()))) {
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,5,6,7,8,15,20,21) ");
								}
								else if (!Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
									//OCAP-759 se ecluyen las RENUNCIAS(15) solo para los perfiles CEIS y CC
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,5,6,7,8) ");
								}else { 
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,5,6,7,8,15) ");
								}
							}
							
						}
					}
					if (Constantes.FASE_CA.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.c_estado_id = '" + 9 + "'");
						consulta.append(" AND exp.C_ITINERARIO_ID is not null ");
						if ((jcylUsuario.getUser().getRol() != null)
								&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST)))
							consulta.append(" AND exp.F_FIN_CA <= SYSDATE ");
						else if (Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) {
							consulta.append(" AND exp.F_INFORME_CC is not null ");
						}
					}

					if (Constantes.FASE_CA_INCL.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND SYSDATE > exp.F_FIN_EVAL_MC");
						if (solicitudesOT.getCEstado() != null) {
							if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
								consulta.append(" AND exp.f_aceptacioncc is not null ");
							}
							if ("I".equals(solicitudesOT.getCEstado()))
								consulta.append(" AND exp.f_respuestainconf_mc is not null ");
						} else {
							consulta.append(" AND (exp.f_aceptacioncc is not null ");
							consulta.append(" OR exp.f_respuestainconf_mc is not null) ");
						}
					}

					if (Constantes.FASE_CA_TERMINADA.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.c_estado_id = '" + 9 + "'");

						consulta.append(" AND exp.C_ITINERARIO_ID is not null ");
					}

					if (Constantes.FASE_CA_ESCANEADA.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.c_estado_id = '" + 9 + "'");

						consulta.append(" AND SYSDATE >= exp.f_fin_ca ");
					}

					if (Constantes.FASE_FIN.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.F_FIN_CC is not null ");
					}

					if (Constantes.FASE_RECONOCIMIENTO_CC.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.F_FIN_CC is not null ");
					}

					if (!Constantes.FASE_INICIACION.equals(solicitudesOT.getCFase())
							&& (solicitudesOT.getCEstado() == null || (solicitudesOT.getCEstado() != null
									&& !Constantes.ESTADO_RENUNCIA_VALUE.equals(solicitudesOT.getCEstado())
									&& !Constantes.ESTADO_DESISTIDO_VALUE.equals(solicitudesOT.getCEstado())
									&& !Constantes.ESTADO_VACIO_VALUE.equals(solicitudesOT.getCEstado())))) {
						
						//OCAP-1503. El CEIS solo puede ver los estados 3,9,10,12
						if (jcylUsuario.getUser().getRol() != null && jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS)) {
							consulta.append(" AND exp.c_estado_id IN (3, 9, 10, 12) ");
						}else {
							consulta.append(" AND exp.c_estado_id NOT IN (1, 2, 4, 5, 6, 7, 8, 15) ");
						}
						
					}
				}
				if (((Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol()))
						|| (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())))
						&& ((solicitudesOT.getCFase() == null) || ("".equals(solicitudesOT.getCFase())))) {
					if ((solicitudesOT.getCEstado() != null) && (!"".equals(solicitudesOT.getCEstado()))
							&& (!Constantes.FASE_MC.equals(solicitudesOT.getCEstado())))
						consulta.append(" AND exp.C_ESTADO_ID = ").append(solicitudesOT.getCEstado());
					else {
						consulta.append(" AND exp.c_estado_id NOT IN (1, 8) ");
					}
					if (reasociarGrs && Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
						consulta.append(
								" AND (select count (c_dni_real) from ocap_usuarios where c_dni_real = usu.c_dni_real) > 1 ");
					}

				}

			}

			if ((jcylUsuario.getUser().getRol() != null)
					&& ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))
							|| (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS)))
					&& (!menu.equals("aportacionEvidencias"))) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				consulta.append(" AND ger.c_gerencia_id = '" + (String) parametros.get("PARAM_GERENCIA") + "'");
				consulta.append(
						" AND ger.c_tipogerencia_id = '" + (String) parametros.get("PARAM_TIPO_GERENCIA") + "'");
				consulta.append(" AND ger.c_provincia_id = '" + (String) parametros.get("PARAM_PROV") + "'");
			}

			if (esPDF)
				if(solicitudesOT.getOrden() != null && !solicitudesOT.getOrden().isEmpty()){
					if(solicitudesOT.getOrden().equals(ORDEN_GERENCIA)){
						consulta.append(
								" ORDER BY ger.d_nombre ASC, exp.c_grado_id ASC, solic.d_apellidos ASC, solic.d_nombre ASC ");
					}else if(solicitudesOT.getOrden().equals(ORDEN_ALFABETICO)){
						consulta.append(
								" ORDER BY solic.d_apellidos ASC, solic.d_nombre ASC, ger.d_nombre ASC, exp.c_grado_id ASC ");
					}
				}else{
					consulta.append(
							" ORDER BY ger.d_nombre ASC, exp.c_grado_id ASC, solic.d_apellidos ASC, solic.d_nombre ASC ");
				}
			else if (esAgrupado)
				consulta.append(
						" ORDER BY ger.d_nombre ASC, exp.c_grado_id ASC, exp.c_profesional_id ASC, exp.c_espec_id ASC, solic.d_apellidos ASC, solic.d_nombre ASC");
			else {
				consulta.append(" ORDER BY solic.d_apellidos ASC , solic.d_nombre ASC, usu.c_usr_id, exp.c_grado_id ASC");
			}
			sentencia = con.prepareStatement(consulta.toString(), 1004, ResultSet.CONCUR_UPDATABLE);

			OCAPConfigApp.logger.info("Sentencia SQL:" + consulta);
			resultado = sentencia.executeQuery();

			if (inicio > 1) {
				resultado.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (resultado.next()) {
				solicitudesOT = new OCAPSolicitudesOT();
				solicitudesOT.setCUsr_id(resultado.getLong("c_usr_id"));
				solicitudesOT.setCGrado_id(resultado.getLong("c_grado_id"));
				solicitudesOT.setCConvocatoriaId(resultado.getLong("c_convocatoria_id"));
				solicitudesOT.setDConvocatoria(resultado.getString("d_convocatoria"));
				solicitudesOT.setDConvocatoriaNombreCorto(resultado.getString("d_convocatoria_corto"));
				solicitudesOT.setFConvocatoria(DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA,
						resultado.getDate("f_convocatoria")));
				solicitudesOT.setFListado(resultado.getDate("f_listado"));
				solicitudesOT.setCProfesional_id(resultado.getLong("c_profesional_id"));
				solicitudesOT.setCEspec_id(resultado.getLong("c_espec_id"));
				solicitudesOT.setDNombre(resultado.getString("d_nombre"));
				solicitudesOT.setDApellido1(resultado.getString("d_apellidos"));

				solicitudesOT.setCDni(resultado.getString("c_dni"));
				solicitudesOT.setCDniReal(resultado.getString("c_dni_real"));
				solicitudesOT.setBSexo(resultado.getString("b_sexo"));
				solicitudesOT.setFRegistro_solic(DateUtil.convertDateToString(resultado.getDate("f_registro_solic")));
				solicitudesOT.setDGrado_des(resultado.getString("d_descripcion"));
				solicitudesOT.setDProfesional_nombre(resultado.getString("d_nombre_cat"));
				solicitudesOT.setFAceptac_solic(resultado.getDate("F_ACEPTAC_SOLIC"));
				solicitudesOT.setFNegacion_solic(resultado.getDate("f_negacion_solic"));
				solicitudesOT.setFRespuesta_inconf_solic(resultado.getDate("F_RESPUESTAINCONF_SOLIC"));
				solicitudesOT.setFAceptacionceis(resultado.getDate("F_ACEPTACIONCEIS"));
				solicitudesOT.setFNegacion_mc(resultado.getDate("f_negacion_mc"));
				solicitudesOT.setFInicio_mc(resultado.getDate("f_inicio_mc"));
				solicitudesOT.setFFin_mc(resultado.getDate("f_fin_mc"));
				solicitudesOT.setFInconf_mc(resultado.getDate("f_inconf_mc"));
				solicitudesOT.setFInicio_eval_mc(resultado.getDate("f_inicio_eval_mc"));
				solicitudesOT.setFFin_eval_mc(resultado.getDate("f_fin_eval_mc"));
				solicitudesOT.setFFin_cc(resultado.getDate("f_fin_cc"));
				solicitudesOT.setBValidacionCC(resultado.getString("B_VALIDACIONCC"));
				solicitudesOT.setBInconf_antiguedad(resultado.getString("B_INCONF_ANTIGUEDAD"));
				solicitudesOT.setBInconf_plazaprop(resultado.getString("B_INCONF_PLAZAPROP"));
				solicitudesOT.setBPersonales(resultado.getString("B_PERSONALES"));
				solicitudesOT.setBOtrosCentros(resultado.getString("B_OTROSCENTROS"));
				solicitudesOT.setBPlazo(resultado.getString("B_PLAZO"));
				solicitudesOT.setBReconocimientoGrado(resultado.getString("B_RECONOCIMIENTO_GRADO"));
				if(resultado.getString("ORDENFIJO") != null){
					solicitudesOT.setOrden(resultado.getString("ORDENFIJO"));
				}

				solicitudesOT.setCSitAdministrativaAuxId(resultado.getString("c_sit_administrativa_id"));
				if ((resultado.getString("c_sit_administrativa_id") != null)
						&& ("1".equals(resultado.getString("c_sit_administrativa_id")))) {
					solicitudesOT.setDSitAdministrativaAux_nombre("Activo");
				} else {
					solicitudesOT.setDSitAdministrativaAux_nombre(resultado.getString("a_otrasitadministrativa"));
				}

				solicitudesOT.setCProcedimientoId(resultado.getString("c_proc_evaluacion_id"));
				long procedimiento = resultado.getLong("c_proc_evaluacion_id");
				if (1L == procedimiento)
					solicitudesOT.setDProcedimiento("General");
				if (2 == procedimiento)
					solicitudesOT.setDProcedimiento("Excedencia por cuidados de familiares");
				if (3 == procedimiento)
					solicitudesOT.setDProcedimiento("Liberado Sindical");
				if (4 == procedimiento)
					solicitudesOT.setDProcedimiento("Puesto de carÃ¡cter directivo");
				if (5 == procedimiento) {
					solicitudesOT.setDProcedimiento("Estructura administrativa y de gestión");
				}
				solicitudesOT.setCGerencia_id(resultado.getLong("c_gerencia_id"));
				solicitudesOT.setDGerencia_nombre(resultado.getString("d_nombre_ger"));
				solicitudesOT.setCodGerencia(resultado.getString("a_codldap").toUpperCase());
				solicitudesOT.setAPuesto(resultado.getString("a_puesto"));
				solicitudesOT.setFDesistidoMC(resultado.getTimestamp("f_desistido_mc"));

				if (resultado.getString("d_nombre_esp") != null)
					solicitudesOT.setDEspec_nombre(resultado.getString("d_nombre_esp"));
				else {
					solicitudesOT.setDEspec_nombre(" ");
				}

				solicitudesOT.setCExp_id(resultado.getLong("c_exp_id"));
				solicitudesOT.setCSolicitudId(resultado.getLong("c_solicitud_id"));
				solicitudesOT.setCEstado_id(resultado.getLong("c_estado_id"));
				solicitudesOT.setCEstado(resultado.getString("dEstado"));
				DecimalFormat formato = new DecimalFormat("000000");
				solicitudesOT.setCodigoId("EPR" + formato.format(solicitudesOT.getCExp_id()));
				solicitudesOT.setDModalidad(resultado.getString("dModalidadCat"));

				solicitudesOT.setFRecGrado(resultado.getDate("f_rec_grado"));
				solicitudesOT.setNumSolicitudes(resultado.getLong("numSolicitudes"));
				solicitudesOT.setFechaFinPgp(resultado.getDate("f_fin_pgp"));

				listado.add(solicitudesOT);

				if (++i == cuantos)
					break;
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudes: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw new SQLException(e.getMessage() + "<br>Error al buscar las solicitudes");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public ArrayList buscarSolicitudesCSV(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT, String estado,
			JCYLUsuario jcylUsuario) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesCSV: Inicio");
		PreparedStatement sentencia = null;
		Connection con = null;
		StringBuffer consulta = new StringBuffer();
		ResultSet resultado = null;
		ArrayList listado = null;
		try {
			con = JCYLGestionTransacciones.getConnection();
			consulta.append(
					"SELECT DISTINCT exp.c_exp_id, solic.d_nombre, solic.d_apellidos, solic.c_dni as c_dni_real, solic.c_dni, solic.b_sexo,")
					.append(" con.d_nombre as d_convocatoria, exp.c_grado_id, ")
					.append(" TO_DATE(EXP.F_REGISTRO_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_REGISTRO_SOLIC , ")
					.append(" TO_DATE(EXP.F_ACEPTAC_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_ACEPTAC_SOLIC , ")
					.append(" TO_DATE(EXP.f_negacion_solic,'DD/MM/RRRR hh24:mi:ss') as f_negacion_solic , ")
					.append(" TO_DATE(EXP.F_RESPUESTAINCONF_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_RESPUESTAINCONF_SOLIC, ")
					.append(" TO_DATE(con.f_publicacion, 'DD/MM/RRRR hh24:mi:ss') as f_convocatoria, ")
					.append(" TO_DATE(EXP.F_RENUNCIA,'DD/MM/RRRR hh24:mi:ss') as F_RENUNCIA , ")
					.append(" exp.b_inconf_antiguedad, exp.b_inconf_plazaprop, exp.b_plazo, ")
					.append(" gra.d_descripcion, cat.d_nombre d_nombre_cat, esp.d_nombre d_nombre_esp, exp.B_VALIDACIONCC, ")
					.append(" exp.n_aniosantiguedad, cen.d_nombre d_nombre_cen, ")
					.append(" exp.c_proc_evaluacion_id, decode(exp.c_sit_administrativa_id, 6, exp.a_otrasitadministrativa, sit.D_NOMBRE) as d_nombre_sit ,exp.c_sit_administrativa_id, nvl(exp.a_otrasitadministrativa,'Otro'), ")
					.append(" ger.d_nombre as d_nombre_ger, exp.b_reconocimiento_grado, exp.c_juridico, EXP.c_juridico_id, decode(EXP.c_juridico_id, 3 , nvl(exp.A_otrojuridico, 'Otro'), reg.D_NOMBRE) as d_nombre_reg , exp.A_OTROJURIDICO,\tmod.d_nombre as d_modalidad, exp.A_SERVICIO, exp.A_PUESTO,  ")
					.append(" est.d_nombre as d_nombre_estado, exp.c_estado_id");

			consulta.append(
					" FROM ocap_usuarios usu, ocap_solicitudes solic, ocap_expedientescarrera exp, ocap_grados gra, ocap_categ_profesionales cat, ocap_especialidades esp, ocap_convocatorias con, ocap_gerencias ger,  ")
					.append(" ocap_centrostrabajo cen, ");
					
					//OCAP-635
					if(solicitudesOT.getCEstadoHist() != null && !solicitudesOT.getCEstadoHist().equals("")) {
						consulta.append(" ocap_modalidades mod , ocap_sit_administrativa sit, ocap_reg_juridico reg,  ocap_estados est, ");
						consulta.append(" ocap_expcarr_estado_hist hist ");
					}else {
						consulta.append(" ocap_modalidades mod , ocap_sit_administrativa sit, ocap_reg_juridico reg,  ocap_estados est ");
					}

			consulta.append(" WHERE usu.c_usr_id = exp.c_usr_id ")
					.append(" AND cat.c_profesional_id = exp.c_profesional_id ")
					.append(" AND esp.c_espec_id (+) = exp.c_espec_id ")
					.append(" AND gra.c_grado_id (+) = exp.c_grado_id ")
					.append(" AND con.c_convocatoria_id = exp.c_convocatoria_id ")
					.append(" AND ger.c_gerencia_id = solic.c_gerencia_actual_id ")
					.append(" AND solic.c_usr_id = usu.c_usr_id ")
					.append(" AND solic.c_convocatoria_id = EXP.c_convocatoria_id ")
					.append(" AND cen.c_centrotrabajo_id = solic.C_CENTROTRAB_ACTUAL_ID ")
					.append(" AND usu.b_borrado = 'N' and exp.b_borrado = 'N' and gra.b_borrado = 'N' and cat.b_borrado = 'N' ")
					.append(" and con.b_borrado = 'N' and ger.b_borrado = 'N' and cen.b_borrado = 'N' and est.b_borrado = 'N' ")
					.append(" AND cat.c_modalidad_id = mod.c_modalidad_id  and sit.C_SIT_ADMINISTRATIVA_ID (+) = exp.c_sit_administrativa_id and reg.C_JURIDICO_ID (+) = exp.C_JURIDICO_ID  ")
					.append(" AND est.C_ESTADO_ID = exp.C_ESTADO_ID ");

			/* Estado: cualquiera menos "Cero Méritos" && "Profesional no ha accedido" */
			if ((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != Constantes.ESTADO_PROF_NO_ACCEDIDO)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != Constantes.ESTADO_CERO_MERITOS)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 19L)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 30L)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 31L)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 22L)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 23L)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 24L)) {
				consulta.append(" AND exp.c_estado_id = '" + solicitudesOT.getCEstado() + "' ");
			} 
			/* Estado: Profesional no ha accedido */
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == Constantes.ESTADO_PROF_NO_ACCEDIDO)) {
				consulta.append(" AND exp.F_FIN_MC IS NULL AND exp.F_INICIO_MC IS NULL AND exp.F_REGISTRO_OFICIAL IS NOT NULL AND exp.C_ESTADO_ID = 3 ");
			}
			/* Estado: Cero Méritos */
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == Constantes.ESTADO_CERO_MERITOS)) {
				consulta.append(" AND exp.F_FIN_MC IS NOT NULL AND exp.F_INICIO_MC IS NOT NULL AND exp.F_REGISTRO_OFICIAL IS NOT NULL AND exp.C_ESTADO_ID = 3 AND ( ( SELECT COUNT(*) FROM OCAP_EXPEDIENTESMCS mcs WHERE mcs.C_EXP_ID = exp.C_EXP_ID ) = 0 )");
			}
			/*Estado Proceso autoevaluación*/
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == 19L)) {
				consulta.append(
						" AND exp.F_FIN_CA > SYSDATE ");				
			}
			/*Estado Fin autoevaluación*/
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == 30L)) {
				consulta.append(
						" AND exp.F_FIN_CA < SYSDATE AND exp.C_EXP_ID in(select distinct(c_exp_id) from OCAP_EXPEDIENTESCAS where b_borrado = 'N') ");				
			}
			/*Estado Proceso evaluación*/
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == 31L)) {
				consulta.append(
						" AND exp.F_FIN_CA < SYSDATE ");
			}
			/*Estado Sin informe CTE*/
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == 22L)) {
				consulta.append(
						" AND exp.F_INFORME_CTE IS NULL"); 
			}
			/*Estado Sin evidencias profesionales*/
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == 23L)) {
				consulta.append(
						" AND exp.C_EXP_ID not in (select distinct(c_exp_id) from OCAP_DOC_FORMULARIO)");
			}
			/*Pendiente asignar evaluador*/
			else if((solicitudesOT.getCEstado() != null) && (solicitudesOT.getCEstado().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstado()) == 24L)) {
				consulta.append(
						" AND exp.C_COMPFQS_ID IS NULL");
			}

			/* Estado Histórico */
			else if ((solicitudesOT.getCEstadoHist() != null) && (solicitudesOT.getCEstadoHist().length() != 0)
					&& (Integer.parseInt(solicitudesOT.getCEstadoHist()) != 0)) {
				consulta.append(" AND hist.c_estado_id = '" + solicitudesOT.getCEstadoHist() + "' ")
						.append(" AND hist.C_EXP_ID     = exp.C_EXP_ID ");
			}
			if (solicitudesOT.getCGrado_id() != 0L)
				consulta.append(" AND exp.c_grado_id = '" + solicitudesOT.getCGrado_id() + "' ");
			if (solicitudesOT.getCProfesional_id() != 0L)
				consulta.append(" AND exp.c_profesional_id = '" + solicitudesOT.getCProfesional_id() + "' ");
			if (solicitudesOT.getCEspec_id() != 0L) {
				consulta.append(" AND exp.c_espec_id = '" + solicitudesOT.getCEspec_id() + "' ");
				consulta.append(" AND esp.b_borrado = 'N' ");
			}
			if (solicitudesOT.getCConvocatoriaId() != 0L) {
				consulta.append(" AND exp.c_convocatoria_id = '" + solicitudesOT.getCConvocatoriaId() + "' ");
			}
			if ((jcylUsuario.getUser().getRol() != null) && (!jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))) {
				if (solicitudesOT.getCGerenciaFiltro_id() != 0L)
					consulta.append(" AND ger.c_gerencia_id = '" + solicitudesOT.getCGerenciaFiltro_id() + "' ");
			}
			if ((solicitudesOT.getCJuridico() != null) && (!solicitudesOT.getCJuridico().equals("")))
				consulta.append(" AND exp.c_juridico_id = '" + solicitudesOT.getCJuridico() + "' ");
			if ((solicitudesOT.getCSitAdministrativaFiltro() != null)
					&& (!solicitudesOT.getCSitAdministrativaFiltro().equals("")))
				consulta.append(
						" AND exp.c_sit_administrativa_id = '" + solicitudesOT.getCSitAdministrativaFiltro() + "' ");
			if ((solicitudesOT.getCProcedimientoFiltro() != null)
					&& (!solicitudesOT.getCProcedimientoFiltro().equals("")))
				consulta.append(" AND exp.c_proc_evaluacion_id = '" + solicitudesOT.getCProcedimientoFiltro() + "' ");
			if ((solicitudesOT.getBSexo() != null) && (!"".equals(solicitudesOT.getBSexo()))) {
				consulta.append(" AND solic.b_sexo = '" + solicitudesOT.getBSexo() + "' ");
			}
			if ((jcylUsuario.getUser().getRol() != null) && (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				consulta.append(" AND ger.c_gerencia_id = '" + (String) parametros.get("PARAM_GERENCIA") + "'");
				consulta.append(
						" AND ger.c_tipogerencia_id = '" + (String) parametros.get("PARAM_TIPO_GERENCIA") + "'");
				consulta.append(" AND ger.c_provincia_id = '" + (String) parametros.get("PARAM_PROV") + "'");
			}

			consulta.append(" ORDER BY ger.d_nombre ASC, exp.c_grado_id ASC, solic.d_apellidos ASC, solic.d_nombre ASC ");

			sentencia = con.prepareStatement(consulta.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultado = sentencia.executeQuery();

			if (inicio > 1) {
				resultado.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (resultado.next()) {
				solicitudesOT = new OCAPSolicitudesOT();
				solicitudesOT.setDNombre(resultado.getString("d_nombre"));
				solicitudesOT.setDApellido1(resultado.getString("d_apellidos"));
				solicitudesOT.setCDni(resultado.getString("c_dni"));
				solicitudesOT.setCDniReal(resultado.getString("c_dni_real"));
				solicitudesOT.setBSexo(resultado.getString("b_sexo"));
				solicitudesOT.setDConvocatoria(resultado.getString("d_convocatoria"));
				solicitudesOT.setCEstado_id(resultado.getLong("c_estado_id"));
				solicitudesOT.setCExp_id(resultado.getLong("c_exp_id"));

				if (resultado.getDate("F_REGISTRO_SOLIC") != null)
					solicitudesOT.setFRegistro_solic(resultado.getDate("F_REGISTRO_SOLIC").toString());
				else {
					solicitudesOT.setFRegistro_solic(" ");
				}

				solicitudesOT.setFAceptac_solic(resultado.getDate("F_ACEPTAC_SOLIC"));
				solicitudesOT.setFNegacion_solic(resultado.getDate("f_negacion_solic"));
				solicitudesOT.setFRespuesta_inconf_solic(resultado.getDate("F_RESPUESTAINCONF_SOLIC"));

				solicitudesOT.setDGrado_des(resultado.getString("d_descripcion"));
				solicitudesOT.setDProfesional_nombre(resultado.getString("d_nombre_cat"));

				if (resultado.getString("d_modalidad") != null)
					solicitudesOT.setDModalidad(resultado.getString("d_modalidad"));
				else {
					solicitudesOT.setDModalidad(" ");
				}

				if (resultado.getString("d_nombre_esp") != null)
					solicitudesOT.setDEspec_nombre(resultado.getString("d_nombre_esp"));
				else {
					solicitudesOT.setDEspec_nombre(" ");
				}

				solicitudesOT.setBValidacionCC(resultado.getString("B_VALIDACIONCC"));
				solicitudesOT.setDRegimenJuridico(resultado.getString("d_nombre_reg"));
				solicitudesOT.setNAniosantiguedad(resultado.getLong("n_aniosantiguedad"));

				if (resultado.getString("d_nombre_cen") != null)
					solicitudesOT.setDCentrotrabajo_nombre(resultado.getString("d_nombre_cen"));
				else {
					solicitudesOT.setDCentrotrabajo_nombre(" ");
				}

				solicitudesOT.setCSitAdministrativaAuxId(resultado.getString("c_sit_administrativa_id"));
				solicitudesOT.setCSitAdministrativaFiltro(resultado.getString("d_nombre_sit"));

				solicitudesOT.setDSitAdministrativaAux_nombre(resultado.getString("d_nombre_sit"));
				solicitudesOT.setDGerencia_nombre(resultado.getString("d_nombre_ger"));
				solicitudesOT.setBReconocimientoGrado(resultado.getString("B_RECONOCIMIENTO_GRADO"));

				solicitudesOT.setCProcedimientoId(resultado.getString("c_proc_evaluacion_id"));
				long procedimiento = resultado.getLong("c_proc_evaluacion_id");
				if (1L == procedimiento)
					solicitudesOT.setDProcedimiento("General");
				if (2 == procedimiento)
					solicitudesOT.setDProcedimiento("Excedencia por cuidados de familiares");
				if (3 == procedimiento)
					solicitudesOT.setDProcedimiento("Liberado Sindical");
				if (4 == procedimiento)
					solicitudesOT.setDProcedimiento("Puesto de carÃ¡cter directivo");
				if (5 == procedimiento) {
					solicitudesOT.setDProcedimiento("Estructura administrativa y de gestión");
				}
				solicitudesOT.setFConvocatoria(DateUtil.convertDateToStringLarga(Constantes.FECHA_LETRA,
						resultado.getDate("f_convocatoria")));
				solicitudesOT.setAServicio(resultado.getString("A_SERVICIO"));
				solicitudesOT.setAPuesto(resultado.getString("A_PUESTO"));
				solicitudesOT.setBInconf_antiguedad(resultado.getString("B_INCONF_ANTIGUEDAD"));
				solicitudesOT.setBInconf_plazaprop(resultado.getString("B_INCONF_PLAZAPROP"));
				solicitudesOT.setBPlazo(resultado.getString("B_PLAZO"));
				solicitudesOT.setCEstado(resultado.getString("d_nombre_estado"));

				if (resultado.getDate("F_RENUNCIA") != null){
					SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					solicitudesOT.setFRenuncia(df.format(resultado.getDate("F_RENUNCIA")));
				}else {
					solicitudesOT.setFRenuncia(" ");
				}
				
				listado.add(solicitudesOT);

				if (++i == cuantos)
					break;
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesCSV: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw new SQLException(e.getMessage() + "<br>Error al buscar las solicitudes");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public int contarSolicitudes(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario, String menu, boolean reasociarGrs)
			throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Inicio");

		PreparedStatement sentencia = null;
		Connection con = null;

		StringBuffer consulta = new StringBuffer();

		ResultSet resultado = null;
		int numSolicitudes = 0;
		try {
			con = JCYLGestionTransacciones.getConnection();

			consulta = new StringBuffer();
			consulta.append("SELECT COUNT(*) Numero from ( ");

			consulta.append("SELECT usu.c_usr_id, exp.c_exp_id ");
			consulta.append(
					" FROM ocap_usuarios usu, ocap_solicitudes solic, ocap_expedientescarrera exp, ocap_estados est, ocap_grados gra, ocap_categ_profesionales cat, ocap_convocatorias con, ocap_especialidades esp ");
			consulta.append(", ocap_gerencias ger, ocap_modalidades mod ");

			consulta.append(" WHERE usu.c_usr_id = exp.c_usr_id").append(" AND exp.c_estado_id = est.c_estado_id(+)")
					.append(" AND cat.c_profesional_id = exp.c_profesional_id ")
					.append(" AND esp.c_espec_id (+) = exp.c_espec_id ")
					.append(" AND gra.c_grado_id (+) = exp.c_grado_id ")
					.append(" AND con.c_convocatoria_id = exp.c_convocatoria_id ")
					.append(" AND ger.c_gerencia_id = solic.C_GERENCIA_ACTUAL_ID ")
					.append(" AND solic.c_usr_id = usu.c_usr_id ")
					.append(" AND solic.c_convocatoria_id = exp.c_convocatoria_id ")
					.append(" AND cat.c_modalidad_id = mod.c_modalidad_id ")
					.append(" AND EXP.C_SOLICITUD_ID = SOLIC.C_SOLICITUD_ID ");

			if ((solicitudesOT.getDApellido1() != null) && (!"".equals(solicitudesOT.getDApellido1())))
				consulta.append(
						" AND UPPER(translate(solic.d_apellidos,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%"
								+ solicitudesOT.getDApellido1() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
			if ((solicitudesOT.getDNombre() != null) && (!"".equals(solicitudesOT.getDNombre())))
				consulta.append(
						" AND UPPER(translate(solic.d_nombre,'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) LIKE UPPER(translate('%"
								+ solicitudesOT.getDNombre() + "%','áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) ");
			if ((solicitudesOT.getCDni() != null) && (!"".equals(solicitudesOT.getCDni())))
				consulta.append(" AND LPAD(solic.c_dni,10,'0') = LPAD('" + solicitudesOT.getCDni() + "',10,'0') ");
			if ((solicitudesOT.getBSexo() != null) && (!"".equals(solicitudesOT.getBSexo())))
				consulta.append(" AND solic.b_sexo = '" + solicitudesOT.getBSexo() + "' ");
			if ((solicitudesOT.getCDniReal() != null) && (!"".equals(solicitudesOT.getCDniReal())))
				consulta.append(
						" AND LPAD(solic.c_dni,10,'0') = LPAD('" + solicitudesOT.getCDniReal() + "',10,'0') ");
			if (solicitudesOT.getCProfesional_id() != 0L)
				consulta.append(" AND exp.c_profesional_id = '" + solicitudesOT.getCProfesional_id() + "' ");
			if (solicitudesOT.getCEspec_id() != 0L)
				consulta.append(" AND exp.c_espec_id = '" + solicitudesOT.getCEspec_id() + "' ");
			if (solicitudesOT.getCGrado_id() != 0L)
				consulta.append(" AND exp.c_grado_id = '" + solicitudesOT.getCGrado_id() + "' ");
			if ((solicitudesOT.getCProcedimientoId() != null) && (!"".equals(solicitudesOT.getCProcedimientoId())))
				consulta.append(" AND exp.c_proc_evaluacion_id = '" + solicitudesOT.getCProcedimientoId() + "' ");
			if (solicitudesOT.getCConvocatoriaId() != 0L)
				consulta.append(" AND exp.c_convocatoria_id = '" + solicitudesOT.getCConvocatoriaId() + "' ");
			if (solicitudesOT.getCGerencia_id() != 0L)
				consulta.append(" AND ger.c_gerencia_id = '" + solicitudesOT.getCGerencia_id() + "' ");
			if (solicitudesOT.getCExp_id() != 0L) {
				consulta.append(" AND exp.c_exp_id = '" + solicitudesOT.getCExp_id() + "' ");
			}
			if (!menu.equals("fechasExpediente")) {
				if ((solicitudesOT.getCFase() != null) && (!"".equals(solicitudesOT.getCFase()))) {
					if (Constantes.FASE_INICIACION.equals(solicitudesOT.getCFase())) {
						if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
							consulta.append(" AND exp.f_registro_solic is not null ");
						} else if ("P".equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_registro_solic is not null ");
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is null ");
						} else if (Constantes.ESTADO_AUTOEVALUADO_VALUE.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_registro_solic is not null ");
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is null ");
						} else if ("A".equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is not null ");
						} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is not null ");
						} else if (Constantes.ESTADO_DESESTIMADO_VALUE.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_negacion_solic is not null ");
							consulta.append(" AND exp.f_respuestainconf_solic is null ");
						} else if ("D".equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND exp.f_aceptac_solic is null ");
							consulta.append(" AND exp.f_respuestainconf_solic is not null ");
						} else if (Constantes.ESTADO_DESISTIDO_VALUE.equals(solicitudesOT.getCEstado())){
							consulta.append(" AND solic.c_estado_id = 5 ");
						}

					}

					if (Constantes.FASE_MC.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.f_inicio_mc is not null ");
						consulta.append(" AND exp.f_fin_mc is not null ");

						if (!Constantes.FASE_MC.equals(solicitudesOT.getCEstado())) {
							consulta.append(" AND SYSDATE < exp.f_fin_mc ");
						}
					}

					if (Constantes.FASE_VALIDACION_LISTADOS.equals(solicitudesOT.getCFase())) {
						if ("A".equals(solicitudesOT.getCEstado())) {
							if ("P".equals(solicitudesOT.getCTipo()))
								consulta.append(" AND exp.F_ACEPTACIONCEIS is not null ");
							else
								consulta.append(" AND exp.F_ACEPTACIONCC is not null ");
						} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
							if ("P".equals(solicitudesOT.getCTipo()))
								consulta.append(" AND exp.f_negacion_mc is not null ");
							else {
								consulta.append(" AND exp.f_respuestainconf_mc is not null ");
							}
						}
					}
					if (Constantes.FASE_VALIDACION.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND EXP.C_SOLICITUD_ID = SOLIC.C_SOLICITUD_ID ");
						//OCAP-886 y OCAP-1386
						//OCAP-1532: Nos piden revertir el cambio introducido en OCAP-886
						/*if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
							if (jcylUsuario.getUser().getD_gerencia() == null || !Constantes.COD_LDAP_GRS.equals(jcylUsuario.getUser().getD_gerencia().toUpperCase())) {
								consulta.append(" AND exp.c_proc_evaluacion_id not in ( 4,5)");
							}
						}*/
						
						if (("P".equals(solicitudesOT.getCEstado())) || (Constantes.ESTADO_AUTOEVALUADO_VALUE.equals(solicitudesOT.getCEstado()))) {
							consulta.append(" AND exp.f_fin_mc is not null ");
							if ("P".equals(solicitudesOT.getCEstado())) {
								consulta.append(" AND (SYSDATE >= exp.f_fin_mc) ");
							}

							if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
								consulta.append(" AND ((exp.c_proc_evaluacion_id in ( 4,5)");
								consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
								consulta.append(" AND exp.f_negacion_mc is null )");
								consulta.append(" or ((exp.F_ACEPTACIONCEIS is not null ");
								consulta.append(" OR exp.f_negacion_mc is not null) ");
								consulta.append(" AND exp.f_respuestainconf_mc is null ");
								consulta.append(" AND exp.F_ACEPTACIONCC is null ))");
							} else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
								consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
								consulta.append(" AND exp.f_negacion_mc is null ");
							} else {
								consulta.append(" AND exp.F_ACEPTACIONCC is null ");
								consulta.append(" AND exp.f_respuestainconf_mc is null ");
							}
						} else {
							if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()) && 
									(solicitudesOT.getCEstado() == null 
									|| (solicitudesOT.getCEstado() != null &&
										!Constantes.ESTADO_RENUNCIA_VALUE.equals(solicitudesOT.getCEstado()) &&
										!Constantes.ESTADO_VACIO_VALUE.equals(solicitudesOT.getCEstado()))
								)) {
								consulta.append(
										" AND (SYSDATE > (exp.F_INICIO_EVAL_MC+con.N_DIAS_INCONF_MC ) OR exp.c_proc_evaluacion_id in ( 4,5))");
							}

							if (Constantes.ESTADO_ACEPTADO_VALUE.equals(solicitudesOT.getCEstado())) {
								if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol()))
									consulta.append(" AND exp.F_ACEPTACIONCEIS is not null ");
								else {
									consulta.append(" AND exp.F_ACEPTACIONCC is not null ");
								}

							} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
								if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
									consulta.append(" AND exp.f_negacion_mc is not null ");
								} else if ((Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol()))
										&& ("alega".equals(solicitudesOT.getCTipo()))) {
									consulta.append(" AND exp.f_negacion_mc is not null ");
									consulta.append(" AND SYSDATE > exp.F_INICIO_EVAL_MC ");
								} else {
									consulta.append(" AND exp.f_respuestainconf_mc is not null ");

									if (Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol()))
										consulta.append(" AND exp.c_estado_id <> '" + 14 + "'");
								}
							} else if (Constantes.ESTADO_EXCLUIDO_ALEGA_VALUE.equals(solicitudesOT.getCEstado())) {
								consulta.append(" AND exp.c_estado_id = '" + 14 + "'");
							}else if(Constantes.ESTADO_RENUNCIA_VALUE.equals(solicitudesOT.getCEstado())){
								consulta.append(" AND exp.c_estado_id = '" + 15 + "' ");
							}else if (Constantes.ESTADO_CERO_MERITOS_VALUE.equals(solicitudesOT.getCEstado())){
								consulta.append(" AND ( ( SELECT COUNT(*) FROM OCAP_EXPEDIENTESMCS mcs WHERE mcs.C_EXP_ID = exp.C_EXP_ID ) = 0 ) ");
							}else if(Constantes.ESTADO_DESISTIDO_VALUE.equals(solicitudesOT.getCEstado())){
								consulta.append(" AND exp.c_estado_id = '" + 5 + "' ");
							}else if (Constantes.ESTADO_VACIO_VALUE.equals(solicitudesOT.getCEstado())){
								if(Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
									//OCAP-1342. El GRS tiene que ver tambien los desistidos
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,6,7,8) ");
								}else if (Constantes.OCAP_CEIS.equals(jcylUsuario.getUser().getRol())) {
									//OCAP-1386 --> Mostrar al CEIS solo los expdtes en estado ACEPTADA, ACEPTADA MERITOS y EXCLUIDA MERITOS
									consulta.append(" AND EXP.c_estado_id IN (3,9,10,12) ");
								}else if ((Constantes.OCAP_EVAL.equals(jcylUsuario.getUser().getRol())) || (Constantes.OCAP_CTE.equals(jcylUsuario.getUser().getRol()))) {
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,5,6,7,8,15,20,21) ");
								}
								else if (!Constantes.OCAP_CC.equals(jcylUsuario.getUser().getRol())) {
									//OCAP-759 se ecluyen las RENUNCIAS(15) solo para los perfiles CEIS y CC
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,5,6,7,8) ");
								}else { 
									consulta.append(" AND EXP.c_estado_id NOT IN (1,2,4,5,6,7,8,15) ");
								}
							}
						}
					}
					if (Constantes.FASE_CA.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.c_estado_id = '" + 9 + "'");
						consulta.append(" AND exp.C_ITINERARIO_ID is not null ");

						if ((jcylUsuario.getUser().getRol() != null)
								&& (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_FQS_GEST)))
							consulta.append(" AND exp.F_FIN_CA <= SYSDATE ");
						else if (Constantes.OCAP_DIRECCION.equals(jcylUsuario.getUser().getRol())) {
							consulta.append(" AND exp.F_INFORME_CC is not null ");
						}
					}

					if (Constantes.FASE_CA_INCL.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND SYSDATE > exp.F_FIN_EVAL_MC");
						if (solicitudesOT.getCEstado() != null) {
							if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
								consulta.append(" AND exp.f_aceptacioncc is not null ");
							}
							if ("I".equals(solicitudesOT.getCEstado()))
								consulta.append(" AND exp.f_respuestainconf_mc is not null ");
						} else {
							consulta.append(" AND (exp.f_aceptacioncc is not null ");
							consulta.append(" OR exp.f_respuestainconf_mc is not null) ");
						}
					}

					if (Constantes.FASE_CA_TERMINADA.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.c_estado_id = '" + 9 + "'");

						consulta.append(" AND exp.C_ITINERARIO_ID is not null ");
					}
					if (Constantes.FASE_CA_ESCANEADA.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.c_estado_id = '" + 9 + "'");

						consulta.append(" AND SYSDATE >= exp.f_fin_ca ");
					}

					if (Constantes.FASE_FIN.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.F_FIN_CC is not null ");
					}
					if (Constantes.FASE_RECONOCIMIENTO_CC.equals(solicitudesOT.getCFase())) {
						consulta.append(" AND exp.F_FIN_CC is not null ");
					}

					if (!Constantes.FASE_INICIACION.equals(solicitudesOT.getCFase()) && 
							(solicitudesOT.getCEstado() == null 
								|| (solicitudesOT.getCEstado() != null &&
									!Constantes.ESTADO_RENUNCIA_VALUE.equals(solicitudesOT.getCEstado()) &&
									!Constantes.ESTADO_DESISTIDO_VALUE.equals(solicitudesOT.getCEstado()) &&
									!Constantes.ESTADO_VACIO_VALUE.equals(solicitudesOT.getCEstado()))
							) ){
						consulta.append(" AND exp.c_estado_id NOT IN (1, 2, 4, 5, 6, 7, 8, 15) ");
					}
				}
				if (((Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol()))
						|| (Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())))
						&& ((solicitudesOT.getCFase() == null) || ("".equals(solicitudesOT.getCFase())))) {
					if ((solicitudesOT.getCEstado() != null) && (!"".equals(solicitudesOT.getCEstado()))
							&& (!Constantes.FASE_MC.equals(solicitudesOT.getCEstado())))
						consulta.append(" AND exp.C_ESTADO_ID = ").append(solicitudesOT.getCEstado());
					else {
						consulta.append(" AND exp.c_estado_id NOT IN (1, 8) ");
					}
					if (reasociarGrs && Constantes.OCAP_GRS.equals(jcylUsuario.getUser().getRol())) {
						consulta.append(
								" AND (select count (c_dni_real) from ocap_usuarios where c_dni_real = usu.c_dni_real) > 1 ");
					}

				}

			}

			if ((jcylUsuario.getUser().getRol() != null)
					&& ((jcylUsuario.getUser().getRol().equals(Constantes.OCAP_PUNTO_GESTION_PERIFE))
							|| (jcylUsuario.getUser().getRol().equals(Constantes.OCAP_CEIS)))
					&& (!menu.equals("aportacionEvidencias"))) {
				Map parametros = jcylUsuario.getParametrosUsuario();
				consulta.append(" AND ger.c_gerencia_id = '" + (String) parametros.get("PARAM_GERENCIA") + "'");
				consulta.append(
						" AND ger.c_tipogerencia_id = '" + (String) parametros.get("PARAM_TIPO_GERENCIA") + "'");
				consulta.append(" AND ger.c_provincia_id = '" + (String) parametros.get("PARAM_PROV") + "'");
			}

			consulta.append(
					" ORDER BY exp.c_grado_id ASC, exp.c_profesional_id ASC, exp.c_espec_id ASC, usu.c_dni ASC");

			consulta.append(" ) ");

			sentencia = con.prepareStatement(consulta.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				numSolicitudes = resultado.getInt("Numero");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: ERROR: " + e.getMessage());
			throw new SQLException(e.getMessage() + "<br>Error al contar las solicitudes");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return numSolicitudes;
	}

	public ArrayList buscarSolicitudesGrs(int inicio, int cuantos, OCAPSolicitudesOT solicitudesOT,
			JCYLUsuario jcylUsuario, String opcion) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesGrs: Inicio");

		PreparedStatement sentencia = null;
		Connection con = null;

		StringBuffer consulta = new StringBuffer();

		ResultSet resultado = null;
		ArrayList listado = null;
		try {
			con = JCYLGestionTransacciones.getConnection();
			consulta.append(
					"SELECT solic.d_nombre as d_nombre_usu, c.d_nombre_corto as nombre_convocatoria, solic.d_apellidos, solic.c_dni as C_DNI_REAL, ger.d_nombre as d_nombre_ger, usu.c_gerencia_id, exp.c_exp_id,exp.c_convocatoria_id,exp.b_validacioncc,")
					.append(" TO_DATE(exp.f_registro_solic, 'DD/MM/RRRR hh24:mi:ss') AS f_registro_solic,")
					.append(" TO_DATE(EXP.F_ACEPTAC_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_ACEPTAC_SOLIC, ")
					.append(" TO_DATE(EXP.F_NEGACION_SOLIC,'DD/MM/RRRR hh24:mi:ss') as F_NEGACION_SOLIC, ")
					.append(" TO_DATE(exp.F_ACEPTACIONCEIS, 'DD/MM/RRRR hh24:mi:ss') AS F_ACEPTACIONCEIS,")
					.append(" TO_DATE(EXP.f_respuestainconf_mc,'DD/MM/RRRR hh24:mi:ss') as f_respuestainconf_mc, ")
					.append(" TO_DATE(EXP.f_negacion_mc,'DD/MM/RRRR hh24:mi:ss') as f_negacion_mc, ")
					.append(" TO_DATE(EXP.f_fin_mc,'DD/MM/RRRR hh24:mi:ss') as f_fin_mc, ")
					.append(" TO_DATE(EXP.f_inconf_mc,'DD/MM/RRRR hh24:mi:ss') as f_inconf_mc, ")
					.append(" exp.C_PROFESIONAL_ID, exp.C_ESPEC_ID,");

			consulta.append(" gra.d_descripcion ");
			consulta.append(
					" FROM ocap_solicitudes solic, ocap_usuarios usu, ocap_expedientescarrera exp, ocap_convocatorias c, ocap_gerencias ger, ocap_grados gra ");
			consulta.append(" WHERE usu.c_usr_id = exp.c_usr_id and exp.c_convocatoria_id =  c.c_convocatoria_id")
			.append(" AND solic.c_usr_id = usu.c_usr_id ")
			.append(" AND solic.c_convocatoria_id = exp.c_convocatoria_id ")
			.append(" AND EXP.c_solicitud_id = solic.c_solicitud_id ")
			.append(" AND solic.c_gerencia_actual_id = ger.c_gerencia_id ")
					.append(" AND gra.c_grado_id (+) = exp.c_grado_id ");

			if (solicitudesOT.getCGerencia_id() != 0L)
				consulta.append(" AND solic.c_gerencia_actual_id = '" + solicitudesOT.getCGerencia_id() + "' ");
			if (solicitudesOT.getCGrado_id() != 0L)
				consulta.append(" AND exp.c_grado_id = '" + solicitudesOT.getCGrado_id() + "' ");
			if (solicitudesOT.getCConvocatoriaId() != 0L) {
				consulta.append(" AND exp.c_convocatoria_id = '" + solicitudesOT.getCConvocatoriaId() + "' ");
			}

			if (Constantes.FASE_INICIACION.equals(solicitudesOT.getCFase())) {
				if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
					consulta.append(" AND exp.f_registro_solic is not null ");
				} else if ("P".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_registro_solic is not null ");
					consulta.append(" AND exp.f_aceptac_solic is null ");
					consulta.append(" AND exp.f_negacion_solic is null ");
				} else if ("A".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_aceptac_solic is not null ");
				} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_aceptac_solic is null ");
					consulta.append(" AND exp.f_negacion_solic is not null ");
				}
			} else if (Constantes.FASE_MC.equals(solicitudesOT.getCFase())) {
				if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
					consulta.append(" AND exp.f_inicio_mc is not null ");
					consulta.append(" AND exp.f_fin_mc is not null ");
				} else if (Constantes.FASE_AUTOEVALUACION_VALUE.equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_inicio_mc is not null ");
					consulta.append(" AND exp.f_fin_mc is not null ");
					consulta.append(" AND SYSDATE < exp.f_fin_mc ");
				} else if ("P".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
					consulta.append(" AND exp.f_negacion_mc is null ");
					consulta.append(" AND exp.f_fin_mc is not null ");
					consulta.append(" AND (SYSDATE >= exp.f_fin_mc OR exp.f_inicio_eval_mc is not null) ");
				} else if ("A".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is not null ");
				} else if (Constantes.ESTADO_DESESTIMADO_VALUE.equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
					consulta.append(" AND exp.f_negacion_mc is not null ");
					consulta.append(" AND exp.f_respuestainconf_mc is null ");
				} else if ("D".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
					consulta.append(" AND exp.f_respuestainconf_mc is not null ");
				}
			} else if (Constantes.FASE_FIN.equals(solicitudesOT.getCFase())) {
				consulta.append(" AND exp.b_reconocimiento_grado = 'Y'");
				consulta.append(" AND exp.C_ESTADO_ID = "+Constantes.ESTADO_ACEPTADA_ASISTENCIAL);
				if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) {
					consulta.append(" AND c.B_BORRADO = 'N' ");
				}
			} else if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
				consulta.append(" AND exp.f_inicio_mc is not null ");
				consulta.append(" AND exp.f_fin_mc is not null ");
				consulta.append(" AND SYSDATE >= exp.f_fin_mc ");
			} else if ("A".equals(solicitudesOT.getCEstado())) {
				consulta.append(" AND exp.b_validacionCC = 'Y'");
			} else if ("D".equals(solicitudesOT.getCEstado())) {
				consulta.append(" AND exp.b_validacionCC = 'N'");
			} else if ("P".equals(solicitudesOT.getCEstado())) {
				consulta.append(" AND exp.f_inicio_mc is not null ");
				consulta.append(" AND exp.f_fin_mc is not null ");
				consulta.append(" AND SYSDATE >= exp.f_fin_mc ");
				consulta.append(" AND exp.b_validacioncc IS NULL ");
			}

			if ((opcion != null) && (opcion.equals("solic")))
				consulta.append(
						" ORDER BY solic.c_gerencia_actual_id ASC, exp.c_grado_id ASC, exp.C_PROFESIONAL_ID ASC, exp.C_ESPEC_ID ASC, solic.d_apellidos ASC");
			else {
				consulta.append(" ORDER BY solic.c_gerencia_actual_id ASC, exp.c_grado_id ASC, solic.d_apellidos ASC");
			}

			sentencia = con.prepareStatement(consulta.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultado = sentencia.executeQuery();

			if (inicio > 1) {
				resultado.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (resultado.next()) {
				solicitudesOT = new OCAPSolicitudesOT();
				solicitudesOT.setDNombre(resultado.getString("d_nombre_usu"));
				solicitudesOT.setDApellido1(resultado.getString("d_apellidos"));
				solicitudesOT.setCDniReal(resultado.getString("c_dni_real"));
				solicitudesOT.setFRegistro_solic(DateUtil.convertDateToString(resultado.getDate("f_registro_solic")));
				solicitudesOT.setFAceptac_solic(resultado.getDate("F_ACEPTAC_SOLIC"));
				solicitudesOT.setFNegacion_solic(resultado.getDate("F_NEGACION_SOLIC"));
				solicitudesOT.setDGerencia_nombre(resultado.getString("d_nombre_ger"));
				solicitudesOT.setDGrado_des(resultado.getString("d_descripcion"));
				solicitudesOT.setFAceptacionceis(resultado.getDate("F_ACEPTACIONCEIS"));
				solicitudesOT.setFRespuestaInconf_MC(resultado.getDate("F_RESPUESTAINCONF_MC"));
				solicitudesOT.setFNegacion_mc(resultado.getDate("F_NEGACION_MC"));
				solicitudesOT.setFFin_mc(resultado.getDate("F_FIN_MC"));
				solicitudesOT.setFInconf_mc(resultado.getDate("F_INCONF_MC"));
				solicitudesOT.setCExp_id(resultado.getLong("c_exp_id"));
				solicitudesOT.setCConvocatoriaId(resultado.getLong("c_convocatoria_id"));
				solicitudesOT.setBValidacionCC(resultado.getString("b_validacioncc"));
				solicitudesOT.setCProfesional_id(resultado.getLong("C_PROFESIONAL_ID"));
				solicitudesOT.setCEspec_id(resultado.getLong("C_ESPEC_ID"));
				solicitudesOT.setDConvocatoriaNombreCorto(resultado.getString("nombre_convocatoria"));

				listado.add(solicitudesOT);

				if (++i == cuantos)
					break;
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesGrs: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesGrs: ERROR: " + e.getMessage());
			throw new SQLException(e.getMessage() + "<br>Error al buscar las solicitudes");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public int contarSolicitudesGrs(OCAPSolicitudesOT solicitudesOT, JCYLUsuario jcylUsuario) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Inicio");

		PreparedStatement sentencia = null;
		Connection con = null;

		StringBuffer consulta = new StringBuffer();

		ResultSet resultado = null;
		int numSolicitudes = 0;
		try {
			con = JCYLGestionTransacciones.getConnection();

			consulta = new StringBuffer();
			consulta.append("SELECT COUNT(*) Numero from ( ");
			consulta.append("SELECT usu.c_usr_id");
			consulta.append(
					" FROM ocap_usuarios usu, ocap_expedientescarrera exp, ocap_gerencias ger,  ocap_solicitudes solic, ocap_convocatorias c, ocap_grados gra ");
			consulta.append(" WHERE usu.c_usr_id = exp.c_usr_id")
					.append(" AND gra.c_grado_id (+) = exp.c_grado_id AND EXP.c_convocatoria_id = c.c_convocatoria_id " + 
							" AND EXP.c_solicitud_id = solic.c_solicitud_id " +
							"               AND solic.c_gerencia_actual_id = ger.c_gerencia_id  AND solic.c_usr_id = usu.c_usr_id " + 
							"         AND solic.c_convocatoria_id = EXP.c_convocatoria_id ");

			if (solicitudesOT.getCGerencia_id() != 0L)
				consulta.append(" AND solic.c_gerencia_actual_id = '" + solicitudesOT.getCGerencia_id() + "' ");
			if (solicitudesOT.getCGrado_id() != 0L)
				consulta.append(" AND exp.c_grado_id = '" + solicitudesOT.getCGrado_id() + "' ");
			if (solicitudesOT.getCConvocatoriaId() != 0L) {
				consulta.append(" AND exp.c_convocatoria_id = '" + solicitudesOT.getCConvocatoriaId() + "' ");
			}

			if (Constantes.FASE_INICIACION.equals(solicitudesOT.getCFase())) {
				if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
					consulta.append(" AND exp.f_registro_solic is not null ");
				} else if ("P".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_registro_solic is not null ");
					consulta.append(" AND exp.f_aceptac_solic is null ");
					consulta.append(" AND exp.f_negacion_solic is null ");
				} else if ("A".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_aceptac_solic is not null ");
				} else if (Constantes.ESTADO_EXCLUIDO_VALUE.equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_aceptac_solic is null ");
					consulta.append(" AND exp.f_negacion_solic is not null ");
				}
			} else if (Constantes.FASE_MC.equals(solicitudesOT.getCFase())) {
				if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
					consulta.append(" AND exp.f_inicio_mc is not null ");
					consulta.append(" AND exp.f_fin_mc is not null ");
				} else if (Constantes.FASE_AUTOEVALUACION_VALUE.equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.f_inicio_mc is not null ");
					consulta.append(" AND exp.f_fin_mc is not null ");
					consulta.append(" AND SYSDATE < exp.f_fin_mc ");
				} else if ("P".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
					consulta.append(" AND exp.f_negacion_mc is null ");
					consulta.append(" AND exp.f_fin_mc is not null ");
					consulta.append(" AND (SYSDATE >= exp.f_fin_mc OR exp.f_inicio_eval_mc is not null) ");
				} else if ("A".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is not null ");
				} else if (Constantes.ESTADO_DESESTIMADO_VALUE.equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
					consulta.append(" AND exp.f_negacion_mc is not null ");
					consulta.append(" AND exp.f_respuestainconf_mc is null ");
				} else if ("D".equals(solicitudesOT.getCEstado())) {
					consulta.append(" AND exp.F_ACEPTACIONCEIS is null ");
					consulta.append(" AND exp.f_respuestainconf_mc is not null ");
				}
			} else if (Constantes.FASE_FIN.equals(solicitudesOT.getCFase())) {
				consulta.append(" AND exp.b_reconocimiento_grado = 'Y'");
				consulta.append(" AND exp.C_ESTADO_ID = "+Constantes.ESTADO_ACEPTADA_ASISTENCIAL);
				if (Constantes.OCAP_PUNTO_GESTION_PERIFE.equals(jcylUsuario.getUser().getRol())) {
					consulta.append(" AND c.B_BORRADO = 'N' ");
				}
			} else if ((solicitudesOT.getCEstado() == null) || ("".equals(solicitudesOT.getCEstado()))) {
				consulta.append(" AND exp.f_inicio_mc is not null ");
				consulta.append(" AND exp.f_fin_mc is not null ");
				consulta.append(" AND SYSDATE >= exp.f_fin_mc ");
			} else if ("A".equals(solicitudesOT.getCEstado())) {
				consulta.append(" AND exp.b_validacionCC = 'Y'");
			} else if ("D".equals(solicitudesOT.getCEstado())) {
				consulta.append(" AND exp.b_validacionCC = 'N'");
			} else if ("P".equals(solicitudesOT.getCEstado())) {
				consulta.append(" AND exp.f_inicio_mc is not null ");
				consulta.append(" AND exp.f_fin_mc is not null ");
				consulta.append(" AND SYSDATE >= exp.f_fin_mc ");
				consulta.append(" AND exp.b_validacioncc IS NULL ");
			}

			consulta.append(" ) ");

			sentencia = con.prepareStatement(consulta.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				numSolicitudes = resultado.getInt("Numero");
			}

			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.info(getClass().getName() + " contarSolicitudes: ERROR: " + e.getMessage());
			throw new SQLException(e.getMessage() + "<br>Error al contar las solicitudes");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return numSolicitudes;
	}

	public ArrayList buscarUsuariosSinFinalizarMC(long convoId, long gradoId) throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " buscarUsuariosSinFinalizarMC: Inicio");

		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		StringBuffer consulta = new StringBuffer();

		ResultSet resultado = null;
		ArrayList listado = null;
		try {
			consulta.append("SELECT usu.c_dni, oex.c_exp_id ")
					.append(" FROM ocap_usuarios usu, ocap_expedientescarrera oex, ocap_convocatorias oco ")
					.append(" WHERE oex.c_convocatoria_id = ? ").append(" AND oex.c_grado_id = ? ")
					.append(" AND oex.f_aceptac_solic is not null").append(" AND oex.C_USR_ID = usu.C_USR_ID ")
					.append(" AND oex.C_EXP_ID not in (select distinct(c_exp_id) from ocap_creditosceis where b_borrado='N') ")
					.append(" AND oex.C_CONVOCATORIA_ID = oco.C_CONVOCATORIA_ID ")
					.append(" AND sysdate > (oco.F_INICIOMC + oco.N_DIAS_AUTO_MC) ");

			st = con.prepareStatement(consulta.toString());
			st.setLong(1, convoId);
			st.setLong(2, gradoId);

			resultado = st.executeQuery();

			listado = new ArrayList();
			while (resultado.next()) {
				this.solicitudesOT = new OCAPSolicitudesOT();
				this.solicitudesOT.setCDni(resultado.getString("c_dni"));
				this.solicitudesOT.setCExp_id(resultado.getLong("c_exp_id"));

				listado.add(this.solicitudesOT);
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarUsuariosSinFinalizarMC: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarUsuariosSinFinalizarMC: ERROR: " + e.getMessage());
			throw new SQLException(e.getMessage() + "<br>Error al buscar las solicitudes");
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}
	
	/**
	 * 
	 * @param jcylUsuario
	 * @param cConvocatoriaId
	 * @return
	 * @throws Exception
	 */
	public ArrayList existeSolicParaUsrYConv(String nif, String cConvocatoriaId)
			throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesGrs: Inicio");
		
		PreparedStatement sentencia = null;
		Connection con = null;

		StringBuffer consulta = new StringBuffer();
		OCAPSolicitudesOT solicitudesOT = new OCAPSolicitudesOT();
		ResultSet resultado = null;
		ArrayList listado = null;
		try {
			con = JCYLGestionTransacciones.getConnection();
			
			consulta.append(" SELECT solic.c_solicitud_id AS solicitud_id,")
					.append(" solic.D_NOMBRE as nombre,")
					.append(" solic.D_APELLIDOS as apellidos,")
					.append(" solic.A_DOMICILIO as domicilio,")
					.append(" solic.B_SEXO as sexo,")
					.append(" solic.C_DNI as dni,")
					.append(" solic.N_TELEFONO1 as telefono1,")
					.append(" solic.N_TELEFONO2 as telefono2,")
					.append(" solic.A_CORREOELECTRONICO as correoelectronico,")
					.append(" solic.A_DOMICILIO as domicilio,")
					.append(" solic.C_PROVINCIA_ID as provincia_id,")
					.append(" solic.D_LOCALIDAD as localidad,")
					.append(" solic.N_CODIGOPOSTAL as codigopostal,")
					.append(" solic.C_JURIDICO_ID as juridico_id,")
					.append(" solic.C_SIT_ADMINISTRATIVA_ID as sitadministrativa_id,")
					.append(" solic.C_GERENCIA_ID as gerencia,")
					.append(" solic.C_CENTROTRABAJO_ID as centrotrabajo,")
					.append(" solic.C_PROC_EVALUACION_ID as procevaluacion,")
					.append(" solic.C_CATEGORIA_ID as categoria,")
					.append(" solic.C_ESPECIALIDAD_ID as especialidad,")
					.append(" solic.N_ANIOSANTIGUEDAD as aniosantiguedad,")
					.append(" solic.N_MESES_ANTIGUEDAD as mesesantiguedad,")
					.append(" solic.N_DIAS_ANTIGUEDAD as diasantiguedad,")
					.append(" solic.F_REGISTRO_SOLIC as fregistrosolic,")
					.append(" solic.B_LOPD_SOLICITUD as blopdsolicitud,")
					.append(" solic.F_REGISTRO_OFICIAL as fregistrooficial,")
					.append(" solic.A_OTRASITADMINISTRATIVA as otrasitadministrativa,")
					.append(" solic.C_PROVINCIA_CARRERA_ID as provinciacarrera_id,")
					.append(" solic.C_USR_ID as usr_id,")
					.append(" solic.C_ESTADO_ID as estado_id,")
					.append(" solic.C_MOD_ANTERIOR_ID as modanterior_id,")
					.append(" solic.C_SERVICIO_ID as servicio_id,")
					.append(" solic.A_OTROJURIDICO as otrojuridico,")
					.append(" solic.A_SERVICIO as servicio,")
					.append(" solic.A_PUESTO as puesto,")
					.append(" solic.C_SIT_ADMON_ACTUAL_ID as admonactual,")
					.append(" solic.A_OTRASITADMON_ACTUAL as otraadmonactual,")
					.append(" solic.C_GERENCIA_ACTUAL_ID as gerenciaact,")
					.append(" solic.C_CENTROTRAB_ACTUAL_ID as centrotrabact,")
					.append(" solic.C_PROV_CARRERA_ACTUAL_ID as provcarract,")
					//.append(" exp.B_OTROSCENTROS as otroscentros,")
					
					.append(" solic.C_GRADO_ID as grado,")
					.append(" solic.C_CONVOCATORIA_ID as convocatoria")
					
					.append(" FROM ")
					.append("  ocap_solicitudes solic,")
					.append("  ocap_grados gra,")
					.append("  ocap_categ_profesionales cat,")
					.append("  ocap_especialidades esp,")
					.append("  ocap_convocatorias con,")
					.append("  ocap_gerencias ger,")
					.append("  ocap_modalidades mod ")
					//.append("  ocap_expedientescarrera exp")
					.append(" WHERE ")
					.append(" cat.c_profesional_id  = solic.c_categoria_id")
					.append(" AND esp.c_espec_id (+)    = solic.c_especialidad_id")
					.append(" AND gra.c_grado_id (+)    = solic.c_grado_id")
					.append(" AND con.c_convocatoria_id = solic.c_convocatoria_id")
					.append(" AND ger.c_gerencia_id     = solic.c_gerencia_id")
					.append(" AND cat.c_modalidad_id    = mod.c_modalidad_id")
					
					//.append(" AND exp.C_GRADO_ID    = solic.C_GRADO_ID")
					//.append(" AND exp.C_CONVOCATORIA_ID    = solic.C_CONVOCATORIA_ID")
					//.append(" AND exp.C_SOLICITUD_ID    = solic.C_SOLICITUD_ID")
					
					.append(" AND UPPER(solic.C_DNI)                 = ? ")
					.append(" AND solic.C_CONVOCATORIA_ID     = ? ");
				
					consulta.append(" ORDER BY solic.C_SOLICITUD_ID DESC ");
			
			sentencia = con.prepareStatement(consulta.toString());
			sentencia.setString(1, nif.toUpperCase());
			sentencia.setInt(2, Integer.parseInt(cConvocatoriaId));
			
			resultado = sentencia.executeQuery();

			listado = new ArrayList();
			while (resultado.next()) {
				solicitudesOT.setCSolicitudId(resultado.getLong("solicitud_id"));
				solicitudesOT.setDNombre(resultado.getString("nombre"));
				solicitudesOT.setDApellido1(resultado.getString("apellidos"));
				solicitudesOT.setDDomicilio(resultado.getString("domicilio"));
				solicitudesOT.setCDni(resultado.getString("dni"));
				solicitudesOT.setCDniReal(resultado.getString("dni"));
				solicitudesOT.setBSexo(resultado.getString("sexo"));
				solicitudesOT.setNTelefono1(resultado.getString("telefono1"));
				solicitudesOT.setNTelefono2(resultado.getString("telefono2"));
				solicitudesOT.setDCorreoelectronico(resultado.getString("correoelectronico"));
				solicitudesOT.setDDomicilio(resultado.getString("domicilio"));
				solicitudesOT.setCProvincia_id(resultado.getString("provincia_id"));
				solicitudesOT.setDLocalidad(resultado.getString("localidad"));
				solicitudesOT.setCPostalUsu(resultado.getString("codigopostal"));
				solicitudesOT.setCJuridicoId(resultado.getString("juridico_id"));
				solicitudesOT.setCSitAdministrativaAuxId(resultado.getString("sitadministrativa_id"));
				solicitudesOT.setCGerencia_id(resultado.getLong("gerencia"));
				solicitudesOT.setCCentrotrabajo_id(resultado.getLong("centrotrabajo"));
				solicitudesOT.setCProcedimientoId(resultado.getString("procevaluacion"));
				solicitudesOT.setCProfesional_id(resultado.getLong("categoria"));
				solicitudesOT.setCEspec_id(resultado.getLong("especialidad"));
				solicitudesOT.setNAniosantiguedad(resultado.getLong("aniosantiguedad"));
				solicitudesOT.setNDiasantiguedad(resultado.getLong("mesesantiguedad"));
				solicitudesOT.setNMesesantiguedad(resultado.getLong("diasantiguedad"));
				if(resultado.getDate("fregistrosolic")!=null){
					solicitudesOT.setFRegistro_solic(DateUtil.convertDateToString(resultado.getDate("fregistrosolic")));
				}
				solicitudesOT.setBLopdSolicitud(resultado.getString("blopdsolicitud"));
				if(resultado.getDate("fregistrooficial")!=null){
					solicitudesOT.setFRegistro_oficial(DateUtil.convertDateToString(resultado.getDate("fregistrooficial")));
				}
				solicitudesOT.setDSitAdministrativaOtros(resultado.getString("otrasitadministrativa"));
				solicitudesOT.setCProvinciaCarrera_id(resultado.getString("provinciacarrera_id"));
				solicitudesOT.setCUsr_id(resultado.getLong("usr_id"));
				solicitudesOT.setCEstado_id(resultado.getLong("estado_id"));
				solicitudesOT.setCModAnterior_id(resultado.getLong("modanterior_id"));
				// NO SE DONDE METERLO
				solicitudesOT.setADondeServicio(resultado.getString("servicio_id"));
				solicitudesOT.setDRegimenJuridicoOtros(resultado.getString("otrojuridico"));
				solicitudesOT.setAServicio(resultado.getString("servicio"));
				solicitudesOT.setAPuesto(resultado.getString("puesto"));
				solicitudesOT.setCSitAdmonActualId(resultado.getLong("admonactual"));
				solicitudesOT.setAOtraSitAdmonActual(resultado.getString("otraadmonactual"));
				solicitudesOT.setCGerenciaActualId(resultado.getLong("gerenciaact"));
				solicitudesOT.setCCentroTrabActualId(resultado.getLong("centrotrabact"));
				solicitudesOT.setDProvCarreraActual(resultado.getString("provcarract"));
				//solicitudesOT.setBOtrosCentros(resultado.getString("otroscentros"));
				solicitudesOT.setCConvocatoriaId(resultado.getLong("convocatoria"));
				solicitudesOT.setCGrado_id(resultado.getLong("grado"));
				
				listado.add(solicitudesOT);

				break;
			}

			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesGrs: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.info(getClass().getName() + " buscarSolicitudesGrs: ERROR: " + e.getMessage());
			throw new SQLException(e.getMessage() + "<br>Error al buscar las solicitudes");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}
	
	/**
	 * 
	 * @param datos
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public int modificarDatosSolicitud(OCAPSolicitudesOT solicitud, Connection con, boolean grs) throws SQLException {
		PreparedStatement st = null;

		int filas = 0;
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("UPDATE OCAP_SOLICITUDES ").append("SET D_NOMBRE = ?, ").append("D_APELLIDOS = ?, ")
					.append("B_SEXO = ?, ").append("N_TELEFONO1 = ?, ").append("N_TELEFONO2 = ?, ")
					.append("A_CORREOELECTRONICO = ?, ");
			if (solicitud.getCDni() != null){
				sql.append("C_DNI = LPAD(?,9,'0'), ");
			}
				sql.append("A_DOMICILIO = ?, ").append("C_PROVINCIA_ID = ?, ")
					.append("D_LOCALIDAD = ?, ").append("N_CODIGOPOSTAL = ?, ").append("C_CENTROTRAB_ACTUAL_ID = ?, ")
					.append("C_GERENCIA_ACTUAL_ID = ?, ");
				
				if (grs){
					sql.append(" C_PROV_CARRERA_ACTUAL_ID = ?, C_PROVINCIA_CARRERA_ID = ?, C_GERENCIA_ID = ?, C_CENTROTRABAJO_ID = ?, C_SIT_ADMON_ACTUAL_ID =?, ");
				}
				
				sql.append("F_USUMODI = SYSDATE, ").append("C_USUMODI = ? ")
					.append("WHERE C_SOLICITUD_ID = ?");

			st = con.prepareStatement(sql.toString());
			int cont = 1;
			st.setString(cont++, solicitud.getDNombre());
			st.setString(cont++, solicitud.getDApellido1());
			st.setString(cont++, solicitud.getBSexo());
			if (solicitud.getNTelefono1() != null)
				st.setString(cont++, String.valueOf(solicitud.getNTelefono1()));
			else
				st.setNull(cont++, 2);
			if (solicitud.getNTelefono2() != null)
				st.setString(cont++, String.valueOf(solicitud.getNTelefono2()));
			else
				st.setNull(cont++, 2);
			st.setString(cont++, solicitud.getDCorreoelectronico());
			if (solicitud.getCDni() != null)
				st.setString(cont++, solicitud.getCDni());
			st.setString(cont++, solicitud.getDDomicilio());
			st.setString(cont++, solicitud.getCProvincia_id());
			st.setString(cont++, solicitud.getDLocalidadUsu());
			st.setString(cont++, solicitud.getCPostalUsu());
			if (solicitud.getCCentrotrabajo_id() != 0)
				st.setInt(cont++, (int)solicitud.getCCentrotrabajo_id());
			else {
				st.setNull(cont++, 4);
			}
			st.setInt(cont++, (int)solicitud.getCGerencia_id());
			
			if (grs) {
				st.setString(cont++, solicitud.getCProvinciaCarrera_id());
				st.setString(cont++, solicitud.getCProvCarreraActualId());
				st.setInt(cont++, (int)solicitud.getCGerenciaActualId());
				st.setInt(cont++, (int)solicitud.getCCentroTrabActualId());
				st.setInt(cont++, (int)solicitud.getCSitAdmonActualId());
			}

			st.setString(cont++, solicitud.getAModificadoPor());

			st.setLong(cont++, solicitud.getCSolicitudId());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null) {
				st.close();
			}
		}

		return filas;
	}
	
	
	public OCAPAccesoOT usuarioTieneSolicitudEnEstado(String aDni, long estado)
			throws Exception {
		OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitudEnEstado: Inicio");
		
		PreparedStatement sentencia = null;
		Connection con = null;

		StringBuffer consulta = new StringBuffer();
		OCAPAccesoOT ocapAccesoOt = null;
		ResultSet resultado = null;
		
		try {
			con = JCYLGestionTransacciones.getConnection();
			
			consulta.append(" SELECT solic.c_solicitud_id AS solicitud_id,")					
					.append(" solic.C_DNI as dni,")					
					.append(" solic.A_CORREOELECTRONICO as correoelectronico,")
					.append(" solic.D_NOMBRE as nombre,")
					.append(" solic.D_APELLIDOS as apellidos,")
					.append(" solic.N_TELEFONO1 as telefono1,")
					.append(" solic.N_TELEFONO2 as telefono2 ")
					.append(" FROM ")
					.append("  ocap_solicitudes solic ")					
					.append(" WHERE ")
					.append(" solic.C_DNI                 = ? ");
					if (estado!=Constantes.ESTADO_SIN_ESTADO )
					{
						consulta.append(" AND solic.C_ESTADO_ID      = ? ");
					}
					consulta.append(" ORDER BY solic.C_SOLICITUD_ID DESC ");
			
					
			
			sentencia = con.prepareStatement(consulta.toString());
			sentencia.setString(1, aDni);
			if (estado!=Constantes.ESTADO_SIN_ESTADO )
			{
				sentencia.setLong(2, estado);
			}
			
			resultado = sentencia.executeQuery();

			
		  if (resultado.next()) {			
			  ocapAccesoOt = new OCAPAccesoOT();
				ocapAccesoOt.setCDni(resultado.getString("dni"));
				ocapAccesoOt.setACorreoRecupera(resultado.getString("correoelectronico"));
				ocapAccesoOt.setAApellidos(resultado.getString("apellidos"));
				ocapAccesoOt.setANombre(resultado.getString("nombre"));
				ocapAccesoOt.setNTelefono(String.valueOf(resultado.getInt("telefono1")));
				ocapAccesoOt.setNMovil(String.valueOf(resultado.getInt("telefono2")));
				
			}

			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitudEnEstado: Fin");
		} catch (SQLException e) {
			OCAPConfigApp.logger.info(getClass().getName() + " usuarioTieneSolicitudEnEstado: ERROR: " + e.getMessage());
			throw new SQLException(e.getMessage() + "<br>Error al buscar las solicitudes");
		} finally {
			if (resultado != null)
				resultado.close();
			if (sentencia != null)
				sentencia.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return ocapAccesoOt;
	}

	public OCAPSolicitudesOT buscarSolicitud(long idSolicitud) {
		OCAPSolicitudesOT solicitud = null;
		PreparedStatement sentencia = null;
		Connection con = null;
		ResultSet resultado = null;
		try {
			con = JCYLGestionTransacciones.getConnection();

			sentencia = con.prepareStatement("SELECT * FROM OCAP_SOLICITUDES WHERE C_SOLICITUD_ID = ? ");
			sentencia.setLong(1, idSolicitud);
			resultado = sentencia.executeQuery();
			if (resultado.next()) {
				solicitud = new OCAPSolicitudesOT();
				solicitud.setDApellido1(resultado.getString("D_APELLIDOS"));
				solicitud.setDNombre(resultado.getString("D_NOMBRE"));
				solicitud.setCDni(resultado.getString("C_DNI"));

			}
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
		}
		return solicitud;

	}
	
}

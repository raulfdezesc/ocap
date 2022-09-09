package es.jcyl.fqs.ocap.oad.creditos;

import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.oad.defMeritosCurriculares.OCAPDefMeritoscurricularesOAD;
import es.jcyl.fqs.ocap.oad.meritosCurriculares.OCAPMeritoscurricularesOAD;
import es.jcyl.fqs.ocap.ot.creditos.OCAPCreditosOT;
import es.jcyl.fqs.ocap.ot.defMeritosCurriculares.OCAPDefMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.meritosCurriculares.OCAPMeritoscurricularesOT;
import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
import es.jcyl.framework.db.JCYLGestionTransacciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class OCAPCreditosOAD {
	public static Logger logger = OCAPConfigApp.logger;
	public Logger loggerBD;
	private static OCAPCreditosOAD instance;

	private void $init$() {
		this.loggerBD = OCAPConfigApp.loggerBD;
	}

	public static OCAPCreditosOAD getInstance() {
		if (instance == null) {
			instance = new OCAPCreditosOAD();
		}
		return instance;
	}

	private OCAPCreditosOAD() {
		$init$();
	}

	public int altaOCAPCreditos(OCAPCreditosOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "INSERT INTO OCAP_CREDITOS (N_CREDITOS, C_ESTATUT_ID, C_CREDITO_ID, B_BORRADO, C_DEFMERITO_ID, F_USUALTA, C_GRADO_ID, C_USUALTA) VALUES (?, ?, OCAP_CRE_ID_SEQ.nextval, 'N', ?, SYSDATE, ?, ?)";

			st = con.prepareStatement(sql);
			st.setDouble(1, datos.getNCreditos());
			st.setLong(2, datos.getCEstatutId());
			st.setLong(3, datos.getCDefmeritoId());
			st.setLong(4, datos.getCGradoId());
			st.setString(5, datos.getACreadoPor());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int bajaOCAPCreditos(int cCreditoId) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CREDITOS SET B_BORRADO = 'Y', F_USUMODI = SYSDATE WHERE C_CREDITO_ID =  ?";

			st = con.prepareStatement(sql);
			st.setInt(1, cCreditoId);

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public int modificacionOCAPCreditos(OCAPCreditosOT datos) throws SQLException {
		PreparedStatement st = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		int filas = 0;
		try {
			String sql = "UPDATE OCAP_CREDITOS  SET N_CREDITOS = ?, C_ESTATUT_ID = ?, F_USUMODI = SYSDATE, C_DEFMERITO_ID = ?, C_GRADO_ID = ? WHERE C_CREDITO_ID = ?";

			st = con.prepareStatement(sql);

			st.setDouble(1, datos.getNCreditos());
			st.setLong(2, datos.getCEstatutId());
			st.setLong(3, datos.getCDefmeritoId());
			st.setLong(4, datos.getCGradoId());
			st.setLong(5, datos.getCCreditoId());

			filas = st.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return filas;
	}

	public OCAPCreditosOT buscarOCAPCreditos(long cCreditoId) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPCreditosOT datos = null;
		try {
			String sql = "SELECT N_CREDITOS, C_ESTATUT_ID, C_CREDITO_ID, B_BORRADO, F_USUMODI, C_DEFMERITO_ID, F_USUALTA, C_GRADO_ID FROM OCAP_CREDITOS WHERE C_CREDITO_ID = ? AND B_BORRADO = 'N'";

			st = con.prepareStatement(sql);
			st.setLong(1, cCreditoId);

			rs = st.executeQuery();

			datos = new OCAPCreditosOT();
			if (rs.next()) {
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCCreditoId(cCreditoId);
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return datos;
	}

	public ArrayList buscarOCAPCreditosPorCategProfesional(OCAPUsuariosOT usuarioOT, Integer cGradoId,
			String meritosSeleccionados, JCYLUsuario jcylUsuario) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList listadoAux = null;
		ArrayList listado = null;

		Connection con = null;
		OCAPCreditosOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPCreditosPorCategProfesional");
			con = JCYLGestionTransacciones.getConnection();

			String sql = "SELECT distinct(cre.C_DEFMERITO_ID), cre.N_CREDITOS, cre.C_ESTATUT_ID, cre.C_CREDITO_ID, cre.B_BORRADO, cre.C_DEFMERITO_ID, cre.C_GRADO_ID, defm.D_NOMBRE D_DEFMERITO_NOMBRE, (select DBMS_LOB.SUBSTR(men.d_descripcion) from ocap_mensajes_mc men where cre.c_defmerito_id = men.c_defmerito_id AND cre.C_ESTATUT_ID = men.C_ESTATUT_ID AND men.b_borrado = 'N') as D_MENSAJE_DESC  FROM OCAP_CREDITOS cre, OCAP_CATEG_PROFESIONALES pro, ocap_def_meritoscurriculares defm  WHERE pro.C_PROFESIONAL_ID = ?  AND cre.C_GRADO_ID = ?  AND pro.C_ESTATUT_ID = cre.C_ESTATUT_ID  AND defm.C_DEFMERITO_ID = cre.C_DEFMERITO_ID  AND cre.B_BORRADO = 'N'  AND pro.B_BORRADO = 'N'  AND defm.B_BORRADO = 'N'  order by cre.c_defmerito_id";

			st = con.prepareStatement(sql);
			st.setInt(1, usuarioOT.getCProfesionalId().intValue());
			st.setInt(2, cGradoId.intValue());

			rs = st.executeQuery();
			listadoAux = new ArrayList();

			boolean bSinPestannia = false;
			if ("".equals(meritosSeleccionados))
				bSinPestannia = true;

			while (rs.next()) {
				datos = new OCAPCreditosOT();
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCCreditoId(rs.getLong("C_CREDITO_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				if (bSinPestannia) {
					datos.setBMeritoSeleccionado(Constantes.SI);
					bSinPestannia = false;
					meritosSeleccionados = String.valueOf(datos.getCDefmeritoId());
				} else if (Long.parseLong(meritosSeleccionados) == datos.getCDefmeritoId()) {
					datos.setBMeritoSeleccionado(Constantes.SI);
				} else {
					datos.setBMeritoSeleccionado(Constantes.NO);
				}
				if (rs.getString("D_MENSAJE_DESC") != null)
					datos.setDDefMeritoDesc(rs.getString("D_MENSAJE_DESC"));
				else {
					datos.setDDefMeritoDesc(" ");
				}

				datos.setDMerito(rs.getString("D_DEFMERITO_NOMBRE"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				listadoAux.add(datos);
			}

			listado = new ArrayList();
			listado.add(meritosSeleccionados);

			OCAPMeritoscurricularesOAD mcOAD = OCAPMeritoscurricularesOAD.getInstance();

			for (int i = 0; i < listadoAux.size(); i++) {
				datos = (OCAPCreditosOT) listadoAux.get(i);

				float creditosTotales = 0.0F;

				float creditosTotalesValidadosCeis = 0.0F;
				float creditosTotalesValidadosCc = 0.0F;
				OCAPMeritoscurricularesOT mcOT;
				if (Constantes.DEF_MERITO_OPCIONALES.equals(String.valueOf(datos.getCDefmeritoId()))) {
					OCAPDefMeritoscurricularesOT defMCOT = null;
					OCAPDefMeritoscurricularesOAD defMCOAD = OCAPDefMeritoscurricularesOAD.getInstance();
					ArrayList listaDefMeritos = defMCOAD.listadoOCAPDefMeritoscurriculares();

					for (int j = 0; j < listaDefMeritos.size(); j++) {
						float credito = 0.0F;

						float creditoValidadoCeis = 0.0F;
						float creditoValidadoCc = 0.0F;
						defMCOT = (OCAPDefMeritoscurricularesOT) listaDefMeritos.get(j);

						if ((!Constantes.DEF_MERITO_FORMACION.equals(String.valueOf(defMCOT.getCDefmeritoId())))
								&& (!Constantes.DEF_MERITO_OPCIONALES.equals(String.valueOf(defMCOT.getCDefmeritoId())))) {
							mcOT = mcOAD.buscarOCAPMeritoscurricularesPorUsuarioOT(usuarioOT,
									Long.valueOf(defMCOT.getCDefmeritoId()), Constantes.SI, jcylUsuario, false);
							credito = mcOT.getCreditos().floatValue();

							creditoValidadoCeis = mcOT.getCreditosValidadosCeis().floatValue();
							creditoValidadoCc = mcOT.getCreditosValidadosCc().floatValue();
						}
						creditosTotales += credito;

						creditosTotalesValidadosCeis += creditoValidadoCeis;
						creditosTotalesValidadosCc += creditoValidadoCc;
					}
				} else {
					mcOT = mcOAD.buscarOCAPMeritoscurricularesPorUsuarioOT(usuarioOT,
							Long.valueOf(datos.getCDefmeritoId()), Constantes.NO, jcylUsuario, false);
					creditosTotales = mcOT.getCreditos().floatValue();

					creditosTotalesValidadosCeis = mcOT.getCreditosValidadosCeis().floatValue();
					creditosTotalesValidadosCc = mcOT.getCreditosValidadosCc().floatValue();
				}

				datos.setNCreditosConseguidos(Float.valueOf((float) (Math.rint(creditosTotales * 100.0F) / 100.0D)));

				datos.setNCreditosValidadosCeis(
						Float.valueOf((float) (Math.rint(creditosTotalesValidadosCeis * 100.0F) / 100.0D)));
				datos.setNCreditosValidadosCc(
						Float.valueOf((float) (Math.rint(creditosTotalesValidadosCc * 100.0F) / 100.0D)));

				listado.add(datos);
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if ((con != null) && (!con.isClosed())) {
				JCYLGestionTransacciones.close(con.getAutoCommit());
			}
		}
		return listado;
	}

	public ArrayList listadoOCAPCreditos() throws SQLException {
		return listadoOCAPCreditos(1, -1);
	}

	public ArrayList listadoOCAPCreditos(int inicio, int cuantos) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String sql = "SELECT N_CREDITOS, C_ESTATUT_ID, C_CREDITO_ID, B_BORRADO, F_USUMODI, C_DEFMERITO_ID, F_USUALTA, C_GRADO_ID FROM OCAP_CREDITOS WHERE B_BORRADO = 'N' order by C_CREDITO_ID";

			st = con.prepareStatement(sql, 1004, 1008);
			rs = st.executeQuery();

			if (inicio > 1) {
				rs.absolute(inicio - 1);
			}
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				OCAPCreditosOT datos = new OCAPCreditosOT();
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCCreditoId(rs.getLong("C_CREDITO_ID"));
				datos.setBBorrado(rs.getString("B_BORRADO"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));

				listado.add(datos);

				if (++i == cuantos)
					break;
			}

		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public int listadoOCAPCreditosCuenta(long cCreditoId, long cEstatutId, long cGradoId, long cDefmeritoId,
			String nCreditos, String fCreacion, String fModificacion) throws Exception {
		ResultSet rs = null;
		PreparedStatement st = null;

		String where = "";
		StringBuffer sbWhere = new StringBuffer(40);
		Connection con = JCYLGestionTransacciones.getConnection();
		String sql = "SELECT COUNT(*) FROM OCAP_CREDITOS";

		boolean isCCreditoId = cCreditoId != 0L;
		if (isCCreditoId)
			sbWhere.append("C_CREDITO_ID = ").append(cCreditoId).append(" AND ");
		boolean isCEstatutId = cEstatutId != 0L;
		if (isCEstatutId)
			sbWhere.append("C_ESTATUT_ID = ").append(cEstatutId).append(" AND ");
		boolean isCGradoId = cGradoId != 0L;
		if (isCGradoId)
			sbWhere.append("C_GRADO_ID = ").append(cGradoId).append(" AND ");
		boolean isCDefmeritoId = cDefmeritoId != 0L;
		if (isCDefmeritoId)
			sbWhere.append("C_DEFMERITO_ID = ").append(cDefmeritoId).append(" AND ");
		boolean isnCreditos = nCreditos != null;
		if (isnCreditos)
			sbWhere.append("N_CREDITOS = ").append(nCreditos).append(" AND ");
		boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
		if (isFCreacion)
			sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
		boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
		if (isFModificacion)
			sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
		sbWhere.append("B_BORRADO = 'N'  AND ");
		int len = sbWhere.length();
		if (len > 0) {
			where = sbWhere.insert(0, " Where ").substring(0, len + 1);
		}
		String sqlStatement = sql + where;
		int total = -1;
		try {
			st = con.prepareStatement(sqlStatement);
			rs = st.executeQuery(sqlStatement);
			if (rs.next())
				total = rs.getInt(1);
		} catch (Exception e) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return total;
	}

	public ArrayList consultaOCAPCreditos(long cCreditoId, long cEstatutId, long cGradoId, long cDefmeritoId,
			String nCreditos, String fCreacion, String fModificacion, int inicio, int cuantos) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();

		ArrayList listado = null;
		try {
			String where = "";
			StringBuffer sbWhere = new StringBuffer(40);
			String selectFrom = "SELECT C_CREDITO_ID, C_ESTATUT_ID, C_GRADO_ID, C_DEFMERITO_ID, N_CREDITOS, B_BORRADO, F_USUMODI, F_USUALTA FROM OCAP_CREDITOS";

			String orderBy = " order by C_CREDITO_ID";
			boolean isCCreditoId = cCreditoId != 0L;
			if (isCCreditoId)
				sbWhere.append("C_CREDITO_ID = ?  AND ");
			boolean isCEstatutId = cEstatutId != 0L;
			if (isCEstatutId)
				sbWhere.append("C_ESTATUT_ID = ?  AND ");
			boolean isCGradoId = cGradoId != 0L;
			if (isCGradoId)
				sbWhere.append("C_GRADO_ID = ?  AND ");
			boolean isCDefmeritoId = cDefmeritoId != 0L;
			if (isCDefmeritoId)
				sbWhere.append("C_DEFMERITO_ID = ?  AND ");
			boolean isnCreditos = nCreditos != null;
			if (isnCreditos)
				sbWhere.append("N_CREDITOS = ?  AND ");
			boolean isFCreacion = (fCreacion != null) && (!fCreacion.equals(""));
			if (isFCreacion)
				sbWhere.append("to_char(F_USUALTA,'DD/MM/RRRR') = '").append(fCreacion).append("' AND ");
			boolean isFModificacion = (fModificacion != null) && (!fModificacion.equals(""));
			if (isFModificacion)
				sbWhere.append("to_char(F_USUMODI,'DD/MM/RRRR') = '").append(fModificacion).append("' AND ");
			sbWhere.append("B_BORRADO = 'N'  AND ");
			int len = sbWhere.length();
			if (len > 0) {
				where = sbWhere.insert(0, " Where ").substring(0, len + 1);
			}
			String sqlStatement = selectFrom + where + orderBy;
			OCAPConfigApp.logger.info("Sentencia SQL:" + sqlStatement);
			st = con.prepareStatement(sqlStatement, 1004, 1008);

			int index = 1;
			OCAPCreditosOT datos = new OCAPCreditosOT();

			if (isCCreditoId)
				st.setLong(index++, cCreditoId);
			if (isCEstatutId)
				st.setLong(index++, cEstatutId);
			if (isCGradoId)
				st.setLong(index++, cGradoId);
			if (isCDefmeritoId)
				st.setLong(index++, cDefmeritoId);
			if (isnCreditos) {
				st.setFloat(index++, Float.valueOf(nCreditos).floatValue());
			}
			rs = st.executeQuery();
			if (inicio > 1)
				rs.absolute(inicio - 1);
			listado = new ArrayList();
			int i = 0;
			while (rs.next()) {
				datos = new OCAPCreditosOT();
				datos.setCCreditoId(rs.getLong("C_CREDITO_ID"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));

				listado.add(datos);
				if (++i == cuantos)
					break;
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public ArrayList buscarOCAPMeritosPorCategProfesional(Integer cProfesionalId, long cGradoId,
			JCYLUsuario jcylUsuario) throws SQLException, Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList listado = null;

		Connection con = JCYLGestionTransacciones.getConnection();

		OCAPCreditosOT datos = null;
		try {
			this.loggerBD.info(" buscarOCAPMeritosPorCategProfesional: " + cProfesionalId + " grado: " + cGradoId);

			String sql = "SELECT defm.D_NOMBRE D_DEFMERITO_NOMBRE, cre.C_DEFMERITO_ID  FROM OCAP_CREDITOS cre, OCAP_CATEG_PROFESIONALES pro, ocap_def_meritoscurriculares defm  WHERE pro.C_PROFESIONAL_ID = ?  AND cre.C_GRADO_ID = ?  AND pro.C_ESTATUT_ID = cre.C_ESTATUT_ID  AND defm.C_DEFMERITO_ID = cre.C_DEFMERITO_ID  AND cre.B_BORRADO = 'N'  AND pro.B_BORRADO = 'N'  AND defm.B_BORRADO = 'N' ";

			st = con.prepareStatement(sql);
			st.setInt(1, cProfesionalId.intValue());
			st.setLong(2, cGradoId);

			rs = st.executeQuery();
			listado = new ArrayList();

			while (rs.next()) {
				datos = new OCAPCreditosOT();
				datos.setDMerito(rs.getString("D_DEFMERITO_NOMBRE"));
				datos.setCDefmeritoId(rs.getLong("C_DEFMERITO_ID"));
				listado.add(datos);
			}
		} catch (SQLException ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}

	public ArrayList listadoOCAPCreditosCompetencias(int cEstatutId) throws Exception {
		PreparedStatement st = null;
		ResultSet rs = null;
		Connection con = JCYLGestionTransacciones.getConnection();
		OCAPCreditosOT datos = null;
		ArrayList listado = null;
		try {
			String query = " SELECT oc.C_CREDITO_ID,  oc.C_ESTATUT_ID,  oc.C_GRADO_ID,  (select D_DESCRIPCION from OCAP_GRADOS og where og.C_GRADO_ID = oc.C_GRADO_ID) as D_GRADO,  oc.N_CREDITOS,  oc.B_BORRADO,  oc.F_USUMODI,  oc.F_USUALTA  FROM OCAP_CREDITOS oc  WHERE B_BORRADO='N'  AND oc.c_defmerito_id is null  AND oc.C_ESTATUT_ID = ?  order by oc.C_CREDITO_ID";

			OCAPConfigApp.logger.info("Sentencia SQL:" + query);
			st = con.prepareStatement(query, 1004, 1008);

			st.setInt(1, cEstatutId);

			rs = st.executeQuery();

			listado = new ArrayList();
			while (rs.next()) {
				datos = new OCAPCreditosOT();
				datos.setCCreditoId(rs.getLong("C_CREDITO_ID"));
				datos.setCEstatutId(rs.getLong("C_ESTATUT_ID"));
				datos.setCGradoId(rs.getLong("C_GRADO_ID"));
				datos.setDGrado(rs.getString("D_GRADO"));
				datos.setNCreditos(rs.getFloat("N_CREDITOS"));
				datos.setFCreacion(rs.getDate("F_USUALTA"));
				datos.setFModificacion(rs.getDate("F_USUMODI"));
				datos.setBBorrado(rs.getString("B_BORRADO"));

				listado.add(datos);
			}
		} catch (Exception ex) {
			OCAPConfigApp.logger.error(Utilidades.getStackTrace(ex));
			throw ex;
		} finally {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			JCYLGestionTransacciones.close(con.getAutoCommit());
		}

		return listado;
	}
}

package es.jcyl.fqs.ocap.actionforms.documentos;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class OCAPDocumentosForm extends ActionForm {

	private static final long serialVersionUID = -7664954811205848994L;
	private long cDocumento_id;
	private long cProyecto_id;
	private String cUsuario_id;
	private String dTitulo;
	private String dDescripcion;
	private FormFile aDatos;
	private String aPublic;
	private String tareaDocu;
	private String nuevoDocumento;
	private String aExt;
	private String aNombre_usuario;
	private Long cExpId;
	private String codigoId;
	private String accionExito;
	private String tarea;
	private ArrayList listaDocumentos;
	private ArrayList listadoAreas;
	private ArrayList listadoCuestionarios;
	private Long cItinerarioId;
	private String cCuestionarioId;
	private String cAreaId;
	private ArrayList ListadoEvidenciasNoUsadas;
	private String dApellido1;
	private String dNombre;
	private String cDni;
	private String cDniReal;
	private String dCorreoelectronico;
	private String cCentrotrabajo_id;
	private String cEspec_id;
	private String cPerfil_id;
	private String cProfesional_id;
	private String cEstatutarioId;
	private String cEspecActual_id;
	private String cProfesionalActual_id;
	private String cEstatutarioActualId;
	private String cGerencia_id;
	private String nTelefono1;
	private String nTelefono2;
	private String dDomicilio;
	private String bSexo;
	private String cProvinciaUsu_id;
	private String dProvinciaUsu;
	private String dLocalidadUsu;
	private String cPostalUsu;
	private String cProvincia_id;
	private String dProvincia;
	private String aCiudad;
	private String cProcedimientoId;
	private String cSitAdministrativaAuxId;
	private String dSitAdministrativaAuxOtros;
	private String dGrado_des;
	private String cConvocatoriaId;
	private String dEstatutario_nombre;
	private String dEstatutarioActual_nombre;
	private String dProfesional_nombre;
	private String dProfesionalActual_nombre;
	private String dEspec_nombre;
	private String dEspecActual_nombre;
	private String dCentrotrabajo_nombre;
	private String cTipogerencia_id;
	private String dTipogerencia_desc;
	private String dGerencia_nombre;
	private String dProcedimientoNombre;
	private String bOtroServicio;
	private String bValidacion_cc;
	private String aDondeServicio;
	private String cJuridico;
	private ArrayList listaCreditos;
	private String nEvidencias;
	private String nDocumentos;
	private String bTratar;

	public void setCDocumento_id(long cDocumento_id) {
		this.cDocumento_id = cDocumento_id;
	}

	public long getCDocumento_id() {
		return this.cDocumento_id;
	}

	public void setCProyecto_id(long cProyecto_id) {
		this.cProyecto_id = cProyecto_id;
	}

	public long getCProyecto_id() {
		return this.cProyecto_id;
	}

	public void setCUsuario_id(String cUsuario_id) {
		this.cUsuario_id = cUsuario_id;
	}

	public String getCUsuario_id() {
		return this.cUsuario_id;
	}

	public void setADatos(FormFile aDatos) {
		this.aDatos = aDatos;
	}

	public FormFile getADatos() {
		return this.aDatos;
	}

	public void setAPublic(String aPublic) {
		this.aPublic = aPublic;
	}

	public String getAPublic() {
		return this.aPublic;
	}

	public void setTareaDocu(String tareaDocu) {
		this.tareaDocu = tareaDocu;
	}

	public String getTareaDocu() {
		return this.tareaDocu;
	}

	public void setAExt(String aExt) {
		this.aExt = aExt;
	}

	public String getAExt() {
		return this.aExt;
	}

	public void setANombre_usuario(String aNombre_usuario) {
		this.aNombre_usuario = aNombre_usuario;
	}

	public String getANombre_usuario() {
		return this.aNombre_usuario;
	}

	public void setAccionExito(String accionExito) {
		this.accionExito = accionExito;
	}

	public String getAccionExito() {
		return this.accionExito;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public String getTarea() {
		return this.tarea;
	}

	public void setCExpId(Long cExpId) {
		this.cExpId = cExpId;
	}

	public Long getCExpId() {
		return this.cExpId;
	}

	public void setListaDocumentos(ArrayList listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public ArrayList getListaDocumentos() {
		return this.listaDocumentos;
	}

	public void setDTitulo(String dTitulo) {
		this.dTitulo = dTitulo;
	}

	public String getDTitulo() {
		return this.dTitulo;
	}

	public void setDDescripcion(String dDescripcion) {
		this.dDescripcion = dDescripcion;
	}

	public String getDDescripcion() {
		return this.dDescripcion;
	}

	public void setListadoAreas(ArrayList listadoAreas) {
		this.listadoAreas = listadoAreas;
	}

	public ArrayList getListadoAreas() {
		return this.listadoAreas;
	}

	public void setCAreaId(String cAreaId) {
		this.cAreaId = cAreaId;
	}

	public String getCAreaId() {
		return this.cAreaId;
	}

	public void setCItinerarioId(Long cItinerarioId) {
		this.cItinerarioId = cItinerarioId;
	}

	public Long getCItinerarioId() {
		return this.cItinerarioId;
	}

	public void setListadoCuestionarios(ArrayList listadoCuestionarios) {
		this.listadoCuestionarios = listadoCuestionarios;
	}

	public ArrayList getListadoCuestionarios() {
		return this.listadoCuestionarios;
	}

	public void setCCuestionarioId(String cCuestionarioId) {
		this.cCuestionarioId = cCuestionarioId;
	}

	public String getCCuestionarioId() {
		return this.cCuestionarioId;
	}

	public void setDApellido1(String dApellido1) {
		this.dApellido1 = dApellido1;
	}

	public String getDApellido1() {
		return this.dApellido1;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setCDni(String cDni) {
		this.cDni = cDni;
	}

	public String getCDni() {
		return this.cDni;
	}

	public void setCDniReal(String cDniReal) {
		this.cDniReal = cDniReal;
	}

	public String getCDniReal() {
		return this.cDniReal;
	}

	public void setDCorreoelectronico(String dCorreoelectronico) {
		this.dCorreoelectronico = dCorreoelectronico;
	}

	public String getDCorreoelectronico() {
		return this.dCorreoelectronico;
	}

	public void setCCentrotrabajo_id(String cCentrotrabajo_id) {
		this.cCentrotrabajo_id = cCentrotrabajo_id;
	}

	public String getCCentrotrabajo_id() {
		return this.cCentrotrabajo_id;
	}

	public void setCEspec_id(String cEspec_id) {
		this.cEspec_id = cEspec_id;
	}

	public String getCEspec_id() {
		return this.cEspec_id;
	}

	public void setCPerfil_id(String cPerfil_id) {
		this.cPerfil_id = cPerfil_id;
	}

	public String getCPerfil_id() {
		return this.cPerfil_id;
	}

	public void setCProfesional_id(String cProfesional_id) {
		this.cProfesional_id = cProfesional_id;
	}

	public String getCProfesional_id() {
		return this.cProfesional_id;
	}

	public void setCEstatutarioId(String cEstatutarioId) {
		this.cEstatutarioId = cEstatutarioId;
	}

	public String getCEstatutarioId() {
		return this.cEstatutarioId;
	}

	public void setCEspecActual_id(String cEspecActual_id) {
		this.cEspecActual_id = cEspecActual_id;
	}

	public String getCEspecActual_id() {
		return this.cEspecActual_id;
	}

	public void setCProfesionalActual_id(String cProfesionalActual_id) {
		this.cProfesionalActual_id = cProfesionalActual_id;
	}

	public String getCProfesionalActual_id() {
		return this.cProfesionalActual_id;
	}

	public void setCEstatutarioActualId(String cEstatutarioActualId) {
		this.cEstatutarioActualId = cEstatutarioActualId;
	}

	public String getCEstatutarioActualId() {
		return this.cEstatutarioActualId;
	}

	public void setCGerencia_id(String cGerencia_id) {
		this.cGerencia_id = cGerencia_id;
	}

	public String getCGerencia_id() {
		return this.cGerencia_id;
	}

	public void setNTelefono1(String nTelefono1) {
		this.nTelefono1 = nTelefono1;
	}

	public String getNTelefono1() {
		return this.nTelefono1;
	}

	public void setNTelefono2(String nTelefono2) {
		this.nTelefono2 = nTelefono2;
	}

	public String getNTelefono2() {
		return this.nTelefono2;
	}

	public void setDDomicilio(String dDomicilio) {
		this.dDomicilio = dDomicilio;
	}

	public String getDDomicilio() {
		return this.dDomicilio;
	}

	public void setBSexo(String bSexo) {
		this.bSexo = bSexo;
	}

	public String getBSexo() {
		return this.bSexo;
	}

	public void setCProvinciaUsu_id(String cProvinciaUsu_id) {
		this.cProvinciaUsu_id = cProvinciaUsu_id;
	}

	public String getCProvinciaUsu_id() {
		return this.cProvinciaUsu_id;
	}

	public void setDProvinciaUsu(String dProvinciaUsu) {
		this.dProvinciaUsu = dProvinciaUsu;
	}

	public String getDProvinciaUsu() {
		return this.dProvinciaUsu;
	}

	public void setDLocalidadUsu(String dLocalidadUsu) {
		this.dLocalidadUsu = dLocalidadUsu;
	}

	public String getDLocalidadUsu() {
		return this.dLocalidadUsu;
	}

	public void setCPostalUsu(String cPostalUsu) {
		this.cPostalUsu = cPostalUsu;
	}

	public String getCPostalUsu() {
		return this.cPostalUsu;
	}

	public void setCProvincia_id(String cProvincia_id) {
		this.cProvincia_id = cProvincia_id;
	}

	public String getCProvincia_id() {
		return this.cProvincia_id;
	}

	public void setDProvincia(String dProvincia) {
		this.dProvincia = dProvincia;
	}

	public String getDProvincia() {
		return this.dProvincia;
	}

	public void setACiudad(String aCiudad) {
		this.aCiudad = aCiudad;
	}

	public String getACiudad() {
		return this.aCiudad;
	}

	public void setCSitAdministrativaAuxId(String cSitAdministrativaAuxId) {
		this.cSitAdministrativaAuxId = cSitAdministrativaAuxId;
	}

	public String getCSitAdministrativaAuxId() {
		return this.cSitAdministrativaAuxId;
	}

	public void setDSitAdministrativaAuxOtros(String dSitAdministrativaAuxOtros) {
		this.dSitAdministrativaAuxOtros = dSitAdministrativaAuxOtros;
	}

	public String getDSitAdministrativaAuxOtros() {
		return this.dSitAdministrativaAuxOtros;
	}

	public void setDGrado_des(String dGrado_des) {
		this.dGrado_des = dGrado_des;
	}

	public String getDGrado_des() {
		return this.dGrado_des;
	}

	public void setDEstatutario_nombre(String dEstatutario_nombre) {
		this.dEstatutario_nombre = dEstatutario_nombre;
	}

	public String getDEstatutario_nombre() {
		return this.dEstatutario_nombre;
	}

	public void setDEstatutarioActual_nombre(String dEstatutarioActual_nombre) {
		this.dEstatutarioActual_nombre = dEstatutarioActual_nombre;
	}

	public String getDEstatutarioActual_nombre() {
		return this.dEstatutarioActual_nombre;
	}

	public void setDProfesional_nombre(String dProfesional_nombre) {
		this.dProfesional_nombre = dProfesional_nombre;
	}

	public String getDProfesional_nombre() {
		return this.dProfesional_nombre;
	}

	public void setDProfesionalActual_nombre(String dProfesionalActual_nombre) {
		this.dProfesionalActual_nombre = dProfesionalActual_nombre;
	}

	public String getDProfesionalActual_nombre() {
		return this.dProfesionalActual_nombre;
	}

	public void setDEspec_nombre(String dEspec_nombre) {
		this.dEspec_nombre = dEspec_nombre;
	}

	public String getDEspec_nombre() {
		return this.dEspec_nombre;
	}

	public void setDEspecActual_nombre(String dEspecActual_nombre) {
		this.dEspecActual_nombre = dEspecActual_nombre;
	}

	public String getDEspecActual_nombre() {
		return this.dEspecActual_nombre;
	}

	public void setDCentrotrabajo_nombre(String dCentrotrabajo_nombre) {
		this.dCentrotrabajo_nombre = dCentrotrabajo_nombre;
	}

	public String getDCentrotrabajo_nombre() {
		return this.dCentrotrabajo_nombre;
	}

	public void setDGerencia_nombre(String dGerencia_nombre) {
		this.dGerencia_nombre = dGerencia_nombre;
	}

	public String getDGerencia_nombre() {
		return this.dGerencia_nombre;
	}

	public void setBOtroServicio(String bOtroServicio) {
		this.bOtroServicio = bOtroServicio;
	}

	public String getBOtroServicio() {
		return this.bOtroServicio;
	}

	public void setBValidacion_cc(String bValidacion_cc) {
		this.bValidacion_cc = bValidacion_cc;
	}

	public String getBValidacion_cc() {
		return this.bValidacion_cc;
	}

	public void setADondeServicio(String aDondeServicio) {
		this.aDondeServicio = aDondeServicio;
	}

	public String getADondeServicio() {
		return this.aDondeServicio;
	}

	public void setCJuridico(String cJuridico) {
		this.cJuridico = cJuridico;
	}

	public String getCJuridico() {
		return this.cJuridico;
	}

	public void setCTipogerencia_id(String cTipogerencia_id) {
		this.cTipogerencia_id = cTipogerencia_id;
	}

	public String getCTipogerencia_id() {
		return this.cTipogerencia_id;
	}

	public void setDTipogerencia_desc(String dTipogerencia_desc) {
		this.dTipogerencia_desc = dTipogerencia_desc;
	}

	public String getDTipogerencia_desc() {
		return this.dTipogerencia_desc;
	}

	public void setCConvocatoriaId(String cConvocatoriaId) {
		this.cConvocatoriaId = cConvocatoriaId;
	}

	public String getCConvocatoriaId() {
		return this.cConvocatoriaId;
	}

	public void setListaCreditos(ArrayList listaCreditos) {
		this.listaCreditos = listaCreditos;
	}

	public ArrayList getListaCreditos() {
		return this.listaCreditos;
	}

	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	public String getCodigoId() {
		return this.codigoId;
	}

	public void setNEvidencias(String nEvidencias) {
		this.nEvidencias = nEvidencias;
	}

	public String getNEvidencias() {
		return this.nEvidencias;
	}

	public void setNDocumentos(String nDocumentos) {
		this.nDocumentos = nDocumentos;
	}

	public String getNDocumentos() {
		return this.nDocumentos;
	}

	public void setListadoEvidenciasNoUsadas(ArrayList ListadoEvidenciasNoUsadas) {
		this.ListadoEvidenciasNoUsadas = ListadoEvidenciasNoUsadas;
	}

	public ArrayList getListadoEvidenciasNoUsadas() {
		return this.ListadoEvidenciasNoUsadas;
	}

	public void setCProcedimientoId(String cProcedimientoId) {
		this.cProcedimientoId = cProcedimientoId;
	}

	public String getCProcedimientoId() {
		return this.cProcedimientoId;
	}

	public void setDProcedimientoNombre(String dProcedimientoNombre) {
		this.dProcedimientoNombre = dProcedimientoNombre;
	}

	public String getDProcedimientoNombre() {
		return this.dProcedimientoNombre;
	}

	public void setBTratar(String bTratar) {
		this.bTratar = bTratar;
	}

	public String getBTratar() {
		return this.bTratar;
	}

	public String getNuevoDocumento() {
		return nuevoDocumento;
	}

	public void setNuevoDocumento(String nuevoDocumento) {
		this.nuevoDocumento = nuevoDocumento;
	}
	
	
}

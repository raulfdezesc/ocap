package es.jcyl.fqs.ocap.actionforms.convocatorias;

import es.jcyl.fqs.ocap.actionforms.comun.OCAPComunForm;

public class OCAPConvocatoriasForm extends OCAPComunForm {

	private static final long serialVersionUID = -1666254556063832919L;
	private long cConvocatoria_id;
	private String dNombre;
	private String dNombreCorto;
	private String cGrado_id;
	
	private String fResolucion;
	private String fPublicacion;
	private String fInicioSolicitud;
	private String fFinSolicitud;
	private String fInicioMC;
	private String fInicioValoracionMC;
	private String fFinValoracionMC;
	private String fFinPgp;
	
	private String fInicioValoracionCC;
	private String fFinValoracionCC;
	
	private String fFinMC;
	private String fInicioAlega;
	private String fInicioEstudioCC;
	private String fFinEstudioCC;
	private String fInicioCA;
	private String fFinCA;
	
	
	private String fInicioValCA;
	private String fFinValCA;
	
	private String fFinCp;
	private long nDias_regsolicitud;
	private long nDias_revsolicitud;
	private long nDias_publistado;
	private long nDias_auto_mc;
	private long nDias_val_mc;
	private long nDias_inconf_mc;
	private long nDias_auto_ca;
	private long nDias_val_ca;
	private long nDias_inconf_ca;
	private long nDias_val_cc;
	private long nDias_respinconf_mc;
	private long nDias_respinconf_ca;
	private long nDias_respinconf_cc;
	private String aObservaciones;
	private String fCreacion;
	private String fModificacion;
	private String bBorrado;
	private String aCreacion;
	private String mCreacion;
	private String dCreacion;
	private String aModificacion;
	private String mModificacion;
	private String dModificacion;
	private String dGrado_nombre;
	private String aResolucion;
	private String mResolucion;
	private String dResolucion;
	private String aPublicacion;
	private String mPublicacion;
	private String dPublicacion;
	
	private String aInicioSolicitud;
	private String mInicioSolicitud;
	private String dInicioSolicitud;
	private String aFinSolicitud;
	private String mFinSolicitud;
	private String dFinSolicitud;
	
	private String aFinPgp;
	private String mFinPgp;
	private String dFinPgp;
	
	private String aInicioMC;
	private String mInicioMC;
	private String dInicioMC;
	
	private String aFinMC;
	private String mFinMC;
	private String dFinMC;
	
	
	
	private String aInicioValoracionMC;
	private String mInicioValoracionMC;
	private String dInicioValoracionMC;
	
	private String aFinValoracionMC;
	private String mFinValoracionMC;
	private String dFinValoracionMC;
	
	private String aInicioValoracionCC;
	private String mInicioValoracionCC;
	private String dInicioValoracionCC;
	
	private String aFinValoracionCC;
	private String mFinValoracionCC;
	private String dFinValoracionCC;
	
	private String aInicioAlega;
	private String mInicioAlega;
	private String dInicioAlega;
	private String aInicioCC;
	private String mInicioCC;
	private String dInicioCC;
	private String aFinCC;
	private String mFinCC;
	private String dFinCC;
	
	private String aInicioCA;
	private String mInicioCA;
	private String dInicioCA;
	private String aFinCA;
	private String mFinCA;
	private String dFinCA;
	
	private String aInicioValCA;
	private String mInicioValCA;
	private String dInicioValCA;
	private String aFinValCA;
	private String mFinValCA;
	private String dFinValCA;
	
	private String aFinCp;
	private String mFinCp;
	private String dFinCp;
	private String fRecGrado;
	private String aRecGrado;
	private String mRecGrado;
	private String dRecGrado;
	private String bCierreSo;
	
	private String dAnioConvocatoria;

	public void setCConvocatoria_id(long cConvocatoria_id) {
		this.cConvocatoria_id = cConvocatoria_id;
	}

	public long getCConvocatoria_id() {
		return this.cConvocatoria_id;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}

	public String getDNombre() {
		return this.dNombre;
	}
	
	public void setDNombreCorto(String dNombreCorto) {
		this.dNombreCorto = dNombreCorto;
	}

	public String getDNombreCorto() {
		return this.dNombreCorto;
	}	

	public void setAObservaciones(String aObservaciones) {
		this.aObservaciones = aObservaciones;
	}

	public String getAObservaciones() {
		return this.aObservaciones;
	}

	public void setFCreacion(String fCreacion) {
		this.fCreacion = fCreacion;
	}

	public String getFCreacion() {
		return this.fCreacion;
	}

	public void setFModificacion(String fModificacion) {
		this.fModificacion = fModificacion;
	}

	public String getFModificacion() {
		return this.fModificacion;
	}

	public void setBBorrado(String bBorrado) {
		this.bBorrado = bBorrado;
	}

	public String getBBorrado() {
		return this.bBorrado;
	}

	public void setACreacion(String aCreacion) {
		this.aCreacion = aCreacion;
	}

	public String getACreacion() {
		return this.aCreacion;
	}

	public void setMCreacion(String mCreacion) {
		this.mCreacion = mCreacion;
	}

	public String getMCreacion() {
		return this.mCreacion;
	}

	public void setDCreacion(String dCreacion) {
		this.dCreacion = dCreacion;
	}

	public String getDCreacion() {
		return this.dCreacion;
	}

	public void setAModificacion(String aModificacion) {
		this.aModificacion = aModificacion;
	}

	public String getAModificacion() {
		return this.aModificacion;
	}

	public void setMModificacion(String mModificacion) {
		this.mModificacion = mModificacion;
	}

	public String getMModificacion() {
		return this.mModificacion;
	}

	public void setDModificacion(String dModificacion) {
		this.dModificacion = dModificacion;
	}

	public String getDModificacion() {
		return this.dModificacion;
	}

	public void setDGrado_nombre(String dGrado_nombre) {
		this.dGrado_nombre = dGrado_nombre;
	}

	public String getDGrado_nombre() {
		return this.dGrado_nombre;
	}

	public void setFResolucion(String fResolucion) {
		this.fResolucion = fResolucion;
	}

	public String getFResolucion() {
		return this.fResolucion;
	}

	public void setFPublicacion(String fPublicacion) {
		this.fPublicacion = fPublicacion;
	}

	public String getFPublicacion() {
		return this.fPublicacion;
	}

	public void setAResolucion(String aResolucion) {
		this.aResolucion = aResolucion;
	}

	public String getAResolucion() {
		return this.aResolucion;
	}

	public void setMResolucion(String mResolucion) {
		this.mResolucion = mResolucion;
	}

	public String getMResolucion() {
		return this.mResolucion;
	}

	public void setDResolucion(String dResolucion) {
		this.dResolucion = dResolucion;
	}

	public String getDResolucion() {
		return this.dResolucion;
	}

	public void setAPublicacion(String aPublicacion) {
		this.aPublicacion = aPublicacion;
	}

	public String getAPublicacion() {
		return this.aPublicacion;
	}

	public void setMPublicacion(String mPublicacion) {
		this.mPublicacion = mPublicacion;
	}

	public String getMPublicacion() {
		return this.mPublicacion;
	}

	public void setDPublicacion(String dPublicacion) {
		this.dPublicacion = dPublicacion;
	}

	public String getDPublicacion() {
		return this.dPublicacion;
	}

	public void setNDias_regsolicitud(long nDias_regsolicitud) {
		this.nDias_regsolicitud = nDias_regsolicitud;
	}

	public long getNDias_regsolicitud() {
		return this.nDias_regsolicitud;
	}

	public void setNDias_revsolicitud(long nDias_revsolicitud) {
		this.nDias_revsolicitud = nDias_revsolicitud;
	}

	public long getNDias_revsolicitud() {
		return this.nDias_revsolicitud;
	}

	public void setNDias_publistado(long nDias_publistado) {
		this.nDias_publistado = nDias_publistado;
	}

	public long getNDias_publistado() {
		return this.nDias_publistado;
	}

	public void setNDias_auto_mc(long nDias_auto_mc) {
		this.nDias_auto_mc = nDias_auto_mc;
	}

	public long getNDias_auto_mc() {
		return this.nDias_auto_mc;
	}

	public void setNDias_val_mc(long nDias_val_mc) {
		this.nDias_val_mc = nDias_val_mc;
	}

	public long getNDias_val_mc() {
		return this.nDias_val_mc;
	}

	public void setNDias_inconf_mc(long nDias_inconf_mc) {
		this.nDias_inconf_mc = nDias_inconf_mc;
	}

	public long getNDias_inconf_mc() {
		return this.nDias_inconf_mc;
	}

	public void setNDias_auto_ca(long nDias_auto_ca) {
		this.nDias_auto_ca = nDias_auto_ca;
	}

	public long getNDias_auto_ca() {
		return this.nDias_auto_ca;
	}

	public void setNDias_val_ca(long nDias_val_ca) {
		this.nDias_val_ca = nDias_val_ca;
	}

	public long getNDias_val_ca() {
		return this.nDias_val_ca;
	}

	public void setNDias_inconf_ca(long nDias_inconf_ca) {
		this.nDias_inconf_ca = nDias_inconf_ca;
	}

	public long getNDias_inconf_ca() {
		return this.nDias_inconf_ca;
	}

	public void setNDias_val_cc(long nDias_val_cc) {
		this.nDias_val_cc = nDias_val_cc;
	}

	public long getNDias_val_cc() {
		return this.nDias_val_cc;
	}

	public void setNDias_respinconf_mc(long nDias_respinconf_mc) {
		this.nDias_respinconf_mc = nDias_respinconf_mc;
	}

	public long getNDias_respinconf_mc() {
		return this.nDias_respinconf_mc;
	}

	public void setNDias_respinconf_ca(long nDias_respinconf_ca) {
		this.nDias_respinconf_ca = nDias_respinconf_ca;
	}

	public long getNDias_respinconf_ca() {
		return this.nDias_respinconf_ca;
	}

	public void setNDias_respinconf_cc(long nDias_respinconf_cc) {
		this.nDias_respinconf_cc = nDias_respinconf_cc;
	}

	public long getNDias_respinconf_cc() {
		return this.nDias_respinconf_cc;
	}

	public void setAInicioMC(String aInicioMC) {
		this.aInicioMC = aInicioMC;
	}

	public String getAInicioMC() {
		return this.aInicioMC;
	}

	public void setMInicioMC(String mInicioMC) {
		this.mInicioMC = mInicioMC;
	}

	public String getMInicioMC() {
		return this.mInicioMC;
	}

	public void setDInicioMC(String dInicioMC) {
		this.dInicioMC = dInicioMC;
	}

	public String getDInicioMC() {
		return this.dInicioMC;
	}

	public void setFInicioMC(String fInicioMC) {
		this.fInicioMC = fInicioMC;
	}

	public String getFInicioMC() {
		return this.fInicioMC;
	}

	public void setFInicioAlega(String fInicioAlega) {
		this.fInicioAlega = fInicioAlega;
	}

	public String getFInicioAlega() {
		return this.fInicioAlega;
	}

	public void setAInicioAlega(String aInicioAlega) {
		this.aInicioAlega = aInicioAlega;
	}

	public String getAInicioAlega() {
		return this.aInicioAlega;
	}

	public void setMInicioAlega(String mInicioAlega) {
		this.mInicioAlega = mInicioAlega;
	}

	public String getMInicioAlega() {
		return this.mInicioAlega;
	}

	public void setDInicioAlega(String dInicioAlega) {
		this.dInicioAlega = dInicioAlega;
	}

	public String getDInicioAlega() {
		return this.dInicioAlega;
	}

	public void setFInicioEstudioCC(String fInicioEstudioCC) {
		this.fInicioEstudioCC = fInicioEstudioCC;
	}

	public String getFInicioEstudioCC() {
		return this.fInicioEstudioCC;
	}

	public void setAInicioCC(String aInicioCC) {
		this.aInicioCC = aInicioCC;
	}

	public String getAInicioCC() {
		return this.aInicioCC;
	}

	public void setMInicioCC(String mInicioCC) {
		this.mInicioCC = mInicioCC;
	}

	public String getMInicioCC() {
		return this.mInicioCC;
	}

	public void setDInicioCC(String dInicioCC) {
		this.dInicioCC = dInicioCC;
	}

	public String getDInicioCC() {
		return this.dInicioCC;
	}

	public void setAFinCp(String aFinCp) {
		this.aFinCp = aFinCp;
	}

	public String getAFinCp() {
		return this.aFinCp;
	}

	public void setMFinCp(String mFinCp) {
		this.mFinCp = mFinCp;
	}

	public String getMFinCp() {
		return this.mFinCp;
	}

	public void setDFinCp(String dFinCp) {
		this.dFinCp = dFinCp;
	}

	public String getDFinCp() {
		return this.dFinCp;
	}

	public void setFFinCp(String fFinCp) {
		this.fFinCp = fFinCp;
	}

	public String getFFinCp() {
		return this.fFinCp;
	}

	public void setFInicioCA(String fInicioCA) {
		this.fInicioCA = fInicioCA;
	}

	public String getFInicioCA() {
		return this.fInicioCA;
	}

	public void setAInicioCA(String aInicioCA) {
		this.aInicioCA = aInicioCA;
	}

	public String getAInicioCA() {
		return this.aInicioCA;
	}

	public void setMInicioCA(String mInicioCA) {
		this.mInicioCA = mInicioCA;
	}

	public String getMInicioCA() {
		return this.mInicioCA;
	}

	public void setDInicioCA(String dInicioCA) {
		this.dInicioCA = dInicioCA;
	}

	public String getDInicioCA() {
		return this.dInicioCA;
	}

	public void setBCierreSo(String bCierreSo) {
		this.bCierreSo = bCierreSo;
	}

	public String getBCierreSo() {
		return this.bCierreSo;
	}

	public void setARecGrado(String aRecGrado) {
		this.aRecGrado = aRecGrado;
	}

	public String getARecGrado() {
		return this.aRecGrado;
	}

	public void setMRecGrado(String mRecGrado) {
		this.mRecGrado = mRecGrado;
	}

	public String getMRecGrado() {
		return this.mRecGrado;
	}

	public void setDRecGrado(String dRecGrado) {
		this.dRecGrado = dRecGrado;
	}

	public String getDRecGrado() {
		return this.dRecGrado;
	}

	public void setFRecGrado(String fRecGrado) {
		this.fRecGrado = fRecGrado;
	}

	public String getFRecGrado() {
		return this.fRecGrado;
	}

	public void setFFinEstudioCC(String fFinEstudioCC) {
		this.fFinEstudioCC = fFinEstudioCC;
	}

	public String getFFinEstudioCC() {
		return this.fFinEstudioCC;
	}

	public void setAFinCC(String aFinCC) {
		this.aFinCC = aFinCC;
	}

	public String getAFinCC() {
		return this.aFinCC;
	}

	public void setMFinCC(String mFinCC) {
		this.mFinCC = mFinCC;
	}

	public String getMFinCC() {
		return this.mFinCC;
	}

	public void setDFinCC(String dFinCC) {
		this.dFinCC = dFinCC;
	}

	public String getDFinCC() {
		return this.dFinCC;
	}

	public String getFInicioSolicitud() {
		return fInicioSolicitud;
	}

	public void setFInicioSolicitud(String fInicioSolicitud) {
		this.fInicioSolicitud = fInicioSolicitud;
	}

	public String getFFinSolicitud() {
		return fFinSolicitud;
	}

	public void setFFinSolicitud(String fFinSolicitud) {
		this.fFinSolicitud = fFinSolicitud;
	}

	public String getAInicioSolicitud() {
		return aInicioSolicitud;
	}

	public void setAInicioSolicitud(String aInicioSolicitud) {
		this.aInicioSolicitud = aInicioSolicitud;
	}

	public String getMInicioSolicitud() {
		return mInicioSolicitud;
	}

	public void setMInicioSolicitud(String mInicioSolicitud) {
		this.mInicioSolicitud = mInicioSolicitud;
	}

	public String getDInicioSolicitud() {
		return dInicioSolicitud;
	}

	public void setDInicioSolicitud(String dInicioSolicitud) {
		this.dInicioSolicitud = dInicioSolicitud;
	}

	public String getAFinSolicitud() {
		return aFinSolicitud;
	}

	public void setAFinSolicitud(String aFinSolicitud) {
		this.aFinSolicitud = aFinSolicitud;
	}

	public String getMFinSolicitud() {
		return mFinSolicitud;
	}

	public void setMFinSolicitud(String mFinSolicitud) {
		this.mFinSolicitud = mFinSolicitud;
	}

	public String getDFinSolicitud() {
		return dFinSolicitud;
	}

	public void setDFinSolicitud(String dFinSolicitud) {
		this.dFinSolicitud = dFinSolicitud;
	}

	public String getFFinMC() {
		return fFinMC;
	}

	public void setFFinMC(String fFinMC) {
		this.fFinMC = fFinMC;
	}

	public String getAFinMC() {
		return aFinMC;
	}

	public void setAFinMC(String aFinMC) {
		this.aFinMC = aFinMC;
	}

	public String getMFinMC() {
		return mFinMC;
	}

	public void setMFinMC(String mFinMC) {
		this.mFinMC = mFinMC;
	}

	public String getDFinMC() {
		return dFinMC;
	}

	public void setDFinMC(String dFinMC) {
		this.dFinMC = dFinMC;
	}

	public String getFInicioValoracionMC() {
		return fInicioValoracionMC;
	}

	public void setFInicioValoracionMC(String fInicioValoracionMC) {
		this.fInicioValoracionMC = fInicioValoracionMC;
	}

	public String getFFinValoracionMC() {
		return fFinValoracionMC;
	}

	public void setFFinValoracionMC(String fFinValoracionMC) {
		this.fFinValoracionMC = fFinValoracionMC;
	}

	public String getAInicioValoracionMC() {
		return aInicioValoracionMC;
	}

	public void setAInicioValoracionMC(String aInicioValoracionMC) {
		this.aInicioValoracionMC = aInicioValoracionMC;
	}

	public String getMInicioValoracionMC() {
		return mInicioValoracionMC;
	}

	public void setMInicioValoracionMC(String mInicioValoracionMC) {
		this.mInicioValoracionMC = mInicioValoracionMC;
	}

	public String getDInicioValoracionMC() {
		return dInicioValoracionMC;
	}

	public void setDInicioValoracionMC(String dInicioValoracionMC) {
		this.dInicioValoracionMC = dInicioValoracionMC;
	}

	public String getAFinValoracionMC() {
		return aFinValoracionMC;
	}

	public void setAFinValoracionMC(String aFinValoracionMC) {
		this.aFinValoracionMC = aFinValoracionMC;
	}

	public String getMFinValoracionMC() {
		return mFinValoracionMC;
	}

	public void setMFinValoracionMC(String mFinValoracionMC) {
		this.mFinValoracionMC = mFinValoracionMC;
	}

	public String getDFinValoracionMC() {
		return dFinValoracionMC;
	}

	public void setDFinValoracionMC(String dFinValoracionMC) {
		this.dFinValoracionMC = dFinValoracionMC;
	}

	public String getAInicioValoracionCC() {
		return aInicioValoracionCC;
	}

	public void setAInicioValoracionCC(String aInicioValoracionCC) {
		this.aInicioValoracionCC = aInicioValoracionCC;
	}

	public String getMInicioValoracionCC() {
		return mInicioValoracionCC;
	}

	public void setMInicioValoracionCC(String mInicioValoracionCC) {
		this.mInicioValoracionCC = mInicioValoracionCC;
	}

	public String getDInicioValoracionCC() {
		return dInicioValoracionCC;
	}

	public void setDInicioValoracionCC(String dInicioValoracionCC) {
		this.dInicioValoracionCC = dInicioValoracionCC;
	}

	public String getAFinValoracionCC() {
		return aFinValoracionCC;
	}

	public void setAFinValoracionCC(String aFinValoracionCC) {
		this.aFinValoracionCC = aFinValoracionCC;
	}

	public String getMFinValoracionCC() {
		return mFinValoracionCC;
	}

	public void setMFinValoracionCC(String mFinValoracionCC) {
		this.mFinValoracionCC = mFinValoracionCC;
	}

	public String getDFinValoracionCC() {
		return dFinValoracionCC;
	}

	public void setDFinValoracionCC(String dFinValoracionCC) {
		this.dFinValoracionCC = dFinValoracionCC;
	}

	public String getFInicioValoracionCC() {
		return fInicioValoracionCC;
	}

	public void setFInicioValoracionCC(String fInicioValoracionCC) {
		this.fInicioValoracionCC = fInicioValoracionCC;
	}

	public String getFFinValoracionCC() {
		return fFinValoracionCC;
	}

	public void setFFinValoracionCC(String fFinValoracionCC) {
		this.fFinValoracionCC = fFinValoracionCC;
	}

	public String getFFinCA() {
		return fFinCA;
	}

	public void setFFinCA(String fFinCA) {
		this.fFinCA = fFinCA;
	}

	public String getAFinCA() {
		return aFinCA;
	}

	public void setAFinCA(String aFinCA) {
		this.aFinCA = aFinCA;
	}

	public String getMFinCA() {
		return mFinCA;
	}

	public void setMFinCA(String mFinCA) {
		this.mFinCA = mFinCA;
	}

	public String getDFinCA() {
		return dFinCA;
	}

	public void setDFinCA(String dFinCA) {
		this.dFinCA = dFinCA;
	}

	public String getAInicioValCA() {
		return aInicioValCA;
	}

	public void setAInicioValCA(String aInicioValCA) {
		this.aInicioValCA = aInicioValCA;
	}

	public String getMInicioValCA() {
		return mInicioValCA;
	}

	public void setMInicioValCA(String mInicioValCA) {
		this.mInicioValCA = mInicioValCA;
	}

	public String getDInicioValCA() {
		return dInicioValCA;
	}

	public void setDInicioValCA(String dInicioValCA) {
		this.dInicioValCA = dInicioValCA;
	}

	public String getAFinValCA() {
		return aFinValCA;
	}

	public void setAFinValCA(String aFinValCA) {
		this.aFinValCA = aFinValCA;
	}

	public String getMFinValCA() {
		return mFinValCA;
	}

	public void setMFinValCA(String mFinValCA) {
		this.mFinValCA = mFinValCA;
	}

	public String getDFinValCA() {
		return dFinValCA;
	}

	public void setDFinValCA(String dFinValCA) {
		this.dFinValCA = dFinValCA;
	}

	public String getFInicioValCA() {
		return fInicioValCA;
	}

	public void setFInicioValCA(String fInicioValCA) {
		this.fInicioValCA = fInicioValCA;
	}

	public String getFFinValCA() {
		return fFinValCA;
	}

	public void setFFinValCA(String fFinValCA) {
		this.fFinValCA = fFinValCA;
	}
	public String getDAnioConvocatoria() {
		return dAnioConvocatoria;
	}

	public void setDAnioConvocatoria(String dAnioConvocatoria) {
		this.dAnioConvocatoria = dAnioConvocatoria;
	}

	public String getCGrado_id() {
		return cGrado_id;
	}

	public void setCGrado_id(String cGrado_id) {
		this.cGrado_id = cGrado_id;
	}

	public String getFFinPgp() {
		return fFinPgp;
	}

	public void setFFinPgp(String fFinPgp) {
		this.fFinPgp = fFinPgp;
	}

	public String getAFinPgp() {
		return aFinPgp;
	}

	public void setAFinPgp(String aFinPgp) {
		this.aFinPgp = aFinPgp;
	}

	public String getMFinPgp() {
		return mFinPgp;
	}

	public void setMFinPgp(String mFinPgp) {
		this.mFinPgp = mFinPgp;
	}

	public String getDFinPgp() {
		return dFinPgp;
	}

	public void setDFinPgp(String dFinPgp) {
		this.dFinPgp = dFinPgp;
	}

}

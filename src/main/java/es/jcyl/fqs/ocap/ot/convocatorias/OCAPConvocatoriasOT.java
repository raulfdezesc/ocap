package es.jcyl.fqs.ocap.ot.convocatorias;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

import es.jcyl.fqs.ocap.ot.especialidades.OCAPEspecialidadesOT;

public class OCAPConvocatoriasOT implements Serializable, Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3777690825780721182L;

	protected long cConvocatoriaId;
	protected Long cGradoId;
	protected String dNombre;
	protected String dNombreCorto;
	protected String fResolucion;
	protected String fPublicacion;
	protected String fInicioMC;
	protected String fInicioEstudioCC;
	protected String fFinEstudioCC;
	protected String fAlegsolicitud;
	protected String fInicioCA;
	protected String fFinCp;
	protected long nDiasRegsolicitud;
	protected long nDiasRevsolicitud;
	protected long nDiasPublistado;
	protected long nDiasAutoMc;
	protected long nDiasValMc;
	protected long nDiasInconfMc;
	protected long nDiasAutoCa;
	protected long nDiasValCa;
	protected long nDiasInconfCa;
	protected long nDiasValCc;
	protected String aObservaciones;
	protected String fCreacion;
	protected String fModificacion;
	protected String bBorrado;
	protected String dGradoNombre;
	protected long nDiasRespinconfMc;
	protected long nDiasRespinconfCa;
	protected long nDiasRespinconfCc;
	protected String aCreadoPor;
	protected String bCierreSo;
	protected String fRecGrado;

	protected String fInicioSolicitud;
	protected String fFinSolicitud;
	protected String fInicioValoracionMC;
	protected String fFinValoracionMC;
	protected String fInicioValoracionCC;
	protected String fFinValoracionCC;
	protected String fFinMC;
	protected String fFinCA;
	protected String fInicioValCA;
	protected String fFinValCA;
	protected String fFinPGP;
	
	protected String dAnioConvocatoria;

	public String getDAnioConvocatoria() {
		return dAnioConvocatoria;
	}

	public void setDAnioConvocatoria(String dAnioConvocatoria) {
		this.dAnioConvocatoria = dAnioConvocatoria;
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

	public String getFFinMC() {
		return fFinMC;
	}

	public void setFFinMC(String fFinMC) {
		this.fFinMC = fFinMC;
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

	public long getCConvocatoriaId() {
		return this.cConvocatoriaId;
	}

	public void setCConvocatoriaId(long cConvocatoriaId) {
		this.cConvocatoriaId = cConvocatoriaId;
	}

	public String getBBorrado() {
		return this.bBorrado;
	}

	public void setBBorrado(String bBorrado) {
		this.bBorrado = bBorrado;
	}

	public String getAObservaciones() {
		return this.aObservaciones;
	}

	public void setAObservaciones(String aObservaciones) {
		this.aObservaciones = aObservaciones;
	}

	public String getDNombre() {
		return this.dNombre;
	}

	public void setDNombre(String dNombre) {
		this.dNombre = dNombre;
	}
	
	public String getDNombreCorto() {
		return this.dNombreCorto;
	}

	public void setDNombreCorto(String dNombreCorto) {
		this.dNombreCorto = dNombreCorto;
	}	

	public Long getCGradoId() {
		return this.cGradoId;
	}

	public void setCGradoId(Long cGradoId) {
		this.cGradoId = cGradoId;
	}

	public String getFModificacion() {
		return this.fModificacion;
	}

	public void setFModificacion(String fModificacion) {
		this.fModificacion = fModificacion;
	}

	public String getFCreacion() {
		return this.fCreacion;
	}

	public void setFCreacion(String fCreacion) {
		this.fCreacion = fCreacion;
	}

	public void setDGradoNombre(String dGradoNombre) {
		this.dGradoNombre = dGradoNombre;
	}

	public String getDGradoNombre() {
		return this.dGradoNombre;
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

	public void setNDiasRegsolicitud(long nDiasRegsolicitud) {
		this.nDiasRegsolicitud = nDiasRegsolicitud;
	}

	public long getNDiasRegsolicitud() {
		return this.nDiasRegsolicitud;
	}

	public void setNDiasRevsolicitud(long nDiasRevsolicitud) {
		this.nDiasRevsolicitud = nDiasRevsolicitud;
	}

	public long getNDiasRevsolicitud() {
		return this.nDiasRevsolicitud;
	}

	public void setNDiasPublistado(long nDiasPublistado) {
		this.nDiasPublistado = nDiasPublistado;
	}

	public long getNDiasPublistado() {
		return this.nDiasPublistado;
	}

	public void setNDiasAutoMc(long nDiasAutoMc) {
		this.nDiasAutoMc = nDiasAutoMc;
	}

	public long getNDiasAutoMc() {
		return this.nDiasAutoMc;
	}

	public void setNDiasValMc(long nDiasValMc) {
		this.nDiasValMc = nDiasValMc;
	}

	public long getNDiasValMc() {
		return this.nDiasValMc;
	}

	public void setNDiasInconfMc(long nDiasInconfMc) {
		this.nDiasInconfMc = nDiasInconfMc;
	}

	public long getNDiasInconfMc() {
		return this.nDiasInconfMc;
	}

	public void setNDiasAutoCa(long nDiasAutoCa) {
		this.nDiasAutoCa = nDiasAutoCa;
	}

	public long getNDiasAutoCa() {
		return this.nDiasAutoCa;
	}

	public void setNDiasValCa(long nDiasValCa) {
		this.nDiasValCa = nDiasValCa;
	}

	public long getNDiasValCa() {
		return this.nDiasValCa;
	}

	public void setNDiasInconfCa(long nDiasInconfCa) {
		this.nDiasInconfCa = nDiasInconfCa;
	}

	public long getNDiasInconfCa() {
		return this.nDiasInconfCa;
	}

	public void setNDiasValCc(long nDiasValCc) {
		this.nDiasValCc = nDiasValCc;
	}

	public long getNDiasValCc() {
		return this.nDiasValCc;
	}

	public void setNDiasRespinconfMc(long nDiasRespinconfMc) {
		this.nDiasRespinconfMc = nDiasRespinconfMc;
	}

	public long getNDiasRespinconfMc() {
		return this.nDiasRespinconfMc;
	}

	public void setNDiasRespinconfCa(long nDiasRespinconfCa) {
		this.nDiasRespinconfCa = nDiasRespinconfCa;
	}

	public long getNDiasRespinconfCa() {
		return this.nDiasRespinconfCa;
	}

	public void setNDiasRespinconfCc(long nDiasRespinconfCc) {
		this.nDiasRespinconfCc = nDiasRespinconfCc;
	}

	public long getNDiasRespinconfCc() {
		return this.nDiasRespinconfCc;
	}

	public void setACreadoPor(String aCreadoPor) {
		this.aCreadoPor = aCreadoPor;
	}

	public String getACreadoPor() {
		return this.aCreadoPor;
	}

	public void setFInicioMC(String fInicioMC) {
		this.fInicioMC = fInicioMC;
	}

	public String getFInicioMC() {
		return this.fInicioMC;
	}

	public void setFAlegsolicitud(String fAlegsolicitud) {
		this.fAlegsolicitud = fAlegsolicitud;
	}

	public String getFAlegsolicitud() {
		return this.fAlegsolicitud;
	}

	public void setFInicioEstudioCC(String fInicioEstudioCC) {
		this.fInicioEstudioCC = fInicioEstudioCC;
	}

	public String getFInicioEstudioCC() {
		return this.fInicioEstudioCC;
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

	public void setBCierreSo(String bCierreSo) {
		this.bCierreSo = bCierreSo;
	}

	public String getBCierreSo() {
		return this.bCierreSo;
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

	/**
	 * Ordenacion alfabética
	 */
	@Override
	public int compareTo(Object o) {
		try {
			Collator comparador = Collator.getInstance(new Locale("es"));
			comparador.setStrength(Collator.PRIMARY);

			if (o != null && ((OCAPConvocatoriasOT) o).getDNombre() != null && this.getDNombre() != null) {
				return comparador.compare(this.getDNombre(), ((OCAPConvocatoriasOT) o).getDNombre());
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 1;
		}
	}

	public String getFFinPGP() {
		return fFinPGP;
	}

	public void setFFinPGP(String fFinPGP) {
		this.fFinPGP = fFinPGP;
	}

}

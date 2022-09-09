 package es.jcyl.fqs.ocap.ot.encuestaCalidad;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 
 public class OCAPEncuestaCalidadOT
   implements Serializable
 {
   protected long cEncuestaId;
   protected long cPreguntaId;
   protected long cRespuestaId;
   protected String dRespuesta;
   protected long cPreguntaPadreId;
   protected String dPregunta;
   protected long numSubPreguntas;
   protected long cAreaId;
   protected String dArea;
   protected String aObservaciones;
   protected long cTipoRespuesta;
   protected float nPonderacion;
   protected float nPonderacionRespuesta;
   protected long cExpId;
   protected String bBorrado;
   protected String aCreadoPor;
   protected String aModificadoPor;
   protected String bEscaneado;
   protected String nDocumentos;
   protected int total1;
   protected int total2;
   protected int total3;
   protected int total4;
   protected int total5;
   protected int total6;
   protected int total7;
   protected int total8;
   protected int total9;
   protected int total10;
   protected int total11;
   protected int totalS;
   protected int totalN;
   protected int totalRespuestas;
   protected float porc1;
   protected float porc2;
   protected float porc3;
   protected float porc4;
   protected float porc5;
   protected float porc6;
   protected float porc7;
   protected float porc8;
   protected float porc9;
   protected float porc10;
   protected float porc11;
   protected String dItinerario;
   protected String respuesta_0;
   protected String respuesta_1;
   protected String respuesta_2;
   protected String respuesta_3;
   protected String respuesta_4;
   protected String respuesta_5;
   protected String respuesta_6;
   protected String respuesta_7;
   protected String respuesta_8;
   protected String respuesta_9;
   protected String respuesta_10;
   protected String respuesta_11;
   protected String respuesta_12;
   protected String respuesta_13;
   protected String respuesta_14;
   protected String respuesta_15;
   protected String respuesta_16;
   protected String respuesta_17;
   protected String respuesta_18;
   protected String respuesta_18_no;
   protected String respuesta_19;
   protected String respuesta_20;
   protected String respuesta_21;
   protected String respuesta_22;
   protected String respuesta_23;
   protected String respuesta_24;
   protected String respuesta_24_no;
   protected String respuesta_25;
   protected String respuesta_26;
   protected long cItinerario_id;
   protected ArrayList listaPreguntas;
   protected ArrayList listaRespuestas;
   protected long cProfesional_id;
   protected long cEspec_id;
   protected long cExpDossierId;
   protected long nEvidencia;
   protected String bEntregado;
 
   public void setCEncuestaId(long cEncuestaId)
   {
     this.cEncuestaId = cEncuestaId;
   }
 
   public long getCEncuestaId()
   {
     return this.cEncuestaId;
   }
 
   public void setDRespuesta(String dRespuesta)
   {
     this.dRespuesta = dRespuesta;
   }
 
   public String getDRespuesta()
   {
     return this.dRespuesta;
   }
 
   public void setCPreguntaId(long cPreguntaId)
   {
     this.cPreguntaId = cPreguntaId;
   }
 
   public long getCPreguntaId()
   {
     return this.cPreguntaId;
   }
 
   public void setCExpId(long cExpId)
   {
     this.cExpId = cExpId;
   }
 
   public long getCExpId()
   {
     return this.cExpId;
   }
 
   public void setBBorrado(String bBorrado)
   {
     this.bBorrado = bBorrado;
   }
 
   public String getBBorrado()
   {
     return this.bBorrado;
   }
 
   public void setACreadoPor(String aCreadoPor)
   {
     this.aCreadoPor = aCreadoPor;
   }
 
   public String getACreadoPor()
   {
     return this.aCreadoPor;
   }
 
   public void setAModificadoPor(String aModificadoPor)
   {
     this.aModificadoPor = aModificadoPor;
   }
 
   public String getAModificadoPor()
   {
     return this.aModificadoPor;
   }
 
   public void setBEscaneado(String bEscaneado)
   {
     this.bEscaneado = bEscaneado;
   }
 
   public String getBEscaneado()
   {
     return this.bEscaneado;
   }
 
   public void setNDocumentos(String nDocumentos)
   {
     this.nDocumentos = nDocumentos;
   }
 
   public String getNDocumentos()
   {
     return this.nDocumentos;
   }
 
   public void setTotal1(int total1)
   {
     this.total1 = total1;
   }
 
   public int getTotal1()
   {
     return this.total1;
   }
 
   public void setTotal2(int total2)
   {
     this.total2 = total2;
   }
 
   public int getTotal2()
   {
     return this.total2;
   }
 
   public void setTotal3(int total3)
   {
     this.total3 = total3;
   }
 
   public int getTotal3()
   {
     return this.total3;
   }
 
   public void setTotal4(int total4)
   {
     this.total4 = total4;
   }
 
   public int getTotal4()
   {
     return this.total4;
   }
 
   public void setTotal5(int total5)
   {
     this.total5 = total5;
   }
 
   public int getTotal5()
   {
     return this.total5;
   }
 
   public void setTotalS(int totalS)
   {
     this.totalS = totalS;
   }
 
   public int getTotalS()
   {
     return this.totalS;
   }
 
   public void setTotalN(int totalN)
   {
     this.totalN = totalN;
   }
 
   public int getTotalN()
   {
     return this.totalN;
   }
 
   public void setDItinerario(String dItinerario)
   {
     this.dItinerario = dItinerario;
   }
 
   public String getDItinerario()
   {
     return this.dItinerario;
   }
 
   public void setCItinerario_id(long cItinerario_id)
   {
     this.cItinerario_id = cItinerario_id;
   }
 
   public long getCItinerario_id()
   {
     return this.cItinerario_id;
   }
 
   public void setListaPreguntas(ArrayList listaPreguntas)
   {
     this.listaPreguntas = listaPreguntas;
   }
 
   public ArrayList getListaPreguntas()
   {
     return this.listaPreguntas;
   }
 
   public void setTotalRespuestas(int totalRespuestas)
   {
     this.totalRespuestas = totalRespuestas;
   }
 
   public int getTotalRespuestas()
   {
     return this.totalRespuestas;
   }
 
   public void setPorc1(float porc1)
   {
     this.porc1 = porc1;
   }
 
   public float getPorc1()
   {
     return this.porc1;
   }
 
   public void setPorc2(float porc2)
   {
     this.porc2 = porc2;
   }
 
   public float getPorc2()
   {
     return this.porc2;
   }
 
   public void setPorc3(float porc3)
   {
     this.porc3 = porc3;
   }
 
   public float getPorc3()
   {
     return this.porc3;
   }
 
   public void setPorc4(float porc4)
   {
     this.porc4 = porc4;
   }
 
   public float getPorc4()
   {
     return this.porc4;
   }
 
   public void setPorc5(float porc5)
   {
     this.porc5 = porc5;
   }
 
   public float getPorc5()
   {
     return this.porc5;
   }
 
   public void setTotal6(int total6)
   {
     this.total6 = total6;
   }
 
   public int getTotal6()
   {
     return this.total6;
   }
 
   public void setTotal7(int total7)
   {
     this.total7 = total7;
   }
 
   public int getTotal7()
   {
     return this.total7;
   }
 
   public void setTotal8(int total8)
   {
     this.total8 = total8;
   }
 
   public int getTotal8()
   {
     return this.total8;
   }
 
   public void setTotal9(int total9)
   {
     this.total9 = total9;
   }
 
   public int getTotal9()
   {
     return this.total9;
   }
 
   public void setTotal10(int total10)
   {
     this.total10 = total10;
   }
 
   public int getTotal10()
   {
     return this.total10;
   }
 
   public void setTotal11(int total11)
   {
     this.total11 = total11;
   }
 
   public int getTotal11()
   {
     return this.total11;
   }
 
   public void setPorc6(float porc6)
   {
     this.porc6 = porc6;
   }
 
   public float getPorc6()
   {
     return this.porc6;
   }
 
   public void setPorc7(float porc7)
   {
     this.porc7 = porc7;
   }
 
   public float getPorc7()
   {
     return this.porc7;
   }
 
   public void setPorc8(float porc8)
   {
     this.porc8 = porc8;
   }
 
   public float getPorc8()
   {
     return this.porc8;
   }
 
   public void setPorc9(float porc9)
   {
     this.porc9 = porc9;
   }
 
   public float getPorc9()
   {
     return this.porc9;
   }
 
   public void setPorc10(float porc10)
   {
     this.porc10 = porc10;
   }
 
   public float getPorc10()
   {
     return this.porc10;
   }
 
   public void setPorc11(float porc11)
   {
     this.porc11 = porc11;
   }
 
   public float getPorc11()
   {
     return this.porc11;
   }
 
   public void setCProfesional_id(long cProfesional_id)
   {
     this.cProfesional_id = cProfesional_id;
   }
 
   public long getCProfesional_id()
   {
     return this.cProfesional_id;
   }
 
   public void setCEspec_id(long cEspec_id)
   {
     this.cEspec_id = cEspec_id;
   }
 
   public long getCEspec_id()
   {
     return this.cEspec_id;
   }
 
   public void setCAreaId(long cAreaId)
   {
     this.cAreaId = cAreaId;
   }
 
   public long getCAreaId()
   {
     return this.cAreaId;
   }
 
   public void setAObservaciones(String aObservaciones)
   {
     this.aObservaciones = aObservaciones;
   }
 
   public String getAObservaciones()
   {
     return this.aObservaciones;
   }
 
   public void setCTipoRespuesta(long cTipoRespuesta)
   {
     this.cTipoRespuesta = cTipoRespuesta;
   }
 
   public long getCTipoRespuesta()
   {
     return this.cTipoRespuesta;
   }
 
   public void setNPonderacion(float nPonderacion)
   {
     this.nPonderacion = nPonderacion;
   }
 
   public float getNPonderacion()
   {
     return this.nPonderacion;
   }
 
   public void setCPreguntaPadreId(long cPreguntaPadreId)
   {
     this.cPreguntaPadreId = cPreguntaPadreId;
   }
 
   public long getCPreguntaPadreId()
   {
     return this.cPreguntaPadreId;
   }
 
   public void setDPregunta(String dPregunta)
   {
     this.dPregunta = dPregunta;
   }
 
   public String getDPregunta()
   {
     return this.dPregunta;
   }
 
   public void setDArea(String dArea)
   {
     this.dArea = dArea;
   }
 
   public String getDArea()
   {
     return this.dArea;
   }
 
   public void setNumSubPreguntas(long numSubPreguntas)
   {
     this.numSubPreguntas = numSubPreguntas;
   }
 
   public long getNumSubPreguntas()
   {
     return this.numSubPreguntas;
   }
 
   public void setCRespuestaId(long cRespuestaId)
   {
     this.cRespuestaId = cRespuestaId;
   }
 
   public long getCRespuestaId()
   {
     return this.cRespuestaId;
   }
 
   public void setNPonderacionRespuesta(float nPonderacionRespuesta)
   {
     this.nPonderacionRespuesta = nPonderacionRespuesta;
   }
 
   public float getNPonderacionRespuesta()
   {
     return this.nPonderacionRespuesta;
   }
 
   public void setListaRespuestas(ArrayList listaRespuestas)
   {
     this.listaRespuestas = listaRespuestas;
   }
 
   public ArrayList getListaRespuestas()
   {
     return this.listaRespuestas;
   }
 
   public void setNEvidencia(long nEvidencia)
   {
     this.nEvidencia = nEvidencia;
   }
 
   public long getNEvidencia()
   {
     return this.nEvidencia;
   }
 
   public void setCExpDossierId(long cExpDossierId)
   {
     this.cExpDossierId = cExpDossierId;
   }
 
   public long getCExpDossierId()
   {
     return this.cExpDossierId;
   }
 
   public void setBEntregado(String bEntregado)
   {
     this.bEntregado = bEntregado;
   }
 
   public String getBEntregado()
   {
     return this.bEntregado;
   }
 
   public void setRespuesta_0(String respuesta_0)
   {
     this.respuesta_0 = respuesta_0;
   }
 
   public String getRespuesta_0()
   {
     return this.respuesta_0;
   }
 
   public void setRespuesta_1(String respuesta_1)
   {
     this.respuesta_1 = respuesta_1;
   }
 
   public String getRespuesta_1()
   {
     return this.respuesta_1;
   }
 
   public void setRespuesta_2(String respuesta_2)
   {
     this.respuesta_2 = respuesta_2;
   }
 
   public String getRespuesta_2()
   {
     return this.respuesta_2;
   }
 
   public void setRespuesta_3(String respuesta_3)
   {
     this.respuesta_3 = respuesta_3;
   }
 
   public String getRespuesta_3()
   {
     return this.respuesta_3;
   }
 
   public void setRespuesta_4(String respuesta_4)
   {
     this.respuesta_4 = respuesta_4;
   }
 
   public String getRespuesta_4()
   {
     return this.respuesta_4;
   }
 
   public void setRespuesta_5(String respuesta_5)
   {
     this.respuesta_5 = respuesta_5;
   }
 
   public String getRespuesta_5()
   {
     return this.respuesta_5;
   }
 
   public void setRespuesta_6(String respuesta_6)
   {
     this.respuesta_6 = respuesta_6;
   }
 
   public String getRespuesta_6()
   {
     return this.respuesta_6;
   }
 
   public void setRespuesta_7(String respuesta_7)
   {
     this.respuesta_7 = respuesta_7;
   }
 
   public String getRespuesta_7()
   {
     return this.respuesta_7;
   }
 
   public void setRespuesta_8(String respuesta_8)
   {
     this.respuesta_8 = respuesta_8;
   }
 
   public String getRespuesta_8()
   {
     return this.respuesta_8;
   }
 
   public void setRespuesta_9(String respuesta_9)
   {
     this.respuesta_9 = respuesta_9;
   }
 
   public String getRespuesta_9()
   {
     return this.respuesta_9;
   }
 
   public void setRespuesta_10(String respuesta_10)
   {
     this.respuesta_10 = respuesta_10;
   }
 
   public String getRespuesta_10()
   {
     return this.respuesta_10;
   }
 
   public void setRespuesta_11(String respuesta_11)
   {
     this.respuesta_11 = respuesta_11;
   }
 
   public String getRespuesta_11()
   {
     return this.respuesta_11;
   }
 
   public void setRespuesta_12(String respuesta_12)
   {
     this.respuesta_12 = respuesta_12;
   }
 
   public String getRespuesta_12()
   {
     return this.respuesta_12;
   }
 
   public void setRespuesta_13(String respuesta_13)
   {
     this.respuesta_13 = respuesta_13;
   }
 
   public String getRespuesta_13()
   {
     return this.respuesta_13;
   }
 
   public void setRespuesta_14(String respuesta_14)
   {
     this.respuesta_14 = respuesta_14;
   }
 
   public String getRespuesta_14()
   {
     return this.respuesta_14;
   }
 
   public void setRespuesta_15(String respuesta_15)
   {
     this.respuesta_15 = respuesta_15;
   }
 
   public String getRespuesta_15()
   {
     return this.respuesta_15;
   }
 
   public void setRespuesta_16(String respuesta_16)
   {
     this.respuesta_16 = respuesta_16;
   }
 
   public String getRespuesta_16()
   {
     return this.respuesta_16;
   }
 
   public void setRespuesta_17(String respuesta_17)
   {
     this.respuesta_17 = respuesta_17;
   }
 
   public String getRespuesta_17()
   {
     return this.respuesta_17;
   }
 
   public void setRespuesta_18(String respuesta_18)
   {
     this.respuesta_18 = respuesta_18;
   }
 
   public String getRespuesta_18()
   {
     return this.respuesta_18;
   }
 
   public void setRespuesta_18_no(String respuesta_18_no)
   {
     this.respuesta_18_no = respuesta_18_no;
   }
 
   public String getRespuesta_18_no()
   {
     return this.respuesta_18_no;
   }
 
   public void setRespuesta_19(String respuesta_19)
   {
     this.respuesta_19 = respuesta_19;
   }
 
   public String getRespuesta_19()
   {
     return this.respuesta_19;
   }
 
   public void setRespuesta_20(String respuesta_20)
   {
     this.respuesta_20 = respuesta_20;
   }
 
   public String getRespuesta_20()
   {
     return this.respuesta_20;
   }
 
   public void setRespuesta_21(String respuesta_21)
   {
     this.respuesta_21 = respuesta_21;
   }
 
   public String getRespuesta_21()
   {
     return this.respuesta_21;
   }
 
   public void setRespuesta_22(String respuesta_22)
   {
     this.respuesta_22 = respuesta_22;
   }
 
   public String getRespuesta_22()
   {
     return this.respuesta_22;
   }
 
   public void setRespuesta_23(String respuesta_23)
   {
     this.respuesta_23 = respuesta_23;
   }
 
   public String getRespuesta_23()
   {
     return this.respuesta_23;
   }
 
   public void setRespuesta_24(String respuesta_24)
   {
     this.respuesta_24 = respuesta_24;
   }
 
   public String getRespuesta_24()
   {
     return this.respuesta_24;
   }
 
   public void setRespuesta_24_no(String respuesta_24_no)
   {
     this.respuesta_24_no = respuesta_24_no;
   }
 
   public String getRespuesta_24_no()
   {
     return this.respuesta_24_no;
   }
 
   public void setRespuesta_25(String respuesta_25)
   {
     this.respuesta_25 = respuesta_25;
   }
 
   public String getRespuesta_25()
   {
     return this.respuesta_25;
   }
 
   public void setRespuesta_26(String respuesta_26)
   {
     this.respuesta_26 = respuesta_26;
   }
 
   public String getRespuesta_26()
   {
     return this.respuesta_26;
   }
 }


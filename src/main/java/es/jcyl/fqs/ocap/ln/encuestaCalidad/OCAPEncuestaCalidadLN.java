 package es.jcyl.fqs.ocap.ln.encuestaCalidad;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.encuestaCalidad.OCAPEncuestaCalidadOAD;
 import es.jcyl.fqs.ocap.ot.encuestaCalidad.OCAPEncuestaCalidadOT;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
 import es.jcyl.framework.JCYLUsuario;
 import es.jcyl.framework.db.JCYLGestionTransacciones;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPEncuestaCalidadLN
 {
   OCAPEncuestaCalidadOAD variableOAD;
   private JCYLUsuario jcylUsuario;
   public Logger loggerBD;
 
   private void $init$()
   {
     this.loggerBD = OCAPConfigApp.loggerBD;
   }
 
   public void OCAPEncuestaCalidadLN()
   {
     this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
   }
   public OCAPEncuestaCalidadLN(JCYLUsuario jcylUsuario) {
     $init$();
     this.jcylUsuario = jcylUsuario;
   }
 
   public int rellenaOCAPEncuestaCalidad(OCAPEncuestaCalidadOT datos)
     throws SQLException, Exception
   {
     int result = 0;
     try {
       OCAPConfigApp.logger.info("Inicio");
       JCYLGestionTransacciones.open(false);
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       result = this.variableOAD.rellenaOCAPEncuestaCalidad(datos);
       JCYLGestionTransacciones.commit(true);
     } catch (SQLException exSQL) {
       JCYLGestionTransacciones.rollback();
       this.loggerBD.error("[ERROR-SQL] [[Estado:" + exSQL.getSQLState() + "][Código error: " + exSQL.getErrorCode() + "][Mensaje: " + exSQL.getMessage() + "]]");
       throw exSQL;
     } catch (Exception e) {
       JCYLGestionTransacciones.rollback();
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     } finally {
       JCYLGestionTransacciones.close(true);
     }
 
     return result;
   }
 
   public boolean estaRellena(long cExpId)
   {
     int result = 0;
     boolean rellena = false;
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       result = this.variableOAD.OCAPCuentaRespuestasEncuesta(cExpId);
       if (result > 0)
         rellena = true;
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
     }
 
     return rellena;
   }
 
   public void eliminaEncuesta(long cExpId, Connection con)
     throws Exception
   {
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       this.variableOAD.OCAPEliminaRespuestasEncuesta(cExpId, con);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public ArrayList buscarOCAPEncuesta(long cExpId)
     throws Exception
   {
     ArrayList listado = null;
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       listado = this.variableOAD.OCAPBuscaRespuestasEncuesta(cExpId);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 
   public ArrayList listarEstadisticas(OCAPEncuestaCalidadOT encOT)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoItinerarios = null;
     ArrayList listadoPreguntas = null;
     OCAPEncuestaCalidadOT datos = null;
     OCAPEncuestaCalidadOT pregunta = null;
     OCAPEncuestaCalidadOT itin = null;
     String itiAnt = "";
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       String porItinerario = null;
       if (encOT.getCItinerario_id() != 0L)
         porItinerario = Constantes.SI;
       if (encOT.getCProfesional_id() != 0L)
         porItinerario = Constantes.NO;
       long paramFiltro = encOT.getCItinerario_id() != 0L ? encOT.getCItinerario_id() : encOT.getCProfesional_id();
 
       array = this.variableOAD.listarEstadisticas(paramFiltro, porItinerario);
 
       listadoItinerarios = new ArrayList();
 
       if (paramFiltro == 0L) {
         itin = new OCAPEncuestaCalidadOT();
         itin.setDItinerario("");
         listadoPreguntas = new ArrayList();
         for (int i = 0; i < array.size(); i++) {
           datos = (OCAPEncuestaCalidadOT)array.get(i);
           if ((datos.getCPreguntaId() == 0L) && (Constantes.SI.equals(encOT.getRespuesta_0()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 1L) && (Constantes.SI.equals(encOT.getRespuesta_1()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 2) && (Constantes.SI.equals(encOT.getRespuesta_2()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 3) && (Constantes.SI.equals(encOT.getRespuesta_3()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 4) && (Constantes.SI.equals(encOT.getRespuesta_4()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 5) && (Constantes.SI.equals(encOT.getRespuesta_5()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 6) && (Constantes.SI.equals(encOT.getRespuesta_6()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 7) && (Constantes.SI.equals(encOT.getRespuesta_7()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 8) && (Constantes.SI.equals(encOT.getRespuesta_8()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 9) && (Constantes.SI.equals(encOT.getRespuesta_9()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 12) && (Constantes.SI.equals(encOT.getRespuesta_12()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 13) && (Constantes.SI.equals(encOT.getRespuesta_13()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 15) && (Constantes.SI.equals(encOT.getRespuesta_15()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 16) && (Constantes.SI.equals(encOT.getRespuesta_16()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 17) && (Constantes.SI.equals(encOT.getRespuesta_17()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 18) && (Constantes.SI.equals(encOT.getRespuesta_18()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 19) && (Constantes.SI.equals(encOT.getRespuesta_19()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 20) && (Constantes.SI.equals(encOT.getRespuesta_20()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 21) && (Constantes.SI.equals(encOT.getRespuesta_21()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 22) && (Constantes.SI.equals(encOT.getRespuesta_22()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 23) && (Constantes.SI.equals(encOT.getRespuesta_23()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 24) && (Constantes.SI.equals(encOT.getRespuesta_24()))) {
             listadoPreguntas.add(datos);
           }
           if ((datos.getCPreguntaId() == 25) && (Constantes.SI.equals(encOT.getRespuesta_25()))) {
             listadoPreguntas.add(datos);
           }
         }
         itin.setListaPreguntas(listadoPreguntas);
         listadoItinerarios.add(itin);
       }
       else {
         for (int i = 0; i < array.size(); i++) {
           datos = (OCAPEncuestaCalidadOT)array.get(i);
 
           pregunta = new OCAPEncuestaCalidadOT();
           pregunta.setCPreguntaId(datos.getCPreguntaId());
           pregunta.setTotal1(datos.getTotal1());
           pregunta.setTotal2(datos.getTotal2());
           pregunta.setTotal3(datos.getTotal3());
           pregunta.setTotal4(datos.getTotal4());
           pregunta.setTotal5(datos.getTotal5());
           pregunta.setTotalS(datos.getTotalS());
           pregunta.setTotalN(datos.getTotalN());
 
           if ((!"".equals(datos.getDItinerario())) && (!itiAnt.equals(datos.getDItinerario()))) {
             if ((listadoPreguntas != null) && (listadoPreguntas.size() > 0)) {
               itin = new OCAPEncuestaCalidadOT();
 
               itin.setListaPreguntas(listadoPreguntas);
               itin.setDItinerario(itiAnt);
 
               listadoItinerarios.add(itin);
             }
 
             listadoPreguntas = new ArrayList();
 
             itiAnt = datos.getDItinerario();
           }
 
           if ((datos.getCPreguntaId() == 0L) && (Constantes.SI.equals(encOT.getRespuesta_0()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 1L) && (Constantes.SI.equals(encOT.getRespuesta_1()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 2) && (Constantes.SI.equals(encOT.getRespuesta_2()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 3) && (Constantes.SI.equals(encOT.getRespuesta_3()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 4) && (Constantes.SI.equals(encOT.getRespuesta_4()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 5) && (Constantes.SI.equals(encOT.getRespuesta_5()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 6) && (Constantes.SI.equals(encOT.getRespuesta_6()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 7) && (Constantes.SI.equals(encOT.getRespuesta_7()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 8) && (Constantes.SI.equals(encOT.getRespuesta_8()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 9) && (Constantes.SI.equals(encOT.getRespuesta_9()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 12) && (Constantes.SI.equals(encOT.getRespuesta_12()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 13) && (Constantes.SI.equals(encOT.getRespuesta_13()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 15) && (Constantes.SI.equals(encOT.getRespuesta_15()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 16) && (Constantes.SI.equals(encOT.getRespuesta_16()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 17) && (Constantes.SI.equals(encOT.getRespuesta_17()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 18) && (Constantes.SI.equals(encOT.getRespuesta_18()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 19) && (Constantes.SI.equals(encOT.getRespuesta_19()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 20) && (Constantes.SI.equals(encOT.getRespuesta_20()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 21) && (Constantes.SI.equals(encOT.getRespuesta_21()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 22) && (Constantes.SI.equals(encOT.getRespuesta_22()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 23) && (Constantes.SI.equals(encOT.getRespuesta_23()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 24) && (Constantes.SI.equals(encOT.getRespuesta_24()))) {
             listadoPreguntas.add(pregunta);
           }
           if ((datos.getCPreguntaId() == 25) && (Constantes.SI.equals(encOT.getRespuesta_25()))) {
             listadoPreguntas.add(pregunta);
           }
         }
 
         if (array.size() > 0) {
           itin = new OCAPEncuestaCalidadOT();
 
           itin.setListaPreguntas(listadoPreguntas);
           itin.setDItinerario(itiAnt);
 
           listadoItinerarios.add(itin);
         }
       }
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listadoItinerarios;
   }
 
   public ArrayList listarPreguntas(long cItinerarioId, long pregId, String porItinerario)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoItinerarios = null;
     ArrayList listadoPreguntas = null;
     OCAPUsuariosOT datos = null;
     OCAPUsuariosOT pregunta = null;
     OCAPUsuariosOT itin = null;
     OCAPEncuestaCalidadOT horas = null;
     Utilidades utilidades = new Utilidades();
     String itiAnt = "";
     float valor = 0.0F;
     float cont1 = 0.0F;
     float cont2 = 0.0F;
     float cont3 = 0.0F;
     float cont4 = 0.0F;
     float cont5 = 0.0F;
     float cont6 = 0.0F;
     float cont7 = 0.0F;
     float cont8 = 0.0F;
     float cont9 = 0.0F;
     float cont10 = 0.0F;
     float cont11 = 0.0F;
     int contTot = 0;
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       array = this.variableOAD.listarPreguntas(cItinerarioId, pregId, porItinerario);
       listadoItinerarios = new ArrayList();
 
       if ((cItinerarioId != 0L) || (pregId == 11)) {
         for (int i = 0; i < array.size(); i++) {
           datos = (OCAPUsuariosOT)array.get(i);
 
           if (pregId != 11)
           {
             pregunta = new OCAPUsuariosOT();
             pregunta.setCodigoId(datos.getCodigoId());
             pregunta.setDNombre(datos.getDNombre());
             pregunta.setDApellido1(datos.getDApellido1());
 
             pregunta.setBContestado(datos.getBContestado());
 
             if ((!"".equals(datos.getDItinerario())) && (!itiAnt.equals(datos.getDItinerario()))) {
               if ((listadoPreguntas != null) && (listadoPreguntas.size() > 0)) {
                 itin = new OCAPUsuariosOT();
 
                 itin.setListaItinerarios(listadoPreguntas);
                 itin.setDItinerario(itiAnt);
 
                 listadoItinerarios.add(itin);
               }
 
               listadoPreguntas = new ArrayList();
 
               itiAnt = datos.getDItinerario();
             }
             listadoPreguntas.add(pregunta);
           }
           else if ((datos.getBContestado() != null) && (utilidades.esNumericoFloat(datos.getBContestado()))) {
             valor = Float.parseFloat(datos.getBContestado());
 
             if (valor < 24.0F) {
               cont1 += 1;
               contTot += 1;
             }
             if ((valor >= 24.0F) && (valor < 48.0F)) {
               cont2 += 1;
               contTot += 1;
             }
             if ((valor >= 48.0F) && (valor < 72.0F)) {
               cont3 += 1;
               contTot += 1;
             }
             if ((valor >= 72.0F) && (valor < 96.0F)) {
               cont4 += 1;
               contTot += 1;
             }
             if ((valor >= 96.0F) && (valor < 120.0F)) {
               cont5 += 1;
               contTot += 1;
             }
             if ((valor >= 120.0F) && (valor < 144.0F)) {
               cont6 += 1;
               contTot += 1;
             }
             if ((valor >= 144.0F) && (valor < 168.0F)) {
               cont7 += 1;
               contTot += 1;
             }
             if ((valor >= 168.0F) && (valor < 192.0F)) {
               cont8 += 1;
               contTot += 1;
             }
             if ((valor >= 192.0F) && (valor < 226.0F)) {
               cont9 += 1;
               contTot += 1;
             }
             if ((valor >= 226.0F) && (valor < 240.0F)) {
               cont10 += 1;
               contTot += 1;
             }
             if (valor >= 240.0F) {
               cont11 += 1;
               contTot += 1;
             }
           }
         }
       }
 
       if (array.size() > 0)
         if (pregId != 11) {
           itin = new OCAPUsuariosOT();
 
           if (cItinerarioId == 0L) {
             listadoPreguntas = array;
           }
           itin.setListaItinerarios(listadoPreguntas);
           itin.setDItinerario(itiAnt);
 
           listadoItinerarios.add(itin);
         } else {
           horas = new OCAPEncuestaCalidadOT();
 
           horas.setTotal1((int)cont1);
           horas.setTotal2((int)cont2);
           horas.setTotal3((int)cont3);
           horas.setTotal4((int)cont4);
           horas.setTotal5((int)cont5);
           horas.setTotal6((int)cont6);
           horas.setTotal7((int)cont7);
           horas.setTotal8((int)cont8);
           horas.setTotal9((int)cont9);
           horas.setTotal10((int)cont10);
           horas.setTotal11((int)cont11);
 
           if (contTot != 0) {
             horas.setPorc1((float)(Math.rint(100.0F * cont1 / contTot * 100.0F) / 100.0D));
             horas.setPorc2((float)(Math.rint(100.0F * cont2 / contTot * 100.0F) / 100.0D));
             horas.setPorc3((float)(Math.rint(100.0F * cont3 / contTot * 100.0F) / 100.0D));
             horas.setPorc4((float)(Math.rint(100.0F * cont4 / contTot * 100.0F) / 100.0D));
             horas.setPorc5((float)(Math.rint(100.0F * cont5 / contTot * 100.0F) / 100.0D));
             horas.setPorc6((float)(Math.rint(100.0F * cont6 / contTot * 100.0F) / 100.0D));
             horas.setPorc7((float)(Math.rint(100.0F * cont7 / contTot * 100.0F) / 100.0D));
             horas.setPorc8((float)(Math.rint(100.0F * cont8 / contTot * 100.0F) / 100.0D));
             horas.setPorc9((float)(Math.rint(100.0F * cont9 / contTot * 100.0F) / 100.0D));
             horas.setPorc10((float)(Math.rint(100.0F * cont10 / contTot * 100.0F) / 100.0D));
             horas.setPorc11((float)(Math.rint(100.0F * cont11 / contTot * 100.0F) / 100.0D));
           }
 
           horas.setTotalRespuestas(contTot);
 
           listadoItinerarios.add(horas);
         }
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listadoItinerarios;
   }
 
   public ArrayList contarEstadisticas(OCAPEncuestaCalidadOT encOT)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoItinerarios = null;
     ArrayList listadoPreguntas = null;
     OCAPEncuestaCalidadOT datos = null;
     OCAPEncuestaCalidadOT pregunta = null;
     OCAPEncuestaCalidadOT itin = null;
     String itiAnt = "";
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
 
       String porItinerario = null;
       if (encOT.getCItinerario_id() != 0L)
         porItinerario = Constantes.SI;
       if (encOT.getCProfesional_id() != 0L)
         porItinerario = Constantes.NO;
       long paramFiltro = encOT.getCItinerario_id() != 0L ? encOT.getCItinerario_id() : encOT.getCProfesional_id();
 
       array = this.variableOAD.contarEstadisticas(paramFiltro, porItinerario);
 
       listadoItinerarios = new ArrayList();
 
       itin = new OCAPEncuestaCalidadOT();
       itin.setDItinerario("");
       listadoPreguntas = new ArrayList();
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPEncuestaCalidadOT)array.get(i);
         if ((datos.getCPreguntaId() == 0L) && (Constantes.SI.equals(encOT.getRespuesta_0()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 1L) && (Constantes.SI.equals(encOT.getRespuesta_1()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 2) && (Constantes.SI.equals(encOT.getRespuesta_2()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 3) && (Constantes.SI.equals(encOT.getRespuesta_3()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 4) && (Constantes.SI.equals(encOT.getRespuesta_4()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 5) && (Constantes.SI.equals(encOT.getRespuesta_5()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 6) && (Constantes.SI.equals(encOT.getRespuesta_6()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 7) && (Constantes.SI.equals(encOT.getRespuesta_7()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 8) && (Constantes.SI.equals(encOT.getRespuesta_8()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 9) && (Constantes.SI.equals(encOT.getRespuesta_9()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 12) && (Constantes.SI.equals(encOT.getRespuesta_12()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 13) && (Constantes.SI.equals(encOT.getRespuesta_13()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 15) && (Constantes.SI.equals(encOT.getRespuesta_15()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 16) && (Constantes.SI.equals(encOT.getRespuesta_16()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 17) && (Constantes.SI.equals(encOT.getRespuesta_17()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 18) && (Constantes.SI.equals(encOT.getRespuesta_18()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 19) && (Constantes.SI.equals(encOT.getRespuesta_19()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 20) && (Constantes.SI.equals(encOT.getRespuesta_20()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 21) && (Constantes.SI.equals(encOT.getRespuesta_21()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 22) && (Constantes.SI.equals(encOT.getRespuesta_22()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 23) && (Constantes.SI.equals(encOT.getRespuesta_23()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 24) && (Constantes.SI.equals(encOT.getRespuesta_24()))) {
           listadoPreguntas.add(datos);
         }
         if ((datos.getCPreguntaId() == 25) && (Constantes.SI.equals(encOT.getRespuesta_25()))) {
           listadoPreguntas.add(datos);
         }
       }
       itin.setListaPreguntas(listadoPreguntas);
       listadoItinerarios.add(itin);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listadoItinerarios;
   }
 
   public int contarOCAPEncuesta(long cEncuestaId, long cItinerarioId, String porItinerario)
     throws Exception
   {
     int resultado = 0;
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       resultado = this.variableOAD.OCAPContarEncuesta(cEncuestaId, cItinerarioId, porItinerario);
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return resultado;
   }
 
   public ArrayList buscarPreguntas()
     throws Exception
   {
     ArrayList listado = null;
     try
     {
       this.variableOAD = OCAPEncuestaCalidadOAD.getInstance();
       OCAPConfigApp.logger.info("LN");
       listado = this.variableOAD.buscarPreguntas();
     }
     catch (Exception e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return listado;
   }
 }


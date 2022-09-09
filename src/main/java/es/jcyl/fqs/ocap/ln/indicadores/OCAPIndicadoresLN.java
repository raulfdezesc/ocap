 package es.jcyl.fqs.ocap.ln.indicadores;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
 import es.jcyl.fqs.ocap.oad.componentesfqs.OCAPComponentesfqsOAD;
 import es.jcyl.fqs.ocap.oad.expedienteCarrera.OCAPExpedientecarreraOAD;
 import es.jcyl.fqs.ocap.oad.usuarios.OCAPUsuariosOAD;
 import es.jcyl.fqs.ocap.ot.usuarios.OCAPUsuariosOT;
import es.jcyl.fqs.ocap.util.Constantes;
import es.jcyl.fqs.ocap.util.Utilidades;
import es.jcyl.framework.JCYLUsuario;
 import java.sql.Connection;
 import java.util.ArrayList;
 import org.apache.log4j.Logger;
 
 public class OCAPIndicadoresLN
 {
   OCAPComponentesfqsOAD variableOAD;
   private JCYLUsuario jcylUsuario;
 
   public OCAPIndicadoresLN(JCYLUsuario jcylUsuario)
   {
     this.jcylUsuario = jcylUsuario;
   }
 
   public OCAPUsuariosOT buscarIndicadorEvaluados(String opcion)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoIndicador = null;
     OCAPUsuariosOT total = new OCAPUsuariosOT();
     OCAPUsuariosOT datos = null;
     OCAPUsuariosOT datosAnt = null;
     long indAnt = 0L;
     long indAct = 0L;
     int totalEval = 0;
     int totalEvalOK = 0;
     int totalEvalKO = 0;
     int totalInd = 0;
     int totalIndOK = 0;
     int totalIndKO = 0;
     float porcIndOK = 0.0F;
     float porcIndKO = 0.0F;
     try
     {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.buscarIndicadorEvaluadores(opcion);
 
       listadoIndicador = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
         if (opcion.equals("eval"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("itin"))
           indAct = datos.getCItinerarioId();
         else if (opcion.equals("gere"))
           indAct = datos.getCGerenciaId().longValue();
         else if (opcion.equals("evalfqs"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("cte"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("ce")) {
           indAct = datos.getCCompfqsId();
         }
         if ((indAct != 0L) && (indAnt != indAct)) {
           totalEval += 1;
           if (i > 0) {
             datosAnt = (OCAPUsuariosOT)array.get(i - 1);
             datosAnt.setTotalIndicador(totalIndOK + totalIndKO);
             datosAnt.setTotalIndicadorOK(totalIndOK);
             datosAnt.setTotalIndicadorKO(totalIndKO);
 
             porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
             porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
 
             porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
             porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
             datosAnt.setPorcIndicadorOK(porcIndOK);
             datosAnt.setPorcIndicadorKO(porcIndKO);
 
             listadoIndicador.add(datosAnt);
           }
           totalIndOK = 0;
           totalIndKO = 0;
 
           indAnt = indAct;
         }
 
         if (Constantes.SI.equals(datos.getBReconocimientoGrado())) {
           totalEvalOK += datos.getTotalIndicador();
           totalIndOK += datos.getTotalIndicador();
         } else {
           totalEvalKO += datos.getTotalIndicador();
           totalIndKO += datos.getTotalIndicador();
         }
 
       }
 
       if (array.size() > 0) {
         datosAnt = (OCAPUsuariosOT)array.get(array.size() - 1);
         datosAnt.setTotalIndicador(totalIndOK + totalIndKO);
         datosAnt.setTotalIndicadorOK(totalIndOK);
         datosAnt.setTotalIndicadorKO(totalIndKO);
 
         porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
         porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
         porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
         porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
         datosAnt.setPorcIndicadorOK(porcIndOK);
         datosAnt.setPorcIndicadorKO(porcIndKO);
 
         listadoIndicador.add(datos);
       }
 
       total.setTotalEvaluados(totalEvalOK + totalEvalKO);
       total.setTotalIndicadores(totalEval);
       total.setTotalIndicadoresOK(totalEvalOK);
       total.setTotalIndicadoresKO(totalEvalKO);
       total.setListaIndicadores(listadoIndicador);
 
       float porcOK = (float)(100.0D * totalEvalOK / (totalEvalOK + totalEvalKO));
       float porcKO = (float)(100.0D * totalEvalKO / (totalEvalOK + totalEvalKO));
       porcOK = (float)(Math.rint(porcOK * 100.0F) / 100.0D);
       porcKO = (float)(Math.rint(porcKO * 100.0F) / 100.0D);
 
       total.setPorcIndicadoresOK(porcOK);
       total.setPorcIndicadoresKO(porcKO);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public OCAPUsuariosOT buscarIndicadorEvaluadosNoMod(String opcion, JCYLUsuario jcylUsuario, boolean mod)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoIndicador = null;
     OCAPUsuariosOT total = new OCAPUsuariosOT();
     OCAPUsuariosOT datos = null;
     OCAPUsuariosOT datosAnt = null;
     long indAnt = 0L;
     long indAct = 0L;
     int totalEval = 0;
     int totalEvalOK = 0;
     int totalEvalKO = 0;
     int totalInd = 0;
     int totalIndOK = 0;
     int totalIndKO = 0;
     float porcIndOK = 0.0F;
     float porcIndKO = 0.0F;
     try
     {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.buscarIndicadorEvaluadoresMod(jcylUsuario, mod);
 
       listadoIndicador = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
         if (opcion.equals("eval"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("itin"))
           indAct = datos.getCItinerarioId();
         else if (opcion.equals("gere"))
           indAct = datos.getCGerenciaId().longValue();
         else if (opcion.equals("evalfqs")) {
           indAct = datos.getCCompfqsId();
         }
         if ((indAct != 0L) && (indAnt != indAct)) {
           totalEval += 1;
           if (i > 0) {
             datosAnt = (OCAPUsuariosOT)array.get(i - 1);
             datosAnt.setTotalIndicadorNoMod(totalIndOK + totalIndKO);
             datosAnt.setTotalIndicadorNoModOK(totalIndOK);
             datosAnt.setTotalIndicadorNoModKO(totalIndKO);
 
             porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
             porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
 
             porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
             porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
             datosAnt.setPorcIndicadorNoModOK(porcIndOK);
             datosAnt.setPorcIndicadorNoModKO(porcIndKO);
 
             listadoIndicador.add(datosAnt);
           }
           totalIndOK = 0;
           totalIndKO = 0;
 
           indAnt = indAct;
         }
 
         if (Constantes.SI.equals(datos.getBReconocimientoGrado())) {
           totalEvalOK += datos.getTotalIndicadorNoMod();
           totalIndOK += datos.getTotalIndicadorNoMod();
         } else {
           totalEvalKO += datos.getTotalIndicadorNoMod();
           totalIndKO += datos.getTotalIndicadorNoMod();
         }
 
       }
 
       if (array.size() > 0) {
         datosAnt = (OCAPUsuariosOT)array.get(array.size() - 1);
         datosAnt.setTotalIndicadorNoMod(totalIndOK + totalIndKO);
         datosAnt.setTotalIndicadorNoModOK(totalIndOK);
         datosAnt.setTotalIndicadorNoModKO(totalIndKO);
 
         porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
         porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
         porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
         porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
         datosAnt.setPorcIndicadorNoModOK(porcIndOK);
         datosAnt.setPorcIndicadorNoModKO(porcIndKO);
 
         listadoIndicador.add(datos);
       }
 
       total.setTotalEvaluadosNoMod(totalEvalOK + totalEvalKO);
       total.setTotalIndicadoresNoMod(totalEval);
       total.setTotalIndicadoresNoModOK(totalEvalOK);
       total.setTotalIndicadoresNoModKO(totalEvalKO);
       total.setListaIndicadoresNoMod(listadoIndicador);
 
       float porcOK = (float)(100.0D * totalEvalOK / (totalEvalOK + totalEvalKO));
       float porcKO = (float)(100.0D * totalEvalKO / (totalEvalOK + totalEvalKO));
       porcOK = (float)(Math.rint(porcOK * 100.0F) / 100.0D);
       porcKO = (float)(Math.rint(porcKO * 100.0F) / 100.0D);
 
       total.setPorcIndicadoresNoModOK(porcOK);
       total.setPorcIndicadoresNoModKO(porcKO);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public OCAPUsuariosOT buscarIndicadorEvaluadosMod(String opcion, JCYLUsuario jcylUsuario, boolean mod)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoIndicadorMod = null;
     OCAPUsuariosOT total = new OCAPUsuariosOT();
     OCAPUsuariosOT datos = null;
     OCAPUsuariosOT datosAnt = null;
     long indAnt = 0L;
     long indAct = 0L;
     int totalEval = 0;
     int totalEvalOK = 0;
     int totalEvalKO = 0;
     int totalInd = 0;
     int totalIndOK = 0;
     int totalIndKO = 0;
     float porcIndOK = 0.0F;
     float porcIndKO = 0.0F;
     try
     {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.buscarIndicadorEvaluadoresMod(jcylUsuario, mod);
 
       listadoIndicadorMod = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
         if (opcion.equals("eval"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("itin"))
           indAct = datos.getCItinerarioId();
         else if (opcion.equals("gere"))
           indAct = datos.getCGerenciaId().longValue();
         else if (opcion.equals("evalfqs")) {
           indAct = datos.getCCompfqsId();
         }
         if ((indAct != 0L) && (indAnt != indAct)) {
           totalEval += 1;
           if (i > 0) {
             datosAnt = (OCAPUsuariosOT)array.get(i - 1);
             datosAnt.setTotalIndicadorMod(totalIndOK + totalIndKO);
             datosAnt.setTotalIndicadorModOK(totalIndOK);
             datosAnt.setTotalIndicadorModKO(totalIndKO);
 
             porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
             porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
 
             porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
             porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
             datosAnt.setPorcIndicadorModOK(porcIndOK);
             datosAnt.setPorcIndicadorModKO(porcIndKO);
 
             listadoIndicadorMod.add(datosAnt);
           }
           totalIndOK = 0;
           totalIndKO = 0;
 
           indAnt = indAct;
         }
 
         if (Constantes.SI.equals(datos.getBReconocimientoGrado())) {
           totalEvalOK += datos.getTotalIndicadorMod();
           totalIndOK += datos.getTotalIndicadorMod();
         } else {
           totalEvalKO += datos.getTotalIndicadorMod();
           totalIndKO += datos.getTotalIndicadorMod();
         }
 
       }
 
       if (array.size() > 0) {
         datosAnt = (OCAPUsuariosOT)array.get(array.size() - 1);
         datosAnt.setTotalIndicadorMod(totalIndOK + totalIndKO);
         datosAnt.setTotalIndicadorModOK(totalIndOK);
         datosAnt.setTotalIndicadorModKO(totalIndKO);
 
         porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
         porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
         porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
         porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
         datosAnt.setPorcIndicadorModOK(porcIndOK);
         datosAnt.setPorcIndicadorModKO(porcIndKO);
 
         listadoIndicadorMod.add(datos);
       }
 
       total.setTotalEvaluadosMod(totalEvalOK + totalEvalKO);
       total.setTotalIndicadoresMod(totalEval);
       total.setTotalIndicadoresModOK(totalEvalOK);
       total.setTotalIndicadoresModKO(totalEvalKO);
       total.setListaIndicadoresMod(listadoIndicadorMod);
 
       float porcOK = (float)(100.0D * totalEvalOK / (totalEvalOK + totalEvalKO));
       float porcKO = (float)(100.0D * totalEvalKO / (totalEvalOK + totalEvalKO));
       porcOK = (float)(Math.rint(porcOK * 100.0F) / 100.0D);
       porcKO = (float)(Math.rint(porcKO * 100.0F) / 100.0D);
 
       total.setPorcIndicadoresModOK(porcOK);
       total.setPorcIndicadoresModKO(porcKO);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public OCAPUsuariosOT buscarIndicadorCTEsMod(String opcion, JCYLUsuario jcylUsuario, boolean mod)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoIndicadorMod = null;
     OCAPUsuariosOT total = new OCAPUsuariosOT();
     OCAPUsuariosOT datos = null;
     OCAPUsuariosOT datosAnt = null;
     long indAnt = 0L;
     long indAct = 0L;
     int totalEval = 0;
     int totalEvalOK = 0;
     int totalEvalKO = 0;
     int totalInd = 0;
     int totalIndOK = 0;
     int totalIndKO = 0;
     float porcIndOK = 0.0F;
     float porcIndKO = 0.0F;
     try
     {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       array = usuariosOAD.buscarIndicadorCTEMod(jcylUsuario, mod, opcion);
 
       listadoIndicadorMod = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
         if (opcion.equals("eval"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("itin"))
           indAct = datos.getCItinerarioId();
         else if (opcion.equals("gere"))
           indAct = datos.getCGerenciaId().longValue();
         else if (opcion.equals("evalfqs"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("cte"))
           indAct = datos.getCCteId();
         else if (opcion.equals("ce")) {
           indAct = datos.getCCteId();
         }
         if ((indAct != 0L) && (indAnt != indAct)) {
           totalEval += 1;
           if (i > 0) {
             datosAnt = (OCAPUsuariosOT)array.get(i - 1);
             datosAnt.setTotalIndicadorMod(totalIndOK + totalIndKO);
             datosAnt.setTotalIndicadorModOK(totalIndOK);
             datosAnt.setTotalIndicadorModKO(totalIndKO);
 
             porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
             porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
 
             porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
             porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
             datosAnt.setPorcIndicadorModOK(porcIndOK);
             datosAnt.setPorcIndicadorModKO(porcIndKO);
 
             listadoIndicadorMod.add(datosAnt);
           }
           totalIndOK = 0;
           totalIndKO = 0;
 
           indAnt = indAct;
         }
 
         if (Constantes.SI.equals(datos.getBReconocimientoGrado())) {
           totalEvalOK += datos.getTotalIndicadorMod();
           totalIndOK += datos.getTotalIndicadorMod();
         } else {
           totalEvalKO += datos.getTotalIndicadorMod();
           totalIndKO += datos.getTotalIndicadorMod();
         }
 
       }
 
       if (array.size() > 0) {
         datosAnt = (OCAPUsuariosOT)array.get(array.size() - 1);
         datosAnt.setTotalIndicadorMod(totalIndOK + totalIndKO);
         datosAnt.setTotalIndicadorModOK(totalIndOK);
         datosAnt.setTotalIndicadorModKO(totalIndKO);
 
         porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
         porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
         porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
         porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
         datosAnt.setPorcIndicadorModOK(porcIndOK);
         datosAnt.setPorcIndicadorModKO(porcIndKO);
 
         listadoIndicadorMod.add(datos);
       }
 
       total.setTotalEvaluadosMod(totalEvalOK + totalEvalKO);
       total.setTotalIndicadoresMod(totalEval);
       total.setTotalIndicadoresModOK(totalEvalOK);
       total.setTotalIndicadoresModKO(totalEvalKO);
       total.setListaIndicadoresMod(listadoIndicadorMod);
 
       float porcOK = (float)(100.0D * totalEvalOK / (totalEvalOK + totalEvalKO));
       float porcKO = (float)(100.0D * totalEvalKO / (totalEvalOK + totalEvalKO));
       porcOK = (float)(Math.rint(porcOK * 100.0F) / 100.0D);
       porcKO = (float)(Math.rint(porcKO * 100.0F) / 100.0D);
 
       total.setPorcIndicadoresModOK(porcOK);
       total.setPorcIndicadoresModKO(porcKO);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public OCAPUsuariosOT buscarIndicadorCTEsNoMod(String opcion, JCYLUsuario jcylUsuario, boolean mod)
     throws Exception
   {
     ArrayList array = null;
     ArrayList listadoIndicador = null;
     OCAPUsuariosOT total = new OCAPUsuariosOT();
     OCAPUsuariosOT datos = null;
     OCAPUsuariosOT datosAnt = null;
     long indAnt = 0L;
     long indAct = 0L;
     int totalEval = 0;
     int totalEvalOK = 0;
     int totalEvalKO = 0;
     int totalInd = 0;
     int totalIndOK = 0;
     int totalIndKO = 0;
     float porcIndOK = 0.0F;
     float porcIndKO = 0.0F;
     try
     {
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       array = usuariosOAD.buscarIndicadorCTEMod(jcylUsuario, mod, opcion);
 
       listadoIndicador = new ArrayList();
 
       for (int i = 0; i < array.size(); i++) {
         datos = (OCAPUsuariosOT)array.get(i);
         if (opcion.equals("eval"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("itin"))
           indAct = datos.getCItinerarioId();
         else if (opcion.equals("gere"))
           indAct = datos.getCGerenciaId().longValue();
         else if (opcion.equals("evalfqs"))
           indAct = datos.getCCompfqsId();
         else if (opcion.equals("cte"))
           indAct = datos.getCCteId();
         else if (opcion.equals("ce")) {
           indAct = datos.getCCteId();
         }
         if ((indAct != 0L) && (indAnt != indAct)) {
           totalEval += 1;
           if (i > 0) {
             datosAnt = (OCAPUsuariosOT)array.get(i - 1);
             datosAnt.setTotalIndicadorNoMod(totalIndOK + totalIndKO);
             datosAnt.setTotalIndicadorNoModOK(totalIndOK);
             datosAnt.setTotalIndicadorNoModKO(totalIndKO);
 
             porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
             porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
 
             porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
             porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
             datosAnt.setPorcIndicadorNoModOK(porcIndOK);
             datosAnt.setPorcIndicadorNoModKO(porcIndKO);
 
             listadoIndicador.add(datosAnt);
           }
           totalIndOK = 0;
           totalIndKO = 0;
 
           indAnt = indAct;
         }
 
         if (Constantes.SI.equals(datos.getBReconocimientoGrado())) {
           totalEvalOK += datos.getTotalIndicadorNoMod();
           totalIndOK += datos.getTotalIndicadorNoMod();
         } else {
           totalEvalKO += datos.getTotalIndicadorNoMod();
           totalIndKO += datos.getTotalIndicadorNoMod();
         }
 
       }
 
       if (array.size() > 0) {
         datosAnt = (OCAPUsuariosOT)array.get(array.size() - 1);
         datosAnt.setTotalIndicadorNoMod(totalIndOK + totalIndKO);
         datosAnt.setTotalIndicadorNoModOK(totalIndOK);
         datosAnt.setTotalIndicadorNoModKO(totalIndKO);
 
         porcIndOK = (float)(100.0D * totalIndOK / (totalIndOK + totalIndKO));
         porcIndKO = (float)(100.0D * totalIndKO / (totalIndOK + totalIndKO));
         porcIndOK = (float)(Math.rint(porcIndOK * 100.0F) / 100.0D);
         porcIndKO = (float)(Math.rint(porcIndKO * 100.0F) / 100.0D);
 
         datosAnt.setPorcIndicadorNoModOK(porcIndOK);
         datosAnt.setPorcIndicadorNoModKO(porcIndKO);
 
         listadoIndicador.add(datos);
       }
 
       total.setTotalEvaluadosNoMod(totalEvalOK + totalEvalKO);
       total.setTotalIndicadoresNoMod(totalEval);
       total.setTotalIndicadoresNoModOK(totalEvalOK);
       total.setTotalIndicadoresNoModKO(totalEvalKO);
       total.setListaIndicadoresNoMod(listadoIndicador);
 
       float porcOK = (float)(100.0D * totalEvalOK / (totalEvalOK + totalEvalKO));
       float porcKO = (float)(100.0D * totalEvalKO / (totalEvalOK + totalEvalKO));
       porcOK = (float)(Math.rint(porcOK * 100.0F) / 100.0D);
       porcKO = (float)(Math.rint(porcKO * 100.0F) / 100.0D);
 
       total.setPorcIndicadoresNoModOK(porcOK);
       total.setPorcIndicadoresNoModKO(porcKO);
     }
     catch (Exception e)
     {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
 
     return total;
   }
 
   public int buscarIndicadorEvaluadosGRS()
     throws Exception
   {
     int retorno = 0;
     Connection con = null;
     try
     {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadoresGRS: Inicio");
 
       OCAPExpedientecarreraOAD expCarrOAD = OCAPExpedientecarreraOAD.getInstance();
 
       retorno = expCarrOAD.buscarEvaluadosGRS();
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadoresGRS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarIndicadoresGRS: ERROR: " + e.getMessage());
 
       throw e;
     }
     finally
     {
     }
 
     return retorno;
   }
 
   public int contarEvaluadosExcluidos()
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosExcluidos: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarEvaluadosExcluidos();
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosExcluidos: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosExcluidos: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public int contarEvaluadosFQS(String reconoceGrado)
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosFQS: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarEvaluadosFQS(reconoceGrado);
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosFQS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosFQS: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public int contarEvaluadosAutoevaluacion(String reconoceGrado, String bFQS)
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosFQS: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarEvaluadosAutoevaluacion(reconoceGrado, bFQS);
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosFQS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosFQS: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public int contarEvaluadosGRS(String reconoceGrado)
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosGRS: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarEvaluadosGRS(reconoceGrado);
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosGRS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadosGRS: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public ArrayList buscarEvaluadosPorCategoria(String quienEvalua, int totalEvaluados)
     throws Exception
   {
     ArrayList listaCategorias = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCategoria: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       listaCategorias = usuariosOAD.buscarEvaluadosPorCategoria(quienEvalua, totalEvaluados);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCategoria: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCategoria: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return listaCategorias;
   }
 
   public int contarEvaluadoresFQS()
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresFQS: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarEvaluadoresFQS();
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresFQS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresFQS: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public int contarEvaluadoresBajaFQS()
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresBajaFQS: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarEvaluadoresBajaFQS();
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresBajaFQS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresBajaFQS: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public int contarEvaluadoresFinFQS()
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresFinFQS: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarEvaluadoresFinFQS();
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresFinFQS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarEvaluadoresFinFQS: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public ArrayList buscarEvaluadoresPorCategoria(int totalEvaluadores)
     throws Exception
   {
     ArrayList listaCategorias = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadoresPorCategoria: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       listaCategorias = usuariosOAD.buscarEvaluadoresPorCategoria(totalEvaluadores);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadoresPorCategoria: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadoresPorCategoria: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return listaCategorias;
   }
 
   public ArrayList buscarEvaluadosPorItinerarioPorEvaluador()
     throws Exception
   {
     ArrayList listaCategorias = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorItinerarioPorEvaluador: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       listaCategorias = usuariosOAD.buscarEvaluadosPorItinerarioPorEvaluador();
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorItinerarioPorEvaluador: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorItinerarioPorEvaluador: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return listaCategorias;
   }
 
   public ArrayList buscarAuditoriasPorCategoria(int totalAuditorias)
     throws Exception
   {
     ArrayList listaCategorias = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarAuditoriasPorCategoria: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       listaCategorias = usuariosOAD.buscarAuditoriasPorCategoria(totalAuditorias);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarAuditoriasPorCategoria: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarAuditoriasPorCategoria: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return listaCategorias;
   }
 
   public int contarInformesFQS()
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarInformesFQS: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarInformesFQS();
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarInformesFQS: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarInformesFQS: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public int contarInformesCE(String bConforme)
     throws Exception
   {
     int retorno = 0;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " contarInformesCE: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
 
       retorno = usuariosOAD.contarInformesCE(bConforme);
 
       OCAPConfigApp.logger.info(getClass().getName() + " contarInformesCE: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " contarInformesCE: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return retorno;
   }
 
   public ArrayList buscarEvaluadosPorCE(int totalInformes, String bConforme)
     throws Exception
   {
     ArrayList listaCategorias = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCE: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       listaCategorias = usuariosOAD.buscarEvaluadosPorCE(totalInformes, bConforme);
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCE: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCE: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return listaCategorias;
   }
 
   public ArrayList buscarEvaluadosPorCategoriaPorCTE()
     throws Exception
   {
     ArrayList listaCategorias = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCategoriaPorCTE: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       listaCategorias = usuariosOAD.buscarEvaluadosPorCategoriaPorCTE();
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCategoriaPorCTE: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCategoriaPorCTE: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return listaCategorias;
   }
 
   public ArrayList buscarEvaluadosPorCTE()
     throws Exception
   {
     ArrayList listaCategorias = null;
     try {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCTE: Inicio");
 
       OCAPUsuariosOAD usuariosOAD = OCAPUsuariosOAD.getInstance();
       listaCategorias = usuariosOAD.buscarEvaluadosPorCTE();
 
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCTE: Fin");
     } catch (Exception e) {
       OCAPConfigApp.logger.info(getClass().getName() + " buscarEvaluadosPorCTE: ERROR: " + e.getMessage());
       throw e;
     }
     finally {
     }
     return listaCategorias;
   }
 }


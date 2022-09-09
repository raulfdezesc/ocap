 package es.jcyl.fqs.ocap.util.xml;
 
 import es.jcyl.fqs.ocap.config.OCAPConfigApp;
import es.jcyl.fqs.ocap.util.Utilidades;

import java.io.IOException;
 import java.io.InputStream;
 import java.util.ArrayList;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;
 import org.apache.commons.collections.OrderedMap;
 import org.apache.log4j.Logger;
 import org.w3c.dom.Document;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 import org.w3c.dom.Text;
 import org.xml.sax.SAXException;
 
 public class GestorXML
 {
   Document documento;
   Node puntero;
   NodeList listatipo;
   int i_lista_tipo;
 
   private void $init$()
   {
     this.i_lista_tipo = 0;
   }
   public Node dame_nodo(String padre, String hijo) {
     NodeList listahijos = null;
     this.puntero = this.documento.getFirstChild();
     Node modulo = null;
 
     while ((this.puntero != null) && (!this.puntero.getNodeName().equals(padre))) {
       try {
         this.puntero = this.puntero.getNextSibling();
       } catch (NullPointerException NPExcepcion) {
         System.exit(1);
       }
 
     }
 
     if (this.puntero.getNodeName().equals(padre)) {
       listahijos = this.puntero.getChildNodes();
       for (int i = 0; i < listahijos.getLength(); i++) {
         if (hijo.compareTo(listahijos.item(i).getNodeName()) == 0)
         {
           modulo = listahijos.item(i);
           break;
         }
       }
 
     }
 
     return modulo;
   }
 
   public void transforma(Node nodo, OrderedMap resultado)
   {
     NodeList nodos = nodo.getChildNodes();
     for (int i = 0; i < nodos.getLength(); i++) {
       Node node = nodos.item(i);
       switch (node.getNodeType()) {
       case 4:
         break;
       case 1:
         transforma(node, resultado);
         break;
       case 7:
         break;
       case 3:
         String texto = ((Text)node).getData();
         if (!texto.startsWith("\n"))
           resultado.put(nodo.getNodeName(), ((Text)node).getData());
       }
     }
   }
 
   public GestorXML(InputStream archivo)
     throws Exception
   {
     $init$();
     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
     try {
       DocumentBuilder builder = factory.newDocumentBuilder();
       this.documento = builder.parse(archivo);
       OCAPConfigApp.logger.info("Documento " + archivo + " cargado con exito");
     } catch (ParserConfigurationException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (SAXException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     } catch (IOException e) {
       OCAPConfigApp.logger.error(Utilidades.getStackTrace(e));
       throw e;
     }
   }
 
   public int nodos_tipo(String tipo) {
     this.listatipo = this.documento.getElementsByTagName(tipo);
 
     return this.listatipo.getLength();
   }
 
   public Node get_element_lista()
   {
     Node nodo = this.listatipo.item(this.i_lista_tipo);
     this.i_lista_tipo += 1;
 
     return nodo;
   }
 
   public void reset_lista_tipo() {
     this.listatipo = null;
     this.i_lista_tipo = 0;
   }
 
   public static Node extrae_hijo(Node padre, String nom_hijo)
   {
     NodeList nodos = padre.getChildNodes();
     Node resultado = null;
     for (int i = 0; i < nodos.getLength(); i++) {
       Node node = nodos.item(i);
       switch (node.getNodeType()) {
       case 4:
         break;
       case 1:
         if (node.getNodeName() == nom_hijo) {
           resultado = node;
         }
         break;
       case 7:
         break;
       case 3:
         break;
       }
 
       if (resultado != null)
       {
         break;
       }
     }
     return resultado;
   }
 
   public static ArrayList extrae_hijos(Node padre)
   {
     NodeList nodos = padre.getChildNodes();
     ArrayList resultado = new ArrayList(nodos.getLength());
     for (int i = 0; i < nodos.getLength(); i++) {
       Node node = nodos.item(i);
       switch (node.getNodeType()) {
       case 4:
         break;
       case 1:
         resultado.add(node);
         break;
       case 7:
         break;
       case 3:
       }
 
     }
 
     return resultado;
   }
 
   public Node dame_nodo(String padre)
   {
     NodeList listahijos = null;
     this.puntero = this.documento.getFirstChild();
     Node modulo = null;
 
     while ((this.puntero != null) && (!this.puntero.getNodeName().equals(padre))) {
       try {
         this.puntero = this.puntero.getNextSibling();
       } catch (NullPointerException NPExcepcion) {
         System.exit(1);
       }
     }
 
     if (this.puntero.getNodeName().equals(padre)) {
       modulo = this.puntero;
     }
 
     return modulo;
   }
 }


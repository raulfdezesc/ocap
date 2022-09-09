 package es.jcyl.cf.seguridad.modelo;
 
 import es.jcyl.cf.seguridad.util.Funcion;
 import es.jcyl.cf.seguridad.util.JCYLUtilidadesXML;
 import es.jcyl.cf.seguridad.util.Menu;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Hashtable;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 
 public class OADAutorizacion
 {
   String fuenteDeDatos;
 
   public OADAutorizacion()
   {
     this.fuenteDeDatos = null;
   }
 
   public OADAutorizacion(String fuenteDeDatos)
   {
     this.fuenteDeDatos = null;
     this.fuenteDeDatos = fuenteDeDatos;
   }
 
   
   private String getCadenaFuncionesMenu(String aplicacion, String rol, String f_padre)
     throws Exception
   {
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(aplicacion))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + aplicacion + "' no existe en el fichero de estructura");
     String cadena = new String();
     ArrayList listaFuncionesRol = new ArrayList();
     if (f_padre != null)
     {
       String[] atributos = { "c_grupo_id", "c_menu_id" };
 
       String[] valores = { rol, "S" };
 
       ArrayList listaFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
       listaFuncionesRol = new ArrayList();
       if (listaFunciones != null)
       {
         for (int ii = 0; ii < listaFunciones.size(); ii++)
         {
           Node nodo = (Node)listaFunciones.get(ii);
           if (JCYLUtilidadesXML.getValor(nodo, "c_funcion_padre").equals(f_padre))
           {
             Node funcion = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"));
             if (funcion != null)
               listaFuncionesRol.add(funcion);
           }
         }
       }
     }
     listaFuncionesRol = JCYLUtilidadesXML.ordenarListaNodos(listaFuncionesRol, "n_orden");
     for (int j = 0; j < listaFuncionesRol.size(); j++)
     {
       Node nodo = (Node)listaFuncionesRol.get(j);
       String funcionId = JCYLUtilidadesXML.getValor(nodo, "c_funcion_id");
       String dDescripcion = JCYLUtilidadesXML.getValor(nodo, "d_descripcion");
       String accion = JCYLUtilidadesXML.getValor(nodo, "d_proceso");
       cadena = cadena + " ,['" + dDescripcion + "' , '" + accion + "'" + getCadenaFuncionesMenu(aplicacion, rol, funcionId) + "] ";
     }
 
     return cadena;
   }
 
   public String getGrupo(String usuario, String aplicacion)
     throws Exception
   {
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     Node nodo = JCYLUtilidadesXML.buscarNodo("usuariosRoles", "asignacion", "c_usr_id", usuario);
     while (i < listaAplicaciones.getLength())
     {
       nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(aplicacion))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + aplicacion + "' no existe en el fichero de estructura");
     String c_grp_id = new String();
     if (nodo == null)
     {
       System.out.println("No se encuentra el usuario, " + usuario);
 
       return null;
     }
 
     c_grp_id = JCYLUtilidadesXML.getValor(nodo, "usuario");
 
     return c_grp_id;
   }
 
   public Hashtable getGrupos(String aplicacion)
     throws Exception
   {
     Hashtable grupos = null;
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(aplicacion))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + aplicacion + "' no existe en el fichero de estructura");
     NodeList nodos = JCYLUtilidadesXML.buscarHijos("roles", "rol");
     if ((nodos == null) || (nodos.getLength() == 0))
     {
       System.out.println("No se encuentra ningun rol");
 
       return null;
     }
     grupos = new Hashtable();
     for (int ii = 0; ii < nodos.getLength(); ii++)
     {
       Node nodo = nodos.item(ii);
       grupos.put(JCYLUtilidadesXML.getValor(nodo, "c_grupo_id"), JCYLUtilidadesXML.getValor(nodo, "d_grupo"));
     }
 
     return grupos;
   }
 
   public Menu getMenu(String usuario, String rol, String aplicacion)
     throws Exception
   {
     Menu menu = new Menu();
     String cadena = "[";
     ArrayList listaFuncionesRol = new ArrayList();
     String[] atributos = { "c_grupo_id", "c_menu_id" };
 
     String[] valores = { rol, "S" };
 
     ArrayList listaFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     listaFuncionesRol = new ArrayList();
     if (listaFunciones != null)
     {
       for (int i = 0; i < listaFunciones.size(); i++)
       {
         Node nodo = (Node)listaFunciones.get(i);
         if (JCYLUtilidadesXML.getValor(nodo, "c_funcion_padre").equals("0"))
         {
           Node funcion = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"));
           if (funcion != null)
             listaFuncionesRol.add(funcion);
         }
       }
     }
     listaFuncionesRol = JCYLUtilidadesXML.ordenarListaNodos(listaFuncionesRol, "n_orden");
     for (int j = 0; j < listaFuncionesRol.size(); j++)
     {
       Node nodo = (Node)listaFuncionesRol.get(j);
       String funcionId = JCYLUtilidadesXML.getValor(nodo, "c_funcion_id");
       String dDescripcion = JCYLUtilidadesXML.getValor(nodo, "d_descripcion");
       String accion = JCYLUtilidadesXML.getValor(nodo, "d_proceso");
       cadena = cadena + "['" + dDescripcion + "' , '" + accion + "'" + getCadenaFuncionesMenu(aplicacion, rol, funcionId) + "], ";
     }
 
     if (cadena.length() > 1)
       cadena = cadena.substring(0, cadena.length() - 1) + "]";
     else
       cadena = cadena + "]";
     menu.setFunciones(cadena);
 
     return menu;
   }
 
   public HashMap getUsuarios(String aplicacion)
     throws Exception
   {
     HashMap hashMap = new HashMap();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(aplicacion))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + aplicacion + "' no existe en el fichero de estructura");
     NodeList listaNodos = JCYLUtilidadesXML.buscarHijos("usuariosRoles", "asignacion");
     for (int ii = 0; ii < listaNodos.getLength(); ii++)
     {
       Node nodo = listaNodos.item(ii);
       hashMap.put(JCYLUtilidadesXML.getValor(nodo, "c_usr_id"), JCYLUtilidadesXML.getValor(nodo, "c_grupo_id"));
     }
 
     return hashMap;
   }
 
   public HashMap getAplicaciones(String usuario)
     throws Exception
   {
     HashMap aplicaciones = new HashMap();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     for (int i = 0; i < listaAplicaciones.getLength(); i++)
     {
       Node nodo = listaAplicaciones.item(i);
       aplicaciones.put(JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id"), JCYLUtilidadesXML.getValor(nodo, "d_aplicacion"));
     }
 
     return aplicaciones;
   }
 
   
   public String estaAutorizado(String usuario, String rol, String aplicacion, String funcion)
     throws Exception
   {
     String[] atributos = { "c_usr_id", "c_grupo_id" };
 
     String[] valores = { usuario, rol };
 
     ArrayList listaNodos = JCYLUtilidadesXML.buscarNodos("usuariosRoles", "asignacion", atributos, valores);
     if ((listaNodos == null) || (listaNodos.size() == 0))
     {
       System.out.println("No se encuentra ningun rol para el usuario, " + usuario + ", rol " + rol);
 
       return "0";
     }
     if (listaNodos.size() > 1)
     {
       System.out.println("El usuario , " + usuario + ",tiene mas de una asignacion dentro del rol " + rol);
 
       return "0";
     }
     atributos = new String[2];
     atributos[0] = "c_grupo_id";
     atributos[1] = "c_funcion_id";
     valores = new String[2];
     valores[0] = rol;
     valores[1] = funcion;
     listaNodos = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     if (listaNodos.size() > 0)
     {
       return "1";
     }
 
     return "0";
   }
 
   
   public HashMap getPermisos(String usuario)
   {
     return null;
   }
 
   
   public Hashtable getFuncionesPantalla(String apli, String rol)
     throws Exception
   {
     Hashtable funcionespantalla = new Hashtable();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     String[] atributos = { "c_grupo_id", "c_menu_id" };
 
     String[] valores = { rol, "N" };
 
     ArrayList listaRolesFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     if (listaRolesFunciones != null)
     {
       for (int ii = 0; ii < listaRolesFunciones.size(); ii++)
       {
         String idFuncion = JCYLUtilidadesXML.getValor((Node)listaRolesFunciones.get(ii), "c_funcion_id");
         Node funcion = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", idFuncion);
         if (funcion != null)
         {
           String dProceso = JCYLUtilidadesXML.getValor(funcion, "d_proceso");
           funcionespantalla.put(idFuncion, dProceso);
         }
       }
 
     }
 
     return funcionespantalla;
   }
 
   
   public Hashtable getFuncionesMenu(String apli, String rol)
     throws Exception
   {
     Hashtable funcionesmenu = new Hashtable();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     String[] atributos = { "c_grupo_id", "c_menu_id" };
 
     String[] valores = { rol, "S" };
 
     ArrayList listaRolesFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     if (listaRolesFunciones != null)
     {
       for (int ii = 0; ii < listaRolesFunciones.size(); ii++)
       {
         String idFuncion = JCYLUtilidadesXML.getValor((Node)listaRolesFunciones.get(ii), "c_funcion_id");
         Node funcion = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", idFuncion);
         if (funcion != null)
         {
           String dProceso = JCYLUtilidadesXML.getValor(funcion, "d_proceso");
           funcionesmenu.put(idFuncion, dProceso);
         }
       }
 
     }
 
     return funcionesmenu;
   }
 
   
   public Hashtable getFuncionesMenu(String apli, String rol, String padre)
     throws Exception
   {
     Hashtable funcionesmenu = new Hashtable();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     String[] atributos = { "c_grupo_id", "c_menu_id", "c_funcion_padre" };
 
     String[] valores = { rol, "S", padre };
 
     ArrayList listaRolesFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     if (listaRolesFunciones != null)
     {
       for (int ii = 0; ii < listaRolesFunciones.size(); ii++)
       {
         String idFuncion = JCYLUtilidadesXML.getValor((Node)listaRolesFunciones.get(ii), "c_funcion_id");
         Node funcion = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", idFuncion);
         if (funcion != null)
         {
           String dProceso = JCYLUtilidadesXML.getValor(funcion, "d_proceso");
           funcionesmenu.put(idFuncion, dProceso);
         }
       }
 
     }
 
     return funcionesmenu;
   }
 
   
   public HashMap getParamus(String usr, String apli)
     throws Exception
   {
     HashMap paramus = getValoresParametros(usr, apli);
 
     return paramus;
   }
 
   
   public HashMap getParamaps(String apli)
     throws Exception
   {
     HashMap paramaps = new HashMap();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     NodeList listaNodos = JCYLUtilidadesXML.buscarHijos("paramAplicacion", "parametro");
     if (listaNodos != null)
     {
       for (int ii = 0; ii < listaNodos.getLength(); ii++)
       {
         Node nodo = listaNodos.item(ii);
         paramaps.put(JCYLUtilidadesXML.getValor(nodo, "c_parametro"), JCYLUtilidadesXML.getValor(nodo, "d_valor"));
       }
     }
 
     HashMap hashmap = paramaps;
 
     return hashmap;
   }
 
   private ArrayList getEstructura(String apli, String rol, String funcionPadre, String tMenu)
     throws Exception
   {
     ArrayList listaEstructura = new ArrayList();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     String[] atributos = { "c_grupo_id", "c_menu_id" };
 
     String[] valores = { rol, tMenu };
 
     ArrayList listaFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     listaFunciones = JCYLUtilidadesXML.ordenarListaNodos(listaFunciones, "n_orden");
     if (listaFunciones != null)
     {
       for (int ii = 0; ii < listaFunciones.size(); ii++)
       {
         Node nodo = (Node)listaFunciones.get(ii);
         if (JCYLUtilidadesXML.getValor(nodo, "c_funcion_padre").equals(funcionPadre))
         {
           Node funcion = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"));
           if (funcion != null)
           {
             Funcion funcionOV = new Funcion();
             funcionOV.setCFuncionId(JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"));
             funcionOV.setBMenu("S");
             funcionOV.setCFuncionPadre(JCYLUtilidadesXML.getValor(nodo, "c_funcion_padre"));
             funcionOV.setDDescripcion(JCYLUtilidadesXML.getValor(funcion, "d_descripcion"));
             funcionOV.setDNombre(JCYLUtilidadesXML.getValor(funcion, "d_nombre"));
             String dProceso = JCYLUtilidadesXML.getValor(funcion, "d_proceso");
             funcionOV.setDProceso(dProceso);
             funcionOV.setNOrden(Integer.parseInt(JCYLUtilidadesXML.getValor(nodo, "n_orden")));
             funcionOV.setNProfundidad(Integer.parseInt(JCYLUtilidadesXML.getValor(nodo, "n_profundidad")));
             listaEstructura.add(funcionOV);
             ArrayList tempLista = getEstructura(apli, rol, JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"), tMenu);
             listaFunciones.addAll(tempLista);
           }
         }
       }
     }
 
     return listaEstructura;
   }
 
   public ArrayList getEstructura(String apli, String rol)
     throws Exception
   {
     ArrayList listaEstructura = getEstructura(apli, rol, "S");
 
     return listaEstructura;
   }
 
   public ArrayList getEstructura(String apli, String rol, String tmenu)
     throws Exception
   {
     ArrayList listaEstructura = new ArrayList();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     String[] atributos = { "c_grupo_id", "c_menu_id" };
 
     String[] valores = { rol, tmenu };
 
     ArrayList listaFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     listaFunciones = JCYLUtilidadesXML.ordenarListaNodos(listaFunciones, "n_orden");
     if (listaFunciones != null)
     {
       for (int ii = 0; ii < listaFunciones.size(); ii++)
       {
         Node nodo = (Node)listaFunciones.get(ii);
         if (JCYLUtilidadesXML.getValor(nodo, "c_funcion_padre").equals("0"))
         {
           Node funcion = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"));
           if (funcion != null)
           {
             Funcion funcionOV = new Funcion();
             funcionOV.setCFuncionId(JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"));
             funcionOV.setBMenu("S");
             funcionOV.setCFuncionPadre(JCYLUtilidadesXML.getValor(nodo, "c_funcion_padre"));
             funcionOV.setDDescripcion(JCYLUtilidadesXML.getValor(funcion, "d_descripcion"));
             funcionOV.setDNombre(JCYLUtilidadesXML.getValor(funcion, "d_nombre"));
             String dProceso = JCYLUtilidadesXML.getValor(funcion, "d_proceso");
             funcionOV.setDProceso(dProceso);
             funcionOV.setNOrden(Integer.parseInt(JCYLUtilidadesXML.getValor(nodo, "n_orden")));
             funcionOV.setNProfundidad(Integer.parseInt(JCYLUtilidadesXML.getValor(nodo, "n_profundidad")));
             listaEstructura.add(funcionOV);
             ArrayList tempList = getEstructura(apli, rol, JCYLUtilidadesXML.getValor(nodo, "c_funcion_id"), "S");
             listaEstructura.addAll(tempList);
           }
         }
       }
     }
 
     return listaEstructura;
   }
 
   public Hashtable getFuncionesTodas(String apli, String rol)
     throws Exception
   {
     Hashtable hashFunciones = new Hashtable();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     ArrayList listaRolesFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", "c_grupo_id", rol);
     if (listaRolesFunciones != null)
     {
       for (int ii = 0; ii < listaRolesFunciones.size(); ii++)
       {
         Node funcion = (Node)listaRolesFunciones.get(ii);
         String idFuncion = JCYLUtilidadesXML.getValor(funcion, "c_funcion_id");
         Node nodo = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", idFuncion);
         if (nodo != null)
         {
           String dProceso = JCYLUtilidadesXML.getValor(nodo, "d_proceso");
           int pos_I = dProceso.indexOf("?");
           if (pos_I > 0)
             hashFunciones.put(idFuncion, dProceso.substring(0, pos_I));
           else {
             hashFunciones.put(idFuncion, dProceso);
           }
         }
       }
     }
     return hashFunciones;
   }
 
   public Hashtable getFuncionesTipo(String apli, String rol, String tMenu)
     throws Exception
   {
     Hashtable hashFunciones = new Hashtable();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     String[] atributos = { "c_grupo_id", "c_menu_id" };
 
     String[] valores = { rol, tMenu };
 
     ArrayList listaRolesFunciones = JCYLUtilidadesXML.buscarNodos("rolesFunciones", "asignacion", atributos, valores);
     if (listaRolesFunciones != null)
     {
       for (int ii = 0; ii < listaRolesFunciones.size(); ii++)
       {
         Node funcion = (Node)listaRolesFunciones.get(ii);
         String idFuncion = JCYLUtilidadesXML.getValor(funcion, "c_funcion_id");
         Node nodo = JCYLUtilidadesXML.buscarNodo("funciones", "funcion", "c_funcion_id", idFuncion);
         if (nodo != null)
         {
           String dProceso = JCYLUtilidadesXML.getValor(nodo, "d_proceso");
           hashFunciones.put(idFuncion, dProceso);
         }
       }
 
     }
 
     return hashFunciones;
   }
 
   public HashMap getValoresParametros(String usr, String apli)
     throws Exception
   {
     HashMap hashValores = new HashMap();
     NodeList listaAplicaciones = JCYLUtilidadesXML.getNodosRaiz();
     boolean existeAplicacion = false;
     int i = 0;
 
     while (i < listaAplicaciones.getLength())
     {
       Node nodo = listaAplicaciones.item(i);
       if (JCYLUtilidadesXML.getValor(nodo, "c_aplicacion_id").equals(apli))
       {
         existeAplicacion = true;
         break;
       }
       i++;
     }
     if (!existeAplicacion)
       throw new Exception("La aplicación '" + apli + "' no existe en el fichero de estructura");
     Node nodoRol = JCYLUtilidadesXML.buscarNodo("usuariosRoles", "asignacion", "c_usr_id", usr);
     String rolUsuario = JCYLUtilidadesXML.getValor(nodoRol, "c_grupo_id");
     NodeList listaParametros = JCYLUtilidadesXML.buscarHijos("paramAplicacion", "parametro");
     for (int ii = 0; ii < listaParametros.getLength(); ii++)
     {
       Node nodo = listaParametros.item(ii);
       String[] atributos = { "c_parametro", "c_usr_id" };
 
       String[] valores = { JCYLUtilidadesXML.getValor(nodo, "c_parametro"), usr };
 
       Node nodoTempUsu = JCYLUtilidadesXML.buscarNodo("paramUsuarios", "parametro", atributos, valores);
       if (nodoTempUsu != null)
       {
         hashValores.put(JCYLUtilidadesXML.getValor(nodo, "c_parametro"), JCYLUtilidadesXML.getValor(nodoTempUsu, "d_valor"));
       }
       else {
         atributos[1] = "c_grupo_id";
         valores[1] = rolUsuario;
         Node nodoTempRol = JCYLUtilidadesXML.buscarNodo("paramGrupos", "parametro", atributos, valores);
         if (nodoTempRol != null)
           hashValores.put(JCYLUtilidadesXML.getValor(nodo, "c_parametro"), JCYLUtilidadesXML.getValor(nodoTempRol, "d_valor"));
         else {
           hashValores.put(JCYLUtilidadesXML.getValor(nodo, "c_parametro"), JCYLUtilidadesXML.getValor(nodo, "d_valor"));
         }
       }
     }
     return hashValores;
   }
 
   public static void main(String[] args)
   {
     String pathBase = "D:/oracle/oraDev/jdev/mywork/SeguFrmWs/SeguFrmPrj/web/WEB-INF";
     JCYLUtilidadesXML.iniciarUtilidades(pathBase);
     OADAutorizacion oad = new OADAutorizacion();
     try
     {
       String grupo = oad.getGrupo("rc", "VACU");
       System.out.println("Grupo: " + grupo);
       Hashtable grupos = oad.getGrupos("VACU");
       System.out.println("grupos.size():" + grupos.size());
       HashMap usuarios = oad.getUsuarios("VACU");
       System.out.println("usuarios.size():" + usuarios.size());
       HashMap aplicaciones = oad.getAplicaciones("rc");
       System.out.println("aplicaciones.size():" + aplicaciones.size());
       System.out.println(oad.estaAutorizado("rc", "RC", "VACU", "VACU_01"));
       System.out.println(oad.estaAutorizado("usu1", "R1", "kk", "PRUE_02"));
       System.out.println(oad.estaAutorizado("usu1", "R2", "PRUE", "PRUE_01"));
       Hashtable funcionesPantalla = oad.getFuncionesPantalla("VACU", "SC");
       System.out.println("funcionespantalla.size():" + funcionesPantalla.size());
       funcionesPantalla = oad.getFuncionesPantalla("VACU", "RST");
       System.out.println("funcionespantalla.size():" + funcionesPantalla.size());
       funcionesPantalla = oad.getFuncionesPantalla("VACU", "ST");
       System.out.println("funcionespantalla.size():" + funcionesPantalla.size());
       funcionesPantalla = oad.getFuncionesPantalla("VACU", "RC");
       System.out.println("funcionespantalla.size():" + funcionesPantalla.size());
       Hashtable funcionesMenu = oad.getFuncionesMenu("VACU", "SC");
       System.out.println("funcionesmenu.size():" + funcionesMenu.size());
       funcionesMenu = oad.getFuncionesMenu("VACU", "ST");
       System.out.println("funcionesmenu.size():" + funcionesMenu.size());
       funcionesMenu = oad.getFuncionesMenu("VACU", "RST");
       System.out.println("funcionesmenu.size():" + funcionesMenu.size());
       funcionesMenu = oad.getFuncionesMenu("VACU", "RC");
       System.out.println("funcionesmenu.size():" + funcionesMenu.size());
       String menu = oad.getCadenaFuncionesMenu("VACU", "ST", "VACU_01");
       System.out.println("Cadena funcionesMenu:" + menu);
       Menu menu1 = oad.getMenu("sc", "SC", "VACU");
       System.out.println("Cadena funcionesMenu:" + menu1.getFunciones());
       HashMap paramUsu = oad.getParamus("st", "VACU");
       System.out.println("paramUsu.size():" + paramUsu.size());
       paramUsu = oad.getParamus("rc", "VACU");
       System.out.println("paramUsu.size():" + paramUsu.size());
       paramUsu = oad.getParamus("rst", "VACU");
       System.out.println("paramUsu.size():" + paramUsu.size());
       paramUsu = oad.getParamus("sc", "VACU");
       System.out.println("paramUsu.size():" + paramUsu.size());
       HashMap paramAps = oad.getParamaps("VACU");
       System.out.println("paramAps.size():" + paramAps.size());
       ArrayList estructura = oad.getEstructura("VACU", "ST");
       System.out.println("estructura.size():" + estructura.size());
       for (int i = 0; i < estructura.size(); i++)
       {
         Funcion funcionOV = (Funcion)estructura.get(i);
         System.out.println("C_funcion_id: " + funcionOV.getCFuncionId());
       }
 
       estructura = oad.getEstructura("VACU", "RST");
       System.out.println();
       System.out.println("estructura.size():" + estructura.size());
       for (int i = 0; i < estructura.size(); i++)
       {
         Funcion funcionOV = (Funcion)estructura.get(i);
         System.out.println("C_funcion_id: " + funcionOV.getCFuncionId());
       }
 
       estructura = oad.getEstructura("VACU", "RC");
       System.out.println();
       System.out.println("estructura.size():" + estructura.size());
       for (int i = 0; i < estructura.size(); i++)
       {
         Funcion funcionOV = (Funcion)estructura.get(i);
         System.out.println("C_funcion_id: " + funcionOV.getCFuncionId());
       }
 
       estructura = oad.getEstructura("VACU", "SC");
       System.out.println();
       System.out.println("estructura.size():" + estructura.size());
       for (int i = 0; i < estructura.size(); i++)
       {
         Funcion funcionOV = (Funcion)estructura.get(i);
         System.out.println("C_funcion_id: " + funcionOV.getCFuncionId());
       }
 
       Hashtable funcionesTodas = oad.getFuncionesTodas("VACU", "SC");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTodas("VACU", "ST");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTodas("VACU", "RST");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTodas("VACU", "RC");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       System.out.println();
       funcionesTodas = oad.getFuncionesTipo("VACU", "SC", "S");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "ST", "S");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "RST", "S");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "RC", "S");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       System.out.println();
       funcionesTodas = oad.getFuncionesTipo("VACU", "SC", "N");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "ST", "N");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "RST", "N");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "RC", "N");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       System.out.println();
       funcionesTodas = oad.getFuncionesTipo("VACU", "SC", "X");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "ST", "X");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "RST", "X");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       funcionesTodas = oad.getFuncionesTipo("VACU", "RC", "X");
       System.out.println("funcionesTodas.size():" + funcionesTodas.size());
       HashMap valoresParametros = oad.getValoresParametros("rc", "VACU");
       System.out.println("valoresParametros.size():" + valoresParametros.size());
       valoresParametros = oad.getValoresParametros("rst", "VACU");
       System.out.println("valoresParametros.size():" + valoresParametros.size());
       valoresParametros = oad.getValoresParametros("st", "VACU");
       System.out.println("valoresParametros.size():" + valoresParametros.size());
       Hashtable hs = oad.getFuncionesMenu("VACU", "RC", "VACU_01");
       System.out.println("funcionesmenu.size():" + hs.size());
       hs = oad.getFuncionesMenu("VACU", "ST", "VACU_01");
       System.out.println("funcionesmenu.size():" + hs.size());
       hs = oad.getFuncionesMenu("VACU", "RST", "VACU_01");
       System.out.println("funcionesmenu.size():" + hs.size());
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 }


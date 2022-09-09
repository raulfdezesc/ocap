package es.jcyl.fqs.ocap.util;

import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;


public class Pagina implements java.io.Serializable {

    
	private static final long serialVersionUID = 6090553047202447187L;
	
    public static final int DEFAULT_REGISTROS_POR_PAGINA = 10;
    public static final int DEFAULT_REGISTROS_POR_PAGINA_SOLIC = 20;

    
    public static final String PRIMER_REGISTRO_PARAMETER_NAME = "iRegistro";

    
    public static final String REGISTROS_PAGINA_PARAMETER_NAME = "nRegistrosP";
    

    
    public static final int DEFAULT_PRIMER_REGISTRO = 1;

    
    protected Collection elementos;

    
    protected int nRegistros;

    
    protected int registroActual = DEFAULT_PRIMER_REGISTRO;

    
    protected int nPaginas;

    
    protected int paginaActual;

    
    protected boolean siguiente;

    
    protected boolean anterior;

    
    protected int registrosPorPagina = DEFAULT_REGISTROS_POR_PAGINA;

    
    protected int siguienteRegistro;

    
    protected int anteriorRegistro;

    
    protected int primerRegistro;

    
    protected int ultimoRegistro;

    
    protected String queryString;

    
    public Pagina(String path, HttpServletRequest request) {
        // Obtenemos un Iterator 
        StringBuffer tmp = new StringBuffer(path);
        Enumeration parameters = request.getParameterNames();
        int primero = 1;
        // Si tiene elementos
        if ( parameters.hasMoreElements() ) {
            tmp.append("?");
            
            while ( parameters.hasMoreElements() ) {
                String name = (String)parameters.nextElement();
                
                // Como vamos a sobreescribir iRegistro y nRegistrosP 
                // no los escribimos ya que entonces los tendríamos 
                // duplicados
                if ( !name.equals(this.REGISTROS_PAGINA_PARAMETER_NAME) &&
                     !name.equals(this.PRIMER_REGISTRO_PARAMETER_NAME) ) {
                     
                    String [] values = request.getParameterValues(name);
                    for(int i=0;i<values.length;i++) {
                        if ( primero!=1) 
                          tmp.append("&");
                        primero++;
                        tmp.append(name).append("=").append(values[i]);
                        //if(i!=values.length-1)
                          //  tmp.append("&");
                    }
                    
                    
                }
            
            }
        }
        queryString = tmp.toString();        
    }

    
    public Collection getElementos() {
        return elementos;
    }

    
    public void setElementos(Collection newElementos) {
        elementos = newElementos;
        refresh();
    }

         
    public int getNRegistros() {
        return nRegistros;
    }

    
    public void setNRegistros(int newNRegistros) {
        nRegistros = newNRegistros;
        refresh();        
    }

    
    public int getRegistroActual() {
        return registroActual;
    }

    
    public void setRegistroActual(int newRegistroActual) {
        registroActual = newRegistroActual;
        refresh();
    }

    
    public int getRegistrosPorPagina() {
        return registrosPorPagina;
    }

    
    public void setRegistrosPorPagina(int newRegistrosPorPagina) {
        registrosPorPagina = newRegistrosPorPagina;
        refresh();        
    }
    

    
    public int getNPaginas() {
        return nPaginas;
    }

    
    public int getPaginaActual() {
        return paginaActual;
    }


    
    public boolean isSiguiente() {
        return siguiente;
    }

    
    public boolean isAnterior() {
        return anterior;
    }

    
    public int getSiguienteRegistro() {
        return siguienteRegistro;
    }

    
    public int getAnteriorRegistro() {
        return anteriorRegistro;
    }

    
    public int getPrimerRegistro() {
        return primerRegistro;
    }

    
    public int getUltimoRegistro() {
        return ultimoRegistro;
    }

    
    public String getURLPaginaSiguiente() {
        if ( siguiente ) {
            
            StringBuffer tmp = new StringBuffer(queryString);
            if ( queryString.indexOf("?") == -1 ) {
                tmp.append("?");
            } else {
                tmp.append("&");
            }
            tmp.append(PRIMER_REGISTRO_PARAMETER_NAME).append("=")
                .append(siguienteRegistro);
            tmp.append("&").append(REGISTROS_PAGINA_PARAMETER_NAME).append("=")
                .append(registrosPorPagina);
            return tmp.toString();
        } else {
            return null;
        }
    }

    
    public String getURLPaginaAnterior() {
        if ( anterior ) {
            StringBuffer tmp = new StringBuffer(queryString);
            if ( queryString.indexOf("?") == -1 ) {
                tmp.append("?");
            } else {
                tmp.append("&");
            }
            tmp.append(PRIMER_REGISTRO_PARAMETER_NAME).append("=")
                .append(anteriorRegistro);
            tmp.append("&").append(REGISTROS_PAGINA_PARAMETER_NAME).append("=")
                .append(registrosPorPagina);
            return tmp.toString();
        } else {
            return null;
        }

    }

    
    public String getURLPrimeraPagina() {
        StringBuffer tmp = new StringBuffer(queryString);
        if ( queryString.indexOf("?") == -1 ) {
            tmp.append("?");
        } else {
            tmp.append("&");
        }
        tmp.append(PRIMER_REGISTRO_PARAMETER_NAME).append("=")
            .append(primerRegistro);
        tmp.append("&").append(REGISTROS_PAGINA_PARAMETER_NAME).append("=")
            .append(registrosPorPagina);
        return tmp.toString();
    }

    
    public String getURLUltimaPagina() {
        StringBuffer tmp = new StringBuffer(queryString);
        if ( queryString.indexOf("?") == -1 ) {
            tmp.append("?");
        } else {
            tmp.append("&");
        }
        tmp.append(PRIMER_REGISTRO_PARAMETER_NAME).append("=")
            .append(ultimoRegistro);
        tmp.append("&").append(REGISTROS_PAGINA_PARAMETER_NAME).append("=")
            .append(registrosPorPagina);
        return tmp.toString();
    }
    
    
    
    private void refresh() {
        // Las páginas las contamos desde 1 y los registros desde 0
        if (nRegistros % registrosPorPagina == 0) {
            nPaginas = nRegistros / registrosPorPagina;
        }else{
            nPaginas = nRegistros / registrosPorPagina + 1; // Contamos desde 1
        } 
        paginaActual = registroActual / registrosPorPagina +1; 
        anterior = (paginaActual == 1) ? false : true;
        siguiente = (paginaActual == nPaginas) ? false : true;
        if ( siguiente ) {
            siguienteRegistro = registroActual + registrosPorPagina;
        } else {
            siguienteRegistro = DEFAULT_PRIMER_REGISTRO;
        }
        if ( anterior ) {
            anteriorRegistro = registroActual - registrosPorPagina;
            if (anteriorRegistro == 0) {
               anteriorRegistro = DEFAULT_PRIMER_REGISTRO;
            }
        } else {
            anteriorRegistro = DEFAULT_PRIMER_REGISTRO;
        }
        primerRegistro = 1; // Este es facil ;D
        ultimoRegistro = (nPaginas - 1) * registrosPorPagina + 1;
    }



}
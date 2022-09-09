package es.jcyl.framework;

import es.jcyl.cf.seguridad.util.Usuario;
import java.io.Serializable;
import java.util.*;

public class JCYLUsuario
    implements Serializable
{

    
	private static final long serialVersionUID = 1288514345000057918L;
	public JCYLUsuario()
    {
        user = null;
        parametrosUsuario = null;
        uri = null;
        ipRemota = null;
    }

    public JCYLUsuario(Usuario user)
    {
        this.user = null;
        parametrosUsuario = null;
        uri = null;
        ipRemota = null;
        this.user = user;
    }

    public Usuario getUser()
    {
        Thread thread = Thread.currentThread();
        Usuario usu = (Usuario)users.get(thread);
        if(usu == null)
        {
            usu = getUsuario();
            if(usu != null)
                users.put(thread, usu);
        }
        return usu;
    }

    public void setUser(Usuario us)
    {
        Thread thread = Thread.currentThread();
        Usuario usu = (Usuario)users.get(thread);
        if(usu == null)
        {
            usu = us;
            users.put(thread, us);
        }
    }

    public void freeUser()
    {
        if(user == null)
        {
            Thread t = Thread.currentThread();
            users.remove(t);
            //System.out.println("INFO: Liberado Usuario del thread actual = " + t.getName());
        } else
        if(user != null && users.containsValue(user))
        {
            Enumeration valor = users.elements();
            Enumeration key = users.elements();
            Thread thread;
            while(valor.hasMoreElements()) 
                if(((JCYLUsuario)valor.nextElement()).getUsuario().equals(user))
                    users.remove((Thread)key.nextElement());
                else
                    thread = (Thread)key.nextElement();
        }
    }

    public void freeUserAll()
    {
        for(Enumeration key = users.elements(); key.hasMoreElements(); users.remove((Thread)key.nextElement()));
    }

    public Usuario getUsuario()
    {
        return user;
    }

    public void setUsuario(Usuario user)
    {
        this.user = user;
    }

    public void setParametrosUsuario(Map map)
    {
        parametrosUsuario = map;
    }

    public Map getParametrosUsuario()
    {
        return parametrosUsuario;
    }

    public String getIpRemota()
    {
        return ipRemota;
    }

    public String getUri()
    {
        return uri;
    }

    public void setIpRemota(String nuevo)
    {
        ipRemota = nuevo;
    }

    public void setUri(String nuevo)
    {
        uri = nuevo;
    }

    private Hashtable users = new Hashtable();
    private Usuario user;
    private Map parametrosUsuario;
    private String uri;
    private String ipRemota;

}
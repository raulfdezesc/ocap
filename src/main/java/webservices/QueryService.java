package webservices;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface QueryService extends Service
{
  public abstract String getQueryPortAddress();

  public abstract Query getQueryPort()
    throws ServiceException;

  public abstract Query getQueryPort(URL paramURL)
    throws ServiceException;
}


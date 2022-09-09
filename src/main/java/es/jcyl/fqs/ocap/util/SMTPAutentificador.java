 package es.jcyl.fqs.ocap.util;
 
 import java.io.IOException;

import javax.mail.Authenticator;
 import javax.mail.PasswordAuthentication;
 
 public class SMTPAutentificador extends Authenticator
 {
   boolean bDesa;
 
   public SMTPAutentificador(boolean desa)
   {
     this.bDesa = desa;
   }
 
   protected PasswordAuthentication getPasswordAuthentication()
   {
	   
     
    
	try {
		 String correoCAU = PropiedadesConfigurables.getProperty("USUARIO");	     
	     String passwordCAU = PropiedadesConfigurables.getProperty("PASSWORD");
	     return new PasswordAuthentication(correoCAU, passwordCAU);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
     return null;
   }
 }


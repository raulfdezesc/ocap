 package es.jcyl.fqs.ocap.actionforms;
 
 import javax.servlet.http.HttpServletRequest;
 import org.apache.struts.action.ActionError;
 import org.apache.struts.action.ActionErrors;
 import org.apache.struts.action.ActionMapping;
 import org.apache.struts.validator.ValidatorForm;
 
 public class OCAPCambioPasswordForm extends ValidatorForm
 {
   private String passwordViejo;
   private String passwordNuevo;
   private String passwordNuevo2;
 
   private void $init$()
   {
     this.passwordViejo = "";
     this.passwordNuevo = "";
     this.passwordNuevo2 = "";
   }
 
   public String getPasswordViejo()
   {
     return this.passwordViejo;
   }
 
   public String getPasswordNuevo()
   {
     return this.passwordNuevo;
   }
 
   public String getPasswordNuevo2()
   {
     return this.passwordNuevo2;
   }
 
   public void setPasswordViejo(String passwordViejo)
   {
     this.passwordViejo = passwordViejo;
   }
 
   public void setPasswordNuevo(String passwordNuevo)
   {
     this.passwordNuevo = passwordNuevo;
   }
 
   public void setPasswordNuevo2(String passwordNuevo2)
   {
     this.passwordNuevo2 = passwordNuevo2;
   }
 
   public void reset(ActionMapping mapping, HttpServletRequest request)
   {
   }
 
   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
   {
     ActionErrors errores = super.validate(mapping, request);
     if (errores == null) {
       errores = new ActionErrors();
     }
     if (this.passwordNuevo.length() < 6) {
       errores.add("passwordNuevo", new ActionError("error.password.corto"));
     }
     if (!this.passwordNuevo.equals(this.passwordNuevo2)) {
       errores.add("passwordNuevo2", new ActionError("error.password.no.coinciden"));
     }
 
     return errores;
   }
 
   public OCAPCambioPasswordForm()
   {
     $init$();
   }
 }


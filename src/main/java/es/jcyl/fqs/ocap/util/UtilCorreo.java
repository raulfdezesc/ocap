package es.jcyl.fqs.ocap.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class UtilCorreo {
	public static final String PROTOCOLO_TRANSPORTE = "mail.transport.protocol";
	public static final String SMTP = "smtp";
	public static final String CLAVE_DIR_SERVIDOR_CORREO = "mail.smtp.host";
	public static final String CLAVE_PUERTO_SERVIDOR_CORREO = "mail.smtp.port";
	public static final String CLAVE_AUTENTIFICACION = "mail.smtp.auth";
	public static final String CONTENIDO_TEXTO = "text/plain";
	public static final String CONTENIDO_HTML = "text/html";
	private static Transport transport;

	public static void enviar(String host, String puerto, String remitente, String destinatario, String asunto,
			String contenido, String tipoContenido) throws Exception {
		try {
			Properties props = new Properties();
			props.put(PROTOCOLO_TRANSPORTE, SMTP);
			props.put(CLAVE_DIR_SERVIDOR_CORREO, host);
			props.put(CLAVE_PUERTO_SERVIDOR_CORREO, puerto);
			props.put(CLAVE_AUTENTIFICACION, "true");
			Authenticator auth = new SMTPAutentificador(false);

			Session sesion = Session.getInstance(props, auth);

			Message mensaje = new MimeMessage(sesion);

			mensaje.setFrom(new InternetAddress(remitente));

			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));

			mensaje.setSubject(asunto);

			mensaje.setContent(contenido, tipoContenido);

			transport = sesion.getTransport(SMTP);
			transport.connect();
			Transport.send(mensaje, mensaje.getAllRecipients());
			transport.close();
		} catch (Exception e) {

			throw e;
		}
	}

	public static void enviar(String host, String puerto, String remitente, ArrayList lista, String asunto,
			String contenido, String tipoContenido) throws SendFailedException, Exception {
		try {
			Properties props = new Properties();
			props.put(PROTOCOLO_TRANSPORTE, "smtp");
			props.put(CLAVE_DIR_SERVIDOR_CORREO, host);
			props.put(CLAVE_PUERTO_SERVIDOR_CORREO, puerto);
			props.put(CLAVE_AUTENTIFICACION, "true");

			Authenticator auth = new SMTPAutentificador(false);

			Session sesion = Session.getInstance(props, auth);

			Message mensaje = new MimeMessage(sesion);

			mensaje.setFrom(new InternetAddress(remitente));

			InternetAddress[] destinatarios = new InternetAddress[lista.size()];
			for (int i = 0; i < lista.size(); i++) {
				destinatarios[i] = new InternetAddress((String) lista.get(i));
			}
			mensaje.setRecipients(Message.RecipientType.TO, destinatarios);

			mensaje.setSubject(asunto);

			mensaje.setContent(contenido, tipoContenido);

			Transport transport = sesion.getTransport(SMTP);
			transport.connect();
			Transport.send(mensaje, mensaje.getAllRecipients());
			transport.close();
		} catch (SendFailedException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void enviarHtml(String host, String puerto, String remitente, String destinatario, String asunto,
			String contenido) throws SendFailedException, Exception {
		try {
			enviar(host, puerto, remitente, destinatario, asunto, contenido, CONTENIDO_HTML);
		} catch (SendFailedException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void enviarHtml(String host, String puerto, String remitente, ArrayList lista, String asunto,
			String contenido) throws Exception {
		enviar(host, puerto, remitente, lista, asunto, contenido, CONTENIDO_HTML);
	}

	public static void enviarHtmlYAdjunto(String host, String puerto, String remitente, String destinatario,
			String asunto, String contenido, String nombreFicheroAdjunto, byte[] contenidoFicheroAdjunto,
			byte[] contenidoFicheroAdjunto2, String nombreFicheroAdjunto2) throws SendFailedException, Exception {
		try {
			Properties props = new Properties();
			props.put(PROTOCOLO_TRANSPORTE, "smtp");
			props.put(CLAVE_DIR_SERVIDOR_CORREO, host);
			props.put(CLAVE_PUERTO_SERVIDOR_CORREO, puerto);
			props.put(CLAVE_AUTENTIFICACION, "true");

			Authenticator auth = new SMTPAutentificador(false);

			Session sesion = Session.getInstance(props, auth);

			MimeMessage mensaje = new MimeMessage(sesion);
			mensaje.setFrom(new InternetAddress(remitente));
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mensaje.setSubject(asunto);
			mensaje.setSentDate(new Date());

			contenido = contenido.replaceAll("á", "&aacute;");
			contenido = contenido.replaceAll("Á", "&Aacute;");
			contenido = contenido.replaceAll("é", "&eacute;");
			contenido = contenido.replaceAll("É", "&Eacute;");
			contenido = contenido.replaceAll("í", "&iacute;");
			contenido = contenido.replaceAll("Í", "&Iacute;");
			contenido = contenido.replaceAll("ó", "&oacute;");
			contenido = contenido.replaceAll("Ó", "&Oacute;");
			contenido = contenido.replaceAll("ú", "&uacute;");
			contenido = contenido.replaceAll("Ú", "&Uacute;");
			contenido = contenido.replaceAll("ñ", "&ntilde;");
			contenido = contenido.replaceAll("Ñ", "&Ntilde;");

			MimeBodyPart bodyPart = new MimeBodyPart();
			String lstrcontenidoMensaje = new String(contenido);

			bodyPart.setContent(lstrcontenidoMensaje, CONTENIDO_HTML);

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(bodyPart);

			MimeBodyPart bodyPartAdjunto = new MimeBodyPart();

			bodyPartAdjunto.setDataHandler(
					new DataHandler(new ByteArrayDataSource(contenidoFicheroAdjunto, "application/pdf")));

			bodyPartAdjunto.setFileName(nombreFicheroAdjunto);

			multipart.addBodyPart(bodyPartAdjunto);

			if (contenidoFicheroAdjunto2 != null) {
				MimeBodyPart bodyPartAdjunto2 = new MimeBodyPart();

				bodyPartAdjunto2.setDataHandler(
						new DataHandler(new ByteArrayDataSource(contenidoFicheroAdjunto2, "application/pdf")));

				bodyPartAdjunto2.setFileName(nombreFicheroAdjunto2);

				multipart.addBodyPart(bodyPartAdjunto2);
			}

			mensaje.setContent(multipart);
			mensaje.saveChanges();

			Transport transport = sesion.getTransport(SMTP);
			Transport.send(mensaje);
		} catch (SendFailedException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void enviar(String destinatario, String asunto, String contenidoHtml) throws Exception {
		try {
			Properties props = new Properties();
			props.put(PROTOCOLO_TRANSPORTE, PropiedadesConfigurables.getProperty("PROTOCOLO_CORREO"));
			props.put(CLAVE_DIR_SERVIDOR_CORREO, PropiedadesConfigurables.getProperty("SERVIDOR_CORREO"));
			props.put(CLAVE_PUERTO_SERVIDOR_CORREO, PropiedadesConfigurables.getProperty("PUERTO_CORREO"));
			props.put(CLAVE_AUTENTIFICACION, "true");
			Authenticator auth = new SMTPAutentificador(false);
			Session sesion = Session.getInstance(props, auth);
			Message mensaje = new MimeMessage(sesion);
			mensaje.setFrom(new InternetAddress(PropiedadesConfigurables.getProperty("EMAIL_REMITENTE")));
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mensaje.setSubject(asunto);
			
			mensaje.setContent(contenidoHtml, CONTENIDO_HTML);
			
			
			
			

			transport = sesion.getTransport(SMTP);
			transport.connect();
			Transport.send(mensaje, mensaje.getAllRecipients());
			transport.close();
		} catch (Exception e) {

			throw e;
		}
	}

}

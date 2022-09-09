package es.jcyl.fqs.ocap.util;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * Clases de utilizad para gestión de contraseñas
 * 
 * @author 09339852N
 *
 */
public class Seguridad {

	private static String NUMEROS = "0123456789";
	private static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	private static int PASS_LENGTH = 8;
	private static String SECRET_KEY = "35Z5C5MZdfwJznL";

	/**
	 * Generador aleatorio de contraseñas
	 * 
	 * @return Contraseña generada
	 */
	public static String getPassword() {

		String pswd = "";

		String key = NUMEROS + MAYUSCULAS + MINUSCULAS;

		for (int i = 0; i < PASS_LENGTH; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;

	}

	public static String encriptar(String texto) {

		String base64EncryptedString = "";

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(SECRET_KEY.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
		}
		return base64EncryptedString;
	}

	public static String desencriptar(String textoEncriptado) throws Exception {

		String base64EncryptedString = "";

		try {
			byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(SECRET_KEY.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");

			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(message);

			base64EncryptedString = new String(plainText, "UTF-8");

		} catch (Exception ex) {
		}
		return base64EncryptedString;
	}

}

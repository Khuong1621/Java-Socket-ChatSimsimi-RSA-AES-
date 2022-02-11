package guichatclient;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import javax.crypto.Cipher;

public class RSA {
	static Cipher cipher = null;
	
	public RSA() {}
	
	public static PrivateKey getPrivateKey(byte[] privateByte) throws Exception {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateByte);
		KeyFactory kf = KeyFactory.getInstance("RSA");
	    return kf.generatePrivate(spec);
	}
	
	public static PublicKey getPublicKey(byte[] publicByte) throws Exception {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(publicByte);
		KeyFactory kf = KeyFactory.getInstance("RSA");
	    return kf.generatePublic(spec);
	}
	
	public static String encrypt(String input, byte[] publicByte) {
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicByte));
			byte[] byteEncrypted = cipher.doFinal(input.getBytes());
			
			String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
			return encrypted;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return "encrypt unsuccessfully!";
	}
	
	public static String decrypt(String input, byte[] privateByte) {
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateByte));
			byte[] byteDencrypted = cipher.doFinal(Base64.getDecoder().decode(input));
			
			String decrypted = new String(byteDencrypted);
			return decrypted;
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		return "dencrypt unsuccessfully!";
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		GenerateKeys g = new GenerateKeys(1024);
		g.createKeys();
		String mh = RSA.encrypt("dihoc", g.getPublicKey().getEncoded());
		String gm = RSA.decrypt(mh, g.getPrivateKey().getEncoded());
		
		System.out.println(mh);
		System.out.println(gm);
	}
}

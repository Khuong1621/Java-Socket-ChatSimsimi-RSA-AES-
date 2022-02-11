package server;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class GenerateKeys {
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private byte[] publicByte;
	private byte[] privateByte;
	
	public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
	    this.keyGen = KeyPairGenerator.getInstance("RSA");
	    this.keyGen.initialize(keylength);
	}
	
	public void createKeys() {
	    this.pair = this.keyGen.generateKeyPair();
	    this.privateKey = pair.getPrivate();
	    this.publicKey = pair.getPublic();
	    
	    this.publicByte = this.publicKey.getEncoded();
	    this.privateByte = this.privateKey.getEncoded();
	    
	    
	}
	
  	public PrivateKey getPrivateKey() {
	    return this.privateKey;
  	}
		  
  	public PublicKey getPublicKey() {
	    return this.publicKey;
  	}
  	
  	public byte[] getPrivateByte() {
  		return this.privateByte;
  	}
  	
  	public byte[] getPublicByte() {
  		return this.publicByte;
  	}
  	
  	public static void main(String[] args) throws InvalidKeySpecException {
  		try {
  			GenerateKeys g = new GenerateKeys(1024);
  			g.createKeys();
  			System.out.println(g.getPublicKey().getEncoded());
  			String pubk = Base64.getEncoder().encodeToString(g.getPublicKey().getEncoded());
  			System.out.println(pubk);
  			System.out.println(pubk.getBytes());
  			
  			
  		} catch (NoSuchAlgorithmException e) {
  			System.err.println(e);
  		} catch (NoSuchProviderException e) {
  			e.printStackTrace();
  		}
  	}
}

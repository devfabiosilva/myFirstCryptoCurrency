package wallet;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {

	public PrivateKey privateKey;
	public PublicKey publicKey;

	public Wallet()
	{
		generateKeyPair();
	}
	
	public void generateKeyPair()
	{
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();
			
			privateKey=keyPair.getPrivate();
			publicKey=keyPair.getPublic();
		}	
		catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

package wallet;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

//public class Wallet {

	import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;

import dev.main.LCcoin;

public class Wallet {
		
		public PrivateKey privateKey;
		public PublicKey publicKey;
		
		public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //only UTXOs owned by this wallet.
		
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
		
	  //returns balance and stores the UTXO's owned by this wallet in this.UTXOs
		public float getBalance() {
			float total = 0;
	        for (Map.Entry<String, TransactionOutput> item: LCcoin.UTXOs.entrySet()){
	        	TransactionOutput UTXO = item.getValue();
	        	System.out.println("Obtendo balanço");
	        	System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(item));
	        	System.out.println(item);
	        	System.out.println("================");
	            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
	            	UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
	            	total += UTXO.value ; 
	            }
	        }  
			return total;
		}
		//Generates and returns a new transaction from this wallet.
		public Transaction sendFunds(PublicKey _recipient,float value ) {
			if(getBalance() < value) { //gather balance and check funds.
				System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
				return null;
			}
	    //create array list of inputs
			ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	    
			float total = 0;
			for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
				TransactionOutput UTXO = item.getValue();
				total += UTXO.value;
				inputs.add(new TransactionInput(UTXO.id));
				if(total > value) break;
			}
			
			Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
			newTransaction.generateSig(privateKey);
			
			for(TransactionInput input: inputs){
				UTXOs.remove(input.transactionOutputId);
			}
			return newTransaction;
		}
		
}
/*
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
*/
//}

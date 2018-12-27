package dev.main;

import java.security.Security;

//public class LCcoin {
//	public static ArrayList<Block> blockchain = new ArrayList<Block>();
//	private static int difficulty =5;
//	public static void main(String[] args) {
/*
		Block genesis = new Block("0", "Hi! Here is my previous Hash");
		System.out.println("Hash 0: "+ genesis.hash);
		
		Block h1 = new Block(genesis.hash, "Hello! Here is my second Hash");
		System.out.println("Hash 1: "+ h1.hash);
		
		Block h2 = new Block(h1.hash, "Hello! Here is my third Hash");
		System.out.println("Hash 2: "+ h2.hash);	
		
		Block h3 = new Block(h2.hash, "Hello! Here is my third Hash");
		System.out.println("Hash 3: "+ h3.hash);
*/
//		blockchain.add(new Block("0","Hi! I am the first block"));
//		System.out.println("Trying to mine genesis block ...");
//		blockchain.get(0).mineBlock(difficulty);

//		blockchain.add(new Block(blockchain.get(blockchain.size()-1).hash,"I am the second block"));
//		System.out.println("Trying to mine first block ...");
//		blockchain.get(1).mineBlock(difficulty);

//		blockchain.add(new Block(blockchain.get(blockchain.size()-1).hash,"I am the third block"));
//		System.out.println("Trying to mine second block ...");
//		blockchain.get(2).mineBlock(difficulty);

//		System.out.println("Is blockchain valid? "+isValid());

		
//		String blockchainJson= new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//		System.out.println(blockchainJson);
//	}
//	public static boolean isValid()
//	{
//		Block currentBlock;
//		Block previousBlock;
		
//		for (int i=1; i<blockchain.size();i++)
//		{
//			previousBlock=blockchain.get(i-1);
//			currentBlock=blockchain.get(i);
			
//			if (!currentBlock.hash.equals(currentBlock.calculateHash())) return false;
			
///			if (!previousBlock.hash.equals(currentBlock.previousHash)) return false ;
//			
//		}
//		return true;
//	}
//}

//import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.GsonBuilder;

import fdev.blockchain.Block;
import fdev.util.WalletDTO;
import wallet.Transaction;
import wallet.TransactionInput;
import wallet.TransactionOutput;
import wallet.Wallet;

public class LCcoin {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	
	public static int difficulty = 2;
	public static float minimumTransaction = 0.1f;
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;

	public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //Setup Bouncey castle as a Security Provider

		//Create wallets:
		walletA = new Wallet();
		walletB = new Wallet();		
		Wallet coinbase = new Wallet();
		
		//create genesis transaction, which sends 100 LCcoin to walletA: 
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSig(coinbase.privateKey);	 //manually sign the genesis transaction	
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //its important to store our first transaction in the UTXOs list.
		
		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);
		/*
		coinbaseJson =new GsonBuilder().setPrettyPrinting().create().toJson(coinbase);
		WalletAJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletA);
		WalletBJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletB);
		System.out.println("Coinbase");
		System.out.println(coinbaseJson);
		System.out.println("WalletA");
		System.out.println(WalletAJson);
		System.out.println("WalletB");
		System.out.println(WalletBJson);
		*/
		WalletDTO.showDetails("coinbase", coinbase);
		WalletDTO.showDetails("WalletA", walletA);
		WalletDTO.showDetails("WalletB", walletB);
		//testing
		Block block1 = new Block(genesis.hash);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		addBlock(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		WalletDTO.showDetails("coinbase", coinbase);
		WalletDTO.showDetails("WalletA", walletA);
		WalletDTO.showDetails("WalletB", walletB);
		/*
		coinbaseJson =new GsonBuilder().setPrettyPrinting().create().toJson(coinbase);
		WalletAJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletA);
		WalletBJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletB);
		System.out.println("Coinbase");
		System.out.println(coinbaseJson);
		System.out.println("WalletA");
		System.out.println(WalletAJson);
		System.out.println("WalletB");
		System.out.println(WalletBJson);
		*/
		
		Block block2 = new Block(block1.hash);
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		/*
		coinbaseJson =new GsonBuilder().setPrettyPrinting().create().toJson(coinbase);
		WalletAJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletA);
		WalletBJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletB);
		System.out.println("Coinbase");
		System.out.println(coinbaseJson);
		System.out.println("WalletA");
		System.out.println(WalletAJson);
		System.out.println("WalletB");
		System.out.println(WalletBJson);
		*/
		WalletDTO.showDetails("coinbase", coinbase);
		WalletDTO.showDetails("WalletA", walletA);
		WalletDTO.showDetails("WalletB", walletB);
		
		Block block3 = new Block(block2.hash);
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds( walletA.publicKey, 20));
		addBlock(block3);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		
		isChainValid();
		WalletDTO.showDetails("coinbase", coinbase);
		WalletDTO.showDetails("WalletA", walletA);
		WalletDTO.showDetails("WalletB", walletB);
		/*
		coinbaseJson =new GsonBuilder().setPrettyPrinting().create().toJson(coinbase);
		WalletAJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletA);
		WalletBJson = new GsonBuilder().setPrettyPrinting().create().toJson(walletB);
		System.out.println("Coinbase");
		System.out.println(coinbaseJson);
		System.out.println("WalletA");
		System.out.println(WalletAJson);
		System.out.println("WalletB");
		System.out.println(WalletBJson);
		*/
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
		System.out.println(blockchainJson);
		
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("#Current Hashes not equal");
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("#Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
			TransactionOutput tempOutput;
			for(int t=0; t <currentBlock.transactions.size(); t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);
				
				if(!currentTransaction.verifiySignature()) {
					System.out.println("#Signature on Transaction(" + t + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
					return false; 
				}
				
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					
					if(tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}
					
					if(input.UTXO.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
						return false;
					}
					
					tempUTXOs.remove(input.transactionOutputId);
				}
				
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}
				
				if( currentTransaction.outputs.get(0).reciepient != currentTransaction.recipient) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}
				
			}
			
		}
		System.out.println("Blockchain is valid");
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
/*
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions. 
	public static int difficulty = 5;
	public static float minimumTransaction = 0.1f;
	public static Wallet walletA;
	public static Wallet walletB;

	public static void main(String[] args) {	
		//Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		//Create the new wallets
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();
		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSig(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());
		
	}
*/
}

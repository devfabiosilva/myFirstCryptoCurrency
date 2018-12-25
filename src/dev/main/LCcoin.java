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

import fdev.blockchain.Block;
import fdev.util.StringUtil;
import wallet.Transaction;
import wallet.Wallet;

public class LCcoin {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
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
}

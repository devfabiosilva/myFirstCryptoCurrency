package fdev.blockchain;

import java.util.ArrayList;
import java.util.Date;

import fdev.util.StringUtil;
import wallet.Transaction;

//import java.util.ArrayList;
//import java.util.Date;

public class Block {
	
	public String hash;
	public String previousHash; 
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	public long timeStamp; //as number of milliseconds since 1/1/1970.
	public int nonce;
	
	//Block Constructor.  
	public Block(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.fSha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				merkleRoot
				);
		return calculatedhash;
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
	//Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((previousHash != "0")) {
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
	
}

/*
public class Block
{
	public String hash;
	public String previousHash;
	private String data;

	private long timestamp;
	private Integer nonce=0;

	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	
	public Block()
	{
		
	}

	public Block(String previousHash, String data) {
		super();
		this.previousHash = previousHash;
		this.data = data;
		this.timestamp=new Date().getTime();
		this.hash=calculateHash(); // This would be the last one
	}

	public void mineBlock(int difficulty)
	{
		String target = new String(new char[difficulty]).replace('\0','0');
		
		while (!hash.substring(0, difficulty).equals(target))
		{
			nonce++;
			hash=calculateHash();
		}
		System.out.println("Block mined! "+hash);
	}

	public String calculateHash()
	{
		String calcHash = StringUtil.fSha256(previousHash+data+Long.toString(timestamp)+nonce.toString());
		return calcHash;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

} */

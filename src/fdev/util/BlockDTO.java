package fdev.util;

import java.util.ArrayList;

import fdev.blockchain.Block;
import wallet.Transaction;

public class BlockDTO 
{
	public String hash;
	public String previousHash; 
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
	public long timeStamp; //as number of milliseconds since 1/1/1970.
	public int nonce;

	public BlockDTO()
	{
		
	}

	public BlockDTO(ArrayList<Block> blockchain) {
		super();
		/*for (Block i: blockchain)
		{
			
			for (Transaction j: i.transactions)
			{
				
			}
		}*/
	}
	
}

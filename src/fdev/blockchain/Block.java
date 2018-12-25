package fdev.blockchain;

import java.util.Date;

import fdev.util.StringUtil;

public class Block
{
	public String hash;
	public String previousHash;
	private String data;

	private long timestamp;
	private Integer nonce=0;
	
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

}

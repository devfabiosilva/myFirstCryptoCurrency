package fdev.util;

import java.util.HashMap;

import com.google.gson.GsonBuilder;

import wallet.TransactionOutput;
import wallet.Wallet;

public class WalletDTO 
{
	public String privateKey;
	public String publicKey;
	
	public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //only UTXOs owned by this wallet.

	public WalletDTO(String name, Wallet wallet) {
		super();
		this.privateKey = StringUtil.getStringFromKey(wallet.privateKey);
		this.publicKey = StringUtil.getStringFromKey(wallet.publicKey);
		UTXOs = wallet.UTXOs;
		
	}
	
	public static void showDetails(String name, Wallet wallet)
	{
		String WalletJson = new GsonBuilder().setPrettyPrinting().create().toJson(new WalletDTO(name, wallet));
		System.out.println(name);
		System.out.println(WalletJson);
	}
}

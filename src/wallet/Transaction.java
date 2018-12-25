package wallet;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import fdev.util.StringUtil;

public class Transaction {

	public String transactionId;
	public PublicKey sender;
	public PublicKey recipient;
	public float value;
	public byte[] sig;
	
	public ArrayList<TransactionInput> inputs=new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int seq=0;

	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs)
	{
		this.sender = from;
		this.recipient = to;
		this.value = value;
		this.inputs = inputs;
	}
	
	public String calulateHash() 
	{
		seq++;
		return StringUtil.fSha256
		(
				StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(recipient) +
				Float.toString(value) + seq
		);
	}
	
	public void generateSig(PrivateKey privateKey)
	{
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
		sig= StringUtil.applyECDSASig(privateKey,data);		
	}
	public boolean verifiySignature()
	{
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, sig);
	}
}

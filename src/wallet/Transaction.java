package wallet;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import dev.main.LCcoin;
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
	//Returns true if new transaction could be created.	
	public boolean processTransaction() {
			
			if(verifiySignature() == false) {
				System.out.println("#Transaction Signature failed to verify");
				return false;
			}
					
			//gather transaction inputs (Make sure they are unspent):
			for(TransactionInput i : inputs) {
				i.UTXO = LCcoin.UTXOs.get(i.transactionOutputId);
			}

			//check if transaction is valid:
			if(getInputsValue() < LCcoin.minimumTransaction) {
				System.out.println("#Transaction Inputs to small: " + getInputsValue());
				return false;
			}
			
			//generate transaction outputs:
			float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
			transactionId = calulateHash();
			outputs.add(new TransactionOutput( this.recipient, value,transactionId)); //send value to recipient
			outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender		

			//add outputs to Unspent list
			for(TransactionOutput o : outputs) {
				LCcoin.UTXOs.put(o.id , o);
			}
			
			//remove transaction inputs from UTXO lists as spent:
			for(TransactionInput i : inputs) {
				if(i.UTXO == null) continue; //if Transaction can't be found skip it 
				LCcoin.UTXOs.remove(i.UTXO.id);
			}
			
			return true;
		}
		
	//returns sum of inputs(UTXOs) values
		public float getInputsValue() {
			float total = 0;
			for(TransactionInput i : inputs) {
				if(i.UTXO == null) continue; //if Transaction can't be found skip it 
				total += i.UTXO.value;
			}
			return total;
		}

	//returns sum of outputs:
		public float getOutputsValue() {
			float total = 0;
			for(TransactionOutput o : outputs) {
				total += o.value;
			}
			return total;
	}
}

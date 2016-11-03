package io.github.apemanzilla.kwallet;

import io.github.apemanzilla.kwallet.types.Address;
import io.github.apemanzilla.kwallet.types.Transaction;
import io.github.apemanzilla.kwallet.util.HTTP;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import org.apache.commons.codec.digest.DigestUtils;

public class KristAPI {
	private URL remoteAPI;
	private String key;
	private String address;
	
	public KristAPI(URL remoteAPI, String privateKey) {
		this.remoteAPI = remoteAPI;
		this.key = privateKey;
		this.address = makeAddressV2(privateKey);
	}
	
	public long getBalance() throws NumberFormatException, MalformedURLException, IOException {
		return getBalance(address);
	}
	
	public long getBalance(String address) throws NumberFormatException, MalformedURLException, IOException {
		return Long.parseLong(HTTP.readURL(new URL(remoteAPI,"?getbalance="+address)));
	}
	
	public Transaction[] getTransactions(String address) throws MalformedURLException, IOException {
		String transactionData = HTTP.readURL(new URL(remoteAPI,"?listtx="+address));
		transactionData = transactionData.substring(0, transactionData.length() - 3).replace("\n", "").replace("\r", "");
		if ( transactionData.length() == 0) {
			return new Transaction[0];
		} else if ( (transactionData.length() % 31) == 0) {
			Transaction[] transactions = new Transaction[transactionData.length() / 31];
			for (int i = 0; i < transactionData.length() / 31; i++) {
				transactions[i] = new Transaction(transactionData.substring(i *  31, (i + 1) * 31), address);
			}
			return transactions;
		} else {
			return new Transaction[0];
		}
	}
	
	public Transaction[] getTransactions() throws MalformedURLException, IOException {
		return getTransactions(address);
	}
	
	public Address[] getRichList() throws MalformedURLException, IOException, ParseException {
		// Extra HTML tags seem to get caught without this regex
		String richList = HTTP.readURL(new URL(remoteAPI, "?richapi")).replaceAll("<[^>]*>", "");
		if (richList.length() == 0) {
			return new Address[0];
		} else if ((richList.length() % 29) == 0) {
			Address[] result = new Address[richList.length() / 29];
			for (int i = 0; i < richList.length() / 29; i++) {
				result[i] = new Address(richList.substring(i * 29,(i+1) * 29));
				
			}
			return result;
		} else {
			return new Address[0];
		}
	}
	
	public enum TransferResults {
		Success,
		InsufficientFunds,
		NotEnoughKST,
		BadValue,
		InvalidRecipient,
		SelfSend,
		Unknown
	}
	
	public TransferResults sendKrist(long amount, String recipient) throws MalformedURLException, IOException {
		if (address == recipient)
			return TransferResults.SelfSend;
		switch(HTTP.readURL(new URL(remoteAPI,"?pushtx2&q=" + recipient + "&pkey=" + key + "&amt=" + amount))) {
			case "Success": {
				return TransferResults.Success;
			}
			case "Error1": {
				return TransferResults.InsufficientFunds;
			}
			case "Error2": {
				return TransferResults.NotEnoughKST;
			}
			case "Error3": {
				return TransferResults.BadValue;
			}
			case "Error4": {
				return TransferResults.InvalidRecipient;
			}
			default: {
				return TransferResults.Unknown;
			}
		}
	}
	
	private char numtochar(int inp) {
	        for (int i = 6; i <= 251; i += 7)
	        {
	            if (inp <= i)
	            {
	                if (i <= 69)
	                {
	                    return (char) ('0' + (i - 6) / 7);
	                }
	                return (char) ('a' + ((i - 76) / 7));
	            }
	        }
	        return 'e';
	    }

	private String makeAddressV2(String key) {
		String[] protein = {"", "", "", "", "", "", "", "", ""};
		int link = 0;
		String v2 = "k";
		String stick = DigestUtils.sha256Hex(DigestUtils.sha256Hex(key));
		for (int i = 0; i < 9; i++) {
			protein[i] = stick.substring(0,2);
			stick = DigestUtils.sha256Hex(DigestUtils.sha256Hex(stick));
		}
		int i = 0;
		while (i <= 8) {
			link = Integer.parseInt(stick.substring(2*i,2+(2*i)),16) % 9;
			if (protein[link].equals("")) {
				stick = DigestUtils.sha256Hex(stick);
			} else {
				v2 = v2 + numtochar(Integer.parseInt(protein[link],16));
				protein[link] = "";
				i++;
			}
		}
		return v2;
	}

	public String getAddress() {
		return address;
	}
}

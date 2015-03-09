package io.github.apemanzilla.kwallet.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// This class stores the address, balance, and last seen date of a given address.
public class Address {
	private String address;
	private long balance;
	private Date lastSeen;
	
	public Address(String addressInfo) throws ParseException {
		this.address = addressInfo.substring(0,10);
		this.balance = Long.parseLong(addressInfo.substring(10,20));
		DateFormat format = new SimpleDateFormat("MMM yyyy");
		this.lastSeen = format.parse(addressInfo.substring(20));
	}

	public String getAddress() {
		return address;
	}

	public long getBalance() {
		return balance;
	}

	public Date getLastSeen() {
		return lastSeen;
	}
}

package io.github.apemanzilla.kwallet.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

// This class stores the address, balance, and last seen date of a given address.
public class Address {
	private String address;
	private long balance;
	private Date lastSeen;
	
	public Address(String addressInfo) throws ParseException {
		address = addressInfo.substring(0,10);
		balance = Long.parseLong(addressInfo.substring(10,18));
		DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		lastSeen = format.parse(addressInfo.substring(18));
		TimeZone tz = TimeZone.getDefault();
		lastSeen.setTime(lastSeen.getTime() + (tz.getOffset(new Date().getTime()) - 3600000));
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

package io.github.apemanzilla.kwallet.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Transaction {
	private Date time;
	private String toAddr;
	private String fromAddr;
	private String sourceAddr;
	private String addr;
	private long amount;
	
	//Server time zone isCET
	
	public Transaction(String data, String sourceAddr) {
		try {
			DateFormat format = new SimpleDateFormat("MMM dd HH:mm", Locale.US);
			time = format.parse(data.substring(0,12));
			// Adjust time to local timezone
			TimeZone tz = TimeZone.getDefault();
			time.setTime(time.getTime() + (tz.getOffset(new Date().getTime()) - 3600000));
			data = data.substring(12);
		} catch (ParseException e) {
			System.out.println(data);
			e.printStackTrace();
		}
		String address = data.substring(0,10);
		data = data.substring(10).replaceFirst("\\+", "");
		amount = Long.parseLong(data);
		if (amount >= 0) {
			toAddr = sourceAddr;
			fromAddr = address;
		} else if (amount < 0) {
			toAddr = address;
			fromAddr = sourceAddr;
		}
		addr = address;
		this.sourceAddr = sourceAddr;
	}

	public Date getTime() {
		return time;
	}

	public String getToAddr() {
		return toAddr;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public boolean isMined() {
		return fromAddr.contains("(Mined)");
	}
	
	public long getAmount() {
		return amount;
	}

	public String getSourceAddr() {
		return sourceAddr;
	}

	public String getAddr() {
		return addr;
	}
}

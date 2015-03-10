package io.github.apemanzilla.kwallet.gui;

import io.github.apemanzilla.kwallet.types.Address;
import java.text.SimpleDateFormat;

import javax.swing.table.AbstractTableModel;

public class AddressTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -3523439709848378990L;
	private String[] columnNames = {
			"#",
			"Address",
			"Balance",
			"First Seen"
	};
	private Address[] data;
	
	public AddressTableModel(Address[] addresses) {
		data = addresses;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return 15;
	}

	public Object getValueAt(int row, int col) {
		try {
			if (row >= 15) {
				return null;
			}
			switch(col) {
			case 0: {
				return row + 1;
			}
			case 1: {
				return data[row].getAddress();
			}
			case 2: {
				return String.format("%,d", data[row].getBalance());
			}
			case 3: {
				SimpleDateFormat format = new SimpleDateFormat("MMMM dd, YYYY");
				return format.format(data[row].getLastSeen());
			}
			default: {
				return null;
			}
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public Class<String> getColumnClass(int col) {
		return String.class;
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
package io.github.apemanzilla.kwallet.gui;

import io.github.apemanzilla.kwallet.types.Transaction;

import java.text.SimpleDateFormat;

import javax.swing.table.AbstractTableModel;

public class TransactionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -3523439709848378990L;
	private String[] columnNames = {
			"Time",
			"Address",
			"Amount"
	};
	private Transaction[] data;
	
	public TransactionTableModel(Transaction[] transactions) {
		data = transactions;
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (data.length < 200) {
			return data.length;
		} else {
			return 200;
		}
	}

	public Object getValueAt(int row, int col) {
		if (row > data.length || row > 200) {
			return null;
		}
		switch(col) {
			case 0: {
				return new SimpleDateFormat("MMMM d, hh:mm a").format(data[row].getTime());
			}
			case 1: {
				if (data[row].isMined()) {
					return "(Mined)";
				} else {
					return data[row].getAddr();
				}
			}
			case 2: {
				return data[row].getAmount();
			}
			default: {
				return null;
			}
		}
	}
	
	public Class<String> getColumnClass(int col) {
		return String.class;
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
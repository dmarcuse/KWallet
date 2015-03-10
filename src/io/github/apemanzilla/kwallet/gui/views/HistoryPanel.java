package io.github.apemanzilla.kwallet.gui.views;

import io.github.apemanzilla.kwallet.KWallet;
import io.github.apemanzilla.kwallet.gui.TransactionTableModel;
import io.github.apemanzilla.kwallet.types.Transaction;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JTable;

import java.awt.Component;

public class HistoryPanel extends JPanel {
	private static final long serialVersionUID = 5110265808807047060L;
	private String address;
	private JTable historyTable;
	private JLabel lblLoading;
	
	public HistoryPanel(String address) throws MalformedURLException, IOException {
		this.address = address;
		setLayout(new BorderLayout(0, 0));
		
//		JPanel buttonPanel = new JPanel();
//		add(buttonPanel, BorderLayout.SOUTH);
//		
//		JButton button = new JButton("<<");
//		button.setToolTipText("First page");
//		buttonPanel.add(button);
//		
//		JButton button_1 = new JButton("<");
//		button_1.setToolTipText("Previous page");
//		buttonPanel.add(button_1);
//		
//		JLabel lblPageXOf = new JLabel("Page x of x");
//		lblPageXOf.setAlignmentX(Component.CENTER_ALIGNMENT);
//		buttonPanel.add(lblPageXOf);
//		
//		JButton button_2 = new JButton(">");
//		button_2.setToolTipText("Next page");
//		buttonPanel.add(button_2);
//		
//		JButton button_3 = new JButton(">>");
//		button_3.setToolTipText("Last page");
//		buttonPanel.add(button_3);
		
		JLabel lblTitle = new JLabel("Last 200 transactions for " + address);
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
		add(lblTitle, BorderLayout.NORTH);
		
		lblLoading = new JLabel("Loading...");
		lblLoading.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLoading.setFont(new Font("SansSerif", Font.BOLD, 12));
		add(lblLoading, BorderLayout.CENTER);
		
		DataLoader thread = new DataLoader();
		thread.start();

	}
	
	class DataLoader extends Thread {
		public void run() {
			try {
				Transaction[] data = KWallet.api.getTransactions(address);
				dataLoaded(data);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void dataLoaded(Transaction[] data) {
		remove(lblLoading);
		historyTable = new JTable(new TransactionTableModel(data));
		JScrollPane sp = new JScrollPane(historyTable);
		sp.setPreferredSize(new Dimension(historyTable.getWidth() + 2, historyTable.getHeight() + 2));
		add(sp, BorderLayout.CENTER);
		invalidate();
		validate();
		repaint();
	}

}
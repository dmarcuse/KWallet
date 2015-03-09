package io.github.apemanzilla.kwallet.gui.views;

import io.github.apemanzilla.kwallet.KWallet;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.BoxLayout;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OverviewPanel extends JPanel {
	
	private static final long serialVersionUID = 3033921426797693589L;
	private JTextField address;
	private JTextField balanceField;
	private JButton btnCopy;
	private OverviewPanel self;
	
	public OverviewPanel(String kristAddress) {
		Long balance = null;
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new BorderLayout(0, 0));
		
		JPanel header = new JPanel();
		add(header, BorderLayout.NORTH);
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		header.add(horizontalStrut_2);
		
		JLabel label = new JLabel("Overview");
		header.add(label);
		label.setFont(new Font("SansSerif", Font.BOLD, 12));
		label.setAlignmentX(0.5f);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		header.add(horizontalGlue);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoadThread().start();
			}
		});
		btnRefresh.setFocusPainted(false);
		btnRefresh.setMinimumSize(new Dimension(72, 25));
		btnRefresh.setMaximumSize(new Dimension(72, 25));
		header.add(btnRefresh);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 0;
		panel.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JLabel lblAddress = new JLabel("Address");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.anchor = GridBagConstraints.EAST;
		gbc_lblAddress.gridx = 1;
		gbc_lblAddress.gridy = 0;
		panel.add(lblAddress, gbc_lblAddress);
		
		address = new JTextField(kristAddress);
		address.setFocusable(false);
		address.setEditable(false);
		GridBagConstraints gbc_address = new GridBagConstraints();
		gbc_address.insets = new Insets(0, 0, 5, 5);
		gbc_address.fill = GridBagConstraints.HORIZONTAL;
		gbc_address.gridx = 2;
		gbc_address.gridy = 0;
		panel.add(address, gbc_address);
		address.setColumns(10);
		
		btnCopy = new JButton("Copy");
		btnCopy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btnCopy.setText("Copy");
				btnCopy.setEnabled(true);
			}
		});
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    StringSelection selection = new StringSelection(address.getText());
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(selection, selection);
			    btnCopy.setText("Copied!");
			    btnCopy.setEnabled(false);
			}
		});
		GridBagConstraints gbc_btnCopy = new GridBagConstraints();
		gbc_btnCopy.insets = new Insets(0, 0, 5, 5);
		gbc_btnCopy.gridx = 3;
		gbc_btnCopy.gridy = 0;
		panel.add(btnCopy, gbc_btnCopy);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 4;
		gbc_horizontalStrut.gridy = 0;
		panel.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblBalance = new JLabel("Balance");
		GridBagConstraints gbc_lblBalance = new GridBagConstraints();
		gbc_lblBalance.anchor = GridBagConstraints.EAST;
		gbc_lblBalance.insets = new Insets(0, 0, 5, 5);
		gbc_lblBalance.gridx = 1;
		gbc_lblBalance.gridy = 1;
		panel.add(lblBalance, gbc_lblBalance);
		
		balanceField = new JTextField("Loading...");
		balanceField.setFocusable(false);
		balanceField.setEditable(false);
		GridBagConstraints gbc_balance = new GridBagConstraints();
		gbc_balance.insets = new Insets(0, 0, 5, 5);
		gbc_balance.fill = GridBagConstraints.HORIZONTAL;
		gbc_balance.gridx = 2;
		gbc_balance.gridy = 1;
		panel.add(balanceField, gbc_balance);
		balanceField.setColumns(10);
		
		self = this;
		new LoadThread().start();
	}
	
	class LoadThread extends Thread {
		public void run() {
			try {
				balanceField.setText("Loading...");
				balanceField.setText(((Long) KWallet.api.getBalance(address.getText())).toString());
			} catch (NumberFormatException | IOException e) {
				balanceField.setText("Error, please retry");
				e.printStackTrace();
			}
		}
	}
	
}

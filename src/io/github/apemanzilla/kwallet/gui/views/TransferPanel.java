package io.github.apemanzilla.kwallet.gui.views;

import io.github.apemanzilla.kwallet.KWallet;
import io.github.apemanzilla.kwallet.KristAPI;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Component;

import javax.swing.Box;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.BoxLayout;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.SpinnerNumberModel;

public class TransferPanel extends JPanel {
	private JTextField recipientField;
	private JButton btnSend;
	private JSpinner spinner;
	private boolean tip;
	
	public TransferPanel(boolean tip) {
		this.tip = tip;
		setLayout(new BorderLayout(0, 0));
		
		JPanel header = new JPanel();
		header.setPreferredSize(new Dimension(10, 25));
		add(header, BorderLayout.NORTH);
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		header.add(horizontalStrut);
		
		if (tip) {
			JLabel lblTransferKrist = new JLabel("Tip apemanzilla");
			lblTransferKrist.setFont(new Font("SansSerif", Font.BOLD, 12));
			header.add(lblTransferKrist);
		} else {
			JLabel lblTransferKrist = new JLabel("Transfer Krist");
			lblTransferKrist.setFont(new Font("SansSerif", Font.BOLD, 12));
			header.add(lblTransferKrist);
		}
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_3.gridx = 0;
		gbc_horizontalStrut_3.gridy = 0;
		panel.add(horizontalStrut_3, gbc_horizontalStrut_3);
		
		if (tip) {
			JLabel lblSend = new JLabel("Send");GridBagConstraints gbc_lblSend = new GridBagConstraints();
			gbc_lblSend.insets = new Insets(0, 0, 5, 5);
			gbc_lblSend.anchor = GridBagConstraints.EAST;
			gbc_lblSend.gridx = 1;
			gbc_lblSend.gridy = 0;
			panel.add(lblSend, gbc_lblSend);
		} else {
			JLabel lblSend = new JLabel("Send");GridBagConstraints gbc_lblSend = new GridBagConstraints();
			gbc_lblSend.insets = new Insets(0, 0, 5, 5);
			gbc_lblSend.anchor = GridBagConstraints.EAST;
			gbc_lblSend.gridx = 1;
			gbc_lblSend.gridy = 0;
			panel.add(lblSend, gbc_lblSend);
		}
		
		
		spinner = new JSpinner();
		spinner.setMinimumSize(new Dimension(100, 28));
		spinner.setPreferredSize(new Dimension(100, 28));
		if (tip)
			spinner.setModel(new SpinnerNumberModel(new Long(250), new Long(1), null, new Long(1)));
		else
			spinner.setModel(new SpinnerNumberModel(new Long(1), new Long(1), null, new Long(1)));
		spinner.setToolTipText("Enter the amount of Krist to send");
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 0;
		panel.add(spinner, gbc_spinner);
		
		if (tip) {
			JLabel lblNewLabel = new JLabel(" Krist to apemanzilla!");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 3;
			gbc_lblNewLabel.gridy = 0;
			panel.add(lblNewLabel, gbc_lblNewLabel);
		} else {
			JLabel lblNewLabel = new JLabel(" Krist to ");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 3;
			gbc_lblNewLabel.gridy = 0;
			panel.add(lblNewLabel, gbc_lblNewLabel);
		}
		
		if (!tip) {
			recipientField = new JTextField();
			recipientField.setToolTipText("Enter the receiving address");
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 5, 5);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 4;
			gbc_textField_1.gridy = 0;
			panel.add(recipientField, gbc_textField_1);
			recipientField.setColumns(10);
		}
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 1;
		gbc_horizontalStrut_1.gridy = 0;
		panel.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_2.gridx = 5;
		gbc_horizontalStrut_2.gridy = 0;
		panel.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		if (tip) {
			btnSend = new JButton("Send");
			btnSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SendThread().start();
				}
			});
			GridBagConstraints gbc_btnSend = new GridBagConstraints();
			gbc_btnSend.insets = new Insets(0, 0, 5, 5);
			gbc_btnSend.gridx = 2;
			gbc_btnSend.gridy = 1;
			panel.add(btnSend, gbc_btnSend);
		} else {
			btnSend = new JButton("Send");
			btnSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SendThread().start();
				}
			});
			GridBagConstraints gbc_btnSend = new GridBagConstraints();
			gbc_btnSend.gridwidth = 3;
			gbc_btnSend.insets = new Insets(0, 0, 5, 5);
			gbc_btnSend.gridx = 2;
			gbc_btnSend.gridy = 1;
			panel.add(btnSend, gbc_btnSend);
		}

	}
	
	class SendThread extends Thread {
		public void run() {
			btnSend.setEnabled(false);
			btnSend.setText("Sending...");
			try {
				String recipient;
				KristAPI.TransferResults result;
				if (tip) {
					recipient = "k5ztameslf";
					result = KWallet.api.sendKrist((long) spinner.getValue(), recipient);
				} else {
					recipient = recipientField.getText();
					result = KWallet.api.sendKrist((long) spinner.getValue(), recipientField.getText());
				}
				switch(result) {
				case Success: {
					JOptionPane.showMessageDialog(null, "Successfully sent " + spinner.getValue() + " Krist to " + recipient + "!"
							,"Krist Sent",JOptionPane.INFORMATION_MESSAGE);
					break;
				}
				case BadValue: {
					JOptionPane.showMessageDialog(null, "Error parsing Krist value.\nKrist has not been sent.","Bad Krist Value",JOptionPane.ERROR_MESSAGE);
					break;
				}
				case InsufficientFunds: {
					JOptionPane.showMessageDialog(null, "You do not have enough Krist!\nKrist has not been sent.","Insufficient Krist",JOptionPane.ERROR_MESSAGE);
					break;
				}
				case InvalidRecipient: {
					JOptionPane.showMessageDialog(null, "The specified recipient could not be found.\nKrist has not been sent.","Invalid Recipient",JOptionPane.ERROR_MESSAGE);
					break;
				}
				case NotEnoughKST: {
					JOptionPane.showMessageDialog(null, "Invalid Krist amount!\nKrist has not been sent.","Invalid Amount",JOptionPane.ERROR_MESSAGE);
					break;
				}
				case SelfSend: {
					JOptionPane.showMessageDialog(null, "You cannot send Krist to yourself!\nKrist has not been sent.","Cannot send to yourself",JOptionPane.ERROR_MESSAGE);
					break;
				}
				case Unknown: {
					JOptionPane.showMessageDialog(null, "An unknown error has occurred.","Unknown Error",JOptionPane.QUESTION_MESSAGE);
					break;
				}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				btnSend.setEnabled(true);
				btnSend.setText("Send");
			}
		}
	}
}

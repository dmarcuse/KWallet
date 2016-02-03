package io.github.apemanzilla.kwallet.gui;

import io.github.apemanzilla.kwallet.KWallet;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import org.apache.commons.codec.digest.DigestUtils;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class LoginFrame extends JFrame {


	private static final long serialVersionUID = -8790221207660445916L;
	private JPanel contentPane;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setResizable(false);
		setTitle("KWallet - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JLabel lblTitle = new JLabel("KWallet by apemanzilla");
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblTitle);
		
		JLabel lblPassword = new JLabel("Please enter your Krist wallet password below.");
		lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblPassword);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setMaximumSize(new Dimension(32767, 25));
		contentPane.add(loginPanel);
		loginPanel.setLayout(new BorderLayout(0, 0));
		
		final JButton btnLogin = new JButton("Login");
		btnLogin.setFocusPainted(false);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogin.setEnabled(false);
				passwordField.setEnabled(false);
				contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						KWallet.login(DigestUtils.sha256Hex("KRISTWALLET" + new String(passwordField.getPassword())) + "-000");
					}
				});
			}
		});
		loginPanel.add(btnLogin, BorderLayout.EAST);
		
		passwordField = new JPasswordField();
		lblPassword.setLabelFor(passwordField);
		loginPanel.add(passwordField, BorderLayout.CENTER);
		
		JLabel lbCreate = new JLabel("If you do not have a wallet, one will be created for you.");
		lbCreate.setFont(new Font("SansSerif", Font.ITALIC, 12));
		lbCreate.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lbCreate);
		
		JSeparator separator = new JSeparator();
		contentPane.add(separator);
		
		JLabel lblWarning = new JLabel("Note: Only v2 addresses are supported!");
		lblWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblWarning);
	}

}

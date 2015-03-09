package io.github.apemanzilla.kwallet;

import io.github.apemanzilla.kwallet.gui.LoginFrame;
import io.github.apemanzilla.kwallet.gui.WalletFrame;
import io.github.apemanzilla.kwallet.gui.WalletFrame.Views;
import io.github.apemanzilla.kwallet.util.HTTP;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class KWallet {
	
	private static LoginFrame loginWindow;
	private static WalletFrame walletWindow;
	public static KristAPI api;

	public static void main(String[] args) {
		System.out.println("Starting KWallet");
		
		// Set Nimbus L&F, if available
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, leave default L&F
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginWindow = new LoginFrame();
					loginWindow.pack();
					loginWindow.setLocationRelativeTo(null);
					loginWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void login(String privateKey) {
		String apiLink = null;
		try {
			apiLink = HTTP.readURL(new URL("https://raw.githubusercontent.com/BTCTaras/kristwallet/master/staticapi/syncNode"));
			System.out.println("API link is " + apiLink);
		} catch (MalformedURLException e) {
			System.out.println("Bad URL");
			JOptionPane.showMessageDialog(null, "Bad URL","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Could not find node");
			JOptionPane.showMessageDialog(null, "Could not find node","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(2);
		}
		try {
			api = new KristAPI(new URL(apiLink), privateKey);
			System.out.println("Address (v2) is " + api.getAddress());
			loginWindow.dispose();
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						walletWindow = new WalletFrame();
						walletWindow.pack();
						walletWindow.setLocationRelativeTo(null);
						walletWindow.setTitle("KWallet - " + api.getAddress());
						walletWindow.setVisible(true);
						walletWindow.setView(Views.OVERVIEW);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (MalformedURLException e) {
			System.out.println("Could not connect to node");
			JOptionPane.showMessageDialog(null, "Could not connect to node","Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(3);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}

package io.github.apemanzilla.kwallet.gui.views;

import io.github.apemanzilla.kwallet.KWallet;
import io.github.apemanzilla.kwallet.gui.AddressTableModel;
import io.github.apemanzilla.kwallet.types.Address;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import javax.swing.border.EmptyBorder;

public class EconomiconPanel extends JPanel {

	private JPanel richlistPanel;
	
	private static final long serialVersionUID = 5859711605797009759L;

	public EconomiconPanel() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		richlistPanel = new JPanel();
		richlistPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		richlistPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Top Balances", null, richlistPanel, null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_1, null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_3, null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 10, 0, 0));
		//tabbedPane.addTab("New tab", null, panel_4, null);
		
		new LoadThread().start();

	}

	class LoadThread extends Thread {
		public void run() {
			try {
				final Address[] richlist = KWallet.api.getRichList();
				JTable tbl = new JTable(new AddressTableModel(richlist));
				tbl.getColumnModel().getColumn(0).setMinWidth(30);
				tbl.getColumnModel().getColumn(0).setMaxWidth(30);
			    tbl.addMouseListener(new MouseAdapter() {
			        public void mousePressed(MouseEvent me) {
			            JTable table =(JTable) me.getSource();
			            Point p = me.getPoint();
			            int row = table.rowAtPoint(p);
			            if (me.getClickCount() == 2) {
		            		JFrame addressLookup = new JFrame("Transactions for " + richlist[row].getAddress());
		            		addressLookup.setMinimumSize(new Dimension(450,300));
		            		try {
								addressLookup.setContentPane(new HistoryPanel(richlist[row].getAddress()));
								addressLookup.pack();
								addressLookup.setLocationRelativeTo(null);
								addressLookup.setVisible(true);
							} catch (IOException e) {
								e.printStackTrace();
							}
		            	}
		            }
		        });
				JScrollPane sp =  new JScrollPane(tbl);
				sp.setPreferredSize(new Dimension(tbl.getWidth() + 2,tbl.getHeight() + 2));
				richlistPanel.add(sp, BorderLayout.CENTER);
				invalidate();
				validate();
				repaint();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}

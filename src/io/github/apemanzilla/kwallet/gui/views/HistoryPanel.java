package io.github.apemanzilla.kwallet.gui.views;

import io.github.apemanzilla.kwallet.KWallet;
import io.github.apemanzilla.kwallet.gui.TransactionGraphFrame;
import io.github.apemanzilla.kwallet.gui.TransactionTableModel;
import io.github.apemanzilla.kwallet.types.Transaction;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class HistoryPanel extends JPanel
{
  private static final long serialVersionUID = 5110265808807047060L;
  private String address;
  private JTable historyTable;
  private JLabel lblLoading;
  Transaction[] data;

  public HistoryPanel(final String address)
    throws MalformedURLException, IOException
  {
    this.address = address;
    setLayout(new BorderLayout(0, 0));
    setBorder(new EmptyBorder(0, 10, 0, 0));

    this.lblLoading = new JLabel("Loading...");
    this.lblLoading.setAlignmentX(0.5F);
    this.lblLoading.setFont(new Font("SansSerif", 1, 12));
    add(this.lblLoading, "Center");

    Box horizontalBox = Box.createHorizontalBox();
    add(horizontalBox, "North");

    JLabel lblTitle = new JLabel("Last 200 transactions for " + address);
    horizontalBox.add(lblTitle);
    lblTitle.setFont(new Font("SansSerif", 1, 12));

    Component horizontalGlue = Box.createHorizontalGlue();
    horizontalBox.add(horizontalGlue);

    JButton btnCreateGraph = new JButton("Open Graph");
    btnCreateGraph.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFrame graph = new TransactionGraphFrame(address);
        graph.setLocationRelativeTo(null);
        graph.setVisible(true);
      }
    });
    horizontalBox.add(btnCreateGraph);

    DataLoader thread = new DataLoader();
    thread.start();
  }

  protected void dataLoaded(final Transaction[] data)
  {
    remove(this.lblLoading);
    historyTable = new JTable(new TransactionTableModel(data));
    historyTable.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent me) {
            JTable table =(JTable) me.getSource();
            Point p = me.getPoint();
            int row = table.rowAtPoint(p);
            if (me.getClickCount() == 2) {
            	if (!data[row].isMined()) {
            		JFrame addressLookup = new JFrame("Transactions for " + data[row].getAddr());
            		addressLookup.setMinimumSize(new Dimension(450,300));
            		try {
						addressLookup.setContentPane(new HistoryPanel(data[row].getAddr()));
						addressLookup.pack();
						addressLookup.setLocationRelativeTo(null);
						addressLookup.setVisible(true);
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            }
        }
    });
    JScrollPane sp = new JScrollPane(this.historyTable);
    sp.setPreferredSize(new Dimension(this.historyTable.getWidth() + 2, this.historyTable.getHeight() + 2));
    add(sp, "Center");
    invalidate();
    validate();
    repaint();
  }

  class DataLoader extends Thread
  {
    DataLoader()
    {
    }

    public void run()
    {
      try
      {
        data = KWallet.api.getTransactions(HistoryPanel.this.address);
        HistoryPanel.this.dataLoaded(data);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
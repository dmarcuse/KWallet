package io.github.apemanzilla.kwallet.gui.views;

import io.github.apemanzilla.kwallet.KWallet;
import io.github.apemanzilla.kwallet.gui.TransactionGraphFrame;
import io.github.apemanzilla.kwallet.gui.TransactionTableModel;
import io.github.apemanzilla.kwallet.types.Transaction;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

  protected void dataLoaded(Transaction[] data)
  {
    remove(this.lblLoading);
    this.historyTable = new JTable(new TransactionTableModel(data));
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
        Transaction[] data = KWallet.api.getTransactions(HistoryPanel.this.address);
        HistoryPanel.this.dataLoaded(data);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
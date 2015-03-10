package io.github.apemanzilla.kwallet.gui;

import io.github.apemanzilla.kwallet.KWallet;
import io.github.apemanzilla.kwallet.types.Transaction;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TransactionGraphFrame extends JFrame
{
  private String address;
  private XYSeries totalSeries;
  private XYSeriesCollection data;
  private static final long serialVersionUID = 3744316270884669223L;
  private JPanel loadingPanel;
  private JProgressBar progressBar;

  public TransactionGraphFrame(String address)
  {
    setMinimumSize(new Dimension(350, 350));
    this.address = address;
    setTitle("Complete Transaction History");

    this.loadingPanel = new JPanel();
    this.loadingPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
    getContentPane().add(this.loadingPanel, "Center");
    this.loadingPanel.setLayout(new BoxLayout(this.loadingPanel, 1));

    JLabel lblProcessingData = new JLabel("Processing transaction data...");
    lblProcessingData.setAlignmentX(0.5F);
    this.loadingPanel.add(lblProcessingData);

    this.progressBar = new JProgressBar();
    this.loadingPanel.add(this.progressBar);

    new BuildData().start();
  }

  public JPanel getLoadingPanel()
  {
    return this.loadingPanel;
  }
  public JProgressBar getProgressBar() {
    return this.progressBar;
  }

  private void addChart() {
    JFreeChart chart = ChartFactory.createXYLineChart(
      "Balance Graph for " + this.address, 
      "Time", 
      "Total Krist", 
      this.data, 
      PlotOrientation.VERTICAL, 
      false, 
      true, 
      false);
    XYPlot plot = chart.getXYPlot();
    DateAxis domain = new DateAxis();
    domain.setDateFormatOverride(new SimpleDateFormat("MMM dd"));
    plot.setDomainAxis(domain);
    ChartPanel chartPanel = new ChartPanel(chart);
    setContentPane(chartPanel);
    revalidate();
    repaint();
  }
  class BuildData extends Thread {
    BuildData() {
    }
    public void run() { TransactionGraphFrame.this.getProgressBar().setIndeterminate(true);
      try {
        Transaction[] transactions = KWallet.api.getTransactions();
        TransactionGraphFrame.this.getProgressBar().setMinimum(0);
        TransactionGraphFrame.this.getProgressBar().setMaximum(transactions.length);
        TransactionGraphFrame.this.data = new XYSeriesCollection();
        TransactionGraphFrame.this.totalSeries = new XYSeries("Total Krist");

        long total = 0L;
        for (int i = transactions.length - 1; i >= 0; i--) {
          Transaction transaction = transactions[i];
          if (!transaction.getAddr().equals(TransactionGraphFrame.this.address)) {
            total += transaction.getAmount();
            TransactionGraphFrame.this.totalSeries.add(transaction.getTime().getTime(), total);
          }
          TransactionGraphFrame.this.getProgressBar().setValue(i);
        }
        TransactionGraphFrame.this.data.addSeries(TransactionGraphFrame.this.totalSeries);
        TransactionGraphFrame.this.addChart();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
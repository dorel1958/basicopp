package dorel.basicopp.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class FrameExecutie extends JFrame implements ActionListener {

    static final long serialVersionUID = 1L;
    public JLabel eticheta;
    private JProgressBar progressBar;
    private final int maxValue;
    private int progress;
    private boolean pauza;
    private boolean stop;
    public JButton butonPauzaContinua;
    public JButton butonStop;
    private String textInitialLabel;

    private void setPauza(boolean pauza) {
        this.pauza = pauza;
        if (pauza) {
            butonPauzaContinua.setText("Continuă");
            textInitialLabel = this.eticheta.getText();
        } else {
            butonPauzaContinua.setText("Pauză");
            this.eticheta.setText(textInitialLabel);
        }
    }

    public boolean isPauza() {
        return pauza;
    }

    public boolean isStop() {
        return stop;
    }

    public FrameExecutie(int maxValue) {
        this.maxValue = maxValue;
        progress = 0;
        this.initComponents();
        eticheta.setText("Asteptati, va rog ...");
    }

    private void initComponents() {
        this.setLayout(new FlowLayout());
        this.setTitle("Urmarire executie (" + maxValue + ")");
        this.setResizable(false);

        progressBar = new JProgressBar(0, maxValue);
        this.add(progressBar);
        eticheta = new JLabel();
        eticheta.setPreferredSize(new Dimension(300, 20));
        this.add(eticheta);
        butonPauzaContinua = new JButton("Pauză");
        butonPauzaContinua.addActionListener(this);
        this.add(butonPauzaContinua);
        butonStop = new JButton("Stop");
        butonStop.addActionListener(this);
        this.add(butonStop);

        this.pack();

        // <editor-fold defaultstate="collapsed" desc="Center in Screen">      
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = 0;
        if (dim.width > w) {
            x = (dim.width - w) / 2;
        }
        int y = 0;
        if (dim.height > h) {
            y = (dim.height - h) / 2;
        }
        // Move the window
        this.setLocation(x, y);
        //</editor-fold>

        this.setVisible(true);
    }

    public void startProgressBar(String mesaj) {
        eticheta.setText(mesaj);
        progressBar.setValue(0);
        progress = 0;

    }

    public void addPas(int pas) {
        progress += pas;
        if (progress > maxValue) {
            progress = 0;
            eticheta.setText("| " + eticheta.getText());
        }
        progressBar.setValue(progress);
    }

    public void addPas() {
        progress += 1;
        if (progress > maxValue) {
            progress = 0;
        }
        progressBar.setValue(progress);
        //this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Continuă":
                setPauza(false);
                break;
            case "Pauză":
                setPauza(true);
                break;
            case "Stop":
                this.stop = true;
                break;
            default:
                this.eticheta.setText("Comanda necunoscuta:" + e.getActionCommand());
                break;
        }
    }
}

// exemplu utilizare:
// in JFrame
// public void actionPerformed(ActionEvent ae) {
//  ...
//     Rga rga = new Rga(...);
//     rga.setPriority(Thread.MIN_PRIORITY);
//     rga.start();
//  ...
//}
//
// clasa:
//public class Rga extends Thread {
//
//    public Rga(...) {
//    ...
//    }
//
//    @Override
//    public void run() {
//        Eroare eroare = new Eroare();
//        fe = new FrameExecutie(1000);
//        fe.startProgressBar("Așteptați, vă rog ...");
//        while (...) {
//           ...
//           fe.addPas(1);
//                        if(fe.isStop()){
//                            fe.setVisible(false);
//                            eroare.afisazaFisierErori();
//                            return;
//                        }
//                        if(fe.isPauza()){
//                            while(fe.isPauza()){
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException ex) {
//                                    fe.eticheta.setText("InterruptedException ex="+ex.getLocalizedMessage());
//                                }
//                            }
//                        }
//        }
//        fe.setVisible(false);
//        eroare.afisazaFisierErori();
//    }
//}


package dorel.basicopp.teste;

import dorel.basicopp.print.ContentHelper;
import dorel.basicopp.print.PlansaPrint;
import dorel.basicopp.print.Print;
import dorel.basicopp.print.PrintViewer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Test extends JFrame implements ActionListener {

    PlansaPrint plansa;

    public static void main(String[] args) {
        Test fer = new Test();
        fer.initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

//        plansa = new PlansaPrint();
//        JScrollPane scroll = new JScrollPane(plansa);
//        scroll.setPreferredSize(new Dimension(595, 841));
//        add(scroll, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton bprint = new JButton("PrintViewer");
        bprint.addActionListener(this);
        panel.add(bprint);

        JButton bexec = new JButton("Executa");
        bexec.addActionListener(this);
        panel.add(bexec);

        JButton bies = new JButton("Iesire");
        bies.addActionListener(this);
        panel.add(bies);

        add(panel, BorderLayout.SOUTH);

        pack();

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

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "PrintViewer":
                ContentHelper content = new ContentHelper();
                PrintViewer printViewer = new PrintViewer(content);
                //print.setCuPageDialog(false);
                //print.tipareste(plansa, print.getPageFormatA4());
                break;
            case "Executa":
                int e = 666;
                int f = 666;
                System.out.println("e == f " + (e == f));
                Integer c = 666;
                Integer d = 666;
                System.out.println("c == d " + (c == d));
                Integer a = 42;
                Integer b = 42;
                System.out.println("a == b " + (a == b));
                //Teste teste = new Teste();
                //teste.testFrameExecutie();
                //teste.testCUICNP();
                //teste.connectPostgress();
                //teste.connectMsSql();
                //teste.connectMySQL();
                //JOptionPane.showMessageDialog(this, "Merge");
                //if (teste.testMyDate()) {
                //    JOptionPane.showMessageDialog(this, "Pare corect ...");
                //}
                //teste.testXML();
                //teste.testMD5();
                //teste.testOpenSave();
                //teste.testDataValida();
                //JOptionPane.showConfirmDialog(this,SumaInLitere.getSumaInLitere("56921", "25"));
                //if (teste.testIBAN("RO25BACX0000000191454000")){
//                if (teste.testIBAN("RO84INGB0000999901409585")){
//                    JOptionPane.showMessageDialog(this, "Corect VV");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Gresit VV");
//                }
                //teste.testCUICNP();
                //teste.testMajorari();
                //teste.testBigDecimal();
                break;
            case "Iesire":
                System.exit(0);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Comanda necunoscuta:" + ae.getActionCommand());
        }
    }
}

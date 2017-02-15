package dorel.basicopp.print;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PrintViewer extends JFrame implements ActionListener {

    static final long serialVersionUID=0L;
    PlansaPrint plansa;
    ContentHelper content;
    //
    JButton bfirst;
    JButton bprev;
    JButton bnext;
    JButton blast;
    JLabel lindex;

    public PrintViewer(ContentHelper content) {
        this.content = content;
        initComponents();
    }

    private void initComponents() {
        setTitle("Print viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //<editor-fold defaultstate="collapsed" desc="Center">
        plansa = new PlansaPrint(content);
        JScrollPane scroll = new JScrollPane(plansa);
        //scroll.setPreferredSize(new Dimension(595, 841));
        scroll.setPreferredSize(new Dimension((int) content.getPageFormat().getWidth() + 20, (int) content.getPageFormat().getHeight() + 40));
        add(scroll, BorderLayout.CENTER);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="South">
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton bprint = new JButton("Print");
        bprint.addActionListener(this);
        panel.add(bprint);

        JLabel lspace1 = new JLabel("        ");
        panel.add(lspace1);

        bfirst = new JButton("<<");
        bfirst.setActionCommand("first");
        bfirst.addActionListener(this);
        panel.add(bfirst);

        bprev = new JButton("<");
        bprev.setActionCommand("prev");
        bprev.addActionListener(this);
        panel.add(bprev);

        lindex = new JLabel();
        panel.add(lindex);

        bnext = new JButton(">");
        bnext.setActionCommand("next");
        bnext.addActionListener(this);
        panel.add(bnext);

        blast = new JButton(">>");
        blast.setActionCommand("last");
        blast.addActionListener(this);
        panel.add(blast);

        JLabel lspace2 = new JLabel("        ");
        panel.add(lspace2);

        JButton bies = new JButton("Iesire");
        bies.addActionListener(this);
        panel.add(bies);

        add(panel, BorderLayout.SOUTH);
        //</editor-fold>

        pack();

        //<editor-fold defaultstate="collapsed" desc="Center in Screen">      
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

        setNavigatie();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Print":
                Print print = new Print();
                print.setCuPageDialog(false);
                print.tipareste(plansa, print.getPageFormatA4());
                break;
            case "first":
                plansa.printFirstPage();
                setNavigatie();
                break;
            case "prev":
                plansa.printPreviousPage();
                setNavigatie();
                break;
            case "next":
                plansa.printNextPage();
                setNavigatie();
                break;
            case "last":
                plansa.printLastPage();
                setNavigatie();
                break;
            case "Iesire":
                setVisible(false);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Comanda necunoscuta:" + ae.getActionCommand());
        }
    }

    private void setNavigatie() {
        int indexMax = plansa.getLastPageIndex();
        int indexCurent = plansa.getPageIndex();
        lindex.setText(String.valueOf(indexCurent));
        if (indexMax == 0) {
            // Nu am navigatie
            bfirst.setEnabled(false);
            bprev.setEnabled(false);
            bnext.setEnabled(false);
            blast.setEnabled(false);
        } else {
            if (indexCurent == 0) {
                bfirst.setEnabled(false);
                bprev.setEnabled(false);
                bnext.setEnabled(true);
                blast.setEnabled(true);
            } else {
                if (indexCurent == indexMax) {
                    bfirst.setEnabled(true);
                    bprev.setEnabled(true);
                    bnext.setEnabled(false);
                    blast.setEnabled(false);
                } else {
                    bfirst.setEnabled(true);
                    bprev.setEnabled(true);
                    bnext.setEnabled(true);
                    blast.setEnabled(true);
                }
            }
        }

    }
}

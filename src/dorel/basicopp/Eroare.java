package dorel.basicopp;

import dorel.basicopp.io.TextWriter;
import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Eroare {

    TextWriter tw;
    boolean eCorect;
    private final String numeFisMesaje;
    String info;
    boolean verbose = false;
    public boolean tempoCorect;  // utilizat pentru a vedea daca sunt erori pe portiuni de program
    boolean showMessageCorect = true;

    public Eroare() {
        numeFisMesaje = "Erori.txt";
        tw = new TextWriter(numeFisMesaje, false, TextWriter.Encoding.UTF8);
        eCorect = true;
        info = "";
    }

    public Eroare(String numeFisMesaje) {
        this.numeFisMesaje = numeFisMesaje;
        tw = new TextWriter(numeFisMesaje, false);
        eCorect = true;
        info = "";
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setShowMessageCorect(boolean showMessageCorect) {
        this.showMessageCorect = showMessageCorect;
    }

    public void eroare(String mesajEroare) {
        eCorect = false;
        tempoCorect = false;
        writeMesaj(mesajEroare);
    }

    public void mesaj(String mesaj) {
        writeMesaj(mesaj);
    }

    public void writeEroare(String mesajEroare) {
        eCorect = false;
        tempoCorect = false;
        writeMesaj(mesajEroare);
    }

    public void writeMesaj(String mesaj) {
        String xmesaj = mesaj;
        if (!info.isEmpty()) {
            xmesaj += " - " + info;
        }
        tw.writeLine(xmesaj);
        if (verbose) {
            System.out.println(xmesaj);
        }
    }

    public void afisazaFisierErori() {
        tw.close();
        if (eCorect) {
            if (showMessageCorect) {
                JOptionPane.showMessageDialog(null, "Corect.");
            }
        } else {
            try {
                Runtime rt = Runtime.getRuntime();
                String path = new java.io.File(".").getCanonicalPath();
                Process pr = rt.exec("notepad.exe " + path + "\\" + numeFisMesaje);
            } catch (HeadlessException | IOException ex) {
                JOptionPane.showMessageDialog(null, "Eroare.afisazaFisierErori -" + ex.getLocalizedMessage());
            }
        }
    }

}

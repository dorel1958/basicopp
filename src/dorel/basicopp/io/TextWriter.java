package dorel.basicopp.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JOptionPane;

public final class TextWriter {

    public enum Encoding {
        ASCII,
        UTF8;
    }
    private BufferedWriter bw;
    private String mesajEroare;

    public String getMesajEroare() {
        return mesajEroare;
    }

    public TextWriter(String fileName, boolean append) {
        init(fileName, append, Encoding.UTF8);
    }

    public TextWriter(String fileName, boolean append, Encoding encoding) {
        init(fileName, append, encoding);
    }

    private void init(String fileName, boolean append, Encoding encoding) {
        mesajEroare = "";
        bw = null;
        if (fileName == null) {
            mesajEroare = "Numele fisierului nu poate fi null.";
            return;
        }
        if (fileName.length() > 0) {
        } else {
            mesajEroare = "Nu ati dat numele fisierului.";
            return;
        }
        try {
            //creaza obiectul BufferedWriter
            File fis = new File(fileName);
            if (!fis.exists()) {
                switch (encoding) {
                    case ASCII:
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, append), "ASCII"));
                        break;
                    case UTF8:
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, append), "UTF-8"));
                        writeUTF8_ID();
                        break;
                }
            } else {
                switch (encoding) {
                    case ASCII:
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, append), "ASCII"));
                        break;
                    case UTF8:
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, append), "UTF-8"));
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            mesajEroare = "FileNotFound exception: " + ex.getLocalizedMessage();
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
        }
    }

    public boolean writeLine(String linia) {
        mesajEroare = "";
        if (bw == null) {
            mesajEroare = "Fisierul nu este deschis pentru scriere.";
            return false;
        }
        try {
            bw.write(linia);
            bw.newLine();
            return true;
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
        }
        return false;
    }

    public boolean write(String linia) {
        mesajEroare = "";
        if (bw == null) {
            mesajEroare = "Fisierul nu este deschis pentru scriere.";
            return false;
        }
        try {
            bw.write(linia);
            return true;
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
        }
        return false;
    }

    public boolean write(int intreg) {
        // scrie intregul (considerat codul caracterului UTF8) ca un caracter UTF8
        mesajEroare = "";
        if (bw == null) {
            mesajEroare = "Fisierul nu este deschis pentru scriere.";
            return false;
        }
        try {
            bw.write(intreg);
            return true;
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
        }
        return false;
    }

    public boolean newLine() {
        // scrie intregul (considerat codul caracterului UTF8) ca un caracter UTF8
        mesajEroare = "";
        if (bw == null) {
            mesajEroare = "Fisierul nu este deschis pentru scriere.";
            return false;
        }
        try {
            bw.newLine();
            return true;
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
        }
        return false;
    }

    public boolean close() {
        mesajEroare = "";
        try {
            if (bw != null) {
                bw.close();
            }
            return true;
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
        }
        return false;
    }

    public boolean writeUTF8_ID() {
        //aBytes[0] == -17 && aBytes[1] == -69 && aBytes[2] == -65  EF BB BF
        String bytesToString = "";
        byte[] aBytes = {-17, -69, -65};
        try {
            bytesToString = new String(aBytes, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null, "Eroare transcriere: " + ex.getLocalizedMessage());
        }
        return write(bytesToString);
    }
}

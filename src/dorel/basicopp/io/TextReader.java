package dorel.basicopp.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextReader {

    private BufferedReader br;
    private String mesajEroare;
    boolean eMarkUTF;

    public String getMesajEroare() {
        return mesajEroare;
    }

    public TextReader(String fileName) {
        mesajEroare = "";
        br = null;
        eMarkUTF = false;
        if (fileName == null) {
            mesajEroare = "Numele fisierului nu poate fi null.";
            return;
        }
        if (fileName.isEmpty()) {
            mesajEroare = "Nu ati dat numele fisierului.";
            return;
        }

        try {
            // fișierele făcute în notepad cu salvare UTF8 au în față 3 octeti de identificare (ï»¿) (EF, BB, BF) (239, 187, 191) (-17, -69, -65)
            // testeaza existenta caractrelor de control Notepad
            FileInputStream fis = new FileInputStream(fileName);
            byte[] aBytes = new byte[3];
            if (fis.read(aBytes) == 3) {
                // a citit cei trei octeti
                if (aBytes[0] == -17 && aBytes[1] == -69 && aBytes[2] == -65) {
                    // e in ordine, i-a gasit
                    eMarkUTF = true;
                }
            }
            fis.close();
            // nu merge cu mark() si reset()
            // creaza obiectul BufferedReader
            fis = new FileInputStream(fileName);
            if (eMarkUTF) {
                br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
                if (fis.skip(3) == 3) {
                    // a sărit cei trei octeți
                } else {
                    mesajEroare = "Nu a sărit cei trei octeți in " + fileName;
                }
            } else {
                // 'US-ASCII'  numei primii 7 biti - numere 0-126
                br = new BufferedReader(new InputStreamReader(fis, "ISO-8859-1"));  // interpreteaza caracterele ca numere 0-255
            }
        } catch (FileNotFoundException ex) {
            mesajEroare = "FileNotFound exception: " + ex.getLocalizedMessage();
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex2) {
                mesajEroare += " IO exception: " + ex2.getLocalizedMessage();
            }
        }
    }

    public String readLine() {
        // intoarce null la finalul fisierului
        mesajEroare = "";
        if (br == null) {
            mesajEroare = "Fisierul nu este deschis pentru scriere.";
            return null;
        }
        try {
            return br.readLine();
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
            return null;
        }
    }

    public boolean close() {
        mesajEroare = "";
        try {
            if (br != null) {
                br.close();
            }
            return true;
        } catch (IOException ex) {
            mesajEroare = "IO exception: " + ex.getLocalizedMessage();
        }
        return false;
    }
}
// Exemplu
// TextReader tr = new TextReader(numeFis);
//while ((line = tr.readLine()) != null) {
//
//}
//tr.close();

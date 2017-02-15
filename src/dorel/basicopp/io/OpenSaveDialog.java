package dorel.basicopp.io;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpenSaveDialog {

    public static String openFile(javax.swing.filechooser.FileFilter fileFilter) {
        String numeFis = "";
        JFileChooser openFile = new JFileChooser();
        openFile.setCurrentDirectory(new File("."));
        openFile.setFileFilter(fileFilter);
        if (openFile.showOpenDialog(openFile) == JFileChooser.APPROVE_OPTION) {
            File xyz = openFile.getSelectedFile();
            numeFis = xyz.getAbsoluteFile().getAbsolutePath();
        }
        return numeFis;
    }

    public static String saveFile(javax.swing.filechooser.FileFilter fileFilter) {
        String numeFis = "";
        JFileChooser saveFile = new JFileChooser();
        saveFile.setCurrentDirectory(new File("."));
        saveFile.setFileFilter(fileFilter);
        if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File xyz = saveFile.getSelectedFile();
            numeFis = xyz.getAbsoluteFile().getAbsolutePath();
        }
        return numeFis;
    }

    public static String openFile(String descriere, String[] tipFile) {
        // descriere, tipFile poate fi:
        //"PDF Documents", "pdf"
        //"Images", "jpg", "png", "gif", "bmp"
        String numeFis = "";
        JFileChooser openFile = new JFileChooser();
        openFile.setCurrentDirectory(new File("."));
        
        openFile.addChoosableFileFilter(new FileNameExtensionFilter(descriere, tipFile));
        // ...
        if (openFile.showOpenDialog(openFile) == JFileChooser.APPROVE_OPTION) {
            File xyz = openFile.getSelectedFile();
            numeFis = xyz.getAbsoluteFile().getAbsolutePath();
        }
        return numeFis;
    }

    public static String saveFile(String descriere, String[] tipFile) {
        String numeFis = "";
        JFileChooser saveFile = new JFileChooser();
        saveFile.setCurrentDirectory(new File("."));
        saveFile.addChoosableFileFilter(new FileNameExtensionFilter(descriere, tipFile));
        // ...
        if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File xyz = saveFile.getSelectedFile();
            numeFis = xyz.getAbsoluteFile().getAbsolutePath();
        }
        return numeFis;
    }
    
    // exemplu de utilizare
    //String[] astr = {"xml", "txt"};
    //String numeFis = OpenSaveDialog.openFile("XML documents", astr);
    
}

package dorel.basicopp.datatypes;

import javax.swing.JOptionPane;

public class MyString {

    public static String strLungMax(String str, int lMax, boolean cuMesajEroare) {
        String strLocal = str.trim();
        if (strLocal.length() > lMax) {
            if (cuMesajEroare) {
                JOptionPane.showMessageDialog(null, "MyString.strLungMax - str='" + str + "' - lungimea este mai mare de " + lMax + " caractere.");
            }
            return strLocal.substring(0, lMax);
        } else {
            return strLocal;
        }
    }
    
    public static String faraCarRom(String stringul) {
        String str;
        if (stringul == null) {
            return "";
        }
        //
        str = stringul.replace("ă", "a");
        str = str.replace("Ă", "A");
        str = str.replace("â", "a");
        str = str.replace("Â", "A");
        str = str.replace("î", "i");
        str = str.replace("Î", "I");
        str = str.replace("ș", "s");
        str = str.replace("Ș", "S");
        str = str.replace("ț", "t");
        str = str.replace("Ț", "T");
        //
        str = str.replace("ã", "a");
        str = str.replace("Ã", "A");
        str = str.replace("Ş", "S");
        str = str.replace("ş", "s");
        str = str.replace("ţ", "t");
        str = str.replace("Ţ", "T");
        //
        str = str.replace("'", "''");
        str = str.replace("\\", "\\\\");
        return str;
    }

    
    public static String carRomMondo(String stringul) {
        String str;
        if (stringul == null) {
            return "";
        }
        //
        str = stringul.replace("ă", "#");
        str = str.replace("Ă", "$");
        //str = str.replace("â", "a");
        //str = str.replace("Â", "A");
        str = str.replace("ș", "%");
        str = str.replace("Ș", "^");
        str = str.replace("ț", "&");
        str = str.replace("Ț", "*");
        //
        str = str.replace("ã", "#");
        //str = str.replace("Ã", "$");
        //str = str.replace("ş", "%");
        str = str.replace("Ş", "^");
        str = str.replace("ţ", "&");
        str = str.replace("Ţ", "*");
        //
        str = str.replace("'", "''");
        str = str.replace("\\", "\\\\");
        return str;
    }
}

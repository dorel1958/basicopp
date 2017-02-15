package dorel.basicopp.datatypes;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Numere {

    public static boolean isInteger(String stringul) {
        //String xstringul = stringul.trim();
        try {
            int numar = Integer.parseInt(stringul);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
//
//    public static int parseInt(String stringul) {
//        String xstringul = stringul.trim();
//        if (isInteger(xstringul)) {
//            int numar = Integer.parseInt(xstringul);
//            return numar;
//        }
//        return 0;
//    }

    public static boolean isLong(String stringul) {
        //String xstringul = stringul.trim();
        try {
            long numar = Long.parseLong(stringul);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
//        for (char c : xstringul.toCharArray()) {
//            if (!Character.isDigit(c)) {
//                return false;
//            }
//        }
//        return true;
    }

    public static boolean isNumeric(String stringul) {
        //String xstringul = stringul.trim();
        try {
            double nDbl = Double.parseDouble(stringul);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static Integer stringToInt(String strData) {
        strData = strData.trim();
        try {
            if (strData == null) {
                return 0;
            } else {
                return Integer.parseInt(strData);
            }
        } catch (NumberFormatException ex) {
            //System.out.println("Eroare parse Int:" + ex.getLocalizedMessage());
            return 0;
        }
    }

    public static Double stringToDouble(String strData) {
        strData = strData.trim();
        try {
            if (strData == null) {
                return 0D;
            } else {
                return Double.parseDouble(strData);
            }
        } catch (NumberFormatException ex) {
            //System.out.println("Eroare parse Double:" + ex.getLocalizedMessage());
            return 0D;
        }
    }
    
    public static String doubleToString2Zecimale(double dublu){
        if (Math.abs(dublu-Math.round(dublu))<0.01){
            int idublu=(int)dublu;
            return String.valueOf(idublu);
        } else {
            BigDecimal bd=new BigDecimal(Math.round(dublu*100));
            BigDecimal bdf=bd.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
            return bdf.toPlainString();
        }
    }

    public static String doubleToString(double numar, int nrZec) {
        String stringFormat = "#";
        if (nrZec>0){
            stringFormat+=".";
        }
        for (int i = 0; i < nrZec; i++) {
            stringFormat += "#";
        }
        //DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat df = new DecimalFormat(stringFormat);
        return df.format(numar);
    }
}

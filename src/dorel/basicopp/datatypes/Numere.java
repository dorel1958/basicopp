package dorel.basicopp.datatypes;

import java.text.DecimalFormat;

public class Numere {

    public static boolean isInteger(String strData) {
        if (strData == null) {
            return false;
        } else {
            try {
                int numar = Integer.parseInt(strData);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }

    public static boolean isLong(String strData) {
        //String xstringul = stringul.trim();
        if (strData == null) {
            return false;
        } else {
            try {
                long numar = Long.parseLong(strData);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
//        for (char c : xstringul.toCharArray()) {
//            if (!Character.isDigit(c)) {
//                return false;
//            }
//        }
//        return true;
    }

    public static boolean isNumeric(String strData) {
        if (strData == null) {
            return false;
        } else {
            try {
                double nDbl = Double.parseDouble(strData);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }

    public static Integer stringToInt(String strData) {
        if (strData == null) {
            return 0;
        } else {
            strData = strData.trim();
            try {
                return Integer.parseInt(strData);
            } catch (NumberFormatException ex) {
                //System.out.println("Eroare stringToInt:" + ex.getLocalizedMessage());
                return 0;
            }
        }
    }

    public static Long stringToLong(String strData) {
        if (strData == null) {
            return 0L;
        } else {
            strData = strData.trim();
            try {
                return Long.parseLong(strData);
            } catch (NumberFormatException ex) {
                //System.out.println("Eroare stringToLong:" + ex.getLocalizedMessage());
                return 0L;
            }
        }
    }

    public static Double stringToDouble(String strData) {
        if (strData == null) {
            return 0D;
        } else {
            strData = strData.trim();
            try {
                return Double.parseDouble(strData);
            } catch (NumberFormatException ex) {
                //System.out.println("Eroare stringToDouble:" + ex.getLocalizedMessage());
                return 0D;
            }
        }
    }

    public static String doubleToString2Zecimale(double dublu) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(dublu);
//        if (Math.abs(dublu - Math.round(dublu)) < 0.01) {
//            int idublu = (int) dublu;
//            return String.valueOf(idublu);
//        } else {
//            BigDecimal bd = new BigDecimal(Math.round(dublu * 100));
//            BigDecimal bdf = bd.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
//            return bdf.toPlainString();
//        }
    }

    public static String doubleToString(double numar, int nrZec) {
        String stringFormat = "#";
        if (nrZec > 0) {
            stringFormat += ".";
        }
        for (int i = 0; i < nrZec; i++) {
            stringFormat += "#";
        }
        //DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat df = new DecimalFormat(stringFormat);
        return df.format(numar);
    }
}

//import java.math.BigDecimal;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//    public static int tryParseInteger(String stringul) {
//        int intregul = 0;
//        String xstringul = stringul.trim();
//        String strPattern = "[0-9]+";
//        Pattern pattern = Pattern.compile(strPattern);
//        Matcher matcher = pattern.matcher(xstringul);
//        if (matcher.matches()) {
//            intregul = Integer.parseInt(xstringul);
//        }
//        return intregul;
//    }
//
//    public static double tryParseDouble(String stringul) {
//        double numarul = 0;
//        String xstringul = stringul.trim();
//        String strPattern = "[0-9]+.?[0-9]+";
//        Pattern pattern = Pattern.compile(strPattern);
//        Matcher matcher = pattern.matcher(xstringul);
//        if (matcher.matches()) {
//            numarul = Double.parseDouble(xstringul);
//        }
//        return numarul;
//    }
//
//    public static int parseInt(String stringul) {
//        String xstringul = stringul.trim();
//        if (isInteger(xstringul)) {
//            int numar = Integer.parseInt(xstringul);
//            return numar;
//        }
//        return 0;
//    }

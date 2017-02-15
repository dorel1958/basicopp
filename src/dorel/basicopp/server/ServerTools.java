package dorel.basicopp.server;

import dorel.basicopp.datatypes.MyString;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ServerTools {

    private static final DateFormat dfm;

    static {
        //dfm = new SimpleDateFormat("dd.MM.yyyy");
        dfm = new SimpleDateFormat("yyyy-MM-dd");
        dfm.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
    }

    public static String sqlString(String stringul) {
        String str;
        if (stringul == null) {
            return "''";
        }
        str = stringul.replace("'", "''");
        str = str.replace("\\", "\\\\");
        str = "'" + str + "'";
        return str;
    }

    public static String sqlStringFaraRom(String stringul) {
        String str;
        str = stringul.replace("'", "''");
        return "'" + MyString.faraCarRom(str) + "'";
//        if (stringul == null) {
//            return "''";
//        }
//        //
//        str = stringul.replace("ă", "a");
//        str = str.replace("Ă", "A");
//        str = str.replace("â", "a");
//        str = str.replace("Â", "A");
//        str = str.replace("ș", "s");
//        str = str.replace("Ș", "S");
//        str = str.replace("ț", "t");
//        str = str.replace("Ț", "T");
//        //
//        str = str.replace("Ş", "S");
//        //
//        str = str.replace("\\", "\\\\");
//        str = "'" + str + "'";
//        return str;
    }

    public static String sqlDate(Date data) {
        String str = "''";
        if (data != null) {
            str = "'" + dfm.format(data) + "'";
        }
        return str;
    }

    public static String sqlDMYDate(Date data) {
        String str = "''";
        SimpleDateFormat ndfm = new SimpleDateFormat("dd.MM.yyyy");
        ndfm.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
        if (data != null) {
            str = "'" + ndfm.format(data) + "'";
        }
        return str;
    }

    public static String sqlBoolean(boolean bo) {
        if (bo) {
            return "1";
        } else {
            return "0";
        }
    }

    public static String sqlDecimal(double numar, int nrZecimale) {
        String pattern;
        if (nrZecimale > 0) {
            pattern = "#.";
            for (int i = 0; i < nrZecimale; i++) {
                pattern = pattern + "#";
            }
        } else {
            if (nrZecimale == 0) {
                pattern = "#";
            } else {
                pattern = "#";
            }
        }
        DecimalFormat df = new DecimalFormat(pattern);
        if (nrZecimale < 0) {
            long znr = Math.round(numar / Math.pow(10, -nrZecimale));
            String xnr = df.format(znr);
            for (int i = 0; i < -nrZecimale; i++) {
                xnr = xnr + "0";
            }
            return xnr;
        } else {
            return df.format(numar);
        }
    }
}

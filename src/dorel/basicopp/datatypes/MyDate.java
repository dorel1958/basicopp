package dorel.basicopp.datatypes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.swing.JOptionPane;

public class MyDate {

    private final DateFormat dfmDMY;
    private final DateFormat dfmYMD;
    private final DateFormat dfmYMDTime;
    private String mesajEroare;
    private final boolean eDesktop;
    private boolean verbose = true;

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public static boolean isAnBisect(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static boolean isDataValida(int year, int month, int day) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return day > 0 && day <= 31;
        } else {
            if (month == 2) {
                if (isAnBisect(year)) {
                    return day > 0 && day <= 29;
                } else {
                    return day > 0 && day <= 28;
                }
            } else {
                return day > 0 && day <= 30;
            }
        }
    }

    public static String verif_DMYData(String data, String separator) {
        String sepFinal = separator;
        if (separator.equals(".")) {
            separator = "\\.";
        }
        String raspuns = "";
        String xData = data.trim();
        String[] aString = xData.split(separator);
        if (aString.length == 3) {
            int day = 0;
            int month = 0;
            int year = 0;
            if (Numere.isInteger(aString[0])) {
                day = Integer.parseInt(aString[0]);
            }
            if (Numere.isInteger(aString[1])) {
                month = Integer.parseInt(aString[1]);
            }
            if (Numere.isInteger(aString[2])) {
                year = Integer.parseInt(aString[2]);
            }
            if (isDataValida(year, month, day)) {
                if (day < 10) {
                    raspuns = "0" + day;
                } else {
                    raspuns = String.valueOf(day);
                }
                raspuns += sepFinal;
                if (month < 10) {
                    raspuns += "0" + month;
                } else {
                    raspuns += String.valueOf(month);
                }
                raspuns += sepFinal;
                raspuns += year;
            }
        }
        return raspuns;
    }

    public String getMesajEroare() {
        return mesajEroare;
    }

    public MyDate(boolean eDesktop) {
        this.eDesktop = eDesktop;
        dfmDMY = new SimpleDateFormat("dd.MM.yyyy");
        dfmDMY.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
        dfmYMD = new SimpleDateFormat("yyyy-MM-dd");
        dfmYMD.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
        dfmYMDTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dfmYMDTime.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
    }

    public Date longToDate(Long time) {
        return new Date(time);
    }

    public Date stringYMDToData(String strData, String defaultValue) {
        mesajEroare = "";
        try {
            if (strData != null) {
                if (!(strData.isEmpty() || strData.equals("null") || strData.equals(defaultValue))) {
                    String[] aStr = strData.split("-");
                    if (aStr.length == 3) {
                        if (testALZ(aStr[0], aStr[1], aStr[2])) {
                            return dfmYMD.parse(strData);
                        } else {
                            eroare("Data inexistenta:" + strData);
                        }
                    } else {
                        eroare("String formatat gresit:" + strData + " corect yyyy-mm-dd");
                    }
                }
            }
        } catch (ParseException ex) {
            eroare("MyDate.stringYMDToData - Eroare parse Data:'" + strData + "' - ParseException:" + ex.getLocalizedMessage());
        }
        return null;
    }

    public Date stringYMDToData(String strData) {
        return stringYMDToData(strData, "0000-00-00");
    }

    public Date stringDMYToData(String strData, String defaultValue) {
        mesajEroare = "";
        try {
            if (strData != null) {
                if (!(strData.isEmpty() || strData.equals("null") || strData.equals(defaultValue))) {
                    //strData = strData.replaceAll(" ", "");
                    String[] aStr = strData.split("\\.");
                    if (aStr.length == 3) {
                        int zi = Numere.stringToInt(aStr[0].trim());
                        int luna = Numere.stringToInt(aStr[1].trim());
                        int an = Numere.stringToInt(aStr[2].trim());
                        if (testALZ(an, luna, zi)) {
                            String nStrData = "";
                            if (zi < 10) {
                                nStrData += "0";
                            }
                            nStrData += zi + ".";
                            if (luna < 10) {
                                nStrData += "0";
                            }
                            nStrData += luna + ".";
                            if (an < 10) {
                                nStrData += "000";
                            } else {
                                if (an < 100) {
                                    nStrData += "00";
                                } else {
                                    if (an < 1000) {
                                        nStrData += "0";
                                    }
                                }
                            }
                            nStrData += an;
                            return dfmDMY.parse(nStrData);
                        }
                    }
                }
            }
        } catch (ParseException ex) {
            eroare("MyDate.stringDMYToData - Eroare parse Data:'" + strData + "' - ParseException:" + ex.getLocalizedMessage());
        }
        return null;
    }

    public Date stringDMYToData(String strData) {
        return stringDMYToData(strData, "  .  .    ");
    }

    public String dataToStringDMY(Date data) {
        return dfmDMY.format(data);
    }

    public String dataToStringYMD(Date data) {
        return dfmYMD.format(data);
    }

    public String dataToStringYMDTime(Date data) {
        return dfmYMDTime.format(data);
    }

    private boolean testALZ(String an, String luna, String zi) {
        int nan;
        int nluna;
        int nzi;
        if (Numere.isInteger(an)) {
            nan = Numere.stringToInt(an);
        } else {
            eroare("Anul nu este un numar intreg.");
            return false;
        }
        if (Numere.isInteger(luna)) {
            nluna = Numere.stringToInt(luna);
        } else {
            eroare("Luna nu este un numar intreg.");
            return false;
        }
        if (Numere.isInteger(zi)) {
            nzi = Numere.stringToInt(zi);
        } else {
            eroare("Ziua nu este un numar intreg.");
            return false;
        }
        return testALZ(nan, nluna, nzi);
    }

    private boolean testALZ(int an, int luna, int zi) {
        boolean raspuns = false;
        String xALZ = " - " + an + "-" + luna + "-" + zi;
        if (0 < luna && luna < 13) {
            if (zi > 0) {
                switch (luna) {
                    case 1:
                        if (zi > 31) {
                            eroare("Luna ianuarie are maxim 31 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 2:
                        if (isAnBisect(an)) {
                            if (zi > 29) {
                                eroare("Luna februarie are maxim 29 de zile." + xALZ);
                                return false;
                            }
                        } else {
                            if (zi > 28) {
                                eroare("Luna februarie are maxim 28 de zile." + xALZ);
                                return false;
                            }
                        }
                        break;
                    case 3:
                        if (zi > 31) {
                            eroare("Luna martie are maxim 31 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 4:
                        if (zi > 30) {
                            eroare("Luna aprilie are maxim 30 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 5:
                        if (zi > 31) {
                            eroare("Luna mai are maxim 31 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 6:
                        if (zi > 30) {
                            eroare("Luna iunie are maxim 30 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 7:
                        if (zi > 31) {
                            eroare("Luna iulie are maxim 31 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 8:
                        if (zi > 31) {
                            eroare("Luna august are maxim 31 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 9:
                        if (zi > 30) {
                            eroare("Luna septembrie are maxim 30 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 10:
                        if (zi > 31) {
                            eroare("Luna octombrie are maxim 31 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 11:
                        if (zi > 30) {
                            eroare("Luna noiembrie are maxim 30 de zile." + xALZ);
                            return false;
                        }
                        break;
                    case 12:
                        if (zi > 31) {
                            eroare("Luna decembrie are maxim 31 de zile." + xALZ);
                            return false;
                        }
                        break;
                    default:
                        eroare("Luna nu poate fi mai mare de 12." + xALZ);
                        return false;
                }
                return true;
            }
        } else {
            eroare("Luna nu este un număr cuprins între 1 și 12.");
        }
        return raspuns;
    }

    private void eroare(String mesaj) {
        mesajEroare = mesaj;
        if (eDesktop) {
            JOptionPane.showMessageDialog(null, mesajEroare);
        }
        if (verbose) {
            System.out.println(mesajEroare);
        }
    }

    public long dateDiff(Date startDate, Date endDate) {
        Calendar sDate = new GregorianCalendar();
        sDate.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
        sDate.setTime(startDate);
        Calendar eDate = new GregorianCalendar();
        eDate.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
        eDate.setTime(endDate);
        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    public static int nrZileLuna(int an, int luna) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));
        cal.set(an, luna, 1); // aici pune prima zi luna urmatoare
        cal.add(Calendar.DAY_OF_MONTH, -1);  // inapoi o zi
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isSD(int an, int nrLuna, int zi) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));
        cal.set(an, nrLuna, zi); // aici pune prima zi luna urmatoare
        return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }
}

package dorel.basicopp.sumecontrol;

import dorel.basicopp.datatypes.MyDate;
import dorel.basicopp.datatypes.Numere;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Cnp {

    private String mesajEroare;
    //
    private String cnp;
    private String S;   // sexul
    private String Sex; // sexul M, F
    private String AA;  // anul
    private String LL;  // luna
    private String ZZ;  // ziua
    private String JJ;  // judetul 01 - 52

    private String secol;  // secolul datei nasterii
    private Date data;     // data nasterii

    private boolean eCorect;

    public String getMesajEroare() {
        return mesajEroare;
    }

    public Cnp() {
        this.cnp = "";
        eCorect = false;
    }

    //<editor-fold defaultstate="collapsed" desc="Public">
    public boolean testCNP(String cnp) {
        this.cnp = cnp;
        eCorect = testCNP();
        return eCorect;
    }

    public String getSexMFX() {
        if (eCorect) {
            return Sex;
        } else {
            return "X";
        }
    }

    public String getCodSex09() {
        if (eCorect) {
            return S;
        } else {
            return "0";
        }
    }

    public Date getDataNasterii() {
        if (eCorect) {
            return data;
        } else {
            return null;
        }
    }

    public boolean testSALZ(String cnp, String sex, int anul, int luna, int ziua) {
        mesajEroare = "";
        if (testCNP(cnp)) {
            if (!sex.equals(Sex)) {
                mesajEroare += "Sexul '" + sex + "' nu este corect (" + Sex + ").";
            }
            if (anul != Integer.parseInt(secol + AA)) {
                mesajEroare += "Anul '" + anul + "' nu este corect (" + secol + AA + ").";
            }
            if (luna != Integer.parseInt(LL)) {
                mesajEroare += "Luna '" + luna + "' nu este corectă (" + LL + ").";
            }
            if (ziua != Integer.parseInt(ZZ)) {
                mesajEroare += "Ziua '" + luna + "' nu este corectă (" + ZZ + ").";
            }
        }
        return mesajEroare.isEmpty();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private">
    private boolean testCNP() {
        //  SAALLZZJJNNNC
        mesajEroare = "";
        if (cnp.isEmpty()) {
            mesajEroare = "Stringul este gol.";
            return false;
        }
        if (!Numere.isLong(cnp)) {
            mesajEroare = "In string nu sunt NUMAI numere CNP='" + cnp + "'";
            return false;
        }
        if (cnp.length() != 13) {
            mesajEroare = "Stringul nu contine 13 cifre. CNP=" + cnp;
            return false;
        }

        // Extrage partile componente ale CNP
        S = cnp.substring(0, 1);
        AA = cnp.substring(1, 3);
        LL = cnp.substring(3, 5);
        ZZ = cnp.substring(5, 7);
        JJ = cnp.substring(7, 9);

        if (!testSexSecol()) {
            mesajEroare = "Sexul nu poate fi '0'." + " CNP=" + cnp;
            return false;
        }

        if (!testDataNasterii()) {
            // mesajul de eroare este creat in functie
            return false;
        }

        // Test Judet
        int nJJ = Integer.parseInt(JJ);
        if (0 < nJJ && nJJ <= 52) {
            // e bine
        } else {
            mesajEroare = "Judetul NU este un numar intre: 1-52:" + JJ + " CNP=" + cnp;
            return false;
        }

        // Test cifra de control
        int cifraControl;
        String pondere = "279146358279";
        int sumaControl = 0;
        for (int i = 0; i < 12; i++) {
            String s1 = cnp.substring(i, i + 1);
            String s2 = pondere.substring(i, i + 1);
            try {
                sumaControl += Integer.parseInt(s1) * Integer.parseInt(s2);
            } catch (NumberFormatException ex) {
                mesajEroare = "Eroare la calcul sumaControl. CNP=" + cnp;
                return false;
            }
        }
        cifraControl = sumaControl % 11;
        if (cifraControl > 9) {
            cifraControl = 1;
        }
        if (cifraControl == Integer.parseInt(cnp.substring(cnp.length() - 1))) {
            mesajEroare = "";
            return true;
        } else {
            mesajEroare = "Cifra control nu este corecta. CNP=" + cnp;
            return false;
        }
        //return cifraControl == Integer.parseInt(cnp.substring(cnp.length() - 1));
    }

    private boolean testSexSecol() {
        // Test Sex -> setare Secol
        switch (S) {
            case "1":
                // 1,2 1900-1999
                secol = "19";
                Sex = "M";
                break;
            case "2":
                secol = "19";
                Sex = "F";
                break;
            case "3":
                // 3,4 1800-1899
                secol = "18";
                Sex = "M";
                break;
            case "4":
                secol = "18";
                Sex = "F";
                break;
            case "5":
                // 5,6 2000-2099
                secol = "20";
                Sex = "M";
                break;
            case "6":
                secol = "20";
                Sex = "F";
                break;
            case "7":
                // 7,8 straini rezidenti 1900+
                secol = "19";
                Sex = "M";
                break;
            case "8":
                secol = "19";
                Sex = "F";
                break;
            case "9":
                // 9 straini 1900+
                secol = "19";
                break;
            default:
                // Sexul nu poate fi "0"
                return false;
        }
        return true;
    }

    private boolean testDataNasterii() {
        String sData;
        // Test luna
        int nLuna = Integer.parseInt(LL);
        if (0 < nLuna && nLuna < 13) {
            // e bine
        } else {
            mesajEroare = "Luna nasterii (" + LL + ") nu este valida (1-12)" + " CNP=" + cnp;
            return false;
        }

        // Test zi
        int nZi = Integer.parseInt(ZZ);
        if (nZi <= 0) {
            // nu ar trebui sa apara
            mesajEroare = "Ziua nasterii (" + ZZ + ") nu poate fi 0 sau negativa:" + LL + " CNP=" + cnp;
            return false;
        }
        if (nLuna == 1 || nLuna == 3 || nLuna == 5 || nLuna == 7 || nLuna == 8 || nLuna == 10 || nLuna == 12) {
            if (nZi < 32) {
                // e bine
            } else {
                mesajEroare = "Ziua nasterii (" + ZZ + ") nu este valida (1-31) in luna:" + LL + " CNP=" + cnp;
                return false;
            }
        } else {
            if (nLuna == 2) {
                if (nZi < 30) {
                    // e bine - ne verificat an bisect - se va verifica pe calendar
                    if (MyDate.isAnBisect(Integer.parseInt(secol + AA))) {
                        // e bine
                    } else {
                        if (nZi < 29) {
                            // e bine
                        } else {
                            mesajEroare = "Ziua nasterii (" + ZZ + ") nu este valida (1-28) in luna:" + LL + " CNP=" + cnp;
                            return false;
                        }
                    }
                } else {
                    mesajEroare = "Ziua nasterii (" + ZZ + ") nu este valida (1-29) in luna:" + LL + " CNP=" + cnp;
                    return false;
                }
            } else {
                if (nZi < 31) {
                    // e bine
                } else {
                    mesajEroare = "Ziua nasterii (" + ZZ + ") nu este valida (1-30) in luna:" + LL + " CNP=" + cnp;
                    return false;
                }
            }
        }

        // test data valida
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
        dfm.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest")); // EET, Eastern Eeuropean Time
        sData = secol + AA + "-" + LL + "-" + ZZ;
        try {
            data = dfm.parse(sData);
            // la zi ce depaseste nr zile in luna, el trece in luna urmatoare!!!
            cal.setTime(data);
            if (cal.get(Calendar.YEAR) == Integer.parseInt(secol + AA)) {
                // e bine
            } else {
                mesajEroare = "Data nasterii nu este valida (an):" + sData + " CNP=" + cnp;
                return false;
            }
            if (cal.get(Calendar.MONTH) == (Integer.parseInt(LL) - 1)) {
                // e bine
            } else {
                mesajEroare = "Data nasterii nu este valida (luna):" + sData + " CNP=" + cnp;
                return false;
            }
            if (cal.get(Calendar.DAY_OF_MONTH) == (Integer.parseInt(ZZ))) {
                // e bine
            } else {
                mesajEroare = "Data nasterii nu este valida (ziua):" + sData + " CNP=" + cnp;
                return false;
            }
        } catch (ParseException ex) {
            mesajEroare = "Data nasterii nu este valida:" + sData + " CNP=" + cnp;
            return false;
        }
        return true;
    }
    //</editor-fold>
}

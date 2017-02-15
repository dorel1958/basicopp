package dorel.basicopp.majorari;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class Majorari {
    // calcul majorari lunare cu procMajLuna

    public double procMajLuna;
    //
    private List<LinieMajorare> listaCalcule;
    private Calendar cal;
    private final int nrDataMin;
    private final int nrDataMax;

    public void setProcMajLuna(double procMajLuna) {
        this.procMajLuna = procMajLuna;
    }

    public Majorari(Date dataMin, Date dataMax) {
        cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));
        nrDataMin = getNrData(dataMin);
        nrDataMax = getNrData(dataMax);
        init();
    }

    public Majorari(int nrDataMin, int nrDataMax) {
        this.nrDataMin = nrDataMin;
        this.nrDataMax = nrDataMax;
        init();
    }

    private void init() {
        if (cal == null) {
            cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));
        }
        procMajLuna = 2;
        listaCalcule = new LinkedList<>();
        for (int i = nrDataMin; i <= nrDataMax; i++) {
            listaCalcule.add(new LinieMajorare(i));
        }
    }

    public void addMiscare(boolean eIntrare, Date data, double suma) {
        int nrData = getNrData(data);
        if (nrDataMin <= nrData && nrData <= nrDataMax) {
            listaCalcule.stream().filter((linieMajorare) -> (linieMajorare.nrData == nrData)).forEach((linieMajorare) -> {
                if (eIntrare) {
                    linieMajorare.intrari += suma;
                } else {
                    linieMajorare.iesiri += suma;
                }
            });
        }
    }

    public double getMajorare() {
        double soldCurent = 0;
        double majorare = 0;
        for (LinieMajorare linieMajorare : listaCalcule) {
            soldCurent = linieMajorare.calcul(soldCurent, procMajLuna);
            majorare += linieMajorare.majLuna;
        }
        return majorare;
    }

    private int getNrData(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.YEAR) * 12 + cal.get(Calendar.MONTH);
    }
}

// Exemplu utilizare:
//        Calendar cal;
//        cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));
//
//        cal.set(2000, Calendar.JANUARY, 1);
//        Date dataIni = cal.getTime();
//        cal.set(2010, Calendar.JANUARY, 1);
//        Date dataFin = cal.getTime();
//
//        Majorari maj = new Majorari(dataIni, dataFin);
//        maj.setProcMajLuna(10);
//
//        cal.set(2001, Calendar.JANUARY, 1);
//        Date data1 = cal.getTime();
//        cal.set(2002, Calendar.JANUARY, 1);
//        Date data2 = cal.getTime();
//
//        maj.addMiscare(true, dataIni, 100);
//        maj.addMiscare(true, data1, 10);
//        maj.addMiscare(false, data2, 50);
//
//        double majorarea = maj.getMajorare();

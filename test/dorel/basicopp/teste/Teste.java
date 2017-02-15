package dorel.basicopp.teste;

import dorel.basicopp.datatypes.MyDate;
import dorel.basicopp.excelreport.FisExcel;
import dorel.basicopp.excelreport.Report;
import dorel.basicopp.io.FileFilterXML;
import dorel.basicopp.io.OpenSaveDialog;
import dorel.basicopp.io.TextWriter;
import dorel.basicopp.majorari.Majorari;
import dorel.basicopp.server.ServerHelper;
import dorel.basicopp.server.ServerInterface;
import dorel.basicopp.sumecontrol.CalculMD5;
import dorel.basicopp.sumecontrol.Cui;
import dorel.basicopp.sumecontrol.Iban;
import dorel.basicopp.suportXML.DocumentXML;
import dorel.basicopp.suportXML.ElementXML;
import dorel.basicopp.swing.FrameExecutie;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;

public class Teste {

    public void connectPostgress() {
        ResultSet rs;
//        //ServerInterface psql = new PostgresSqlServer(true);
//        psql.setConnectionString(PostgresSqlServer.getConnectionString("localhost", "5432", "zzz", "postgres", "MisiPisi"));
//        if (psql.openConnection()) {
//            String comanda = "SELECT * FROM mysqhema.mytable";
//            if (psql.execQueryRs(comanda)) {
//                try {
//                    rs = psql.getResultSet();
//                    while (rs.next()) {
//                        int idVal = rs.getInt("id");
//                        String nameVal = rs.getString("nume");
//                        String prenVal = rs.getString("prenume");
//                        System.out.println(
//                                "id = " + idVal
//                                + ", nume = " + nameVal
//                                + ", prenume = " + prenVal);
//                    }
//                    rs.close();
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(null, "Eroare SQL:" + ex.getLocalizedMessage());
//                }
//            }
//            psql.closeConnection();
//        }
    }

    public void connectMsSql() {
        ResultSet rs;
//        ServerInterface sql = new MsSqlServer("sa", "MisiPisi");
//        sql.setConnectionString(MsSqlServer.getConnectionString("localhost", "1433", "test"));
//        if (sql.openConnection()) {
//            String comanda = "SELECT * FROM dbo.mytable";
//            if (sql.execQueryRs(comanda)) {
//                try {
//                    rs = sql.getResultSet();
//                    while (rs.next()) {
//                        int idVal = rs.getInt("id");
//                        String nameVal = rs.getString("nume");
//                        String prenVal = rs.getString("prenume");
//                        System.out.println(
//                                "id = " + idVal
//                                + ", nume = " + nameVal
//                                + ", prenume = " + prenVal);
//                    }
//                    rs.close();
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(null, "Eroare SQL:" + ex.getLocalizedMessage());
//                }
//            }
//            sql.closeConnection();
//        }
    }

    public void connectMySQL() {
        ServerInterface sql = new ServerHelper("MySQL");
        if (sql.openConnection()) {
            JOptionPane.showMessageDialog(null, "S-a conectat");

        }

    }

    public boolean testMyDate() {
        boolean eCorect = true;
        dorel.basicopp.datatypes.MyDate md = new dorel.basicopp.datatypes.MyDate(false);
        String raspuns;
        String raspunsCorect;
        Date rData;
        Date rDataCorect;

        raspuns = md.dataToStringDMY(new Date(0));
        raspunsCorect = "01.01.1970";
        if (!raspuns.equals(raspunsCorect)) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare dataToStringDMY: raspuns=" + raspuns + " corect=" + raspunsCorect);
        }

        raspuns = md.dataToStringYMD(new Date(0));
        raspunsCorect = "1970-01-01";
        if (!raspuns.equals(raspunsCorect)) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare dataToStringYMD: raspuns=" + raspuns + " corect=" + raspunsCorect);
        }

        raspuns = md.dataToStringYMDTime(new Date(0));
        raspunsCorect = "1970-01-01 02:00:00";
        if (!raspuns.equals(raspunsCorect)) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare dataToStringYMDTime: raspuns=" + raspuns + " corect=" + raspunsCorect);
        }

        rData = md.stringDMYToData("01.01.1970");
        rDataCorect = new Date(0);
        if (rData.getTime() == rDataCorect.getTime()) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare dataToStringYMDTime: raspuns=" + raspuns + " corect=" + raspunsCorect);
        }

        rData = md.stringDMYToData("  .  .    ");
        raspuns = md.getMesajEroare();
        if (!(rData == null && raspuns.isEmpty())) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare stringDMYToData(\"  .  .    \")");
        }

        rData = md.stringDMYToData("null");
        raspuns = md.getMesajEroare();
        if (!(rData == null && raspuns.isEmpty())) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare stringDMYToData(\"null\")");
        }

        rData = md.stringDMYToData(" 1.  .    ");
        raspuns = md.getMesajEroare();
        if (!(rData == null && !raspuns.isEmpty())) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare stringDMYToData(\" 1.  .    \")");
        }

        rData = md.stringYMDToData("1970-01-01");
        rDataCorect = new Date(0);
        if (rData.getTime() == rDataCorect.getTime()) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare stringYMDToData: raspuns=" + raspuns + " corect=" + raspunsCorect);
        }

        rData = md.stringYMDToData("0000-00-00");
        raspuns = md.getMesajEroare();
        if (!(rData == null && raspuns.isEmpty())) {
            eCorect = false;
            JOptionPane.showMessageDialog(null, "Eroare stringDMYToData(\"0000-00-00\")");
        }

        Date datamin = md.longToDate(Long.MIN_VALUE);
        System.out.println("Long.MIN_VALUE = " + Long.MIN_VALUE);
        System.out.println("datamin = " + md.dataToStringDMY(datamin));
        Date datamax = md.longToDate(Long.MAX_VALUE);
        System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);
        System.out.println("datamax = " + md.dataToStringDMY(datamax));
        return eCorect;
    }

    public void testXML() {

        String sursa = OpenSaveDialog.openFile(new FileFilterXML());

        ElementXML element = DocumentXML.FisierToElement(sursa);

        element.selfTest();

        //Document doc = DocumentXML.ElementToDocXML(element);
        String destinatie = OpenSaveDialog.saveFile(new FileFilterXML());
        //DocumentXML.docXMLToFileXML(doc, destinatie);
        TextWriter tw = new TextWriter(destinatie, false);
        tw.writeLine("<?xml version=\"1.0\"?>");
        element.writeElementXml(tw);
        tw.close();

    }

    public void testOpenSave() {
        String[] astr = {"xml", "txt"};
        String numeFis;
        numeFis = OpenSaveDialog.openFile("XML documents", astr);
        JOptionPane.showMessageDialog(null, numeFis);
        numeFis = OpenSaveDialog.saveFile("XML documents", astr);
        JOptionPane.showMessageDialog(null, numeFis);
    }

    public void ExcelReportExample() {
        Report excelReport = new Report("MateriiPrime.xls", FisExcel.TipFisExcel.xls);

        excelReport.genExcel("MateriiPrime");
        String[] content;

        // randuri
        content = new String[1];
        content[0] = "TITLU MARE";
        excelReport.addRow(0, 0, content);
        excelReport.mergeCells(0, 0, 0, 5, true);

        content = new String[2];
        content[0] = "Anul: 2014";
        content[1] = "Luna: Septembrie";
        excelReport.addRow(0, 0, content);

        // formatare
        List<Integer> lista = new ArrayList<>();
        lista.add(0);
        lista.add(1);
        excelReport.autoSizeColumns(lista);

        excelReport.writeToFile();
        excelReport.viewFisExcel();
    }

    public void testMD5() {
        String string = "admțn";
        String rasp = CalculMD5.getStringMD5(string);
        //21232f297a57a5a743894a0e4a801fc3 - 32 char
        //21232f297a57a5a743894a0e4a801fc3 - sursa externa
        //E92888CDE9288F2F4AE9
        char[] ch = {'a', 'd', 'm', 'ț', 'n'};
        String raspc = CalculMD5.getCharsMD5(ch);
    }

    public void testCUICNP() {
        Cui cui = new Cui();
        boolean raspuns; //=cui.testCUI("1580402384180", true);
        //raspuns=cui.testCUI("2741203160025", true);
        raspuns = cui.testCUI("3450130", true);
        JOptionPane.showMessageDialog(null, String.valueOf(raspuns));
    }

    public void testDataValida() {
        String raspuns = MyDate.verif_DMYData("1.9.2013", ".");
        raspuns += "|";
    }

    public boolean testIBAN(String cont) {
        Iban iban = new Iban();
        return iban.testIBAN(cont);
    }

    public void testFrameExecutie() {
        FrameExecutie fe = new FrameExecutie(10);
//        fe.setVisible(true);
//        fe.startProgressBar("Asteptati ...");
//        fe.addPas(1);
//        fe.addPas(1);
//        fe.addPas(1);
//        fe.addPas(1);
//        fe.addPas(1);
//        fe.addPas(1);
//        fe.addPas(1);
        //JOptionPane.showMessageDialog(null, "fe");
        //fe.setVisible(false);
    }

    public void testMajorari() {
        Calendar cal;
        cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Bucharest"));

        cal.set(2000, Calendar.JANUARY, 1);
        Date dataIni = cal.getTime();
        cal.set(2010, Calendar.JANUARY, 1);
        Date dataFin = cal.getTime();

        Majorari maj = new Majorari(dataIni, dataFin);
        maj.setProcMajLuna(10);

        cal.set(2001, Calendar.JANUARY, 1);
        Date data1 = cal.getTime();
        cal.set(2002, Calendar.JANUARY, 1);
        Date data2 = cal.getTime();

        maj.addMiscare(true, dataIni, 100);
        maj.addMiscare(true, data1, 10);
        maj.addMiscare(false, data2, 50);

        double majorarea = maj.getMajorare();
    }

    public void testBigDecimal() {
        String valoare;
        double dublu=12.32659;
//        BigDecimal bd = new BigDecimal(Math.round(dublu * 100));
//        valoare=bd.toPlainString();
//        BigDecimal bdf = bd.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
//        valoare=bdf.toPlainString();
        if (Math.abs(dublu-Math.round(dublu))<0.01){
            int idublu=(int)dublu;
            valoare= String.valueOf(idublu);
        } else {
            BigDecimal bd=new BigDecimal(Math.round(dublu*100));
            BigDecimal bdf=bd.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
            valoare= bdf.toPlainString();
        }
    }
}

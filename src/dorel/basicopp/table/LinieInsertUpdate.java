package dorel.basicopp.table;

import java.util.List;

public class LinieInsertUpdate {

    private String nume;
    private String valoare;

    public String getNume() {
        return nume;
    }

    public String getValoare() {
        return valoare;
    }

    public LinieInsertUpdate(String nume, String valoare) {
        this.nume = nume;
        this.valoare = valoare;
    }

    public static String getStringInsert(String numeTabela, List<LinieInsertUpdate> lista) {
        String comanda;
        boolean ePrima;
        comanda = "INSERT INTO " + numeTabela + "(";
        ePrima = true;
        for (LinieInsertUpdate linie : lista) {
            if (ePrima) {
                ePrima = false;
            } else {
                comanda += ",";
            }
            comanda += linie.getNume();
        }
        comanda += ") VALUES (";
        ePrima = true;
        for (LinieInsertUpdate linie : lista) {
            if (ePrima) {
                ePrima = false;
            } else {
                comanda += ",";
            }
            comanda += linie.getValoare();
        }
        comanda += ")";
        //System.out.println(comanda);
        return comanda;
    }

    public static String getStringUpdate(String numeTabela, int id, List<LinieInsertUpdate> lista) {
        String comanda;
        boolean ePrima;
        comanda = "UPDATE " + numeTabela + " SET";
        ePrima = true;
        for (LinieInsertUpdate linie : lista) {
            if (ePrima) {
                ePrima = false;
                comanda += " ";
            } else {
                comanda += ",";
            }
            comanda += linie.getNume() + "=" + linie.getValoare();
        }
        comanda += " WHERE";
        comanda += " id=" + id + ";";
        return comanda;
    }
}
//            List<LinieInsert> lista = new ArrayList<>();
//            //
//            lista.add(new LinieInsert("pfpj", cladirePJ.getRol().getPfpj().getMondoPfPjString()));
//            lista.add(new LinieInsert("nrro", String.valueOf(cladirePJ.getRol().getNrRol())));
//            lista.add(new LinieInsert("srol", String.valueOf(cladirePJ.getRol().getNrSrol())));
//            lista.add(new LinieInsert("nrol", cladirePJ.getRol().getNrolMondoString()));
//            lista.add(new LinieInsert("tipo", "0"));
//            lista.add(new LinieInsert("stip", "0"));
//            lista.add(new LinieInsert("dent", ServerTools.sqlString(cladirePJ.getDenTaxa())));
//            lista.add(new LinieInsert("grouptaxa", ServerTools.sqlString(cladirePJ.getDenTaxa())));
//            //
//            lista.add(new LinieInsert("tx1", ServerTools.sqlString(cladirePJ.getDocumente().getFormaDetinere().getStringFormaDetinere())));
//            lista.add(new LinieInsert("tx2", ServerTools.sqlString(cladirePJ.getNrExtrasCarteFunciara())));
//            lista.add(new LinieInsert("tx3", ServerTools.sqlString(cladirePJ.getNrTopo())));
//            lista.add(new LinieInsert("tx4", ServerTools.sqlString(cladirePJ.getAdresa())));
//            lista.add(new LinieInsert("tx5", ServerTools.sqlString(cladirePJ.getDestinatie())));
//            lista.add(new LinieInsert("tx6", MySqlStringDouble(cladirePJ.getValoareInventar())));
//            lista.add(new LinieInsert("tx7", ServerTools.sqlString(cladirePJ.getStareAmortizare().getStringStareAmortizare())));
//            lista.add(new LinieInsert("tx8", ServerTools.sqlDate(cladirePJ.getDataReevaluare())));
//            lista.add(new LinieInsert("tx9", ServerTools.sqlString(cladirePJ.getStareReevaluare().getStringStareReevaluare())));
//            lista.add(new LinieInsert("tx10", "''")); // tx10 - cota impozitare
//            lista.add(new LinieInsert("tx11", MySqlStringDouble(cladirePJ.getImpozitCalculat_ei())));
//            lista.add(new LinieInsert("tx12", "''"));// tx12 - impozit lunar
//            //
//            String comanda = getStringInsert(lista);

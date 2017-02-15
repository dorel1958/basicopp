package dorel.basicopp.sumecontrol;

import dorel.basicopp.datatypes.Numere;

public class Cui {
    
    private String cui;
    private String mesajEroare;

    public String getMesajEroare() {
        return mesajEroare;
    }

    public Cui() {
        this.cui = "";
        mesajEroare="";
    }

    public boolean testCUI(String cui, boolean cuCNP) {
        this.cui = cui;
        return testCUI(cuCNP);
    }

    private boolean testCUI(boolean cuCNP) {
        //String lcui=this.cui;
        if (cui.isEmpty()) {
            mesajEroare = "Stringul este gol.";
            return false;
        }
        if (!Numere.isLong(cui)) {
            mesajEroare = "In string nu sunt NUMAI numere:'" + cui + "'";
            return false;
        }
        int lungimeCui;
        int cifraControl;
        lungimeCui = cui.length();
        if (lungimeCui < 2 || 10 < lungimeCui) {
            // PARE putin dezordonata, dar Foarte rar apare CNP pe post de CUI!!
            if (cuCNP) {
                Cnp cnp = new Cnp();
                if (cnp.testCNP(cui)) {
                    // e bine
                    mesajEroare = "Nu e CUI e CNP!";
                    return true;
                } else {
                    mesajEroare = "Eroare CNP in CUI:"+cnp.getMesajEroare();
                    return false;
                }
            } else {
                mesajEroare = "Lungimea nu este intre 2 si 10: '" + cui + "'";
                return false;
            }
        } else {
            String pondere = "7532175321";
            pondere = pondere.substring(10 - lungimeCui);
            int sumaControl = 0;
            for (int i = 0; i < lungimeCui - 1; i++) {
                String s1 = cui.substring(i, i + 1);
                String s2 = pondere.substring(i, i + 1);
                try {
                    sumaControl += Integer.parseInt(s1) * Integer.parseInt(s2);
                } catch (NumberFormatException ex) {
                    mesajEroare = "Eroare la calcul sumaControl. CUI=" + cui;
                    return false;
                }
            }
            cifraControl = ((sumaControl * 10) % 11) % 10;
            if (cui.endsWith(String.valueOf(cifraControl))) {
                mesajEroare = "";
                return true;
            } else {
                mesajEroare = "Cifra control nu este corecta. CUI=" + cui;
                return false;
            }
        }
    }

}

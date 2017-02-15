package dorel.basicopp.sumecontrol;

public class Iban {
    
    //private String iban;
    private String mesajEroare;

    public String getMesajEroare() {
        return mesajEroare;
    }

    public Iban() {
        //iban = "";
        mesajEroare = "";
    }

    public boolean testIBAN(String iban) {
        //this.iban=iban;
        boolean raspuns;
        mesajEroare = "";
        if (iban.length() != 24) {
            mesajEroare = "IBAN mai scurt de 24 caractere:'" + iban + "'";
            return false;
        }
        // RO65TREZ6715069XXX004479
        // cont valid: RO84TREZ6715069XXX001421
        String codTara = iban.substring(0, 2);        // 2:  RO
        String nrVerifString = iban.substring(2, 4);  // 2:  72
        //String BBAN = iban.substring(4);
        //String bicBanca = iban.substring(4, 8);       // 4:  AAAA
        //String sucCont = iban.substring(8);           // 16: 1234 1234 1234 1234

        // primele 2 - RO
        if (codTara.equals("RO")) {
            // e din Romania - continua
        } else {
            mesajEroare = "IBAN nu incepe cu RO:'" + iban + "'";
            return false;
        }
        // urmat 2 - numar intreg
        int nrVerif;
        try {
            nrVerif = Integer.parseInt(nrVerifString);
            if (nrVerif < 0 || nrVerif > 96) {
                mesajEroare = "Cifrele de control de dupa RO (restul impartirii la 97=" + nrVerifString + ") nu sunt intre (0-96)";
                return false;
            }
        } catch (NumberFormatException ex) {
            mesajEroare = "IBAN NU contine 2 cifre dupa codul de tara:'" + iban + "'";
            return false;
        }

        // 4 caractere - BIC banca

        // 16 caractere sau numere

        // test
        String caracter;
        String ibanul = iban.trim();
        String ibanult;
        // trec la litere mari
        ibanul = ibanul.toUpperCase();
        // elimnin spatiile goale
        ibanul = ibanul.replaceAll(" ", "");
        // mut primele 4 caractere la final
        ibanul = ibanul.substring(4) + ibanul.substring(0, 4);
        // substitui caracterele cu numere A=10, B=11, ... Z=35
        ibanult = "";
        for (int i = 0; i < ibanul.length(); i++) {
            caracter = ibanul.substring(i, i + 1);
            switch (caracter) {
                case "0":
                    ibanult += "0";
                    break;
                case "1":
                    ibanult += "1";
                    break;
                case "2":
                    ibanult += "2";
                    break;
                case "3":
                    ibanult += "3";
                    break;
                case "4":
                    ibanult += "4";
                    break;
                case "5":
                    ibanult += "5";
                    break;
                case "6":
                    ibanult += "6";
                    break;
                case "7":
                    ibanult += "7";
                    break;
                case "8":
                    ibanult += "8";
                    break;
                case "9":
                    ibanult += "9";
                    break;
                case "A":
                    ibanult += "10";
                    break;
                case "B":
                    ibanult += "11";
                    break;
                case "C":
                    ibanult += "12";
                    break;
                case "D":
                    ibanult += "13";
                    break;
                case "E":
                    ibanult += "14";
                    break;
                case "F":
                    ibanult += "15";
                    break;
                case "G":
                    ibanult += "16";
                    break;
                case "H":
                    ibanult += "17";
                    break;
                case "I":
                    ibanult += "18";
                    break;
                case "J":
                    ibanult += "19";
                    break;
                case "K":
                    ibanult += "20";
                    break;
                case "L":
                    ibanult += "21";
                    break;
                case "M":
                    ibanult += "22";
                    break;
                case "N":
                    ibanult += "23";
                    break;
                case "O":
                    ibanult += "24";
                    break;
                case "P":
                    ibanult += "25";
                    break;
                case "Q":
                    ibanult += "26";
                    break;
                case "R":
                    ibanult += "27";
                    break;
                case "S":
                    ibanult += "28";
                    break;
                case "T":
                    ibanult += "29";
                    break;
                case "U":
                    ibanult += "30";
                    break;
                case "V":
                    ibanult += "31";
                    break;
                case "W":
                    ibanult += "32";
                    break;
                case "X":
                    ibanult += "33";
                    break;
                case "Y":
                    ibanult += "34";
                    break;
                case "Z":
                    ibanult += "35";
                    break;
                default:
                    ibanult += "";
                    break;
            }
        }
        // aplic algoritmul MOD 97-10
        raspuns = MOD_97_10(ibanult);
        if (!raspuns){
            mesajEroare="Cifra de control nu este corecta.";
        }
        return raspuns;
    }

    private boolean MOD_97_10(String numarul) {
        int digitRest = numarul.length();
        int pas = 9;
        int poz = 0;
        String nString;
        int nInteger;
        int rest = 0;
        String sRest = "";
        while (digitRest > 0) {
            // construct n from the first 9 digits of D or rerast+next 7
            nString = sRest + numarul.substring(poz, poz + pas);
            nInteger = Integer.parseInt(nString);
            rest = nInteger % 97;
            sRest = String.valueOf(rest);
            digitRest = digitRest - pas;
            poz = poz + pas;
            if (digitRest > 7) {
                pas = 7;
            } else {
                pas = digitRest;
            }
        }
        // restul este 1?
        return rest == 1;
    }

}

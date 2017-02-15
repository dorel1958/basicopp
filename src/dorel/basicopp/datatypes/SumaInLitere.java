package dorel.basicopp.datatypes;

import javax.swing.JOptionPane;

public class SumaInLitere {

    public static String getSumaInLitere(String parteaInt, String parteaZec) {
        // determina textul stringLeii
        String stringLeii;
        if (parteaInt.equals("1")) {
            stringLeii = "un";
        } else {
            int ordin = 0;
            stringLeii = "";
            int lungInt = parteaInt.length();
            while (lungInt > 3) {
                ordin += 1;
                String grupa = parteaInt.substring(lungInt - 3);
                parteaInt = parteaInt.substring(0, lungInt - 3);
                lungInt = parteaInt.length();
                stringLeii = grup3Litere(grupa, ordin) + stringLeii;
            }
            ordin += 1;
            stringLeii = grup3Litere(parteaInt, ordin) + stringLeii;
        }
        // concateneaza leii si banii
        String sumaInLitere;
        if (stringLeii.isEmpty()) {
            if (parteaZec.isEmpty()) {
                sumaInLitere = "";
            } else {
                sumaInLitere = parteaZec + "bani";
            }
        } else {
            if (stringLeii.equals("un")) {
                sumaInLitere = stringLeii + "leu";
            } else {
                sumaInLitere = stringLeii + "lei";
            }
            if (!parteaZec.isEmpty()) {
                sumaInLitere += "," + parteaZec + "bani";
            }
        }
        return sumaInLitere;
    }

    private static String grup3Litere(String valoarea, int ordinul) {
        // scoate sute, zeci, unitati din stringul primit
        int sute = 0;
        int zeci = 0;
        int unitati = 0;
        switch (valoarea.length()) {
            case 0:
                sute = 0;
                zeci = 0;
                unitati = 0;
                break;
            case 1:
                sute = 0;
                zeci = 0;
                unitati = Integer.parseInt(valoarea);
                break;
            case 2:
                sute = 0;
                zeci = Integer.parseInt(valoarea.substring(0, 1));
                unitati = Integer.parseInt(valoarea.substring(1));
                break;
            case 3:
                sute = Integer.parseInt(valoarea.substring(0, 1));
                zeci = Integer.parseInt(valoarea.substring(1, 2));
                unitati = Integer.parseInt(valoarea.substring(2));
                break;
        }

        //scrie textul
        String textSute = "";
        String textZeci;
        String textUnitati = "";
        if (sute > 0) {
            if (sute == 1) {
                textSute = numarToString(sute, "f") + "suta";
            } else {
                textSute = numarToString(sute, "f") + "sute";
            }
        }
        //
        String gen;
        if (ordinul == 2 || ordinul == 5) {
            gen = "f";
        } else {
            gen = "m";
        }
        //
        if (zeci * 10 + unitati < 20) {
            textZeci = "";
            textUnitati = numarToString(zeci * 10 + unitati, gen);
        } else {
            textZeci = numarToString(zeci, "z") + "zeci";
            if (unitati > 0) {
                textUnitati = "si" + numarToString(unitati, gen);
            }
        }
        //
        String grup3Litere = textSute + textZeci + textUnitati;
        // adauga sufixele de la ordinele superioare
        if (!grup3Litere.isEmpty()) {
            switch (ordinul) {
                case 1:
                    break;
                case 2:
                    if (grup3Litere.equals("una") || grup3Litere.equals("o")) {
                        grup3Litere += "mie";
                    } else {
                        grup3Litere += "mii";
                    }
                    break;
                case 3:
                    if (grup3Litere.equals("unu")) {
                        grup3Litere += "unmilion";
                    } else {
                        grup3Litere += "milioane";
                    }
                    break;
                case 4:
                    if (grup3Litere.equals("unu")) {
                        grup3Litere += "unmiliard";
                    } else {
                        grup3Litere += "miliarde";
                    }
                    break;
                case 5:
                    if (grup3Litere.equals("una") || grup3Litere.equals("o")) {
                        grup3Litere += "miemiliarde";
                    } else {
                        grup3Litere += "miimiliarde";
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "SumaInLitere.grup3Litere - Numar prea mare.");
            }
        }
        return grup3Litere;
    }

    private static String numarToString(int numar, String gen) {
        // gen poate fi m, f, z! (z la formare numar cu zeci - vezi 6)
        switch (numar) {
            case 0:
                return "";
            case 1:
                switch (gen) {
                    case "m":
                        return "unu";
                    case "f":
                        return "una";
                    //return "o";
                    default:
                        return "unu";
                }
            case 2:
                switch (gen) {
                    case "m":
                        return "doi";
                    case "f":
                        return "doua";
                    default:
                        // z
                        return "doua";
                }
            case 3:
                return "trei";
            case 4:
                return "patru";
            case 5:
                return "cinci";
            case 6:
                if (gen.equals("z")) {
                    return "sai";
                } else {
                    return "sase";
                }
            case 7:
                return "sapte";
            case 8:
                return "opt";
            case 9:
                return "noua";
            case 10:
                return "zece";
            case 11:
                return "unsprezece";
            case 12:
                switch (gen) {
                    case "m":
                        return "doisprezece";
                    case "f":
                        return "douasprezece";
                    default:
                        return "doisprezece";
                }
            case 13:
                return "treisprezece";
            case 14:
                return "paisprezece";
            case 15:
                return "cinsprezece";
            case 16:
                return "saisprezece";
            case 17:
                return "saptesprezece";
            case 18:
                return "optsprezece";
            case 19:
                return "nouasprezece";
            default:
                JOptionPane.showMessageDialog(null, "SumaInLitere.numarToString - Numar necunoscut.");
                return String.valueOf(numar);
        }
    }
}

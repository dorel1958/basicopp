package dorel.basicopp;

import dorel.basicopp.io.TextReader;
import dorel.basicopp.io.TextWriter;
import java.io.File;
import java.util.TreeMap;
import java.util.Map;

public class ConfigLocalHelper implements ConfigLocalInterface {

    private String numeFis;
    private Map<String, String> setari;
    boolean newKeys = false;

    //<editor-fold defaultstate="collapsed" desc="GetSet">
    @Override
    public void setNumeFis(String numeFis) {
        this.numeFis = numeFis;
    }

    @Override
    public String getValue(String key) {
        String value = setari.get(key);
        if (value == null) {
            value = "";
        }
        return value;
    }

    @Override
    public void setValue(String key, String value) {
        setari.put(key, value);
    }

    @Override
    public void setOnlyNewKey(String key, String value) {
        if (getValue(key).isEmpty()) {
            setValue(key, value);
            newKeys = true;
        }
    }

    @Override
    public boolean isNewKeys() {
        return newKeys;
    }

    @Override
    public void resetNewKeys() {
        newKeys = false;
    }
    //</editor-fold>

    public ConfigLocalHelper(String serverType) {
        numeFis = "set.ini";
        setari = new TreeMap<>();
        switch (serverType) {
            case "MySQL":
                setari.put("Server", "MySQL");
                setari.put("Host", "localhost");
                setari.put("Port", "3306");
                setari.put("Database", "test");
                setari.put("User", "root");
                setari.put("Pass", "");
                break;
            case "MSSQL":
                setari.put("Server", "MSSQL");
                setari.put("Host", "localhost");
                setari.put("Port", "1433");
                setari.put("Database", "test");
                setari.put("User", "sa");
                setari.put("Pass", "MisiPisi");
                break;
            case "Postgres":
                setari.put("Server", "Postgres");
                setari.put("Host", "localhost");
                setari.put("Port", "5432");
                setari.put("Database", "postgres");
                setari.put("User", "postgres");
                setari.put("Pass", "MisiPisi");
                break;
            default:
            // nu pune nimic
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Read / Write">
    @Override
    public void writeSettings() {
        String line;
        TextWriter tw = new TextWriter(numeFis, false);
        for (String key : setari.keySet()) {
            line = key + "=" + setari.get(key);
            tw.writeLine(line);
        }
        tw.close();
        newKeys = false;
    }

    @Override
    public void readSettings() {
        String line;
        File file = new File(numeFis);
        if (!file.exists()) {
            writeSettings();
        }
        TextReader tr = new TextReader(numeFis);
        int pozEgal;
        String cheie;
        String valoare;
        setari = new TreeMap<>();
        while ((line = tr.readLine()) != null) {
            if (line.startsWith("#") || line.isEmpty()) {
                // comment
            } else {
                pozEgal = line.indexOf("=");
                if (pozEgal > 0) { // contine key si =
                    cheie = line.substring(0, pozEgal);
                    valoare = line.substring(pozEgal + 1);
                    setari.put(cheie, valoare);
                } else {
                    // randurile fara egal in ele nu sunt perechi cheie - valoare -> comantarii
                }
            }
        }
        tr.close();
        newKeys = false;
    }
    //</editor-fold>

    @Override
    public void resetValues() {
        setari.clear();
    }
}

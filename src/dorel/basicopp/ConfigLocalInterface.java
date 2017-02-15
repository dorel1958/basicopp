package dorel.basicopp;

public interface ConfigLocalInterface {

    public void setNumeFis(String numeFis);

    public void resetValues();

    public void setValue(String key, String value);

    public String getValue(String key);

    public void setOnlyNewKey(String key, String value);

    public void resetNewKeys();

    public boolean isNewKeys();

    public void readSettings();

    public void writeSettings();
}

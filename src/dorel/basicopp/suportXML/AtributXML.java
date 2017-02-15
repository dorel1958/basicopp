package dorel.basicopp.suportXML;

import javax.swing.JOptionPane;

public class AtributXML {

    private String name;
    private String value;
    private boolean required;

    //<editor-fold defaultstate="collapsed" desc="Set Get">
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //</editor-fold>

    public AtributXML(String name, String value) {
        if (name==null){
            this.name = "atribut";
        } else {
            this.name = name;
        }
        if (value==null){
            this.value = "";
        } else {
            this.value = value;
        }
        this.required = true;
    }

    public AtributXML(String name, String value, boolean required) {
        if (name==null){
            this.name = "atribut";
        } else {
            this.name = name;
        }
        if (value==null){
            this.value = "";
        } else {
            this.value = value;
        }
        this.required = required;
    }

    public void selfTest() {
        if (name == null) {
            JOptionPane.showMessageDialog(null, "AtributXML - name null.");
        }
        if (value == null) {
            JOptionPane.showMessageDialog(null, "AtributXML - value null.");
        }

    }

}

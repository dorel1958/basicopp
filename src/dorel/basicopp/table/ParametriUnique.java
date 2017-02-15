package dorel.basicopp.table;

public class ParametriUnique {

    private String tableName;
    private String conditia;
    private int id;
    private String mesajEroare;

    public String getTableName() {
        return tableName;
    }

    public String getConditia() {
        return conditia;
    }

    public int getId() {
        return id;
    }
    
    public String getMesajEroare(){
        return mesajEroare;
    }

    public ParametriUnique(String tableName, String conditia, int id, String mesajEroare) {
        this.tableName = tableName;
        this.conditia = conditia;
        this.id = id;
        this.mesajEroare=mesajEroare;
    }

}

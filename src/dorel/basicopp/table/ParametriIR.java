package dorel.basicopp.table;

public class ParametriIR {
    
    private String tabela;
    private String conditia;
    private String mesajEroare;

    public String getTabela() {
        return tabela;
    }

    public String getConditia() {
        return conditia;
    }

    public String getMesajEroare() {
        return mesajEroare;
    }
    
    public ParametriIR(String tabela, String conditia, String mesajEroare) {
        this.tabela = tabela;
        this.conditia = conditia;
        this.mesajEroare=mesajEroare;
    }

}

package dorel.basicopp.html.helpers;

import dorel.basicopp.html.interfaces.HtmlTableColumnInterface;

public class HtmlTableColumnHelper implements HtmlTableColumnInterface {

    private Tip tip;
    //
    private final String title;
    private final String numeJava;
    private final int width;
    //
    private final String tabela_ref;
    private final String coloana_ref;
    //
    private final String linkul;

    //<editor-fold defaultstate="collapsed" desc="Set Get">
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getNumeJava() {
        return numeJava;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Tip getTip() {
        return tip;
    }

    @Override
    public String getColoana_ref() {
        return coloana_ref;
    }

    @Override
    public String getTabela_ref() {
        return tabela_ref;
    }

    @Override
    public String getLinkul() {
        return linkul;
    }
    //</editor-fold>

    public HtmlTableColumnHelper(String title, String numeJava, int width) {
        this.title = title;
        this.numeJava = numeJava;
        this.width = width;
        this.tabela_ref = "";
        this.coloana_ref = "";
        linkul = "";
    }

    public HtmlTableColumnHelper(String title, String numeJava, int width, Tip tip, String tabela_ref, String coloana_ref, String linkul) {
        this.title = title;
        this.numeJava = numeJava;
        this.width = width;
        this.tip = tip;
        //
        switch (tip) {
            case REFERINTA:
                // link spre raportul ce contine NUMAI randul respectiv din tabela referita (EX: date clientul curent fiind in raportul facturi enise)
                this.tabela_ref = tabela_ref;
                this.coloana_ref = coloana_ref;
                this.linkul = "";
                break;
            case LINK:
                // link spre raportul ce contine NUMAI randul curent din tabela curenta (EX: date client curent fiind in raportul tabela clienti)
                this.tabela_ref = "";
                this.coloana_ref = "";
                this.linkul = linkul;
                break;
            default:
                this.tabela_ref = "";
                this.coloana_ref = "";
                this.linkul = "";
        }
    }
}

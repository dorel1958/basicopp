package dorel.basicopp.html.interfaces;

public interface HtmlTableColumnInterface {

    public enum Tip{
        REFERINTA,
        LINK,
        VALOARE
    }
    
    public String getTitle();

    public String getNumeJava();

    public int getWidth();

    public Tip getTip();

    public String getTabela_ref();

    public String getColoana_ref();

    public String getLinkul();
}

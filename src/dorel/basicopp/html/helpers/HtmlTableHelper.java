package dorel.basicopp.html.helpers;

import dorel.basicopp.html.clases.StyleRule;
import dorel.basicopp.html.clases.StyleSheet;
import dorel.basicopp.html.interfaces.HtmlElementInterface;
import dorel.basicopp.html.interfaces.HtmlTableColumnInterface;
import dorel.basicopp.html.interfaces.HtmlTableRowInterface;
import java.util.ArrayList;
import java.util.List;

public class HtmlTableHelper {

    private final List<HtmlTableColumnInterface> columns;
    private final List<HtmlTableRowInterface> rows;

    public void addColumn(HtmlTableColumnHelper col) {
        columns.add(col);
    }

    public void addRow(HtmlTableRowInterface tri) {
        rows.add(tri);
    }

//    public void setColumns(List<HtmlTableColumnInterface> columns) {
//        this.columns = columns;
//    }
    public HtmlTableHelper() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }

    public HtmlTableHelper(List<HtmlTableColumnInterface> columns, List<HtmlTableRowInterface> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public static void addCss(StyleSheet style) {
        StyleRule sc;
        // O singura linie intre celule
        sc = new StyleRule("table");
        sc.addProp("border-collapse", "collapse");
        style.addStyleRule(sc);

        // border, aliniament text
        sc = new StyleRule("table, td, th");
        sc.addProp("border", "1px solid black");
        sc.addProp("text-align", "left");
        style.addStyleRule(sc);

        // header - font bold
        sc = new StyleRule("th");
        sc.addProp("font-weight", "bold");
        sc.addProp("background", "lightgray");
        style.addStyleRule(sc);

        // sa nu sublinieze si coloreze link
        sc = new StyleRule("a,u");
        sc.addProp("text-decoration", "none");
        sc.addProp("color", "black");
        style.addStyleRule(sc);
    }

    public HtmlElementInterface getElement() {
        HtmlElementInterface row;
        HtmlElementInterface cell;
        HtmlElementInterface table = new HtmlElementHelper("table");
        //
        // table header
        row = new HtmlElementHelper("tr");
        for (HtmlTableColumnInterface tc : columns) {
            cell = new HtmlElementHelper("th", tc.getTitle());
            cell.addAtribute("width", tc.getWidth() + "px");
            row.addHtmlElement(cell);
        }
        table.addHtmlElement(row);
        //
        // table content
        for (HtmlTableRowInterface tri : rows) {
            row = new HtmlElementHelper("tr");
            for (HtmlTableColumnInterface tc : columns) {
                switch (tc.getTip()) {
                    case REFERINTA:
                        //String ref = "<a href=\"/rlsv/lista_table?tabela=" + tc.getTabela_ref() + "&amp;id=" + tri.getValueByName(tc.getColoana_ref()) + "\" target=\"_blank\">" + tri.getValueByName(tc.getTitle()) + "</a>";
                        String ref = "<a href=\"/rlsv/lista_table?tabela=" + tc.getTabela_ref() + "&amp;id=" + tri.getValueByName(tc.getColoana_ref()) + "\" target=\"_blank\">" + tri.getValueByName(tc.getNumeJava()) + "</a>";
                        cell = new HtmlElementHelper("td", ref);
                        break;
                    case LINK:
                        //String ref = "<a href=\"" + tri.getValueByName(tc.getLinkul()) + "\" target=\"_blank\">" + tri.getValueByName(tc.getTitle()) + "</a>";
                        String link = "<a href=\"" + tri.getValueByName(tc.getLinkul()) + "\" target=\"_blank\">" + tri.getValueByName(tc.getNumeJava()) + "</a>";
                        cell = new HtmlElementHelper("td", link);
                        break;
                    default:
                        //cell = new HtmlElementHelper("td", tri.getValueByName(tc.getTitle()));
                        cell = new HtmlElementHelper("td", tri.getValueByName(tc.getNumeJava()));
                }
                cell.addAtribute("width", tc.getWidth() + "px");
                row.addHtmlElement(cell);
            }
            table.addHtmlElement(row);
        }
        return table;
    }
}

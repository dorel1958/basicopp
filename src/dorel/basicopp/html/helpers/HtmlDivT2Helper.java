package dorel.basicopp.html.helpers;

import dorel.basicopp.html.clases.StyleRule;
import dorel.basicopp.html.clases.StyleSheet;
import dorel.basicopp.html.interfaces.HtmlElementInterface;
import dorel.basicopp.html.interfaces.HtmlTableColumnInterface;
import dorel.basicopp.html.interfaces.HtmlTableRowInterface;
import java.util.List;

public class HtmlDivT2Helper {

    private List<HtmlTableColumnInterface> columns;
    private HtmlTableRowInterface tableRow;

    public void addColumn(HtmlTableColumnInterface col) {
        columns.add(col);
    }

    public void setRow(HtmlTableRowInterface tri) {
        tableRow = tri;
    }

    public void setColumns(List<HtmlTableColumnInterface> columns) {
        this.columns = columns;
    }

    public HtmlDivT2Helper(List<HtmlTableColumnInterface> columns, HtmlTableRowInterface row) {
        this.columns = columns;
        this.tableRow = row;
    }

    public static void addCss(StyleSheet style) {
        StyleRule sc;
        sc = new StyleRule(".divTableBo");
        sc.addProp("display", "table");
        sc.addProp("width", "auto");
        style.addStyleRule(sc);

        sc = new StyleRule(".divRowBo");
        sc.addProp("display", "table-row");
        sc.addProp("width", "auto");
        style.addStyleRule(sc);

        sc = new StyleRule(".divCellTitleBo");
        sc.addProp("display", "table-column");
        sc.addProp("float", "left");
        sc.addProp("width", "200px");
        style.addStyleRule(sc);

        sc = new StyleRule(".divCellValueBo");
        sc.addProp("display", "table-column");
        sc.addProp("float", "left");
        sc.addProp("width", "440px");
        sc.addProp("font-weight", "bold");
        style.addStyleRule(sc);
    }

    public HtmlElementInterface getElement() {
        HtmlElementInterface row;
        HtmlElementInterface cell;
        HtmlElementInterface table = new HtmlElementHelper("div");
        table.addAtribute("class", "divTableBo");
        //
        for (HtmlTableColumnInterface tc : columns) {
            row = new HtmlElementHelper("div");
            row.addAtribute("class", "divRowBo");

            // header
            cell = new HtmlElementHelper("div", tc.getTitle() + ": ");
            cell.addAtribute("class", "divCellTitleBo");
            row.addHtmlElement(cell);

            String value = tableRow.getValueByName(tc.getNumeJava());
            if (value.isEmpty()) {
                value = "&nbsp;";
            }
            cell = new HtmlElementHelper("div", value);
            cell.addAtribute("class", "divCellValueBo");
            row.addHtmlElement(cell);

            table.addHtmlElement(row);
        }
        return table;
    }
}

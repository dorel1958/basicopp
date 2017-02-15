package dorel.basicopp.html.tags;

import dorel.basicopp.html.helpers.HtmlElementHelper;


public class HtmlDiv extends HtmlElementHelper {

    public HtmlDiv(String cssClass) {
        super("div");
        addAtribute("class", cssClass);
    }

    public HtmlDiv(String cssClass, String sContent) {
        super("div", sContent);
        addAtribute("class", cssClass);
    }
}

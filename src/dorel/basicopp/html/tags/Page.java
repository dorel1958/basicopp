package dorel.basicopp.html.tags;

import dorel.basicopp.html.helpers.HtmlElementHelper;
import dorel.basicopp.html.interfaces.HtmlElementInterface;


public class Page extends HtmlElementHelper {

    public Page(HtmlElementInterface head, HtmlElementInterface body) {
        super("html");
        isPage = true;
        if (head == null) {
            addHtmlElement(new HtmlElementHelper("head"));
        } else {
            addHtmlElement(head);
        }
        if (body == null) {
            addHtmlElement(new HtmlElementHelper("body"));
        } else {
            addHtmlElement(body);
        }
    }
}

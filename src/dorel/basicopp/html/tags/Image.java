package dorel.basicopp.html.tags;

import dorel.basicopp.html.helpers.HtmlElementHelper;


public class Image extends HtmlElementHelper {

    public Image(String src) {
        super("img");
        addAtribute("src", src);
    }
    
}

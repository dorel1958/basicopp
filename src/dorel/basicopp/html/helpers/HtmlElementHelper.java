package dorel.basicopp.html.helpers;

import dorel.basicopp.html.interfaces.HtmlElementInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HtmlElementHelper implements HtmlElementInterface {

    protected TYPE type;
    private final List<HtmlElementInterface> elemente;
    private String sContent;
    private final String tagName;
    private final Map<String, String> atributes;
    protected boolean isPage = false;
    protected boolean isXmlDocument = false;

    public void setIsXmlDocument(boolean bo) {
        isXmlDocument = bo;
    }

    public void setIsPage(boolean bo) {
        isPage = bo;
    }

    public HtmlElementHelper(String tagName) {
        type = TYPE.complex;
        elemente = new ArrayList<>();
        atributes = new HashMap<>();
        this.tagName = tagName;
        sContent = "";
    }

    public HtmlElementHelper(String tagName, String sContent) {
        type = TYPE.text;
        elemente = new ArrayList<>();
        atributes = new HashMap<>();
        this.tagName = tagName;
        this.sContent = sContent;
    }

    @Override
    public String getText() {
        String cont = "";
        if (isXmlDocument || isPage) {
            cont += "<?xml version='1.0' encoding='UTF-8' ?>\n";
        }
        if (isPage) {
            cont += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 4.01//EN\">\n";
        }
        cont += "<" + tagName;
        Set<String> keys = atributes.keySet();
        for (String key : keys) {
            cont += " " + key + "=\"" + atributes.get(key) + "\"";
        }
        switch (type) {
            case text:
                if (sContent == null) {
                    cont += ">\n";
                    cont += "" + "\n";
                    cont += "</" + tagName + ">\n";
                } else {
                    if (sContent.isEmpty()) {
                        cont += "/>\n";
                    } else {
                        cont += ">\n";
                        cont += sContent + "\n";
                        cont += "</" + tagName + ">\n";
                    }
                }
                break;
            case complex:
                cont += ">\n";
                for (HtmlElementInterface he : elemente) {
                    cont += he.getText();
                }
                cont += sContent + "</" + tagName + ">\n";
                break;
            default:
                break;
        }
        return cont;
    }

    @Override
    public void addHtmlElement(HtmlElementInterface he) {
        elemente.add(he);
    }

    @Override
    public void setText(String text) {
        sContent = text;
        type = TYPE.text;
    }

    @Override
    public void addAtribute(String name, String value) {
        atributes.put(name, value);
    }

}

package dorel.basicopp.html.clases;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StyleRule {

    private String selector;

    private final Map<String, String> prop;

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void addProp(String key, String value) {
        prop.put(key, value);
    }

    public StyleRule(String selector) {
        this.selector = selector;
        prop = new HashMap<>();
    }

    public String getText() {
        String cont = "";
        cont += selector + " {\n";
        Set<String> keys = prop.keySet();
        for (String key : keys) {
            cont += key + ": " + prop.get(key) + ";\n";
        }
        cont += "}\n";
        return cont;
    }

}

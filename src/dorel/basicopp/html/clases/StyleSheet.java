package dorel.basicopp.html.clases;

import dorel.basicopp.html.helpers.HtmlElementHelper;
import java.util.ArrayList;
import java.util.List;

public class StyleSheet  extends HtmlElementHelper {

    List<StyleRule> lista;
    
    public StyleSheet() {
        super("style");
        addAtribute("type", "text/css");
        lista= new ArrayList<>();
    }
    
    public void addStyleRule(StyleRule sc){
        lista.add(sc);
    }

    @Override
    public String getText() {
        String text="";
        for(StyleRule sc: lista){
            text+=sc.getText();
        }
        this.setText(text);
        return super.getText();
    }
    
}

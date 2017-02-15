package dorel.basicopp.html.interfaces;

public interface HtmlElementInterface {

    public static enum TYPE {

        text,
        complex
    }

    public String getText();

    public void setText(String text);

    public void addHtmlElement(HtmlElementInterface he);

    public void addAtribute(String name, String value);
}

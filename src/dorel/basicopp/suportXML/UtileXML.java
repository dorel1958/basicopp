package dorel.basicopp.suportXML;

import dorel.basicopp.io.TextWriter;

public class UtileXML {

    public static void writeXMLLinie(TextWriter tw, String tag, String valoare) {
        tw.write("<" + tag);
        if (valoare.isEmpty()) {
            tw.write("/>");
        } else {
            tw.write(">" + valoare);
            tw.write("</" + tag + ">");
        }
    }

}

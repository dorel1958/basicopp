package dorel.basicopp.suportXML;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class DocumentXML {

    public static ElementXML FisierToElement(String numeFis) {
        ElementXML element = null;
        Document doc;
        //xmlFile = "D:\\Curente\\Java\\D112\\DinFox\\d112.xml";
        File fis = new File(numeFis);
        if (fis.exists()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                try {
                    doc = factory.newDocumentBuilder().parse(numeFis);
                    Element root = doc.getDocumentElement();
                    element = ElementXML.deSerializeElement(root);
                } catch (SAXException ex) {
                    JOptionPane.showMessageDialog(null, "SAXException: " + ex.getLocalizedMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "IOException: " + ex.getLocalizedMessage());
                }
            } catch (ParserConfigurationException ex) {
                JOptionPane.showMessageDialog(null, "ParserConfigurationException: " + ex.getLocalizedMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fisierul " + fis.getAbsolutePath() + " nu exista.");
        }
        return element;
    }

    public static Document ElementToDocXML(ElementXML element) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser;
        try {
            parser = factory.newDocumentBuilder();
            doc = parser.newDocument();
            doc.setXmlStandalone(true);
            //create & add the root element
            Element newRoot = element.serializeElement(doc);
            doc.appendChild(newRoot);
        } catch (ParserConfigurationException ex) {
            JOptionPane.showMessageDialog(null, "ParserConfigurationException: " + ex.getLocalizedMessage());
        }
        return doc;
    }

    public static void docXMLToFileXML(Document doc, String numeFis) {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tranFactory.newTransformer();
            //tran.setOutputProperty("standalone", "yes");
            //  http://www.w3.org/TR/xslt#output
            //<!-- Category: top-level-element -->
            // <xsl:output
            //   method = "xml" | "html" | "text" | qname-but-not-ncname 
            //   version = nmtoken 
            //   encoding = string 
            //   omit-xml-declaration = "yes" | "no"
            //   standalone = "yes" | "no"
            //   doctype-public = string 
            //   doctype-system = string 
            //   cdata-section-elements = qnames 
            //   indent = "yes" | "no"
            //   media-type = string />
            Source src = new DOMSource(doc);
            Result dest = new StreamResult(new File(numeFis));
            try {
                transformer.transform(src, dest);
            } catch (TransformerException ex) {
                JOptionPane.showMessageDialog(null, "TransformerException: " + ex.getLocalizedMessage());
            }
        } catch (TransformerConfigurationException ex) {
            JOptionPane.showMessageDialog(null, "TransformerConfigurationException: " + ex.getLocalizedMessage());
        }

    }
}

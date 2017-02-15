package dorel.basicopp.suportXML;

import dorel.basicopp.io.TextWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class ElementXML {

    private String name;
    private List<AtributXML> atribute;
    //private boolean areElemente;
    private String valoare;
    private List<ElementXML> elemente;

    //<editor-fold defaultstate="collapsed" desc="Get Set">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            this.name = "element";
        } else {
            this.name = name;
        }
    }

    public void setAtribute(List<AtributXML> atribute) {
        if (atribute == null) {
            this.atribute = new ArrayList<>();
        } else {
            this.atribute = atribute;
        }
    }

    public List<AtributXML> getAtribute() {
        return atribute;
    }

    public void setValoare(String valoare) {
        //areElemente = false;
        //elemente.clear();  // nu anulez elementele
        if (valoare == null) {
            this.valoare = "";
        } else {
            this.valoare = valoare;
        }
    }

    public String getValoare() {
        if (elemente.size() > 0) {
            JOptionPane.showMessageDialog(null, "ElementXML.getValoare - Elementul " + name + " nu are valoare, are elemente.");
            return "";
        } else {
            return valoare;
        }
    }

    public void setElemente(List<ElementXML> elemente) {
        //areElemente = true;
        //valoare = "";  // nu anulez valoarea
        if (elemente != null) {
            this.elemente = elemente;
        } else {
            this.elemente = new ArrayList<>();
        }
    }

    public List<ElementXML> getElemente() {
        //if (areElemente) {
        return elemente;
        //} else {
        //    JOptionPane.showMessageDialog(null, "ElementXML.getElemente - Elementul " + name + " nu are elemente, are valoare.");
        //    return new ArrayList<>();
        //}
    }
    //</editor-fold>

    public ElementXML(String name) {
        if (name == null || name.isEmpty()) {
            this.name = "element";
        } else {
            this.name = name;
        }
        atribute = new ArrayList<>();
        //areElemente = true;
        valoare = "";
        elemente = new ArrayList<>();
    }

    public ElementXML(String name, String valoare) {
        if (name == null || name.isEmpty()) {
            this.name = "element";
        } else {
            this.name = name;
        }
        if (valoare==null){
            this.valoare = "";
        } else {
            this.valoare = valoare;
        }
        atribute = new ArrayList<>();
        elemente = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="Operatii">
    public void addAtribut(AtributXML atribut) {
        // pentru a nu dubla atributele
        boolean gasit = false;
        for (AtributXML catribut : atribute) {
            if (catribut.getName().equals(atribut.getName())) {
                gasit = true;
                break;
            }
        }
        if (!gasit) {
            atribute.add(atribut);
        } else {
            JOptionPane.showMessageDialog(null, "ElementXML.addAtribut - Elementul " + name + " incercare de dublare atribut " + atribut.getName() + ".");
        }
    }

    public void addElement(ElementXML element) {
        //areElemente = true;
        elemente.add(element);
        //valoare = "";  // nu anulez valoarea
    }

    public void setAtribute(AtributXML atribut) {
        // modifica daca este, adauga daca nu este
        boolean gasit = false;
        for (AtributXML catribut : atribute) {
            if (catribut.getName().equals(atribut.getName())) {
                catribut.setValue(atribut.getValue());
                catribut.setRequired(atribut.isRequired());
                gasit = true;
                break;
            }
        }
        if (!gasit) {
            addAtribut(atribut);
        }
    }

    public String getAtribut(String aName, String valNull) {
        for (AtributXML atribut : atribute) {
            if (atribut.getName().equals(aName)) {
                return atribut.getValue();
            }
        }
        return valNull;
    }

    public List<ElementXML> getChildsNamed(String sName) {
        List<ElementXML> lista = new ArrayList<>();
        //if (areElemente) {
        for (ElementXML element : elemente) {
            if (element.getName().equals(sName)) {
                lista.add(element);
            }
        }
        //} else {
        //    JOptionPane.showMessageDialog(null, "ElementXML.getChildsNamed - Elementul " + name + " nu are elemente " + sName + ".");
        //}
        return lista;
    }

    public ElementXML getChildNamed(String sName) {
        List<ElementXML> lista = new ArrayList<>();
        for (ElementXML element : elemente) {
            if (element.getName().equals(sName)) {
                lista.add(element);
            }
        }
        ElementXML elementul;
        switch(lista.size()){
            case 0:
                JOptionPane.showMessageDialog(null, "ElementXML.getChildNamed - Elementul " + name + " nu are elemente cu numele:" + sName + ".");
                elementul=new ElementXML("sName");
                break;
            case 1:
                elementul= lista.get(0);
                break;
            default:
                JOptionPane.showMessageDialog(null, "ElementXML.getChildNamed - Elementul " + name + " nu are un singur element cu numele:" + sName + ".");
                elementul= lista.get(0);
                break;
        }
        return elementul;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="XML Document">
    public Element serializeElement(Document dom) {
        Element ele;
        ele = dom.createElement(name);
        for (AtributXML atribut : atribute) {
            if (atribut.isRequired() || !atribut.getValue().isEmpty()) {
                ele.setAttribute(atribut.getName(), atribut.getValue());
            }
        }
        if (elemente.size() > 0) {
            for (ElementXML element : elemente) {
                Element eleNew = element.serializeElement(dom);
                ele.appendChild(eleNew);
            }
        } else {
            Text contin;
            contin = dom.createTextNode(valoare);
            ele.appendChild(contin);
        }
        return ele;
    }

    public static ElementXML deSerializeElement(Element elem) {
        ElementXML element = new ElementXML(elem.getNodeName());
        // extrage atribute
        //if (elem.getNodeName().equals("id")){
        //    String xvalo;
        //    xvalo = elem.getNodeValue();
        //    String nume=elem.getNodeName();
        //    String zvalo="";
        //}
        NamedNodeMap attributesList = elem.getAttributes();
        for (int i = 0; i < attributesList.getLength(); i++) {
            element.addAtribut(new AtributXML(attributesList.item(i).getNodeName(), attributesList.item(i).getNodeValue(), true));
        }
        boolean eText=true;
        String valo;
        if (elem.hasChildNodes()) {
            // are Elemente -> completeaza List elemente
            NodeList nl = elem.getChildNodes();
            //int nnodes=nl.getLength();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                //int type=node.getNodeType();
                switch (node.getNodeType()) {
                    case Node.ELEMENT_NODE:
                        Element elemChild = (Element) node;
                        element.addElement(ElementXML.deSerializeElement(elemChild));
                        eText=false;
                        break;
                    case Node.TEXT_NODE:
                        valo = node.getNodeValue();
                        if (valo == null) {
                            valo = "";
                        }
                        if (!valo.equals("\n")){
                            element.setValoare(valo);
                        }
                        break;
                }
            }
        }
        if(!eText){
            element.setValoare("");
        }
        return element;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="write direct to file">
    public void writeElementXml(TextWriter tw) {
        tw.write("<" + name);
        // atribute
        for (AtributXML atribut : atribute) {
            if (!atribut.getValue().isEmpty() || atribut.isRequired()) {
                tw.write(" " + atribut.getName() + "=\"" + atribut.getValue() + "\"");
            }
        }
        if (elemente.size() > 0) {
            //if (elemente.isEmpty()) {
            //    tw.write("/>");
            //} else {
            tw.write(">" + valoare);
            for (ElementXML element : elemente) {
                element.writeElementXml(tw);
            }
            tw.write("</" + name + ">");
            //}
        } else {
            if (valoare.isEmpty()) {
                tw.write("/>");
            } else {
                tw.write(">" + valoare);
                tw.write("</" + name + ">");
            }
        }
    }

    public void writeAtrAsElem(TextWriter tw, String numeAtribut, String numeElement, String valoareImplicita) {
        UtileXML.writeXMLLinie(tw, numeElement, getAtribut(numeAtribut, valoareImplicita));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Teste">
    public boolean selfTest() {
        boolean raspuns = true;
        if (name == null) {
            JOptionPane.showMessageDialog(null, "ElementXML name null.");
            raspuns = false;
        }
        for (AtributXML atribut : atribute) {
            if (atribut == null) {
                JOptionPane.showMessageDialog(null, "ElementXML atribut null.");
                raspuns = false;
            }
        }
        if (elemente.size() > 0) {
            for (ElementXML element : elemente) {
                if (element == null) {
                    JOptionPane.showMessageDialog(null, "ElementXML element null.");
                    raspuns = false;
                } else {
                    boolean xraspuns = element.selfTest();
                    if (!xraspuns) {
                        raspuns = false;
                    }
                }
            }
        } else {
            if (valoare == null) {
                JOptionPane.showMessageDialog(null, "ElementXML " + name + " valoare null.");
                raspuns = false;
            }
        }
        return raspuns;
    }

    public boolean comparaCuElementD112(ElementXML element, boolean eFinal) {
        // NUMAI pentru D112
        String mEroare;
        boolean raspuns = true;
        if (!name.equals(element.getName())) {
            mEroare = "Elemente cu name diferit: '" + name + "' # '" + element.getName() + "'";
            if (eFinal) {
                JOptionPane.showMessageDialog(null, mEroare);
            }
            return false;
        }
        if (element.name.equals("angajatorA")) {
            if (!this.getAtribut("A_codOblig", "").equals(element.getAtribut("A_codOblig", ""))) {
                return false;
            }
        }
        if (element.name.equals("asigurat")) {
            if (!this.getAtribut("cnpAsig", "").equals(element.getAtribut("cnpAsig", ""))) {
                return false;
            }
        }
        if (element.name.equals("coAsigurati")) {
            if (!this.getAtribut("cnp", "").equals(element.getAtribut("cnp", ""))) {
                return false;
            }
        }
        if (element.name.equals("asiguratD")) {
            if (!this.getAtribut("D_5", "").equals(element.getAtribut("D_5", ""))) {
                return false;
            }
        }
        if (atribute.size() != element.atribute.size()) {
            mEroare = name + " - Numar Atribute diferit.";
            if (eFinal) {
                JOptionPane.showMessageDialog(null, mEroare);
            }
            return false;
        } else {
            for (AtributXML atribut : atribute) {
                String atribName = atribut.getName();
                if (atribName.equals("idAsig")) {
                    // sare peste
                } else {
                    if (!this.getAtribut(atribName, "").equals(element.getAtribut(atribName, ""))) {
                        mEroare = name + " - Atribute '" + atribName + "' diferite: '" + this.getAtribut(atribName, "") + "' # '" + element.getAtribut(atribName, "") + "'";
                        JOptionPane.showMessageDialog(null, mEroare);
                        raspuns = false;
                    }
                }
            }
        }
        if (elemente.size() > 0) {
            if (elemente.size() != element.elemente.size()) {
                mEroare = name + " - Numar Elemente diferit.";
                if (eFinal) {
                    JOptionPane.showMessageDialog(null, mEroare);
                }
                return false;
            } else {
                for (ElementXML cElement : elemente) {
                    boolean eEgal = false;
                    for (ElementXML dElement : element.elemente) {
                        if (cElement.comparaCuElementD112(dElement, false)) {
                            eEgal = true;
                            break;
                        }
                    }
                    if (!eEgal) {
                        raspuns = false;
                        if (eFinal) {
                            mEroare = name + " - subElement diferit: '" + cElement.getName() + "'";
                            if (cElement.name.equals("asigurat")) {
                                mEroare += ", cnpAsig= '" + cElement.getAtribut("cnpAsig", "") + "'";
                            }
                            JOptionPane.showMessageDialog(null, mEroare);
                            //mesajEroare += mEroare;
                        }
                    }
                }
            }
        } else {
            if (!valoare.equals(element.valoare)) {
                mEroare = name + " - Valori diferite: '" + valoare + "' # '" + element.valoare + "'";
                JOptionPane.showMessageDialog(null, mEroare);
                raspuns = false;
            }
        }
        return raspuns;
    }

    public boolean comparaCuElement(ElementXML element, TextWriter tw, boolean notTest) {
        boolean raspuns = true;
        String mEroare;
        boolean inchide = false;
        if (tw == null) {
            tw = new TextWriter("BasicOpp.suportXML.ElementXML.comparaCuElement.txt", false);
            inchide = true;
        }
        if (!name.equals(element.getName())) {
            if (notTest) {
                mEroare = "Elemente cu name diferit: '" + name + "' # '" + element.getName() + "'";
                tw.writeLine(mEroare);
            }
            return false;
        }
        if (atribute.size() != element.atribute.size()) {
            if (notTest) {
                mEroare = name + " - Numar Atribute diferit.";
                tw.writeLine(mEroare);
            }
            return false;
        } else {
            for (AtributXML atribut : atribute) {
                String atribName = atribut.getName();
                if (!this.getAtribut(atribName, "").equals(element.getAtribut(atribName, ""))) {
                    if (notTest) {
                        mEroare = name + " - Atribute '" + atribName + "' diferite: '" + this.getAtribut(atribName, "") + "' # '" + element.getAtribut(atribName, "") + "'";
                        tw.writeLine(mEroare);
                    }
                    raspuns = false;
                }
            }
        }
        if (elemente.size() > 0) {
            if (elemente.size() != element.elemente.size()) {
                mEroare = name + " - Numar Elemente diferit.";
                tw.writeLine(mEroare);
                return false;
            } else {
                for (ElementXML cElement : elemente) {
                    boolean eEgal = false;
                    for (ElementXML dElement : element.elemente) {
                        if (cElement.comparaCuElement(dElement, tw, false)) {
                            eEgal = true;
                            break;
                        }
                    }
                    if (!eEgal) {
                        if (notTest) {
                            mEroare = name + " - subElement diferit: '" + cElement.getName() + "'";
                            tw.writeLine(mEroare);
                        }
                        raspuns = false;
                    }
                }
            }
        } else {
            if (!valoare.equals(element.valoare)) {
                if (notTest) {
                    mEroare = name + " - Valori diferite: '" + valoare + "' # '" + element.valoare + "'";
                    tw.writeLine(mEroare);
                }
                raspuns = false;
            }
        }
        if (inchide) {
            tw.close();
        }
        return raspuns;
    }
    //</editor-fold>
}


//  public void processNode(Node node, String spacer) throws IOException {
//    if (node == null)
//      return;
//    switch (node.getNodeType()) {
//    case Node.ELEMENT_NODE:
//      String name = node.getNodeName();
//      System.out.print(spacer + "<" + name);
//      NamedNodeMap nnm = node.getAttributes();
//      for (int i = 0; i < nnm.getLength(); i++) {
//        Node current = nnm.item(i);
//        System.out.print(" " + current.getNodeName() + "= " + current.getNodeValue());
//      }
//      System.out.print(">");
//      NodeList nl = node.getChildNodes();
//      if (nl != null) {
//        for (int i = 0; i < nl.getLength(); i++) {
//          processNode(nl.item(i), "");
//        }
//      }
//      System.out.println(spacer + "</" + name + ">");
//      break;
//    case Node.TEXT_NODE:
//      System.out.print(node.getNodeValue());
//      break;
//    case Node.CDATA_SECTION_NODE:
//      System.out.print("" + node.getNodeValue() + "");
//      break;
//    case Node.ENTITY_REFERENCE_NODE:
//      System.out.print("&" + node.getNodeName() + ";");
//      break;
//    case Node.ENTITY_NODE:
//      System.out.print("<ENTITY: " + node.getNodeName() + "> </" + node.getNodeName() + "/>");
//      break;
//    case Node.DOCUMENT_NODE:
//      NodeList nodes = node.getChildNodes();
//      if (nodes != null) {
//        for (int i = 0; i < nodes.getLength(); i++) {
//          processNode(nodes.item(i), "");
//        }
//      }
//      break;
//    case Node.DOCUMENT_TYPE_NODE:
//      DocumentType docType = (DocumentType) node;
//      System.out.print("<!DOCTYPE " + docType.getName());
//      if (docType.getPublicId() != null) {
//        System.out.print(" PUBLIC " + docType.getPublicId() + " ");
//      } else {
//        System.out.print(" SYSTEM ");
//      }
//      System.out.println(" " + docType.getSystemId() + ">");
//      break;
//    default:
//      break;
//    }
//  }
//
//  public static void main(String[] args) {
//    String uri = "test.xml";
//    try {
//      bookDescDOM bd = new bookDescDOM();
//      System.out.println("Parsing XML File: " + uri + "\n\n");
//      DOMParser parser = new DOMParser();
//      parser.setFeature("http://xml.org/sax/features/validation", true);
//      parser.setFeature("http://xml.org/sax/features/namespaces", false);
//      parser.parse(uri);
//      Document doc = parser.getDocument();
//      bd.processNode(doc, "");
//    } catch (Exception e) {
//      e.printStackTrace();
//      System.out.println("Error: " + e.getMessage());
//    }
//  }

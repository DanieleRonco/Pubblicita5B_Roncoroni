package XMLParserPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Daniele Roncoroni
 */
public class XMLParser {
    //ATTRIBUTI
    private String xmlFile;
    private Document document;
    private Element root;
    
    //COSTRUTTORE
    //Costruttore parametrico - come solo parametro xmlFile
    public XMLParser(String xmlFile){
        this.xmlFile = xmlFile;
        this.document = null;
        this.root = null;
    }

    //GET
    public String getXmlFile() {
        return xmlFile;
    }
    public Document getDocument() {
        return document;
    }
    public Element getRoot() {
        return root;
    }

    //SET
    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }
    public void setDocument(Document document) {
        this.document = document;
    }
    public void setRoot(Element root) {
        this.root = root;
    }

    //METODI
    
    //Metodo per effettuare una richiesta http, creare il file .xml e salvare l'elemento "root"
    public void XMLFileBuilding(String risposta){
        PrintWriter out = null;
        try {
            out = new PrintWriter(xmlFile);
            out.print(risposta);
            out.close();
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            document = builder.parse(xmlFile);
            root = document.getDocumentElement();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo per ottenere la lista di elementi aventi il nome specificato (0, 1 o più)
    // N.B: nel passaggio del parametro potrebbe essere necessario effettuare il cast (es."(Element)lista.item(POSIZIONE)") se 'element' è in una lista
    public NodeList getElements(Element elemento, String nome){
        NodeList ritorno = elemento.getElementsByTagName(nome);
        if(ritorno != null && ritorno.getLength() > 0) return ritorno;
        else return null;
    }
    
    //Metodo per ottenere il valore testuale dell'attributo di un elemento specificato
    //{ inutile } perché già ritorna "" se non c'è nulla
    //elemento.getAttribute(nome);
    
    //Metodo per ottenere il valore testuale dell’elemento figlio specificato (0 o 1)
    // N.B: nel passaggio del parametro potrebbe essere necessario effettuare il cast (es."(Element)lista.item(POSIZIONE)") se 'element' è in una lista
    public String getTextValue(Element element, String tag) {
        NodeList nodelist = element.getElementsByTagName(tag);
        if (nodelist != null && nodelist.getLength() > 0) {
            return nodelist.item(0).getFirstChild().getNodeValue();
        }
        return null;
    }

    //Metodo per ottenere il valore intero dell’elemento figlio specificato
    public int getIntValue(Element element, String tag) {
        return Integer.parseInt(getTextValue(element, tag));
    }

    //Metodo per ottenere il valore numerico dell’elemento figlio specificato
    public float getFloatValue(Element element, String tag) {
        return Float.parseFloat(getTextValue(element, tag));
    }

    //Metodo per ottenere il valore numerico dell’elemento figlio specificato
    public double getDoubleValue(Element element, String tag) {
        return Double.parseDouble(getTextValue(element, tag));
    }
    
    //Metodo per ottenere il valore numerico dell’elemento figlio specificato
    public Long getLongValue(Element element, String tag) {
        return Long.parseLong(getTextValue(element, tag));
    }
}
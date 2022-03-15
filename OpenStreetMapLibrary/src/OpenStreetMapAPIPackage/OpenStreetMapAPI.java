//TODO: CAMBIARE XML PARSER
package OpenStreetMapAPIPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
public class OpenStreetMapAPI {
    //ATTRIBUTI
    private String xmlFile;
    private Document document;
    
    //COSTRUTTORI
    //Costruttore di default
    public OpenStreetMapAPI(){
        this.xmlFile = "";
        this.document = null;
    }
    //Costruttore parametrico - come solo parametro XmlFile
    public OpenStreetMapAPI(String XmlFile){
        this.xmlFile = XmlFile;
        this.document = null;
    }

    //GET
    public String getXmlFile() {
        return xmlFile;
    }
    public Document getDocument(){
        return this.document;
    }

    //SET
    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }
    public void setDocument(Document document) {
        this.document = document;
    }
    
    //METODI
    public OCoordinate TrovaCoordinate(String luogo) {
        PrintWriter out;
        try {
            out = new PrintWriter(xmlFile);
            String stringaURL = "https://nominatim.openstreetmap.org/search?q=" + encodeValue(luogo) + "&format=xml&addressdetails=1";
            URL url = new URL(stringaURL);
            Scanner s = new Scanner(url.openStream());
            s.useDelimiter("\u001a");
            String testo = s.next();
            out.print(testo);
            out.close();
        
            //N.B: può ritornare valore NULL
            return this.ConvertiDaXML(xmlFile);        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    
    }
    
    private OCoordinate ConvertiDaXML(String xmlFile) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Element root, element;
        NodeList nodelist;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        
        document = builder.parse(xmlFile);
        root = document.getDocumentElement();
        
        //N.B: essendo una procedura automatica, considero il primo risultato come quello più attendibile
        nodelist = root.getElementsByTagName("place");
        if(nodelist != null && nodelist.getLength() > 0){
            element = (Element) nodelist.item(0);
            return new OCoordinate(element.getAttribute("lat"), element.getAttribute("lon"));
        } else return null;
    }
    
    public double DistanzaTraDuePunti(OCoordinate c1, OCoordinate c2){
        double R = 6371; //raggio terrestre (km)
        double dLat = deg2rad(c2.getLatitudine()-c1.getLatitudine());
        double dLon = deg2rad(c2.getLongitudine()-c1.getLongitudine());
        double a = 
          Math.sin(dLat/2) * Math.sin(dLat/2) +
          Math.cos(deg2rad(c1.getLatitudine())) * Math.cos(deg2rad(c2.getLatitudine())) * 
          Math.sin(dLon/2) * Math.sin(dLon/2)
          ; 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        return R * c; //distanza (km)
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
    
    private String encodeValue(String ricerca) throws UnsupportedEncodingException {
        return URLEncoder.encode(ricerca, StandardCharsets.UTF_8.toString());
    }
}
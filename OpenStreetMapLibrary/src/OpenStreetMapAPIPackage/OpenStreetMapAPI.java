package OpenStreetMapAPIPackage;

import HTTPAPIPackage.*;
import XMLParserAPIPackage.XMLParser;
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
    private XMLParser parser;
    
    //COSTRUTTORI
    //Costruttore di default
    public OpenStreetMapAPI(){
        this.xmlFile = "";
        this.document = null;
        this.parser = new XMLParser();
    }
    //Costruttore parametrico - come solo parametro xmlFile
    public OpenStreetMapAPI(String XmlFile){
        this.xmlFile = XmlFile;
        this.document = null;
        this.parser = new XMLParser(xmlFile);
    }

    //GET
    public String getXmlFile() {
        return xmlFile;
    }
    public Document getDocument(){
        return this.document;
    }
    public XMLParser getParser(){
        return this.parser;
    }

    //SET
    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }
    public void setDocument(Document document) {
        this.document = document;
    }
    public void setParser(XMLParser parser){
        this.parser = parser;
    }
    
    //METODI
    public OCoordinate TrovaCoordinate(String luogo) {
        HHttp h = new HHttp();
        String stringaURL = null;
        try {
            stringaURL = "https://nominatim.openstreetmap.org/search?q=" + h.encodeValue(luogo) + "&format=xml&addressdetails=1";
            
            parser.HttpRequestAndXMLFileBuilding(stringaURL);
        
            NodeList lista = parser.getElements("place");
            if(lista != null){
                Element elemento = (Element)lista.item(0);
                return new OCoordinate(elemento.getAttribute("lat"), elemento.getAttribute("lon"));
            } else return null;        
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;  
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
}
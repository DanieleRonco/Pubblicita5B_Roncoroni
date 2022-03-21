package OpenStreetMapPackage;

import HTTPPackage.HHttp;
import XMLParserPackage.XMLParser;
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
    //Metodo per ottenere le coordinate di un luogo
    public OCoordinate TrovaCoordinate(String luogo) {
        HHttp h = new HHttp();
        String stringaURL = null;
        try {
            stringaURL = "https://nominatim.openstreetmap.org/search?q=" + h.encodeValue(luogo) + "&format=xml&addressdetails=1";

            parser.XMLFileBuilding(h.Richiesta(stringaURL));
        
            NodeList lista = parser.getElements(parser.getRoot(), "place");
            if(lista != null){
                Element elemento = (Element)lista.item(0);
                return new OCoordinate(elemento.getAttribute("lat"), elemento.getAttribute("lon"));
            } else return null;        
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;  
    }
    
    //Metodo per ottenere la distanza (in km) tra due punti (coordinate)
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

    //Metodo per convertire gradi in radianti
    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
    
    //Metodo per ottenere una lista di "Place"
    public List<OPlace> ListaDiPlace(String luogo){
        List<OPlace> ritorno = new ArrayList<OPlace>();
        try {
            HHttp httpHelper = new HHttp();
            XMLParser parserPlace = new XMLParser("xml/place.xml");
            
            String stringaURL = "https://nominatim.openstreetmap.org/search?q=" + httpHelper.encodeValue(luogo) + "&format=xml&addressdetails=1";
            parserPlace.XMLFileBuilding(httpHelper.Richiesta(stringaURL));
            
            NodeList listaPlace = parserPlace.getElements(parserPlace.getRoot(), "place");
            if(listaPlace != null){
                Element place;
                for(int i = 0; i < listaPlace.getLength(); i++){
                    place = (Element) listaPlace.item(i);
                    
                    OCoordinate coordinateDaInserire = new OCoordinate(place.getAttribute("lat"), place.getAttribute("lon"));
                    
                    String city = parserPlace.getTextValue(place, "city");
                    if(city == null) city = "";
                    
                    String county = parserPlace.getTextValue(place, "county");
                    if(county == null) county = "";
                    
                    String state = parserPlace.getTextValue(place, "state");
                    if(state == null) state = "";
                    
                    String postcode = parserPlace.getTextValue(place, "postcode");
                    if(postcode == null) postcode = "";
                    
                    String country = parserPlace.getTextValue(place, "country");
                    if(country == null) country = "";
                    
                    String countrycode = parserPlace.getTextValue(place, "country_code");
                    if(countrycode == null) countrycode = "";
                    
                    OPlace daInserire = new OPlace(coordinateDaInserire, city, county, state, postcode, country, countrycode);
                    ritorno.add(daInserire);
                }
            } else return null;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OpenStreetMapAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ritorno;
    }
}
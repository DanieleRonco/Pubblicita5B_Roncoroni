package HTTPAPIPackage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Roncoroni
 */
public class HHttp {
    //ATTRIBUTI
    
    //COSTRUTTORI
    public HHttp(){
        String ok = "";
    }
    
    //METODI
    public String Richiesta(String stringaURL){
        URL url = null;
        Scanner s = null;
        try {
            url = new URL(stringaURL);
            s = new Scanner(url.openStream());
            s.useDelimiter("\u001a");
            return s.next();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HHttp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HHttp.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return "";
    }
    
    public String encodeValue(String ricerca) throws UnsupportedEncodingException {
        return URLEncoder.encode(ricerca, StandardCharsets.UTF_8.toString());
    }
}
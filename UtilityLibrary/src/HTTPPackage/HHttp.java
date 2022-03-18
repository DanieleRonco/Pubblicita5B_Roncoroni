package HTTPPackage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    
    //GET
    
    //SET

    //METODI
    //Metodo che permette di effettuare una richiesta HTTP
    public String Richiesta(String stringaURL){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(stringaURL))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();
        } catch (IOException ex) {
            Logger.getLogger(HHttp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HHttp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //Metodo che permette di codificare un testo - viene utilizzato per l'URI
    public String encodeValue(String ricerca) throws UnsupportedEncodingException {
        return URLEncoder.encode(ricerca, StandardCharsets.UTF_8.toString());
    }
}
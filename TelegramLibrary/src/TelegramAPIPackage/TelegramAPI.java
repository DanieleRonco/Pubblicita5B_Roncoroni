package TelegramAPIPackage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

/**
 *
 * @author Daniele Roncoroni
 */
public class TelegramAPI {
    //URL
    private String URL;
    //API_KEY
    private String API_KEY;
    
    //ID dell'ultimo update da essere ritornato - usato in getUpdates per non ottenere una lista eccessivamente lunga
    long offset;
    
    //COSTRUTTORI
    //Costruttore di default
    public TelegramAPI(){
        this.API_KEY = "";
        this.URL = "";
        
        this.offset = 0;
    }
    //Costruttore parametrico
    public TelegramAPI(String API_KEY){
        this.API_KEY = API_KEY;
        this.URL = "https://api.telegram.org/bot" + API_KEY;
        
        this.offset = 0;
    }
    
    public List<TUpdate> getUpdates() throws IOException, InterruptedException{
        String getUpdatesURL = URL + "/getUpdates";
        if(offset != 0) getUpdatesURL = URL + "/getUpdates?offset=" + String.valueOf(offset);
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(getUpdatesURL))
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
        
        String risposta = response.body();
        
        List<TUpdate> ritorno = this.ConvertiDaJSONgetUpdates(risposta);
        offset = (ritorno.get(ritorno.size() - 1).getUpdateID());
        
        return ritorno;
    }

    public List<TUpdate> ConvertiDaJSONgetUpdates(String JSONBody) {
        JSONObject obj = new JSONObject(JSONBody);
        JSONArray arr = obj.getJSONArray("result");
        
        List<TUpdate> ListaUpdate = new ArrayList<TUpdate>();
        for (int i = 0; i < arr.length(); i++)
        {
            //ID dell'update
            int update_id = arr.getJSONObject(i).getInt("update_id");
            
            //Messaggio
            JSONObject message = arr.getJSONObject(i).getJSONObject("message");
            int message_id = message.getInt("message_id");
            long date = message.getLong("date");
            String text = message.getString("text");
            
            //From
            JSONObject from = message.getJSONObject("from");
            long from_id = from.getLong("id");
            boolean from_is_bot = from.getBoolean("is_bot");
            String from_first_name = from.getString("first_name");
            //String from_last_name = from.getString("last_name");
            //String from_language_code = from.getString("language_code");
            
            //Chat
            JSONObject chat = message.getJSONObject("chat");
            long chat_id = chat.getLong("id");
            String chat_first_name = chat.getString("first_name");
            //String chat_last_name = chat.getString("last_name");
            //String chat_type = chat.getString("type");            
            
            //TUser tUtente = new TUser(from_id, from_is_bot, from_first_name, from_last_name, from_language_code);
            TUser tUtente = new TUser(from_id, from_first_name);
            //TChat tChat = new TChat(chat_id, chat_first_name, chat_last_name, chat_type);
            TChat tChat = new TChat(chat_id, chat_first_name);
            TMessage tMessaggio = new TMessage(message_id, tUtente, tChat, date, text);
            TUpdate tAggiornamento = new TUpdate(update_id, tMessaggio);
            
            ListaUpdate.add(tAggiornamento);
        }
        
        return ListaUpdate;
    }
    
    public boolean sendMessage(String testoDaInviare, String CHAT_ID) throws UnsupportedEncodingException, IOException, InterruptedException{
        //if(offset != "") aggiungi offset;
        
        testoDaInviare = this.encodeValue(testoDaInviare);
        String sendMessageURL = URL + "/sendMessage?chat_id=" + CHAT_ID + "&text=" + testoDaInviare;
        
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(sendMessageURL))
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
        
        //Eventualmente vedi cosa fare con la risposta
        return this.ConvertiDaJSONsendMessage(response.body());
    }
    
    public boolean ConvertiDaJSONsendMessage(String JSONBody) {
        JSONObject obj = new JSONObject(JSONBody);
        if(obj.getBoolean("ok")) return true;
        else return false;
    }
    
    private String encodeValue(String ricerca) throws UnsupportedEncodingException {
	return URLEncoder.encode(ricerca, StandardCharsets.UTF_8.toString());
    }
}
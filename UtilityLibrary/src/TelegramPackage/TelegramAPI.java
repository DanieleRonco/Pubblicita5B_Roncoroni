package TelegramPackage;

import HTTPPackage.HHttp;
import JSONParserPackage.JSONParser;
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
    //Costruttore parametrico
    public TelegramAPI(String API_KEY){
        this.API_KEY = API_KEY;
        this.URL = "https://api.telegram.org/bot" + API_KEY;
        
        this.offset = 0;
    }
    
    public List<TUpdate> getUpdates() throws IOException, InterruptedException{
        String getUpdatesURL = URL + "/getUpdates";
        if(offset != 0) getUpdatesURL = URL + "/getUpdates?offset=" + String.valueOf(offset);
        
        HHttp httpHelper = new HHttp();
        String risposta = httpHelper.Richiesta(getUpdatesURL);
        
        List<TUpdate> ritorno = this.ConvertiDaJSONgetUpdates(risposta);
        
        if(ritorno != null ) offset = (ritorno.get(ritorno.size() - 1).getUpdateID()) + 1;
        
        return ritorno;
    }

    public List<TUpdate> ConvertiDaJSONgetUpdates(String JSONBody) {
        JSONParser jsonParser = new JSONParser(JSONBody);
        JSONArray arr = jsonParser.getJSONArrayIfExists(jsonParser.getObj(), "result");
        
        if(arr != null && arr.length() != 0){
            List<TUpdate> ListaUpdate = new ArrayList<TUpdate>();
            for(int i = 0; i < arr.length(); i++){
                //Elemento del vettore
                JSONObject elemento = arr.getJSONObject(i);
                
                String risultato;
                
                //update_id
                int update_id = 0;
                if(elemento.has("update_id")) update_id = elemento.getInt("update_id");
                
                //Messaggio
                JSONObject messaggio = jsonParser.getComplexElementIfExists(elemento, "message");
                int message_id = 0, date = 0;
                String text = "";
                
                if(messaggio != null){
                    //from, chat
                    //message_id
                    message_id = messaggio.getInt("message_id");
                    
                    //date
                    date = messaggio.getInt("date");
                    
                    //text
                    if(messaggio.has("text")) text = messaggio.getString("text");
                    
                    //from
                    JSONObject from = jsonParser.getComplexElementIfExists(messaggio, "from");
                    long from_id = 0;
                    boolean from_is_bot = false;
                    String from_first_name = "", from_last_name = "", from_nickname = "", from_language_code = "";
                    if(from != null){
                        //id, is_bot, first_name, last_name, username, language_code
                        from_id = from.getLong("id");
                        
                        from_is_bot = from.getBoolean("is_bot");
                        
                        from_first_name = from.getString("first_name");
                        
                        if(from.has("last_name")) from_last_name = from.getString("last_name");
                        
                        if(from.has("nickname")) from_nickname = from.getString("nickname");
                        
                        if(from.has("language_code")) from_language_code = from.getString("language_code");
                    }
                    
                    JSONObject chat = jsonParser.getComplexElementIfExists(messaggio, "chat");
                    long chat_id = 0;
                    String chat_first_name = "", chat_last_name = "", chat_nickname = "", chat_type = "";
                    if(chat != null){
                        
                        chat_id = chat.getLong("id");
                        
                        chat_first_name = chat.getString("first_name");
                        
                        if(chat.has("last_name")) chat_last_name = chat.getString("last_name");
                        
                        if(chat.has("nickname")) chat_nickname = chat.getString("nickname");
                        
                        if(chat.has("type")) chat_type = chat.getString("type");
                    }
                    
                    if(from != null && chat != null){
                        TUser userDaInserire = new TUser(from_id, from_is_bot, from_first_name, from_last_name, from_nickname, from_language_code);
                        TChat chatDaInserire = new TChat(chat_id, chat_first_name, chat_last_name, chat_nickname, chat_type);
                        TMessage messaggioDaInserire = new TMessage(message_id, userDaInserire, chatDaInserire, date, text);
                        TUpdate updateDaInserire = new TUpdate(update_id, messaggioDaInserire);
                        ListaUpdate.add(updateDaInserire);
                    }
                }
            }
            return ListaUpdate;
        } else return null;
    }
    
    public boolean sendMessage(String testoDaInviare, String CHAT_ID) throws UnsupportedEncodingException, IOException, InterruptedException{
        HHttp httpHelper = new HHttp();
        String sendMessageURL = URL + "/sendMessage?chat_id=" + CHAT_ID + "&text=" + httpHelper.encodeValue(testoDaInviare);
        
        return this.ConvertiDaJSONsendMessage(httpHelper.Richiesta(sendMessageURL));
    }
    
    public boolean ConvertiDaJSONsendMessage(String JSONBody) {
        JSONParser jsonParser = new JSONParser(JSONBody);
        
        if(jsonParser.getObj().getBoolean("ok")) return true;
        else return false;
    }
}
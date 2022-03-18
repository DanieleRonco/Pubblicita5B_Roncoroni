package TelegramAPIPackage;

import HTTPAPIPackage.HHttp;
import JSONParserAPIPackage.JSONParser;
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
        
        /*
        if(arr != null) System.out.println("ok");
        else System.out.println("No");
        
        List<TUpdate> ListaUpdate = new ArrayList<TUpdate>();
        for (int i = 0; i < arr.length(); i++)
        {
            String ritorno 
            //ID dell'Update
            
            if(jsonParser.getTextValueIfExists(arr.getJSONObject(i), ))
            
            
            //ID dell'update
            int id;
            if(arr.getJSONObject(i).has("update_id")) id = jsonParser.getIntValue(arr.getJSONObject(i), "update_id");
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
        */
    }
    
    public boolean sendMessage(String testoDaInviare, String CHAT_ID) throws UnsupportedEncodingException, IOException, InterruptedException{
        //if(offset != "") aggiungi offset;
        HHttp httpHelper = new HHttp();
        String sendMessageURL = URL + "/sendMessage?chat_id=" + CHAT_ID + "&text=" + httpHelper.encodeValue(testoDaInviare);
        
        //Eventualmente vedi cosa fare con la risposta
        return this.ConvertiDaJSONsendMessage(httpHelper.Richiesta(sendMessageURL));
    }
    
    public boolean ConvertiDaJSONsendMessage(String JSONBody) {
        JSONParser jsonParser = new JSONParser(JSONBody);
        
        if(jsonParser.getObj().getBoolean("ok")) return true;
        else return false;
    }
    
   
}


/*
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
        */
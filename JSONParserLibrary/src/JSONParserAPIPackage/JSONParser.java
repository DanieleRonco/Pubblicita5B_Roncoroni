package JSONParserAPIPackage;

import org.json.*;

/**
 *
 * @author Daniele Roncoroni
 */
public class JSONParser {
    //ATTRIBUTI
    JSONObject obj;
    
    //COSTRUTTORE
    //Costruttore di default
    public JSONParser(){
        this.obj = null;
    }
    //Costruttore parametrico
    public JSONParser(String JSONBody){
        this.obj = new JSONObject(JSONBody);
    }

    //GET
    public JSONObject getObj(){
        return obj;
    }
    
    //SET
    public void setObj(JSONObject obj){
        this.obj = obj;
    }

    //METODI
    //Metodo per ottenere un elemento di cui si specifica il nome e che è un vettore, contenuto in un elemento specificato
    //con controllo se esiste
    public JSONArray getJSONArray(JSONObject elemento, String nome){
        if(elemento.has(nome)) return elemento.getJSONArray(nome);
        else return null;
    }
    
    //in questo caso il controllo è meglio farlo prima
    //dato un elemento, ottenere il valore di un suo elemento
    //sempre string, così posso fare "!= null"
    public String getTextValue(JSONObject elemento, String nome){
        return elemento.getString(nome);
    }
    
    //dato un elemento, un suo elemento che può avere altri elementi
    public JSONObject getComplexElement(JSONObject elemento, String nome){
        if(elemento.has(nome)) return elemento.getJSONObject(nome);
        else return null;
    }
}
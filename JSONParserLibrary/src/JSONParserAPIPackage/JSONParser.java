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
    
    //Metodo per creare e salvare!
    
    //Metodo per ottenere un elemento vettore indicandone il nome e l'elemento nel quale è contenuto
    // N.B: controlla se esiste => può ritornare valore null
    public JSONArray getJSONArrayIfExists(JSONObject elemento, String nome){
        if(elemento.has(nome)) return elemento.getJSONArray(nome);
        else return null;
    }
    
    //Metodo per ottenere un elemento (complesso) indicandone il nome e l'elemento nel quale è contenuto
    // N.B: controlla se esiste => può ritornare valore null
    public JSONObject getComplexElementIfExists(JSONObject elemento, String nome){
        if(elemento.has(nome)) return elemento.getJSONObject(nome);
        else return null;
    }
    
    //Metodo per ottenere il valore String di un elemento indicandone il nome e l'elemento nel quale è contenuto
    public String getTextValue(JSONObject elemento, String nome){
        return elemento.getString(nome);
    }
    
    public int getIntValue(JSONObject elemento, String name){
        return Integer.parseInt(this.getTextValue(elemento, name));
    }
    
    public long getLongValue(JSONObject elemento, String name){
        return Long.parseLong(this.getTextValue(elemento, name));
    }
    
    public float getFloatValue(JSONObject elemento, String name){
        return Float.parseFloat(this.getTextValue(elemento, name));
    }
    
    public double getDoubleValue(JSONObject elemento, String name){
        return Double.parseDouble(this.getTextValue(elemento, name));
    }
}
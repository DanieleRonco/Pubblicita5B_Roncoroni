package JSONParserPackage;

import org.json.*;

/**
 *
 * @author Daniele Roncoroni
 */
public class JSONParser {
    //ATTRIBUTI
    JSONObject obj;
    
    //COSTRUTTORE
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
    
    //Metodo per controllare se l'elemento indicato ha il "campo" indicato e, in tal caso, ottenerne il valore (String, boolean, int ...)
    //{ inutile } perché basta fare
    //type valore = null/false/0;
    //if(elemento.has(nome)) valore = elemento.getString(nome) .getBoolean(nome) .getInt(nome) ...;
    //else { ... }
}
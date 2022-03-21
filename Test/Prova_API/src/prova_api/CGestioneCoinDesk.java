package prova_api;

import HTTPPackage.*;
import JSONParserPackage.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Daniele Roncoroni
 */
public class CGestioneCoinDesk {
    //ATTRIBUTI
    private String URL;
    
    //COSTRUTTORI
    public CGestioneCoinDesk(String URL){
        this.URL = URL;
    }

    //GET
    public String getURL() {
        return URL;
    }

    //SET
    public void setURL(String URL) {
        this.URL = URL;
    }
    
    //METODI
    public void InviaRichiesta(String ricerca){
        HHttp httpHelper = new HHttp();
        List<CUpdate> ritorno = this.ConvertiJSON(httpHelper.Richiesta(URL));
        
        for(int i = 0; i < ritorno.size(); i++){
            if(ritorno.get(i).getBpi().getCode().equals(ricerca)) System.out.println(ritorno.get(i).toString());
        }
    }
    
    private List<CUpdate> ConvertiJSON(String JSONBody){
        List<CUpdate> ritorno = new ArrayList<CUpdate>();
        JSONParser jsonParser = new JSONParser(JSONBody);
        
        JSONObject time = jsonParser.getComplexElementIfExists(jsonParser.getObj(), "time");
        
        String updated = "";
        if(time.has("updated")) updated = time.getString("updated");
        
        JSONObject bpi = jsonParser.getComplexElementIfExists(jsonParser.getObj(), "bpi");
        JSONObject EUR = jsonParser.getComplexElementIfExists(bpi, "EUR");
        
        String code = "";
        if(EUR.has("code")) code = EUR.getString("code");
        
        String symbol = "";
        if(EUR.has("symbol")) symbol = EUR.getString("symbol");
        
        String rate = "";
        if(EUR.has("rate")) rate = EUR.getString("rate");
        
        String description = "";
        if(EUR.has("description")) description = EUR.getString("description");
        
        float rate_float = 0;
        if(EUR.has("rate_float")) rate_float = EUR.getFloat("rate_float");
        
        CBpi bpiDaInserire = new CBpi(code, symbol, rate, description, rate_float);
        CUpdate updateDaInserire = new CUpdate(updated, bpiDaInserire);
        
        ritorno.add(updateDaInserire);
        return ritorno;
    }
}
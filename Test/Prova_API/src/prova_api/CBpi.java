package prova_api;

/**
 *
 * @author Daniele Roncoroni
 */
public class CBpi {
    //ATTRIBUTI
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private float rate_float;
    
    //COSTRUTTORI
    public CBpi(String code, String symbol, String rate, String description, float rate_float) {
        this.code = code;
        this.symbol = symbol;
        this.rate = rate;
        this.description = description;
        this.rate_float = rate_float;
    }

    //GET
    public String getCode() {
        return code;
    }
    public String getSymbol() {
        return symbol;
    }
    public String getRate() {
        return rate;
    }
    public String getDescription() {
        return description;
    }
    public float getRate_float() {
        return rate_float;
    }

    //SET
    public void setCode(String code) {
        this.code = code;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRate_float(float rate_float) {
        this.rate_float = rate_float;
    }
    
    public String toString(){
        return code + ";" + symbol + ";" + rate + ";" + description + ";" + rate_float;
    }
}
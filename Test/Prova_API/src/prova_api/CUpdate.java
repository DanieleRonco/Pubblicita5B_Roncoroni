package prova_api;

/**
 *
 * @author Daniele Roncoroni
 */
public class CUpdate {
    //ATTRIBUTI
    private String Updated;
    private CBpi Bpi;
    
    //COSTRUTTORI
    public CUpdate(String Updated, CBpi Bpi){
        this.Updated = Updated;
        this.Bpi = Bpi;
    }

    //GET
    public String getUpdated() {
        return Updated;
    }
    public CBpi getBpi() {
        return Bpi;
    }

    //SET
    public void setUpdated(String Updated) {
        this.Updated = Updated;
    }
    public void setBpi(CBpi Bpi) {
        this.Bpi = Bpi;
    }
    
    public String toString(){
        return Updated + ";" + Bpi.toString();
    }
}
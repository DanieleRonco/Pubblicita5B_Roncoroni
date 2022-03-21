package TelegramPackage;

/**
 *
 * @author Daniele Roncoroni
 */
public class TUpdate {
    //ATTRIBUTI
    //ID dell'update
    private long UpdateID;
    //Messaggio dell'update
    private TMessage Messaggio;
    
    //COSTRUTTORI
    //Costruttore parametrico
    public TUpdate(long UpdateID, TMessage Messaggio){
        this.UpdateID = UpdateID;
        this.Messaggio = Messaggio;
    }
    
    //METODI GET
    public long getUpdateID() {
        return UpdateID;
    }
    public TMessage getMessaggio() {
        return Messaggio;
    }

    //METODI SET
    public void setUpdateID(long UpdateID) {
        this.UpdateID = UpdateID;
    }
    public void setMessaggio(TMessage Messaggio) {
        this.Messaggio = Messaggio;
    }
}
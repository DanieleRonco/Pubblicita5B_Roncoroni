package TelegramAPIPackage;

/**
 *
 * @author Daniele Roncoroni
 */
public class TChat {
    //ATTRIBUTI
    //ID dell'utente
    private long UserID;
    //Nome dell'utente
    private String FirstName;
    //Cognome dell'utente
    private String LastName;
    //Tipo della chat
    private String Type;
    
    //COSTRUTTORI
    //Costruttore di default
    public TChat(){
        this.UserID = 0;
        this.FirstName = "";
        this.LastName = "";
        this.Type = "";
    }
    //Costruttore parametrico - con solo attributi obbligatori
    public TChat(long ID, String FirstName){
        this.UserID = ID;
        this.FirstName = FirstName;
        this.LastName = "";
        this.Type = "";
    }
    //Costruttore parametrico
    public TChat(long ID, String FirstName, String LastName, String Type) {
        this.UserID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Type = Type;
    }

    //METODI GET
    public long getID() {
        return UserID;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }
    public String getType(){
        return Type;
    }

    //METODI SET
    public void setID(long ID) {
        this.UserID = ID;
    }
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    public void setType(String Type){
        this.Type = Type;
    }
}
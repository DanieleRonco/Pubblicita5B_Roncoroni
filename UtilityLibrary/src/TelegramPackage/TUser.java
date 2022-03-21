package TelegramPackage;

/**
 *
 * @author Daniele Roncoroni
 */
public class TUser {
    //ATTRIBUTI
    //ID dell'utente
    private long UserID;
    //Indica se l'utente è un bot oppure no
    private boolean Bot;
    //Nome dell'utente
    private String FirstName;
    //Cognome dell'utente
    private String LastName;
    //Username dell'utente
    private String Username;
    //Codice del linguaggio dell'utente
    private String LanguageCode;
    
    //COSTRUTTORI
    //Costruttore parametrico - con solo attributi obbligatori e con bot sempre a false
    public TUser(long ID, String FirstName){
        this.UserID = ID;
        this.Bot = false;
        this.FirstName = FirstName;
        this.LastName = "";
        this.Username = "";
        this.LanguageCode = "";
    }
    //Costruttore parametrico - con bot come parametro
    public TUser(long ID, boolean Bot, String FirstName, String LastName, String Username, String LanguageCode) {
        this.UserID = ID;
        this.Bot = Bot;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Username = Username;
        this.LanguageCode = LanguageCode;
    }
    //Costruttore parametrico - con bot sempre a false
    public TUser(long ID, String FirstName, String LastName, String Username, String LanguageCode) {
        this.UserID = ID;
        this.Bot = false;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Username = Username;
        this.LanguageCode = LanguageCode;
    }

    //METODI GET
    public long getID() {
        return UserID;
    }
    public boolean isBot() {
        return Bot;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }
    public String getNickname(){
        return Username;
    }
    public String getLanguageCode(){
        return LanguageCode;
    }

    //METODI SET
    public void setID(long ID) {
        this.UserID = ID;
    }
    public void setBot(boolean Bot) {
        this.Bot = Bot;
    }
    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    public void setUsername(String Username) {
        this.Username = Username;
    }
    public void setLanguageCode(String LanguageCode){
        this.LanguageCode = LanguageCode;
    }
}
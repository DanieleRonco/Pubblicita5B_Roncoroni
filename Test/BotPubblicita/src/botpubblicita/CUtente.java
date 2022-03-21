package botpubblicita;

/**
 *
 * @author Daniele Roncoroni
 */
public class CUtente {
    //ATTRIBUTI
    private long IDChat;
    private String Nome;
    private double Latitudine;
    private double Longitudine;
    
    //COSTRUTTORI
    //Costruttore di default
    public CUtente(){
        this.IDChat = 0;
        this.Nome = "";
        this.Latitudine = 0;
        this.Longitudine = 0;
    }
    //Costruttore parametrico
    public CUtente(long IDChat, String Nome, double latitudine, double longitudine) {
        this.IDChat = IDChat;
        this.Nome = Nome;
        this.Latitudine = latitudine;
        this.Longitudine = longitudine;
    }

    //GET
    public long getIDChat() {
        return IDChat;
    }
    public String getNome() {
        return Nome;
    }
    public double getLatitudine() {
        return Latitudine;
    }
    public double getLongitudine() {
        return Longitudine;
    }

    //SET
    public void setIDChat(long IDChat) {
        this.IDChat = IDChat;
    }
    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    public void setLatitudine(double latitudine) {
        this.Latitudine = latitudine;
    }
    public void setLongitudine(double longitudine) {
        this.Longitudine = longitudine;
    }
    
    //METODI
    public String toCsv(){
        return this.IDChat + ";" + this.Nome + ";" + this.Latitudine + ";" + this.Longitudine + ";\n";
    }
}
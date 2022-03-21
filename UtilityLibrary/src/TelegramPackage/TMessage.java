package TelegramPackage;

/**
 *
 * @author Daniele Roncoroni
 */
public class TMessage {
    //ATTRIBUTI
    //ID del messaggio
    private long MessageID;
    //Mittente
    private TUser From;
    //Mittente in Chat
    private TChat Chat;
    //Data
    private long Date;
    //Testo - Contenuto
    private String Text;
    
    //COSTRUTTORI
    //Costruttore parametrico
    public TMessage(long MessageID, TUser From, TChat Chat, long Date, String Text) {
        this.MessageID = MessageID;
        this.From = From;
        this.Chat = Chat;
        this.Date = Date;
        this.Text = Text;
    }

    //METODI GET
    public long getMessageID() {
        return MessageID;
    }
    public TUser getFrom() {
        return From;
    }
    public TChat getChat() {
        return Chat;
    }
    public long getDate() {
        return Date;
    }
    public String getText() {
        return Text;
    }

    //METODI SET
    public void setMessageID(long MessageID) {
        this.MessageID = MessageID;
    }
    public void setFrom(TUser From) {
        this.From = From;
    }
    public void setChat(TChat Chat) {
        this.Chat = Chat;
    }
    public void setDate(long Date) {
        this.Date = Date;
    }
    public void setText(String Text) {
        this.Text = Text;
    }
}
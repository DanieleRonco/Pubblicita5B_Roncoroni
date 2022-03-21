package botpubblicita_ripasso;

/**
 *
 * @author Daniele Roncoroni
 */
public class CUtente {
    private Long ChatId;
    private String Nome;
    private Double Latitudine;
    private Double Longitudine;

    public CUtente(Long ChatId, String Nome, Double Latitudine, Double Longitudine) {
        this.ChatId = ChatId;
        this.Nome = Nome;
        this.Latitudine = Latitudine;
        this.Longitudine = Longitudine;
    }

    public Long getChatId() {
        return ChatId;
    }
    public String getNome() {
        return Nome;
    }
    public Double getLatitudine() {
        return Latitudine;
    }
    public Double getLongitudine() {
        return Longitudine;
    }

    public void setChatId(Long ChatId) {
        this.ChatId = ChatId;
    }
    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    public void setLatitudine(Double Latitudine) {
        this.Latitudine = Latitudine;
    }
    public void setLongitudine(Double Longitudine) {
        this.Longitudine = Longitudine;
    }
    
    public String toCsv(){
        return ChatId + ";" + Nome + ";" + Latitudine + ";" + Longitudine + ";\n";
    }
}
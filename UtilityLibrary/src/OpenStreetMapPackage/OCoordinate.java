package OpenStreetMapPackage;

/**
 *
 * @author Daniele Roncoroni
 */
public class OCoordinate {
    //ATTRIBUTI
    //Latitudine
    private double latitudine;
    //Longitudine
    private double longitudine;
    
    //COSTRUTTORI
    //Costruttore parametrico - latitudine e longitudine long
    public OCoordinate(double latitudine, double longitudine){
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }
    //Costruttore parametrico - latitudine e longitudine string
    public OCoordinate(String latitudine, String longitudine){
        this.latitudine = Double.parseDouble(latitudine);
        this.longitudine = Double.parseDouble(longitudine);
    }

    //GET
    public double getLatitudine() {
        return latitudine;
    }
    public double getLongitudine() {
        return longitudine;
    }

    //SET
    public void setLatitudine(long latitudine) {
        this.latitudine = latitudine;
    }
    public void setLongitudine(long longitudine) {
        this.longitudine = longitudine;
    }
    
    //METODI
    @Override
    public String toString() {
        return "Latitudine: " + Double.toString(latitudine) + "   Longitudine: " + Double.toString(longitudine);
    }
}
package OpenStreetMapPackage;

/**
 *
 * @author Daniele Roncoroni
 */
public class OPlace {
    //ATTRIBUTI
    private OCoordinate Coordinate;
    private String City;
    private String County;
    private String State;
    private String Postcode;
    private String Country;
    private String CountryCode;
    
    //COSTRUTTORI
    //Costruttore parametrico
    public OPlace(OCoordinate Coordinate, String City, String County, String State, String Postcode, String Country, String CountryCode) {
        this.Coordinate = Coordinate;
        this.City = City;
        this.County = County;
        this.State = State;
        this.Postcode = Postcode;
        this.Country = Country;
        this.CountryCode = CountryCode;
    }

    public OCoordinate getCoordinate() {
        return Coordinate;
    }
    public String getCity() {
        return City;
    }
    public String getCounty() {
        return County;
    }
    public String getState() {
        return State;
    }
    public String getPostcode() {
        return Postcode;
    }
    public String getCountry() {
        return Country;
    }
    public String getCountryCode() {
        return CountryCode;
    }

    public void setCoordinate(OCoordinate Coordinate) {
        this.Coordinate = Coordinate;
    }
    public void setCity(String City) {
        this.City = City;
    }
    public void setCounty(String County) {
        this.County = County;
    }
    public void setState(String State) {
        this.State = State;
    }
    public void setPostcode(String Postcode) {
        this.Postcode = Postcode;
    }
    public void setCountry(String Country) {
        this.Country = Country;
    }
    public void setCountryCode(String CountryCode) {
        this.CountryCode = CountryCode;
    }
}
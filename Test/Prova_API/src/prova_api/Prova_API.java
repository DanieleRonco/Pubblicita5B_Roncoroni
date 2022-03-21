package prova_api;

/**
 *
 * @author Daniele Roncoroni
 */
public class Prova_API {
    public static void main(String[] args) {
        CGestioneCoinDesk gcd = new CGestioneCoinDesk("https://api.coindesk.com/v1/bpi/currentprice.json");
        
        gcd.InviaRichiesta("EUR");
    }
}
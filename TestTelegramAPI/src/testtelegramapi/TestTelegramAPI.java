package testtelegramapi;

import OpenStreetMapAPIPackage.*;
import TelegramAPIPackage.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.json.*;
import org.xml.sax.SAXException;

public class TestTelegramAPI {
    public static void main(String[] args) throws IOException, InterruptedException, FileNotFoundException, UnsupportedEncodingException, MalformedURLException, ParserConfigurationException, SAXException {
        /*
        TelegramAPI tUtility = new TelegramAPI("5132830366:AAFtRD0PsuAuYtsKOqmrCg9sdbZ14rvN0zM");
        
        Scanner jin = new Scanner(System.in);
        String testoDaInviare = jin.nextLine();
        String ID;
        ID = "";
        ID = "5123384303";
        tUtility.sendMessage(testoDaInviare, ID);
        
        List<TUpdate> Lista = tUtility.getUpdates();
        Lista = tUtility.getUpdates();
        */
        
        OpenStreetMapAPI osma = new OpenStreetMapAPI();
        OCoordinate coordinate = osma.TrovaCoordinate("");
        System.out.println(coordinate.getLatitudine());
        System.out.println(coordinate.getLongitudine());
    }
}
package testtelegramapi;

import TelegramAPIPackage.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.json.*;

public class TestTelegramAPI {
    public static void main(String[] args) throws IOException, InterruptedException {
        String ID = "5123384303";
        TelegramAPI tUtility = new TelegramAPI("5132830366:AAFtRD0PsuAuYtsKOqmrCg9sdbZ14rvN0zM", ID);
        
        //Scanner jin = new Scanner(System.in);
        //String testoDaInviare = jin.nextLine();
        tUtility.sendMessage("Ciao");
        
        //List<TUpdate> Lista = tUtility.getUpdates();
    }
}
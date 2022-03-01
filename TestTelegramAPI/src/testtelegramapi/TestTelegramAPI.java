package testtelegramapi;

import TelegramAPIPackage.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.json.*;

public class TestTelegramAPI {
    public static void main(String[] args) throws IOException, InterruptedException {
        TelegramAPI tUtility = new TelegramAPI("5132830366:AAFtRD0PsuAuYtsKOqmrCg9sdbZ14rvN0zM");
        
        /*
        Scanner jin = new Scanner(System.in);
        String testoDaInviare = jin.nextLine();
        String ID;
        ID = "";
        ID = "578041481";
        tUtility.sendMessage(testoDaInviare, ID);
        */
        
        List<TUpdate> Lista = tUtility.getUpdates();
        
        while(true);
    }
}
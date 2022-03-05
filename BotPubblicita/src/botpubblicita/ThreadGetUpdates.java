package botpubblicita;

import TelegramAPIPackage.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Roncoroni
 */
public class ThreadGetUpdates extends Thread {
    //ATTRIBUTI
    TelegramAPI tBot;
    
    //COSTRUTTORE
    //Costruttore di default
    public ThreadGetUpdates(){
        this.tBot = null;
    }
    //Costruttore parametrico - api_key
    public ThreadGetUpdates(String api_key){
        this.tBot = new TelegramAPI(api_key);
    }

    //METODI
    @Override
    public void run() {
        System.out.println("ThreadGetUpdates: THREAD AVVIATO!");
        try{
            List<TUpdate> listaUpdates;
            while(true){
                listaUpdates = tBot.getUpdates();
                
                if(listaUpdates.size() != 0){
                    //c'è qualcosa all'interno della lista
                    System.out.println("c'è qualcosa all'interno della lista");
                    for(int i = 0; i < listaUpdates.size(); i++){
                        System.out.println(listaUpdates.get(i).getMessaggio().getText());
                    }
                } else {
                    //non c'è nulla all'interno della lista
                    System.out.println("non c'è nulla all'interno della lista");
                }
                
                Thread.sleep(10000); //TODO: da cambiare con 60000
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadGetUpdates.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}